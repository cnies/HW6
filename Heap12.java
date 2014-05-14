import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
/** Heap12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  Heap12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A Heap12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a Heap12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the Heap12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a Heap12 instance concurrently if any of the 
 * threads modifies the Heap12. 
 */
public class Heap12<E extends Comparable <? super E>> extends 
AbstractQueue<E> 
{

	private ArrayList<E> theHeap; /*The backbone storage of the Heap*/
	private boolean isMax; /*False if Min-Heap, True if Max-Heap*/
	private int size;/*Number of elements in the heap*/
	private int capacity;/*Index of the underlying arrayList object*/


	/** O-argument constructor. Creates and empty Heap12 with unspecified
	 *  initial capacity, and is a min-heap 
	 */ 
	public Heap12()
	{
		this(5, false);
	}

	/** 
	 * Constructor to build a min or max heap
	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
	 */ 
	public Heap12( boolean isMaxHeap)
	{
		this(5, isMaxHeap);
	}

	/** 
	 * Constructor to build a with specified initial capacity 
	 *  min or max heap
	 * @param capacity  	initial capacity of the heap.	
	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
	 */ 
	public Heap12( int capacity, boolean isMaxHeap)
	{
		this.theHeap = new ArrayList<E>(capacity);
		this.size = 0;
		this.isMax = isMaxHeap;
		this.capacity = capacity;
	}
	/** Copy constructor. Creates Heap12 with a deep copy of the argument
	 * @param toCopy      the heap that should be copied
	 */
	public Heap12 (Heap12<E> toCopy)
	{
		this.theHeap = new ArrayList<E>(toCopy.capacity);
		this.size = toCopy.size;
		this.capacity = toCopy.capacity;
		this.isMax = toCopy.isMax;
		//Make the copy deep by copying the references
		for (int i = 0; i<toCopy.size; i++){
			this.theHeap.add(i, toCopy.theHeap.get(i));
		}
	}

	/* The following are defined "stub" methods that provide degenerate
	 * implementations of methods defined as abstract in parent classes.
	 * These need to be coded properly for Heap12 to function correctly
	 */

	/** Size of the heap
	 * @return the number of elements stored in the heap
	 */
	public int size()
	{
		return size; 
	}

	/** 
	 * @return an Iterator for the heap 
	 */
	public Iterator<E> iterator()
	{
		return new Heap12Iterator(); 
	}

	/** peek - see AbstractQueue for details 
	 * 
	 * @return Element at top of heap. Do not remove 
	 */
	public E peek()
	{
		if (size()>0)
			return theHeap.get(0);
		else
			return null;
	}
	/** poll - see AbstractQueue for details 
	 * @return Element at top of heap. And remove it from the heap. 
	 * 	return <tt>null</tt> if the heap is empty
	 */
	public E poll()
	{
		//If the list is empty, return null
		if (size() == 0)
			return null;
		//Get the top of the heap, switch it with the bottom, and trickleDown
		E oldTop = this.theHeap.get(0);
		this.theHeap.set(0, theHeap.get(size -1));
		this.theHeap.set(size-1, null);
		size--;
		trickleDown(0);
		//I would get SO many errors if I didn't do this, I don't know exactly why
		if (size == 0)
			theHeap.clear();
		return oldTop;
	}
	/** offer -- see AbstractQueue for details
	 * insert an element in the heap
	 * @return true
	 * @throws NullPointerException 
	 * 	if the specified element is null	
	 */
	public boolean offer (E e)
	{
		//Throw proper exception if the parameter is null
		if (e == null)
			throw new NullPointerException();
		//Doubles ArrayList capacity if the list is full
		if (this.size == this.capacity){
			ArrayList<E> doubled = new ArrayList<E>(this.capacity*2);
			for (int i = 0; i<this.capacity; i++){
				doubled.add(i, this.theHeap.get(i));
			}
			this.theHeap = doubled;
			this.capacity = this.capacity*2;
		}
		//Add the new element at the end, then bubbleUp
		this.theHeap.add(e);
		size++;
		bubbleUp(this.theHeap.size()-1);
		return true;
	}

	/* ------ Private Helper Methods ----
	 *  DEFINE YOUR HELPER METHODS HERE
	 */

	//Basic checks, quite helpful
	private int rightChild(int index){
		return index*2+2;
	}
	private int leftChild(int index){
		return index*2+1;
	}
	private boolean hasLeftChild(int index){
		return leftChild(index) <= size()-1;
	}
	private boolean hasRightChild(int index){
		return rightChild(index) <= size()-1;
	}
	private boolean isLeaf(int index){
		return !hasLeftChild(index);
	}
	private int getParent(int index){
		return (index-1)/2;
	}
	private boolean hasParent(int index){
		return index>0;
	}
	//Swaps two elements in an array
	private void swap(int i1, int i2){
		E temp = theHeap.get(i1);
		theHeap.set(i1, theHeap.get(i2));
		theHeap.set(i2, temp);
	}
	//Finds and returns the child to swap wtih
	//(Smallest if !isMax, largest if isMax)
	//NOTE: Obsolete, didn't really need this code
	private int childToBeSwapped(int parent){
		if (!hasRightChild(parent)||theHeap.get(rightChild(parent))==null)
			return leftChild(parent);
		else{
			E left = theHeap.get(leftChild(parent));
			E right = theHeap.get(rightChild(parent));
			if (left.compareTo(right)*(isMax ? 1:-1)>0)
				return leftChild(parent);
			else
				return rightChild(parent);
		}
	}
	//Returns true if the object at index 1 should be swapped. Very helpful
	//in the trickleDown and bubbleUp methods, because it takes into account
	//whether or not the heap is a Min-Heap or a Max-Heap. Essentially it 
	//returns whether or not the two objects at the input elements should
	//be swapped given the type of Heap it belongs in.
	//PRECONDITION: index1 > index2, ie index 1 is lower down the tree
	private boolean shouldBeSwapped(int index1, int index2){
		if (index1>=size() || index1<0 || index2>=size() ||
				index2<0)
			return false;
		E object1 = theHeap.get(index1);
		E object2 = theHeap.get(index2);
		//Causes issues if one of them is null
		if (object1 == null || object2 == null)
			return false;
		//This line is key, because it takes into account whether or not
		//this heap is a Min-Heap or Max-Heap using the Ternary operator.
		return 0<object1.compareTo(object2)*(isMax ? 1:-1);
	}

	//Bubble up (iterative version)
	private void bubbleUp(int index){
		//While the element at index should be swapped with it's parent, if it has one
		while (hasParent(index) && shouldBeSwapped(index, getParent(index))){
			//Swap with parent, move the index further up the tree
			swap(index, getParent(index));
			index = getParent(index);
		}
	}

	private void trickleDown(int index){
		//While the element at index has children, and while
		//it needs to be switched with either of them
		while (hasLeftChild(index)&&
				(shouldBeSwapped(rightChild(index), index) ||
				 shouldBeSwapped(leftChild(index),index))){
			//If it has a right child
			if (hasRightChild(index)&&theHeap.get(rightChild(index))!= null){
				//If left should be swapped with right, then that means the top
				//should be swapped with left (because it is the smallest in a MinHeap,
				//or the largest in a Max Heap)
				if ((shouldBeSwapped(leftChild(index), rightChild(index)))) {
					swap(leftChild(index), index);
					index = leftChild(index);
				}
				//Otherwise, return the right child
				else{
					swap(rightChild(index), index);
					index = rightChild(index);
				}
			}
			//If right child does not exist, return the left child
			else{
				swap(leftChild(index), index);
				index = leftChild(index);
			}
		}
	}

	//Just used for testing
	public String toString(){
		String toReturn = "\n";
		for(E e: theHeap)
			toReturn+=""+e+" ";
		return toReturn;
	}

	//Just used for testing
	private static void testChild(Heap12 heap, int i){
		System.out.println("Child to be swapped: "+heap.childToBeSwapped(i));
		System.out.println("It will be swapped: "+ 
				heap.shouldBeSwapped(heap.childToBeSwapped(i), i));
	}

	//Simple test I wrote to make sure it is a proper heap
	private boolean isHeap(){
		for (int i = 0; i<theHeap.size(); i++){
			E toCompare = theHeap.get(i);
			if ( (hasLeftChild(i) && theHeap.get(leftChild(i))!=null &&
				toCompare.compareTo(theHeap.get(leftChild(i)))*(isMax ? 1:-1)<0)
					||  (hasRightChild(i) && theHeap.get(rightChild(i))!=null &&
					toCompare.compareTo(theHeap.get(rightChild(i)))*(isMax ? 1:-1)<0))
				return false;
		}
		return true;
	}


	//Just used for testing
	public static void main(String[] args){
		java.util.Random gen = new java.util.Random();
		Heap12<Integer> theHeap = new Heap12<Integer>(false);
		for (int i = 1; i<=30; i++){
			theHeap.offer(new Integer(gen.nextInt(30)));
		}
		Iterator iter = theHeap.iterator();
		for (int i = 0; i<15; i++)
			iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		System.out.println(theHeap.isHeap());
	}

	/** Inner Class for an Iterator **/
	/* stub routines */

	private class Heap12Iterator implements Iterator<E>
	{
		private int index;
		private boolean canRemove;

		private Heap12Iterator()
		{
			index = size()-1;
			canRemove = false;
		}

		/* hasNext() to implement the Iterator<E> interface */
		public boolean hasNext()
		{
			if (index == -1)
				return false;
			else
				return true;
		}

		/* next() to implement the Iterator<E> interface */
		public E next() throws NoSuchElementException
		{ 
			if (!hasNext()){
				throw new NoSuchElementException();
			}

			else{
				E toReturn = theHeap.get(this.index);
				this.index--;
				canRemove = true;
				return toReturn;
			}
		}

		/* remove() to implement the Iterator<E> interface */
		public void remove() throws IllegalStateException
		{
			if (!canRemove)
				throw new IllegalStateException();
			else{
				int removedIndex = this.index+1;
				swap(removedIndex, theHeap.size()-1);
				theHeap.set(theHeap.size()-1, null);
				size--;
				trickleDown(removedIndex);
				canRemove = false;
			}
		}	
	}
} 
// vim:ts=4:sw=4:tw=78:et
