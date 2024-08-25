package DataProviders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataSupplier {
	String filepath = ".\\TestData\\GICExcel.xlsx";
	private static int startRow, endRow ; 
	private static String sheetName;

	public static void setSheetName(String shtName, int sr, int er) {
		sheetName = shtName;
		startRow = sr;
		endRow = er;
	}

	@DataProvider(name = "dataContainer")
	public String[][] getExcelData() throws IOException {
		return getSelectiveExcelData();
	}

	public String[][] getSelectiveExcelData() throws IOException {
		FileInputStream file = new FileInputStream(filepath);
		XSSFWorkbook Workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = Workbook.getSheet(sheetName);
		int row = endRow - startRow + 1;
		// int row= sheet.getPhysicalNumberOfRows();
		// System.out.println(row);
		int col = sheet.getRow(0).getLastCellNum();
		// System.out.println(col);
		String[][] data = new String[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				DataFormatter df = new DataFormatter();
				data[i][j] = df.formatCellValue(sheet.getRow(i + startRow + 1).getCell(j));

			}
			System.out.println();
		}
//		for (String[] s1 : data) {
//			System.out.println(Arrays.toString(s1));
//		}

		Workbook.close();
		file.close();
		return data;
	}

	public String[][] getAllExcelData() throws IOException {
		FileInputStream file = new FileInputStream(filepath);
		XSSFWorkbook Workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = Workbook.getSheet(sheetName);

		int row = sheet.getPhysicalNumberOfRows();
		// System.out.println(row);
		int col = sheet.getRow(0).getLastCellNum();
		// System.out.println(col);
		String[][] data = new String[row - 2][col];
		for (int i = 0; i < row - 2; i++) {
			for (int j = 0; j < col; j++) {
				DataFormatter df = new DataFormatter();
				data[i][j] = df.formatCellValue(sheet.getRow(i + 2).getCell(j));
			}
			System.out.println();
		}
		Workbook.close();
		file.close();
		return data;
	}
}
