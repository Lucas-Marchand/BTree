import java.util.Arrays;

/**
 * @author Lucas, Lam, Tuan
 *
 */
public class BTreeNode {
	private int nodeOffset;
	private int numObjects;
	private boolean leaf;
	private TreeObject[] objects;
	private int[] childrenOffsets;
	private static int nodeSize;
	
	/**
	 * 
	 * @param nodeOffset
	 * @param numObjects
	 * @param leaf
	 * @param degree
	 */
	public BTreeNode(int nodeOffset, Boolean leaf, int degree) {
		this.nodeOffset = nodeOffset;
		this.numObjects = 0;
		this.leaf = leaf;
		objects = new TreeObject[2*degree];
		childrenOffsets = new int[2*degree+1];
		Arrays.fill(childrenOffsets, -1);
		CalcNodeSize(degree);
	}
	
	private void CalcNodeSize(int degree) {
		int Pointer = 4;
		int Object = 12;
		int Metadata = 12;
		this.nodeSize = ((Object * 2 * degree) + ((Pointer * 2 * degree) + 1) + Metadata);
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
	
	public TreeObject[] getObjects() {
		return objects;
	}
	
	public int[] getChildren() {
		return childrenOffsets;
	}

	/**
	 * @return the object[index]
	 */
	public TreeObject getObjectAt(int index) {
		return objects[index];
	}

	/**
	 * @param index, the obj to set
	 */
	public void setObjectAt(int index, TreeObject obj) {
		 objects[index] = obj;
	}
	
	/**
	 * @return the offsetOfChild
	 */
	public int getChildOffsetAt(int index) {
		return childrenOffsets[index];
	}

	/**
	 * @param index, the obj to set
	 */
	public void setChildrenOffsetAt(int index, int offset) {
		 childrenOffsets[index] = offset;
	}
	
	public String toString() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("Node Offset: " + this.nodeOffset);
		System.out.println("# of Objects: " + numObjects);
		System.out.println("Leaf: " + leaf);
		
		StringBuilder string = new StringBuilder();
		for(TreeObject to : objects) {
			if(to != null) {
				string.append("[" + to.getKey() + " : " + to.getFrequency() + "], ");
			}else {
				string.append("null, ");
			}
		}
		string.deleteCharAt(string.length()-1);
		
		System.out.println("Tree Objects: " + string);
		
		string = new StringBuilder();
		for(Integer child : childrenOffsets) {
			if(child != null) {
				string.append(child.toString() + ", ");
			}else {
				string.append("null, ");
			}
		}
		string.deleteCharAt(string.length()-1);
		
		System.out.println("Children Pointers: " + string);
		
		System.out.println("Size of Object: " + nodeSize);
		System.out.println("------------------------------------------------------------------");
		System.out.println("");
		
		return null;
	}

	public static int getNodeSize() {
		return nodeSize;
	}

	public static void setNodeSize(int nodeSize) {
		BTreeNode.nodeSize = nodeSize;
	}
}