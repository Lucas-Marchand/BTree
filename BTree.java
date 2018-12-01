import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 *  BTree file Layout
 *  | BOF | int location of root | int degree | beginning of root node | ... rest of nodes | EOF |
 *  
 *  BTreeNode Layout
 *  | BOF | int location | int numObject | Boolean leaf | int TreeObject #1 | int TreeObject #2 | int TreeObject #n | EOF |
 *  
 *  TreeObject Layout
 *  | BOF | long key | int frequency | EOF |
 *  
 *  
 */
public class BTree {
	int rootLocation;
	int numTreeObjects;
	int keySize;
	int degree;
	int sizeOfMetaData = 8;
	BTreeNode root;

	static RandomAccessFile file;

	public BTree(int degree){
		try {
			file = new RandomAccessFile("BTreeFile", "rwd"); 	// TODO
			this.degree = degree;
			numTreeObjects = CalcNumTreeObjects(this.degree);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeTree() throws IOException {
		rootLocation = (int) file.getFilePointer();
		BTreeNode root = new BTreeNode(rootLocation, numTreeObjects, true, degree);
		WriteNodeToFile(root);
		WriteMetaData();
	}

	private static int optimalDegree() {

        double optimal= 4096;
        int Pointer = 4;
        int Object = 12 ;
        int Metadata =12 ;
    
        optimal += Object;
        optimal -= Pointer;
        optimal -= Metadata;
        optimal /= (2 * (Object + Pointer));
        return (int) Math.floor(optimal);
	}

	public BTree() {
		this(optimalDegree());
	}

	private void WriteMetaData() throws IOException {
		file.seek(0);

		// locaton of root
		file.writeInt(rootLocation);

		// the degree of the BTree
		file.writeInt(degree);
	}

	private static void PrintMetaData() throws IOException {
		file.seek(0);
		System.out.println("Location: " + file.readInt());
		System.out.println("Degree: " + file.readInt());
	}

	private void WriteNodeToFile(BTreeNode node) throws IOException {
		// long Location
		file.writeInt( (int) file.getFilePointer());

		// int num Objects
		file.writeInt(node.getNumObjects());

		// Boolean leaf
		file.writeBoolean(node.isLeaf());

		// Boolean leaf
		file.writeInt(degree);

		// Write Tree Objects to file
		TreeObject[] objects = node.getObjects();
		for(TreeObject to : objects) {
			file.writeLong(to.getKey());
			file.writeInt(to.getFrequency());
		}

		// write all children pointers to file
		int[] children = node.getChildren();
		for (int child : children ) {
			file.writeInt(child);
		}
	}

	private void WriteNodeToFile(BTreeNode node, int location) throws IOException {
		file.seek(location);

		// long Location
		file.writeInt( (int) file.getFilePointer());

		// int num Objects
		file.writeInt(node.getNumObjects());

		// Boolean leaf
		file.writeBoolean(node.isLeaf());

		// Boolean leaf
		file.writeInt(degree);

		// Write Tree Objects to file
		TreeObject[] objects = node.getObjects();
		for(TreeObject to : objects) {
			file.writeLong(to.getKey());
			file.writeInt(to.getFrequency());
		}

		// write all children pointers to file
		int[] children = node.getChildren();
		for (int child : children ) {
			file.writeInt(child);
		}
	}

	// recursive add method to add to a BTree 
	public void add(long data,int location) {

	}

	private int CalcNumTreeObjects(int deg) {
		return (2*deg-1);
	}

	public BTreeNode GetNodeFromFile(int location) throws IOException {
		file.seek(location);
		BTreeNode bNode = new BTreeNode(file.readInt(),file.readInt(),file.readBoolean(),file.readInt());

		// Write Tree Objects to file
		TreeObject[] objects = new TreeObject[bNode.getNumObjects()];
		for(TreeObject to : objects) {
			to = new TreeObject(file.readLong(),file.readInt());
		}

		// write all children pointers to file
		int[] children = bNode.getChildren();
		for (int child : children ) {
			child = file.readInt();
		}
		return null;
	}

	public void SplitChild(int parentOfSplittingNode, int indexToSplitOn) throws IOException {


		BTreeNode x = GetNodeFromFile(parentOfSplittingNode);

		// 1
		BTreeNode z = new BTreeNode( (int) file.getFilePointer(), numTreeObjects, true, degree);

		// 2
		BTreeNode y = GetNodeFromFile(z.getChildOffsetAt(indexToSplitOn));

		// 3
		z.setLeaf(y.isLeaf());

		// 4
		z.setNumObjects(degree - 1);

		// 5
		for (int j = 1; j < degree - 1; j++) {
			// 6
			z.setObjectAt(j, y.getObjectAt(j+degree));
		}

		// 7
		if(!y.isLeaf()) {
			// 8
			for(int j = 1; j < degree; j++) {
				// 9
				z.setChildrenOffsetAt(j, y.getChildOffsetAt(j+degree));
			}
		}

		// 10
		y.setNumObjects(degree-1);

		// 11
		for(int j = x.getNumObjects()+1; j > indexToSplitOn+1; j--) {
			// 12
			x.setChildrenOffsetAt(j+1, x.getChildOffsetAt(j));
		}

		// 13
		x.setChildrenOffsetAt( indexToSplitOn + 1, z.getnodeOffset());

		// 14
		for (int j = x.getNumObjects(); j > indexToSplitOn; j--) {
			// 15
			x.setObjectAt(j+1, x.getObjectAt(j));
		}

		// 16
		x.setObjectAt(indexToSplitOn, y.getObjectAt(degree));

		// 17
		x.setNumObjects(x.getNumObjects() + 1);

		// 18
		WriteNodeToFile(y);

		// 19
		WriteNodeToFile(z);

		// 20
		WriteNodeToFile(x);
	}

	public void insert (long k) throws IOException { 		// Deleted parameter T
		//1
		BTreeNode r = this.root;
		//2
		if (r.getNumObjects()== (2*degree-1)) {
			//3
			BTreeNode s = new BTreeNode((int) file.getFilePointer(), numTreeObjects,degree);	// TODO
			//4
			this.root = s;
			//5							// TODO
			boolean l = s.isLeaf();
			l = false;
			//6
			s.setNumObjects(0);
			//7
			s.setChildrenOffsetAt(1, r.getnodeOffset());
			//8
			SplitNodeInFile(s,1);		// TODO
			//9
			insertNonFull(s, k);	
		}else {
			//10
			insertNonFull(r, k);
		}
	}

	// TODO change frequency
	public void insertNonFull(BTreeNode x, long k) throws IOException {
		BTreeNode ch = null;

		//1
		int i = x.getNumObjects();
		//2
		if (x.isLeaf()) {
			//3
			while (i >= 1 && k < x.getObjectAt(i).getKey()) {
				//4
				x.setObjectAt(i+1, new TreeObject(k) );
				//5
				i = i-1;
			}
			//6??  
			x.setObjectAt(i+1,new TreeObject(k));
			//7
			i = x.getNumObjects()+1;
			//8
			WriteNodeToFile(x);  
			//9

		}else {
			while (i >= 1 && k < ch.getNumObjects()) {
				//10
				i = i-1;  
			}
			//11
			i = i+1;
			//12
			ch = GetNodeFromFile(x.getChildOffsetAt(i));
			//13
			if (ch.getNumObjects()==2*degree-1) {
				//14
				SplitChild(x, i);				// TODO
				//15
				if (k>ch.getNumObjects()) {
					//16
					i = i+1; 
				}
			}
			//17
			ch = GetNodeFromFile(x.getChildOffsetAt(i));
			insertNonFull(ch, k);


		}
	}

	public TreeObject search(BTreeNode x, long key) throws IOException {
		int i = 1;
		while (i <= x.getNumObjects() && key > x.getObjectAt(i).getKey()) {
			i++;
		}
		if (i <= x.getNumObjects() && key == x.getObjectAt(i).getKey()) {
			return x.getObjectAt(i);
		}
		else if (x.isLeaf()) {
			return null;
		} else {
			return search(GetNodeFromFile(x.getChildOffsetAt(i)), key);
		}
	}
}
