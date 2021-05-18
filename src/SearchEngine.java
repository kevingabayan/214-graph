import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * The <code>SearchEngine<code> class initializes a WebGraph from appropriate text files and allows the user
 * to search for keywords in the graph.
 *	  @author Kevin Gabayan
 *    e-mail: kevin.gabayan@stonybrook.edu
 *    Stony Brook ID: 111504873
 */
public class SearchEngine {
	public static final String PAGES_FILE = "pages.txt";
	public static final String LINKS_FILE = "links.txt";
	private WebGraph web;
	
	/**
	 * This is a constructor for creating the WebGraph.
	 * @throws IOException 
	 */
	public SearchEngine() throws IOException {
		this.web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
	}
	
	/**
	 * This bonus function prints out the menu for easy generation.
	 */
	public static void menuGeneration() {
		System.out.print("Menu:\r\n" + 
				"    (AP) - Add a new page to the graph.\r\n" + 
				"    (RP) - Remove a page from the graph.\r\n" + 
				"    (AL) - Add a link between pages in the graph.\r\n" + 
				"    (RL) - Remove a link between pages in the graph.\r\n" + 
				"    (P)  - Print the graph.\r\n" + 
				"    (S)  - Search for pages with a keyword.\r\n" + 
				"    (Q)  - Quit.\r\n" + 
				"\r\n" + 
				"Please select an option: ");
	}
	
