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
	}

	/**
	 * @return the nodeOffset
	 */
	public int getnodeOffset() {
		return nodeOffset;
	}

	/**
	 * @param nodeOffset the nodeOffset to set
	 */
	public void setnodeOffset(int nodeOffset) {
		this.nodeOffset = nodeOffset;
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
	 */
	public int getChildOffsetAt(int index) {
		return childrenOffsets[index];
	}
	
	public boolean addData() {
		return false;
	}

	/**
	 * @param index, the obj to set
	 */
	public void setChildrenOffsetAt(int index, int offset) {
		 childrenOffsets[index] = offset;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
}
