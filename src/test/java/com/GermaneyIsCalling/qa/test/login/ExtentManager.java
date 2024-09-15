package com.GermaneyIsCalling.qa.test.login;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Com.GermaneyIsCalling.LibraryFiles.BaseClass;
import freemarker.template.SimpleDate;

public class ExtentManager implements ITestListener {
	ExtentSparkReporter sparkReporter;
	ExtentReports extent;
	ExtentTest test;
	String repName;

	public void onStart(ITestContext context) {
		extent = new ExtentReports();
		String timeStamp =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM.YYYY.HH.mm.ss"));
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter( System.getProperty("user.dir")+"\\ExtentReports\\" + repName);
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
	   test.log(Status.PASS,result.getMethod()+"got Successfully Executed");
	  }

	public void onTestSuccess(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());
		   test.log(Status.PASS,result.getMethod()+"got Successfully Executed");
	}

	public void onTestFailure(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());
		   test.log(Status.INFO,result.getThrowable().getMessage()+"got failed!");
		    try {
				String imgPath = new BaseClass().captureSS(result.getName());
				test.addScreenCaptureFromPath(imgPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void onTestSkipped(ITestResult result) {
		  test = extent.createTest(result.getTestClass().getName());
		   test.log(Status.SKIP,result.getMethod()+"got skipped");
		   test.log(Status.INFO,result.getThrowable().getMessage());
	}

//	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//		  test = extent.createTest(result.getTestClass().getName());
//		   test.log(Status.PASS,result.getMethod()+"got Successfully Executed");
//	}
//
//	public void onTestFailedWithTimeout(ITestResult result) {
//		  test = extent.createTest(result.getTestClass().getName());
//		   test.log(Status.PASS,result.getMethod()+"got Successfully Executed");
//	}

	public void onFinish(ITestContext context) {
		 extent.flush();
	}
}
