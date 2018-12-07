import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

public class GeneBankCreateBTree {
	
	static int seqLength;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		seqLength = Integer.parseInt(args[3]);
		boolean seq = false;
		int cachesize = 0;
		String leftover = "";
		boolean dump = false;
		int dumpInd = 0;

		File file = new File(args[2]);
		
		try {
			int degree = Integer.parseInt(args[1]);
			if (degree == 0) {
				degree = BTree.optimalDegree();
			}
			String bTreeFile = args[2] + ".btree.data." + seqLength + "." + degree;
			String bTreeDump = args[2] + ".btree.dump." + seqLength;
			BTree tree;
			
			if ((args[0]).equals("1")) {
				if (Integer.parseInt(args[4]) <= 0) {
					System.err.println("Cache size must be greater than 0");
					System.exit(1);
				}
				cachesize = Integer.parseInt(args[4]);
				dumpInd = 5;
			} else {
				dumpInd = 4;
			}
			
			if (args.length > dumpInd && args[dumpInd].equals("1")) {
				dump = true;
				tree = new BTree(degree, bTreeFile, bTreeDump, cachesize);
			} else {
				tree = new BTree(degree, bTreeFile, cachesize);
			}

			Scanner fileScan = new Scanner(file);			
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();	
				if ((line.length() > 1) && (line.substring(0, 2).equals("//"))) {
					seq = false;
					leftover = "";
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
			tree.closeTree();
			if (dump) {
				tree.DumpFile();
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("The gbk file does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String decodeLongValue(Long key) {
		String seq = "";
		String bin = Long.toBinaryString(key);
		String code = "";

		while (bin.length() < seqLength * 2) {
			bin = "0" + bin;
		}

		for (int i = 1; i <= seqLength; i++) {
			String sub = bin.substring(bin.length() - 2 * i, bin.length() - 2 * (i - 1));
			if (sub.equals("00")) {
				code = "a";
			} else {
				if (sub.equals("11")) {
					code = "t";
				} else {
					if (sub.equals("01")) {
						code = "c";
					} else {
						if (sub.equals("10")) {
							code = "g";
						}
					}
				}
			}
			seq = code + seq;
		}

		return seq;
	}

}
