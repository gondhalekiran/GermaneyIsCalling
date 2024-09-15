package Com.GermaneyIsCalling.LibraryFiles;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
public class BaseClass {

	public static WebDriver driver;

	public void initializeBrowser() throws IOException {

		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.get(UtilityClass.getPFData("URL"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
	}
	public String captureSS(String testName) throws IOException {
		  File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  String path = System.getProperty("user.dir")+".\\FailedTCScreenshot\\"+testName+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM.yyyy.HH.mm.ss"))+".png";
		 File dest=new File(path);
		 FileHandler.copy(src,dest);
		return path;
	}
}
