 package pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

import java.util.ConcurrentModificationException;

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import pqueue.exceptions.UnimplementedMethodException;

/**
 * <p>A {@link LinkedMinHeap} is a tree (specifically, a <b>complete</b> binary tree) where every node is
 * smaller than or equal to its descendants (as defined by the {@link Comparable#compareTo(Object)} overridings of the type T).
 * Percolation is employed when the root is deleted, and insertions guarantee maintenance of the heap property in logarithmic time. </p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a &quot;linked&quot;, <b>non-contiguous storage</b> implementation based on a
 * binary tree of nodes and references. Use the skeleton code we have provided to your advantage, but always remember
 * that the only functionality our tests can test is {@code public} functionality.</p>
 * 
 * @author --- BRANDON RUBIO ---
 *
 * @param <T> The {@link Comparable} type of object held by {@code this}.
 *
 * @see MinHeap
 * @see ArrayMinHeap
 */
public class LinkedMinHeap<T extends Comparable<T>> implements MinHeap<T> {

	/* ***********************************************************************
	 * An inner class representing a minheap's node. YOU *SHOULD* BUILD YOUR *
	 * IMPLEMENTATION ON TOP OF THIS CLASS!                                  *
 	 * ********************************************************************* */
	private class MinHeapNode {
		private T data;
		private MinHeapNode lChild, rChild;
		
        /* *******************************************************************
         * Write any further data elements or methods for MinHeapNode here...*
         ********************************************************************* */
		public MinHeapNode(T data) {
			this.data = data;
			this.lChild = null;
			this.rChild = null;
		}
		private int index;

	}

	/* *********************************
	  * Root of your tree: DO NOT ERASE!
	  * *********************************
	 */
	private MinHeapNode root;
	



    /* *********************************************************************************** *
     * Write any further private data elements or private methods for LinkedMinHeap here...*
     * *************************************************************************************/
	 private int size;
	 private int modCount;
	 
	 
	 //helper setter method used for Linear Priority Queue
	 public void setSize(int capacity) {
		 size = capacity;
	 }


    /* *********************************************************************************************************
     * Implement the following public methods. You should erase the throwings of UnimplementedMethodExceptions.*
     ***********************************************************************************************************/

	/**
	 * Default constructor.
	 */
	public LinkedMinHeap() {
		this.root = null;
		this.size = 0;
		this.modCount = 0;
	}

	/**
	 * Second constructor initializes {@code this} with the provided element.
	 *
	 * @param rootElement the data to create the root with.
	 */
	public LinkedMinHeap(T rootElement) {
		this.root = new MinHeapNode(rootElement);
		this.size = 1;
	}

	/**
	 * Copy constructor initializes {@code this} as a carbon
	 * copy of the parameter, which is of the general type {@link MinHeap}!
	 * Since {@link MinHeap} is an {@link Iterable} type, we can access all
	 * of its elements in proper order and insert them into {@code this}.
	 *
	 * @param other The {@link MinHeap} to copy the elements from.
	 * @throws EmptyHeapException 
	 */
	public LinkedMinHeap(MinHeap<T> other) throws EmptyHeapException {
		for(T element: other) {
			insert(element);
		}
		//root = copyNode((MinHeap<T>) other);
	}

	
	/*private MinHeapNode copyNode(MinHeap<T> minHeap) throws EmptyHeapException {
		if (minHeap == null) {
			throw new EmtpyHeapException("Root is null");
		}
		MinHeapNode newNode = new MinHeapNode();
		newNode.data = minHeap.getMin();
		//newNode.lChild = copyNode(minHeap.getMin().next());
		//newNode.rChild = copyNode(minHeap.rChild);
		
		return newNode;
	}*/

