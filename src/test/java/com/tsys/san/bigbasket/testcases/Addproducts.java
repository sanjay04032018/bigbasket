package com.tsys.san.bigbasket.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.base.Base;
import com.tsys.san.util.DataUtil;
import com.tsys.san.util.Xls_Reader;

public class Addproducts extends Base {
	static String testcase="AddProducts";
	static Xls_Reader xls;
	SoftAssert softassert = new SoftAssert();

	@BeforeTest()
	public void Login() throws IOException{ 
		init();
		doLogin();
	}
	@Test(dataProvider="getdata")
	public void addItems(Hashtable<String,String> table){
		if(!DataUtil.isRunnable(testcase, xls) ||   table.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		String str = driver.findElement(By.id("city-drop-overlay")).getAttribute("style");
		System.out.println(str   + "  " + str.contains("none"));  
		if(!str.contains("none") )click("continue_button_xpath");

		type("searchText_id",table.get("Item"));
		click("search_button_xpath");

		type("addqty_xpath",table.get("Quantity"));
		click("addItem_xpath");
		wait(9);

	}
	@DataProvider
	public Object[][] getdata() throws IOException{
		init();
		xls = new Xls_Reader(System.getProperty("user.dir") + prop.getProperty("excelpath"));
		return DataUtil.getTestData(xls, testcase);
	}
	@AfterTest
	public void quit(){
		try {
			softassert.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("quit called");
		EmptyBasket();
		rep.endTest(test);
		rep.flush();
		driver.quit();
		login=false;
	}
	
	public void EmptyBasket(){
		Actions actions = new Actions(driver);
		WebElement elem = driver.findElement(By.xpath("//a[@qa='myBasket']"));
		actions.moveToElement(elem).build().perform();
	    wait(2);
		click("viewbasket_xpath");
		click("emptyBasketBSKT_xpath");
	//	acceptAlert();
		driver.findElement(By.id("ok_button")).click();
		
	}

}
