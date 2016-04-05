package com.tk20.seleniumuiflipassessment.test.home;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.driver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill;

public class Home_KeywordEvents {
        int total_courses_stored=0;
    	private String email="";
    	private boolean flag=false;
    	KeywordEventsUtill keyUtil=new KeywordEventsUtill();
    	
    	/*By Anil Kumar Mishra Date:31/07/13
	 * VerifyDateFormat method is used to verify date format(mm:dd:YYYY).
	 * */
	public String VerifyDateFormatInGF(String object,String data)
	{
		logger.debug("verify date format");
	 try
	 {
		 String datetext=driver.findElement(By.xpath(OR.getProperty(object))).getText();
		
		 String date[]=datetext.split("/");
		 String year[]=date[2].split(" ");
		 String finalYear=year[0];
		 date[2]=finalYear;
	       
	        for (int i = 0; i < date.length; i++) {
	        	
	        	logger.debug(Integer.parseInt(date[i]));
	        }	       
	      if(Integer.parseInt(date[0])<=12 && Integer.parseInt(date[1])<=31 && Integer.parseInt(date[2])>2000 )
	      {
	    
	    	  return Constants.KEYWORD_PASS+"--date is correct format";
	      }
	        
	        else
	        {
	        	return Constants.KEYWORD_FAIL+"--date is not correct format";
	        }
	        
	  }
		
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL;
		}
	
	}
	
	/*By Anil Kumar Mishra Date:31/07/13
	 * DeleteForum method is used to delete all forum.
	 * */
	public String DeleteForum(String object,String data)
	{
		 List<WebElement> frmnme=driver.findElements(By.xpath(OR.getProperty(object)));
		 boolean flag=false;
		 try{
		 if(frmnme.size()>0)
		 {
			
			for(int i=0;i<frmnme.size();i++)
			{
				boolean frmdata=frmnme.get(i).getAttribute("title").startsWith("Forum");
				if(frmdata==true)
				{
					frmnme.get(i).click();					
					flag=true;
				}
				else
				{
					flag=false;
				}							
			}
			if(flag)
			{
				keyUtil.clickImage("hm_del_img", "");
				keyUtil.isAlertAccept("", "");
				return Constants.KEYWORD_PASS+"--All Forum deleted ";
			}
			else{
				return Constants.KEYWORD_FAIL+"--unable to delete forum";
			}
		 }
		 else
		 {
			 return Constants.KEYWORD_FAIL+"--therse is no forum to delete"; 
		 }
		 }
		 catch(Exception e)
		 {
			 return Constants.KEYWORD_FAIL;
		 }		
	}	
	
	/*By Anil Kumar Mishra Date:31/07/13
	 * VerifyDateFormatInHm method is used to verify date format(mm:dd:YYYY).
	 * */
	public String VerifyDateFormatInHm(String object,String data)
	{
		logger.debug("verify date format");
	 try
	 {
		 String datetext=driver.findElement(By.xpath(OR.getProperty(object))).getText();
		
		 String date[]=datetext.split("/"); 
		 String yearmonth[]=date[0].split(" ");
		 String final_month=yearmonth[1];
		 date[0]=final_month;
		 String year[]=date[2].split(" ");
		 String finalYear=year[0];
		 date[2]=finalYear;
	       
	        for (int i = 0; i < date.length; i++) {
	        	
	        	logger.debug(Integer.parseInt(date[i]));
	        }	       
	      if(Integer.parseInt(date[0])<=12 && Integer.parseInt(date[1])<=31 && Integer.parseInt(date[2])>2000 )
	      {
	    
	    	  return Constants.KEYWORD_PASS+"--date is correct format";
	      }
	        
	        else
	        {
	        	return Constants.KEYWORD_FAIL+"--date is not correct format";
	        }
	        
	  }
		
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL;
		}
	
	}
    	
	/**
	 * @since  07/12/13 
	 * @author Balkrishan Bhola 
	 * This method is used to match the CSS Value, if not matched then it returns PASS
	 */
	public String verifyNoCssPropertyBySelector(String object, String data){
	    try{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String property[]=data.split(":");
		String exp_prop=property[0];
		String exp_value=property[1];
		String prop=driver.findElement(By.cssSelector(OR.getProperty(object))).getCssValue(exp_prop);
		if(prop.equals(exp_value)){
		    return Constants.KEYWORD_FAIL +" -- CSS value present --";
		}
		return Constants.KEYWORD_PASS+" -- No CSS value present --";
	    }
	    catch(Exception e){
		return Constants.KEYWORD_FAIL +" -- Object not found "+e.getCause();
	    }
	}
	
	/**
	 * @since  07/12/13 
	 * @author Balkrishan Bhola 
	 * This method is used to match the CSS Value, if matched then it returns PASS
	 */
	public String verifyCssPropertyBySelector(String object, String data){
	    try{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String property[]=data.split(":");
		String exp_prop=property[0];
		String exp_value=property[1];
		String prop=driver.findElement(By.cssSelector(OR.getProperty(object))).getCssValue(exp_prop);
		if(prop.equals(exp_value)){
		    return Constants.KEYWORD_PASS+" -- CSS value present --";
		}
		return Constants.KEYWORD_FAIL +" -- No CSS value present --";
	    }
	    catch(Exception e){
		return Constants.KEYWORD_FAIL +" -- Object not found "+e.getCause();
	    }
	}
	
	/**
	 * @since  07/12/13 
	 * @author Balkrishan Bhola 
	 * This method append random number in Email which is again appended by ".com" string
	 */
	public String enterRandomEmail(String object, String data){
	    try{
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		if(flag==false){
		    email=data+KeywordEventsUtill.createRandomNum()+"@tk20.com";
		    driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(email);
		    logger.debug("flag is False and email is "+email);
		    flag=true;
		}else{
		    driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(email);
		    logger.debug("Flag is true and email is "+email);
		}
	    }
	    catch(Exception e){
		return Constants.KEYWORD_FAIL +" -- Object not found "+e.getCause();
	    }
	    return Constants.KEYWORD_PASS;
	}
	
	/* Date:07/30/2013
	       Anil Reddy:
	       clickOnUserGroup method is used to click on the User group name in Home Tab*/

	public  String clickOnUserGroup(String object,String data){
	    logger.debug("Clicking on User group...");
	    try
	    {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		List<WebElement> ugName=driver.findElements(By.xpath(OR.getProperty(object)));
		boolean flag = false;
		String temp=data.trim();
		for (int i =0;i<ugName.size();i++)
		{
		    String userGroup_name=ugName.get(i).getText().trim();
		    if (userGroup_name.equals(temp)) 
		    {
			ugName.get(i).click();
			flag=true;
			break;					}
		}
		if (flag) 
		{
		    return Constants.KEYWORD_PASS+"--User group is found to click";
		}
	    }catch (NoSuchElementException nse ) {
		return Constants.KEYWORD_FAIL + nse.getLocalizedMessage() ;
	    }
	    return Constants.KEYWORD_FAIL+"--No user group is found to click";
	}


		
	/**
	 * 26 July, 2013
	 * Tarun Sharma
	 * VerifyEmptyInbox() This method verifies that whether any message exist in the inbox
	 * or not.
	 * This method checks the empty inbox. if there are some messages present then it checks the 
	 * message subject.
	 * 
	 * @param object is the <b> xpath of message subject</b> and <b> xpath of empty inbox message</b> separated by |
	 * @param data is the empty inbox message
	 * @return 
	 */
	public String verifyEmptyInbox(String object, String data)
	{
	    try {
		logger.debug("verifying the inbox....");
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String objArr[]=object.split(Constants.DATA_SPLIT);
		final String ALL_MESSAGE_SUBJECT=objArr[0];
		final String EMPTY_INBOX_MSG=objArr[1];
		final String MESSAGE=data.trim();
		//finds the messages subject if any present
		List<WebElement> allMessages=driver.findElements(By.xpath(OR.getProperty(ALL_MESSAGE_SUBJECT)));
		if(allMessages.size()>0)
		{
			return Constants.KEYWORD_PASS+" There are some messages in inbox. "; 
		}
		else if(allMessages.size()<1)
		{
		    //verify the empty inbox message
		    String emptyInboxResult=keyUtil.verifyText(EMPTY_INBOX_MSG, MESSAGE);
		    return emptyInboxResult+" Inbox is empty";
		}
		else
		{
		    return Constants.KEYWORD_FAIL;
		}
	    } catch (Exception e) {
		return Constants.KEYWORD_FAIL +" Object not found "+e.getLocalizedMessage();
	    }
	}
		
		
	/**
	 * 29 July, 2013
	 * Tarun Sharma
	 * verifyMessageReceived() This method verifies that a particular message is received at
	 * receiver's side or not
	 * 
	 * @param object => xpath of message subject and xpath of link to navigate to check the arrival of message
	 * separated by "|". e.g. => msg_subject_xpath|msg_inbox_link
	 * 
	 * @param data => subject of the message to be searched.
	 * 
	 * @return = > PASS when message passed in the data is found otherwise returns FAIL.
	 */
	public String verifyMessageReceived(final String object, final String data) {
		logger.debug("Checking the arrival of message...");
		try {
			String objArr[]=object.split(Constants.DATA_SPLIT);
			final String temp=data.trim();
			final String msgSubject=objArr[0];
			final String navLink=objArr[1];
			Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 300); // wait for max
			// of 3 min
			ExpectedCondition<Boolean> resultsAreDisplayed = new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver arg0) {
					boolean receiveFlag=false;
					try {
						driver.findElement(By.xpath(OR.getProperty(navLink))).click();

						Thread.sleep(2000);
						String received=isMessagePresent(msgSubject, temp);
						if(received.contains(Constants.KEYWORD_PASS))
						{
							receiveFlag=true;
						}
						return receiveFlag;
					} catch (Exception e) {
						e.printStackTrace();
						return receiveFlag;
					}
				}
			};
			wait.until(resultsAreDisplayed);

			String result=isMessagePresent(msgSubject, temp);

			return result;


		} catch (Exception e) {
			return Constants.KEYWORD_FAIL+" Some Exception occured"
			+ " -- Subject is not displayed in the subject column. "
			+ e.getMessage();

		}
	}
	
	/**
	 * 29 July, 2013
	 * Tarun Sharma
	 * 
	 * isMessagePresent method is used to check that a message is present.
	 * 
	 * @param object => xpath of the message title
	 * 
	 * @param data => Title of the message to be searched
	 * 
	 * @return => This method returns PASS if the message title passed in the data matched 
	 * the title of the message present in the inbox otherwise this method will return FAIL.
	 */
	public String isMessagePresent(String object, String data)
	{
		try
		{
			logger.debug("Looking for the "+data+" ....");
			String temp=data.trim();
			String name;
			List<WebElement> names=driver.findElements(By.xpath(OR.getProperty(object)));
			for(int i=0;i<names.size();i++)
			{
				name=names.get(i).getText().trim();
				logger.debug("data=> "+temp);
				logger.debug("actual => "+name);
				if(name.equals(temp))
				{

					return Constants.KEYWORD_PASS+" Message with title \""+temp+"\" is Present";
				}
				
			}
			
			int nextEnable=driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
			logger.debug("Message with title \""+temp+"\" not found on first page");
			if(nextEnable!=0)
			{
				driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
				Thread.sleep(1500);
				String result=isMessagePresent(object,data);
				return result;
			}
			return Constants.KEYWORD_FAIL+" Message with title \""+temp+"\" not present";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Some exception occured";

		}

	}
	
	
	/**
	 * 30 July, 2013
	 * Tarun Sharma
	 * selectCheckbox(object, data) This method selects a check box corresponding to a value 
	 * 
	 *  in this method object is given in two parts separated by "|".
	 *  
	 *  object => starting xpath of check box|ending xpath of check box
	 *  data => corresponding value against the check box.
	 */

	public String selectCheckbox(String object, String data)
	{
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			logger.debug("finding check box to select...");
			String objArr[]=object.split(Constants.DATA_SPLIT);
			final String CHECKBOX_OBJECT_START=OR.getProperty(objArr[0]);
			final String CHECKBOX_OBJECT_END=OR.getProperty(objArr[1]);
			String checkboxName=data.trim();
			
			//check that element is present or not.
			int size=driver.findElements(By.xpath(CHECKBOX_OBJECT_START+checkboxName+CHECKBOX_OBJECT_END)).size();
		
			if(size>0)
			{
				logger.debug("check box found corresponding to "+checkboxName);
				List<WebElement> checkboxes=driver.findElements(By.xpath(CHECKBOX_OBJECT_START+checkboxName+CHECKBOX_OBJECT_END));
				//if check box found then check that check box is not already selected
				String checked=checkboxes.get(0).getAttribute("checked");
				if(checked==null)
				{
					//if check box is not already checked then check mark the check box.
					checkboxes.get(0).click();
					return Constants.KEYWORD_PASS+" check box corresponding to \""+checkboxName+"\" selected successfully";

				}
				return Constants.KEYWORD_FAIL+" could not select the check box corresponding to \""+checkboxName+"\" " +
						"because it is already checked.";
				
			
			}
			else
			{
				logger.debug("check box not found on current page looking on next page...");

				//if check box not found on first page then check next pages.
				int nextEnable=driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
				if(nextEnable!=0)
				{
					driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
					Thread.sleep(1500);
					String result=selectCheckbox(object,data);
					return result;
				}
				return Constants.KEYWORD_FAIL+" Next > link is not enabled and no checkbox present corresponding to \""+checkboxName+"\" ";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" Object not found";
		}
	}

	/**
	 * 30 July, 2013
	 * Tarun Sharma
	 * 
	 * isMessageNotPresent method is used to check that a message is not present.
	 * 
	 * @param object => xpath of the message title
	 * 
	 * @param data => Title of the message to be searched
	 * 
	 * @return => This method returns PASS if the message title passed in the data is
	 * not present in the inbox otherwise this method will return FAIL.
	 */
	public String isMessageNotPresent(String object, String data)
	{
		try
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			logger.debug("Looking for the "+data+" ....");
			String temp=data.trim();
			String name;
			List<WebElement> names=driver.findElements(By.xpath(OR.getProperty(object)));
			for(int i=0;i<names.size();i++)
			{
				name=names.get(i).getText().trim();
				logger.debug("data=> "+temp);
				logger.debug("actual => "+name);
				if(name.equals(temp))
				{

					return Constants.KEYWORD_FAIL+" Message with title \""+temp+"\" is Present which is not expected";
				}
				
			}
			
			int nextEnable=driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
			logger.debug("Message with title \""+temp+"\" not found on current page looking on next page....");
			if(nextEnable!=0)
			{
				driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
				Thread.sleep(1500);
				String result=isMessageNotPresent(object,data);
				return result;
			}
			return Constants.KEYWORD_PASS+" Message with title \""+temp+"\" is not present";
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Some exception occured";

		}

	}
	

	/**
	 * 30 July, 2013
	 * Tarun Sharma
	 * 
	 * checkMaxAttachment(Object, Data) This method check the maximum number of attachments
	 * that can be attached to a message while sending a message.
	 * 
	 * @param object is the xpath of the link which displays the file upload button.
	 * 
	 * @param data is the maximum number of attachments that can be attached.
	 * 
	 * @return PASS if after clicking maximum number of times on the "Attachment link", that 
	 * link disappears. It will return FAIL otherwise.
	 * 
	 */
	public String checkMaxAttachment(String object, String data)
	{
		try {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			logger.debug("Checking the attachments....");
			int maxNum=Integer.parseInt(data); // get the maximum number from the excel sheet.
			boolean attachLink;
			WebElement documentAttachLink=driver.findElement(By.xpath(OR.getProperty(object)));
			String documentAttachLinkText=documentAttachLink.getText();
			logger.debug("Maximum number of attachments should be "+maxNum);
			
			for(int i=0;i<maxNum;i++)
			{
				documentAttachLink.click();
				Thread.sleep(1000);
				
			}
			
			 // check after clicking the link maximum number of time, the link is displayed or not.
			attachLink=documentAttachLink.isDisplayed(); 
			
			if(attachLink)
			{
				return Constants.KEYWORD_FAIL+" \""+documentAttachLinkText+"\" link is still visible" +
						"after clicking \""+maxNum+"\" of times.";
			}
			
			return Constants.KEYWORD_PASS+" \""+documentAttachLinkText+"\" link is hidden " +
			"after clicking \""+maxNum+"\" times.";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" Some Exception occured while converting data into maximum number";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" Interrupted Exception in Thread";
		}
	}
	
	/**
	 * Tarun Sharma
	 * 30 July, 2013
	 * clickWebElement(String object, String data)
	 * 
	 * This method clicks on a WebElement whose text is passed from the data
	 * 
	 * 
	 *  In this method object is passed in two parts separated by "|"
	 *  
	 *  
	 *  Object => webelement_start_xpath|webelement_end_xpath
	 *  Data => Text on the WebElement
	 */

	public String clickWebElement(String object, String data)
	{

		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			String objectArr[]=object.split(Constants.DATA_SPLIT);

			final String START_OBJECT=OR.getProperty(objectArr[0]);
			final String END_OBJECT=OR.getProperty(objectArr[1]);
			final String VALUE=data.trim();

			logger.debug("clicking on \""+VALUE+"\"....");
			int size=driver.findElements(By.xpath(START_OBJECT+VALUE+END_OBJECT)).size();

			if(size>0)
			{
				logger.debug("Web element found");
				List<WebElement> elements=driver.findElements(By.xpath(START_OBJECT+VALUE+END_OBJECT));
				WebElement element=elements.get(0);
				element.click();
				return Constants.KEYWORD_PASS+" clicked on  \""+VALUE+"\"" ;

			}
			else
			{
				logger.debug("Web element not found on first page looking on next page...");

				int nextEnable=driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
				if(nextEnable!=0)
				{
					driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
					Thread.sleep(1500);
					String result=clickWebElement(object,data);
					return result;
				}
			}
			return Constants.KEYWORD_FAIL+" Unable to click on element with value \""+VALUE+"\"";
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL+" object not found";
		}


	}
	
	
	/**
	 * 5 August, 2013
	 * Tarun Sharma
	 * countReceivedMessage() This method counts the no. of times a message is received
	 * it takes the Subject of the message and the no of the times the message has been 
	 * sent separated
	 * For Example :- Test Message,2
	 * @param object xpath of the messages subjects
	 * @param data is message subject and number of times the messages should be received
	 * @return PASS if expected count of the messages matches the actual count.
	 */
	public String countReceivedMessage(final String object, final String data)
	{
		try {
			logger.debug("Counting the messages received....");
			WebDriverWait wait = new WebDriverWait(driver, 300); // wait for max
			// of 3 min
			ExpectedCondition<Boolean> resultsAreDisplayed = new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver arg0) {
					boolean receiveFlag=false;
					try {
						driver.findElement(By.xpath(OR.getProperty("messages_inbox_link"))).click();

						Thread.sleep(2000);
						String received=countMessage(object, data);
						if(received.contains(Constants.KEYWORD_PASS))
						{
							receiveFlag=true;
						}
						return receiveFlag;
					} catch (Exception e) {
						e.printStackTrace();
						return receiveFlag;
					}
				}
			};
			wait.until(resultsAreDisplayed);

			String result=countMessage(object, data);

			return result;


		} catch (Exception e) {
			return Constants.KEYWORD_FAIL+" Some Exception occured"
			+ " -- Count is not matched. "
			+ e.getMessage();

		}
	}

	public String countMessage(String object, String data)
	{
		try
		{
			
			String temp=data.trim();
			String [] tempData=temp.split(",");
			String subject=tempData[0].trim();
			int expectedCount=Integer.parseInt(tempData[1]);
			boolean nameflag=false;
			int counter=0;
			List<WebElement> names=driver.findElements(By.xpath(OR.getProperty(object)));
			for(int i=0;i<names.size();i++)
			{
				String actual=names.get(i).getText().trim();
				if(actual.equals(subject))
				{
					nameflag=true;
					counter++;

				}
			}
			logger.debug("Message is received \""+counter+"\" number of times");
			if(!nameflag)
			{
				
				int lastpage=driver.findElements(By.linkText((OR.getProperty("next_link")))).size();
				if(lastpage>0)
				{
					driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
					Thread.sleep(2000);
					String result=countReceivedMessage(object,data);
					return result;
				}
				else
				{
					return Constants.KEYWORD_FAIL+"element not found";
				}

			}

			if(nameflag &&(counter==expectedCount))
			{
				return Constants.KEYWORD_PASS+" element found and count matches";

			}
			return Constants.KEYWORD_FAIL+" count did not match";

		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Following exception occured \n\n"+e;
		}

	}

	
	
	/**Added by vikas bhadwal on 12,November 2013
	 * this method is used to store
	 * all courses count.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	
	public String storeTotalCourses(String object,String data)
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.debug("storeTotalCourses");
		try
		{
			total_courses_stored = driver.findElements(By.xpath(OR.getProperty(object))).size();
			return Constants.KEYWORD_PASS + " "  + total_courses_stored;
		
				
		}
		catch(Exception e)
		{
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
	}
	
	
	/**Added by vikas bhadwal on 12,November 2013
	 * this method is used to verify courses count.
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	
	
	public String verifyTotalCourses(String object,String data)
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.debug("verifyTotalCourses");
		try
		{
		int course_catalog_courses_total = driver.findElements(By.xpath(OR.getProperty(object))).size();
		
     			   if(total_courses_stored == course_catalog_courses_total)
     			   {
			          return Constants.KEYWORD_PASS + " -- All courses present" + " " + course_catalog_courses_total;
			   }
     			   else{
			return Constants.KEYWORD_FAIL + " -- All  courses are not present"+ course_catalog_courses_total;
			   }
			} catch (Exception e) {
			   return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
			}
			}
			
		}

	
	
	
	
	
	
	
	
