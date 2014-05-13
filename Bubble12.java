import java.lang.Comparable;
import java.util.List;
/** Bubble Sort 
 * @author Philip Papadopoulos
 * @date  28 April 2014
*/
public class Bubble12 implements Sort12
{
	public <T extends Comparable<? super T>> void sort(List<T> list)
	{
	 	T temp;
		int n = list.size();
		for (int i = 0; i < n; i++)
		{
			int nswaps = 0;
			for(int j = 1; j < (n-i); j++)
			{	
				if (list.get(j-1).compareTo(list.get(j)) > 0 )
				{
					temp = list.get(j-1);
					list.set(j-1,list.get(j));
					list.set(j,temp);
					nswaps++;
				}
			}
			if (nswaps == 0 ) break;
		}
	}
}
// vim:ts=4:sw=4:sw=78
