package LibraryFiles;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;

public class BaseClass {

	public static WebDriver driver;

	public void initializeBrowser() throws IOException {

		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		// Cast the WebDriver instance to JavascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Set the zoom level to 100% using JavaScript
		js.executeScript("document.body.style.zoom='100%'");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.get(UtilityClass.getPFData("URL"));
	}

	public String captureSS(String testName) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + ".\\FailedTCScreenshot\\" + testName + "_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM.yyyy.HH.mm.ss")) + ".png";
		File dest = new File(path);
		FileHandler.copy(src, dest);
		return path;
	}

	@AfterClass
	public void closeBrowser() {
		// driver.close();
	}
}
