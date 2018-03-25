package com.tsys.san.bigbasket.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tsys.san.base.Base;
import com.tsys.san.util.DataUtil;
import com.tsys.san.util.Xls_Reader;


public class rough extends Base{
	static String testcase="AddProducts";
	 Xls_Reader xls;
	@BeforeMethod
	public void begin(){
		System.out.println("beginsss");
	}
	
	@Test(dataProvider="getdata")
	public void runtest(Hashtable<String,String> table){
		System.out.println("runningsss");
		
	}
	@DataProvider
	public Object[][] getdata() throws IOException{
		init();
		xls = new Xls_Reader(System.getProperty("user.dir") + prop.getProperty("excelpath"));
		return DataUtil.getTestData(xls, testcase);
	}

}
