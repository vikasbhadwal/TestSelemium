package com.tk20.seleniumuiflipassessment.test.portfolios;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;

public class PF_KeywordEvents {
    /**
	 * Added by Kritika on 22/06/2015 
	 * This method is used to match Column partial Value ie First Name(Yan, Aaron) with given value
	 * @param object: xpath of the column values
	 * @param data: Value with Which you want to match Column Values
	 */
	public String verifyColumnPartialFirstName(String object, String data) {
		try {

			logger.debug("Entered into verifyColumnValues()");

			List<WebElement> expected = explictWaitForElementList(object);
			expected = explictWaitForElementList(object);
			String data1=data.toLowerCase();
			for (int i = 0; i < expected.size(); i++) {
			    String last_val[]=expected.get(i).getText().trim().toLowerCase().split(",");
				if (last_val[1].contains(data1)) //modified on 13/05/2015 replace equal with contains.
				{
					logger.debug(last_val[1]+"--Present");
				}
				else
				{
					return Constants.KEYWORD_FAIL + "---"+ "Element not Matched";
				}
			}
			return Constants.KEYWORD_PASS + "--" + "All Elements Matched"; 
		} 
		catch (TimeoutException ex) 
		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} 
		catch (Exception e) 
		{

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}
	/**
	 * Added by Kritika on 22/06/2015 
	 * This method is used to match Column partial Value ie Last Name(Yan, Aaron) with given value
	 * @param object: xpath of the column values
	 * @param data: Value with Which you want to match Column Values
	 */
	public String verifyColumnPartialLasttName(String object, String data) {
		try {

			logger.debug("Entered into verifyColumnValues()");

			List<WebElement> expected = explictWaitForElementList(object);

			expected = explictWaitForElementList(object);
			String data1=data.toLowerCase();
			for (int i = 0; i < expected.size(); i++) {
			    String last_val[]=expected.get(i).getText().trim().toLowerCase().split(",");
				if (last_val[0].contains(data1)) //modified on 13/05/2015 replace equal with contains.
				{
					logger.debug(last_val[0]+"--Present");
				}
				else
				{
					return Constants.KEYWORD_FAIL + "---"+ "Element not Matched";
				}
			}
			return Constants.KEYWORD_PASS + "--" + "All Elements Matched"; 
		} 
		catch (TimeoutException ex) 
		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} 
		catch (Exception e) 
		{

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}
}
