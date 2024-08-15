package pqueue.priorityqueues; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORTS ARE HERE ONLY TO MAKE THE JAVADOC AND iterator() METHOD SIGNATURE
 * "SEE" THE RELEVANT CLASSES. SOME OF THOSE IMPORTS MIGHT *NOT* BE NEEDED BY YOUR OWN
 * IMPLEMENTATION, AND IT IS COMPLETELY FINE TO ERASE THEM. THE CHOICE IS YOURS.
 * ********************************************************************************** */

import demos.GenericArrays;
import pqueue.exceptions.*;
import pqueue.fifoqueues.FIFOQueue;
import pqueue.heaps.ArrayMinHeap;
import pqueue.heaps.EmptyHeapException;
import pqueue.heaps.LinkedMinHeap;

import java.util.*;
/**
 * <p>{@link LinearPriorityQueue} is a {@link PriorityQueue} implemented as a linear {@link java.util.Collection}
 * of common {@link FIFOQueue}s, where the {@link FIFOQueue}s themselves hold objects
 * with the same priority (in the order they were inserted).</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to
 * this class, your implementation <b>must</b>  use <b>whichever</b> linear {@link Collection} you want (e.g
 * {@link ArrayList}, {@link LinkedList}, {@link java.util.Queue}), or even the various {@link List} and {@link FIFOQueue}
 * implementations that we provide for you. You can also use <b>raw</b> arrays, but take a look at {@link GenericArrays}
 * if you intend to do so. Note that, unlike {@link ArrayMinHeap}, we do not insist that you use a contiguous storage
 * {@link Collection}, but any one available (including {@link LinkedList}) </p>
 *
 * @param <T> The type held by the container.
 *
 * @author  ---- BRANDON RUBIO ----
 *
 * @see MinHeapPriorityQueue
 * @see PriorityQueue
 * @see GenericArrays
 */
public class LinearPriorityQueue<T> implements PriorityQueue<T> {

	/* ***********************************************************************************
	 * Write any private data elements or private methods for LinearPriorityQueue here...*
	 * ***********************************************************************************/
	
	//private PriorityQueue<T> priorityQueue;
	//private LinkedList<LinkedList<T>> priorityQueue; 
	
	private ArrayList<pqNode> priorityQueue; 
	private int modCount;
	private int capacity;

	
	public class pqNode{
		T element;
		int priority;
		
		public pqNode(T element, int priority) {
			this.element = element;
			this.priority = priority;
		}
	}
	/* *********************************************************************************************************
	 * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/

	/**
	 * Default constructor initializes the element structure with
	 * a default capacity. This default capacity will be the default capacity of the
	 * underlying element structure that you will choose to use to implement this class.
	 */
	public LinearPriorityQueue(){
		this.priorityQueue = new ArrayList<>();
		this.capacity = 10;
	}

	/**
	 * Non-default constructor initializes the element structure with
	 * the provided capacity. This provided capacity will need to be passed to the default capacity
	 * of the underlying element structure that you will choose to use to implement this class.
	 * @see #LinearPriorityQueue()
	 * @param capacity The initial capacity to endow your inner implementation with.
	 * @throws InvalidCapacityException if the capacity provided is less than 1.
	 */
	public LinearPriorityQueue(int capacity) throws InvalidCapacityException{	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(capacity < 1) {
			throw new InvalidCapacityException("Capacity too small!");
		}else {
			priorityQueue = new ArrayList<>();
			this.capacity = capacity;
		}
	}

	@Override
	public void enqueue(T element, int priority) throws InvalidPriorityException{	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if (priority <= 0) {
            throw new InvalidPriorityException("Invalid priority level: " + priority);
        }else {
        	//increment modification counter
        	modCount++;
        	boolean added_or_not = false; 
        	//check if empty, if so add new node
        	if(priorityQueue.size() == 0) {
        		priorityQueue.add(new pqNode(element, priority));
        	} else {
        		// find a node with a lesser priority and insert before
        	    for (int index = 0; index < this.priorityQueue.size(); index++) {
        	    	pqNode curr = this.priorityQueue.get(index);
        	    	if(curr.priority > priority) {
        	    		priorityQueue.add(index, new pqNode(element, priority));
        	    		added_or_not = true;
        	    		return;
        	    	}
        	    }
        	    //add to end if not added previously
        	    if(added_or_not == false) {
        	    	priorityQueue.add(new pqNode(element, priority));
        	    }
        	    
        	}
        }
		
		/*if (priorityQueue.isEmpty()) {
	        priorityQueue.add(new LinkedList<>());
	    }

        priorityQueue.get(priority).add(element);*/
		
        /*if (priority < priorityQueue.size()) {
            priorityQueue =  new LinkedMinHeap();
           priorityQueue.enqueue(element, priority);
        
        
            priorityQueue.insert((Comparable) element);
        }*/
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException { 	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(this.priorityQueue.size() == 0) {
			throw new EmptyPriorityQueueException("Empty Queue!");
		}else {
			// increment modification count
			modCount++;
			//select minimum, remove, and return
			T min = priorityQueue.get(0).element;
			priorityQueue.remove(0);
			return min;
		}
		
		
		/*for(LinkedList<T> list : priorityQueue) {
			if(!list.isEmpty()) {
				return list.remove();
			}
		}
		
		return null;*/
		
		/*try {
			return (T) priorityQueue.deleteMin();
		} catch (EmptyHeapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
        
		//return priorityQueue.dequeue();*/
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(this.priorityQueue.size() == 0) {
			throw new EmptyPriorityQueueException("Empty Queue!");
		}
		// return first element
		return this.priorityQueue.get(0).element;
		/*for(LinkedList<T> list : priorityQueue) {
			if(!list.isEmpty()) {
				return list.getFirst();
			}
		}
		return null;*/
		
	}

	@Override
	public int size() {
		return priorityQueue.size();
	}

	@Override
	public boolean isEmpty() {
		return priorityQueue.size() == 0;
	}


	@Override
	public Iterator<T> iterator() {
		/*LinkedList<T> allElements = new LinkedList<T>();
		for(LinkedList<T> sublists : priorityQueue) {
			allElements.addAll(sublists);
		}
		return allElements.iterator();*/
		return new linPqIter();
	}
	
	private class linPqIter implements Iterator<T>{
		private ArrayList<pqNode> curr = priorityQueue;
		private int index = 0;
		private int currModCount = modCount;
		@Override
		public boolean hasNext() {
			return (index < curr.size());
		}
		@Override
		public T next() {
			if(currModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			T next_val = curr.get(index).element;
			index++;
			return next_val; 
		}
		
	}

}