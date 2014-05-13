import junit.framework.TestCase;
import java.util.*;

public class Heap12Tester extends TestCase {
	private AbstractQueue<Integer> emptyMax;
	private AbstractQueue<Integer> emptyMin;
	private AbstractQueue<Integer> rootOnlyMax;
	private AbstractQueue<Integer> rootOnlyMin;
	private AbstractQueue<Integer> sevenObjectMin;
	private AbstractQueue<Integer> sevenObjectMax;

	protected void setUp() throws Exception {
		/*  emptyMax = new PriorityQueue<Integer>(7, new MyComparator());
				emptyMin = new PriorityQueue<Integer>();
				rootOnlyMax = new PriorityQueue<Integer>(7, new MyComparator());
				rootOnlyMin = new PriorityQueue<Integer>();
				sevenObjectMin = new PriorityQueue<Integer>();
				sevenObjectMax = new PriorityQueue<Integer>(7, new MyComparator());
		 */
		emptyMax = new Heap12<Integer>(7, true);
		emptyMin = new Heap12<Integer>(7, false);
		rootOnlyMax = new Heap12<Integer>(7, true);
		rootOnlyMin = new Heap12<Integer>(7, false);
		sevenObjectMin = new Heap12<Integer>(7, false);
		sevenObjectMax = new Heap12<Integer>(7, true);

		rootOnlyMax.add(new Integer(1));
		rootOnlyMin.add(new Integer(1));
		for (int i =1; i<=7; i++){
			sevenObjectMin.add(i);
			sevenObjectMax.add(i);
		}
	}

	//Private class I used to test Java's default max-heap priority queue,
	//since the default is a min-heap I needed a comparator object that does the 
	//exact opposite of normal comparing for testing my Heap12 implentation
	//against
	private class MyComparator implements Comparator<Integer>
	{
		public int compare( Integer x, Integer y ){
			return (y - x);
		}
	}	

	//Simple, tests the add method on a MinHeap. Also kinda tests the poll method
	//a bit to make sure everything trickles down correctly.
	public void testAddMin(){
		assertEquals(new Integer(1), rootOnlyMin.peek());
		rootOnlyMin.add(new Integer(2));
		assertEquals(rootOnlyMin.size(), 2);
		rootOnlyMin.add(new Integer(-1));
		rootOnlyMin.add(new Integer(0));
		assertEquals(new Integer(-1), rootOnlyMin.poll());
		assertEquals(new Integer(0), rootOnlyMin.poll());
	}

	//Pretty much does the exact same as above, except also makes sure the offer
	//method throws the proper exception.
	public void testOfferMin(){
		rootOnlyMin.offer(new Integer(2));
		assertEquals(new Integer(1), rootOnlyMin.peek());
		try{
			rootOnlyMin.offer(null);
			fail("Should not reach this part, should have thrown Exception");
		}
		catch (NullPointerException e){
			//Pass
		}
	}

	//Tests the poll method on a MinHeap a little more thoroughly than the above
	//two methods, also makes sure it returns null when the list is empty.
	public void testPollMin(){
		assertEquals(null, emptyMin.poll());
		assertEquals(new Integer(1), rootOnlyMin.poll());
		for (int i=0; i<7; i++){
			assertEquals(new Integer(i+1), sevenObjectMin.poll());
		}
		assertEquals(null, rootOnlyMin.poll());
	}


	//Same as testAddMin, but with a Max-Heap
	public void testAddMax(){
		assertEquals(new Integer(1), rootOnlyMax.peek());
		rootOnlyMax.add(new Integer(2));
		assertEquals(rootOnlyMax.size(), 2);
		rootOnlyMax.add(new Integer(-1));
		rootOnlyMax.add(new Integer(0));
		assertEquals(new Integer(2), rootOnlyMax.poll());
		assertEquals(new Integer(1), rootOnlyMax.poll());
	}

	//Same as testOfferMin, but with a Max-Heap
	public void testOfferMax(){
		rootOnlyMax.offer(new Integer(2));
		assertEquals(new Integer(2), rootOnlyMax.peek());
		try{
			rootOnlyMax.offer(null);
			fail("Should not reach this part, should have thrown Exception");
		}
		catch (NullPointerException e){
			//Pass
		}
	}

	//Same as testPollMin, but with a Max-Heap
	public void testPollMax(){
		assertEquals(null, emptyMax.poll());
		assertEquals(new Integer(1), rootOnlyMax.poll());
		for (int i=0; i<7; i++){
			assertEquals(new Integer(7-i), sevenObjectMax.poll());
		}
		sevenObjectMax.offer(new Integer(50));
		sevenObjectMax.offer(new Integer(49));
		assertEquals(new Integer(50), sevenObjectMax.poll());
		assertEquals(new Integer(49), sevenObjectMax.poll());
		assertEquals(null, rootOnlyMax.poll());
	}

	//Mostly justs tests to make sure the heap can return the
	//correct element, even if added in a wonky order
	public void testAllTheThings(){
		rootOnlyMin.add(new Integer(17));
		assertEquals(new Integer(1), rootOnlyMin.poll());
		assertEquals(new Integer(17), rootOnlyMin.poll());
		for (int i = 0; i<70; i++){
			if (i%2==1)
				rootOnlyMin.offer(new Integer(i));
			else
				rootOnlyMin.offer(new Integer(i));
		}
		for (int i = 0; i<70; i++)
			assertEquals(new Integer(i), rootOnlyMin.poll());
	}

	//Tests the Heap's internal iterator method to make sure it never
	//causes an index out of bounds exception, and returns the proper
	//amount of values
	public void testIterator(){
		Iterator iter = sevenObjectMax.iterator();
		try{
			iter.next();
			iter.remove();
			iter.remove();
			fail("Should have thrown exception");
		}
		catch (IllegalStateException e){
			//pass
		}
		for (int i = 0; i <5; i++){
			iter.next();
		}
		assertEquals(new Integer(7), iter.next());
		try{
			iter.next();
			fail("Should fail, at end of list");
		}
		catch (NoSuchElementException e){
			//pass
		}
	}

	//Tests the copy constructor
	public void testCopy(){
		for (int i = 0; i<=1400; i++){
			rootOnlyMin.offer(new Integer(i));
			rootOnlyMax.offer(new Integer(i));
		}
		Heap12<Integer> copy1 = new Heap12<Integer>((Heap12) rootOnlyMin);
		assertEquals(copy1.size(), rootOnlyMin.size());
		Heap12<Integer> copy2 = new Heap12<Integer>((Heap12) rootOnlyMax);
		assertEquals(copy2.size(), rootOnlyMax.size());
		for (int i = 0; i<rootOnlyMin.size(); i++){
			assertEquals(copy1.poll(), rootOnlyMin.poll());
			assertEquals(copy2.poll(), rootOnlyMax.poll());
		}
	}

}
