package com.tk20.seleniumuiflipassessment.accessibility;


import static com.tk20.seleniumuiflipassessment.base.DataProvider.CONFIG;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.accessibilityLogger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.driver;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementUsingFluent;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.tk20.seleniumuiflipassessment.base.Constants;

public class AccessibilityMethods{
	public void openWave(String object){
		WebElement ele=explictWaitForElementUsingFluent(object);
		ele.sendKeys(Keys.ALT + "T");
		   ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_RIGHT);
		    ele.sendKeys(Keys.ENTER);
		
	}
	
	public void closeWave(String object){
		WebElement ele=explictWaitForElementUsingFluent(object);
			ele.sendKeys(Keys.ALT + "T");
			ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_RIGHT);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ARROW_DOWN);
		    ele.sendKeys(Keys.ENTER);
		
	}
	
	public String checkAccessibilityError(String object,String data)
	{
		try{
			if(!(CONFIG.getProperty("performAcceessibilityTest").equals(Constants.RUNMODE_YES))){
			return "Accessibility Test is not Perfromed";	
			}
			 
			 openWave(object);
			 List<WebElement> l1 =explictWaitForElementList("wave_error");
			 if(l1.size()==0)
			 {
				 closeWave(object);
				 return Constants.KEYWORD_PASS+"This page has no accessbility Errors";
			 }
				  else{
					  accessibilityLogger.debug("There are total "+l1.size()+" Accessibility Error in page ");
			 for(int i=1;i<=l1.size();i++){
				
				 WebElement temp=driver.findElement(By.xpath("(//img[@class='wave4tip'][contains(@alt,'ERROR:')])[position()='"+i+"']"));
				 String parent="(//img[@class='wave4tip'][contains(@alt,'ERROR:')])[position()='"+i+"']/preceding-sibling::*";
				 if(temp.getAttribute("alt").startsWith("ERROR")){

					 accessibilityLogger.debug("Error Number "+i);
					 accessibilityLogger.debug(temp.getAttribute("alt").trim()+" and Description to the Error: "+temp.getAttribute("description").trim());
					 WebElement ele=driver.findElement(By.xpath(parent));
					checkParent( ele);
		
				 }
				
			   }
			 closeWave(object);
			 return Constants.KEYWORD_FAIL+"There are "+l1.size()+" Accessibility Error in page ";
			 }  
		}
	catch(Exception ex){
	    return Constants.KEYWORD_FAIL + ex.getMessage();
	}
	}

	public static void checkParent(WebElement ele){
		String temp="";
		String slash="";
		boolean flag=false;
		do{
			if(!flag)
				temp="/"+ele.getTagName()+temp;
			
			else
			temp=slash+ele.getTagName()+temp;

			ele=ele.findElement(By.xpath("./parent::*"));
			
			
			slash="/";	
		flag=true;
		}while(!(ele.getTagName().equalsIgnoreCase("HTML")));
		temp="//HTML"+temp;
		 accessibilityLogger.debug("Expected Xpath For the element "+temp);
		 accessibilityLogger.debug("");
	}
}