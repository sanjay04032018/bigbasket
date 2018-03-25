package com.tsys.san.bigbasket.testcases;
import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.base.Base;
import com.tsys.san.util.DataUtil;
import com.tsys.san.util.Xls_Reader;

public class LoginTest extends Base {
	static String testCaseName="LoginTest";
	SoftAssert softassert = new SoftAssert();
	 Xls_Reader xls;

	@Test(dataProvider="getdata")
	public void LoginTests(Hashtable<String,String> table) throws IOException{
		if(!DataUtil.isRunnable(testCaseName, xls) ||  table.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
			test.log(LogStatus.INFO,"logging into bigbasket ");
			openBrowser("chrome"); 
			test.log(LogStatus.INFO, "browser opened"); 
			navigate("appurl");
			click("loginlink_xpath");
			type("username_name",table.get("Loginname"));
			type("password_name",table.get("password"));
			click("email_next_xpath");
			verifyText("Logger_xpath",table.get("Text"));
			test.log(LogStatus.INFO, "test case passed");
		}
	
	@AfterMethod
	public void quit(){
		try {
			softassert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		rep.endTest(test);
		rep.flush();
		driver.quit();
		login=false;
	}
	@DataProvider
	public Object[][] getdata() throws IOException{
		init();
		xls = new Xls_Reader(System.getProperty("user.dir") + prop.getProperty("excelpath"));
		return DataUtil.getTestData(xls, testCaseName);
	}
}
