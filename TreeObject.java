
public class TreeObject {
	long data;
	int leftChild;
	int rightChild;
	int frequency;
	
	public TreeObject(long data, int leftChild, int rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		frequency = 0;
	}
	
	public void incrementFrequency() {
		++frequency;
	}
	
	public int getFrequency() {
		return frequency;
	}
}
