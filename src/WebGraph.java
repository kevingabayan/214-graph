import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The <code>WebGraph<code> class organizes the WebPage objects as a directed
 * graph.
 * 
 * @author Kevin Gabayan e-mail: kevin.gabayan@stonybrook.edu Stony Brook ID:
 *         111504873
 */
public class WebGraph {
	public static final int MAX_PAGES = 40;
	private ArrayList<WebPage> pages;
	private ArrayList<ArrayList<Integer>> edges;

	/**
	 * WebGraph variables
	 * 
	 * @param pages
	 *            The WebPages member variable
	 * @param edges
	 *            A 2 dimensional adjacency matrix of the int member variable. [IS
	 *            REFERRED TO AS LINKS ON ASSIGNMENT]
	 */

	/**
	 * This is an empty constructor for the WebGraph. I want every value initalized
	 * to zero before proceeding.
	 */
	public WebGraph() {
		pages = new ArrayList<WebPage>();
		edges = new ArrayList<ArrayList<Integer>>(MAX_PAGES);
		for (int i = 0; i < MAX_PAGES; i++) {
			edges.add(i, new ArrayList<Integer>(MAX_PAGES));
			for (int j = 0; j < MAX_PAGES; j++) {
				edges.get(i).add(j, 0);
			}
		}
	}

	/**
	 * This method constructs a WebGraph object using the indicated files as the
	 * source for pages and edges.
	 * 
	 * @param pagesFile
	 *            The string of the relative path to the file containing the page
	 *            information.
	 * @param linksFile
	 *            The string of the relative path containing the link information.
	 *            <dt><b>Preconditions:</b>
	 *            <dd>Both parameters reference text files that exist, and the files
	 *            follow proper format.
	 *            <dt><b>Postconditions:</b>
	 *            <dd>A WebGraph has been constructed and initialized based on the
	 *            text files.
	 * @throws IllegalArgumentException
	 *             Thrown if either of the files does not reference a valid text
	 *             file, or if the files are not formatted correctly.
	 * @return The WebGraph constructed from the text files.
	 * @throws IOException
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IOException {

		// File Creation, Checks for Illegal Arguments
		FileInputStream page = null;
		FileInputStream link = null;
		try {
			page = new FileInputStream(pagesFile);
		} catch (IllegalArgumentException | FileNotFoundException e) {
			System.out.println("File is most definitely not valid! Exiting program.");
			return null;
		}
		try {
			link = new FileInputStream(linksFile);
		} catch (IllegalArgumentException | FileNotFoundException e) {
			System.out.println("File is most definitely not valid! Exiting program.");
			return null;
		}

		InputStreamReader pageStream = new InputStreamReader(page);
		InputStreamReader linkStream = new InputStreamReader(link);

		BufferedReader pageReader = new BufferedReader(pageStream);
		BufferedReader linkReader = new BufferedReader(linkStream);

		// Constructs the WebGraph
		WebGraph toReturn = new WebGraph();

		// Inserts all of the web pages into the pages Collection in the WebGraph
		// object.
		int index = 0;
		String pageToRead = pageReader.readLine();
		while (pageToRead != null) {

			// Trims the text file to ensure no whitespace at the beginning.
			while (pageToRead.charAt(0) == ' ') {
				pageToRead = pageToRead.substring(1);
			}

			// Retrieves the URL of the web page.
			int indexRetrieval = pageToRead.indexOf(" ");
			String url;
			if (indexRetrieval != -1)
				url = pageToRead.substring(0, indexRetrieval);
			else
				url = pageToRead;

			// Checks if URL is invalid... if so.... exits the program
			if (!url.substring(url.length() - 4, url.length()).equals(".org")
					&& !url.substring(url.length() - 4, url.length()).equals(".edu")
					&& !url.substring(url.length() - 4, url.length()).equals(".com")) {
				System.out.println("File is most definitely not valid! Exiting program.");
				return null;
			}

			// Retrieves the keywords of the web page.
			ArrayList<String> keywords = new ArrayList<String>();
			while (indexRetrieval != -1) {
				pageToRead = pageToRead.substring(indexRetrieval + 1);
				indexRetrieval = pageToRead.indexOf(" ");
				if (indexRetrieval == -1) {
					keywords.add(pageToRead);
				} else {
					String keyword = pageToRead.substring(0, indexRetrieval);
					keywords.add(keyword);
					indexRetrieval = pageToRead.indexOf(" ");
				}
			}
			// Constructs the web page
			WebPage toAdd = new WebPage(url, index, keywords);
			toReturn.pages.add(toAdd);

			// Used for Loop
			index++;
			pageToRead = pageReader.readLine();

		}

		// Reads each of the links and adds them to the links adjacency matrix, ensuring
		// that
		// you do not attempt to add and link between pages before adding either of the
		// pages to the graph.
		String linkToRead = linkReader.readLine();
		while (linkToRead != null) {

			// Trimming
			while (linkToRead.charAt(0) == ' ') {
				linkToRead = linkToRead.substring(1);
			}

			String firstHalf = linkToRead.substring(0, linkToRead.indexOf(" "));
			String secondHalf = linkToRead.substring(linkToRead.indexOf(" ") + 1);

			if (!firstHalf.substring(firstHalf.length() - 4, firstHalf.length()).equals(".org")
					&& !firstHalf.substring(firstHalf.length() - 4, firstHalf.length()).equals(".edu")
					&& !firstHalf.substring(firstHalf.length() - 4, firstHalf.length()).equals(".com")) {
				System.out.println("File is most definitely not valid! Exiting program.");
				return null;
			}

			if (!secondHalf.substring(secondHalf.length() - 4, secondHalf.length()).equals(".org")
					&& !secondHalf.substring(secondHalf.length() - 4, secondHalf.length()).equals(".edu")
					&& !secondHalf.substring(secondHalf.length() - 4, secondHalf.length()).equals(".com")) {

				System.out.println("File is most definitely not valid! Exiting program.");
				return null;
			}

			int indexFirst = -1;
			int indexSecond = -1;
			for (int i = 0; i < toReturn.pages.size(); i++) {
				if (toReturn.pages.get(i).getUrl().equals(firstHalf)) {
					indexFirst = i;
				}
				if (toReturn.pages.get(i).getUrl().equals(secondHalf)) {
					indexSecond = i;
				}
			}
			// Tells you if the link is invalid.
			if (indexFirst == -1 || indexSecond == -1) {
				System.out.println("Link file is invalid!");
				return null;
			}
			toReturn.edges.get(indexFirst).set(indexSecond, toReturn.edges.get(indexFirst).get(indexSecond) + 1);
			linkToRead = linkReader.readLine();
		}
		return toReturn;
	}

	/**
	 * This method adds a page to the WebGraph.
	 * 
	 * @param url
	 *            The URL of the web page.
	 * @param keywords
	 *            The keywords associated with the web page.
	 * @throws IllegalArgumentException
	 *             Is thrown if the URL is not unique and already exists in the
	 *             graph. or if either argument is null.
	 *             <dt><b>Preconditions:</b>
	 *             <dd>The URL is unique and does not exist as the URL of a WebPage
	 *             already in the graph. The URL and keywords are not null.
	 *             <dt><b>Postconditions:</b>
	 *             <dd>The page has been added to pages at index 'i' and links has
	 *             been logically extended to include the new row and column indexed
	 *             by i.
	 * 
	 */
	public void addPage(String url, ArrayList<String> keywords) {
		// Creates the WebPage, adds it to 'index' i
		int newIndex = this.pages.size();
		WebPage toAdd = new WebPage(url, newIndex, keywords);
		this.pages.add(toAdd);
		this.updatePageRanks();

	}

