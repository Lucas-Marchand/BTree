import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * @author Lam, Tuan
 *
 */
public class GeneBankSearch {

	public static void main(String[] args) throws IOException {
		File file = new File(args[2]);
		try {
			RandomAccessFile bTreeFile = new RandomAccessFile(args[1], "rw");
			bTreeFile.seek(0);
			int rootOffset = bTreeFile.readInt(); //get the root offset, check later
			bTreeFile.seek(rootOffset);
			
			//GET THE ROOT INFO
			//BTreeNode root = new BTreeNode(...);
			
			Scanner fileScan = new Scanner(file);			
			while (fileScan.hasNextLine()) {
				String query = fileScan.nextLine();
				
				String binNum = "";
				query = query.toLowerCase();
				for (int j = 0; j < query.length(); j++) {
					String bin = "";
					
					switch (query.charAt(j)) {
					case 'a':
						bin = "00";
						break;
					case 't':
						bin = "11";
						break;
					case 'c':
						bin = "01";
						break;
					case 'g':
						bin = "10";
						break;
					}
					binNum = binNum + bin;
				}
				long keyVal = Long.parseLong(binNum, 2);
				
//				TreeObject retVal = BTreeSearch(root, keyVal);
//				if (retVal != null) {
//					System.out.println(query + ": " + retVal.getFrequency());
//				} else {
//					System.out.println(query + ": 0");
//				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("The gbk file does not exist.");
		}
	}

	public TreeObject BTreeSearch(BTreeNode x, int key) {
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
			//DISK-READ(x,ci); new node child of x
			//return BTreeSearch(x.ci, key);
		}
		return null; //delete this
	}
}
