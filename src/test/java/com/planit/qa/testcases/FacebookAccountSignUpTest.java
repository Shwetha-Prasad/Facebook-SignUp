package com.planit.qa.testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.planit.qa.base.TestBase;
import com.planit.qa.exceptions.DataNotFoundException;
import com.planit.qa.exceptions.ElementNotFoundException;
import com.planit.qa.exceptions.StringNullException;
import com.planit.qa.pages.SignUpPage;
import com.planit.qa.util.DriverWait;
import com.planit.qa.util.TestUtils;
import com.planit.qa.pages.AccountHomePage;
import com.planit.qa.pages.ConfirmEmailPage;

public class FacebookAccountSignUpTest extends TestBase {
	
	SignUpPage signUpPage;
	ConfirmEmailPage confirmEmailPage;
	AccountHomePage accountHomePage;
	DriverWait driverWait;
	
	public FacebookAccountSignUpTest(){
		
		super();
	}

	@BeforeSuite
	public void setUp(){
		initialization();
		signUpPage = new SignUpPage();
		confirmEmailPage = new ConfirmEmailPage();
		accountHomePage= new AccountHomePage();
		driverWait = new DriverWait();
	}
	@Test(priority = 1)
	public void validatePageAfterLaunchingURL(){
		
		String title = signUpPage.signUpPageTitle();
		Assert.assertEquals(title, "Facebook – log in or sign up");
		
	}
	
	@Test(priority = 2)
	public void validateCreateAccountHeading(){

		String heading = signUpPage.createAccountHeading();
		Assert.assertEquals(heading, "Create an account");
		
	}
	
	@Test(priority = 3)
	public void validateSuccessfulFormEntry() throws DataNotFoundException, IOException, ElementNotFoundException{
		
		confirmEmailPage=signUpPage.formDetailsToCreateAccount();
		String homePageTitleString = null;
		try {
			homePageTitleString = confirmEmailPage.confirmEmailPageTitle();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Assert.assertEquals(homePageTitleString,"Facebook");
		
		boolean profileIconDisplayed = confirmEmailPage.profileIcon();
		Assert.assertTrue(profileIconDisplayed,"Profile Icon is not displayed");
	}
	
	@Test(priority =4,dependsOnMethods ="validateSuccessfulFormEntry")
	public void validateUserAccountCreated() throws DataNotFoundException, IOException
	{
		String userFirstNameFromData=SignUpPage.firstNameData;
		String userNameFromProfileIcon = confirmEmailPage.userName();
		Assert.assertEquals(userFirstNameFromData,userNameFromProfileIcon);
		
	}
	@Test(priority =5,dependsOnMethods ="validateSuccessfulFormEntry")
	public void validateAfterProfileIconClick() throws DataNotFoundException, IOException, StringNullException, ElementNotFoundException {
		
		accountHomePage = confirmEmailPage.cickOnProfileIcon();

		String title=confirmEmailPage.titleAfterClickingOnProfileIcon();
		if(title.equals("Facebook"))
		{
			confirmEmailPage.logOutAfterProfileIconClick();
			driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			Assert.assertEquals(driver.getTitle(), "Facebook – log in or sign up");
		}
		else {
		
		String userFirstNameFromData=SignUpPage.firstNameData;
		String userSurnameFromData=SignUpPage.firstNameData;
		String accountUserName = userFirstNameFromData + " "+ userSurnameFromData;
		
		String homePageTitle = accountHomePage.homePageTitle();
		Assert.assertEquals(homePageTitle,accountUserName);
		
		String userNameOnTimeLine=accountHomePage.accountUserNameOnTimeLine();
		Assert.assertEquals(userNameOnTimeLine,accountUserName);
		}
	}
	@Test(priority=6, dependsOnMethods ="validateAfterProfileIconClick")
	public void validateLogOut() throws ElementNotFoundException{
		
		if(!driver.getTitle().contains("sign up"))
		{
		boolean logOutButtonFound=accountHomePage.clickLogOutArrow();
		Assert.assertTrue(logOutButtonFound,"Log Out not found");
		
		String confirmLogOutMessage = accountHomePage.confirmLogOutMessage();
		Assert.assertEquals(confirmLogOutMessage,"Log out of Facebook?");
		
		signUpPage = accountHomePage.cliclLogOutButton();
		
		String title = signUpPage.signUpPageTitle();
		Assert.assertEquals(title, "Facebook – log in or sign up");
		
		String heading = signUpPage.createAccountHeading();
		Assert.assertEquals(heading, "Create an account");
		
		}
	}
	
	@AfterSuite
	public void tearDown()
	{
		driver.quit();
		cleanUpBeforeExit();

	}
}
