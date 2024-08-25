package Com.GermaneyIsCalling.WebPages.Login;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Com.GermaneyIsCalling.LibraryFiles.UtilityClass;

public class GICLoginPage {
	// Step1: Variable declaration
	@FindBy(xpath = "//input[@name='username']")
	private WebElement uNInp;
	@FindBy(xpath = "//input[@name='password']")
	private WebElement pWDInp;
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitBtn;
	@FindBy(xpath = "//div[@class='alert alert-danger']/descendant::li")
	private List<WebElement> ErrorMsgLst;
	@FindBy(xpath = "//div[@class='alert alert-danger']/descendant::li")
	private WebElement ErrorMsg;
	Actions act;
	ArrayList<String> al;

	// Step2: Variable initialization
	public GICLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this); // diffClassName.methodName(webdriverObject, this->Keyword);
		this.act = new Actions(driver);
		this.al = new ArrayList<String>();
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
		for (WebElement wb : ErrorMsgLst) {
			UtilityClass.drawBorder(driver, wb);
			al.add(wb.getText());
		}
		return al;
	}

	public String getGICLoginPageErrorMsg(WebDriver driver) {
		UtilityClass.drawBorder(driver, ErrorMsg);
		return ErrorMsg.getText();
	}
}
