package com.tk20.seleniumuiflipassessment.util;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.CONFIG;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.base.Function;
import com.tk20.seleniumuiflipassessment.base.Constants;

public class KeywordEventsUtill {

	static Long explicitwaitTime = Long.parseLong(CONFIG.getProperty("explicitwait"));
	static Long pollingTime = Long.parseLong(CONFIG.getProperty("pollingTime"));
	public static WebDriver driver;
	Long delayTime = Long.parseLong(CONFIG.getProperty("pauseDelay"));
	public String browserName = null;
	public String browserVersion = null;
	public int enterDueDate = 0;
	String browser = CONFIG.getProperty("browserType");
	public static Wait<WebDriver> wait =null;
	public static Map<Integer,File> map=new HashMap<Integer,File>(); //added by Vikas Bhadwal
	public static int incremental_value=Constants.INCREMENTAL_VALUE;
	public KeywordEventsUtill() {
		openBrowser("", CONFIG.getProperty("browserType"));
	}

	// private String randCourseNo;

	public static Object keyUtillFactory() {
		try {
			Class c= Class.forName("com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill");
			return c.newInstance();
		} catch (ClassNotFoundException e) {

			throw new RuntimeException("Class not found : "+e);
			
		} catch (InstantiationException e) {

			throw new RuntimeException("Instialization Exception : "+e);
			
		} catch (IllegalAccessException e) {

			throw new RuntimeException("Illegal Access Exception : "+e);
		}

	}

	/**
	 * openBrowser method opens the browser
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public WebDriver openBrowser(String object, String data) {

		try {

			String osName = System.getProperty("os.name");

			logger.debug(osName + " platform detected");

			osName = osName.toLowerCase();

			logger.debug(osName);

			if (osName.indexOf("win") >= 0) {

				if (driver == null) {

					logger.debug("Opening browser");

					if (data.equals("Mozilla")) {

						FirefoxProfile profile = new FirefoxProfile();
						String sep = System.getProperty("file.separator");
						File dir=new File( System.getProperty("user.dir")+ sep + "externalFiles" + sep + "downloadFiles");
						 if(dir.exists()){
							logger.debug("File Exits");
							}
							else{
							dir.mkdir();
							}

						
						profile.setPreference("browser.download.folderList",2);
					    profile.setPreference("browser.download.manager.showWhenStarting",false);
					    profile.setPreference("browser.download.dir", System.getProperty("user.dir")+ sep + "externalFiles" + sep + "downloadFiles");
					    FirefoxBinary binary = new FirefoxBinary(new File(CONFIG.getProperty("mozilla_path")));
					    profile.addExtension(new File(System.getProperty("user.dir")+ sep + "externalFiles"+sep+"uploadFiles"+sep+"wave_toolbar-1.1.6-fx.xpi"));
						driver = new FirefoxDriver(binary, profile);

						driver.manage().window().maximize();

					} else if (data.equals("IE")) {

						System.setProperty(

						"webdriver.ie.driver",

						System.getProperty("user.dir")

						+ CONFIG.getProperty("ie_path"));

						DesiredCapabilities d = new DesiredCapabilities();

						driver = new InternetExplorerDriver(d);

						driver.manage().window().maximize();

					}

						else if (data.equals("Safari")) {

						DesiredCapabilities capabilities = new DesiredCapabilities();

						driver = new SafariDriver(capabilities);

						driver.manage().window().maximize();

					}

					else if (data.equals("Chrome")) 
					{
						
						System.setProperty(	"webdriver.chrome.driver",System.getProperty("user.dir")+ CONFIG.getProperty("chrome_path"));
						Map<String, Object> prefs = new HashMap<String, Object>();  // Added Code to set Download Path and allowing Multiple downloads on Chrome
						prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
						prefs.put("download.default_directory",  System.getProperty("user.dir")+ File.separator + "externalFiles" + File.separator + "downloadFiles");
						 ChromeOptions options = new ChromeOptions();
						 options.setExperimentalOption("prefs", prefs);
						  driver = new ChromeDriver(options);
						 driver.manage().window().maximize();
					
					}

					long implicitWaitTime = Long.parseLong(CONFIG.getProperty("implicitwait"));

					driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
					wait=new WebDriverWait(driver, explicitwaitTime);
					return driver;

				} else {

					return driver;

				}

			}

			else if (osName.indexOf("mac") >= 0) {

				if (driver == null) {

					logger.debug("Opening browser");

					if (data.equals("Mozilla")) {

						FirefoxProfile profile = new FirefoxProfile();

						FirefoxBinary binary = new FirefoxBinary(new File(CONFIG.getProperty("mozilla_path_mac")));

						driver = new FirefoxDriver(binary, profile);

						driver.manage().window().maximize();

					} else if (data.equals("IE")) {

						System.setProperty(

						"webdriver.ie.driver",

						System.getProperty("user.dir")

						+ CONFIG.getProperty("ie_path"));

						DesiredCapabilities d = new DesiredCapabilities();

						driver = new InternetExplorerDriver(d);

						driver.manage().window().maximize();

					}

					else if (data.equals("Safari")) {

						DesiredCapabilities capabilities = new DesiredCapabilities();
						capabilities.setJavascriptEnabled(false);
						driver = new SafariDriver(capabilities);

						driver.manage().window().maximize();

					}

					else if (data.equals("Chrome")) {

						System.setProperty(

						"webdriver.chrome.driver",

						System.getProperty("user.dir")

						+ CONFIG.getProperty("chrome_path_mac"));
						ChromeOptions options = new ChromeOptions();
						options.setBinary(CONFIG.getProperty("chrome_binary"));

						driver = new ChromeDriver(options);

						driver.manage().window().maximize();

					}

					long implicitWaitTime = Long.parseLong(CONFIG

					.getProperty("implicitwait"));

					driver.manage().timeouts()

					.implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

					return driver;

				} else {

					return driver;

				}

			}

			else if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {

				if (driver == null) {

					logger.debug("Opening browser");

					if (data.equals("Mozilla")) {

						FirefoxProfile profile = new FirefoxProfile();

						FirefoxBinary binary = new FirefoxBinary(new File(

						CONFIG.getProperty("mozilla_path_linux")));

						driver = new FirefoxDriver(binary, profile);

						driver.manage().window().maximize();

					}

					else if (data.equals("IE")) {

						System.setProperty("webdriver.ie.driver",

						"ie_path_linux");

						driver = new InternetExplorerDriver();

					}

					else if (data.equals("Chrome")) {

						new DesiredCapabilities();

						URL serverurl = new URL("http://localhost:9515");

						DesiredCapabilities capabilities = DesiredCapabilities

						.chrome();

						driver = new RemoteWebDriver(serverurl, capabilities);

					}

					long implicitWaitTime = Long.parseLong(CONFIG

					.getProperty("implicitwait"));

					driver.manage().timeouts()

					.implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);

					return driver;

				} else {

					return driver;

				}

			} else {

				return driver;

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			return null;

		}

	}

	/**
	 * createRandomNum creates a random string
	 * 
	 * @return
	 */
	public static String createRandomNum() {
		logger.debug("Navigating to URL");
		String num = "";
		try {
			num = RandomStringUtils.random(4, true, true);
			logger.debug("Generated Random Number is : " + num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * navigate method navigate the browser to a specific web address specified
	 * in the config.properties file
	 * 
	 * @param object
	 * @param data
	 * @return * Added By Anil Kumar Mishra Date: 03/07/2014 Added code for
	 *         check Browser Type And Browser version
	 *         
	 * Modified By MAyank Saini  Added code to set download direcory path in chrome  
	 *  
	 * Removed By Shweta Gupta on 6th January 2015, Code to call method setdownloadPath
	 */

	public String navigate(String object, String data) {
		logger.debug("Navigating to URL");
		
		try {
							
   			driver.navigate().to(data);
   			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			browserName = caps.getBrowserName();
			browserVersion = caps.getVersion();
			logger.debug(browserName + " " + browserVersion);			
			
		} 
		catch(TimeoutException ex){
			 
			 return Constants.KEYWORD_FAIL + "--Browser Name--" + browserName + "--Browser Version--" + browserVersion + "--Application Version--";
		}
				
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS + "--Browser Name--" + browserName + "--Browser Version--" + browserVersion + "--Application Version--";
		
	}
	/**
	 * click on a link by finding its xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickLink(String object, String data) {
		logger.debug("Clicking on link ");
		WebElement ele=null;
		try { 
			
			ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			ele.click();
		    browserSpecificPause(object, data);
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + ex.getCause();
		}
		catch(WebDriverException ex)
		{
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}
/**
 * 07/03/2014
 *Added By Pankaj Sharma 
 *This method is used to add pause after every click
 */
	public String browserSpecificPause(String object, String data) {
		try {
			if (CONFIG.getProperty("browserType").equals("Mozilla")) {
				Thread.sleep(Integer.parseInt(CONFIG.getProperty("pauseTimeForMozilla")));
			} else if (CONFIG.getProperty("browserType").equals("IE")) {
				Thread.sleep(Integer.parseInt(CONFIG.getProperty("pauseTimeForIE")));
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				Thread.sleep(Integer.parseInt(CONFIG.getProperty("pauseTimeForChrome")));
			}
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + "Unable to pause" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	/**
	 *Added By Mayank Saini 
	 *This method is used to add pause after every alert
	 */
		public String browserSpecificPauseForAlert(String object, String data) {
			try {
				if (CONFIG.getProperty("browserType").equals("Mozilla")) {
					Thread.sleep(Integer.parseInt(CONFIG.getProperty("alertPauseForMozilla")));
				} else if (CONFIG.getProperty("browserType").equals("IE")) {
					Thread.sleep(Integer.parseInt(CONFIG.getProperty("alertPauseForIE")));
				} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
					Thread.sleep(Integer.parseInt(CONFIG.getProperty("alertPauseForChrome")));
				}
			} catch (Exception e) {

				return Constants.KEYWORD_FAIL + "Unable to pause" + e.getMessage();
			}
			return Constants.KEYWORD_PASS;
		}
	
	/**
	 * moves to a frame by finding its id
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String moveToFrame(final String object, String data) {
		logger.debug("Move to the iframe .......");
		try {

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS)
					.pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(Exception.class);

		WebElement	iframe = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.id(OR.getProperty(object)));
				}
			});

			driver.switchTo().frame(iframe);
			pause(object, data);

		}
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * moves to a frame by finding its name
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String moveToFrameByName(final String object, String data) {
		logger.debug("Move to the iframe .......");
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS)
					.pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(Exception.class);

		WebElement	iframe = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.name(OR.getProperty(object)));
				}
			});

			driver.switchTo().frame(iframe);
			pause(object, data);

		}
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + ex.getCause();
		}
		
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * verifies a link text by finding its linkText
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyLinkTextBytext(String object, String data) {
		logger.debug("Verifying link Text");
		try {
			String actual = wait.until(explicitWaitForElement(By.linkText(OR.getProperty(object)))).getText();
			String expected = data.trim();
			logger.debug(actual + "actual");
			logger.debug(expected + "expec");
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Link text not verified";

		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Link text not verified" + e.getMessage();

		}

	}

	/**
	 * moves to a frame by using its xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String moveToFrameByXpath(String object, String data) {
		logger.debug("Move to the iframe .......");
		try {
			WebElement iframe = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			logger.debug("iframe" + iframe);
			driver.switchTo().frame(iframe);
			pause(object, data);
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + ex.getCause();
		}
		
		catch (Exception e) {
	
			return Constants.KEYWORD_FAIL + " -- Not able to move into iframe" + e.getMessage();
		}

	
	}

	/**
	 * moves to the default frame i.e. parent window
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String moveToDefaultFrame(String object, String data) {
		logger.debug("Move to the default window .......");
		try {

			driver.switchTo().defaultContent();
			Thread.sleep(3000);

		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " -- Not able to move back to default window" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * moves to the main window
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String moveToMainWindow(String object, String data) {
		logger.debug("move To MainWindow");
		try {

			String mainwindow = driver.getWindowHandle();
			driver.switchTo().window(mainwindow);
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to move To MainWindow" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * clicks on a link by using its complete link text
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickLink_linkText(String object, String data) {
		logger.debug("Clicking on link ");
		WebElement ele=null;
		try {
			ele = wait.until(explicitWaitForElement(By.linkText(OR.getProperty(object))));
			ele.click();
			browserSpecificPause(object, data);
			return Constants.KEYWORD_PASS;
		}
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + ex.getCause();
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -- " + OR.getProperty(object) + " link not present";
		}
	}

	/**
	 * verify the link bu using its xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyLinkText(String object, String data) {
		logger.debug("Verifying link Text");
		try {
			String actual = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).getText();
			String expected = data.trim();
			logger.debug("act" + actual);
			logger.debug("expected" + expected);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Link text not verified";

		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Link text not verified" + e.getMessage();

		}

	}

	/**
	 * click on a button by using its xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickButton(String object, String data) {
		logger.debug("Clicking on Button");
		WebElement ele=null;
		try {
				 ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
				 ele.click();
				 browserSpecificPause(object, data);
			}
		
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(WebDriverException ex){
				try{
					String exceptionMessage=ex.getMessage();
						if(exceptionMessage.contains("Element is not clickable at point"))
						{
					if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
								return Constants.KEYWORD_PASS;
						else
								return Constants.KEYWORD_FAIL;
						}
					else
							return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
					}
					catch(Exception e){
						return Constants.KEYWORD_FAIL+e.getMessage();
					}
					
					} 
		catch (Exception e) {
				return Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * verify the text on a button
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyButtonText(String object, String data) {
		logger.debug("Verifying the button text");
		try {

			String actual = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).getText();
			String expected = data.trim();
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Button text not verified " + actual + " -- " + expected;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"TimeoutCause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
					}

	}

	/**
	 * select a list from a dropdown
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String selectList(final String object, String data) {
		logger.debug("Selecting from list");
		try {

			WebElement select = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));

			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG").trim()));
			String expected = data.trim();
			logger.debug("Expected is > " + expected);
			for (WebElement option : options) {

				String actual = option.getText().trim();
				logger.debug("Actual > " + actual);
				if (actual.equals(expected)) {
					logger.debug("Selected..." + data);
					option.click();
					return Constants.KEYWORD_PASS + "--" + data + " - selected";
				}
			}
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + " - Could not select from list. ";
	}

	/**
	 * verify that a check box is unchecked
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String isCheckboxUncheck(String object, String data) {
		logger.debug("isCheckbobUncheck");
		try {

			boolean checked = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).isSelected();
			if (checked)
				return Constants.KEYWORD_FAIL + " -- checkbox is selected,it shouldn't hav been";
			else
				return Constants.KEYWORD_PASS + " -- checkbox is not selected";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}

	}

	/**
	 * check mark a check box
	 * 
	 * @param object
	 * @param data
	 * @return
	 * 
	 *  Modified by sdhamija. added code that handles pagination
	 *  Modified by  Kritika on 4th May'2015 :- Added code to check checkbox using following sibling node-set
	 */
	
	public String checkCheckBox(String object, String data) 
	{
	    logger.debug("Checking checkbox");
	    List<WebElement> element_List=null;
	    try {
		while (true) 
		{
		    element_List = explictWaitForElementList(object);

		    List<WebElement> next_Link_List =  driver.findElements(By.xpath(OR.getProperty("pagination_next_link")));

		    if (element_List.size() > 0 )
		    {
			for(int i=1;i<=element_List.size();i++)
			{									
			    boolean checked = element_List.get(0).isSelected();

			    if (!checked) //checkbox is unchecked
			    { 								
				String xpathVal=OR.getProperty(object);						

				if(!xpathVal.contains(Constants.INPUT_C_X))
				{

				    if(xpathVal.endsWith(Constants.LABEL_X) || xpathVal.endsWith(Constants.DIV_X) || xpathVal.endsWith(Constants.DIV_CLASS_X) || xpathVal.contains(Constants.LABEL_C_X) ||xpathVal.contains(Constants.DIV_C_X) )
				    {
					element_List.get(0).click();
					return Constants.KEYWORD_PASS + " clicked on check-box";
				    }
				}
				else
				{            	 
				    WebElement ele1 = driver.findElement(By.xpath(OR.getProperty(object)+"/following-sibling::*"));  // if Xpath is made using input as last nodes.
				    if(ele1.getTagName().equals(Constants.LABEL))
				    {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
					executor.executeScript("arguments[0].click();", ele1);

					return Constants.KEYWORD_PASS + " clicked on check-box";
				    } 
				    else 
				    {
					return Constants.KEYWORD_PASS + " check-box is already checked";

				    }
				}
			    }
			}

		    }
		    else if (next_Link_List.size() > 0) 
		    {
			next_Link_List.get(0).click();
		    }

		    else 
		    {
			return Constants.KEYWORD_FAIL + " checkbox is not present";
		    }

		}
	    }
	    catch(TimeoutException ex)
	    {
		return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
	    }
	    catch(WebDriverException ex){
		try{
		    String exceptionMessage=ex.getMessage();
		    if(exceptionMessage.contains("Element is not clickable at point"))
		    {
			if(new ApplicationSpecificKeywordEventsUtil().clickJs(element_List.get(0)).equals(Constants.KEYWORD_PASS))
			    return Constants.KEYWORD_PASS;
			else
			    return Constants.KEYWORD_FAIL+ex.getMessage();
		    }
		    else
			return Constants.KEYWORD_FAIL+"not able to Click";
		}
		catch(Exception e){

		    return Constants.KEYWORD_FAIL+e.getMessage();
		}

	    }


	    catch (Exception e) {

		return Constants.KEYWORD_FAIL + " - Could not find checkbox";
	    }
	}



	/**
	 * uncheck a check box
	 * 
	 * @param object
	 * @param data
	 * @return 29/10/2013 Modified By Mayank Saini Now it handles pagination
	 *         also
	 */
	public String unCheckCheckBox(String object, String data) {
		logger.debug("UnChecking checkbox");
		List<WebElement> element_List=null;
		try {
			while (true) {
					element_List = explictWaitForElementList(object);
					List<WebElement> next_Link_List =  driver.findElements(By.xpath(OR.getProperty("pagination_next_link")));

				if (element_List.size() > 0 && element_List.get(0).isDisplayed()) {

						String checked = element_List.get(0).getAttribute("checked");
						if (checked == null)
						{ // checkbox is unchecked
						return Constants.KEYWORD_PASS + " check-box is already unchecked";

						} 
						else {
						element_List.get(0).click();
						return Constants.KEYWORD_PASS + " unchecked check-box";

					}

				}

				else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
				}

				else {
					return Constants.KEYWORD_FAIL + " checkbox is not present";
				}

			}
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
						if(new ApplicationSpecificKeywordEventsUtil().clickJs(element_List.get(0)).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
						else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}

	}

	/**
	 * verify that a check box is checked
	 *  Modified By Nitin on 10/10/2014
	 * Added explictWaitForElementUsingFluent instead of explictWaitForElement
	 * @param object
	 * @param data
	 * @return
	 */
	
	public String verifyCheckBoxSelected(String object, String data) {
		logger.debug("inside verifyCheckBoxSelected");
		try {
			WebElement ele=explictWaitForElementUsingFluent(object);
			
						boolean checked =ele.isSelected();
						if (checked) {
							return Constants.KEYWORD_PASS + "--Check box is Selected";
						} else {
							return Constants.KEYWORD_FAIL + " Check box is Not selected";
						}
					
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception e) {
			
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";
		}

	}
	/* Sandeep Dhamija 
	/* verify that a checkbox is unchecked
	/* 16 April, 2013 
	 * Modified by Nitin Gupta on 10/10/2014
	 * Added method isSelected() in place of getAttribute()  
	 * and explictWaitForElementUsingFluent instead of explictWaitForElement
	 */
	public String verifyCheckBoxIsNotSelected(String object, String data) {
		logger.debug("Verifying checkbox is not selected");
		try {
			WebElement ele=explictWaitForElementUsingFluent(object);
			
			boolean checked =ele.isSelected();
			if (checked) {
				return Constants.KEYWORD_FAIL + "--Check box is Selected";
			} else {
				return Constants.KEYWORD_PASS + " Check box is Not selected";
			}
		}
		
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not find checkbox";

		}
	}
	/**
	 * verify a text or heading by using its xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyText(String object, String data) {
		logger.debug("Verifying the text");
		try {

			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String actual = ele.getText();
			String expected = data.trim();

			logger.debug("data" + data);
			logger.debug("act" + actual);
			if (actual.trim().equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified Actual text = " + actual + " -- Expected Text = " + expected + " ";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
	
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * verify a text in the text field.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyTextinInput(String object, String data) {
		logger.debug("Verifying the text in input box");
		try {
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String actual = ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
			String expected = data.trim();
			logger.debug("actual: " + actual);
			logger.debug("expected: " + expected);
			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				logger.debug("actual- " + actual);
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	/**
	 * Sandeep dhamija 28 May, 2013
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	/*
	 * Added OR operator in If Condition Modified by : Sandeep Dhamija, 23
	 * oct,2013
	 */

	public String verifyTextinInputUsingContains(String object, String data) {
		logger.debug("Verifying the text in input box");
		try {
			String actual =wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			String expected = data;

			if (expected.contains(actual) || (actual.contains(expected))) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " Not matching ";
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to find input box " + e.getMessage();

		}
	}

	/**
	 * click on an image
	 * 
	 * @param object
	 * @param data
	 * @return
	 */

	public String clickImage(String object, String data) {
		logger.debug("Clicking the image");
		WebElement ele=null;
		try {
				 ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
				ele.click();
				browserSpecificPause(object, data);
				return Constants.KEYWORD_PASS;
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL+ex.getMessage();
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click";
				} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -- Not able to click on image" + e.getMessage();
		}

		}
		catch (Exception e) {
			
			return Constants.KEYWORD_FAIL + " -- Not able to click on image" + e.getMessage();
		}
	}

	/**
	 * verify the title of the popup window
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyTitle(String object, String data) {
		logger.debug("Verifying title");
		try {
			String actualTitle = driver.getTitle();
			String expectedTitle = data.trim();
			if (actualTitle.equals(expectedTitle))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Title not verified " + expectedTitle + " -- " + actualTitle;
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Error in retrieving title";
		}
	}

	/**
	 * This method close the browser
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String closeBrowser(String object, String data) {
		logger.debug("Checking existance of browser");
		try {
			driver.close();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object doest not exist";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * this method clicks on an element whose xpath is given in the object
	 * Modified By Surender on 13/10/2014
	 * Added explictWaitForElementUsingFluent instead of explictWaitForElement
	 * Modified By Surender on 16/12/2014 :- Added catch block for ElementNotVisibleException
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickRadio(String object, String data) {
		logger.debug("Clicking on any element");
		WebElement ele=null;
		try {
			 ele = explictWaitForElementUsingFluent(object);
			 ele.click();
			browserSpecificPause(object, data);
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		 catch(ElementNotVisibleException ex)
	        {
	        	if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
					return Constants.KEYWORD_PASS;
			else
					return Constants.KEYWORD_FAIL;
	        }
		catch(StaleElementReferenceException ex){
			ele.click();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
				
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " " + e.getMessage() + " Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * uses javascript executor to load the page
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String synchronize(String object, String data) {
		logger.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript("function pageloadingtime()" + "{" + "return 'Page has completely loaded'" + "}"
				+ "return (window.onload=pageloadingtime());");

		return Constants.KEYWORD_PASS;
	}

	/**
	 * waits for a particular element to be visible
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String waitForElementVisibility(String object, String data) {
		logger.debug("Waiting for an element to be visible");
		int start = 0;
		int time = (int) Double.parseDouble(data);
		try {
			while (time == start) {
				if (driver.findElements(By.xpath(OR.getProperty(object))).size() == 0) {
					Thread.sleep(1000L);
					start++;
				} else {
					break;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "Unable to close browser. Check if its open" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Delay execution time by 5 second.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String pause(String object, String data) {
		try {
			Thread.sleep(delayTime);
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + "Unable to pause" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Delay execution time by the given time.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String executionDelay(String object, String data) {

		try {
			long l = Long.parseLong(data);
			Thread.sleep(l);
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + "Unable to pause" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Anil Reddy /* This method is used to check whether alert is present or
	 * not
	 */
	public String isAlertPresent(String object, String data) {

		boolean presentFlag = false;
		try {

			Alert alert = explicitWaitForAlert();
			presentFlag = true;
			if (presentFlag) {
				alert.accept();
			browserSpecificPauseForAlert(object, data);
			} 
			return Constants.KEYWORD_PASS + "----Alert is Present";
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			// Alert not present
		
			return Constants.KEYWORD_FAIL + "----No alert is present";
		}
	}

	/**
	 * this method accepts the alert
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String isAlertAccept(String object, String data) {
		logger.debug("Verify the Alert pop up ");
		boolean presentFlag = false;
		try {
			Alert alert = explicitWaitForAlert();
			if (!presentFlag) {
				logger.debug("Alert detected");
				alert.accept();
				browserSpecificPauseForAlert(object, data);
			} else {
				return Constants.KEYWORD_FAIL + "--Alert not found";
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "--Alert not found" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * this method dismiss the alert
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String isAlertDismis(String object, String data) {
		logger.debug("Verify the Alert pop up ");
		boolean presentFlag = false;

		try {
			Alert alert = explicitWaitForAlert();
			if (!presentFlag) {
				logger.debug("Alert detected");
				alert.dismiss();
				browserSpecificPauseForAlert(object, data);
			} else {
				return Constants.KEYWORD_FAIL + "--Alert not found";
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "--Alert not found" + e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * this method moves the mouse over the element specified in the object
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String mouseOver(String object, String data) {
		try {
			WebElement ele=null;
			ele = explictWaitForElementUsingFluent(object);
			Actions action = new Actions(driver);
			action.moveToElement(ele).perform();
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/**
	 * This method is used to upload the file
	 **/

	public String uploadFile(String object, String data) throws InterruptedException {
		logger.debug("upload the file.");
		try {

			wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).sendKeys(data);
			wait.until(explicitWaitForElement((By.xpath(OR.getProperty("Finish_Button"))))).click();
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " - Could not attached the file. " + e.getMessage();
		}
	}

