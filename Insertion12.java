import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
/** This is a modification of classic Insertion sort that sorts
 *  the List elements to an auxilliary  ArrayList.  
 *  It uses binary search to find the insertion point
 *  @author Philip Papadopoulos 
 *  @date 28 April 2014
 */
public class Insertion12 implements Sort12
{
	public  <T extends Comparable<? super T>> void sort(List<T> list)
	{
		// Create the arrayList to insert into
		ArrayList<T> inserted = new ArrayList<T>(list.size());
		T temp;
		int n = list.size();
		for (int i = 0; i < n; i++)
		{
			T target = list.get(i);
			int loc = binSearch(inserted,target);
			// System.out.format("%d: loc: %d  target: %s\n",i,loc,target.toString());
			inserted.add(loc+1,target);
		}
		// Copy the inserted array to unsorted List
		for (int i = 0; i < n; i++)
			list.set(i,inserted.get(i));
	}

	/** Search the ArrayList to find where to insert the target
 	 *  @param sarray - a sorted ArrayList
 	 *  @param target - looking for where to insert 
 	 *  @return index i such that sarray[i] <= target, sarray[i] >= target
	 */
	private <T extends Comparable<? super T>> 
			int binSearch(ArrayList<T> sarray, T target)
	{
		int left = 0, right = sarray.size() - 1 ;
		int mid = 0;
		int loc = mid;

		if (right == -1) return -1; // nothing to insert in first slot
 
		// Check if what we are looking for is >= than largest element
		if (right >= 0  && sarray.get(right).compareTo(target) <= 0 )
			return right;
		// Check if what we are looking for is <= than smallest element
		if (sarray.get(left).compareTo(target)  >= 0 )
			return -1;

		// somewhere in the middle, binsearch for it
		while ((right - left) > 1 )
		{
			mid = (left + right)/2;
			T tmp = sarray.get(mid);
			int cvalue = tmp.compareTo(target);
			if (cvalue < 0)
			{
				left = mid;  // in (mid,right)
				loc = mid; 
			}
			else
			{
				right = mid;  // in (left,mid)
				loc = mid - 1;
			}
			if (cvalue == 0 ) return mid; // exact match, stop 
		}
		return loc;
	}	
}
// vim:ts=4:sw=4:sw=78
