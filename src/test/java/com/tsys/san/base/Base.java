package com.tsys.san.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tsys.san.util.ExtentManager;

public class Base {

	public WebDriver driver ;
	public static Properties prop;
	public static ExtentReports rep ;
	public static ExtentTest test;
	boolean gridrun=false;
	public static boolean login=false;

	public static void init() throws IOException{
		if(prop==null){
			prop=new Properties();
			FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "//src/test//resources//bigbasketconfig.properties");
			prop.load(input); 
			String env =prop.getProperty("env");
			FileInputStream inputenv = new FileInputStream(System.getProperty("user.dir")+ "//src/test//resources//"+ env +".properties");
			prop.load(inputenv); //  we can load multiple property file in one Properties object
			rep = ExtentManager.getInstance();  
			test =	rep.startTest("start test");

			//			for(Object key :prop.keySet()){
			//				System.out.print(key);
			//				System.out.println ("    "  + prop.get(key));
			//			}

		}
	}


	public void openBrowser(String browsertype) throws IOException{
		if(!gridrun){
			if(browsertype.equals("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("chromedriver_exe"));
				driver = new ChromeDriver();
			}
			else if(browsertype.equals("ff")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + prop.getProperty("chromedriver_exe"));
				driver = new FirefoxDriver();
			}
		}
		else{
			DesiredCapabilities cap=null;
			if(browsertype.equals("Mozilla")){
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);

			}else if(browsertype.equals("Chrome")){
				cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser " + browsertype + "has opened" );  
	}	



	public void navigate(String urlkey){
		test.log(LogStatus.INFO, "navigated to " +prop.getProperty(urlkey) ); 
		driver.get(prop.getProperty(urlkey)); 
	}

	public void click(String elemkey){
		test.log(LogStatus.INFO, "clicking to " +prop.getProperty(elemkey) ); 
		getElement(elemkey).click();
	}

	public void type(String elemkey,String data){
		getElement(elemkey).clear();
		test.log(LogStatus.INFO, "Tying in "+elemkey+". Data - "+data);
		getElement(elemkey).sendKeys(data);   
	}

	public WebElement getElement(String locatorkey) {
		try {
			if(locatorkey.endsWith("xpath")){
				return 	driver.findElement(By.xpath(prop.getProperty(locatorkey))) ;
			}
			else if(locatorkey.endsWith("id")){
				return 	driver.findElement(By.id(prop.getProperty(locatorkey))) ;
			}
			else if(locatorkey.endsWith("name")){ 
				return 	driver.findElement(By.name(prop.getProperty(locatorkey))) ;
			}
			else {
				reportfailure("Locator not correct - " + locatorkey);
				Assert.fail("Locator not correct - " + locatorkey);
			}
		} catch (Exception e) {
			reportfailure("locator not correctly defined");
			e.printStackTrace();
			Assert.fail();
		}
		return null;

	}
	public boolean  isElementPresent(String locatorkey){
		List<WebElement> elemlist =null ;

		if(locatorkey.endsWith("xpath")){
			elemlist = driver.findElements(By.xpath(prop.getProperty(locatorkey))) ;

		}
		else if(locatorkey.endsWith("id")){
			elemlist = driver.findElements(By.id(prop.getProperty(locatorkey))) ;

		}else if(locatorkey.endsWith("name")){
			elemlist = driver.findElements(By.name(prop.getProperty(locatorkey))) ;

		}else {
			reportfailure("");
			Assert.fail();
		}
		if(elemlist.size()==0){
			System.out.println("elemlist.size() is" + elemlist.size());
			return false;	
		}else
			return true;

	}

	public boolean verifyText(String locatorkey,String ExpectedText ){

		String ActualText =  getElement(locatorkey).getText();
		//	String ExpectedText = prop.getProperty(ExpectedTextkey);
		System.out.println( "ActualText " + ActualText    +  "   " + ExpectedText);
		if(ActualText.equals(ExpectedText))
			return true;
		else{
			reportfailure("user name not matching"); 
			return false;

		}
	}

	public void acceptAlert(){
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.alertIsPresent());
		test.log(LogStatus.INFO,"Accepting alert");
		driver.switchTo().alert().accept();
		driver.switchTo().defaultContent();
	}

	public void waitforpagetoload() {
		wait(1);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String state = (String) js.executeScript("return document.readystate");
		while(state!="complete"){
			state = (String) js.executeScript("return document.readystate");
			System.out.println(state);
			wait(2);
		}
	}

	public void wait(int i) {
		try {
			Thread.sleep(i *1000);
		} catch (InterruptedException e) {
			//	e.printStackTrace();
		}
	}

	public String getText(String locatorkey){
		test.log(LogStatus.INFO, "Getting text from "+locatorkey);
		return	getElement(locatorkey).getText();
	}


	///////////////////////REPORTING FUNCTION///////////////

	public void reportfailure(String msg) {
		test.log(LogStatus.FAIL, msg); 
		takeScreenShot();
		Assert.fail(msg);
	}

	public  void reportpass(String string)	{
		test.log(LogStatus.PASS, string); 
	}
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		//put screenshot file in reports
	}
	/// app specific functions
	public void doLogin() throws IOException{ 
		if(!login)
		{
			test.log(LogStatus.INFO,"logging into bigbasket ");
			openBrowser("chrome"); 
			test.log(LogStatus.INFO, "browser opened"); 
			navigate("appurl");
			click("loginlink_xpath");
			type("username_name",prop.get("Loginname").toString());
			type("password_name",prop.get("password").toString());
			click("email_next_xpath");
			verifyText("Logger_xpath",prop.get("Text").toString());
			test.log(LogStatus.INFO, "test case passed");
			login=true;
		}
	}


}