	/************************ APPLICATION RELATED KEYWORDS ********************************/

	/**
	 * this method validates the logged in user by comparing the name from data
	 
	* Modified By Karan Sood on 27th July 2014,  Added code to get Build Version
	*/
	
	public String validateDisplayUserName(String object, String data) {
		logger.debug("check the display user name");
		String username[] = null;
		String DisplayName = "";
		String build="";
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			DisplayName = element.getText();
			logger.debug("DisplayName--" + DisplayName);

			username = DisplayName.split(Constants.Object_SPLIT);
			if (!data.equals(username[1].trim())) {
				logger.debug("login username " + data + " and display username" + username[1] + " is not matched");
				Assert.assertFalse(true, "login username " + data + " and display username" + username[1] + " is not matched");
			}

			String version=(wait.until(explicitWaitForElement(By.xpath(OR.getProperty("build_version")))).getText()).split(":")[1].trim(); // Modified By Karan on 28/07/2015
									
			if(version.contains("v"))
			{
				build=version.split("v")[1].trim();
			}
			else
			{
				build=version;
			}
			
			logger.debug("build value = " + build);	
		}
		
		catch(ArrayIndexOutOfBoundsException  aex)
		{
			build="N.A.";
			return Constants.KEYWORD_FAIL +"Cause: "+ aex.getCause() + Constants.DATA_SPLIT + build;
		}
		