    /**
     * Standard {@code equals} method. We provide this for you. DO NOT EDIT!
     * You should notice how the existence of an {@link Iterator} for {@link MinHeap}
     * allows us to access the elements of the argument reference. This should give you ideas
     * for {@link #LinkedMinHeap(MinHeap)}.
     * @return {@code true} If the parameter {@code Object} and the current MinHeap
     * are identical Objects.
     *
     * @see Object#equals(Object)
     * @see #LinkedMinHeap(MinHeap)
     */
	/**
	 * Standard equals() method.
	 *
	 * @return {@code true} If the parameter Object and the current MinHeap
	 * are identical Objects.
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof MinHeap))
			return false;
		Iterator itThis = iterator();
		Iterator itOther = ((MinHeap) other).iterator();
		while (itThis.hasNext())
			if (!itThis.next().equals(itOther.next()))
				return false;
		return !itOther.hasNext();
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return getSize(root);
	}
	
	private int getSize(MinHeapNode node) {
		if(node == null) {
			return 0;
		}
		
		return 1 + getSize(node.lChild) + getSize(node.rChild);
	}


	@Override
	public void insert(T element) {
		MinHeapNode newNode = new MinHeapNode(element);
		//newNode.data = element;
		
		if(isEmpty()) {
			root = newNode;
			size++;
			return;
		}else {
			insert(root,element);
		}
		
		size++;
		modCount++;
	}
	
	private void insert(MinHeapNode node, T element) {
		if(node.lChild == null) {
			node.lChild = new MinHeapNode(element);
			//Percolate UP the heap!
			percolateUp(node.lChild);
		}else if(node.rChild == null) {
			node.rChild = new MinHeapNode(element);
			//Percolate UP the heap!
			percolateUp(node.rChild);
		}else {
			if(node.lChild != null && node.rChild != null) {
				if (countNodes(node.lChild) <= countNodes(node.rChild)) {
					insert(node.lChild, element);
				}else {
					insert(node.rChild,element);
				}
			}
		}
	}
	
	
	// a helper method to percolate up a node until it is in the right position
	private void percolateUp(MinHeapNode node) {
		if(node == root) {
			return;
		}
	    // get the parent node
	    MinHeapNode parent = getParent(root, node);
	    // compare the node with its parent
	    if (parent != null && node.data.compareTo(parent.data) < 0) {
	        // swap the node with its parent
	        T temp = node.data;
	        node.data = parent.data;
	        parent.data = temp;
	        // percolate up the parent
	        percolateUp(parent);
	    }
	}

	// a helper method to get the parent of a node
	private MinHeapNode getParent(MinHeapNode current, MinHeapNode node) {
	    // if the node is null or the root, return null
	    if (current == null || node == root) {
	        return null;
	    }
	    
	    if(current.lChild == node || current.rChild == node) {
	    	return current;
	    }
	    MinHeapNode leftParent = getParent(current.lChild, node);
	    if(leftParent != null) {
	    	return leftParent;
	    }
	    return getParent(current.rChild, node);
	    
	    // create a queue to perform a level-order traversal of the heap
	   /* Queue<MinHeapNode> queue = new LinkedList<>();
	    queue.add(root);
	    // find the parent of the node
	    while (!queue.isEmpty()) {
	        MinHeapNode parent = queue.remove();
	        if (parent.lChild == node || parent.rChild == node) {
	            // return the parent
	            return parent;
	        } else {
	            // add the children to the queue
	            if (parent.lChild != null) {
	                queue.add(parent.lChild);
	            }
	            if (parent.rChild != null) {
	                queue.add(parent.rChild);
	            }
	        }
	    }*/
	    // the node is not in the heap, return null
	    // return null
	}
	
	private int countNodes(MinHeapNode node) {
		if (node == null) {
			return 0;
		}
		return 1 + countNodes(node.lChild) + countNodes(node.rChild);
	}
	
	
	@Override
	public T getMin() throws EmptyHeapException {		// DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(isEmpty()) {
			throw new EmptyHeapException("Heap is Empty!");
		}
		
		return root.data;
	}

	@Override
	public T deleteMin() throws EmptyHeapException {    // DO *NOT* ERASE THE "THROWS" DECLARATION!
		if(isEmpty()) {
			throw new EmptyHeapException("Heap is Empty!");
		}else if(size == 1) {
			T min = root.data;
			this.root = null;
			modCount++;
			return min;
		}else {
		
		T min = root.data;
		MinHeapNode last = getLast(root);
		
		root.data = last.data;
		
		removeLast(last);
		
		percolateDown(root);
		
		size--;
		modCount++;
		
		return min;
		}
		
	}

	//helper method for grabbing last node in LinkedList
	private MinHeapNode getLast(MinHeapNode node) {
		if(node == null) {
			return null;
		}
		if(node.lChild == null && node.rChild == null) {
			return node;
		}
		MinHeapNode last = getLast(node.rChild);
		if(last == null) {
			last = getLast(node.lChild);
		}
		
		return last;
	}
	//helper method for removing last node in LinkedList
	private void removeLast(MinHeapNode last) {
		MinHeapNode parent = getParent(root, last);
		if(parent != null) {
			if(parent.lChild == last) {
				parent.lChild = null;
			}else {
				parent.rChild = null;
			}
		}
	}
	
	private void percolateDown(MinHeapNode node) {
		
		if(node == null) {
			return;
		}
		
		T min = node.data;
	    MinHeapNode minNode = node;

	    // Check left child
	    if (node.lChild != null && node.lChild.data.compareTo(min) < 0) {
	        min = node.lChild.data;
	        minNode = node.lChild;
	    }

	    // Check right child
	    if (node.rChild != null && node.rChild.data.compareTo(min) < 0) {
	        min = node.rChild.data;
	        minNode = node.rChild;
	    }

	    // If one of the children is smaller, swap with the smaller child and continue percolating down
	    if (minNode != node) {
	        T temp = node.data;
	        node.data = minNode.data;
	        minNode.data = temp;
	        percolateDown(minNode);
	    }
		/*MinHeapNode left = node.lChild;
		MinHeapNode right = node.rChild;
		
		MinHeapNode smaller = null;
		
		if(left != null && right != null) {
			if(left.data.compareTo(right.data) < 0) {
				smaller = left;
			}else {
				smaller = right;
			}
		}else if( left != null) {
			smaller = left;
		}else if(right != null) {
			smaller = right;
		}
		
		if(smaller != null && node.data.compareTo(smaller.data) > 0) {
			T temp = node.data;
			node.data = smaller.data;
			smaller.data = temp;
			
			percolateDown(smaller);
		}*/
	}
	
	@Override
	public Iterator<T> iterator() {
		return new HeapIterator();
		
	}
	
	private class HeapIterator implements Iterator<T> {
		private MinHeapNode nextNode;
	    private int expectedModCount;

	    public HeapIterator() {
	        this.nextNode = root;
	        this.expectedModCount = modCount;
	    }

	    @Override
	    public boolean hasNext() {
	        if (expectedModCount != modCount)
	            throw new ConcurrentModificationException("Heap structure modified during iteration");
	        return nextNode != null;
	    }

	    @Override
	    public T next() {
	        if (!hasNext())
	            throw new NoSuchElementException("No more elements in the heap");
	        T data = nextNode.data;
	        nextNode = findSuccessor(nextNode);
	        return data;
	    }

        private MinHeapNode findSuccessor(MinHeapNode node) {
        	if (node == null)
                return null;

            // Find the leftmost node in the right subtree
            MinHeapNode successor = node.rChild;
            while (successor != null && successor.lChild != null)
                successor = successor.lChild;

            // If the right subtree is not empty, return the leftmost node
            if (successor != null)
                return successor;

            // Otherwise, find the first ancestor whose left child is not the current node
            MinHeapNode parent = getParent(root, successor);
            while (parent != null && parent.lChild != node) {
                node = parent;
                parent = getParent(root, parent);
            }

            return parent;
        }
            /*if (node.rChild != null) {
                return findMinNode(node.rChild);
            }

            while (true) {
                MinHeapNode parent = getParent(root, node);
                if (parent == null || parent.lChild == node) {
                    return parent;
                }
                node = parent;
            }
        }*/
    }
	
	private void inOrder(MinHeapNode node, List<T> list) {
		//if the node is null, return
		if(node == null) {
			return;
		}
		
		inOrder(node.lChild, list);
		//add data of the node to the list
		list.add(node.data);
		//visit right child after visiting left
		inOrder(node.rChild, list);
	}

}
