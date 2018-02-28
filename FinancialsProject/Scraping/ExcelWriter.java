package Scraping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class is used to write the values to excel, I used that as a starting point
 * as it would be easier for visualization, but a database like mysql could be 
 * better used for extracting the data, this class requires the use of the apache poi
 * library so those jars must be imported for this to work
 * @author Jack Hughes
 *
 */
public class ExcelWriter {

	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;

	/**
	 * Constructor for the ExcelWriter object, writes to the
	 * folder in algo trading
	 * @throws IOException cause it might not find the document
	 */
	public ExcelWriter() throws IOException {
		File excelWb = new File("/home/jack/Documents/Spring2018/AlgoTrading/FinancialTemplate.xlsx");
		FileInputStream fis = new FileInputStream(excelWb);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheetAt(0);
		System.out.println("Object Succesfully Created");
	}
	
	/**
	 * Gets the last column for adding a new row for the 800 rows
	 * @return
	 */
	public int getLastRow() {
		return sheet.getLastRowNum() + 1;
	}
	
	/**
	 * Takes an array of values and adds all of the rows to its
	 * proper place in the excel file, it assumes that the array is in
	 * the correct order
	 * @param inputs is an array of strings basically
	 */
	public void writeToExcel(String[] inputs) {
		XSSFRow row = sheet.createRow(getLastRow());
		for(int i = 0; i < inputs.length; i++) {
			row.createCell(i).setCellValue(inputs[i]);
		}
		System.out.println("Finished setting row values");
	}
	
	/**
	 * Writes the inputed things to the file and closes it
	 * @throws IOException
	 */
	public void closeAndWrite() throws IOException {
		wb.write(new FileOutputStream("/home/jack/Documents/Spring2018/AlgoTrading/FinancialTemplate.xlsx"));
		wb.close();
	}
}

//This left for testing purposes
//public static void main(String[] args) throws IOException {
//ScrapForStockInfo scrap = new ScrapForStockInfo("JPM");
//scrap.getFinInfo();
//scrap.getStockCompetitors();
//ExcelWriter exceler = new ExcelWriter();
//exceler.writeToExcel(scrap.getArray());
//exceler.closeStuff();
//}