		catch(TimeoutException ex)
		{
			build="N.A.";
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause() + Constants.DATA_SPLIT + build;
		} 
		catch (Exception e) {
		
			logger.debug("DisplayName--" + DisplayName);
			return Constants.KEYWORD_FAIL + "login username" + data + " and display username" + username[1] + " is not matched." + e.getMessage();
		}
		return Constants.KEYWORD_PASS + Constants.DATA_SPLIT + build;
	}
	
	/**
	 * Take a screen shot of failed cases
	 * @param filename
	 * @param keyword_execution_result
	 * @throws IOException
	 * Modified by Vikas Bhadwal on 04/03/2015 to link Screenshot with Report.html and Application.html.
	 */
	
	
	public void captureScreenshot(String filename, String keyword_execution_result,Integer testStepId) throws IOException {
		
		String file_name=filename.replaceAll("\\s",""); //added by Vikas 
		
		if (CONFIG.getProperty("screenshot_everystep").equals("Y")) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//screenshots//" + file_name + ".jpg"));

		} else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y")) {

			try {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String user_directory=System.getProperty("user.dir");
				String directory=user_directory.replaceAll("\\s","");
				File errorFile=new File(directory + "//screenshots//" + file_name + ".jpg");
				FileUtils.copyFile(scrFile,errorFile );
					if(map.containsKey(testStepId))
					{
						map.put(testStepId+incremental_value, errorFile);
						incremental_value++;
					}
					else
				     {
					map.put(testStepId, errorFile);
					
					}
			} 
			catch (WebDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.debug("following exception occured while capturing the screenshot\n\n" + e);
			}
		}
	}

	/**
	 * selectMultipleList->
	 * 
	 * This method is used to selct the multiple value from the list.*
	 */
	public String selectMultipleList(String object, String data) {
		logger.debug("Selecting from list");
		try {

			boolean flag = false;
			WebElement select = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));

			String temp = data.trim();
			String allElements[] = temp.split(Constants.Object_SPLIT);
			for (WebElement option : options) {

				logger.debug("optionvalue " + option.getText());
				for (int i = 0; i < allElements.length; i++) {
					if (option.getText().equals(allElements[i].trim())) {
						option.click();
						flag = true;
					}
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " - Could not select from list. ";
			}
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();

		}
	}

	/**
	 * setFCKeditorMessage-> This method is used to enter the message in
	 * FCKeditor box.
	 *  Modified by Karan Sood on 14th Nov, 2014 : Removed If Else Condition for Different Browsers
	 *
	 */
	public String setFCKeditorMessage(String object, String data) throws InterruptedException

	{
		try {
			
			logger.debug("enter the message in FCKeditor");
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
		
			driver.switchTo().frame(ele);
			WebElement editable = driver.switchTo().activeElement();
			editable.clear();		
			((JavascriptExecutor) driver).executeScript("document.body.innerHTML='" + data + "'");
			Set<String> availableWindows = driver.getWindowHandles();
			logger.debug("Handle Size:" + availableWindows.size());
			// Retreive all the window handles into variables
			String WindowIDparent = null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1) {
					logger.debug(Integer.toString(counter) + " " + windowId);
					WindowIDparent = windowId;
				}
				counter++;
			}
			// Navigate to Parent window
			driver.switchTo().window(WindowIDparent);
			logger.debug("In the Parent");

		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}


	/**
	 * clears an input field
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clearInput(String object, String data) {
		try {
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			
			ele.clear();
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception nse) {

			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();// .printStackTrace();
		}


	}

	/**
	 * Balkrishan Bhola This method is used to verify whether msg entered in CK
	 * editor remains or not
	 * 
	 */
	public String verifyFCKeditorMessage(String object, String data) throws InterruptedException

	{
		try {

			logger.debug("verifyFCKeditorMessage");
			logger.debug("enter the message in FCKeditor");
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			driver.switchTo().frame(ele);
			WebElement editable = driver.switchTo().activeElement();
			String actual = editable.getText();

			Set<String> availableWindows = driver.getWindowHandles();
			logger.debug("Handle Size:" + availableWindows.size());
			// Retreive all the window handles into variables
			String WindowIDparent = null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1) {
					logger.debug(Integer.toString(counter) + " " + windowId);
					WindowIDparent = windowId;
				}
				counter++;
			}
			// Navigate to Parent window
			driver.switchTo().window(WindowIDparent);
			logger.debug("In the Parent");
			if (actual.equals(data)) {
				logger.debug("actual " + actual);
				Thread.sleep(2000);
				return Constants.KEYWORD_PASS + " -- saved message is present";
			} else
				return Constants.KEYWORD_FAIL + " -- saved message is not present";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
		}
	}

	/**
	 * this method handles a popup window
	 * 
	 * @param object
	 * @param data
	 * @return
	 * @throws InterruptedException
	 */
	public String handleWindows(String object, String data) throws InterruptedException {
		logger.debug("Handling the windows....");
		try {
			String mainwindow = "";
			String newwindow = "";
			Set<String> windowids = driver.getWindowHandles();
			Iterator<String> ite = windowids.iterator();
			mainwindow = ite.next();
			newwindow = ite.next();
			driver.switchTo().window(newwindow);
			//Thread.sleep(2000);
			driver.close();
			driver.switchTo().window(mainwindow);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}

	}

	public String verifyButtonTextByValueLibrary(String object, String data) {
		logger.debug("Verifying the button text");
		try {
			String actual = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			String expected = data;
			logger.debug("actual" + actual);
			logger.debug("expected" + expected);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Button text not verified " + actual + " -- " + expected;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/*
	 * Balkrishan Bhola This method is used to Enter the date in Text box
	 * without clicking on calendar image
	 */
	public String enterDate(String object, String data) {
		logger.debug("Writing in text box");

		try {
			data = OR.getProperty("date");

		WebElement ele=	wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,data);
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Sandeep Dhamija// Verifies the text on Alert Box
	 * 
	 * @param object
	 * @param data
	 * @return
	 * 
	 *         Modified By :- Balkrishan Bhola [Added trim() in 'actual' and
	 *         'expected' literal]
	 */

	/*
	 * modified on 9 September, 2013 by SDhamija Added .trim() on actual and
	 * expected data
	 */

	public String verifyAlertText(String object, String data) {
		logger.debug("Verify the text of Alert pop up ");
		try {

			Alert alert = explicitWaitForAlert();
			String actual = alert.getText().trim();
			String expected = data.trim();

			logger.debug("actual: " + actual);
			logger.debug("expected: " + expected);

			if (actual.trim().equals(expected.trim())) {
				logger.debug("Alert detected and Text has been verified");
				return Constants.KEYWORD_PASS + " " + "--" + actual;
			} else {
				return Constants.KEYWORD_FAIL + " " + actual;
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Alert not found" + e.getMessage();
		}
	}

	/**
	 * verify the entered date
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyEnteredDateByValue(String object, String data) {
		logger.debug("Verifying the button text");
		try {
			String actual = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object))))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			data = OR.getProperty("les_cal_date");
			logger.debug("actual" + actual);
			logger.debug("expected" + data);
			if (actual.equals(data))
				return Constants.KEYWORD_PASS + "--EnteredDate present";
			else
				return Constants.KEYWORD_FAIL + " -- EnteredDate not verified " + actual + " -- " + data;
		} catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * This method gets the relative path of the file which is appended by the
	 * FILE NAME specified in EXCEL SHEET
	 * */
	public String getFilePath(String object, String data) {
		logger.debug("Writing in text box");

		try {

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + data.trim();
			logger.debug(path);
			WebElement ele=driver.findElement(By.xpath(OR.getProperty(object)));
			ele.sendKeys(path);
		
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Balkrishan Bhola This method is used to verify whether WebElement is
	 * present or not
	 * 
	 * */
	public String isWebElementPresent(final String object, String data) {
		logger.debug("Entered into isWebElementPresent()");
		try {
			int element = explictWaitForElementSize(object);
			if (element == 0) {
				logger.debug("webElement not present.." + element);
				return Constants.KEYWORD_FAIL + " -- No webElement present";
			} else
				return Constants.KEYWORD_PASS + " -- webElement present -- ";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * checks the breadcrumb text on the page
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String checkBreadcrumb(String object, String data) {
		try {
			logger.debug("checking breadcrumb text....");
			String breadcrumb = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String finalbreadcrumb = breadcrumb.substring(13);
			logger.debug("Compairing the Breadcrumb links");
			 boolean flag = false;			
				String actualtemp = finalbreadcrumb.trim();
				String expectedtemp = data.trim();
				if (actualtemp.equals(expectedtemp)) {
					flag = true;
				} else {
					flag = false;
					
				}

			
			if (flag) {
				return Constants.KEYWORD_PASS + "--BreadCrumb text metched";
			}

			return Constants.KEYWORD_FAIL + " BreadCrumb text did not match";
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getLocalizedMessage();
		}
	}
	/**
	 * verify that a link is active or not
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyLinkIsActive(String object, String data) {

		try {

			WebElement link = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			if (link.getAttribute(OR.getProperty("ATTRIBUTE_CLASS")).equals(OR.getProperty("CLASS_VALUE_ACTIVE"))) {
				return Constants.KEYWORD_PASS + "Link is Active";
			}

			else {
				return Constants.KEYWORD_FAIL + "Link is InActive";
			}

		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Balkrishan Bhola This method is used to verify whether Link is present or
	 * not
	 * */
	public String verifyLinkPresent(String object, String data) {
		logger.debug("Entered into verifyLinkPresent()");
		try {
			boolean linkPresent = false;
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			linkPresent = element.isDisplayed();
			if (linkPresent == true) {
				logger.debug("Link present.." + linkPresent);
				return Constants.KEYWORD_PASS + " -- Link present";
			}

			return Constants.KEYWORD_FAIL + " -- No Link present -- ";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Balkrishan Bhola This method is used to verify the Text appeared on the
	 * button by its Value attribute.
	 * */
	public String verifyButtonTextByValue(String object, String data) {
		logger.debug("Verifying the button text");
		try {
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			String expected = data;
			logger.debug("actual" + actual);
			logger.debug("expected" + expected);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Button text not verified " + actual + " -- " + expected;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/**
	 * Balkrishan Bhola This method is used to verify Placeholder Text using
	 * placeholder atttribute
	 * */
	public String verifyPlaceholderText(String object, String data) {
		logger.debug("Verifying verifyPlaceholderText");
		try {
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_PLACEHOLDER"));
			String expected = data;

			logger.debug("expected" + expected);
			logger.debug("act" + actual);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS + " -- " + expected + " is present";
			else
				return Constants.KEYWORD_FAIL + " -- " + expected + " is not present";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * 2/4/13 Balkrishan Bhola This method is used to verify whether auto search
	 * is working or not
	 * */
	public String verifyAutoSearch(String object, String data) {
		logger.debug("Verifying verifyAutoSearch");
		try {

			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			logger.debug("actual text" + actual);
			String expected = data.trim();
			logger.debug("Text from sheet" + expected);
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS + " -- Autosearch is working " + data + " is present ";
			else
				return Constants.KEYWORD_FAIL + " -- Autosearch is not working " + data + " is not present ";

		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -- Auto search is not working , Object not found ";
		}
	}

	/**
	 * Sohal Bansal /*This method is used to verify that link is present using
	 * contains
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyTextUsingContains(String object, String data) {
		logger.debug("Verifying the text");
		try {

			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim();
			String expected = data.trim();

			logger.debug("data" + data);
			logger.debug("act" + actual);
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		}
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * this method uses robot class to download the file
	 * 
	 * @param object
	 * @param data
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public String openWith(String object, String data) throws InterruptedException, AWTException {
		try {
		
			Robot rb = new Robot();
			Thread.sleep(5000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(3000);
			Dimension de = Toolkit.getDefaultToolkit().getScreenSize();
			int x = de.width;
			rb.mouseMove(x - 20, 0);
			rb.mousePress(InputEvent.BUTTON1_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_MASK);

		} catch (NoSuchElementException nse) {

			return Constants.KEYWORD_FAIL + "---File cannot be opened Sucessfully" + nse.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "----File opened Sucessfully";
	}

	/**
	 * This mehtod is used to drag and drop the elements
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String dragAnddrop(String object, String data) {

		try {

			String[] dragdrop = object.split(Constants.Object_SPLIT);
			String src = dragdrop[0];
			String trgt = dragdrop[1];
			WebElement source = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(src))));
			WebElement target = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(trgt))));

			new Actions(driver).dragAndDrop(source, target).build().perform();
			return Constants.KEYWORD_PASS + "--Element Dropped sucessfully";
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "Cannot Drag and Drop element";
		}

		

	}

	/**
	 * This method verifies all the values of a list
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyAllVauesOfList(String object, String data) {
		logger.debug("Selecting from list");
		try {

			boolean flag = false;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			String temp = data;
			String allElements[] = temp.split(Constants.Object_SPLIT);
			String actual;
			for (int i = 0; i < allElements.length; i++) {
				actual = options.get(i).getText().trim();
				if (actual.equals(allElements[i].trim())) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- Dropdown's all values are present";
			} else {
				return Constants.KEYWORD_FAIL + " -- Dropdown's all values are not present";
			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();

		}
	}

	/**
	 * Anil kumar Mishra Date: 2/20/13 verifyDisableElement method is used to
	 * verify that drop down list is disable or enable.
	 */

	// Modified by SDhamija. removed data from result
	public String verifyDisableElement(String object, String data) {
		logger.debug("Verifying if element is disabled");
		Boolean flag = false;
		try {

			flag = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).isEnabled();

			if (!flag) {
				return Constants.KEYWORD_PASS + "    --Element is Non editable";
			} else {
				return Constants.KEYWORD_FAIL + "    --Element is  editable";

			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "element not found";
		}
	}

	/**
	 * By Anish Sharma This function moves the cursor to particular location(x,y
	 * coordinates)
	 */
	public String moveToElementByRobot(String object, String data) {

		try {

			Point coordinates = driver.findElement(By.xpath(OR.getProperty(object))).getLocation();
			Robot robot = new Robot();
			robot.mouseMove(coordinates.getX(), coordinates.getY() + Integer.parseInt(OR.getProperty("change_in_value")));
			return Constants.KEYWORD_PASS;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/**
	 * By Anish Sharma
	 * 
	 * This method get the text from CkEditor and compares it with actual data
	 */
	public String getFCKeditorMessage(String object, String data) throws InterruptedException

	{
		try {
			logger.debug("getitng the text from FCKeditor");
			driver.switchTo().frame(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))));
			String get_text = driver.switchTo().activeElement().getText();

			if (get_text.equals(data)) {
				return Constants.KEYWORD_PASS + " TEXT IS MATCHING...";

			} else {
				return Constants.KEYWORD_FAIL + "--TEXT IS NOT MATCHING...";
			}

		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (WebDriverException e) {
		
			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
		}

	}

	/**
	 * Puneet 26/03/2013 This method is used to verify that size of element is
	 * equal to required size.
	 */
	public String verifyElementSize(String object, String data) {

		logger.debug("Verifying that size of element is equal to required size ");
		try {
			int actual_cnt = explictWaitForElementSize(object);
			System.out.println("Actual size of Element" + actual_cnt);
			int expected_cnt = Integer.parseInt(data);
			System.out.println("Expected size of Element" + expected_cnt);

			if (actual_cnt == expected_cnt)
				return Constants.KEYWORD_PASS + "--Element size is correct--";
			else
				return Constants.KEYWORD_FAIL + " --Elements size is not correct-- ";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Puneet 21/03/2013 This Method id used to verify that number one type of
	 * elements are equal to number of other type of elements
	 */
	public String matchElements(String object, String data) {

		logger.debug("Verifying that number one type of elements are equal to number of other type of elements ");
		try {
			String temp[] = object.split(Constants.Object_SPLIT);
			int first_element_cnt = explictWaitForElementSize(temp[0]);
			int second_element_cnt =explictWaitForElementSize(temp[1]);

			System.out.println("No. of first type of element" + first_element_cnt);
			System.out.println("No. of second type of element" + second_element_cnt);

			if (first_element_cnt == second_element_cnt) {
				return Constants.KEYWORD_PASS + "--Elements Matches--";
			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object Not found " + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + "--Elements Do Not Matches--";
	}

	/**
	 * Puneet 16/03/2013 This method is used to check if all links are present
	 * having some common xpath
	 * 
	 */
	public String verifyLinks(String object, String data) {
		logger.debug("Check if correct fields are displayed");
		try {

			String temp[] = data.split(Constants.Object_SPLIT);
			List<WebElement> list = explictWaitForElementList(object);
			int fld_size = list.size();
			System.out.println("No of Links:" + fld_size);

			for (int i = 0; i < fld_size; i++) {
				if (!temp[i].equals(list.get(i).getText())) {
					return Constants.KEYWORD_FAIL + temp[i] + "--Link Not present-- ";
				}
			}
			return Constants.KEYWORD_PASS + "--All Links are present--";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object Not found " + e.getMessage();
		}
	}

	/**
	 * Puneet 21/03/2013 This method is used to verify the tool tip text.
	 */
	public String verifyToolTipText(String object, String data) {

		logger.debug("Verifying the ToolTip text");
		try {
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_ALT"));
			String expected = data;
			logger.debug("actual" + actual);
			logger.debug("expected" + expected);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS + "-- ToolTip text verified-- " + expected;
			else
				return Constants.KEYWORD_FAIL + " -- ToolTip text not verified-- " + actual + " -- " + expected;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}

	}

	/**
	 * Tarun Sharma This method writes in the input box and hit the enter key.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String writeInInputHitEnter(String object, String data) {
		logger.debug("Writing in text box");

		try {

		WebElement ele=	wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
		ele.clear();
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, data);
		ele.sendKeys(Keys.RETURN);
				
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * Tarun sharma 3 April, 2013 isInvalidAlert() This method handles the
	 * invalid alert which come unnecessarily and give fail result.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String isInvalidAlert(String object, String data) {
		logger.debug("Verify the Alert pop up ");
		try {
			Thread.sleep(2000);
			try {
				if (driver.switchTo().alert() != null) {
					logger.debug("Alert detected");
					Alert alert = explicitWaitForAlert();
					browserSpecificPauseForAlert(object, data);
					alert.accept();
					
					
				} else {
					return Constants.KEYWORD_PASS + " Alert not detected";
				}
			} catch (NoAlertPresentException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return Constants.KEYWORD_PASS + " Alert not detected";
			}

		} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "--some exception occured" + e.getMessage();
		}
		return Constants.KEYWORD_FAIL + " Alert Should not be detected";
	}

	/**
	 * Anil Reddy: /* reloadPage-> This method is used to Reload a page by
	 * pressing F5 Key
	 */
	public String reloadPage(String object, String data) {
		logger.debug("Reloading the page by pressing F5 Key");

		try {
			Actions reloadPage = new Actions(driver);
			reloadPage.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to reload Page" + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "---Page reloaded sucessfully";

	}

	/*
	 * Date:08/08/2013 Anil Reddy: verifyListSelection method is used to verify
	 * whether correct Value is selected in DropDown or Not.
	 */

	public String verifyListSelection(String object, String data) {
		logger.debug("Verifying Whether correct value is selected in Drop down....");
		try {

			String expectedVal = data.trim();
			WebElement droplist = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			String actualVal = null;
			for (int i = 0; i < droplist_cotents.size(); i++) {
				String selected_status = droplist_cotents.get(i).getAttribute(OR.getProperty("ATTRIBUTE_SELECTED"));
				if (selected_status != null) {
					actualVal = droplist_cotents.get(i).getText().trim();
					logger.debug("actual value is > " + actualVal);
					if (actualVal.equals(expectedVal)) {
						return Constants.KEYWORD_PASS + "--Correct value is selected";
					}
				}
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();

		}
		return Constants.KEYWORD_FAIL + "--Wrong value is selected";
	}

	/**
	 * Sandeep Dhamija This method is used to refresh the page
	 * */

	public String refresh(String object, String data) {

		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
		return Constants.KEYWORD_PASS + " Page has been refreshed";
	}

	/**
	 * Sandeep Dhamija Verify that an element gets deleted
	 */
	public String verifyElementIsDeleted(String object, String data) throws InterruptedException {

		try {
			int i = explictWaitForElementSize(object);
			if (i > 0) {
				return Constants.KEYWORD_FAIL + " Element is still present";
			} else {
				return Constants.KEYWORD_PASS + "--Element has been deleted";
			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Puneet This method is used to check if radio button is disabled or not
	 * Modified by Nitin Gupta on 30/09/2014
	 * Added method isEnabled() in place of getAttribute() 
	 */
	public String isRadioDisabled(String object, String data) {
		logger.debug("Verifying radio disabled");
		try {
			boolean checked = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).isEnabled();
			if (checked)
				return Constants.KEYWORD_FAIL + "--RadioButton is Enabled";
			else
				return Constants.KEYWORD_PASS + " - RadioButton is Disabled";

		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not find RadioButton";

		}
	}


	/**
	 * Puneet This method is used to check if radio button is selected or not
	 * Modified by Nitin Gupta on 30/09/2014
	 * Added method isSelected() in place of getAttribute() 
	 * Modified By Surender on 13/10/2014
	 * Added explictWaitForElementUsingFluent instead of explictWaitForElement
	 */
	public String isRadioSelected(String object, String data) {
		logger.debug("Verifying Radio is selected");
		try {
			boolean checked =explictWaitForElementUsingFluent(object).isSelected();
			if (checked) {
				return Constants.KEYWORD_PASS + "Radio is Selected";
			} else {
				return Constants.KEYWORD_FAIL + " Radio is Not selected";
			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not find RadioButton";

		}
	}


	/**
	 * Sohal Bansal 8 april,2013 doubleClick() This method is used to double
	 * click on button
	 */
	public String doubleClick(String object, String data) {
		try {

			WebElement we = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Actions ac = new Actions(driver);
			ac.doubleClick(we).build().perform();
			return Constants.KEYWORD_PASS;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * sohal bansal verifyIconIsInActive this method is used to verify that the
	 * particular icon is disable
	 */

	public String verifyIconIsInActive(String object, String data) {

		try {

			WebElement link = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			if (link.getAttribute(OR.getProperty("ATTRIBUTE_CLASS")).equals(OR.getProperty("DISABLE_ICON_CLASS"))) {
				return Constants.KEYWORD_PASS + "Icon is In Active";
			}

			else {
				return Constants.KEYWORD_FAIL + "Icon is Active";
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * 1 April, 2013 Sohal Bansal verifyTextNotPresentByUsingContains this
	 * method verify the text by using contains() method of string class
	 */
	public String verifyTextNotPresentByUsingContains(String object, String data) {
		logger.debug("Verifying the text");
		try {
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim();
			String expected = data.trim();

			logger.debug("data" + data);
			logger.debug("act" + actual);
			if (actual.contains(expected))
				return Constants.KEYWORD_FAIL + "text present";
			else
				return Constants.KEYWORD_PASS + "text not present";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * sohal bansal 13 march,2013 this method is used to verify the text using
	 * partial link element locator
	 */
	public String verifyTextByUsingpartialLink(String object, String data) {
		logger.debug("Verifying the text");
		try {
			String actual = driver.findElement(By.partialLinkText((OR.getProperty(object)))).getText().trim();
			String expected = data.trim();

			logger.debug("data" + data);
			logger.debug("act" + actual);
			if (actual.contains(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified " + actual + " -- " + expected;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * sohal bansal verifyLinkIsActive 16 april,2013 this method is used to
	 * verify the link is active
	 */

	public String verifyIconIsActive(String object, String data) {

		try {

			WebElement link = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			if (link.getAttribute(OR.getProperty("ATTRIBUTE_CLASS")).equals(data)) {
				return Constants.KEYWORD_PASS + "--Link is Active";
			}

			else {
				return Constants.KEYWORD_FAIL + "--Link is InActive";
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Sohal Bansal 16 april,2013 this method is used to verify that the element
	 * is enable
	 */
	public String verifyEnableElement(String object, String data) {
		logger.debug("Verifying the button text");
		Boolean flag = false;
		try {
			flag = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).isEnabled();

			if (flag) {
				return Constants.KEYWORD_PASS + "--element is enabled";
			} else {
				return Constants.KEYWORD_FAIL + "--element is disabled";

			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + "element not found";
		}
	}

	/**
	 * Sohal Bansal 17 april,2013 verifyLinkNotPresentUsingItsText this method
	 * is used to verify that the particular link text is not present
	 */
	public String verifyLinkNotPresentUsingItsText(String object, String data) {
		try {

			int size = driver.findElements(By.partialLinkText(OR.getProperty(object))).size();
			if (size == 0)
				return Constants.KEYWORD_PASS + "--link text not present";
			else
				return Constants.KEYWORD_FAIL + "--link text present";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Balkrishan Bhola This method is used to verify whether an element is
	 * present after deleting that element
	 * */
	public String isElementPresent(String object, String data) {
		logger.debug("Entered into isElementPresent()");
		try {
			boolean element = false;
			int size = explictWaitForElementSize(object);

			if (size >= 1) {

				element =explictWaitForElementUsingFluent(object).isDisplayed();
				logger.debug("element  " + element);
				if (element == true) {
					logger.debug("element present.." + element);
					return Constants.KEYWORD_FAIL + " -- element present which is not expected ";
				} else
					return Constants.KEYWORD_PASS + " -- element not present ";

			} else
				return Constants.KEYWORD_PASS + "--element not present";
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " --object not found" + e.getMessage();
		}
	}

	/**
	 * This method verifies a link by taking its link text
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyLinkTextByText(String object, String data) {
		logger.debug("Verifying link Text");
		try {
			String actual = wait.until(explicitWaitForElement(By.linkText(OR.getProperty(object)))).getText().trim();
			String expected = data.trim();

			if (actual.equals(expected)) {
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_FAIL + " -- Link text not verified \t actual is =>" + actual + "expected is =>" + expected;
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -- Link text not verified" + e.getMessage();

		}

	}

	/**
	 * 15 April, 2013 Tarun Sharma clickLinkDiscardChanges() this method click
	 * on the Cancel without saving/Discard changes link
	 */
	public String clickLinkDiscardChanges(String object, String data) {
		try {
			int i = explictWaitForElementListByLinkText("cancel_without_saving_link").size();
			int j = explictWaitForElementListByLinkText("discard_changes_link").size();
			if (i > 0) {
				wait.until(explicitWaitForElement(By.linkText("cancel_without_saving_link"))).click();
				browserSpecificPause(object, data);
				return Constants.KEYWORD_PASS + " clicked on cancel without saving link";

			} else if (j > 0) {
				driver.findElement(By.linkText(OR.getProperty("discard_changes_link"))).click();
				browserSpecificPause(object, data);
				return Constants.KEYWORD_PASS + " clicked on Discard Changes link";
			}
			return Constants.KEYWORD_FAIL + " No link found";
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/**
	 * verifyPagenationLinks() method verify whether the pagenation links are
	 * present or not 21 February, 2013 Tarun Sharma
	 * 
	 * @param object
	 * @param data
	 * @return
	 */

	public String verifyPaginationLinks(String object, String data) {

		try {
			String pagenationText = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();

			if (pagenationText.contains(OR.getProperty("first_link")) && pagenationText.contains(OR.getProperty("next_link"))
					&& pagenationText.contains(OR.getProperty("previous_link")) && pagenationText.contains(OR.getProperty("last_link"))) {
				return Constants.KEYWORD_PASS;
			}

			return Constants.KEYWORD_FAIL;
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getLocalizedMessage();
		}

	}

	/**
	 * Sohal Bansal 23 april,2013 scrollAndClick() this method is used to click
	 * on elemnt which is hidden in scroll bar
	 */
	public String scrollAndClick(String object, String data) {
		try {
			logger.debug("enter in scrolling and clicking");
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			int elementPosition = element.getLocation().getY();
			String js = String.format("window.scroll(0, %s)", elementPosition);
			((JavascriptExecutor) driver).executeScript(js);
			element.click();
			return Constants.KEYWORD_PASS + "--element is clicked";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Tarun Sharma verifyValueInDropdown() this method verifies whether a
	 * particular value is present in the dropdown list or not
	 */
	public String verifyValueInDropdown(String object, String data) {
		try {
			logger.debug("looking up the list");

			boolean flag = false;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			String expected = data.trim();

			for (int i = 0; i < options.size(); i++) {

				String actual = options.get(i).getText().trim();
				logger.debug("actual --" + actual);
				logger.debug("expected --" + expected);
				if (actual.equals(expected)) {
					flag = true;
					break;
				} else {
					flag = false;

				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- " + expected + " present in dropdown";
			} else {
				return Constants.KEYWORD_FAIL + " -- " + expected + " not present in dropdown";
			}
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {

		
			return Constants.KEYWORD_FAIL + " Object not found";
		}

	}

	/**
	 * Tarun Sharma 25 April, 2013 isWebElementDisplayed This method verify that
	 * whether an element is displayed on the page or not
	 */
	public String isWebElementDisplayed(String object, String data) {
		logger.debug("Entered into isWebElementDisplayed");

		try {
			boolean element = false;
			int size = explictWaitForElementSize(object);

			logger.debug(size + " no of objects found");
			if (size >= 1) {
				element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).isDisplayed();
				logger.debug("element is displayed ");
				if (element) {
					logger.debug("element present.." + element);
					return Constants.KEYWORD_PASS + " -- element is being displayed";
				} else
					return Constants.KEYWORD_FAIL + " -- element present but is hidden ";

			} else
				return Constants.KEYWORD_FAIL + " element not present";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " --object not found" + e.getMessage();
		}
	}

	/**
	 * Tarun Sharma 25 April, 2013 isWebElementNotDisplayed verifies that
	 * element is not displayed
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String isWebElementNotDisplayed(String object, String data) {
		logger.debug("Entered into isWebElementDisplayed");

		try {
			boolean element = false;
			int size = explictWaitForElementSize(object);

			logger.debug(size + " no of objects found");
			if (size >= 1) {
				element =explictWaitForElementUsingFluent(object).isDisplayed();
				logger.debug("element is displayed ");
				if (element) {
					logger.debug("element present.." + element);
					return Constants.KEYWORD_FAIL + " -- element is being displayed which is not expected";
				} else
					return Constants.KEYWORD_PASS + " -- element present but is hidden ";

			} else
				return Constants.KEYWORD_FAIL + " element not present";
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " --object not found" + e.getMessage();
		}
	}

	/**
	 * Puneet 23/04/2013 This method is used to verify that element is disabled
	 * or not (data takes 2 value viz. attribute name and its value)
	 */
	public String isElementDisable(String object, String data) {

		try {
			String temp[] = data.split(Constants.Object_SPLIT);
			String att_name = temp[0];
			String att_val = temp[1];
			WebElement link = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			if (link.getAttribute(att_name).equals(att_val)) {
				return Constants.KEYWORD_PASS + "--Icon is Disabled";
			}

			else {
				return Constants.KEYWORD_FAIL + "--Icon is Enabled";
			}

		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Puneet This Method is used to find whether element is present or not
	 */
	public String isWebElementNotPresent(String object, String data) {
		logger.debug("Entered into isWebElementPresent()");
		try {
			int element = driver.findElements(By.xpath(OR.getProperty(object))).size();
			if (element == 0) {
				logger.debug("webElement not present.." + element);
				return Constants.KEYWORD_PASS + " -- No webElement present";
			} else
				return Constants.KEYWORD_FAIL + " -- webElement present -- ";
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Puneet This method is used to verify mouseOver text by title
	 */
	public String verifyMouseOverText(String object, String data) {
		logger.debug("verifying Mouseover text");
		try {

			boolean flag = false;
			String actual = "";
			actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_TITLE"));
			logger.debug("data" + data);
			logger.debug("act" + actual);
			if (actual.equals(data)) {
				flag = true;
			}
			if (flag)
				return Constants.KEYWORD_PASS + "--- Mouseover text matched";
			else
				return Constants.KEYWORD_FAIL + " -- Mouseover text not verified " + actual + " -- " + data;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Sandeep Dhamija Give a random value in input
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String randomWriteInInput(String object, String data) {
		logger.debug("Writing in text box");

		try {

			WebElement ele=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, "number: "+ createRandomNum()); // modified
	          
			return Constants.KEYWORD_PASS;
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		

	}

	/**
	 * Tarun Sharma clicks on a link by getting partial link.
	 * 
	 * @param object
	 * @param data
	 * @return added in version 1.40
	 */
	public String clickLink_partialLinkText(String object, String data) {

		logger.debug("Clicking on link ");
		try {
			driver.findElement(By.partialLinkText(OR.getProperty(object))).click();
			browserSpecificPause(object, data);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -- " + OR.getProperty(object) + " link not present";
		}
	}

	/*
	 * Sohal Bansal 7 may,2013 verifyLinkInActive This method is used to verify
	 * that the link is in active
	 */
	public String verifyLinkInActive(String object, String data) {
		try {

			logger.debug("verifying link is inactive");
			String attribute = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_CLASS"));
			logger.debug("actual attribute value: " + attribute);
			logger.debug("expected attribute value: " + OR.getProperty("CLASS_VALUE_INACTIVE"));
			if (attribute.equals(OR.getProperty("CLASS_VALUE_INACTIVE"))) {
				return Constants.KEYWORD_PASS + "--link is inactive";
			} else
				return Constants.KEYWORD_FAIL + "--link is not inactive";
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Sohal Bansal 7 may,2013 verify that only one element is present for xpath
	 * passed
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyElementPresentOnce(String object, String data) {

		logger.debug("verifying the elemnt is present once ");
		List<WebElement> elements = explictWaitForElementList(object);
		if (elements.size() == 1)
			return Constants.KEYWORD_PASS + "--element is present once";
		else
			return Constants.KEYWORD_FAIL + "--more than one element is present";
	}

	/*
	 * Sohal Bansal 13 may,2013 scroll this method is used to scroll the page
	 * downwards
	 */
	public String scroll(String object, String data) {
		try {
			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)", "");
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Sandeep Dhamija Verify that an element is not present
	 */
	public String verifyElementIsNotPresent(String object, String data) throws InterruptedException {

		try {
			int i = explictWaitForElementSize(object);
			if (i > 0) {
				return Constants.KEYWORD_FAIL + " Element is still present";
			} else {
				return Constants.KEYWORD_PASS + "--Element is not present";
			}
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/*
	 * Sohal Bansal 22 may,2013 verifyElementIsMovable this method is used to
	 * verify that the element is dragged and move to any place over the window
	 */
	public String verifyElementIsMovable(String object, String data) {
		try {

			logger.debug("move the element");
			WebElement we = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			new Actions(driver).dragAndDropBy(we, 200, -50).build().perform();
			return Constants.KEYWORD_PASS + "--element is moved";
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Sohal Bansal 25 may,2013 verify that the elements of drop down list are
	 * in sorted order
	 * 
	 * Modified By Karan on 26 Oct, 2015
	 * Display Actual and Sorted List in Logger and get options with element locator By.xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyDropDownListElementsInSorted(String object, String data) {
		try {

			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			select.click();
			boolean flag = false;
			List<WebElement> options = driver.findElements(By.xpath(OR.getProperty(object) + OR.getProperty("OPTION_TAG_DISPLAY"))); // Modified By Karan
			List<String> actual_list = new ArrayList<String>();
			List<String> sortedlist = new ArrayList<String>();
			for (int i = 0; i < options.size(); i++) {
				String name = options.get(i).getText();
				actual_list.add(name);
				sortedlist.add(name);
			}
			Collections.sort(sortedlist, String.CASE_INSENSITIVE_ORDER);
			logger.debug("Actual List = " + actual_list );   // Modified By Karan
			logger.debug("Sorted List = " + sortedlist );	// Modified By Karan
			for (int i = 0; i < sortedlist.size(); i++) {
				if (sortedlist.get(i).equals(actual_list.get(i))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS + "--elements in drop down are in sorted order";
			else
				return Constants.KEYWORD_FAIL + "--elements are not in sorted order";
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	public String handleCalender(String object, String data) {
		logger.debug("Handling the windows....");
		try {
			String mainwindow = "";
			String newwindow = "";
			Set<String> windowids = driver.getWindowHandles();
			Iterator<String> ite = windowids.iterator();
			mainwindow = ite.next();
			newwindow = ite.next();
			// // logger.debug(newwindow);
			driver.switchTo().window(newwindow);
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			ele.click();
			driver.switchTo().window(mainwindow);

		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception nse) {
			// TODO Auto-generated catch block
			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * 07/02/2013 Balkrishan Bhola This method handles Inconsistent alerts .
	 * this method is used when we are not sure whether an alert will come or
	 * not.
	 */
	public String isInConsistentAlert(String object, String data) {
		logger.debug("Verify the Invalid Alert pop up ");
		boolean presentFlag = false;
		try {
			Alert alert = driver.switchTo().alert();
			if (!presentFlag) {
				logger.debug("Alert detected");
				alert.accept();
				browserSpecificPauseForAlert(object, data);
				presentFlag = true;
			}
			if (presentFlag) {	
				return Constants.KEYWORD_PASS;
			} else {
				return Constants.KEYWORD_PASS;
			}
		} catch (Exception e) {
		
			return Constants.KEYWORD_PASS + " -- Alert not detected";
		}
	}

	/*
	 * kritika 11 July, 2013 get value of css property
	 */
	/*
	 * MOdified By Sanjay Sharma On 18/11/2013 Added Logger to Actual And
	 * Expected value
	 */
	public String verifyCssProperty(String object, String data) {
		try {
			String property[] = data.split(":",2);
			String exp_prop = property[0];
			String exp_value = property[1];
			boolean flag = false;
			String prop = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getCssValue(exp_prop);
			logger.debug(prop);
			logger.debug(exp_value);
			if (prop.trim().equals(exp_value.trim())) 
			{
				flag = true;
			}
			if (flag)
					return Constants.KEYWORD_PASS + "--actvalue-" + prop + "--expectes-" + exp_value;
			else
					return Constants.KEYWORD_FAIL;
		} 	
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " " + e.getMessage();
		}
	}

	/*
	 * Sohal Bansal 17 may,2013 writeDateInInput this method is used to write
	 * date in input box passinf from OR.properties file
	 */
	public String writeDateInInput(String object, String data) {
		try {

			String[] objects = object.split(Constants.Object_SPLIT);
			wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objects[0])))).clear();

		WebElement ele=	wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objects[0]))));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, OR.getProperty(objects[1]));
		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--data passed in input box successfully";
	}

	/*
	 * Date:07/23/2013 Anil Reddy: enterCurrentStartorDueDate method is used to
	 * enter current date for start/due date fields of Course Evaluation Tab.
	 */

	public String enterCurrentStartorDueDate(String object, String data) {
		logger.debug("Entering Current Start date");
		try {

			int flag = 0;
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String startDate_dueDate = timeFormat.format(calendar.getTime());
		WebElement ele=	wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			 ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, startDate_dueDate);
				
			flag = 1;
			if (flag == 1) {
				return Constants.KEYWORD_PASS + "---start/due date has been Entered.. ";
			} else {
				return Constants.KEYWORD_FAIL + "---start/due date has not been Entered.. ";
			}
		}catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} 
		catch (Exception e) {
		

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/*
	 * Date:07/24/2013 Anil Reddy: selectStartorDueHours method is used to enter
	 * the start hours in Course Evaluation Tab.
	 */

	public String selectStartorDueHours(String object, String data) {
		logger.debug("Entering Hours");
		try {

			int hrs = 0;

			Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("K");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String Hours = timeFormat.format(date.getTime());
			int hrs2 = Integer.parseInt(Hours);
			if (data.equals(Constants.VALUE_60)) {
				hrs = Integer.parseInt(Hours);
				if (hrs == 11) {
					String currentAmorPM = Constants.AM;
					SimpleDateFormat timeFormat3 = new SimpleDateFormat("a");
					timeFormat3.setTimeZone(TimeZone.getTimeZone("US/Central"));
					String AmorPm = timeFormat3.format(date.getTime());
					hrs = hrs + 1;
					Hours = String.valueOf(hrs);
					selectList(object, Hours);
					if (object.equals("start_hour_dd")) {
						if (AmorPm.equals(currentAmorPM)) {
							object = "start_am_pm_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "start_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							SimpleDateFormat timeFormat1 = new SimpleDateFormat("MM/dd/yyyy");
							timeFormat1.setTimeZone(TimeZone.getTimeZone("US/Central"));
							String dt = timeFormat1.format(date.getTime());
							Calendar c = Calendar.getInstance();
							try {
								c.setTime(timeFormat.parse(dt));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							c.add(Calendar.DATE, 1); // number of days to add
							String dueDate = timeFormat1.format(c.getTime());
							clearInput("end_date_input", "");
							writeInInput("end_date_input", dueDate);
							enterDueDate = 1;
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}
					} else {
						if (AmorPm.equals(currentAmorPM)) {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}
					}
				}
				hrs = hrs + 1;
				Hours = String.valueOf(hrs);
				selectList(object, Hours);

			} else if (data.equals(Constants.VALUE_65)) {

				if (hrs2 == 11) {
					String currentAmorPM = Constants.AM;
					SimpleDateFormat timeFormat4 = new SimpleDateFormat("a");
					timeFormat4.setTimeZone(TimeZone.getTimeZone("US/Central"));
					String AmorPm = timeFormat4.format(date.getTime());
					hrs2 = hrs2 + 1;
					Hours = String.valueOf(hrs2);
					selectList(object, Hours);
					if (object.equals("start_hour_dd")) {
						if (AmorPm.equals(currentAmorPM)) {
							object = "start_hour_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "start_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							SimpleDateFormat timeFormat12 = new SimpleDateFormat("MM/dd/yyyy");
							timeFormat12.setTimeZone(TimeZone.getTimeZone("US/Central"));
							String dt = timeFormat12.format(date.getTime());
							Calendar c = Calendar.getInstance();
							try {
								c.setTime(timeFormat.parse(dt));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							c.add(Calendar.DATE, 1); // number of days to add
							String dueDate = timeFormat12.format(c.getTime());
							clearInput("end_date_input", "");
							writeInInput("end_date_input", dueDate);
							// enterDueDate=1;
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}
					} else {
						if (AmorPm.equals(currentAmorPM)) {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							// return
							// Constants.KEYWORD_PASS+"  Hour has been Entered.. ";
						}
					}
				}
				hrs2 = hrs2 + 1;
				Hours = String.valueOf(hrs2);
				selectList(object, Hours);
				return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
			} else if (data.equals(Constants.VALUE_70)) {

				if (hrs2 == 11) {
					String currentAmorPM = Constants.AM;
					SimpleDateFormat timeFormat1 = new SimpleDateFormat("a");
					timeFormat1.setTimeZone(TimeZone.getTimeZone("US/Central"));
					String AmorPm = timeFormat1.format(date.getTime());
					hrs2 = hrs2 + 1;
					Hours = String.valueOf(hrs2);
					selectList(object, Hours);
					if (object.equals("start_hour_dd")) {
						if (AmorPm.equals(currentAmorPM)) {
							object = "start_am_pm_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "start_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							SimpleDateFormat timeFormat10 = new SimpleDateFormat("MM/dd/yyyy");
							timeFormat1.setTimeZone(TimeZone.getTimeZone("US/Central"));
							String dt = timeFormat10.format(date.getTime());
							Calendar c = Calendar.getInstance();
							try {
								c.setTime(timeFormat.parse(dt));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							c.add(Calendar.DATE, 1); // number of days to add
							String dueDate = timeFormat10.format(c.getTime());
							clearInput("end_date_input", "");
							writeInInput("end_date_input", dueDate);
							enterDueDate = 1;
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}
					} else {
						if (AmorPm.equals(currentAmorPM)) {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.PM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}

						else {
							object = "end_am_pm_dd";
							selectChangedAMorPM(object, Constants.AM);
							return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
						}
					}
				}
				hrs2 = hrs2 + 1;
				Hours = String.valueOf(hrs2);
				selectList(object, Hours);
				return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
			} else if (Hours.equals(Constants.ZERO_VALUE)) {
				Hours = String.valueOf(Constants.VALUE_12);
				selectList(object, Hours);
				// return Constants.KEYWORD_PASS+"  Hour has been Entered.. ";
			}
			selectList(object, Hours);
			return Constants.KEYWORD_PASS + "  Hour has been Entered.. ";
		} catch (Exception e) {

			e.printStackTrace();
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/*
	 * Date:07/24/2013 Anil Reddy: selectStartMinutes method is used to enter
	 * the start minutes in Course Evaluation Tab.
	 */

	public String selectStartMinutes(String object, String data) {
		logger.debug("Entering start Minutes");
		try {

			String allObjs[] = object.split(Constants.DATA_SPLIT);
			object = allObjs[0];
			String hr = allObjs[1];
			String FinalNum = "";
			int num = 0;
			Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("mm");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String Min = timeFormat.format(date.getTime());
			logger.debug(Min);
			int Minutes = Integer.parseInt(Min);
			logger.debug("Minutes=" + Minutes);
			if ((Minutes % 5) == 0) {
				num = Minutes + 5;
				if (num == 5) {
					String num3 = Constants.VALUE_0 + num;
					selectList(object, num3);
					logger.debug("when num==5:--" + num3);
					return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";

				}
				if (num >= 60) {
					data = String.valueOf(num);
					data = ":" + data;
					selectStartorDueHours(hr, data);
					if (!(data.equals(Constants.VALUE_12))) {
						FinalNum = Constants.VALUE_DOUBLE_ZERO;
						selectList(object, FinalNum);
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					}

					return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
				}
				FinalNum = String.valueOf(num);
				FinalNum = ":" + FinalNum;
				selectList(object, FinalNum);
				return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
			} else {
				int unitdigit = Minutes % 10;
				logger.debug("Unitdigit=" + unitdigit);
				num = Minutes - unitdigit;
				logger.debug("num=" + num);
				if (unitdigit > 0 && unitdigit < 4) {
					num = num + 5;
					if (num == 5) {
						String num3 = Constants.VALUE_0 + num;
						selectList(object, String.valueOf(num3));
						logger.debug("when num==5:--" + num3);
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					} else {
						String num3 = ":" + num;
						selectList(object, String.valueOf(num3));
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					}
				} else if (unitdigit == 4) {
					num = num + 10;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						FinalNum = Constants.VALUE_DOUBLE_ZERO;
						selectList(object, FinalNum);
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					}
					data = String.valueOf(num);
					data = ":" + data;
					selectStartorDueHours(hr, data);
					String num8 = ":" + num;
					selectList(object, String.valueOf(num8));
					return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
				} else if (unitdigit > 5 && unitdigit < 9) {
					num = num + 10;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						FinalNum = Constants.VALUE_DOUBLE_ZERO;
						selectList(object, FinalNum);
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					}
				} else if (unitdigit == 9) {
					num = num + 15;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						num = num - 60;
						if (num == 5) {
							String num3 = Constants.VALUE_0 + num;
							selectList(object, num3);
							return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";

						}
						String num4 = String.valueOf(num);
						num4 = ":" + num4;
						selectList(object, num4);
						return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
					}
				}
				FinalNum = String.valueOf(num);
				FinalNum = ":" + FinalNum;
				selectList(object, FinalNum);
				return Constants.KEYWORD_PASS + "  Start Minutes has been Entered.. ";
			}
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/*
	 * Date:07/24/2013 Anil Reddy: selectendMinutes method is used to enter the
	 * end minutes in Course Evaluation Tab.
	 */

	public String selectendMinutes(String object, String data) {
		logger.debug("Entering End minutes");
		try {

			String allObjs[] = object.split(Constants.DATA_SPLIT);
			object = allObjs[0];
			String hr = allObjs[1];
			String endFinalMin = "";
			int num = 0;
			Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("mm");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String Min = timeFormat.format(date.getTime());
			logger.debug(timeFormat);
			logger.debug(Min);
			int Minutes = Integer.parseInt(Min);
			logger.debug("Minutes=" + Minutes);
			if ((Minutes % 5) == 0) {
				int endMint = Minutes + 10;
				if (endMint == 60) {
					data = String.valueOf(endMint);
					data = ":" + data;
					selectStartorDueHours(hr, data);
					endFinalMin = Constants.VALUE_DOUBLE_ZERO;
					selectList(object, endFinalMin);
					return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

				}
				if (endMint > 60) {
					data = String.valueOf(endMint);
					data = ":" + data;
					selectStartorDueHours(hr, data);
					endFinalMin = Constants.VALUE_05;
					selectList(object, endFinalMin);
					return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

				}
				String FinalEndMint = String.valueOf(endMint);
				FinalEndMint = ":" + FinalEndMint;
				selectList(object, FinalEndMint);
				return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";
			} else {
				int unitdigit = Minutes % 10;
				logger.debug("Unitdigit=" + unitdigit);
				num = Minutes - unitdigit;
				logger.debug("num=" + num);
				if (unitdigit > 0 && unitdigit < 4) {
					num = num + 10;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						endFinalMin = Constants.VALUE_DOUBLE_ZERO;
						selectList(object, endFinalMin);
						return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

					}
					String num5 = String.valueOf(num);
					num5 = ":" + num5;
					selectList(object, num5);
					return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

				} else if (unitdigit == 4) {
					num = num + 15;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						endFinalMin = Constants.VALUE_05;
						selectList(object, endFinalMin);
						return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

					}
					data = String.valueOf(num);
					data = ":" + data;
					// selectStartorDueHours(hr, data);
					selectList(object, data);
					return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";
				} else if (unitdigit > 5 && unitdigit < 9) {
					num = num + 15;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						endFinalMin = Constants.VALUE_05;
						selectList(object, endFinalMin);
						return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

					}
					String num6 = String.valueOf(num);
					num6 = ":" + num6;
					selectList(object, num6);
					return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";

				} else if (unitdigit == 9) {
					num = num + 20;
					if (num >= 60) {
						data = String.valueOf(num);
						data = ":" + data;
						selectStartorDueHours(hr, data);
						num = num - 60;
						String num7 = Constants.VALUE_0 + num;
						String num12 = String.valueOf(num7);
						selectList(object, num12);
						return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";
					} else if (num < 60) {
						String num8 = String.valueOf(num);
						num8 = ":" + num8;
						selectList(object, num8);
						return Constants.KEYWORD_PASS + "  End Minutes has been Entered.. ";
					}
				}
			}

		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "---End Minutes has been Entered..";
	}

	/*
	 * Date:07/24/2013 Anil Reddy: selectAMorPM method is used to enter the
	 * AM/PM in Course Evaluation Tab.
	 */

	public String selectAMorPM(String object, String data) {
		logger.debug("Entering Am/Pm");
		try {

			Boolean flag = false;
			Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("a");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String AmorPm = timeFormat.format(date.getTime());
			logger.debug(AmorPm);
			selectList(object, AmorPm);
			flag = true;
			if (flag) {
				return Constants.KEYWORD_PASS + "  Am/Pm has been Entered.. ";
			} else {
				return Constants.KEYWORD_FAIL + "   Am/Pm has been Not Entered.. ";
			}

		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/*
	 * Date:07/24/2013 Anil Reddy: selectChangedAMorPM method is used to enter
	 * the AM/PM in Course Evaluation Tab.
	 */

	public String selectChangedAMorPM(String object, String data) {
		logger.debug("Entering Am/Pm");
		try {

			Boolean flag = false;
			Date date = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("a");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String AmorPm = timeFormat.format(date.getTime());
			logger.debug(AmorPm);
			if (data.equals("PM")) {
				selectList(object, data);
				flag = true;
			} else if (data.equals("AM")) {
				selectList(object, data);
				flag = true;
			} else {
				selectList(object, AmorPm);
				flag = true;
			}

			if (flag) {
				return Constants.KEYWORD_PASS + "  Am/Pm has been Entered.. ";
			} else {
				return Constants.KEYWORD_FAIL + "   Am/Pm has been Not Entered.. ";
			}

		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Sandeep Dhamija 9 July, 2013 This method handles pagination and selects
	 * any web element
	 * 
	 * @param object
	 * @param data
	 * @return
	 * 
	 * Modified By Karan Sood on 22/12/2014 :- Added code to check checkbox using following sibling node-set
	 * Modified By Navdeep on 13/07/2105 :- Handled WebDriver Exception(Element is not clickable at point)
	 */
	public String selectElement(String object, String data) {
		logger.debug("inside selectElement..");
		List<WebElement> element_List=null;
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];

			while (true) {
				 element_List = driver.findElements(By.xpath(OR.getProperty(element_Start) + data + OR.getProperty(element_End)));

				List<WebElement> next_Link_List = driver.findElements(By.xpath(OR.getProperty("pagination_next_link")));

				if (element_List.size() > 0) {
					// condition if the webelement is check-box

					if (element_List.get(0).getAttribute(OR.getProperty("ATTRIBUTE_TYPE")).equals(OR.getProperty("CHECKBOX"))) 
					{
						String checked = element_List.get(0).getAttribute("checked");
						if (checked == null) { // checkbox is unchecked
							
							WebElement ele1 = driver.findElement(By.xpath(OR.getProperty(element_Start) + data + OR.getProperty(element_End)+"/following-sibling::*"));  // if Xpath is made using input as last nodes.
			                if(ele1.getTagName().equals(Constants.LABEL)){
			                    JavascriptExecutor executor = (JavascriptExecutor) driver;
			                    executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
			                    executor.executeScript("arguments[0].click();", ele1);
			                    }							
							return Constants.KEYWORD_PASS + " clicked on check-box";
						}
						else {
							return Constants.KEYWORD_PASS + " check-box is already checked";

						}

					}
					// Webelement is not a checkbox
					element_List.get(0).click();
					Thread.sleep(3000);
					return Constants.KEYWORD_PASS + " clicked on element";
				} 
				else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}

			}

		}
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
						if(new ApplicationSpecificKeywordEventsUtil().clickJs(element_List.get(0)).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
						else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " " + e.getMessage();

		}
	}

	/**
	 * Sandeep Dhamija 9 July, 2013 This method handles pagination and verifies
	 * that web element is Present
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyElementIsPresent(String object, final String data) {
		logger.debug("inside verifyElementIsPresent..");
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			final String element_Start = element[0];
			final String element_End = element[1];

			while (true) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class);

				List<WebElement> element_List = wait.until(new Function<WebDriver, List<WebElement>>() {

					@Override
					public List<WebElement> apply(WebDriver driver) {
						return driver.findElements(By.xpath(OR.getProperty(element_Start) + data + OR.getProperty(element_End)));
					}

				});

				List<WebElement> next_Link_List = explictWaitForElementList("pagination_next_link");

				if (element_List.size() > 0) {
					return Constants.KEYWORD_PASS + " element is present";
				} else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}

			}}

//		}catch(TimeoutException ex)
//		
//		{
//			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
//		} 
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}

	/**
	 * @since 07/24/13
	 * @author Balkrishan Bhola This method is used to verify the records in the
	 *         page
	 */
	public String verifyPageRecord(String object, String data) throws Exception {
		logger.debug("Entered into verifyPageRecord()");
		try {

			int count = 0;
			String pageobjects[] = object.split(Constants.DATA_SPLIT);
			object = pageobjects[0].trim();
			List<WebElement> expected;
			WebElement list = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(pageobjects[2].trim()))));
			Select sel = new Select(list);
			@SuppressWarnings("rawtypes")
			List allOptions = sel.getOptions();
			int size = allOptions.size();
			logger.debug("Total no. of Pages:- " + size);
			String str = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(pageobjects[1].trim())))).getText();
			String totalRecords[] = str.split("of ");
			logger.debug("Total no. of Records:- " + totalRecords[1]);
			int rec = Integer.parseInt(totalRecords[1]);
			int lastPageElements = rec % Integer.parseInt(data);
			if (lastPageElements == 0) {
				lastPageElements = Integer.parseInt(data);
			}
			logger.debug("Records in Last Page:- " + lastPageElements);

			if (size > 2) {
				while (true) {
					int nextSize = driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
					expected = explictWaitForElementList(object);
					logger.debug("Clicked on " + lastPageElements + " no. of Pages.");
					if (count == 2) {
						if (driver.findElements(By.linkText(OR.getProperty("last_link"))).size() > 0) {
							driver.findElement(By.linkText(OR.getProperty("last_link"))).click();
							expected = explictWaitForElementList(object);
							if (expected.size() == lastPageElements) {
								return Constants.KEYWORD_PASS;
							} else
								return Constants.KEYWORD_FAIL;
						} else {
							expected = explictWaitForElementList(object);
							if (expected.size() == lastPageElements) {
								return Constants.KEYWORD_PASS;
							} else
								return Constants.KEYWORD_FAIL;
						}

					}
					if (nextSize > 0 && driver.findElements(By.linkText(OR.getProperty("last_link"))).size() > 0) {
						if (Integer.parseInt(data) == expected.size()) {
							count++;
							driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
						} else
							return Constants.KEYWORD_FAIL;
					} else if (driver.findElements(By.linkText(OR.getProperty("last_link"))).size() == 0) {
						if (expected.size() == lastPageElements) {
							return Constants.KEYWORD_PASS;
						} else
							return Constants.KEYWORD_FAIL;
					}
				}
			} else {
				if (driver.findElements(By.linkText(OR.getProperty("last_link"))).size() == 0) {
					expected = explictWaitForElementList(object);
					if (expected.size() == lastPageElements) {
						return Constants.KEYWORD_PASS;
					} else
						return Constants.KEYWORD_FAIL;
				} else {
					if (driver.findElements(By.linkText(OR.getProperty("last_link"))).size() > 0) {
						logger.debug("Clicked on Last Page.");
						driver.findElement(By.linkText(OR.getProperty("last_link"))).click();
						expected = explictWaitForElementList(object);
						if (expected.size() == lastPageElements) {
							return Constants.KEYWORD_PASS;
						} else
							return Constants.KEYWORD_FAIL;
					} else {
						driver.findElement(By.linkText(OR.getProperty("last_link"))).click();
						expected =explictWaitForElementList(object);
						if (expected.size() == lastPageElements) {
							return Constants.KEYWORD_PASS;
						} else
							return Constants.KEYWORD_FAIL;
					}
				}
			}

		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 07/11/13
	 * @author Balkrishan Bhola This method is used to verify sorting(Date) in
	 *         Ascending order ,pass DATE FORMAT from Excel.
	 * 
	 *         Modified on 08/23/13 [Handled pagination and Null Values if
	 *         appears in Date.]
	 */
	public String verifyDateInAscending(String object, String data) {
		try {
			int size=0;
			logger.debug("Entered into verifyDateInAscending()");
			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			while (true) {
				size = explictWaitForElementSize("pagination_next_link");
                List<WebElement> expected = explictWaitForElementList(object);
                if (size > 0) { 
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
                    browserSpecificPause(object, data);
                   
                } else {
                    expected = explictWaitForElementList(object);
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
                        break;
                    }
                }
			}
			final DateFormat df = new SimpleDateFormat(data.trim());
			Collections.sort(actual, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					try {
						return df.parse(o1).compareTo(df.parse(o2));
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);
			if (real.equals(actual)) {
				size = driver.findElements(By.xpath(OR.getProperty("pagination_first_lnk"))).size();
                if (size > 0) {
                    driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
                }
				return Constants.KEYWORD_PASS + " -- Dates are sorted in ascending order";
			} else
				return Constants.KEYWORD_FAIL + " -- Dates are not sorted in Descending Order";
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * 07/31/2013 Anil Reddy isValueNotDisplayedInDropdown() this method
	 * verifies whether a particular value is not present in the dropdown list
	 * or not--- It returns Pass if the value is not present.
	 */
	public String isValueNotDisplayedInDropdown(String object, String data) {
		try {
			logger.debug("Searching for the value");

			boolean flag = false;
			WebElement select =wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			String expected = data.trim();

			for (int i = 0; i < options.size(); i++) {
				String actual = options.get(i).getText().trim();
				logger.debug("actual --" + actual);
				logger.debug("expected --" + expected);
				if (actual.equals(expected)) {
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
			if (flag) {
				return Constants.KEYWORD_FAIL + " -- " + expected + "--present in dropdown";
			} else {
				return Constants.KEYWORD_PASS + " -- " + expected + "--not present in dropdown";
			}
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

		
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/*
	 * Date:07/15/2013 Anil Reddy: enterFutureDuedateForCE method is used to
	 * enter due date for Course Evaluation Tab.
	 */

	public String enterFutureDuedateForCE(String object, String data) {
		logger.debug("Entering Due date");
		try {
			data = OR.getProperty("ce_future_due_date_txt");
			WebElement ele=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,data);
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--Due Date entered successfully";
	}

	/**
	 * S.Dhamija- 15 July, 2013 This method is used to verify that printable
	 * view gets opened in new window
	 * 
	 * */
	public String verifyNewWindowURL(String object, String data) {
		logger.debug("Inside verifyPrintableViewIsOpening() ");

		try {
			String mainwindow = "";
			String newwindow = "";
			Set<String> windowids = driver.getWindowHandles();
			Iterator<String> ite = windowids.iterator();
			mainwindow = ite.next();
			newwindow = ite.next();
			driver.switchTo().window(newwindow);
			Thread.sleep(5000);
			String url = driver.getCurrentUrl();
			driver.close();
			driver.switchTo().window(mainwindow);
			logger.debug(url);
			logger.debug(data);
			if (url.contains(data)) {
				return Constants.KEYWORD_PASS + " printable window opens";
			} else {
				return Constants.KEYWORD_FAIL + " printable window does not open";
			}

		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Sandeep Dhamija. 14 August, 2013 Move to a newly opened window
	 * 
	 * @param object
	 * @param data
	 * @return
	 * @throws InterruptedException
	 */
	public String moveToNewWindow(String object, String data) throws InterruptedException {
		logger.debug("move to new window....");
		try {
			String mainwindow = "";
			String newwindow = "";
			Set<String> windowids = driver.getWindowHandles();
			Iterator<String> ite = windowids.iterator();
			mainwindow = ite.next();
			newwindow = ite.next();
			driver.switchTo().window(newwindow);

		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * Date:07/30/2013 Anil Reddy: verifyDateFormat method is used to verify
	 * whether date is displayed in correct format(i.e MM/DD/YYYY)
	 */

	public String verifyDateFormat(String object, String data) {
		logger.debug("verifying the date format");
		try {
			String date_text = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String date[] = date_text.split("/");
			Boolean flag = false;

			int month = Integer.parseInt(date[0]);
			int day = Integer.parseInt(date[1]);
			int year = Integer.parseInt(date[2]);

			for (int i = 0; i < date.length; i++) {
				if (month <= 12 && day <= 31 && year > 2000) {
					flag = true;
					break;
				}

			}
			if (flag) {

				return Constants.KEYWORD_PASS + "--date is in correct format";
			}

			else {
				return Constants.KEYWORD_FAIL + "--date is not in correct format";
			}

		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/*
	 * Date:08/06/2013 Anil Reddy: enterBeforeDueDate method is used to enter a
	 * Due date before current Date.
	 */

	public String enterBeforeDueDate(String object, String data) {
		logger.debug("Entering Due date....");

		try {
			data = OR.getProperty("ce_fall_due_date_txt");

			WebElement ele=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,data);
			return Constants.KEYWORD_PASS + "--Due Date entered successfully";
		} 
		
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		

	}

	/**
	 * Sandeep Dhamija This method is used to click on browser's back button
	 * */

	public String navigateBackward(String object, String data) {

		try {
			driver.navigate().back();
		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
		return Constants.KEYWORD_PASS + " clicked on back button";
	}

	/*
	 * Sandeep Dhamija 11/6/2013 This methods on an object whether pagination
	 * links are present or not pass xpath of element as 'object' argument
	 */
	/*
	 * Sandeep Dhamija 11/6/2013 This methods on an object whether pagination
	 * links are present or not pass xpath of element as 'object' argument
	 */
	public String clickElement(String object, String data) {
		logger.debug("inside clickElement()..");
		List<WebElement> element_List=null;
		try {
			
			while (true) {
				 element_List = explictWaitForElementList(object);
				List<WebElement> next_Link_List = explictWaitForElementListByLinkText("next_link");

				if (element_List.size() > 0)
				{
					//WebElement ele=wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
					element_List.get(0).click();
							//ele.click();
					return Constants.KEYWORD_PASS + " clicked on element";
				} else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
					Thread.sleep(2000);
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}

			}
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
						if(new ApplicationSpecificKeywordEventsUtil().clickJs(element_List.get(0)).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
						else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				}
		
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL+e.getMessage();
		
	}

	}

	/*
	 * Puneet This method is used to verify that textbox input value is null
	 */
	public String verifyNullValue(String object, String data) {
		logger.debug("Verifying the Textbox text is null");
		try {
			data = "";
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			String expected = data;

			logger.debug("actual" + actual);
			logger.debug("expected" + expected);
			if (actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified--" + actual + " -- " + expected;
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * Balkrishan Bhola This method is used to verify whether dropdowns selected
	 * value are present or not
	 * 
	 * Added trim() by Sanjay Sharma Date: 08/10/13
	 */
	public String verifyDropdownSelectedValue(String object, String data) {
		logger.debug("Entered into verifyDropdownSelectedValue()");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Select sel = new Select(select);
			String defSelectedVal = sel.getFirstSelectedOption().getText();
			logger.debug(defSelectedVal);
			logger.debug(data);
			if (defSelectedVal.trim().equals(data.trim())) {
				return Constants.KEYWORD_PASS + " -- Default value present ";
			} else {
				return Constants.KEYWORD_FAIL + " -- Default value not present ";
			}
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}

	/*
	 * 09 Sept,2013 Mayank Saini This method is used to verify that all values
	 * in column of table are same Modified on 24 OCt,2013
	 */

	public String verifyAllvaluesOfColumn(String object, String data) {
		logger.debug("Verifying all values ol column");
		try {
			boolean flag = false;
			while (true) {

				int size = explictWaitForElementListByLinkText("next_link").size();
				List<WebElement> all_values = explictWaitForElementList(object);
				if (size > 0) {
					for (int i = 0; i < all_values.size(); i++) {
						String value = all_values.get(i).getText();
						if (value.toLowerCase().trim().contains(data.toLowerCase().trim())) {

							flag = true;
						} else {
							flag = false;
							break;
						}
					}
					driver.findElement(By.linkText(OR.getProperty("next_link"))).click();

				} else {
					all_values = explictWaitForElementList(object);
					for (int i = 0; i < all_values.size(); i++) {
						String value = all_values.get(i).getText();
						if (value.toLowerCase().trim().contains(data.toLowerCase().trim())) {
							flag = true;
						} else {
							flag = false;
							break;
						}
					}

				}
				if (driver.findElements(By.linkText(OR.getProperty("next_link"))).size() == 0) {
					break;
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- Elements are same";
			} else

				return Constants.KEYWORD_FAIL + " -- Elements are not same";
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}

	/*
	 * 09 Sept,2013 Mayank Saini This method is used to sort the elemnts of
	 * column in descending order Modified on 24 OCt,2013
	 * Modified by Diksha Khattar on 27 OCt,2014:changed object used for Next link.
	 */

	public String verifySortingInDescending(String object, String data) throws Exception {
		logger.debug("Entered into verifySortingInDescending()");
		int size = 0;
		try {
			size = explictWaitForElementSize("pagination_next_link");
			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			while (true) {
				size = explictWaitForElementSize("pagination_next_link");
				List<WebElement> expected = explictWaitForElementList(object);
				if (size > 0) { 
					for (int i = 0; i < expected.size(); i++) {
						if (expected.get(i).getText().trim().length() != 0) {
							actual.add(expected.get(i).getText().trim());
							real.add(expected.get(i).getText().trim());
						}
					}
					driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
					browserSpecificPause(object, data);

				} else {
					expected = explictWaitForElementList(object);
					for (int i = 0; i < expected.size(); i++) {
						if (expected.get(i).getText().trim().length() != 0) {
							actual.add(expected.get(i).getText().trim());
							real.add(expected.get(i).getText().trim());
						}
					}
					if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
						break;
					}
				}
			}
			Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(actual);

			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);

			if (real.equals(actual)) {
				size = driver.findElements(By.xpath(OR.getProperty("pagination_first_lnk"))).size();
				if (size > 0) {
					driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
				}
				return Constants.KEYWORD_PASS + " -- Elements are sorted in Descending Order";
			} else {

				return Constants.KEYWORD_FAIL + " -- Elements are not sorted Descending Order";
			}
		}
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
        }
    }

	/*
	 * 06 Sept,2013 Mayank Saini This method is used to verify by clicking on
	 * last link we reached to last page or not
	 */
	public String verifyDropdownLastValue(String object, String data) {
		logger.debug("Entered into verifyDropdownLastValue()");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Select sel = new Select(select);
			List<WebElement> l1 = sel.getOptions();
			int lastvalue = Integer.parseInt(sel.getFirstSelectedOption().getText());
			logger.debug(lastvalue);
			if (lastvalue == l1.size()) {
				return Constants.KEYWORD_PASS + " -- Last value attained";
			} else {
				return Constants.KEYWORD_FAIL + " -- Last Value is not attained ";
			}
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}

	/* Sandeep Dhamija 
	* This method will check all the checkbox elements with same xpath 
	* Modified By Karan Sood on 11/12/2014 :- Added code to check checkbox using following sibling node-set
	* Parameters: pass object upto input only.
	*/
	
	 public String checkAllCheckBox(String object, String data) throws InterruptedException {
	        logger.debug("inside 'CheckAllCheckBox' method");

	        try {


	            List<WebElement> all_checkbox = explictWaitForElementList(object);
	             
	            if (all_checkbox.size() == 0) {
	                return Constants.KEYWORD_FAIL + " no checkbox found";
	            }
	            
	            for(int i=1;i<=all_checkbox.size();i++)
	            //for (WebElement checkbox : all_checkbox)
	            {
	                               
	                Thread.sleep(2000);
	               
	                boolean checked = all_checkbox.get(i-1).isSelected();
	                if (!checked)
	                {
	                    String xpathVal=all_checkbox.get(i-1).toString();
	                    if(!xpathVal.contains(Constants.INPUT_NX)){
	                   
	                    if(xpathVal.endsWith(Constants.LABEL_X) || xpathVal.endsWith(Constants.DIV_X) || xpathVal.endsWith(Constants.DIV_CLASS_X) || xpathVal.contains(Constants.LABEL_C_X) || xpathVal.contains(Constants.LABEL_C_X))
	                    {                   
	                        WebElement ele2 = driver.findElement(By.xpath("("+OR.getProperty(object)+")"+"[position()="+i+"]"));
	                        JavascriptExecutor executor = (JavascriptExecutor) driver;                        // if Xpath is made using label, div as last nodes.
	                        executor.executeScript("arguments[0].scrollIntoView(true);", ele2);
	                        executor.executeScript("arguments[0].click();", ele2); 
	                    }
	                    }
	                    else{                 
	                        WebElement ele1 = driver.findElement(By.xpath("("+OR.getProperty(object)+")"+"[position()="+i+"]"+"/following-sibling::*")); // if Xpath is made using input as last nodes.
	                    if(ele1.getTagName().equals(Constants.LABEL)){
	                        JavascriptExecutor executor = (JavascriptExecutor) driver;
	                        executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
	                        executor.executeScript("arguments[0].click();", ele1);
	                    }   
	                        }
	                    }
	           
	                else
	                    continue;
	            }

	        }
	catch(TimeoutException ex)
	       
	        {
	            return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
	        }
	        catch (Exception e) {
	       
	            return Constants.KEYWORD_FAIL + e.getMessage();

	        }
	        return Constants.KEYWORD_PASS + " checkboxes have been checked";
	    }

	/**
	 * Sandeep Dhamija 9 July, 2013 This method handles pagination and verifies
	 * that web element is not Present
	 *
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyItemIsNotPresent(String object, String data) {
		logger.debug("inside verifyItemIsNotPresent..");
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			while (true) {
				List<WebElement> element_List = driver.findElements(By.xpath(OR.getProperty(element_Start) + data + OR.getProperty(element_End)));

				List<WebElement> next_Link_List = driver.findElements(By.xpath(OR.getProperty("pagination_next_link")));

				if (element_List.size() > 0) {
					return Constants.KEYWORD_FAIL + " element is present";
				} else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
				}

				else {
					return Constants.KEYWORD_PASS + " Element is not present";
				}

			}

		} 
				catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}

	/**
	 * Date:- 09/06/13 Balkrishan Bhola This method returns PASS if WebElement
	 * is Blank [e.g- tr[1]/td[1]] Otherwise returns FAIL
	 * */
	public String verifyBlankValue(String object, String data) {
		logger.debug("Verifying the text");
		try {

			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim();
			logger.debug("act" + actual);
			if (actual.length() == 0)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Some Value Present";
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/*
	 * 2/6/13 Balkrishan Bhola This method is used to verify the default values
	 * of drop down present in FE->student placeement->pending notification
	 */

	public String verifyDropdownDefaultValues(String object, String data) {

		logger.debug("Selecting from list");
		try {
			int flag = 0;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			for (WebElement option : options) {
				if (option.getText().trim().equals(data.trim())) {
					flag = 1;
					break;
				} else
					break;
			}
			if (flag == 1) {
				return Constants.KEYWORD_PASS + " -- Default value present ";
			} else {
				return Constants.KEYWORD_FAIL + " -- Default value not present ";
			}
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}

	/**
	 * Anish Sharma pass data from property file
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String writeInInputUsingOR(String object, String data) {
		logger.debug("Writing in text box");

		try {
			String values[] = object.split(Constants.Object_SPLIT);
			String inputbox = values[0];
			String value = OR.getProperty(values[1]);
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(inputbox))));
			ele.clear();
			
	        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,value);
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;

	}

	/**
	 * This method verifies the search results whether the search results are <Br>
	 * in accordance with the search criteria or not.
	 * 
	 * @author Tarun Sharma
	 * @since 10 September, 2013
	 * 
	 * @param object
	 *            is the xpath of the search results. It include <br>
	 *            list of all the search results.
	 * @param data
	 *            is the search criteria on the bases of which search is
	 *            conducted
	 * 
	 * @return <b>PASS</b> if search results contains the keyword which is
	 *         passed in the data<br>
	 *         as an argument and <b>FAIL</b> otherwise.
	 */
	public String verifySearch(String object, String data) {
		try {
			logger.debug("Checking the search results...");

			final String SEARCH_RESULT_LIST = OR.getProperty(object);
			final String SEARCH_CRITERIA = data.trim();

			// validate the parameters
			if (SEARCH_RESULT_LIST == null || SEARCH_RESULT_LIST == "" || SEARCH_RESULT_LIST.equals("")) {
				return Constants.KEYWORD_FAIL + " Xpath is null. Please check the xpath";
			}
			if (SEARCH_CRITERIA == null || SEARCH_CRITERIA == "" || SEARCH_CRITERIA.equals("")) {
				return Constants.KEYWORD_FAIL + " Data is either null empty. Please check the data";
			}

			List<WebElement> searchedElements = driver.findElements(By.xpath(SEARCH_RESULT_LIST));
			logger.debug("Search criteria is " + SEARCH_CRITERIA);
			logger.debug(searchedElements.size() + " elements Searched on the bases of " + SEARCH_CRITERIA);
			String actualResult;
			/*
			 * following loop checks each searched element whether it is
			 * according to search criteria or not
			 */

			for (int i = 0; i < searchedElements.size(); i++) {
				actualResult = searchedElements.get(i).getText();
				if (!actualResult.contains(SEARCH_CRITERIA)) {
					return Constants.KEYWORD_FAIL + "---" + actualResult + " is not according to " + SEARCH_CRITERIA;
				}
			}
			int paginationLastLink = driver.findElements(By.linkText(OR.getProperty("last_link"))).size();
			// if pagination links present then check the last page for the
			// search results.
			if (paginationLastLink != 0) {
				// click on Last pagination link.
				logger.debug("checking the last page for the search results...");
				driver.findElement(By.linkText(OR.getProperty("last_link"))).click();
				Thread.sleep(1500);
				return verifySearch(object, data);
			}
			return Constants.KEYWORD_PASS + " All the search Results are according to search Criteria i.e. " + SEARCH_CRITERIA;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " following Exception occured \n" + e;
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Following Exception occured \n" + e;

		}
	}

	/**
	 * This method verifies the Total of the given values is same as displayed
	 * on the Web Page .<Br>
	 * And This Method also verifies the no of decimal values after (.)
	 * 
	 * @author Pankaj Sharma
	 * @since 7 October, 2013
	 * 
	 * @param object
	 *            : This method accepts One xpath , Check the no of digits in
	 *            Mantissa(i.e. After (.)) Decision and No of Decimal Values <Br>
	 *            that are separated by '|'. First xpath is of label that shows
	 *            the Total on the Web Page, <Br>
	 *            Second is Check the no of digits in Mantissa(i.e. After (.))
	 *            Decision means 'Y' or 'N'. if it is 'Y' then THis method also
	 *            checks the Decimal values <Br>
	 *            Other wish it will check only the Total of the given values is
	 *            same as displayed on the Web Page .<Br>
	 *            And Third is No of Decimal Values i.e. if u want to check how
	 *            many decimal values after (.) in total.
	 * 
	 *            Restriction: Do not Change the order of xpath,Check Decimal
	 *            Decision and No of Decimal Values in object parameter <br>
	 *            It should be like this = Label's_xpath|Y|2
	 * 
	 * @param data
	 *            is the values whose Total is checked with the Label on the Web
	 *            Page in this method.<Br>
	 *            All the Values are separated by ',' in this method. <Br>
	 *            for Example : 1.22,1.99,1.23
	 * 
	 * @return <b>PASS</b> if the option Check the no of digits in Mantissa(i.e.
	 *         After (.)) Decision is given as 'Y' and Both the Total and
	 *         Decimal no are same . or if the option Check the no of digits in
	 *         Mantissa(i.e. After (.)) Decision no is given as 'N' and the
	 *         Total is same <br>
	 *         <b>FAIL</b> otherwise.
	 */
	public String verifyTotalAndDecimalLength(String object, String data) {
		logger.debug("Verify the Total count of records");
		try {

			// Following line Splits the Object String by '|'

			String objectArr[] = object.split(Constants.DATA_SPLIT);
			String XPATH = OR.getProperty(objectArr[0]);
			String CHECKDECIMAL = objectArr[1];
			String DECIMALSIZE = objectArr[2];

			// validate the parameters
			if (XPATH == null) {
				return Constants.KEYWORD_FAIL + " Either Xpath  is null. Please check the xpath";
			}

			WebElement element = driver.findElement(By.xpath(OR.getProperty(objectArr[0])));

			String fteTotalString = element.getText();

			float TotalFloat = (float) Float.parseFloat(fteTotalString);
			logger.debug("Actual Total  is : " + TotalFloat);

			// Following line Splits the data String by ','
			String dataArr[] = data.split(Constants.Object_SPLIT);
			float sum = 0;

			// This for loop Calculates the Total of all values passed in to the
			// data parameter
			for (String nm : dataArr) {
				float f = (float) Float.parseFloat(nm);
				sum = sum + f;
			}
			logger.debug("Calculated Sum is: " + sum);

			boolean flag1 = false;
			boolean flag2 = false;

			// This if Evaluates the Calculated Total and the Actual Total
			if (sum == TotalFloat) {
				logger.debug("The Calculated and Actual total is same:" + sum);
				flag1 = true;
			}

			// Following Line Splits the Decimal no by '.'

			String decimalArr[] = fteTotalString.split("\\.");

			if (decimalArr.length > 1) {
				int actualSize = decimalArr[1].length();
				int expectdSize = Integer.parseInt(DECIMALSIZE);

				// This if Checks Check Decimal value Option is 'Y' or 'N'
				if (CHECKDECIMAL.equals("Y")) {
					// This if Evaluates the no of digits in mantissa with no of
					// digits given by User
					if (actualSize == expectdSize) {
						logger.debug("There are two decimal digits after .");
						flag2 = true;
					}
					if (flag1 == flag2) {
						return Constants.KEYWORD_PASS + " The Calculated and Actual total is same And There are " + actualSize
								+ " decimal digits after . ";
					} else {
						return Constants.KEYWORD_FAIL + "Either The Calculated and Actual total is not same OR There are more than or less than "
								+ expectdSize + " decimal digits after . ";
					}
				}
				if (flag1) {
					return Constants.KEYWORD_PASS + " The Calculated and Actual total is same.The Total is a Float No  ";
				} else {
					return Constants.KEYWORD_FAIL + "The Calculated and Actual total is not same. ";
				}
			} else {
				if (flag1) {
					return Constants.KEYWORD_PASS + " The Calculated and Actual total is same.The Total is a Integer No ";
				} else {
					return Constants.KEYWORD_FAIL + "The Calculated and Actual total is not same. ";
				}
			}
		}
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Some Exception  " + e.toString();
		}

	}

	/**
	 * This method verifies the product of the given values is same as displayed
	 * on the Web Page .<Br>
	 * 
	 * @author Pankaj Sharma
	 * @since 8 October, 2013
	 * 
	 * @param object
	 *            : This method accepts FOUR Xpaths in object parameter that are
	 *            saperateed by '|'.<Br>
	 *            The First Xpath is of the First part of the xpath for the
	 *            common row for example://tr[td[contains(text(),' <Br>
	 *            The Second xpath is of Second part of xpath of the Credit
	 *            Hours , for example:')]]//td[5] The Third xpath is of Secobd
	 *            part of xpath of the Students Enrooled ,for
	 *            example:')]]//td[6] And The fourth xpath is of second part of
	 *            xpath of the SCH ,for example')]]//td[7]
	 * 
	 *            Restriction: Do not Change the order of xpaths in the object
	 *            parameter, <br>
	 *            It should be like this =
	 *            verify_sch_value_part1|credit_hours_part2
	 *            |student_enrolled_part2|sch_part2
	 * 
	 * @param data
	 *            is the Course Number for which You want to use this method For
	 *            Example:CHEM231
	 * 
	 * @return <b>PASS</b> if the calculated product is same as displayed on the
	 *         Web Page. <b>FAIL</b> otherwise.
	 */

	public String verifyTotalOfProduct(String object, String data) {
		logger.debug("Verify Product of Credit Hours and Students Enrolled = SCH");
		try {

			String xpathArr[] = object.split(Constants.DATA_SPLIT);
			String part_1 = OR.getProperty(xpathArr[0]);

			String credit_hours = driver.findElement(By.xpath(part_1 + data + OR.getProperty(xpathArr[1]))).getText();

			String student_enrolled = driver.findElement(By.xpath(part_1 + data + OR.getProperty(xpathArr[2]))).getText();

			String sch = driver.findElement(By.xpath(part_1 + data + OR.getProperty(xpathArr[3]))).getText();

			float ch = (float) Float.parseFloat(credit_hours);
			float se = (float) Float.parseFloat(student_enrolled);

			float calculated_SCH = ch * se;
			logger.debug("Calculated SCH is: " + calculated_SCH);

			float actual_SCH = (float) Float.parseFloat(sch);
			logger.debug("Actual SCH is : " + actual_SCH);

			// This if verify that Calculated Product is same as displayed on
			// the Web Page or not
			if (calculated_SCH == actual_SCH) {
				logger.debug("Calculated SCH and Actual SCH are Same ");
				return Constants.KEYWORD_PASS + "Calculated SCH and Actual SCH are Same ";
			} else {
				logger.debug("Calculated SCH and Actual SCH are not Same ");
				return Constants.KEYWORD_FAIL + "Calculated SCH and Actual SCH are not Same ";
			}

		}
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " Some Exception  " + e.toString();
		}

	}

	/**
	 * This method verifies that is Page dropdown menu Changing according to
	 * Last page no <Br>
	 * When we are on Last Page of Pagination .
	 * 
	 * @author Pankaj Sharma
	 * @since 6 September, 2013
	 * 
	 * @param object
	 *            : It accepts two xpath that are separated by '|'. First xpath
	 *            is of label that shows the <Br>
	 *            total no of records of the search results for example : 1 - 25
	 *            of 308 <br>
	 *            And Second xpath is of Page dropdown menu in pagination . <br>
	 *            Restriction: Do not Change the order of xpaths in object
	 *            parameter <br>
	 *            It should be like this = Label's_xpath|Dropdown_menu_xpath
	 * 
	 * @param data
	 *            is the size of pagination (total no of records in one page of
	 *            search results).
	 * 
	 * @return <b>PASS</b> if Page dropdown menu Changing according to Last page
	 *         no. <br>
	 *         <b>FAIL</b> otherwise.
	 */
	public String verifyLastValueFromDropdown(String object, String data) {
		try {
			logger.debug("Verifing the dropdown menu changes according to Last page");

			String objArr[] = object.split(Constants.DATA_SPLIT);
			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];

			final String PAGE_SIZE = data.trim();

			// validate the parameters
			if (XPATH1 == null || XPATH2 == null) {
				return Constants.KEYWORD_FAIL + " Either Xpath1 or Xpath2 is null. Please check the xpath";
			}
			if (PAGE_SIZE == null || PAGE_SIZE == "" || PAGE_SIZE.equals("")) {
				return Constants.KEYWORD_FAIL + " Page Size is either null empty. Please check the data";
			}

			/*
			 * objArr[] stores two xpaths by splitting the object parameter .
			 * objArr[0] stores the xpath of label for e.g 1 - 25 of 308
			 * objArr[1] stores the xpath of Page dropdown menu in pagination.
			 */

			WebElement dropdown = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[1]))));
			String fullString = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[0])))).getText();

			int pagination = Integer.parseInt(data.trim());
			logger.debug("The Pagination size is : " + pagination);

			// strArr[] stores two strings by splitting the full label string
			// e.g 1 - 25 of 308 by 'of '
			String strArr[] = fullString.split("of ");
			int totalNoOfRecords = Integer.parseInt(strArr[1]);
			int totalPageInIntiger = 0;

			// Following if determines total page of no on different conditions

			if (totalNoOfRecords % pagination != 0) {
				totalPageInIntiger = (totalNoOfRecords / pagination) + 1;
			} else {
				totalPageInIntiger = (totalNoOfRecords / pagination);
			}
			logger.debug("Last page should be : " + totalPageInIntiger);

			String totalPage = String.valueOf(totalPageInIntiger);
			Select select = new Select(dropdown);
			String selectedPageInDd = select.getFirstSelectedOption().getText();
			logger.debug("In Page Drope down menu selected value is : " + selectedPageInDd);

			// Following if verify that the dropdown menu changes according to
			// Last page or not

			if (totalPage.equals(selectedPageInDd)) {
				return Constants.KEYWORD_PASS + " -- Value is changing accordingly to the Last page ";
			} else {
				return Constants.KEYWORD_FAIL + "  -- Value is not change accordingly to the Last page";
			}
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -Some Exception occured, Could not select from list. " + e.getMessage();
		}

	}

	/**
	 * Karan Sood 04 Sept,2013 verify that the elements(except first element) of
	 * drop down list are in sorted order
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyIgnoringFirstElemDDInSorted(String object, String data) {
		try {

			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			// select.click();
			boolean flag = false;
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
			List<String> actual_list = new ArrayList<String>();
			List<String> sortedlist = new ArrayList<String>();
			for (int i = 1; i < options.size(); i++) {
				String name = options.get(i).getText();
				actual_list.add(name);
				sortedlist.add(name);
			}
			Collections.sort(sortedlist, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < sortedlist.size(); i++) {
				if (sortedlist.get(i).equals(actual_list.get(i))) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS + "--elements in drop down are in sorted order";
			else
				return Constants.KEYWORD_FAIL + "--elements are not in sorted order";
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}

	}

	/**
	 * Karan Sood 04 Sept,2013 This method is used to verify that on putting any
	 * alphabet/word in textbox, IS auto search showing only that data
	 * containing that typed input
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	/*
	 * Updated By:- Balkrishan Bhola Date:- 10/31/13 Added "break;" statement in
	 * else part so that it gives expected result.
	 */
	public String verifyAllAutoNameSearch(String object, String data) {
		logger.debug("Verifying verifyAllAutoNameSearch");

		try {
			Thread.sleep(3000);
			List<WebElement> l1 = explictWaitForElementList(object);
			logger.debug("Number of elements found:- " + l1.size());
			boolean flag = false;
			for (int i = 0; i < l1.size(); i++) {
				WebElement ele = l1.get(i);
				String str = ele.getText();
				logger.debug("actual string: " + str);
				if (str.toLowerCase().contains(data.toLowerCase())) {
					flag = true;
				} else {
					logger.debug(str + " does not contain " + data);
					flag = false;
					break;
				}
			}

			if (flag)
				return Constants.KEYWORD_PASS + "--elements in auto search contains the input";
			else
				return Constants.KEYWORD_FAIL + "--elements in auto search  do not contains the input";

		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();

		}

	}

	/**
	 * Karan Sood 06 Sept,2013 This method is used to verify whether each row in
	 * the list displayed contains some specific data
	 * 
	 * @param object
	 * @param data
	 * @return
	 */

	public String isAllRowsHasSpecificData(String object, String data) {
		logger.debug("isAllRowsHasSpecificData");

		try {
			String values[] = object.split(Constants.Object_SPLIT);
			String totalRows = values[0];
			String totalSpecificData = values[1];

			List<WebElement> total_rows = explictWaitForElementList(totalRows);
			int i1 = total_rows.size();

			List<WebElement> total_rows_with_data = explictWaitForElementList(totalSpecificData);
			int i2 = total_rows_with_data.size();

			if (i1 == i2)
				return Constants.KEYWORD_PASS + "--All Rows contains Specific Data";
			else
				return Constants.KEYWORD_FAIL + "--All Rows does not contains Specific Data";

		}
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();

		}

	}

	/**
	 * 17 Sept,2013 Mayank Saini This method is used to add date in textbox
	 */

	public String addDate(String object, String data) {
		logger.debug("Adding Date to input with comma seperated data");
		try {
			String temp = data.replace(",", "/");
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			
			ele.clear();

	        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,temp);

		} catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--" + data;
	}

	/**
	 * 22 Sept,2013 Mayank Saini This method is used to verify partial last and
	 * first name in instant search
	 */
	@SuppressWarnings("unused")
	public String verifyPartialFirstLastName(String object, String data) {
		logger.debug("verifying row count");
		try {
			boolean flag = false;
			List<WebElement> l1 = explictWaitForElementList(object);
			String temp[] = data.split(Constants.Name_SPLIT);
			for (int i = 0; i < l1.size(); i++) {
				String input = l1.get(i).getText();
				if (input.toLowerCase().contains(temp[0].toLowerCase()) && input.toLowerCase().contains(temp[1].toLowerCase())) {
					flag = true;
					break;
				} else {
					flag = false;
					break;
				}
			}

			if (flag)
				return Constants.KEYWORD_PASS + "--elements in auto search contains the input value";
			else
				return Constants.KEYWORD_FAIL + "--elements in auto search do not contains the input";

		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
     * @since 09/13/13
     * @author Anil Kumar Mishra This method is used to verify sorting(Date) in
     *         Descending order ,pass DATE FORMAT from Excel.
     * 
     * 
     */
    public String verifyDateInDescending(String object, String data) {
        try {
        	int size=0;
            logger.debug("Entered into verifyDateInDescending()");
            List<String> actual = new ArrayList<String>();
            List<String> real = new ArrayList<String>();
            while (true) {
            	size = explictWaitForElementSize("pagination_next_link");
                List<WebElement> expected = explictWaitForElementList(object);
                if (size > 0) { 
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
                    browserSpecificPause(object, data);
                   
                } else {
                    expected = explictWaitForElementList(object);
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
                        break;
                    }
                }
            }
            final DateFormat df = new SimpleDateFormat(data.trim());
            Collections.sort(actual, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    try {
                        return df.parse(o2).compareTo(df.parse(o1));
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
            logger.debug("actual is as follows-- " + actual);
            logger.debug("real is as follows-- " + real);
            if (real.equals(actual)) {
            	size = driver.findElements(By.xpath(OR.getProperty("pagination_first_lnk"))).size();
                if (size > 0) {
                    driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
                }
                return Constants.KEYWORD_PASS + " -- Dates are sorted in Descending order";
            } else
                return Constants.KEYWORD_FAIL + " -- Dates are not sorted in Descending order";
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * @since 06/24/13
     * @author Balkrishan Bhola This method clicks on WebElement (Button) which
     *         is hidden
     */
    public String clickOnHiddenWebElement(String object, String data) {
        logger.debug("Entered into clickOnHiddenWebElement()");
        try {

            WebElement elementToClick = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + elementToClick.getLocation().y + ")");
            elementToClick.click();
            browserSpecificPause(object, data);
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
        }
        return Constants.KEYWORD_PASS;
    }

    /*
     * Created by Pankaj Dogra On 06 september This method is used to get the
     * last value from the dropdown and click on the link "Last" and then give
     * you whether the last value and from the dropdown is changing accordingly.
     * This method require two objects split with pipe
     */

    public String getLastValueFromDropdown(String object, String data) {

        try {
            logger.debug("Selecting last value from  the list");

            String objArr[] = object.split(Constants.DATA_SPLIT);
            final String GET_LAST_VALUE = objArr[0];
            final String CLICK_LAST_LINK = objArr[1];

            WebElement dropdown = explictWaitForElementUsingFluent(GET_LAST_VALUE);
            List<WebElement> options = dropdown.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
            int size = options.size();
            driver.findElement(By.xpath(OR.getProperty(CLICK_LAST_LINK))).click(); 
            Thread.sleep(4000);
            WebElement dropdown_last =explictWaitForElementUsingFluent(GET_LAST_VALUE);
            Select sel = new Select(dropdown_last);
            String defSelectedVal = sel.getFirstSelectedOption().getText();

            int aftclick_size = Integer.parseInt(defSelectedVal);
            if (size == aftclick_size) {
                return Constants.KEYWORD_PASS + " -- Value is changing accordingly ";
            } else {
                return Constants.KEYWORD_FAIL + "  -- Value is not changing accordingly";
            }

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();

        }

    }

    /**
     * Created by Pankaj Dogra On 26 september 2013 This method is used to give
     * the leading spaces .You have to pass data only. No need to pass the
     * leading space in data.
     * 
     * @param object
     * @param data
     * @return
     */
    public String writeInInputWithSpace(String object, String data) {
        logger.debug("Writing in text box");
        logger.debug("Data: " + data);
        try {
            WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            ele.clear();
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele," "+data);
            return Constants.KEYWORD_PASS + "--" + data;
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
        }
      

    }

    /**
     * sohal bansal scroll down
     * 
     * @param object
     * @param data
     * @return
     */
    public String scrollVertically(String object, String data) {
        try {

            Thread.sleep(1000);
            int d = Integer.parseInt(data);
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + d + ")", "");
            return Constants.KEYWORD_PASS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * S.Dhamija- 15 July, 2013 This method is used to verify that printable
     * view gets opened in new window
     * 
     * */
    public String verifyPrintableViewIsOpening(String object, String data) {
        logger.debug("Inside verifyPrintableViewIsOpening() ");

        try {

            String mainwindow = "";
            String newwindow = "";
            Set<String> windowids = driver.getWindowHandles();
            Iterator<String> ite = windowids.iterator();
            mainwindow = ite.next();
            newwindow = ite.next();
            driver.switchTo().window(newwindow);
            Thread.sleep(5000);
            String url = driver.getCurrentUrl();
            driver.close();
            driver.switchTo().window(mainwindow);
            if (url.contains(data)) {
                return Constants.KEYWORD_PASS + " printable window opens";
            } else {
                return Constants.KEYWORD_FAIL + " printable window does not open";
            }

        } catch (Exception nse) {

            return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
        }
    }

    /**
     * @since 09/24/13
     * @author Balkrishan Bhola This method verifies Whether Date is in proper
     *         format or not
     */
    public String verifyDateTimeFormat(String object, String data) {
        logger.debug("verifying the date format");

        String actual_pattern = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
        SimpleDateFormat sdf = new SimpleDateFormat(data.trim());
        boolean flag = false;
        try {
            sdf.setLenient(false);
            sdf.parse(actual_pattern);
            flag = true;
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (ParseException e) {
            e.printStackTrace();
            return Constants.KEYWORD_FAIL + " -- date is not in correct format";
        }
        if (flag) {
            return Constants.KEYWORD_PASS + " -- date is in correct format";
        } else {
            return Constants.KEYWORD_FAIL + " -- date and time is not in correct format";
        }
    }

    /*
     * Anil Kumar Mishra Date:24/09/13
     * 
     * 
     * /*Anil Kumar Mishra Date:24/09/13 >>>>>>> 1.97 verifyNonEditableTextbox
     * method is used to verify text box is edit able or not.
     */

    public String verifyNonEditableTextbox(String object, String data) {

        try {

            int row = explictWaitForElementSize(object);

            if (row > 0) {
                String value = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_READONLY"));

                if (value != null) {

                    return Constants.KEYWORD_PASS + "--Text box is non editable";

                } else {
                    return Constants.KEYWORD_FAIL + "--Text box is editable";
                }
            }

            else {
                return Constants.KEYWORD_FAIL;
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
        }

    }

    /**
     * Sandeep Dhamija, 1 October, 2103 This mehtod is used to drag and drop the
     * elements
     * 
     * @param object
     * @param data
     * @return
     */
    public String delayedDragAndDrop(String object, String data) {

        try {

            String[] dragdrop = object.split(Constants.Object_SPLIT);
            String src = dragdrop[0];
            String trgt = dragdrop[1];
            WebElement source = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(src))));
            WebElement target = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(trgt))));

            new Actions(driver).clickAndHold(source).build().perform();
            Thread.sleep(3000);
            new Actions(driver).moveToElement(target).build().perform();
            Thread.sleep(3000);
            new Actions(driver).release().build().perform();
            Thread.sleep(3000);

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + "Cannot Drag and Drop element";
        }

        return Constants.KEYWORD_PASS + "--Element Dropped sucessfully";

    }

    /**
     * Added by Surender Dated:07/10/2013 This method will check all
     * checkboxes(with common xpaths) as checked with pagination link handle if
     * exists
     * 
     * **/

    public String verifyAllCheckboxesCheckedWithPagination(String object, String data) {
        logger.debug("entered into verifyAllCheckboxesCheckedWithPagination() method");
        try {
            Boolean flag = false;
            while (true) {

                int size = driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
                List<WebElement> all_values = explictWaitForElementList(object);
                if (size > 0) {
                    for (int i = 0; i < all_values.size(); i++) {
                        String checked = all_values.get(i).getAttribute("checked");

                        if (checked != null)
                            flag = true;

                        else
                            flag = false;
                        break;
                    }
                    driver.findElement(By.linkText(OR.getProperty("next_link"))).click();

                } else {
                    all_values = driver.findElements(By.xpath(OR.getProperty(object)));
                    for (int i = 0; i < all_values.size(); i++) {

                        String checked = all_values.get(i).getAttribute(OR.getProperty("ATTRIBUTE_CHECKED"));

                        if (checked != null)
                            flag = true;

                        else
                            flag = false;
                        break;
                    }

                }
                if (driver.findElements(By.linkText(OR.getProperty("next_link"))).size() == 0) {
                    break;
                }
            }
            if (flag) {
                return Constants.KEYWORD_PASS + " All checkboxes checked";
            } else

                return Constants.KEYWORD_FAIL + "  All checkkboxes not checked";
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }

    }

    /*
     * Balkrishan Bhola This method is used to verify whether dropdowns ALL
     * selected
     */
    public String verifyDropdownAllSelectedValues(String object, String data) {
        logger.debug("Entered into verifyDropdownAllSelectedValues()");
        try {
            String allValues[] = data.split(Constants.Object_SPLIT);
            int count = 0;
            WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            Select sel = new Select(select);
            List<WebElement> defSelectedVal = sel.getAllSelectedOptions();
            if (allValues.length == defSelectedVal.size()) {
                for (int i = 0; i < allValues.length; i++) {
                    if (allValues[i].trim().equals(defSelectedVal.get(i).getText().trim())) {
                        count++;
                    }
                }
            } else {
                return Constants.KEYWORD_FAIL + " Elements Passed doesnot match with the present all selected values";
            }
            if (count == defSelectedVal.size()) {
                return Constants.KEYWORD_PASS + " All Selected Values present";
            } else {
                return Constants.KEYWORD_FAIL + " All Selected values not present";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
        }
    }

    /**
     * Added by Surender Dated:04/10/2013 This method will deselect all
     * previously selected values from dropdown
     * 
     * **/
    public String deselectDropdownAllSelectedValues(String object, String data) {
        logger.debug("Entered into deselectDropdownAllSelectedValues()");
        try {
            WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            Select sel = new Select(select);
            sel.deselectAll();
            List<WebElement> all_ele = sel.getAllSelectedOptions();
            if (all_ele.size() == 0)
                return Constants.KEYWORD_PASS + " --All values deselected  ";
            else {
                return Constants.KEYWORD_FAIL + " -- Selected values still present ";
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
        }
    }

    /**
     * Added by Surender Dated:04/10/2013 verifyFCKeditorBlank method is used to
     * check whether ckeditor is blank or not
     */

    /**
     * Modified By Karan Sood Dated:20/12/2013 verifyFCKeditorBlank method Now
     * After Checking ckEditor as Blank , it will move back to Parent Window
     */

    public String verifyFCKeditorBlank(String object, String data) throws Exception

    {
        try {
            data = "";

            logger.debug("entered into verifyFCKeditorBlank");
            WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            driver.switchTo().frame(ele);
            WebElement editable = driver.switchTo().activeElement();
            String actual = editable.getText();

            Set<String> availableWindows = driver.getWindowHandles();
            logger.debug("Handle Size:" + availableWindows.size());
            // Retreive all the window handles into variables
            String WindowIDparent = null;
            int counter = 1;
            for (String windowId : availableWindows) {
                if (counter == 1) {
                    logger.debug(Integer.toString(counter) + " " + windowId);
                    WindowIDparent = windowId;
                }
                counter++;
            }
            // Navigate to Parent window
            driver.switchTo().window(WindowIDparent);
            logger.debug("In the Parent");
            Thread.sleep(2000);

            if (actual.equals(data))
                return Constants.KEYWORD_PASS + " --CkEditor is blank  ";
            else
                return Constants.KEYWORD_FAIL + " --CkEditor is not blank  ";

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - " + e.getMessage();
        }

    }

    /**
     * Sandeep Dhamija 16 May, 2013 Delete all elements that have same xpath as
     * passed as object argument
     */
    public String deleteAllElements(String object, String data) {

        try {

            while (true) {
                int element_count = explictWaitForElementSize(object);

                if (element_count > 0) {
                    wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).click();
                    isInConsistentAlert("", "");
                    Thread.sleep(3000);
                   isInConsistentAlert("", "");
                    Thread.sleep(3000);
                } else {
                    return Constants.KEYWORD_PASS + " all elements have been removed";
                }
            }
        } 
        
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + "-" + e.getMessage();

        }

    }

    /**
     * Added on 7th Oct Mayank Saini This method is used to verify active count
     * of faculty members in faculty qualifications.
     **/
    public String verifyActiveCount(String object, String data) {
        logger.debug("verifying active count");
        Boolean flag = false;
        try {
            String temp[] = object.split(Constants.Object_SPLIT);
            WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(temp[1]))));
            int before = Integer.parseInt(element.getText());
            logger.debug(before);
            selectList(temp[0], data);
            Thread.sleep(new Long(CONFIG.getProperty("method_delay_time")));
            element = driver.findElement(By.xpath(OR.getProperty(temp[1])));
            int after = Integer.parseInt(element.getText());
            if (before - 1 == after)
                flag = true;
            else
                flag = false;
            if (flag)
                return Constants.KEYWORD_PASS + "--Active count is decremented";
            else
                return Constants.KEYWORD_FAIL + "--Active count is not decremented";
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
  
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }
    }

    /**
     * 07 Oct,2013 Mayank Saini This method is used to click outside the frame
     * to close the frame. provide the xpath of tab visible outside the frame
     * */
    public String clickOutsideWindow(String object, String data) {
        logger.debug("Clicking outside window");
        try {
            WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            Actions actions = new Actions(driver);
            Thread.sleep(2000);
            actions.moveToElement(ele).click().build().perform();
            Thread.sleep(2000);

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }
        return Constants.KEYWORD_PASS + "Clicked outside window";
    }

    /**
     * Created by Vikas Bhadwal On 15 October 2013 This method is used to Verify
     * current date in (MM/DD/YYYY) Format according to US/Central Time Zone For
     * Example: to verify Date created of an artifact or Assignment completed
     * etc.
     * 
     * @param object
     *            :This method accepts One xpath,i.e location where the date is
     *            displayed.
     * @param data
     *            : There is no need to pass any data
     * @return:PASS , if displayed date mtaches with current date in
     *              (MM/DD/YYYY) Format. FAIL: otherwise.
     */
    // edited by sdhamija, 7 November, 2013. replaced equals with contains
   //  edited by Surender on 01 April, 2015, Added code to check ATTRIBUTE_VALUE

    public String verifyCurrentDate(String object, String data) {
        logger.debug("Verifying the Current Date");
        try {
        	WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));        	
			String actual = ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")); //added by Surender
			if(actual==null)
        	{
				 actual =ele.getText().trim();
        	}
        	else
        		 actual =ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
            DateFormat DtFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            DtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
            String expected = DtFormat.format(date).toString().trim();

            logger.debug("expected" + expected);
            logger.debug("act" + actual);
            if (actual.trim().contains(expected)) // edited by sdhamija.
                                                    // replaced equals with
                                                    // contains
                return Constants.KEYWORD_PASS;
            else
                return Constants.KEYWORD_FAIL + " --current date is not displayed, actual: " + actual + " -- Expected  = " + expected + " ";
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
        }
    }

    /**
     * Created by Vikas Bhadwal On 16 October 2013. This method is used to
     * verify that there is no duplicate values in the table data(td).This
     * method also handles pagination.
     * 
     * @param object
     *            :This method accepts One xpath,i.e comman Xpath of all the
     *            possible values.
     * @param data
     *            : data is the value against which we have to verify that no
     *            duplicate is present of this value.
     * @return PASS: if no duplicate value is present. FAIL: if duplicate values
     *         are present.
     */
    public String verifyNoDuplicateValuesPresent(String object, String data) {
        logger.debug("Verifying No Duplicates value");
        try {
            int count = 0;
            while (true) {

                int size = explictWaitForElementListByLinkText("next_link").size();
                List<WebElement> all_values = explictWaitForElementList(object);
                if (size > 0) {
                    for (int i = 0; i < all_values.size(); i++) {
                        String value = all_values.get(i).getText();
                        if (value.trim().equals(data.trim())) {
                            count++;
                        } else {
                            continue;
                        }
                    }
                    driver.findElement(By.linkText(OR.getProperty("next_link"))).click();

                } else {
                    all_values = explictWaitForElementList(object);
                    for (int i = 0; i < all_values.size(); i++) {
                        String value = all_values.get(i).getText();
                        if (value.trim().equals(data.trim())) {
                            count++;
                        } else {
                            continue;
                        }
                    }
                }
                if (driver.findElements(By.linkText(OR.getProperty("next_link"))).size() == 0) {
                    break;
                }
            }
            if (count > 1) {
                return Constants.KEYWORD_FAIL + " --Duplicate values are present";
            } else {

                return Constants.KEYWORD_PASS + " --No Duplicate values are present";
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }
    }

    /**
     * !8 Sept,2013 Mayank This method will uncheck all the checkbox elements
     * with same xpath
     * Modified by Shweta Gupta
     * Modified code to verify whether checkbox is selected or not. 
     */
    
    
    public String uncheckAllCheckBox(String object, String data) throws InterruptedException {
        logger.debug("inside 'unCheckAllCheckBox' method");

        try {
            List<WebElement> all_checkbox = explictWaitForElementList(object);
            logger.debug("Total Checkbox = " + all_checkbox.size());
            
           for(int i=1;i<=all_checkbox.size();i++)
           {
                Thread.sleep(1000);
                boolean checked = all_checkbox.get(i-1).isSelected();
                if (checked){
                    WebElement ele1 = driver.findElement(By.xpath("("+OR.getProperty(object)+")"+"[position()="+i+"]"+"/following-sibling::*"));
                    if(ele1.getTagName().equals(Constants.LABEL)){
                        JavascriptExecutor executor = (JavascriptExecutor) driver;
                        executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
                        executor.executeScript("arguments[0].click();", ele1);
                    }
                    
                    logger.debug("unchecked the checkbox");
                }
                else
                {
                    logger.debug("checkbox is already unchecked");
                }
            }
            return Constants.KEYWORD_PASS + " checkboxes have been unchecked";
        } 
catch(TimeoutException ex)
        
        {
            return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
        }
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();

        }
   
    }

    /**
     * Added on 17 Oct,2013 Mayank Saini Verify the date in input match to given
     * data
     * 
     */
    public String verifyDateInput(String object, String data) {
        logger.debug("verifying Date in Input");
        try {
            Boolean flag = false;
            String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute("value");
            String expected = data.replace(",", "/");
            logger.debug(actual);
            logger.debug(expected);
            if (actual.trim().equals(expected.trim()))
                flag = true;
            else
                flag = false;

            if (flag)
                return Constants.KEYWORD_PASS + "--Date Inputs Matched";
            else
                return Constants.KEYWORD_FAIL + "--Date Input is not matched";

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }
    }

    /**
     * Anil kumar Mishra Date:10/21/13 isDropdownMultiSelect method is used to
     * verify multiple selection in drop down.
     * 
     * **/
    public String isDropdownMultiSelect(String object, String data) {
        logger.debug("verify multiple selection in drop down");
        try {
            boolean flag = false;
            WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            Select sel = new Select(select);
            flag = sel.isMultiple();
            if (flag)
                return Constants.KEYWORD_PASS + " --it is possible to select multiple value ";
            else {
                return Constants.KEYWORD_FAIL + " -- it is not possible to select multiple value ";
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not find object " + e.getMessage();
        }
    }

    /**
     * 
     * This method enters tomorrow's date in the date input. <br>
     * It enters the date according to server time.
     * 
     * @author Tarun Sharma <br>
     * @since 20 August, 2013 <br>
     * 
     * @param object
     *            which is xpath of the input where date has to be entered.
     * 
     * @param data
     *            no data is repuired.
     * 
     * @return <b>PASS</b> on entering the tomorrow's date
     */
    public String enterTomorrowDate(String object, String data) {
        logger.debug("Entering Tomorrow's date");
        try {

            int flag = 0;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
            timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
            calendar.add(Calendar.DATE, 1);
            String dueDate = timeFormat.format(calendar.getTime());
          WebElement ele=  wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,dueDate);
            flag = 1;
            if (flag == 1) {
                return Constants.KEYWORD_PASS + "--dueDate has been Entered.. ";
            } else {
                return Constants.KEYWORD_FAIL + "--dueDate has not been Entered.. ";
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * 18 Oct,2013 Added by MayanK Saini Drag to increase the size of textarea
     * 
     * */
    public String DragTextArea(String object, String data) {
        try {

            WebElement draggable = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            Point point = draggable.getLocation();
            int x = point.getX() + Constants.incremnent;
            int y = point.getY() + Constants.incremnent;
            new Actions(driver).dragAndDropBy(draggable, x, y).build().perform();
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }
        return Constants.KEYWORD_PASS + "--Text Area size is increased";
    }

    /**
     * 18 Oct,2013 Added by MayanK Saini Verify the cursor position is in active
     * Element o
     * */
    public String verifyCursorPosition(String object, String data) {
        try {
            WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            if (element.equals(driver.switchTo().activeElement()))
                return Constants.KEYWORD_PASS + "--Cursor is in given Input";
            else
                return Constants.KEYWORD_FAIL + "--Cursor is not in given Input";
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
        }

    }

    /**
     * Pooja Sharma 1 October, 2013 this method is used to verify the un
     * editable content is present in the newly opened print window
     * 
     * @param object
     * @param data
     * @return
     */
    public String verifyDisabledContentInPrintWindow(String object, String data) {
        logger.debug("Inside verifyDisabledContentInPrintWindow() ");

        try {
            /*
             * boolean flag=false; boolean flag2=false;
             */
            String mainwindow = "";
            String newwindow = "";
            Set<String> windowids = driver.getWindowHandles();
            Iterator<String> ite = windowids.iterator();
            mainwindow = ite.next();
            newwindow = ite.next();
            driver.switchTo().window(newwindow);
            Thread.sleep(5000);

            String objArr[] = object.split(Constants.Object_SPLIT);

            for (int i = 0; i < objArr.length; i++) {
                int size = explictWaitForElementSize(objArr[i]);
                if (size > 0) {
                    continue;
                } else {
                    driver.close();
                    driver.switchTo().window(mainwindow);
                    return Constants.KEYWORD_FAIL + " -elements in printable window are not disabled";

                }
            }
            String url = driver.getCurrentUrl();
            logger.debug(url);
            driver.close();
            driver.switchTo().window(mainwindow);
            if (url.contains(data)) {
                return Constants.KEYWORD_PASS + " printable window opens and elements are disabled";
            } else {
                return Constants.KEYWORD_FAIL + " printable window does not open";
            }

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
        }
    }

    /*
     * Created by Pankaj Dogra on October 17,2013
     * 
     * This method is used to verify the text which is present in FCKeditor is
     * not equal to the Expected text.
     */

    public String verifyFCKeditorMessageNotPresent(String object, String data) throws InterruptedException

    {
        try {

            logger.debug("verifyFCKeditorMessageNotPresent");
            logger.debug("enter the message in FCKeditor");
            WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            driver.switchTo().frame(ele);
            WebElement editable = driver.switchTo().activeElement();
            String actual = editable.getText().trim();

            Set<String> availableWindows = driver.getWindowHandles();
            logger.debug("Handle Size:" + availableWindows.size());
            // Retreive all the window handles into variables
            String WindowIDparent = null;
            int counter = 1;
            for (String windowId : availableWindows) {
                if (counter == 1) {
                    logger.debug(Integer.toString(counter) + " " + windowId);
                    WindowIDparent = windowId;
                }
                counter++;
            }
            // Navigate to Parent window
            driver.switchTo().window(WindowIDparent);
            logger.debug("In the Parent");
            if (!(actual.equals(data.trim()))) {
                logger.debug("actual " + actual);
                Thread.sleep(2000);
                return Constants.KEYWORD_PASS + " -- Text is not  present";
            } else
                return Constants.KEYWORD_FAIL + " -- Text is  present";
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
        }
    }

    /**
     * @since 08/29/13
     * @author Balkrishan Bhola This method verifies Whether element is present
     *         only once. [Also handles Pagination]
     */
    public String elementIsPresentWithPagination(String object, String data) {
        logger.debug("inside elementIsPresentWithPagination()");
        try {

            while (true) {
                List<WebElement> element_List = explictWaitForElementList(object);
                List<WebElement> next_Link_List = explictWaitForElementListByLinkText("next_link");
                if (element_List.size() == 1) {
                    return Constants.KEYWORD_PASS + " Element is present";
                } else if (next_Link_List.size() > 0) {
                    next_Link_List.get(0).click();
                } else {
                    return Constants.KEYWORD_FAIL + " Element not present";
                }
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /*
     * Sandeep Dhamija 10/11/2013 This methods finds a web element whether
     * pagination links are present or not pass xpath of element as 'object'
     * argument
     */
    public String findElement(String object, String data) {
        logger.debug("inside findElement()..");
        try {

            int page_no = 0;
            while (true) {
                page_no++;
                List<WebElement> element_List = explictWaitForElementList(object);
                List<WebElement> next_Link_List = explictWaitForElementListByLinkText("next_link");

                if (element_List.size() > 0) {
                    return Constants.KEYWORD_PASS + " element is found on page - " + page_no;
                } else if (next_Link_List.size() > 0) {
                    next_Link_List.get(0).click();
                }

                else {
                    return Constants.KEYWORD_FAIL + " Element not present";
                }

            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " Object Not found " + e.getMessage();
        }
    }

    /**
     * Dated 25/10/2013 Added By Surender This method is used to press enter
     * from keyboard. It takes the xpath of input field.
     * */
    public String pressEnterFromKeyboard(String object, String data) {
        logger.debug("entered into pressEnterFromKeyboard");
        try {
            Actions act = new Actions(driver);
            Action pressEnterKey = act.sendKeys(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))), Keys.ENTER).build();
            pressEnterKey.perform();
            return Constants.KEYWORD_PASS;
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * Sandeep Dhamija 16 May, 2013 this method first checks checkboxe
     * corresponding to item to be removed and then clicks on delete img or icon
     * whatever xpath we are passing pass two xpaths seperated by comma. First
     * xpath is for the checkbox and 2nd xpath is of delete img
     */
    public String deleteDegreePlan(String object, String data) {

        try {

            String[] objects = object.split(Constants.Object_SPLIT);
            String checkbox = objects[0];
            String delete_img = objects[1];

            String is_Element_Present = new KeywordEventsUtill().findElement(checkbox, data);
            if (is_Element_Present.contains("PASS")) {
                new KeywordEventsUtill().checkCheckBox(checkbox, data);
                WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(delete_img))));
                ele.click();
                new KeywordEventsUtill().isAlertAccept("", "");
                // Thread.sleep(3000);
                if (new KeywordEventsUtill().isWebElementPresent("form_used_error_txt", data).contains("PASS")) {
                    if (driver.findElements(By.linkText(OR.getProperty("first_link"))).size() > 0) {
                        driver.findElement(By.linkText(OR.getProperty("first_link"))).click(); 
                    }
                    return Constants.KEYWORD_FAIL + " this form is being used, so it can't be removed. Choose different form";
                } else {
                    if (driver.findElements(By.linkText(OR.getProperty("first_link"))).size() > 0) {
                        driver.findElement(By.linkText(OR.getProperty("first_link"))).click(); 
                    }
                    return Constants.KEYWORD_PASS + " Form has been removed";
                }
            } else {
                if (driver.findElements(By.linkText(OR.getProperty("first_link"))).size() > 0) {
                    driver.findElement(By.linkText(OR.getProperty("first_link"))).click(); 
                }
                return Constants.KEYWORD_PASS + "  form has been removed";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + "-" + e.getMessage();

        }
    }

    /**
     * @since 07/16/13
     * @author Balkrishan Bhola This method is used to verify sorting in
     *         Ascending order
     *  Modified by Diksha Khattar on 27 OCt,2014:changed object used for Next link.
     */
    public String verifySortingInAscending(String object, String data) throws Exception {
        logger.debug("Entered into verifySortingInAscending()");
        int size = 0;
        try {
            size = explictWaitForElementSize("pagination_next_link");
            List<String> actual = new ArrayList<String>();
            List<String> real = new ArrayList<String>();
            while (true) {
            	size = explictWaitForElementSize("pagination_next_link");
                List<WebElement> expected = explictWaitForElementList(object);
                if (size > 0) { 
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
                    browserSpecificPause(object, data);
                   
                } else {
                    expected = explictWaitForElementList(object);
                    for (int i = 0; i < expected.size(); i++) {
                        if (expected.get(i).getText().trim().length() != 0) {
                            actual.add(expected.get(i).getText().trim());
                            real.add(expected.get(i).getText().trim());
                        }
                    }
                    if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
                        break;
                    }
                }
            }
            Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);

            logger.debug("actual is as follows-- " + actual);
            logger.debug("real is as follows-- " + real);

            if (real.equals(actual)) {
                size = driver.findElements(By.xpath(OR.getProperty("pagination_first_lnk"))).size();
                if (size > 0) {
                    driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
                }
                return Constants.KEYWORD_PASS + " -- Elements are sorted in ascending order";
            } else {

                return Constants.KEYWORD_FAIL + " -- Elements are not sorted in ascending order";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
        }
    }

    /**
     * @since 10/17/13
     * @author Balkrishan Bhola This method verify whether all checkboxes are selected
     * Modified By -- Surender on 07/10/2014
     * Added specific return statement message for single/multiple checkbox(s).        
     * Edited by -- Surender on 01 April, 2015, Removed Thread.sleep().     * 
     * Modified By -- Karan Sood on 28/04/2015
     * Added break in for loop and replaced variable count to size with print in logger "Total CBs:-" 
     */
    public String isAllCheckboxesSelected(String object, String data) {
        logger.debug("isAllCheckboxesSelected");
        try {

            List<WebElement> expected;
            int count = 0;
            int size=0;
            expected = explictWaitForElementList(object);
            size=explictWaitForElementSize(object);
            for (WebElement checkbox : expected) {
                if (checkbox.isSelected())
                    count++;
                else
                	break;
            }
            logger.debug("Total CBs:-" + size);
            if (count == size ) {
            	if(size>0)
            	{
            		if(size==1)
            			return Constants.KEYWORD_PASS + " -- Single checkbox is Selected";
            		else 
            			return Constants.KEYWORD_PASS + " -- Multiple checkboxes are selected with count=" +count;
            	}
            	else
            	{
            		return Constants.KEYWORD_FAIL + " - Could not find checkbox";
            	}
           }else {
                return Constants.KEYWORD_FAIL + " -- Checkboxes are not selected";
            }
        }
catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL +  e.getMessage();
        }
    }



    /**
     * @since 10/17/13
     * @author Balkrishan Bhola This method verify whether all checkboxes are
     *         not selected
     *  Edited by -- Surender on 01 April, 2015, Removed Thread.sleep().
     *  Modified By -- Karan Sood on 28/04/2015
     *  Added break in for loop and replaced variable count with size to print in logger "Total CBs:-" 
     */
    public String isAllCheckboxesNotSelected(String object, String data) {
        logger.debug("isAllCheckboxesNotSelected");
        try {

            List<WebElement> expected;
            int count = 0;
            int size=0;
            expected = explictWaitForElementList(object);
            size=explictWaitForElementSize(object);
            for (WebElement checkbox : expected) {
                if (!checkbox.isSelected())
                    count++;
                else
                	break;
            }
            logger.debug("Total CBs:-" + size);
            if (count == size) {
                return Constants.KEYWORD_PASS + " -- CBs are not Selected with count= " +count ;
            } else {
                return Constants.KEYWORD_FAIL + " -- CBs are Selected";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " - Could not find checkbox";
        }
    }

    /**
     * Added By: Mayank Saini 
     * Date:17/10/2013 This method is used to verify the table header in a
     * single row. Provide the xpath of the table header.
     * 
     * Modify by Pankaj dogra: Added Break statement to exit form the method
     *                         if any column name is not valid.
     * Pass only Column Name form data using (,) split.No Extra space needed
     * Now ownwards it Neglect table header i.e th whose class attribute is present and 
     * verify text of header using contains.
     */
    public String verifyTableHeader(String object, String data) {
        logger.debug("verifying Table Header");
        Boolean flag = false;
        try {
            // give the xpath of table header
            List<WebElement> rowCollection = explictWaitForElementList(object);
            String temp[] = data.split(",");
            for (WebElement rowElement : rowCollection) {
                List<WebElement> colCollection = rowElement.findElements(By.xpath(OR.getProperty(object) + "/th[not(@class)]"));

                for (int i = 0; i<colCollection.size(); i++) {
                    String value = colCollection.get(i).getText();
                    if (value.trim().length() != 0) {

                        if (value.trim().contains(temp[i].trim())) {
                            logger.debug("Expected is" + value + "  /Actual IS " + temp[i]);

                            flag = true;

                        } else {
                            flag = false;
                            break;
                        }
                    }
                }

            }
            if (flag)
                return Constants.KEYWORD_PASS + "Elements are present in table header";
            else
                return Constants.KEYWORD_FAIL + "Elements are not present in table header";
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception ex) {
        	
            return Constants.KEYWORD_FAIL + ex.getMessage();
        }

    }

    /**
     * select a radio button by using its index from the data and xpath in
     * splitted form e.g==> start|end
     * 
     * Modified by Navdeep Kaur on Date:28/04/2015
     * Handled WebDriver Exception(Element is not clickable at point)
     * 
     * @param object
     * @param data
     * @return
     */
    
    public String selectRadio(String object, String data) {
        logger.debug("Selecting a radio button");
        WebElement slectRadio =null;
        try {

            String temp[] = object.split(Constants.DATA_SPLIT);
            slectRadio = driver.findElement(By.xpath(OR.getProperty(temp[0]) + data + OR.getProperty(temp[1])));
            slectRadio.click();
            
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch(WebDriverException ex)
		{
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(slectRadio).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on Button" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

       
    /**
     * 28 May, 2013 Tarun Sharma selectRadioButton this method select a radio
     * button corresponding to a name in the list
     * 
     * @param object
     * @param data
     * @return
     */
    public String selectRadioButton(String object, String data) {
        try {
            logger.debug("selectiong radio button");

            String objectArr[] = object.split(Constants.DATA_SPLIT);
            String temp = data.trim();
            boolean nameflag = false;
            List<WebElement> radio = explictWaitForElementList(objectArr[0]);
            List<WebElement> names = explictWaitForElementList(objectArr[1]);
            logger.debug("check box size=>" + radio.size());
            logger.debug("names size=>" + names.size());
            logger.debug("Expected text > " + temp);
            for (int i = 0; i < radio.size(); i++) {
                String actual = names.get(i).getText().trim();
                logger.debug("Actual text > " + actual);
                if (actual.equals(temp)) {
                    radio.get(i).click();
                    nameflag = true;
                }
            }
            if (!nameflag) {
                int nextEnable = driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
                if (nextEnable != 0) {
                    driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
                    Thread.sleep(1500);
                    String result = selectRadioButton(object, data);
                    return result;
                }

            }
            if (nameflag) {
                return Constants.KEYWORD_PASS + "--" + " radio selected corresponding to " + data;
            }
            return Constants.KEYWORD_FAIL + "-" + "unable to select radio. " + data + " not found";

        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + "Object not found ";
        }
    }

    /**
     * 26 Oct,2013 Added By Mayank Saini
     * 
     * This method is used to add future Date Pass the data as as the no of
     * dates you want to add after current date
     * */
    public String enterFutureDate(String object, String data) {
        logger.debug("Entering Future's date");
        try {
            int extend = Integer.parseInt(data);

            int flag = 0;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
            timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
            calendar.add(Calendar.DATE, extend);
            String dueDate = timeFormat.format(calendar.getTime());
         WebElement ele=   wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele,dueDate);
            flag = 1;
            if (flag == 1) {
                return Constants.KEYWORD_PASS + "--dueDate has been Entered.. ";
            } else {
                return Constants.KEYWORD_FAIL + "--dueDate has not been Entered.. ";
            }
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }

    }

    /**
     * Dated 26/10/2013 Added By Surender This method is used to press arrow up
     * key from keyboard. Data is passed as integer value.
     * */
    public String pressArrowupKeyFromKeyboard(String object, String data) {
        logger.debug("entered into pressArrowupKeyFromKeyboard");
        int count = Integer.parseInt(data);
        try {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Actions act = new Actions(driver);
                    Action pressEnterKey = act.sendKeys(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))), Keys.ARROW_UP).build();
                    pressEnterKey.perform();
                }
                return Constants.KEYWORD_PASS + "Arrowup key pressed " + count + " times.";
            } else
                return Constants.KEYWORD_FAIL;
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * Dated 26/10/2013 Added By Surender This method is used to press arrow
     * down key from keyboard. Data is passed as integer value.
     * */
    public String pressArrowdownKeyFromKeyboard(String object, String data) {
        logger.debug("entered into pressArrowdownKeyFromKeyboard");
        int count = Integer.parseInt(data);
        try {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Actions act = new Actions(driver);
                    Action pressEnterKey = act.sendKeys(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))), Keys.ARROW_DOWN).build();
                    pressEnterKey.perform();
                }
                return Constants.KEYWORD_PASS + "Arrowdown key pressed " + count + " times.";
            } else
                return Constants.KEYWORD_FAIL;
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * Dated 26/10/2013 Added By Surender This method is used to click an
     * element using mouse. It takes the xpath of the element to be clicked.
     * */
    public String mouseOverAndClick(String object, String data) {
        logger.debug("entered into mouseOverAndClick");
        try {
            Actions act = new Actions(driver);
            Action pressEnterKey = act.moveToElement(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))))).click().build();
            pressEnterKey.perform();
            return Constants.KEYWORD_PASS;
        } 
        catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getMessage();
        }
    }

    /**
     * 15/11/2013 Added By Mayank Saini This method is used to add time to
     * current time
     * */
    public String enterExpiryDate(String object, String data) {
        try {
            int newMin;
            int wait = Integer.parseInt(data);
            String Objects[] = object.split(",");
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
            calendar.add(Calendar.MINUTE, wait);
            String dueDate = timeFormat.format(calendar.getTime());
            String param[] = dueDate.split(" ");
            writeInInput(Objects[0], param[0]);
            logger.debug("Date is" + param[0]);
            Thread.sleep(2000);
            selectList(Objects[3], param[2]);
            logger.debug("Meridian is" + param[2]);
            String time[] = param[1].split(":");
            Thread.sleep(2000);
            WebElement elementMin = driver.findElement(By.xpath(OR.getProperty(Objects[2])));
            Select selectMin = new Select(elementMin);

            int min = Integer.parseInt(time[1]);
            if (min % 5 == 0) {
                newMin = min;

            }
            if (min > 55) {
                int n = min / 5;
                newMin = n * 5;

            } else {
                int n = min / 5;
                newMin = n * 5 + 5;
            }

            String timeMin = String.valueOf(newMin);
            if (timeMin.equals("5")) {
                selectMin.selectByValue("05");
            } else if (timeMin.equals("60")) {
                selectMin.selectByValue("00");
                int hr = Integer.parseInt(time[0]);
                if (hr == 12) {
                    hr = 1;
                    String timeHr = String.valueOf(hr);
                    selectList(Objects[1], timeHr);
                    if (param[2].equals("AM")) {
                        param[2] = "PM";
                        selectList(Objects[3], param[2]);
                    }
                    if (param[2].equals("PM")) {
                        param[2] = "AM";
                        selectList(Objects[3], param[2]);
                    }
                } else {
                    hr = hr + 1;
                    String timeHr = String.valueOf(hr);
                    selectList(Objects[1], timeHr);
                }
            } else {
                logger.debug("Minute is > " + timeMin);
                selectMin.selectByValue(timeMin);
            }
            selectList(Objects[1], time[0]);
            int flag = 1;
            if (flag == 1)
                return Constants.KEYWORD_PASS + "  Due date entered Successfully";
            else
                return Constants.KEYWORD_FAIL + "  Due date is not entered Successfully ";
        } catch (Exception ex) {

        	
            return Constants.KEYWORD_FAIL + ex.getMessage();
        }

    }

    /*
     * 4/12/2013 Added By Pallavi Singla This method is used to verify order of
     * uploaded files.
     */

    public String verifyOrderofUploadedFiles(String object, String data) throws InterruptedException {

        boolean flag = false;
        List<WebElement> imageRows = explictWaitForElementList(object);
        int rows = imageRows.size();
        String temp[] = data.split(",");
        logger.debug("Verifying the order ");
        try {

            for (int i = 0; i < rows; i++) {
                String imageName = imageRows.get(i).getText().trim();
                if (imageName.trim().equals(temp[i].trim())) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return Constants.KEYWORD_PASS + "--Uploaded Images are in the order in which they were Uploaded";
            } else
                return Constants.KEYWORD_FAIL + "--Uploaded Images are not in the order in which they were uploaded";

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception nse) {

            return Constants.KEYWORD_FAIL + "--Correct Image Names are not Found " + nse.getLocalizedMessage();
        }

    }

    /**
     * Added By Sandeep Dhamija Date : 20/12/2013 This Method selects a text
     * from input, copy it and then paste it to another input we have to provide
     * 2 xpaths one from where text has to be copied and one where text has to
     * be pasted
     */
    public String copyText(String object, String data) {

        try {
            String objects[] = object.split(",");
            String src = objects[0];
            String dest = objects[1];
            String SLCTaLL = Keys.chord(Keys.CONTROL, "a");
            String ctrlC = Keys.chord(Keys.CONTROL, "c");
            String ctrlV = Keys.chord(Keys.CONTROL, "v");

            wait.until(explicitWaitForElement(By.xpath(OR.getProperty(src)))).sendKeys(SLCTaLL);
            wait.until(explicitWaitForElement(By.xpath(OR.getProperty(src)))).sendKeys(ctrlC);
            Thread.sleep(2000);
            wait.until(explicitWaitForElement(By.xpath(OR.getProperty(dest)))).sendKeys(" " + ctrlV);

        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
        }
        return Constants.KEYWORD_PASS + "--" + data;

    }

    /**
     * Added By Karan Sood Date :20/12/2013 This method ckecks Sorting in
     * Ascending order only for the current page and is without pagination we
     * have to provide xpath of columns on current page for which sorting has to
     * be checked
     */

    public String verifySortingInAscendingWithoutPagination(String object, String data) throws Exception {
        logger.debug("Entered into verifySortingInAscending()");

        try {

            List<String> actual = new ArrayList<String>();
            List<String> real = new ArrayList<String>();
            while (true) {

                List<WebElement> expected = explictWaitForElementList(object);
                
                for (int i = 0; i < expected.size(); i++) {
                    if (expected.get(i).getText().trim().length() != 0) {
                        actual.add(expected.get(i).getText().trim());
                        real.add(expected.get(i).getText().trim());
                    }
                }
                break;
            }

            Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);

            logger.debug("actual is as follows-- " + actual);
            logger.debug("real is as follows-- " + real);

            if (real.equals(actual)) {
                return Constants.KEYWORD_PASS + " -- Elements are sorted";
            }

            else {
                return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
            }

        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
        }
    }

    /**
     * enters a text in the text field
     * 
     * @param object
     * @param data
     * @return
     */
    /*
     * modified by sandeep added clear().
     */
    /*
     * modified by Anil Kumar Mishra removed trim().
     */
    /*
     * modified by Anil Kumar Mishra Added click().
     */
    /* added by S.Dhamija ->logger.debug("Data: "+data); */
   

    /**
     * this method "verifyCountOfActivities" is used to verify the aggregate
     * count according to activities
     * 
     * @param object
     *            , two xpaths ---ist -for activities links and 2nd--- aggregate
     *            sum enclosed in braces.
     * @param
     * @author : sanjay sharma
     * @date: 29/01/2014
     * @return
     */
    public String verifyCountOfActivities(String object, String data) {
        logger.debug("Verify the Total count of records");
        try {

            String objArr[] = object.split(",");
            final String XPATH1 = OR.getProperty(objArr[0]);
            final String XPATH2 = OR.getProperty(objArr[1]);

            // validate the parameters
            if (XPATH1 == null || XPATH2 == null) {
                return Constants.KEYWORD_FAIL + " Either Xpath1 or Xpath2 is null. Please check the xpath";
            }

            String strLabel = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[1])))).getText();

            String str = strLabel;
            String regex = "\\(|\\]";
            str = str.replaceAll(regex, "");
            String regex1 = "\\)|\\]";
            str = str.replaceAll(regex1, "");
            System.out.println(str);
            int actualCount = Integer.parseInt(str);
            logger.debug("Total count on the header:" + actualCount);

            List<WebElement> totalactivity = explictWaitForElementList(objArr[0]);
            int rowSize = totalactivity.size();
            logger.debug("Total no of activities under category : " + rowSize);

            if (rowSize == actualCount) {
                return Constants.KEYWORD_PASS + " No of counts and the no of activities are same ";
            } else {
                return Constants.KEYWORD_FAIL + " No of counts and the no of activities are not same ";
            }

        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
        
            return Constants.KEYWORD_FAIL + " " + e.getMessage();
        }

    }

    /*
     * Created by Pankaj Dogra On February 12,2014 This method check whether the
     * checkbox is checked or not, if not then check the checkbox of given xpath
     * Parameters: xpath pass as object upto input only.
     * Modified By Surender on 13/10/2014
	 * Added explictWaitForElementUsingFluent instead of explictWaitForElement
	 * Modified By Surender on 25/11/2014 :- Added catch block for ElementNotVisibleException
	 * Modified By Karan Sood on 09/12/2014 :- Added code to check checkbox using following sibling node-set
     */
    public String checkCheckBoxWithoutPagination(String object, String data) {
        logger.debug("Checking checkCheckBoxWithoutPagination");
        WebElement element=null;
        try {
             element = explictWaitForElementUsingFluent(object);
             String xpathVal=element.toString();
                          
            boolean checked = element.isSelected();

            if (!checked) {            	// checkbox is unchecked
            	
            	if(!xpathVal.contains(Constants.INPUT_C_X)){
            		
					if(xpathVal.endsWith(Constants.LABEL_X) || xpathVal.endsWith(Constants.DIV_X) || xpathVal.endsWith(Constants.DIV_CLASS_X) || xpathVal.contains(Constants.LABEL_C_X) ||xpathVal.contains(Constants.DIV_C_X) )
	            	{
            		element.click();  // if Xpath is made using label, div as last nodes.
	            	}
            	}
            	else{            	 
            		WebElement ele1 = driver.findElement(By.xpath(OR.getProperty(object)+"/following-sibling::*"));  // if Xpath is made using input as last nodes.
                if(ele1.getTagName().equals(Constants.LABEL)){
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
                    executor.executeScript("arguments[0].click();", ele1);
                }
            	}
                return Constants.KEYWORD_PASS + " clicked on check-box";
            } else {
                return Constants.KEYWORD_PASS + " check-box is already checked";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch(ElementNotVisibleException ex)
        {
        	if(new ApplicationSpecificKeywordEventsUtil().clickJs(element).equals(Constants.KEYWORD_PASS))
				return Constants.KEYWORD_PASS;
		else
				return Constants.KEYWORD_FAIL;
        }
        catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(element).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
		}
    }

    /*
     * Created by Pankaj Dogra On February 12,2014 This method check whether the
     * checkbox is Unchecked or not, if not then Uncheck the checkbox of given
     * xpath Parameters: xpath pass as object upto input only.
     * Modified By Surender on 13/10/2014
	 * Added explictWaitForElementUsingFluent instead of explictWaitForElement
	 * Modified By Surender on 25/11/2014 :- Added catch block for ElementNotVisibleException
	 * Modified By Karan Sood on 09/12/2014 :- Added code to uncheck checkbox using following sibling node-set
     */
    public String unCheckCheckBoxWithoutPagination(String object, String data) {
        logger.debug("UnChecking unCheckCheckBoxWithoutPagination");
        WebElement element=null;
        try {

             element = explictWaitForElementUsingFluent(object);
             String xpathVal=element.toString();
             
            boolean checked = element.isSelected();
            
            if (!checked)// checkbox is unchecked
            {
                return Constants.KEYWORD_PASS + " check-box is already unchecked";

            } else {
            	
            	if(!xpathVal.contains(Constants.INPUT_C_X)){
            		
            		if(xpathVal.endsWith(Constants.LABEL_X) || xpathVal.endsWith(Constants.DIV_X) || xpathVal.endsWith(Constants.DIV_CLASS_X) || xpathVal.contains(Constants.LABEL_C_X) ||xpathVal.contains(Constants.DIV_C_X) )
	            	{
            		element.click();	// if Xpath is made using label, div as last nodes.
	            	}
            	}	
            	else{ 
            		            	
            		WebElement ele1 = driver.findElement(By.xpath(OR.getProperty(object)+"/following-sibling::*"));   // if Xpath is made using input as last nodes.
            		if(ele1.getTagName().equals(Constants.LABEL)){
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
                    executor.executeScript("arguments[0].click();", ele1);
                } 	}
                return Constants.KEYWORD_PASS + " unchecked check-box";
            }
        }
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch(ElementNotVisibleException ex)
        {
        	if(new ApplicationSpecificKeywordEventsUtil().clickJs(element).equals(Constants.KEYWORD_PASS))
				return Constants.KEYWORD_PASS;
		else
				return Constants.KEYWORD_FAIL;
        }
        catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(element).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
		}

    }

    /**
     * 14/02/2014 Added By Mayank Saini This method return element as soon as it
     * finds out the element
     * 08/10/2014 Modified by Mayank Saini called uiblocker 
     * */
    public static ExpectedCondition<WebElement> explicitWaitForElement(final By by) throws WebDriverException {
      uiBlockerTest("", "");
    	return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
              WebElement element = driver.findElement(by);
              return element.isDisplayed()?element:null;
                  }
              };
        }

   
    /**
     * 14/02/2014 Added By Mayank Saini This method return alert as soon as it
     * finds out the alert
     * 
     * */
    public static Alert explicitWaitForAlert() {

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS)
                .pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(Exception.class);

        Alert alert = wait.until(new Function<WebDriver, Alert>() {

            @Override
            public Alert apply(WebDriver driver) {

                return driver.switchTo().alert();
            }
        });

        return alert;

    }

    /**
     * 17/02/2014 Added By Mayank Saini 
     * This method return element size as soon as it finds out the element
     * 
     * 08/10/2014 Modified by Mayank Saini called uiblocker 
     * 25/02/2015 Modified by Karan Sood Added Condition using null value in apply() 
     **/
    public static int explictWaitForElementSize(final String object) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
        uiBlockerTest("", "");
        try{
        
        int element_size = wait.until(new Function<WebDriver, Integer>() {

            @Override
            public Integer apply(WebDriver driver) {
            	               
                int size= driver.findElements(By.xpath(OR.getProperty(object))).size();
                return size>0?size:null;	// Modified By Karan
            }
        });
        
        return element_size;
        }
        catch(TimeoutException e)
        {
        	return Integer.parseInt(Constants.ZERO_VALUE);
        }
    }

    /**
     * 17/02/2014 Added By Mayank Saini This method return elements list as soon
     * as it finds out the element list
     * 
     * 08/10/2014 Modified by Mayank Saini called uiblocker 
     * 25/02/2015 Modified by Karan Sood Added Condition using null value in apply()
     * */
    public static List<WebElement> explictWaitForElementList(final String object) {
    	
    		 
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
        uiBlockerTest("", "");
        
        try {
        List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>() {

            @Override
            public List<WebElement> apply(WebDriver driver) {

                List<WebElement> listElements=driver.findElements(By.xpath(OR.getProperty(object)));
                return listElements.size()>0 ?listElements:null;  // Modified By Karan
            }
        });
        return elements;
        }
    	
        catch(TimeoutException e)
        {        
        	return Collections.emptyList();
        }
    }

    /**
     * 17/02/2014 Added By Mayank Saini This method return element list by link
     * text as soon as it finds out the element list by link text.
     * 
     * 08/10/2014 Modified by Mayank Saini called uiblocker 
     * 25/02/2015 Modified by Karan Sood Added Condition using null value in apply()
     * */
    public static List<WebElement> explictWaitForElementListByLinkText(final String object) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
        uiBlockerTest("", "");
        	try{
        		List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>() {

        			@Override
        			public List<WebElement> apply(WebDriver driver) {

        				List<WebElement> listElements=driver.findElements(By.linkText(OR.getProperty(object)));
        				return listElements.size()>0?listElements:null;    // Modified By Karan                                
        															}
        						});
        
        				return elements;
        	}
        catch(TimeoutException e)
        {        
        	return Collections.emptyList();
        }
        
    }
    
    
    /**
     * 17/02/2014 Added By Mayank Saini This method return element size as soon
     * as it finds out the element
     * 
     * 08/10/2014 Modified by Mayank Saini called uiblocker 
     * */
    public static WebElement explictWaitForElementUsingFluent(final String object) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
        uiBlockerTest("", "");	
        WebElement ele = wait.until(new Function<WebDriver, WebElement>() {

            @Override
            public WebElement apply(WebDriver driver) {

                return driver.findElement(By.xpath(OR.getProperty(object)));
            }

        });
        return ele;
    }    
    
    /**
	 * write in input by finding xpath
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
    public String writeInInput(String object, String data) {
        logger.debug("Writing in text box");
        logger.debug("Data: " + data);
        try {
            String browser = CONFIG.getProperty("browserType");
            WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))); // modified by  mayank
            ele.clear();
            Thread.sleep(2000); // added by sdhamija, 11 Nov, 2013
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, data);
            if (browser.equals("IE")) {
            	Thread.sleep(3000);
            } 
        } 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
        catch (Exception e) {
       //     e.printStackTrace();
        
            return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
        }
        return Constants.KEYWORD_PASS + "--" + data;

    }
   
	
	/** 10/08/2014
	 * Added By Anil Kumar Mishra
	 * This method is used to check that whether ui blocked is present or not
	 *  
	 **/    
	public static void uiBlockerTest(String object,String data)
	   {
	       try{
	    	   driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	       while(true)
	       {
	    	   int el=driver.findElements(By.xpath(OR.getProperty("hm_test_uiblocker_id"))).size();
	           if(el==0)
	                   break;
	       }
	       long implicitWaitTime = Long.parseLong(CONFIG.getProperty("implicitwait"));
	       driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
	       }
	    catch(Exception ex){
	           ex.printStackTrace();
	           
	       }
	       
	       }
	/**
	 * This method clicks on an element whose xpath is given in the object
	 * For ex:- For clicking on div,label,text,row etc.
	 * Added By Surender on 16/10/2014
	 * Modified By Surender on 16/12/2014 :- Added catch block for ElementNotVisibleException
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickWebElement(String object, String data) {
		logger.debug("Clicking on element");
		WebElement ele=null;
		try {

			 ele = explictWaitForElementUsingFluent(object);
			 JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			ele.click();
			browserSpecificPause(object, data);
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(ElementNotVisibleException ex)
        {
        	if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
				return Constants.KEYWORD_PASS;
		else
				return Constants.KEYWORD_FAIL;
        }
		catch(StaleElementReferenceException ex){
			ele.click();
		}
		catch(WebDriverException ex){
			try{
				String exceptionMessage=ex.getMessage();
					if(exceptionMessage.contains("Element is not clickable at point"))
					{
				if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
							return Constants.KEYWORD_PASS;
					else
							return Constants.KEYWORD_FAIL;
					}
				else
						return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
				}
				catch(Exception e){
				
					return Constants.KEYWORD_FAIL+e.getMessage();
				}
				
				} 
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " " + e.getMessage() + " Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * Added by Vikas Bhadwal on 21/10/2014
	 * This method is used to verify sorting(Date) in
	 * Ascending order ,pass DATE FORMAT from Excel.
	 * @param object
	 * @param data:DATE FORMAT(Ex. MM/dd/yyyy)
	 *        
	 */
	

	public String verifyDateInAscendingWithOutPagination(String object, String data) {
		try {

			logger.debug("Entered into verifyDateInAscendingWithOutPagination()");
			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			
				List<WebElement> expected = explictWaitForElementList(object);
				
					expected = explictWaitForElementList(object);
					for (int i = 0; i < expected.size(); i++) {
						if (expected.get(i).getText().trim().length() != 0) {
							actual.add(expected.get(i).getText().trim());
							real.add(expected.get(i).getText().trim());
						}
					
					
				
			}
			final DateFormat df = new SimpleDateFormat(data.trim());
			Collections.sort(actual, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					try {
						return df.parse(o1).compareTo(df.parse(o2));
					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);
			if (real.equals(actual)) {
				return Constants.KEYWORD_PASS + " -- Dates are sorted";
			} else
				return Constants.KEYWORD_FAIL + " -- Dates are not sorted";
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

}

