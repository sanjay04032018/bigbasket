package com.tsys.san.testcases.old;

import org.junit.Assert;
import org.testng.annotations.Test;

public class DummyTestA {
	
	
	@Test(priority=1)
	public void TestA1(){
		Assert.fail();
		
	}
	@Test(priority=2,dependsOnMethods={"TestA1"})
	public void TestA2(){
		
	}
	@Test(priority=3,dependsOnMethods={"TestA1","TestA2"})
	public void TestA3(){
		
	}

}
