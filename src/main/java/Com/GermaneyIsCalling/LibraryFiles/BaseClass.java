package Com.GermaneyIsCalling.LibraryFiles;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseClass {

	public WebDriver driver;

	public void initializeBrowser() throws IOException {

		// driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.get(UtilityClass.getPFData("URL"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
	}
}
