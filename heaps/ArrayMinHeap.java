package pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import pqueue.exceptions.UnimplementedMethodException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import demos.GenericArrays;

import java.lang.Math;


/**
 * <p>{@link ArrayMinHeap} is a {@link MinHeap} implemented using an internal array. Since heaps are <b>complete</b>
 * binary trees, using contiguous storage to store them is an excellent idea, since with such storage we avoid
 * wasting bytes per {@code null} pointer in a linked implementation.</p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a <b>contiguous storage</b> implementation based on a linear {@link java.util.Collection}
 * like an {@link java.util.ArrayList} or a {@link java.util.Vector} (but *not* a {@link java.util.LinkedList} because it's *not*
 * contiguous storage!). or a raw Java array. We provide an array for you to start with, but if you prefer, you can switch it to a
 * {@link java.util.Collection} as mentioned above. </p>
 *
 * @author -- BRANDON RUBIO ---
 *
 * @see MinHeap
 * @see LinkedMinHeap
 * @see demos.GenericArrays
 */

public class ArrayMinHeap<T extends Comparable<T>> implements MinHeap<T> {

	/* *****************************************************************************************************************
	 * This array will store your data. You may replace it with a linear Collection if you wish, but
	 * consult this class' 	 * JavaDocs before you do so. We allow you this option because if you aren't
	 * careful, you can end up having ClassCastExceptions thrown at you if you work with a raw array of Objects.
	 * See, the type T that this class contains needs to be Comparable with other types T, but Objects are at the top
	 * of the class hierarchy; they can't be Comparable, Iterable, Clonable, Serializable, etc. See GenericArrays.java
	 * under the package demos* for more information.
	 * *****************************************************************************************************************/
	private Object[] data;

	/* *********************************************************************************** *
	 * Write any further private data elements or private methods for LinkedMinHeap here...*
	 * *************************************************************************************/
	private int size;
	private static int CAPACITY = 10;
	private int modCount;
	
	
	private void preserveCheck(Object[] data, int curr){
		int left = (2*curr) + 1;
		int right = (2*curr) +2;
		
		int parent = (int) Math.floor((curr-1)/2);
		
		//Now must check if invariant was preserved after insertion!!!
		if(curr != 0 && size > 1) {
			if( data[left] != null && ((T) data[curr]).compareTo((T) data[left]) > 0) {
				Object temp = data[left];
				data[left] = data[curr];
				data[curr] = temp;
				preserveCheck(data, left);
	    	}else if(data[right] != null &&((T) data[curr]).compareTo((T) data[right]) > 0) {
	    		Object temp = data[right];
	    		data[right] = data[curr];
	    		data[curr] = temp;
	    		preserveCheck(data, right);
	    	}else {
	    		//parent = ????;
	    		preserveCheck(data, parent);
	    	}
		}else {
			return;
		}
	}


	/* *********************************************************************************************************
	 * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
	 ***********************************************************************************************************/

	/**
	 * Default constructor initializes the data structure with some default
	 * capacity you can choose.
	 */
	public ArrayMinHeap(){
		this.data = new Object[CAPACITY];
		this.size = 0;
		this.modCount = 0;
	}

	/**
	 *  Second, non-default constructor which provides the element with which to initialize the heap's root.
	 *  @param rootElement the element to create the root with.
	 */
	public ArrayMinHeap(T rootElement){
		this();
		this.data[0] = rootElement;
	}

	/**
	 * Copy constructor initializes {@code this} as a carbon copy of the {@link MinHeap} parameter.
	 *
	 * @param other The MinHeap object to base construction of the current object on.
	 */
	public ArrayMinHeap(MinHeap<T> other){
		if(other.isEmpty()) {
			return;
		}
		
		data = new Object[other.size()];
		size = other.size();
		
		Iterator<T> iter = other.iterator();
		
		for(int i = 0; i < other.size(); i++) {
			data[i] = iter.next();
		}
	}

