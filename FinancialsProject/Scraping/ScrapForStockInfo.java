package Scraping;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A class is centered around adding relevant info to an array, pertaining
 * to the stock that is specified, it stores everything as a string at this
 * point in time as it will be converted to numbers later when it is drawn
 * from the database, this class requires teh jsoup library so that must be
 * added to the build path 
 * 
 * @author Jack Hughes
 *
 */
public class ScrapForStockInfo {
	
	//vars to break out of a recursive loop
	private int breakRecurseFin;
	private int breakRecurseComp;	
	private int breakRecurseAnalyst;
	
	//the array for all the info
	private String[] infoForDB;
	private int infoCounter;
	
	private String stockTicker;
	
	/**
	 * Constructor for the object it gets the URL's and puts them into scanners
	 * @param ticker is the ticker for the stock
	 * @throws IOException
	 */
	public ScrapForStockInfo(String ticker) throws IOException {
		
		stockTicker = ticker;
		
		breakRecurseFin = 0;
		breakRecurseComp = 0;
		breakRecurseAnalyst = 0;
		
		infoForDB = new String[21];
		infoForDB[0] = ticker;
		infoCounter++;
		try {
			//Wait 2 seconds as a courtesy thing
			Thread.sleep(2000);
		
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Gets the competitors from the one line of text, grabs only 3 and ignores the first
	 * as it is the original ticker and we already have that, this method is different because
	 * Google doesnt put the data in the html itself but inside of a function so I have to 
	 * iterate through the function string to get to the data, so it is not within a tag like usual
	 */
	public void getStockCompetitors() throws IOException {
		try {
			Document doc = Jsoup.connect("https://finance.google.com/finance/related?q=NYSE%3A" + stockTicker + "&ei=ThZRWoDHEpPrePSQsfAG").get();
			int count = 0;
			
			Elements elem = doc.select("script");
			for (Iterator<Element> iterator = elem.iterator(); iterator.hasNext();) {
				Element element = iterator.next();
				if(element.toString().contains("google.finance.data")) {
					String temp = element.toString();
					// sets the proper column name and the keyword to look for */
					String keyword = ":[\"";
					int index = temp.indexOf(keyword);
					/* this checks the entire string for mutliple keywords
					 * it does this by checking for the index of the keyword +3 and +8
					 * for a max of 5 chars in a ticker it then will remove any extra chars 
					 * not in the ticker
					 */
					while(index >= 0) {
						String tempComp = temp.substring(index+3,index+8);
						if(tempComp.contains("\"")) {
							int findThreeOrFour = tempComp.indexOf("\"");
							tempComp = tempComp.substring(0, findThreeOrFour);
						}
						// This is for skipping the first entry as it will be the original stock
						if(count > 0) {
							infoForDB[infoCounter] = tempComp;
							infoCounter++;
						}
						index = temp.indexOf(keyword, index+keyword.length());
						count++;
						if(count == 4) {
							break;
						}
					}
					break;
				}
			}
		} catch(Exception e) {
			breakRecurseComp++;
			if(breakRecurseComp < 5) {
				System.out.println("did not get goog url");
				getStockCompetitors();
			}
			else {
				System.out.println("Breaking out of comp recurse loop");
			}
		}
	}
	
	/**
	 * This method grabs values much like that last one, it uses two arrays to get the right info as one
	 * is the keywords to look for and the other is the offset for the values
	 */
	public void getFinInfo() {
		try {
			//this matrix is Value measure list top, financial info middle, and trading info last,
			//these are the offsets since we need to find pretty much random elements in a table
			int[][] offsets = {{1, 3, 5, 11, 13, 17},
								{15, 25, 29, 33, 39},
								{19}};
			Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + stockTicker + "/key-statistics?p=" + stockTicker).get();
			int count = 0;
			int counter = 0;
		
			Elements elem = doc.select("span");
			for (Iterator<Element> iterator = elem.iterator(); iterator.hasNext();) {
				Element element = iterator.next();
				if(element.text().contains("Valuation Measures")) {
					Element tableElement = element.parent().nextElementSibling();
					for(Element row : tableElement.select("td")) {
						if(counter == offsets[0][count]) {
							count++;
							infoForDB[infoCounter] = row.text();
							infoCounter++;
							if(count == offsets[0].length) {
								break;
							}
						}
						counter++;
					}
					counter = 0;
					count = 0;
				}
				if(element.text().contains("Financial Highlights")){
					Element tableElement = element.parent().nextElementSibling();
					for(Element row : tableElement.select("td")) {
						if(counter == offsets[1][count]) {
							count++;
							infoForDB[infoCounter] = row.text();
							infoCounter++;
							if(count == offsets[1].length) {
								break;
							}
						}
						counter++;
					}
					counter = 0;
					count = 0;
				}
				
				if(element.text().contains("Trading Information")){
					Element tableElement = element.parent().nextElementSibling();
					for(Element row : tableElement.select("td")) {
						if(counter == offsets[2][count]) {
							count++;
							infoForDB[infoCounter] = row.text();
							infoCounter++;
							if(count == offsets[2].length) {
								break;
							}
						}
						counter++;
					}
				}
				
			}
		} catch (Exception e) {
			breakRecurseFin++;
			if(breakRecurseFin < 5) {
				System.out.println("Did not get yahoo url");
				getFinInfo();
			}
			else {
				System.out.println("Breaking out of Fin recursive loop");
			}
		}
	}
	
	/**
	 * This method goes to the WSJ site and get the relevant information using the same technique as above
	 * but in a simple way since we are only getting one table instead of a few
	 */
	public void getAnalystOpinion() {
		try {
			Document doc = Jsoup.connect("http://quotes.wsj.com/" + stockTicker + "/research-ratings").get();
			int count = 0;
			Elements elem = doc.select("h3");
			for (Iterator<Element> iterator = elem.iterator(); iterator.hasNext();) {
				Element element = iterator.next();
				if(element.text().contains("Stock Price")) {
					Element tableElement = element.parent().nextElementSibling();
					for(Element row : tableElement.select("span")) {
						if(count % 2 == 1) {
							infoForDB[infoCounter] = row.text();
							infoCounter++;
						}
						count++;
					}
				}
			}
		} catch (Exception e) {
			breakRecurseAnalyst++;
			if(breakRecurseAnalyst < 5) {
				System.out.println("Did not get wsj url");
				getAnalystOpinion();
			}
			else {
				System.out.println("Breaking out of analyst recursive loop");
			}
		}
	}
	
	/**
	 * This method is for testing purposes
	 */
	public void printArray() {
		for(int i = 0; i < infoForDB.length; i++) {
			System.out.println(infoForDB[i]);
		}
	}
	
	/**
	 * This method is for getting the array after all of the values have been set, so 
	 * it is for another class to be able to grab the contents of the array
	 * @return returns the array that is being edited
	 */
	public String[] getArray() {
		return infoForDB;
	}
}

//This left here for testing
//public static void main(String[] args) throws IOException {
//ScrapForStockInfo scrap = new ScrapForStockInfo("JPM");
////scrap.getStockCompetitors();
//scrap.getFinInfo();
//scrap.getStockCompetitors();
//scrap.getAnalystOpinion();
//scrap.printArray();
//}
