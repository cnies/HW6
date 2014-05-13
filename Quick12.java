import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;
/** A class that implements quick sort for Lists of Comparables
* @author Philip Papadopoulos
*/
public class Quick12 implements Sort12
{
	public  <T extends Comparable<? super T>> void  sort(List<T> list)
	{
		if ( list == null )
			 throw new NullPointerException("Null argument to sort");

		// Create the arrayList that we will sort 
		ArrayList<T> inputArray = new ArrayList<T>(list.size());
		// Copy the data into the input array
		for (int i = 0; i < list.size(); i++)
			inputArray.add(i,list.get(i));

		quickSort(inputArray, 0, list.size()-1);
		// Copy the inserted array to unsorted List
		for (int i = 0; i < list.size(); i++)
			list.set(i,inputArray.get(i));
	}

	/** quick sort 
 	 *     recursive method that implements quick sort 
 	 * 	@param inputArray
 	 * 			ArrayList of data to sort
 	 *  @param first
 	 *  		beginning index
 	 *  @param end
 	 *  		ending index
 	 */
	private  <T extends Comparable<? super T>> void 
		quickSort(ArrayList<T> inputArray, int first, int last)
	{
		
		if ( first >= last )
			return;

		int pivot = partition(inputArray, first, last);
		
		quickSort(inputArray,first, pivot-1);
		quickSort(inputArray,pivot+1, last);
	} 

	/** This is where comparisons occur. It's job is exchange
 	 *  elements around a designated pivot value so that everything to 
 	 *  left <= pivot everything to the right is > pivot. It does this
 	 *  through a series of exchanges as a linear walk
 	 * 	@param inputArray
 	 * 			ArrayList of data to sort
 	 *  @param first
 	 *  		beginning index
 	 *  @param end
 	 *  		ending index
 	 * 	@return  index where the pivot is located
 	 */
	private  <T extends Comparable<? super T>> int 
		partition(ArrayList<T> inputArray, int first, int last) 
	{
		T pivotVal; 
		pivotVal = inputArray.get(first);
		// There are MANY choices here, just pick the first index as the 
		// the pivot
		int pivot = first;

		int i = first+1, j = last;
		// two indices i--> moves right
		//             <--j  moves left
		// 
		while (i < j)
		{

			// scan from left, look for first value > pivot
			while (i < j && pivotVal.compareTo(inputArray.get(i)) >= 0)
				i++;
			// now scan from right look for first value <= pivot
			while (j > i && pivotVal.compareTo(inputArray.get(j)) <= 0)
				j--;

			// we found two elements, A[j] < pivot, A[i] > pivot, so exchange
			// them.
			if (i < j)
			{
				exchange(inputArray,i,j);
				i++;
				j--;
			}
		}

		assert j == i;
		// at this point have scanned the sub array and we need to decide
		// if we need to exchange the pivot with a different place

		// do we need to exchange the pivot with the index ?
		// Case I: A[pivot] >= A[i]
		if (pivotVal.compareTo(inputArray.get(i)) >= 0)
		{
			exchange(inputArray,pivot,i);
			pivot = i;
		}
		else 
		{
			// Case II: look at index i-1; if A[pivot] > A[i-1] then exchange
			i--;
			if (i > first && pivotVal.compareTo(inputArray.get(i)) >= 0)
			{
				exchange(inputArray,pivot,i);
				pivot = i;
			}
		}
		return pivot;	
	} // partition 

	/** exchange two elements in an ArrayList
     * @param inputArray 
     * 		ArrayList of values;
     * @param i
     * 		first index
     * @param
     * 		second index
     */
	private <T> void exchange(ArrayList<T> inputArray, int i, int j) 
	{
		T tmp = inputArray.get(i);
		inputArray.set(i,inputArray.get(j));
		inputArray.set(j,tmp);
	}
}
// vim:ts=4:sw=4:sw=78
