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
			file = new RandomAccessFile("BTreeFile", "rwd");
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
	
	public static int optimalDegree() {
		return 3;
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
	
	private BTreeNode GetNodeFromFile(int location) throws IOException {
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
	
	public static void main(String[] args) throws IOException {
		BTree tree = new BTree();
	}
}
