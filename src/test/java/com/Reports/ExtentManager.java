package com.Reports;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import LibraryFiles.BaseClass;

public class ExtentManager implements ITestListener {
	/**
	 * 1]windows ->preferance->general->network-->direct
	 * 2]windows->preferance->Maven->click checkbook of all download
	 */
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;
	String repName;

	public void onStart(ITestContext context) {
		extent = new ExtentReports();
		String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM.YYYY.HH.mm.ss"));
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + ".\\ExtentReports\\" + repName);
		sparkReporter.config().setDocumentTitle("Germaney Is Calling Automation Report");
		sparkReporter.config().setReportName("GIC Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Germaney Is Calling");
		extent.setSystemInfo("Module", "Login");
		extent.setSystemInfo("Use Case", "General User");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviroment", "QA");

	}

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.PASS, result.getMethod() + "got Successfully Executed");
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.PASS, result.getMethod() + "got Successfully Executed");
		printTestData(result.getParameters());
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.FAIL, result.getThrowable().getMessage() + "got fAILED!");
		printTestData(result.getParameters());
		try {
			/** make webdriver static */
			String path = new BaseClass().captureSS(result.getName());
			test.addScreenCaptureFromPath(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.SKIP, result.getMethod() + "got SKIPPed");
		test.log(Status.INFO, result.getThrowable().getMessage() + "got SKIPPed");
		printTestData(result.getParameters());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.FAIL, result.getMethod() + "got Successfully Executed");
		printTestData(result.getParameters());
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.log(Status.FAIL, result.getMethod() + "got Successfully Executed");
		printTestData(result.getParameters());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

	public void printTestData(Object[] parameters) {
		// Log test data from DataProvider
		if (parameters != null && parameters.length > 0) {
			StringBuilder data = new StringBuilder("Test Data: ");
			for (Object param : parameters) {
				data.append(param.toString()).append(" | ");
			}
			test.info(data.toString());
		}
	}

}
