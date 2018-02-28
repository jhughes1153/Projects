package Scraping;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * This class is used to connect to a local mysql database, so it will only work for the local
 * machine but can be set up to work with a server later, basically this class is used to connect
 * to the database and write info to it, it will eventually bring info in as well
 * 
 * @author Jack Hughes
 *
 */
public class AddToDatabase {

	private static Connection con;
	
	private static String url;
	
	public AddToDatabase() {
		
		url = "jdbc:mysql://localhost:3306/FInancialsTest?user=root&password=#1Runner!!@@";
		
	}
	
	/**
	 * This method just takes an array that is input and then calls a procedure from the database, passes
	 * the values in the array to the procedure and the procedure writes them to the database
	 * @param stockInfo is a string array with the proper data
	 * @throws SQLException 
	 */
	public void addArray(String[] stockInfo) throws SQLException {
			con = DriverManager.getConnection(url);
			//it adds a lot of info that is why this has so much stuff here
			String sql = "{call FInancialsTest.insert_Into_tabel(?, ?, ? ,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
			CallableStatement stmt = con.prepareCall(sql);
			
			for(int i = 0; i < stockInfo.length; i++) {
				stmt.setString(i+1, stockInfo[i]);
			}

			ResultSet rs = stmt.executeQuery();
			System.out.println(rs.toString());	
		
	}

}