package Scraping;

import java.io.IOException;

/**
 * This is just a main method for using all of the classes in one, this one uses the excel
 * writer to store all of the data which isn't as good as a database but was still an 
 * intermediate step
 * @author Jack Hughes
 *
 */
public class BringingItAllTogetherExcel {

	public static void main(String[] args) throws IOException {
		GetStocksInSector get = new GetStocksInSector();
		get.setInfo();
		String[] finSector = get.getFinArray();
		
		ExcelWriter exceler = new ExcelWriter();
		int length = finSector.length;
		for(int i = 0; i < length; i++) {
			String ticker = finSector[i];
			try {
				ScrapForStockInfo temp = new ScrapForStockInfo(ticker);
				temp.getFinInfo();
				temp.getStockCompetitors();
				exceler.writeToExcel(temp.getArray());
			} catch (Exception e) {
				System.out.println("Did not get values for: " + finSector[i]);
			}
		}
		exceler.closeAndWrite();
	}

}
