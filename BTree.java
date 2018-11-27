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
	int rootLocation = 8;
	int numTreeObjects;
	int keySize;
	int degree;
	
	BTreeNode root;
	
	static RandomAccessFile file;
	
	public BTree(int degree){
		numTreeObjects = CalcNumTreeObjects(this.degree);
		BTreeNode root = new BTreeNode(rootLocation, numTreeObjects, true);
		try {
			file = new RandomAccessFile("BTreeFile", "rwd");
			WriteMetaData();
			WriteNodeToFile(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public BTree() {
		numTreeObjects = CalcNumTreeObjects(optimalDegree());
		BTreeNode root = new BTreeNode(rootLocation, numTreeObjects, true);
		try {
			file = new RandomAccessFile("BTreeFile", "rwd");
			WriteMetaData();
			WriteNodeToFile(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void WriteMetaData() throws IOException {
		file.writeInt(rootLocation);
		file.writeInt(degree);
	}

	private void WriteNodeToFile(BTreeNode node) throws IOException {
		file.writeInt(node.getFileOffset());
		file.writeInt(node.getNumObjects());
		file.writeBoolean(node.isLeaf());
	}
	
	private int WriteTreeObjectToFile() {
		// TODO
		return 0;
	}
	
	// recursive add method to add to a BTree 
	public void add(long data,int location) {
		
	}
	
	private int CalcNumTreeObjects(int deg) {
		return (2*deg-1);
	}
	
	private BTreeNode GetNodeFromFile(int location) {
		// TODO
		return null;
	}
	
	private TreeObject GetTreeObjectFromFile(int location) {
		// TODO
		return null;
	}
	
	private void SplitChild(int parentOfSplittingNode, int indexToSplitOn) throws IOException {
		
		
		BTreeNode x = GetNodeFromFile(parentOfSplittingNode);
		
		// 1
		BTreeNode z = new BTreeNode((int) file.getFilePointer(), numTreeObjects, true);
		
		// 2
		BTreeNode y = GetNodeFromFile(z.getChildrenOffset(indexToSplitOn));
		
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
				z.setChildrenOffset(j, y.getChildrenOffset(j+degree));
			}
		}
		
		// 10
		y.setNumObjects(degree-1);
		
		// 11
		for(int j = x.getNumObjects()+1; j > indexToSplitOn+1; j--) {
			// 12
			x.setChildrenOffset(j+1, x.getChildrenOffset(j));
		}
		
		// 13
		x.setChildrenOffset( indexToSplitOn + 1, z.getFileOffset());
		
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

	private int optimalDegree() {
		return 0;
	}
	
	public static void main(String[] args) {
		try {
			File f = new File("test");
			file = new RandomAccessFile(f, "rwd");
			file.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
