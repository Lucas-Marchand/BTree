import java.util.ArrayList;

/**
 * @author Lucas, Lam, Tuan, Adel
 *
 */
public class BTreeNode {
	private int fileOffset;
	private int numObjects;
	private boolean leaf;
	private int numChildren;
	private int[] objects;
	private int[] childrenOffsets;
	
	public BTreeNode(int fileOffset,int numObjects,boolean leaf) {
		this.fileOffset = fileOffset;
		this.numObjects = numObjects;
		this.leaf = leaf;
		setNumChildren(0);
		objects = new int[numObjects];
		childrenOffsets = new int[numObjects+1];
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

	public int getObjectAt(int index) {
		return objects[index];
	}
	
	public int setObjectAt(int index, int element) {
		return objects[index] = element;
	}

	/**
	 * @param index, the obj to set
	 */
	public void add(int index, int element) {
		 objects[index] = element;
	}
	
	public boolean addData() {
		return false;
	}

	public int getChildrenOffset(int index) {
		return childrenOffsets[index];
	}

	public void setChildrenOffset(int index, int element) {
		this.childrenOffsets[index] = element;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}
}
