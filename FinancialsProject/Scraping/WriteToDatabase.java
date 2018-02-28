package Scraping;

import java.io.IOException;
import java.sql.SQLException;

/**
 * This class is to bring all of the other classes together with a main method, it uses
 * the info from scraping in other classes to write to the database
 * @author Jack Hughes
 *
 */
public class WriteToDatabase {

	public static void main(String[] args) throws IOException, SQLException {
		
		GetStocksInSector sect = new GetStocksInSector();
		sect.setInfo();
		String[] finSector = sect.getFinArray();
		for(int i = 0; i < finSector.length; i++) {
			try {
				ScrapForStockInfo scrap = new ScrapForStockInfo(finSector[i]);
				scrap.getFinInfo();
				scrap.getStockCompetitors();
				String[] info = scrap.getArray();
				AddToDatabase adder = new AddToDatabase();
				adder.addArray(info);
			} catch(Exception e) {
				System.out.println("Did not get values for: " + finSector[i]);
			}
		}

	}

}
