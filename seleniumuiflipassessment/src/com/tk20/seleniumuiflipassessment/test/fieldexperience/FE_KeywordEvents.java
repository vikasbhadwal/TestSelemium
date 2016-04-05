package com.tk20.seleniumuiflipassessment.test.fieldexperience;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;

public class FE_KeywordEvents {

    
    /**
	 * Added by Kritika on 2/9/2015 ,This method is used to match Column Values i.e. first name by spliting its name with comma(,) with given value
	 * @param object: xpath of the column values
	 * @param data: Value with Which you want to match Column Values
	 */
	public String verifyAllColumnFname(String object, String data) {
		try {

			logger.debug("Entered into verifyColumnValues()");

			List<WebElement> expected = explictWaitForElementList(object);

			expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
			    
			    String name[]=expected.get(i).getText().split(",");
				if ((name[1].trim()).contains(data)) 
				{
					logger.debug((name[1].trim()));
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
	 * Added by Kritika on 2/9/2015 ,This method is used to match Column Values i.e. first name by spliting its name with comma(,) with given value
	 * @param object: xpath of the column values
	 * @param data: Value with Which you want to match Column Values
	 */
	public String verifyAllColumnLname(String object, String data) {
		try {

			logger.debug("Entered into verifyColumnValues()");

			List<WebElement> expected = explictWaitForElementList(object);

			expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
			    
			    String name[]=expected.get(i).getText().split(",");
				if ((name[0].trim()).contains(data)) 
				{
					logger.debug((name[0].trim()));
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
