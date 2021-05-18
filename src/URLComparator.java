import java.util.Comparator;
/**
 * The <code>URLComparator<code> helper class organizes the WebPage objects alphabetically ascending based on the URL
 * of the WebPage.
 *    e-mail: kevin.gabayan@stonybrook.edu
 *    Stony Brook ID: 111504873
 */
public class URLComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		WebPage w1 = (WebPage) o1;
		WebPage w2 = (WebPage) o2;
		return (w1.getUrl().compareTo(w2.getUrl()));
	}

}
