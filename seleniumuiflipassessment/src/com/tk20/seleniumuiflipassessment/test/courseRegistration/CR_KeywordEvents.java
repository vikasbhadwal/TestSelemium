package com.tk20.seleniumuiflipassessment.test.courseRegistration;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;

import org.openqa.selenium.By;


import com.tk20.seleniumuiflipassessment.base.Constants;

public class CR_KeywordEvents {
	public int number=0;
	
	/**
	 * This method is used to save the Number 
	 * @author : Pankaj Dogra
	 * @since :02/07/2015
	 * @param object
	 *            : It accept one xpath 
	 * @param data
	 *            : No data	             
	 * @return PASS : if xpath is present on page
	 * @return Fail : Otherwise.
	 **/
	public String saveNumber(String object, String data)
	{
		try{
			String text = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String[] arr=text.split(" ");
			number=Integer.parseInt(arr[arr.length-1]);
			return Constants.KEYWORD_PASS + " -  Number has Saved";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL ;
		}
		
	}
	
	/**
	 * This method is used to Match the Number with save number
	 * @author : Pankaj Dogra
	 * @since :02/07/2015
	 * @param object
	 *            : It accept one xpath 
	 * @param data
	 *            : No data	             
	 * @return PASS : if count of both numbers are same.
	 * @return Fail : Otherwise.
	 **/
	public String matchNumber(String object, String data)
	{
		try{
			String text = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String[] arr=text.split(" ");
			int num=Integer.parseInt(arr[arr.length-1]);
			if(num==number)
			return Constants.KEYWORD_PASS + " - Both Numbers are equal";
			else
				return Constants.KEYWORD_FAIL +" - Both Numbers are not equal";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL ;
		}
		
	}

}
