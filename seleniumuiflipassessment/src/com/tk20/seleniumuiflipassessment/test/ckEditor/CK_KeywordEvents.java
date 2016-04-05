
package com.tk20.seleniumuiflipassessment.test.ckEditor;


import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;



public class CK_KeywordEvents {
	
	/**
	 * Anil Kumar Mishra this method is used to verify icon is enabled date : 02/04/15
	 * verifyImageIsActive
	 */

	public String verifyImageIsActive(String object, String data) {

		try {

			WebElement icon = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			logger.debug(icon.getAttribute(OR.getProperty("ATTRIBUTE_ICON_ACTIVE")));
			if (icon.getAttribute(OR.getProperty("ATTRIBUTE_ICON_ACTIVE")).equals("true")) {
				return Constants.KEYWORD_PASS + "--Image icon is enabled";
			}

			else {
				return Constants.KEYWORD_FAIL + "--Image icon is disabled";
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Anil Kumar Mishra this method is used to verify icon is  disabled date : 02/04/15
	 * verifyImageInActive
	 */

	public String verifyImageInActive(String object, String data) {

		try {

			WebElement icon = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			logger.debug(icon.getAttribute(OR.getProperty("ATTRIBUTE_ICON_INACTIVE")));
			if (icon.getAttribute(OR.getProperty("ATTRIBUTE_ICON_INACTIVE")).equals("true")) {
				return Constants.KEYWORD_PASS + "--Image icon is disabled";
			}

			else {
				return Constants.KEYWORD_FAIL + "--Image icon is enabled";
			}

		} catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

}

