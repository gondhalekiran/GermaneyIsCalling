package Utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class waitUtils {
	public static void waitForAjax(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(driver1 -> {
			JavascriptExecutor js = (JavascriptExecutor) driver1;
			// Return true if no active AJAX requests are found
			return (Boolean) js.executeScript("return jQuery.active == 0");
		});
	}

	public static void waitForElementToBeClickable(WebDriver driver, WebElement ele, int timeInSecond) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSecond));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
}
