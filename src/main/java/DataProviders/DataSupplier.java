package DataProviders;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataSupplier {
	String filepath = ".\\TestData\\GICExcel.xlsx";
	private static int startRow, endRow;
	private static String sheetName;

	public static void setSheetName(String shtName, int sr, int er) {
		sheetName = shtName;
		startRow = sr;
		endRow = er;
	}

	@DataProvider(name = "dataContainer")
	public String[][] getExcelData() throws IOException {
		return DPMethod.getSelectiveExcelData(filepath, sheetName, startRow, endRow);
	}

}
