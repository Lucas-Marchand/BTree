import java.io.File;
import java.io.RandomAccessFile;

/*
 *  gbk file layout
 *  | BOF | int location of root | int number of keys | int size of each Key | int degree | beginning of root node | ... rest of nodes | EOF |
 *  
 *  BTreeNode layout
 *  | BOF | 
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
	
	public BTree(){
		try {
			file = new RandomAccessFile(gbk,"rwd");
			rootLocation = file.readInt();
			numTreeObjects = file.readInt();
			keySize = file.readInt();
			degree = file.readInt();
			file.seek(rootLocation);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public BTree(int degree){
		
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
