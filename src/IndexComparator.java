import java.util.Comparator;
/**
 * The <code>IndexComparator<code> helper class sorts numerically ascending based on the index of the WebPage.
 *	  @author Kevin Gabayan
 *    e-mail: kevin.gabayan@stonybrook.edu
 *    Stony Brook ID: 111504873
 */
public class IndexComparator implements Comparator {
	public int compare(Object o1, Object o2) {
			WebPage w1 = (WebPage) o1;
			WebPage w2 = (WebPage) o2;
			if(w1.getIndex() == w2.getIndex()) {
				return 0;
			}
			else if (w1.getIndex() > w2.getIndex()) {
				return 1;
			}
			else 
				return -1;
	}	
}