	/**
	 * Adds a link from the WebPage with the URL indicated by source to the WebPage
	 * with the URL indicated by destination.
	 * 
	 * @param source
	 *            The URL of the page which contains the hyper-link to destination.
	 * @param desitnation
	 *            The URL of the page which the hyper-link points to.
	 *            <dt><b>Preconditions:</b>
	 *            <dd>Both parameters reference WebPages which exist in the graph.
	 * @throws IllegalArgumentException
	 *             If the URLs are null or cannot be found in pages.
	 */
	public void addLink(String source, String destination) {
		// Adds the Link
		int indexFirst = -1;
		int indexSecond = -1;
		for (int i = 0; i < this.pages.size(); i++) {
			if (this.pages.get(i).getUrl().equals(source)) {
				indexFirst = i;
			}
			if (this.pages.get(i).getUrl().equals(destination)) {
				indexSecond = i;
			}
		}
		if (indexFirst == -1 || indexSecond == -1) {
			System.out.println();
			System.out.println("One of the URLs cannot be found!");
			return;
		}
		this.edges.get(indexFirst).set(indexSecond, this.edges.get(indexFirst).get(indexSecond) + 1);
		this.updatePageRanks();
		System.out.println();
		System.out.println("Link successfully added from " + source + " to " + destination + "!");
	}

