
package com.tk20.seleniumuiflipassessment.test.courseEvaluation;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.driver;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill;

public class CE_KeywordEvents {
	KeywordEventsUtill keyUtil = (KeywordEventsUtill) KeywordEventsUtill
			.keyUtillFactory();
	String value=null;
	/**
     * Added by Shweta Gupta Dated:18/12/2014 This method will check all
     * checkboxes(with common xpaths) as checked with pagination link if
     * exists
     * @param data
     *      no data is required.
     * @param object
     *      This method accepts two xpaths separated by a comma.
     * First- x-path of the next link
     * Second- xpath of all the checkboxes(common x-path) to be verified.
     * 
     * **/
	public String isAllCheckboxesCheckedWithPagination(String object, String data) {
        logger.debug("entered into verifyAllCheckboxesCheckedWithPagination() method");
        try {
        	keyUtil.browserSpecificPause(object, data);
            Boolean flag = false;
            String temp[]=object.split(",");
            while (true) {

            	int size = driver.findElements(By.xpath(OR.getProperty(temp[0]))).size();
            	List<WebElement> all_values = explictWaitForElementList(temp[1]);
            	if (size > 0) {
            		for (int i = 0; i < all_values.size(); i++) {
            			boolean checked = all_values.get(i).isSelected();

            			if (checked)
            				flag = true;

            			else
            			{
            				flag = false;
            				break;
            			}
            		}

            		WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(temp[0])))));
            		JavascriptExecutor executor = (JavascriptExecutor) driver;
            		keyUtil.browserSpecificPause(object, data);
            		executor.executeScript("arguments[0].scrollIntoView(true);", ele);
            		keyUtil.browserSpecificPause(object, data);
            		ele.click();
            		keyUtil.browserSpecificPause(object, data);

            	} else {
            		all_values = driver.findElements(By.xpath(OR.getProperty(temp[1])));
            		for (int i = 0; i < all_values.size(); i++) {
            			boolean checked = all_values.get(i).isSelected();
            			if (checked)
            				flag = true;
            			else
            			{
            				flag = false;
            				break;
            			}
            		}

            	}
            	if (driver.findElements(By.xpath(OR.getProperty(temp[0]))).size() == 0) {
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

	public String getStartDateOfCE(String object, String data) {
		logger.debug("entered into getStartDate() method");
		try {
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String element=ele.getText();
			String[] date= element.split(" ");
			value=date[0];
			return Constants.KEYWORD_PASS +"Date has Copied";
		}
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}
	
	public String enterStartDateOfCE(String object, String data) {
		logger.debug("entered into getStartDate() method");
		try {
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, value);
			return Constants.KEYWORD_PASS +"Date has Pasted Successfully";
		}
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}
}
	
