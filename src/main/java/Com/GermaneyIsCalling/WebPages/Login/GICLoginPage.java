package Com.GermaneyIsCalling.WebPages.Login;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

import LibraryFiles.UtilityClass;
import Utils.HelperMethods;

public class GICLoginPage {
	// Step1: Variable declaration
	@FindBy(xpath = "//input[@name='username']")
	private WebElement uNInp;
	@FindBy(xpath = "//input[@name='password']")
	private WebElement pWDInp;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitBtn;
	@FindBy(xpath = "//div[@class='alert alert-danger']/descendant::li")
	private List<WebElement> errorMsgLst;
	@FindBy(xpath = "//div[@class='alert alert-danger']/descendant::li")
	private WebElement errorMsg;
	@FindBy(xpath = "//div[@class='alert alert-danger']/descendant::li")
	private WebElement sweetAlertMsg;

	Actions act;
	ArrayList<String> al;
	FluentWait<WebDriver> flwait;

	// Step2: Variable initialization
	public GICLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this); // diffClassName.methodName(webdriverObject, this->Keyword);
		this.act = new Actions(driver);
		this.al = new ArrayList<String>();
		this.flwait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(15))
				.pollingEvery(Duration.ofMillis(25)).ignoring(NoSuchElementException.class);
	}

	public void helperGICLoginPageLogin(String username, String password) {
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

	public List<String> getGICLoginPageErrorMsgLst(WebDriver driver) {
		for (WebElement wb : errorMsgLst) {
			UtilityClass.drawBorder(driver, wb);
			al.add(wb.getText());
		}
		return al;
	}

	public String getGICLoginPageErrorMsg(WebDriver driver) {
		UtilityClass.drawBorder(driver, errorMsg);
		return errorMsg.getText();
	}

	public LinkedHashMap<String, String> getGICLoginPageAlertMsgErrorMsg(WebDriver driver) throws InterruptedException {
		return HelperMethods.getHelperMethodsAlertMsgErrorMsg(driver, flwait, submitBtn, sweetAlertMsg, errorMsg,
				errorMsgLst);
	}
}
