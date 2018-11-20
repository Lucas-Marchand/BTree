import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/*
 *  gbk file layout
 *  BOF| int location of root | root node |  |EOF
 *  
 *  
 */
public class BTree {
	int metaDataSize = 10;
	LinkedList<TreeObject> data;
	static File gbk;
	static RandomAccessFile file;
	
	public BTree(){
		try {
			file = new RandomAccessFile(gbk,"rwd");
			
		} catch (FileNotFoundException e) {
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
