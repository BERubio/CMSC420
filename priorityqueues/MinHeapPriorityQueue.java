package pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******


/* *****************************************************************************************
 * THE FOLLOWING IMPORTS WILL BE NEEDED BY YOUR CODE, BECAUSE WE REQUIRE THAT YOU USE
 * ANY ONE OF YOUR EXISTING MINHEAP IMPLEMENTATIONS TO IMPLEMENT THIS CLASS. TO ACCESS
 * YOUR MINHEAP'S METHODS YOU NEED THEIR SIGNATURES, WHICH ARE DECLARED IN THE MINHEAP
 * INTERFACE. ALSO, SINCE THE PRIORITYQUEUE INTERFACE THAT YOU EXTEND IS ITERABLE, THE IMPORT OF ITERATOR
 * IS NEEDED IN ORDER TO MAKE YOUR CODE COMPILABLE. THE IMPLEMENTATIONS OF CHECKED EXCEPTIONS
 * ARE ALSO MADE VISIBLE BY VIRTUE OF THESE IMPORTS.
 ** ********************************************************************************* */

import pqueue.exceptions.*;
import pqueue.heaps.ArrayMinHeap;
import pqueue.heaps.EmptyHeapException;
import pqueue.heaps.MinHeap;

import java.util.Iterator;
import java.util.Queue;
import java.util.Comparator;
/**
 * <p>{@link MinHeapPriorityQueue} is a {@link PriorityQueue} implemented using a {@link MinHeap}.</p>
 *
 * <p>You  <b>must</b> implement the methods of this class! To receive <b>any credit</b> for the unit tests
 * related to this class, your implementation <b>must</b> use <b>whichever</b> {@link MinHeap} implementation
 * among the two that you should have implemented you choose!</p>
 *
 * @author  ---- BRANDON RUBIO ----
 *
 * @param <T> The Type held by the container.
 *
 * @see LinearPriorityQueue
 * @see MinHeap
 * @see PriorityQueue
 */
public class MinHeapPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T>{

	/* ***********************************************************************************
	 * Write any private data elements or private methods for MinHeapPriorityQueue here...*
	 * ***********************************************************************************/
	
	/*public class MyComparator implements Comparator<Integer> {
	    public int compare(Integer x, Integer y) {
	        return y - x;
	    }
	}*/
		
	//Queue<T> minHeapPriorityQueue;
	//private MinHeap<T> heap;
	//private ArrayMinHeap<T> heap;
	
	private ArrayMinHeap<Entry<T>> minHeap;
	private int counter;

	private class Entry<T extends Comparable<T>> implements Comparable<Entry<T>> {
		private T element;
		private int priority;
		private int counter;
		
		Entry(T element, int priority, int counter) {
			this.element = element;
			this.priority = priority;
			this.counter = 0;
		}

		@Override
		public int compareTo(Entry<T> other) {
			if(this.priority != other.priority) {
				return Integer.compare(this.priority, other.priority);
			}else {
				return Integer.compare(this.counter, other.counter);
			}
		}
	}
	/* *********************************************************************************************************
	 * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/
		/**
	 * Simple default constructor.
	 */
	public MinHeapPriorityQueue(){
		this.minHeap = new ArrayMinHeap<>();
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(priority <= 0) {
			throw new InvalidPriorityException("Priority must be positive integer");
		}
		
		Entry<T> entry = new Entry<>(element, priority, counter++);
		minHeap.insert(entry);
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException {		// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(this.isEmpty()) {
			throw new EmptyPriorityQueueException("Queue is empty");
		}
		
		try {
			Entry<T> entry = minHeap.deleteMin();
			return entry.element;
		} catch (EmptyHeapException e) {
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(this.isEmpty()) {
			throw new EmptyPriorityQueueException("Queue is emtpy!");
		}
		
		try {
			Entry<T> entry = minHeap.getMin();
			return entry.element;
		} catch (EmptyHeapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int size() {
		return minHeap.size();
	}

	@Override
	public boolean isEmpty() {
		return minHeap.isEmpty();
	}


	@Override
	public Iterator<T> iterator() {
		return (Iterator<T>) minHeap.iterator();
	}

}
