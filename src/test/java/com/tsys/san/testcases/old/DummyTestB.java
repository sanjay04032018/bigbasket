package com.tsys.san.testcases.old;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.base.Base;
import com.tsys.san.util.DataUtil;
import com.tsys.san.util.ExtentManager;
import com.tsys.san.util.Xls_Reader;

public class DummyTestB extends Base {
	static String testCaseName="LoginTest";
	SoftAssert assert1 = new SoftAssert();
	static Xls_Reader xls;

	//	@BeforeMethod
	//	public void inits() throws IOException{
	//		init();
	//	}

	/**
	 * @throws IOException
	 */
	@Test(dataProvider="getData")
	public void TestB(Hashtable<String,String> data) throws IOException{
		System.out.println(" data.get " + data.get("Runmode")); 
	

		test.log(LogStatus.INFO,data.toString());
		test.log(LogStatus.INFO, "starting " + testCaseName);
		openBrowser("chrome");
		test.log(LogStatus.PASS, "browser opened"); 
		navigate("appurl");
		type("email_xpath",data.get("username"));
		click("email_next_xpath");
		type("password_name",data.get("password"));
		assert1.assertTrue(false,"asert has failed");
		assert1.assertTrue(false,"asert2 has failed"); 
		//	reportfailure("my issue");
		}
	
	@AfterMethod
	public void quit(){
		try {
			assert1.assertAll();
		} catch (Error e) {
			//	e.printStackTrace();
		}
		System.out.println(rep);
		rep.endTest(test);
		rep.flush();
		driver.quit();
	}
	@DataProvider
	public static Object getData()[][] throws IOException {
	init();
		xls = new Xls_Reader(System.getProperty("user.dir") + prop.getProperty("excelpath"));
		return DataUtil.getTestData(xls, testCaseName);

	}

}
