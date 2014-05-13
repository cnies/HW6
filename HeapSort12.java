import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/** This is a Heap Sort
 *  @author Philip Papadopoulos 
 *  @date 28 April 2014
 */
public class HeapSort12 implements Sort12
{
	public  <T extends Comparable<? super T>> void sort(List<T> list)
	{
		Iterator iter = list.iterator();
		Heap12<T> theHeap = new Heap12<T>(list.size(), false);
		for (int i=0; i<list.size(); i++){
			theHeap.offer(list.get(i));
		}
		for (int i =0; i<list.size(); i++){
			list.set(i, theHeap.poll());
		}
	}
	public static void main(String[] args){
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i<501; i++){
				list.add(""+(char)((int)'A'+i%52));
		}
		HeapSort12 sorter = new HeapSort12();
		sorter.sort(list);
		for (String i: list){
			System.out.println(i);
		}
	}
}

// vim:ts=4:sw=4:sw=78
