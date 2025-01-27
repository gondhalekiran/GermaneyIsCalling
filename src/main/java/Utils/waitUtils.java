package Utils;

public class waitUtils {
public static void waitForAjax(WebDriver driver) {     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));     wait.until(driver1 -> {       JavascriptExecutor js = (JavascriptExecutor) driver1;       //Return true if no active AJAX requests are found       return (Boolean) js.executeScript("return jQuery.active == 0");     });   }
}
