package Com.GermaneyIsCalling.LibraryFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

public class UtilityClass {
	// this method is use to get login credentials as value from property file
	// need to pass 1 inputs: key
	public static String getPFData(String key) throws IOException {
		FileInputStream file = new FileInputStream(
				".\\PropertyFile.properties");// property
																											// file path
		Properties p = new Properties();
		p.load(file);
		String value = p.getProperty(key);

		return value;
	}

	// this method is use to get the test data from excel sheet
	// need to pass 2 inputs: 1.rowIndex 2.colIndex
	public static String getTD(int rowIndex, int colIndex) throws IOException {
		FileInputStream file = new FileInputStream(
				".\\TestData\\GICExcel.xlsx");
		Sheet sh = WorkbookFactory.create(file).getSheet("Sheet1");
		String value = sh.getRow(rowIndex).getCell(colIndex).getStringCellValue();

		return value;
	}

	// this method is use to draw red border to indicate WebElement which is under
	// testing
	public static void drawBorder(WebDriver driver, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// this method is use to get screenshot of pass/fail TC as per requirement and
	// store it in desired folder
	public static void captureSS(WebDriver driver, String TCID) throws IOException {
	

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File(
				".\\FailedTCScreenshot\\TestCaseID" + TCID
						+ ".png");
		FileHandler.copy(src, dest);
	}

}
