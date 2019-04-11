package com.planit.qa.util;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.planit.qa.base.TestBase;

public class DriverWait extends TestBase{
	
	private static WebDriverWait wait;
	
	public  DriverWait() {
		wait = new WebDriverWait(driver, TestUtils.WAIT_TIME);
	}
	
	public  void waitForElement(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		
	}
	public  void elementVisibility(WebElement element) throws Throwable {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public  void elementClickable(WebElement element) throws Throwable{
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public  boolean elementToAppear(WebElement element) throws Throwable{
		boolean webElementPresence = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			if (element.isDisplayed()) {
				webElementPresence = true;
			}
		} catch (Exception e) {
			System.out.println("Exception occured<br></br>" + e.getStackTrace());
		}
		return webElementPresence;
	}

}
