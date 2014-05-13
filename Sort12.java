/** Interface Sort12
 *  	This specifies a similar interface as java Collections Interface
*/
import java.lang.Comparable;
import java.util.List;
public interface Sort12
{
	public <T extends Comparable<? super T>> void sort(List<T> list);
}
// vim:ts=4:sw=4:sw=78
