package com.tk20.seleniumuiflipassessment.test.admissions;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;

public class ADMSN_Create_KeywordEvents {
	
	int count=0;   
    
	
	/* Date:09/04/2013
	Anil Reddy:
	selectAdmissionAtStuLogin method is used to select admission template in Application Tab*/

	public  String selectAdmissionAtStuLogin(String object,String data){
		logger.debug("Selecting Admission...");
		try
		{
			
			boolean flag=false;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName("option"));
			String expected=data.trim();
			for(WebElement option : options){

				String actual=option.getText().trim();
				if(actual.equals(expected)) {
					option.click();
					flag=true;
					count=0;
					return Constants.KEYWORD_PASS +"--"+data+ " - selected";
				} 
			}
			if(!flag)
			{
				if (count == 3)
				{
				    count=0;
					return Constants.KEYWORD_FAIL + " - Could not select from list. ";
				}
				Thread.sleep(180000);
				count++;
				wait.until(explicitWaitForElement(By.linkText(OR.getProperty("browse_link_txt")))).click();
				wait.until(explicitWaitForElement(By.xpath(OR.getProperty("admsn_com_create_new_app_btn")))).click();
			
				String browse_again=selectAdmissionAtStuLogin(object, data);
				return browse_again;
			}
		}catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}catch(Exception e){
			 e.printStackTrace();
			}
		return Constants.KEYWORD_FAIL +" - Could not select from list. " ;
	}
    
	
	}
