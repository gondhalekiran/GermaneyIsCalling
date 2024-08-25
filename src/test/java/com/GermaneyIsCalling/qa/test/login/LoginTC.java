package com.GermaneyIsCalling.qa.test.login;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Com.GermaneyIsCalling.LibraryFiles.BaseClass;
import Com.GermaneyIsCalling.LibraryFiles.UtilityClass;
import Com.GermaneyIsCalling.WebPages.Login.GICLoginPage;
import Com.GermaneyIsCalling.WebPages.SideMenuBar.GICSideMenubar;
import DataProviders.DataSupplier;
import net.bytebuddy.utility.RandomString;

public class LoginTC extends BaseClass {
	GICLoginPage loginPage;
	GICSideMenubar sideMenu;

	String TCID;
	SoftAssert soft;
	Logger log = LogManager.getLogger(LoginTC.class);
	ExtentReports extent;
	public String sheetName;

	@BeforeClass
	public void openBrowser() throws EncryptedDocumentException, IOException {
		sheetName = "LoginFunctional"; // LoginFunctional,LoginUnit
		DataSupplier.setSheetName(sheetName, 1, 6);

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
		extent.attachReporter(spark);
	}

	@BeforeMethod
	public void loginToApp() throws InterruptedException, IOException {
		// for reset soft object for each itertion of @Test
		initializeBrowser();
		loginPage = new GICLoginPage(driver);
		sideMenu = new GICSideMenubar(driver);
		soft = new SoftAssert();

	}

	@Test(enabled = true, priority = 1, groups = "Unit", dataProvider = "dataContainer", dataProviderClass = DataSupplier.class)
	public void loginTest(String Scenario, String error, String name, String eMail, String password, String toastMsg)
			throws IOException, InterruptedException {

		extent.createTest("SubmitEnquiryForm").log(Status.PASS,
				"This is a logging event for MyFirstTest, and it passed!");

		TCID = RandomString.make(2); // ab cd a1 a5 s4
		// AddProject page method call
		log.info("Filling Form");
		// Login page method call
		log.info("Signing In..");
		loginPage.inpGICLoginPageEmail(eMail);
		loginPage.inpGICLoginPagePassword(password);
		Thread.sleep(2000);
		loginPage.clickGICLoginPageLoginBtn();
		log.info("Login succcess");
		Thread.sleep(200);

		if (Scenario.equals("BothTrue")) {
			Reporter.log(Scenario + sideMenu.getGICSideMenubarUserName(driver, name) + "<==>" + name, true);
			soft.assertEquals(sideMenu.getGICSideMenubarUserName(driver, name), name);
		}

		else if (Scenario.equals("BothFalse")) {
			Reporter.log(Scenario + loginPage.getGICLoginPageErrorMsgLst(driver).toString() + "<==>" + toastMsg, true);
			soft.assertEquals(loginPage.getGICLoginPageErrorMsgLst(driver), toastMsg);
		} else {
			Reporter.log(Scenario + loginPage.getGICLoginPageErrorMsg(driver)+ "<==>" + toastMsg, true);
			soft.assertEquals(loginPage.getGICLoginPageErrorMsg(driver), toastMsg);
		}
		soft.assertAll();
	}

	@AfterMethod
	public void logoutFromApp(ITestResult s1, Method m) throws IOException, InterruptedException {
		// Get the current date and time

		LocalDateTime date = LocalDateTime.now();

		// Define the format pattern
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyyHHmm");

		// Format the date
		String formattedDate = date.format(formatter);

		// Output the formatted date
		System.out.println("Formatted Date: " + formattedDate);

		if (s1.getStatus() == ITestResult.FAILURE) {
			Thread.sleep(5000);
			UtilityClass.captureSS(driver, TCID + m.getName() + formattedDate); // code to capture SS
		}
		// sideMenu.getGICSideMenubarLogoutBtn();
	}

	@AfterClass
	public void closeBrowser() {
		
		// driver.close();
	}

}
