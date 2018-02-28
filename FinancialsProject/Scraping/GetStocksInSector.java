package Scraping;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A class that builds an array of every stock in the financial sector for just their ticker
 * @author Jack Hughes
 *
 */
public class GetStocksInSector {
	
	public String[] info;
	public URL url;
	public Scanner console;
	
	/**
	 * The constructor for the class it grabs the url but should probably recurse for the url, but nasdaq is pretty reliable
	 * it will also just fail anyways if it does not get this and can be run again, since it is once running it again wont
	 * be all that much to do, it is not super dynamic but I do not believe that I need to do a jsoup of it because
	 * this method will work just as good because it is uniform
	 * @throws IOException in case the url not being caught
	 */
	public GetStocksInSector() throws IOException {
		url = new URL("http://www.nasdaq.com/screening/companies-by-industry.aspx?industry=Finance&pagesize=1000&region=North+America");
		console = new Scanner(new InputStreamReader(url.openStream()));
		info = new String[860];
	}

	/**
	 * This takes the input from the console and parses through it to look for the tickers since they are in a uniform pattern
	 * @return
	 */
	public void setInfo() {
		System.out.println("Checking html");
		String inputLine;
		int count = 0;
		try{
			while((inputLine = console.nextLine()) != null){
				try {
					if(inputLine.contains(" &nbsp;")){
						info[count] = (inputLine.substring(20, inputLine.indexOf(" Stock")));
						System.out.println(info[count]);
						count++;
						if(count == 1740) {
							console.close();
						}
					}
				} catch(Exception e) {
					System.out.println("To skip the first one");
				}
			}
			System.out.println(count);
		}
		catch (NoSuchElementException e){
			console.close();
		}
        console.close();
	}
	
	public String[] getFinArray() {
		return info;
	}
	
	/**
	 * This was for testing to make sure it is getting tickers and not all null
	 */
	public void getIndexes() {
		for(int i = 0; i < 20; i++) {
			System.out.println(info[i]);
		}
	}
}

//This is kept just as a reference no properly calling the class
//public static void main(String[] args) throws IOException{
//GetStocksInSector get = new GetStocksInSector();
//get.setInfo();
//String[] test = get.getFinArray();
//for(int i = 0; i < test.length; i++) {
//	System.out.println(test[i]);
//}
//}
