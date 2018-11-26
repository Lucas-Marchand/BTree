import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 *  gbk file Layout
 *  | BOF | int location of root | int number of keys | int size of each Key | int degree | beginning of root node | ... rest of nodes | EOF |
 *  
 *  BTreeNode Layout
 *  | BOF | Boolean leaf | int location | int numObject | int TreeObject #1 | int TreeObject #2 | int TreeObject #n | EOF |
 *  
 *  TreeObject Layout
 *  | BOF | int leftChild | int rightChild | int frequency | long key |
 *  
 *  
 */
public class BTree {
	int metaDataSize;
	int rootLocation;
	int numTreeObjects;
	int keySize;
	int degree;
	
	BTreeNode root;
	
	static File gbk;
	static RandomAccessFile file;
	
	public BTree(int degree){
		try {
			file = new RandomAccessFile(gbk,"rwd");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	// recursive add method to add to a BTree 
	public void add(long data,int location) {
		// create a node from file to start searching with
		BTreeNode temp = GetNodeFromFile(location);
		int numObjects = temp.getNumObjects();
		
		// iterate through all objects in the node
		for (int i = 0; i < numObjects; i++) {
			TreeObject to = GetTreeObjectFromFile(temp.getObjectAt(i));
			long key = to.getKey();
			// check if the Tree Object has the same value;
			if(data == key) {
				to.incrementFrequency();
				return;
			}else { // check if it is higher or lower.
				if(data < key) {
					if (temp.isFull()) {
						int newNode = SplitNodeInFile(location);
						add(data, newNode);
					}else {
						int tObject = CreateTreeObjectInFile();
						temp.add( location, tObject);
					}
				}
			}
		}
	}
	
	private int CreateTreeObjectInFile() {
		// TODO
		return 0;
	}
	
	private int CreateNodeInFile() {
		// TODO
		return 0;
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
