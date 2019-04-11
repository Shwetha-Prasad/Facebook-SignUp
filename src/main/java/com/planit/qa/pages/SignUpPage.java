package com.planit.qa.pages;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.planit.qa.base.TestBase;
import com.planit.qa.exceptions.DataNotFoundException;
import com.planit.qa.util.DriverWait;
import com.planit.qa.util.ExcelUtils;
import com.planit.qa.util.StringUtils;
import com.planit.qa.util.TestUtils;

public class SignUpPage extends TestBase {

	ExcelUtils excelUtils = new ExcelUtils();
	TestUtils testUtils = new TestUtils();
	StringUtils stringUtils = new StringUtils();
	DriverWait driverWait = new DriverWait();
	public static String firstNameData;
	public static String surnameData;

	@FindBy(xpath = "//span[text()='Create an account']")
	public WebElement createAccountHeading;

	@FindBy(name = "firstname")
	public WebElement firstName;

	@FindBy(name = "lastname")
	public WebElement surName;

	@FindBy(name = "reg_email__")
	public WebElement emailAddress;

	@FindBy(name = "reg_email_confirmation__")
	public WebElement reEnterEmail;

	@FindBy(name = "reg_passwd__")
	public WebElement password;

	@FindBy(xpath = "//select[@id='day' and @title='Day']")
	public WebElement birthDay;

	@FindBy(xpath = "//select[@id='month' and @title='Month']")
	public WebElement birthMonth;

	@FindBy(xpath = "//select[@id='year' and @title='Year']")
	public WebElement birthYear;

	@FindBy(xpath = "//input[@type='radio'and @value='1']")
	public WebElement female;

	@FindBy(xpath = "//input[@type='radio'and @value='2']")
	public WebElement male;

	@FindBy(xpath = "//button[@type='submit' and @name='websubmit']")
	public WebElement signUpButton;

	public SignUpPage() {
		PageFactory.initElements(driver, this);
	}

	public String signUpPageTitle() {
		return driver.getTitle();
	}

	public String createAccountHeading() {

		return createAccountHeading.getText();
	}

	public ArrayList<String> readDataFromExcel() throws DataNotFoundException, IOException {
		ArrayList<String> data = excelUtils.getTestDataBasedOnTestCase("SignUp", "FacebookAccountSignUpTest");
		return data;
	}

	public ConfirmEmailPage formDetailsToCreateAccount()
			throws NumberFormatException, DataNotFoundException, IOException {

		try {

			ArrayList<String> data = readDataFromExcel();

			firstNameData = data.get(1);

			surnameData = data.get(2);

			firstName.clear();
			firstName.sendKeys(firstNameData);

			surName.clear();
			surName.sendKeys(surnameData);
			
			String randomEmailCharacter = stringUtils.randomEmailGenerator(4);

			String emailId = firstNameData.toLowerCase()+ "." + surnameData.toLowerCase() + randomEmailCharacter + "@gmail.com";

			emailAddress.sendKeys(emailId);
			
			try {
				driverWait.elementVisibility(reEnterEmail);
				reEnterEmail.sendKeys(emailId);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
			String randomPassword = stringUtils.randomPasswordGenerator(7);

			password.sendKeys(randomPassword);

			String day = data.get(3);
			int dayValue = Integer.parseInt(day);
			if (!((dayValue == 0) && (dayValue > 31)))

				testUtils.selectByText(birthDay, day);

			String month = data.get(4);
			testUtils.selectByText(birthMonth, month);

			String year = data.get(5);
			testUtils.selectByText(birthYear, year);

			if (data.get(6).equals("Female")) {
				female.click();
			} else {
				male.click();
			}

			if (signUpButton.isEnabled()) {

				signUpButton.click();
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return new ConfirmEmailPage();

	}

	
}