	/**
	 * This main method allows the function to come together.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		
		// Loads WebGraph from file, calculates PageRanks
		System.out.println("Loading WebGraph data...");
		SearchEngine toUse = new SearchEngine();
		try {
			toUse.web.updatePageRanks();
		}
		catch(NullPointerException e) {
			return;
		}
		System.out.println("Success!");
		System.out.println();
		
		// Loop Configuration
		boolean end = false;
		menuGeneration();
		String selection = input.nextLine();
		selection = selection.toLowerCase();
		System.out.println();
		while(end != true) { 
			if(selection.equals("AP") || selection.equals("ap")) {
				
				// Ask for values
				System.out.print("Enter a URL: ");
				String url = input.nextLine();
				System.out.print("Enter keywords (space-separated): ");
				String keywords = input.nextLine();
				
				ArrayList<String> keywordList = new ArrayList<>();
				
				// Please.
				if(keywords.equals(" ")) {
					System.out.println("Stop trying to break my code please! Thank you, next :)");
					continue;
				}
				// Is used for when the keyword is empty but you still have a URL
				if(keywords.equals("")) {
					System.out.println();
					// Checks if either argument is null
					if(url == null || keywords == null) {
						System.out.println("One or more arguments are null!");
						System.out.println();
						menuGeneration();
						selection = input.nextLine();
						selection = selection.toLowerCase();
						continue;
					}
					// Check if URL is unique
					for(int i = 0; i < toUse.web.getPages().size(); i++) {
						if(toUse.web.getPages().get(i).getUrl().equals(url)) {
							System.out.println("This URL is not unique!");
							System.out.println();
							menuGeneration();
							selection = input.nextLine();
							selection = selection.toLowerCase();
							break;
						}
					}
					toUse.web.addPage(url, keywordList);
					System.out.println(url + " successfully added to the WebGraph!");
					System.out.println();
					menuGeneration();
					selection = input.nextLine();
					selection = selection.toLowerCase();
					continue;
				}
				// Trimming
				while(keywords.charAt(0) == ' ') {
					keywords = keywords.substring(1);
				}
				// Adds keywords onto list
				int spacingOut = keywords.indexOf(" ");
				while(spacingOut != -1) {
					String word = keywords.substring(0, spacingOut);
					keywordList.add(word);
					keywords = keywords.substring(spacingOut + 1);
					spacingOut = keywords.indexOf(" ");
					if(spacingOut == -1 && !keywords.equals("")) {
						keywordList.add(keywords);
					}
				}
				System.out.println();
				// Precondition check
				// Checks if either argument is null
				boolean unique = true;
				// Check if URL is unique
				for(int i = 0; i < toUse.web.getPages().size(); i++) {
					if(toUse.web.getPages().get(i).getUrl().equals(url)) {
						System.out.println("This URL is not unique!");
						unique = false;
					}
				}
				if(url == null || keywords == null) {
					System.out.println("One or more arguments are null!");
					System.out.println();
					menuGeneration();
					selection = input.nextLine();
					selection = selection.toLowerCase();
					continue;
				}
				else if(unique == false) {
					System.out.println();
					menuGeneration();
					selection = input.nextLine();
					selection = selection.toLowerCase();
					continue;
				}
				else {
				toUse.web.addPage(url, keywordList);
				System.out.println(url + " successfully added to the WebGraph!");
				System.out.println();
			}
			}
			else if(selection.equals("RP") || selection.equals("rp")) {
				System.out.print("Enter the URL: ");
				String url = input.nextLine();
				System.out.println();
				toUse.web.removePage(url);
				
			}
			else if(selection.equals("AL") || selection.equals("al")) {
				System.out.print("Enter a source URL: ");
				String source = input.nextLine();
				source = source.toLowerCase();
				System.out.print("Enter a destination URL: ");
				String destination = input.nextLine();
				destination = destination.toLowerCase();
				
				// Checks for Illegal Arguments
				if(source == null || destination == null) {
					System.out.println("One or more arguments are null!");
					System.out.println();
					menuGeneration();
					selection = input.nextLine();
					selection = selection.toLowerCase();
					continue;
				}
				
				toUse.web.addLink(source, destination);
				System.out.println();
			}
			else if(selection.equals("RL") || selection.equals("rl")) {
				System.out.print("Enter a source URL: ");
				String source = input.nextLine();
				System.out.print("Enter a destination URL: ");
				String destination = input.nextLine();
				toUse.web.removeLink(source, destination);
	
			}
			else if(selection.equals("P") || selection.equals("p")) {
				System.out.println("    (I) Sort based on index (ASC)\r\n" + 
						"    (U) Sort based on URL (ASC)\r\n" + 
						"    (R) Sort based on rank (DSC)");
				System.out.println();
				System.out.print("Please select an option: ");
				selection = input.nextLine();
				System.out.println();
				if(selection.equals("I") || selection.equals("i")) {
					Collections.sort(toUse.web.getPages(), new IndexComparator());
					toUse.web.printTable();
					System.out.println();
				}
				else if (selection.equals("U") || selection.equals("u")) {
					Collections.sort(toUse.web.getPages(), new URLComparator());
					toUse.web.printTable();
					System.out.println();
					
				}
				else if (selection.equals("R") || selection.equals("r")) {
					Collections.sort(toUse.web.getPages(), new RankComparator());
					toUse.web.printTable();
					System.out.println();
					
				}
				else {
					System.out.println("You didn't select a valid option, so we're going back to the menu!");
					System.out.println();
				}
			}
			else if(selection.equals("S") || selection.equals("s")) {
				System.out.print("Search keyword: ");
				String keyword = input.nextLine();
				keyword = keyword.toLowerCase();
				System.out.println();
				ArrayList<WebPage> search = new ArrayList<WebPage>();
				for(int i = 0; i < toUse.web.getPages().size(); i++) {
					for(int j = 0; j < toUse.web.getPages().get(i).getKeywords().size(); j++) {
						if(keyword.equals(toUse.web.getPages().get(i).getKeywords().get(j))) {
							search.add(toUse.web.getPages().get(i));
							break;
						}
					}
				}
				if(search.isEmpty()) {
					System.out.println("No search reuslts found for the keyword.");
					System.out.println();
				}
				else {
					Collections.sort(search, new RankComparator());
					System.out.println("Rank   PageRank    URL");
					System.out.println("---------------------------------------------");
					for(int k = 0; k < search.size(); k++) {
						System.out.printf(" %2d  |   %2d     | %-27s\n", k+1  , search.get(k).getRank(), search.get(k).getUrl());
					}
					System.out.println();
				}
				
	
			}
			else if(selection.equals("Q") || selection.equals("q")) {
				end = true;
				System.out.println("And that concludes the last CSE 214 HW! :)");
				break;
				
			}
			
			menuGeneration();
			selection = input.nextLine();
			selection = selection.toLowerCase();
			System.out.println();
			
		}
}
}
