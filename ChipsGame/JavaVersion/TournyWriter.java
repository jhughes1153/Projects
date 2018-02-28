import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class TournyWriter {

	public static void main(String[] args) throws IOException {
		
		ArrayList<String> result = new ArrayList<String>();
		Player[] play = { new OnesPlayer("Onesy"), new MaxPlayer("Greedy"),
				new MaxPlayer("Selfish"), new RandomPlayer("Lucky"), 
				new RandomPlayer("Bashful")};
		int[] pilestuff = { 10, 21, 55, 117 };
		
		result = Tournament.runTourney(play, pilestuff);
		// Creates the Database
		File file = new File("Tournament.mdb");
		Database db = new DatabaseBuilder(file).setFileFormat(Database.FileFormat.V2000).create();

		// Creates the table to be used in the Database
		Table table = new TableBuilder("Tournament")
			.addColumn(new ColumnBuilder("Name", DataType.TEXT))
			.addColumn(new ColumnBuilder("Wins", DataType.TEXT))
			.addColumn(new ColumnBuilder("Name", DataType.TEXT))
			.addColumn(new ColumnBuilder("Wins", DataType.TEXT))
			.toTable(db);
		for(int i = 0; i < Tournament.getTotal(); i++){
			Scanner input = new Scanner(result.get(i));
			String firstIn = input.next();
			int wins = input.nextInt();
			String secondIn = input.next();
			int loses = input.nextInt();
			table.addRow(firstIn, wins, secondIn, loses);
			input.close();
		}
		
		db.close();

	}

}
