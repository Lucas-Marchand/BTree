<<<<<<< HEAD
import java.util.ArrayList;

/**
 * @author Lucas, Lam, Tuan
 *
 */
public class BTreeNode {
	private int nodeOffset;
	private int numObjects;
	private boolean leaf;
	private boolean full;
	private ArrayList<Integer> objects;
	private int[] childrenOffsets;
	
	public BTreeNode(int nodeOffset,int numObjects, int degree) {
		this.setFull(false);
		this.nodeOffset = nodeOffset;
		this.numObjects = numObjects;
		leaf = true;
		objects = new ArrayList<Integer>();
		childrenOffsets = new int[2*degree];
=======
public class BTreeNode {
	int fileOffset;
	int numObjects;
	boolean leaf;
	long data;
	
	public BTreeNode(int fileOffset,int numObjects,boolean leaf, long data) {
		this.fileOffset = fileOffset;
		this.numObjects = numObjects;
		this.leaf = leaf;
		this.data = data;
>>>>>>> parent of 56845d4... Add files via upload
	}

	/**
	 * @return the fileOffset
	 */
	public int getFileOffset() {
		return fileOffset;
	}

	/**
	 * @param fileOffset the fileOffset to set
	 */
	public void setFileOffset(int fileOffset) {
		this.fileOffset = fileOffset;
	}

	/**
	 * @return the numObjects
	 */
	public int getNumObjects() {
		return numObjects;
	}

	/**
	 * @param numObjects the numObjects to set
	 */
	public void setNumObjects(int numObjects) {
		this.numObjects = numObjects;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
<<<<<<< HEAD
	 * @return the object[index]
	 */
	public int getObjectAt(int index) {
		return objects.get(index);
	}

	/**
	 * @param index, the obj to set
	 */
	public void add(int index, int element) {
		 objects.set(index, element);
	}
	
	/**
	 * @return the offsetOfChild
=======
	 * @return the data
>>>>>>> parent of 56845d4... Add files via upload
	 */
	public long getData() {
		return data;
	}
	
	public boolean addData() {
		return false;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(long data) {
		this.data = data;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
}