	/**
	 * Standard {@code equals()} method. We provide it for you: DO NOT ERASE! Consider its implementation when implementing
	 * {@link #ArrayMinHeap(MinHeap)}.
	 * @return {@code true} if the current object and the parameter object
	 * are equal, with the code providing the equality contract.
	 * @see #ArrayMinHeap(MinHeap)
	 */
	@Override
	public boolean equals(Object other){
		if(other == null || !(other instanceof MinHeap))
			return false;
		Iterator itThis = iterator();
		Iterator itOther = ((MinHeap) other).iterator();
		while(itThis.hasNext())
			if(!itThis.next().equals(itOther.next()))
				return false;
		return !itOther.hasNext();
	}


	@Override
	public void insert(T element) {
		
		//System.out.printf("Capacity: " + capacity);
		//System.out.printf("  Size: " + size);
		//check if array is full
		if(size == data.length) {
			//double the capacity of the array
			CAPACITY = CAPACITY*2;
			Object[] newData = new Object[data.length * 2];
			System.arraycopy(data, 0, newData, 0, data.length);
			data = newData;
		}
	
		//int insertedIndex = 0;
		/*for(int i = 0; i < size(); i++) {
		    if(data[size-1] == null) {
		    	//insert element at the end of array
		    	data[i] = element;
		    	//break loop
		    	i = size();
		    	insertedIndex =i;
		    }
		}*/
	    	//insert element at the next available position
	    data[size] = element;
	    percolateUp(size);
	    size++;
	    modCount++;
		//preserveCheck(data, insertedIndex);
	}

	private void percolateUp(int index) {
		while (index > 0) {
			int parent = (int) Math.floor((index-1)/2);
            //int parent = (index - 1) / 2;
            
            if (((T) data[index]).compareTo((T) data[parent]) < 0) {
            	Object temp = data[index];
                data[index] = data[parent];
                data[parent] = temp;
                index = parent;
            } else {
                break;
            }
        }
	}


	@Override
	public T deleteMin() throws EmptyHeapException { // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(isEmpty()) {
			throw new EmptyHeapException(null);
		}
		
		T min = getMin();
		//replace root with last element in the array
		data[0] = data[size-1];
		
		size--;
		percolateDown(0);
		modCount++;
		
		//return minimum
		return min;
		
		/*int curr = 0;
		int left = 2 *curr +1;
		int right = 2*curr + 2;
		
		//find smaller child
		while(left < size) {
			int smaller = left;
			if(right < size && ((T) data[right]).compareTo( (T) data[left]) < 0) {
				smaller = right;
			}
			
			//compare element with smaller child
			if(((T) data[curr]).compareTo((T) data[smaller]) > 0 ) {
				Object temp = data[curr];
				data[curr] = data[smaller];
				data[smaller] = temp;
				
				curr = smaller;
				left = 2 * curr +1;
				right = 2* curr +2;
			}else {
				break;
			}
			
		}*/
	}

	private void percolateDown(int index) {
		while(index < size) {
			int leftChild = (2 * index) + 1;
			int rightChild = (2 * index) + 2;
			int minimum = index;
			//check values of children of root
			if(leftChild < size && ((T) data[leftChild]).compareTo((T) data[minimum]) < 0){
				minimum = leftChild;
			}
			if(rightChild < size && ((T) data[rightChild]).compareTo((T) data[minimum]) < 0){
				minimum = rightChild;
			}
			//swap if root is not minimum
			if(minimum != index) {
				Object temp = data[index];
				data[index] = data[minimum];
				data[minimum] = temp;
				
				index = minimum;
			}else {
				break;
			}
		}
	}


		@Override
	public T getMin() throws EmptyHeapException {	// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(size() == 0) {
			throw new EmptyHeapException("size==0 was TRUE");
		}
		
		return (T) data[0];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Standard equals() method.
	 * @return {@code true} if the current object and the parameter object
	 * are equal, with the code providing the equality contract.
	 */


	@Override
	public Iterator<T> iterator() {
		return new HeapIterator();
		/*return new Iterator<T>() {
			private int index = 0;
			
			public boolean hasNext() {
				return index < size;
			}
			
			public T next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				
				return (T) data[index++];
			}
			
			
		};*/
	}
	
	private class HeapIterator implements Iterator<T> {
        private int marker;
        private int expectedModCount;

        public HeapIterator() {
            this.marker = 0;
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException("Heap structure modified during iteration");
            return marker < size;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException("No more elements in the heap");
            return (T) data[marker++];
        }
    }

}