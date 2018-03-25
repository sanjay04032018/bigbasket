package com.tsys.san.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.base.Base;

public class Listeners extends Base implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getName()+"listeners");
		
	}

	public void onTestFailure(ITestResult result) {
		test.log(LogStatus.FAIL, result.getName() +"listeners");
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	

}
