package Utils;

import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import LibraryFiles.UtilityClass;

public class HelperMethods {
	public static LinkedHashMap<String, String> getHelperMethodsAlertMsgErrorMsg(WebDriver driver,
			FluentWait<WebDriver> flwait, WebElement submitBtn, WebElement sweetAlertMsg, WebElement errorMsg,
			List<WebElement> errorMsgLst) throws InterruptedException {
		LinkedHashMap<String, String> lmp = new LinkedHashMap<String, String>();
		lmp.put("AlertMsg", "0");
		lmp.put("Errors", "0");
		String msg = null;
		try {
			submitBtn.click();

			// Check for SweetAlert message
			flwait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(sweetAlertMsg),
					ExpectedConditions.visibilityOf(errorMsg)));
			msg = sweetAlertMsg.getText();
			UtilityClass.drawBorder(driver, sweetAlertMsg);
			lmp.put("AlertMsg", msg);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// Handle other exceptions
			try {
				UtilityClass.drawBorder(driver, errorMsg);
				lmp.put("Errors", String.valueOf(errorMsgLst.size()));
			} catch (org.openqa.selenium.NoSuchElementException ea) {
				// Handle other exceptions
				System.out.println("An unexpected error occurred:both are not displayed" + e.getMessage());
			}
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
			lmp.put("Unexpected Error", "both are not visible");
		}
		// Return the result
		return lmp;
	}
}
