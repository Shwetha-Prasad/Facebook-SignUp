package com.planit.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.planit.qa.base.TestBase;
import com.planit.qa.exceptions.ElementNotFoundException;
import com.planit.qa.exceptions.StringNullException;
import com.planit.qa.util.DriverWait;
import com.planit.qa.util.TestUtils;

public class ConfirmEmailPage extends TestBase{

	TestUtils testUtils = new TestUtils();
	DriverWait driverWait = new DriverWait();
	
	@FindBy(xpath="//h2[@class='accessible_elem' and text()='Confirm your email address']")
	public WebElement confirmEmailHeadingElement;

	@FindBy(xpath="//a[@title='Profile']")
	public WebElement profileIcon;

	@FindBy(linkText="Log Out")
	public WebElement logOut;

	public ConfirmEmailPage() {

		PageFactory.initElements(driver,this);
	}

	public String confirmEmailPageTitle() throws Throwable 
	{
		try {
			driverWait.waitForElement(confirmEmailHeadingElement);
				 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ElementNotFoundException("Page not loaded successfully");
		}
		return driver.getTitle();
	}

	public boolean profileIcon() throws ElementNotFoundException {
			try {
				driverWait.elementVisibility(profileIcon);
			} catch (Throwable e) {
				e.printStackTrace();
				throw new ElementNotFoundException("Profile Icon not found");
			}
			
		return profileIcon.isDisplayed();
	}

	public boolean confirmEmailMessage() {

		return confirmEmailHeadingElement.isDisplayed();
	}

	public String userName()
	{
		String profileName=null;
		try {
			profileName=profileIcon.getText().trim();
			if(profileName=="")
			{
				throw new StringNullException(profileName+" String is null");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profileName;
		
	}

	public AccountHomePage cickOnProfileIcon() throws ElementNotFoundException
	{
		try {
			profileIcon.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ElementNotFoundException("Profile Icon cannot be clicked");
		} 
		return new AccountHomePage();
	}
	
	public String titleAfterClickingOnProfileIcon() throws StringNullException, ElementNotFoundException
	{
		try {
			driverWait.waitForElement(logOut);
				 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ElementNotFoundException("Page not loaded successfully");
		}
		return driver.getTitle();
	}
	
	public SignUpPage logOutAfterProfileIconClick() throws ElementNotFoundException
	{
		try {
			logOut.click();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ElementNotFoundException("Unable to click Log out button");
		}
		return new SignUpPage();
	}
	
}