	/**
	 * Removes the WebPage from the graph with the given URL.
	 * 
	 * @param url
	 *            The URL of the page to remove from the graph.
	 *            <dt><b>Postconditions:</b>
	 *            <dd>The WebPage with the URL has been removed from the graph. It's
	 *            corresponding row and column has been removed from the adjacency
	 *            matrix. All pages that have an index greater than the index that
	 *            was removed has their index value decreased by 1. If the URL is
	 *            null or cannot be found in pages, the method ignores the input and
	 *            does nothing. Note: When the page is removed, its corresponding
	 *            row and column must be removed from the adjacency matrix.
	 */
	public void removePage(String url) {
		// If URL is null
		if (url == null) {
			System.out.println("The URL is null!");
			return;
		}
		// Finds the url in pages, saves its index, if index cannot be found, returns
		// nothing
		int urlIndex = -1;
		for (int i = 0; i < this.pages.size(); i++) {
			if (this.pages.get(i).getUrl().equals(url)) {
				urlIndex = i;
			}
		}
		if (urlIndex == -1) {
			System.out.println("The URL could not be found!");
			return;
		}
		// Removes from pages, removes from adjacency matrix
		for (int k = 0; k < pages.size(); k++) {
			for (int j = urlIndex; j < pages.size() - 1; j++) {
				this.edges.get(k).set(j, this.edges.get(k).get(j + 1));
			}
		}

		for (int j = urlIndex; j < pages.size() - 1; j++) {
			for (int k = 0; k < pages.size(); k++) {
				this.edges.get(j).set(k, this.edges.get(j + 1).get(k));
			}
		}
		this.pages.remove(urlIndex);

		// Makes the correct index
		for (int p = urlIndex; p < this.pages.size(); p++) {
			this.pages.get(p).setIndex(this.pages.get(p).getIndex() - 1);
		}
		this.updatePageRanks();
		System.out.println(url + " has been removed from the graph!");
		System.out.println();
	}

	/**
	 * This method removes the link from WebPage with the URL indicated by source to
	 * the WebPage with the URL indicated by destination.
	 * 
	 * @param source
	 *            The URL of the WebPage to remove the link/
	 * @param destination
	 *            The URL of the link to be removed.
	 *            <dt><b>Postconditions:</b>
	 *            <dd>The entry in links for the specified hyper-link has been set
	 *            to 0 (no link). If either of the URLs cannot be found, the input
	 *            is ignored and the method does nothing.
	 */
	public void removeLink(String source, String destination) {
		int indexFirst = -1;
		int indexSecond = -1;
		for (int i = 0; i < this.pages.size(); i++) {
			if (this.pages.get(i).getUrl().equals(source)) {
				indexFirst = i;
			}
			if (this.pages.get(i).getUrl().equals(destination)) {
				indexSecond = i;
			}
		}
		if (indexFirst == -1 || indexSecond == -1) {
			return;
		}
		this.edges.get(indexFirst).set(indexSecond, 0);
		this.updatePageRanks();
		System.out.println("Link removed from " + source + " to " + destination + "!");
		System.out.println();
	}

	/**
	 * This method calculates and assigns the PageRank for every page in the
	 * WebGraph. Note: This operation should be performed after ANY alteration of
	 * the graph structure.
	 * <dt><b>Postconditions:</b>
	 * <dd>All WebPages in the graph have been assigned their proper PageRank.
	 */
	public void updatePageRanks() {
		for (int i = 0; i < pages.size(); i++) {
			int toRank = 0;
			for (int j = 0; j < pages.size(); j++) {
				toRank += edges.get(j).get(i);
				pages.get(i).setRank(toRank);
			}
		}
	}

	/**
	 * This method prints the WebGraph in tabular form.
	 */
	public void printTable() {
		System.out.println("Index     URL               PageRank  Links               Keywords");
		System.out.println("-----------------------------------------------------------------------------"
				+ "-------------------------");
		for (int i = 0; i < this.pages.size(); i++) {
			String okay = pages.get(i).toString();

			// Find the links
			String links = "";
			for (int j = 0; j < this.pages.size(); j++) {
				if (this.edges.get(pages.get(i).getIndex()).get(j) != 0) {
					links += Integer.toString(j) + ", ";
				}
			}
			if (links.length() > 1)
				links = links.substring(0, links.length() - 2);
			links = String.format("%-18s", links);
			okay = okay.replace("***", links);
			System.out.println(okay);
		}
	}

	/**
	 * Getters and Setters for the WebGraph
	 * 
	 * @return
	 */
	public ArrayList<WebPage> getPages() {
		return pages;
	}

	public ArrayList<ArrayList<Integer>> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<ArrayList<Integer>> edges) {
		this.edges = edges;
	}

	public void setPages(ArrayList<WebPage> pages) {
		this.pages = pages;
	}
}
