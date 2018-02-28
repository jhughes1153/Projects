import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

/**
 * A class that creates a MS Access file and also creates
 * a table inside of it with the given fields
 * 
 * @author Jack Hughes
 * @version 04/04/17
 */
public class DBWriter {

	static Table table;
	public static void main(String[] args) throws IOException {
		
		//Creates the Database
		File file = new File("first.mdb");
		Database db = new DatabaseBuilder(file)
				.setFileFormat(Database.FileFormat.V2000)
				.create();
		//Creates the table to be used in the Database
		table = new TableBuilder("Smarts")
				.addColumn(new ColumnBuilder("Name", DataType.TEXT))
				.addColumn(new ColumnBuilder("IQ", DataType.INT))
				.toTable(db);
		table.addRow("Jack", "150");
		table.addRow("Steve", "100");
		table.addRow("Emily", "200");
		
		db.close();

	}

}
