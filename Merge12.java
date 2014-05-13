import java.lang.Comparable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
public class Merge12 implements Sort12
{

	public  <T extends Comparable<? super T>> void  sort(List<T> list)
	{
		if ( list == null )
			 throw new NullPointerException("Null argument to sort");

		// Create the arrayList to insert into
		ArrayList<T> inputArray = new ArrayList<T>(list.size());
		ArrayList<T> tempArray = new ArrayList<T>(list.size()/2);

		//Copy input list into inputArray, and tempArray
		for (int i =0; i<list.size(); i++){
			if (i<list.size()/2)
				tempArray.add(list.get(i));
			inputArray.add(list.get(i));
		}

		//Main sorting algorithm call
		internalMergeSort(inputArray, tempArray, 0, list.size()-1);
		for(int i = 0; i<list.size(); i++){
			list.set(i, inputArray.get(i));
		}
	}

	private  <T extends Comparable<? super T>> void 
		internalMergeSort(ArrayList<T> inputArray, ArrayList<T> tempArray,
					int first, int last)
	{
		if ((last-first+1)<=1){
			return;
		}
		int mid = (last+first+1)/2;

		//Sort on the bottom half of the list
		internalMergeSort(inputArray, tempArray, first, mid-1);
		//Sort on the top half of the list
		internalMergeSort(inputArray, tempArray, mid, last);
		//Merge the two sorted halves together 
		merge(inputArray, tempArray, first, mid, last); 
	} 

	
	
	
	
	
	private  <T extends Comparable<? super T>> void 
		merge(ArrayList<T> inputArray, ArrayList<T> tempArray,
					int first, int mid, int last)
	{
		int tempSize = last - first + 1;
		int insertIndex = first;
		int firstPartitionIndex = 0;
		int secondPartitionIndex = mid;
		
		//Copy first partition into tempArray
		int copyToIndex = 0;
		int copyFromIndex = first;
		for (int i = 0; i<(mid-first); i++){
			tempArray.set(copyToIndex, inputArray.get(copyFromIndex));
			copyToIndex++; copyFromIndex++;
		}

		//Main loop
		while ((firstPartitionIndex<(mid-first)) && (secondPartitionIndex<=last)){
			T object1 = tempArray.get(firstPartitionIndex);
			T object2 = inputArray.get(secondPartitionIndex);

			//Compare what's on top of both lists, find the lowest, and put it in
			//the inputArray
			if(object1.compareTo(object2)<0){
				inputArray.set(insertIndex, tempArray.get(firstPartitionIndex));
				firstPartitionIndex++;
			}
			else{
				inputArray.set(insertIndex, inputArray.get(secondPartitionIndex));
				secondPartitionIndex++;
			}
			insertIndex++;
		}

		//If the second half is empty, but the first half isn't, copy the first
		//half into the sortedArray
		while (firstPartitionIndex<(mid-first)){
			inputArray.set(insertIndex, tempArray.get(firstPartitionIndex));
			firstPartitionIndex++; insertIndex++;
		}
	} 

	//Just for testing
	public static void main(String[] args){
		LinkedList<String> alphabet = new LinkedList<String>();
		LinkedList<Integer> integers= new LinkedList<Integer>();
		for (int i = 0; i<26; i++){
			alphabet.add(""+ (char)((int)'Z'-i));
			integers.add(new Integer(26-i));
		}
		Merge12 sorter = new Merge12();
		sorter.sort(alphabet);
		sorter.sort(integers);
		for(String letter: alphabet){
			System.out.println(letter);
		}
		for(Integer number: integers){
			System.out.println(number);
		}

	}
}
