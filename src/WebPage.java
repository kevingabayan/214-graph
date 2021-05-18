import java.util.ArrayList;

/**
 * The <code>WebPage<code> class represents a hyper linked document.
 *	  @author Kevin Gabayan
 *    e-mail: kevin.gabayan@stonybrook.edu
 *    Stony Brook ID: 111504873
 */
public class WebPage {
	private String url;
	private int index;
	private int rank;
	private ArrayList<String> keywords;
	/**
	 * WebPage Variables
	 * @param url
	 * The URL of the document.
	 * @param index
	 * The position in the adjacency matrix.
	 * @param rank
	 * The rank of the WebPage.
	 * @param keywords
	 * A collection of strings containing the keywords describing the page.
	 */
	
	/**
	 * This is a constructor for a web page with parameters.
	 */
	public WebPage(String url, int index, ArrayList<String> keywords) {
		this.url = url;
		this.index = index;
		this.keywords = keywords;
	}
	// These are basic getters and setters for your WebPage variables.
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getRank() {
		return rank;
	}
	public String getUrl() {
		return url;
	}
	/**
	 * THis is an overloaded constructor for the WebPage class.
	 */
	public WebPage(String url, ArrayList<String> keywords) {
		this.url = url;
		this.keywords = keywords;
	}
	
	/**
	 * This method returns the string of data members in tabular form.
	 * 
	 */
	public String toString() {
		String finalString = "";
		finalString += String.format(" %2d    | %-19s|   %2d    | *** | %-50s", index, url, rank, keywords);
		return finalString;
	}
	/**
	 * This is a method to allow comparisons between indices.
	 * @param o
	 * The object to be compared to.
	 * @return
	 * 1 if the first object is greater than the second, -1 if the opposite, and 0 if htey are equal.
	 */
}
