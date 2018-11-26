/**
 * @author Lucas, Lam, Tuan
 *
 */
public class TreeObject {
	private long key;
	private int frequency;
	private int leftChild;
	private int rightChild;
	
	public TreeObject(long key) {
		this.key = key;
		this.frequency = 1;
		this.setLeftChild(-1);
		this.setRightChild(-1);
	}
	
	public void incrementFrequency() {
		frequency++;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public long getKey() {
		return key;
	}
	
	public boolean hasLeftChild() {
		return leftChild >= 0;
	}
	
	public boolean hasRightChild() {
		return rightChild >= 0;
	}

	public int getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(int leftChild) {
		this.leftChild = leftChild;
	}

	public int getRightChild() {
		return rightChild;
	}

	public void setRightChild(int rightChild) {
		this.rightChild = rightChild;
	}
}
