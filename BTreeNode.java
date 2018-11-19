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
	 * @return the data
	 */
	public long getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(long data) {
		this.data = data;
	}
}
