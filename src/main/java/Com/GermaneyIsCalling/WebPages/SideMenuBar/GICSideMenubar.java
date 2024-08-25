package Com.GermaneyIsCalling.WebPages.SideMenuBar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Com.GermaneyIsCalling.LibraryFiles.UtilityClass;

public class GICSideMenubar {
	// Step1: Variable declaration
	@FindBy(xpath = "//span[text()='CV analysis']")
	private WebElement cvAnalysisBtn;
	@FindBy(xpath = "//a[@id='dropdownUser1']")
	private WebElement expDropdown;
	@FindBy(xpath = "//a/span/i")
	private WebElement LogoutBtn;
	Actions act;

	// Step2: Variable initialization
	public GICSideMenubar(WebDriver driver) {
		PageFactory.initElements(driver, this); // diffClassName.methodName(webdriverObject, this->Keyword);
		this.act = new Actions(driver);
	}

	// Step3: Variable usage
	public void clickGICSideMenubarCVAnalysisBtnBtn() {
		cvAnalysisBtn.click();
	}

	public String getGICSideMenubarUserName(WebDriver driver, String name) {
		WebElement wb = driver.findElement(By.xpath("//span[text()=\'" + name + "\']"));
		UtilityClass.drawBorder(driver, wb);
		return wb.getText();
	}
	public void getGICSideMenubarLogoutBtn() throws InterruptedException {
		expDropdown.click();
		Thread.sleep(500);
		LogoutBtn.click();
		
	}
}
