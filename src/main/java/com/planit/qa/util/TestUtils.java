package com.planit.qa.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.planit.qa.base.TestBase;

public class TestUtils extends TestBase{ 


	public static long PAGE_LOAD_TIMEOUT = 10;
	public static long IMPLICIT_WAIT =10;
	public static long WAIT_TIME= 10;

	public void selectByText(WebElement element,String textToBeSelected)
	{
		try {
			Select select = new Select(element);
			select.selectByVisibleText(textToBeSelected);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		if (System.getProperty("os.name").contains("Windows"))
			FileUtils.copyFile(scrFile, new File(currentDir + "\\Screenshots\\" + System.currentTimeMillis() + ".jpg"));
		else
			FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		
	}
	
}
