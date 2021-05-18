import java.util.Comparator;
/**
 * The <code>RankComparator<code> class sorts numerically descending based on the PageRank
 * of the WebPage.
 *	  @author Kevin Gabayan
 *    e-mail: kevin.gabayan@stonybrook.edu
 *    Stony Brook ID: 111504873
 */
public class RankComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPage w1 = (WebPage) o1;
		WebPage w2 = (WebPage) o2;
		if(w1.getRank() == w2.getRank()) {
			return 0;
		}
		else if (w1.getRank() > w2.getRank()) {
			return -1;
		}
		else 
			return 1;
}	

	
}
