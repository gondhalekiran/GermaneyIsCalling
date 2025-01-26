package com.GermaneyIsCalling.qa.test.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//To see ITestListener interface abstract method press CTRL+click ITestListener in class implements statement (public class ExtentReportManager implements ITestListener)
//implement all method of ITestListener interface by making them public
public class ExtentReportManager implements ITestListener {
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;
	String repName;

	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.sss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + ".//Reports//" + repName);
		sparkReporter.config().setDocumentTitle("Germaney Is Calling Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Germaney Is Calling");
		extent.setSystemInfo("Module", "Login");
		extent.setSystemInfo("UserName", System.getProperty("user.name"));
		extent.setSystemInfo("Enviorment", "QA");
		extent.setSystemInfo("OS", "Windows11");
		extent.setSystemInfo("Browser", "Chrome");
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
//		if (!includedGroups.isEmpty()) {
//			extent.setSystemInfo("Groups", includedGroups.toString());
//		}
	}

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getName() + "got Passed");
		test.log(Status.INFO, result.getName());
	}

	public void onTestFailure(ITestResult result) {
	}

	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, result.getName() + "got Skipped");
		test.log(Status.INFO, result.getName());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
