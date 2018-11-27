
public class TreeObject {
	private long key;
	private int frequency;
	
	public TreeObject(long key) {
		this.key = key;
		this.frequency = 1;
	}
	
	public void incrementFrequency() {
		++frequency;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public long getKey() {
		return key;
	}
}
