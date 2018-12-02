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
	static int rootLocation;
	int keySize;
	static int degree;
	int sizeOfMetaData = 8;
	static BTreeNode root;

	static RandomAccessFile file;

	public BTree(int degree, String BTreeFile) {
		try {
			file = new RandomAccessFile("BTree", "rwd"); // TODO
			this.degree = degree;
			root = new BTreeNode(sizeOfMetaData, true, degree);
			file.seek(8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeTree() throws IOException {
		rootLocation = (int) file.getFilePointer();
		root.setnodeOffset(rootLocation);
		WriteNodeToFile(root);
		WriteMetaData();
	}

	private static int optimalDegree() {

		double optimal = 4096;
		int Pointer = 4;
		int Object = 12;
		int Metadata = 12;

		optimal += Object;
		optimal -= Pointer;
		optimal -= Metadata;
		optimal /= (2 * (Object + Pointer));
		return (int) Math.floor(optimal);
	}

	public BTree(String bTreeFile) {
		this(optimalDegree(), bTreeFile);
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
		System.out.println("Root Location: " + file.readInt());
		System.out.println("Degree: " + file.readInt());
	}

	private static void PrintIntData(int location) throws IOException {
		file.seek(location);
		System.out.println("Int data is: " + file.readInt());
	}

	private static void PrintRootData() throws IOException {
		BTreeNode node = GetNodeFromFile(rootLocation);
		node.toString();
	}
	
	private static void PrintNodeData(int index) throws IOException {
		BTreeNode node = GetNodeFromFile(index);
		node.toString();
	}

	private void WriteNodeToFile(BTreeNode node) throws IOException {
		// long Location
		file.writeInt((int) file.getFilePointer());

		// int num Objects
		file.writeInt(node.getNumObjects());

		// Boolean leaf
		file.writeBoolean(node.isLeaf());

		// Write Tree Objects to file
		for (int i = 1; i <= node.getNumObjects(); i++) {
			if (node.getObjectAt(i) != null) {
				file.writeLong(node.getObjectAt(i).getKey());
				file.writeInt(node.getObjectAt(i).getFrequency());
			} else {
				file.skipBytes(12);
			}
		}

		// write all children pointers to file
		for (int i = 1; i <= node.getNumObjects()+1; i++) {
			if (node.getChildOffsetAt(i) > 0) {
				file.writeInt(node.getChildOffsetAt(i));
			} else {
				file.writeInt(-1);
			}
		}
	}

	private void WriteNodeToFile(BTreeNode node, int location) throws IOException {
		file.seek(location);

		// long Location
		file.writeInt((int) file.getFilePointer());

		// int num Objects
		file.writeInt(node.getNumObjects());

		// Boolean leaf
		file.writeBoolean(node.isLeaf());

		// Write Tree Objects to file
		for (int i = 1; i <= node.getNumObjects(); i++) {
			if (node.getObjectAt(i) != null) {
				file.writeLong(node.getObjectAt(i).getKey());
				file.writeInt(node.getObjectAt(i).getFrequency());
			} else {
				file.skipBytes(12);
			}
		}

		// write all children pointers to file
		for (int i = 1; i <= node.getNumObjects() + 1; i++) {
			if (node.getChildOffsetAt(i) > 0) {
				file.writeInt(node.getChildOffsetAt(i));
			} else {
				file.writeInt(-1);
			}
		}
	}

	// recursive add method to add to a BTree
	public void add(long data, int location) {

	}

	private int CalcNumTreeObjects(int deg) {
		return (2 * deg - 1);
	}

	public static BTreeNode GetNodeFromFile(int location) throws IOException {
		file.seek(location);

		int nodeOffset = file.readInt();
		int numObjects = file.readInt();
		boolean bleaf = file.readBoolean();

		BTreeNode bNode = new BTreeNode(nodeOffset, bleaf, degree);
		bNode.setNumObjects(numObjects);

		// read Tree Objects from file
		for (int i = 1; i <= bNode.getNumObjects(); i++) {
			bNode.setObjectAt(i, new TreeObject(file.readLong(), file.readInt()));
		}
		
		// read in the child pointers
		for (int i = 1; i <= bNode.getNumObjects()+1; i++) {
			bNode.setChildrenOffsetAt(i, file.readInt());
		}

		return bNode;
	}

	public void SplitChild(BTreeNode x, int indexToSplitOn) throws IOException {

		// 1
		BTreeNode z = new BTreeNode((int) file.getFilePointer(), true, degree);

		// 2
		BTreeNode y = GetNodeFromFile(x.getChildOffsetAt(indexToSplitOn));

		// 3
		z.setLeaf(y.isLeaf());

		// 4
		z.setNumObjects(degree - 1);

		// 5
		for (int j = 1; j <= degree - 1; j++) {
			// 6
			z.setObjectAt(j, y.getObjectAt(j + degree));
		}

		// 7
		if (!y.isLeaf()) {
			// 8
			for (int j = 1; j <= degree; j++) {
				// 9
				z.setChildrenOffsetAt(j, y.getChildOffsetAt(j + degree));
				y.setObjectAt(j+degree, null);
			}
		}

		// 10
		y.setNumObjects(degree - 1);

		// 11
		for (int j = x.getNumObjects() + 1; j >= indexToSplitOn + 1; j--) {
			// 12
			x.setChildrenOffsetAt(j + 1, x.getChildOffsetAt(j));
		}

		// 13
		x.setChildrenOffsetAt(indexToSplitOn + 1, z.getnodeOffset());

		// 14
		for (int j = x.getNumObjects(); j >= indexToSplitOn; j--) {
			// 15
			x.setObjectAt(j + 1, x.getObjectAt(j));
		}

		// 16
		x.setObjectAt(indexToSplitOn, y.getObjectAt(degree));

		// 17
		x.setNumObjects(x.getNumObjects() + 1);

		// 18
		WriteNodeToFile(y, y.getnodeOffset());

		// 19
		WriteNodeToFile(z,z.getnodeOffset());

		// 20
		if(!x.equals(root)) {
			WriteNodeToFile(x, x.getnodeOffset());
		}
	}

	public void insert(long k) throws IOException {
		// 1
		BTreeNode r = this.root;

		// 2
		if (r.getNumObjects() == (2 * degree - 1)) {

			// 3
			BTreeNode s = new BTreeNode(-1, true, degree);

			// 4
			this.root = s;

			// 5
			s.setLeaf(false);

			// 6
			s.setNumObjects(0);

			// 7
			r.setnodeOffset((int) file.getFilePointer());
			WriteNodeToFile(r);
			s.setChildrenOffsetAt(1, r.getnodeOffset());

			// 8
			SplitChild(s,1);

			// 9
			insertNonFull(s, k);
		} else {

			// 10
			insertNonFull(r, k);
		}
	}

	public void insertNonFull(BTreeNode x, long k) throws IOException {
		BTreeNode ch = null;

		// 1
		int i = x.getNumObjects();
		// 2
		if (x.isLeaf()) {
			// 3
			while (i >= 1 && k < x.getObjectAt(i).getKey()) {
				// 4
				x.setObjectAt(i + 1, x.getObjectAt(i));
				// 5
				i--;
			}

			// inc Freq
			if (i >= 1 && x.getObjectAt(i).getKey() == k) {
				x.getObjectAt(i).incrementFrequency();
				WriteNodeToFile(x, x.getnodeOffset());
				return;
			}

			// 6
			x.setObjectAt(i + 1, new TreeObject(k));

			// 7
			x.setNumObjects(x.getNumObjects() + 1);

			// 8
			if (!root.equals(x)) {
				WriteNodeToFile(x, x.getnodeOffset());
			}
			// 9

		} else {
			while (i >= 1 && k < x.getObjectAt(i).getKey()) {
				// 10
				i--;
			}
			// inc Freq
			if (x.getObjectAt(i).getKey() == k) {
				x.getObjectAt(i).incrementFrequency();
				if(!x.equals(root)) {
					WriteNodeToFile(x, x.getnodeOffset());
				}
				return;
			}
			// 11
			i++;
			// 12
			ch = GetNodeFromFile(x.getChildOffsetAt(i));
			// 13
			if (ch.getNumObjects() == 2 * degree - 1) {
				// 14
				SplitChild(x, i);
				if (k > ch.getNumObjects()) {
					// 16
					i = i + 1;
				}
			}
			// 17
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
		} else if (x.isLeaf()) {
			return null;
		} else {
			return search(GetNodeFromFile(x.getChildOffsetAt(i)), key);
		}
	}

	public static void main(String[] args) throws IOException {
		BTree tree = new BTree(3, "BTree");
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		tree.insert(5);
		tree.insert(6);
		tree.closeTree();
		PrintMetaData();
		PrintRootData();
		PrintNodeData(8);
		PrintNodeData(101);
	}
}