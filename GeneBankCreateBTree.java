import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Tuan, Lam
 *
 */
public class GeneBankCreateBTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//TODO
		//Cache
		//Debug level
		
		int seqLength = Integer.parseInt(args[3]);
		boolean seq = false;
		String leftover = "";

		File file = new File(args[2]);
		try {
			
			int degree = Integer.parseInt(args[1]);
			
			BTree tree;
			if (degree == 0) {
				tree = new BTree();
			} else {
				tree = new BTree(degree);
			}
			
			Scanner fileScan = new Scanner(file);			
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();	
				if ((line.length() > 1) && (line.substring(0, 2).equals("//"))) {
					seq = false;
				}
				if (seq) {
					String seqLine = line.substring(line.lastIndexOf("1") + 2, line.length());
					seqLine = leftover + seqLine;
					seqLine = seqLine.replaceAll(" ","");
					int i = 0;
					while (i + seqLength <= seqLine.length()) {
						String subSeq = seqLine.substring(i, i + seqLength);
						String binNum = "";
						subSeq = subSeq.toLowerCase();
						if (!subSeq.contains("n")) {
							for (int j = 0; j < subSeq.length(); j++) {
								String bin = "";
								switch (subSeq.charAt(j)) {
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
							
							tree.insert(keyVal);
						}
						i++;
					}
					leftover = seqLine.substring(i, seqLine.length());
				}
				if ((line.length() > 5) && (line.substring(0, 6).equals("ORIGIN"))) {
					seq = true;
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("The gbk file does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
