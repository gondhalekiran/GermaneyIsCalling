package Com.GermaneyIsCalling.WebPages.UploadCV;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GICUploadCVPage {
	// Step1: Variable declaration
		@FindBy(xpath = "//input[@name='email']")
		private WebElement uNInp;
		@FindBy(xpath = "//input[@name='password']")
		private WebElement pWDInp;
		@FindBy(xpath = "//button[@type='submit']")
		private WebElement submitBtn;
		Actions act;

		// Step2: Variable initialization
		public GICUploadCVPage(WebDriver driver) {
			PageFactory.initElements(driver, this); // diffClassName.methodName(webdriverObject, this->Keyword);
			this.act = new Actions(driver);
		}
		
		public void helperGICLoginPageLogin(String username,String password) {
			uNInp.sendKeys(username);
			pWDInp.sendKeys(password);
			submitBtn.click();
		}
		
		// Step3: Variable usage
		public void inpGICLoginPageEmail(String username) {
			uNInp.sendKeys(username);
		}

		public void inpGICLoginPagePassword(String password) {
			pWDInp.sendKeys(password);
		}

		public void clickGICLoginPageLoginBtn() {
			submitBtn.click();
		}
}
