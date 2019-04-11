package com.planit.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.planit.qa.util.TestUtils;
import com.planit.qa.util.WebEventListener;

public class TestBase {

	public static Properties properties;
	public static WebDriver driver;
	public static EventFiringWebDriver eventFiringWebDriver;
	public static WebEventListener eventListener;
	public static String browserName = null;

	public TestBase() {
		FileInputStream configFile = null;
		try {

			properties = new Properties();
			if (System.getProperty("os.name").contains("Mac")) {
				configFile = new FileInputStream(
						System.getProperty("user.dir") + "/src/main/resources/config.properties");
			} else if (System.getProperty("os.name").contains("Windows")) {

				configFile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
			}
			properties.load(configFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() {
		try {
			//reading the browser from config.property file
			browserName = properties.getProperty("browser");
			
			if (browserName.equalsIgnoreCase("chrome")) {
				if (System.getProperty("os.name").contains("Mac")) {
					
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver");
				} else if (System.getProperty("os.name").contains("Windows")) {

					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
				}
				Map<String, Object> preferences = new HashMap<String, Object>();
				
				//adding key and value to map to turn off browser notification
				preferences.put("profile.default_content_setting_values.notifications", 2);
				
				ChromeOptions options = new ChromeOptions();
				
				options.setExperimentalOption("prefs", preferences);
				
				options.setCapability("applicationCacheEnabled", false);
				driver = new ChromeDriver(options);
				
				
			}
			else if(browserName.equalsIgnoreCase("firefox"))
			{
				if (System.getProperty("os.name").contains("Mac")) {
					
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/Drivers/geckodriver");
				} else if (System.getProperty("os.name").contains("Windows")) {

					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
					//adding key and value to map to turn off browser notification
					FirefoxProfile profile = new FirefoxProfile();
			        profile.setPreference("permissions.default.desktop-notification", 1);
			        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
			        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			        driver = new FirefoxDriver();
				}	
				driver = new FirefoxDriver();
					
			}
			else if(browserName.equalsIgnoreCase("ie")) {
				if (System.getProperty("os.name").contains("Windows")) 
					
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Drivers\\IEDriverServer");
				//adding key and value to map to turn off browser notification
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setJavascriptEnabled(true);
					driver = new InternetExplorerDriver();
			}
			else if(browserName.equalsIgnoreCase("edge"))
			{
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\Drivers\\MicrosoftWebDriver.exe");
				//adding key and value to map to turn off browser notification
				 EdgeOptions options = new EdgeOptions();
				 options.setCapability("disable-infobars", true);
				 options.setCapability("--disable-notifications", true);
				driver = new EdgeDriver(options);
			}
				
			//registering driver with eventFiringWebDriver
			eventFiringWebDriver= new EventFiringWebDriver(driver);
			
			// creating object of EventListerHandler to register it with EventFiringWebDriver
			eventListener = new WebEventListener();
			
			eventFiringWebDriver.register(eventListener);
			driver = eventFiringWebDriver;
			
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_WAIT, TimeUnit.SECONDS);

			driver.get( properties.getProperty("url"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void cleanUpBeforeExit()
	{
		Process process = null;
		if (System.getProperty("os.name").contains("Windows")) {
		try {
			if(browserName.equalsIgnoreCase("ie"))
				process = Runtime.getRuntime().exec("TaskKill /im iexplore.exe /f");
			if (browserName.equalsIgnoreCase("chrome")) 
				process = Runtime.getRuntime().exec("TaskKill /im chrome.exe /f");
			if (browserName.equalsIgnoreCase("firefox"))
				process = Runtime.getRuntime().exec("TaskKill /im firefox.exe /f");
			if (browserName.equalsIgnoreCase("edge"))
				process = Runtime.getRuntime().exec("TaskKill /im microsoftedge.exe /f");
			}
			catch (Exception e) {
		} finally {
			driver = null;
			try {
				process.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
