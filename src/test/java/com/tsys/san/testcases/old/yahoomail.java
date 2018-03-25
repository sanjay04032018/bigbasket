package com.tsys.san.testcases.old;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.base.Base;
import com.tsys.san.util.DataUtil;
import com.tsys.san.util.Xls_Reader;

public class yahoomail extends Base {
	static String testCaseName="Test D";
	SoftAssert assert1 = new SoftAssert();
	static Xls_Reader xls;
	
	@Test(dataProvider="getData")
	public void ymail(Hashtable<String,String> data) throws IOException{

		test.log(LogStatus.INFO,data.toString());
		test.log(LogStatus.INFO,"starting test d for yahoo");
		openBrowser("chrome");
		test.log(LogStatus.PASS, "browser opened"); 
		navigate("appurlyahoo");
		type("yahooemail_name",data.get("username"));
		click("yahoonext_name");
		type("yahoo_password_name",data.get("password"));
//		assert1.assertTrue(false,"asert has failed");
//		assert1.assertTrue(false,"asert2 has failed"); 
		reportfailure("yhkfsf");
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


