package com.planit.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.planit.qa.base.TestBase;
import com.planit.qa.exceptions.ElementNotFoundException;

public class AccountHomePage extends TestBase{
	
	@FindBy(xpath="//span[@id='fb-timeline-cover-name']/a")
	public WebElement userNameOnTimeLine;
	
	@FindBy(xpath="//a[@id='pageLoginAnchor' and @role='button']")
	public WebElement navigationArrow;
	
	@FindBy(xpath="//div[@class='uiContextualLayer _5v-0 _53il uiContextualLayerBelowRight']/descendant::ul")
	public WebElement logOutDropDown;
	
	//div[@class='uiContextualLayer _5v-0 _53il uiContextualLayerBelowRight']/descendant::form/ancestor::a
	
	@FindBy(xpath="//h3[text()='Log out of Facebook?']")
	public WebElement logOutMessage;
	
	@FindBy(xpath="//button[text()='Log Out' and @type='submit']")
	public WebElement logOutButton;
	
	
public AccountHomePage() {
		
		PageFactory.initElements(driver,this);
	}

public String homePageTitle()
{
	return driver.getTitle();
}

public String accountUserNameOnTimeLine()
{
	return userNameOnTimeLine.getText().trim();
}

public boolean clickLogOutArrow() throws ElementNotFoundException
{
	boolean logOutFound= false;
	try {
		navigationArrow.click();
		List<WebElement> list = logOutDropDown.findElements(By.tagName("li"));
		for(WebElement listItem:list)
		{
			System.out.println(listItem.getText());
			if(listItem.getText().trim().contains("Log Out")) {
				logOutFound= true;
				listItem.findElement(By.xpath("/a")).click();
			    break;
			}
			else {
				throw new ElementNotFoundException("Element not found");
			}
		}
	}catch (NoSuchElementException e) {
		e.printStackTrace();
		throw new NoSuchElementException("Log Out button not found");
	}
	return logOutFound;
	
}

public String confirmLogOutMessage()
{
	String logOutConfirmationMessage =logOutMessage.getText().trim();
	return logOutConfirmationMessage;
	
}

public SignUpPage cliclLogOutButton()
{
	try {
		logOutButton.click();
	} catch (NoSuchElementException e) {
		e.printStackTrace();
		throw new NoSuchElementException("Log Out button not found");
	}
	return new SignUpPage();
}







}
