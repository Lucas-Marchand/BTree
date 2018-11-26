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
	
	public void add(long data) {
		// create a node from file to start searching with
		BTreeNode temp = GetNodeFromFile(rootLocation);
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
					if(i+1 < numObjects) {
						temp.getObjectAt(i+1);
					}else {
						
					}
				}
			}
		}
	}
	
	private TreeObject CreateTreeObject(long data, int frequency) {
		// TODO
		return null;
	}
	
	private BTreeNode GetNodeFromFile(int location) {
		// TODO
		return null;
	}
	
	private TreeObject GetTreeObjectFromFile(int location) {
		// TODO
		return null;
	}
	
	private BTreeNode SplitNode() {
		return null;
		// TODO Auto-generated method stub
		
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
