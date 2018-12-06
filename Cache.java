import java.util.NoSuchElementException;

/***
 * Interface for a simple memory cache ADT.  
 * @author CS 321
 *
 * @param <BTreeNode> - generic type of objects stored in cache
 */
public class Cache
{
	private DLLNode<BTreeNode> head;
	private DLLNode<BTreeNode> tail;
	int size;
	private int cacheSize;
	private int numHit, numRef;
	
	/**
	 * @constructor
	 * @param cachesize
	 */
	public Cache(int cachesize) {
		head = tail = null;
		size = 0;
		numHit = numRef = 0;
		this.cacheSize = cachesize;
	}
	
	
	/**
	 * Gets the data from cache and moves it to the front, if it's there.
	 * If not, add the data to cache and returns null reference. 
	 * @param target - object of type T
	 * @return object of type T, or null reference 
	 */
	public BTreeNode getObject(int targetOffset) {
		numRef++;
		BTreeNode target;
		//try {
			target = write(targetOffset);
		//} catch (NoSuchElementException e) {
		//	return null;
		//}
		numHit++;
		return target;
	}
	
	
	/***
	 * Clears contents of the cache,
	 * but doesn't change its capacity. 
	 */
	public void clearCache() {
		head = tail = null;
		size = 0;
	}
	
	
	/***
	 * Adds given data to front of cache. 
	 * Removes data in last position, if full.
	 * @param data - object of type T
	 */
	public void addObject(BTreeNode data) {
		DLLNode<BTreeNode> newNode = new DLLNode<BTreeNode>(data);
		if (tail != null) {
			tail.setNext(newNode);
			newNode.setPrevious(tail);
		} else {
			head = newNode;
		}
		tail = newNode;
		if(size >= cacheSize) {
			removeLast();
		}
		size++;
	}
	
	
	/***
	 * Removes data in last position in cache.
	 * @throws IllegalStateException - if cache is empty. 
	 */
	public void removeLast() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		if (size > 1) {
			head = head.getNext();
			head.setPrevious(null);
		} else {
			head = tail = null;
		}
		size--;
	}
	
	
	/**
	 * Removes the given target data from the cache.
	 * @throws NoSuchElementException - if target not found 
	 * @param target - object of type T 
	 */
	public void removeObject(BTreeNode target) {
		DLLNode<BTreeNode> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(target)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		if (size == 1) { //only node
			head = tail = null;
		} else if (targetNode == head) { //oldest node
			head = targetNode.getNext();
			head.setPrevious(null);
		} else if (targetNode == tail) { //newest node
			tail = targetNode.getPrevious();
			tail.setNext(null);
		} else { //somewhere in the middle
			targetNode.getPrevious().setNext(targetNode.getNext());
		}
		size--;
	}
	
	
	/**
	 * Moves data already in cache to the front. 
	 * @throws NoSuchElementException - if data not in cache   
	 * @param dataOffset - int
	 */
	public BTreeNode write(int dataOffset) {
		DLLNode<BTreeNode> targetNode = head;
		while (targetNode != null && targetNode.getElement().getnodeOffset() != dataOffset) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			return null;
		}
		if (size == 1 || targetNode == tail) { //only node or newest node
			
		} else if (targetNode == head) { //oldest node
			head = head.getNext();
			head.setPrevious(null);
			targetNode.setPrevious(tail);
			targetNode.setNext(null);
			tail.setNext(targetNode);
			tail = tail.getNext();
		} else { //somewhere in the middle
			targetNode.getPrevious().setNext(targetNode.getNext());
			targetNode.getNext().setPrevious(targetNode.getPrevious());
			targetNode.setPrevious(tail);
			targetNode.setNext(null);
			tail.setNext(targetNode);
			tail = tail.getNext();
		}
		return targetNode.getElement();
	}
		
	/**
	 * @return numHit
	 */
	public int getNumHit() {
		return numHit;
	}
	
	/**
	 * Get hit rate of the cache.
	 * @return double value 
	 */
	public double getHitRate() {
		return (double) numHit/numRef;	
	}
	
	/**
	 * @return numRef
	 */
	public int getNumRef() {
		return numRef;
	}
	
	/**
	 * Whether there's any data in cache. 
	 * @return boolean value 
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
}
