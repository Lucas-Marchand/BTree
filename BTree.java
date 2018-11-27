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
	
	private int SplitNodeInFile(int location) {
		return 0;
		// TODO returns the integer of the root node of the two split nodes.
		
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
