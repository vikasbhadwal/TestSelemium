package com.tk20.seleniumuiflipassessment.test.courses;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.driver;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementSize;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill;

public class CRS_KeywordEvents {
	String date_text= null;
	String Modifieddate_text=null;
	int admin_Org_size,courses_org_size;
	int titleCount=0;
	List<String> allDropdownElements=new ArrayList<String>();	
	 List<WebElement> allRows=new ArrayList<WebElement>();
	 List<WebElement> allCBs=new ArrayList<WebElement>();
	 List<String> allListDomainSubject=new ArrayList<String>();
	 List<String> allSubjects=new ArrayList<String>();
	 KeywordEventsUtill keyUtil = (KeywordEventsUtill) KeywordEventsUtill.keyUtillFactory();
	/**
	 * This method verify that All courses are present on some Expected Location.<Br>
	 * 
	 * @author Pankaj Sharma
	 * @since 30 Dec, 2013
	 * 
	 * @param object: It accepts three xpath that are separated by '|'. First xpath <Br>
	 *                is of label that shows the total no of Courses on the Expected 
	 *                Location, For Example : 1 - 25 of 308 <br>
	 *                And Second xpath is of label that shows the total no of Courses 
	 *                on the another location from where you can verify that All courses are <Br>
	 *                Present, For Example : 1 - 25 of 308 (Some other location)<br>
	 *                And Third xpath is of the other location
	 *                Restriction: Do not Change the order of xpaths in object
	 *                parameter <br>
	 *                
	 * 
	 * @param data : Do not Require.
	 * 
	 * @return <b>PASS</b>If the all the courses are present <br>
	 *         <b>FAIL</b> otherwise.
	 */
	public String verifyAllCoursesPresent(String object, String data) {
		try {
			logger.debug("Verifing the dropdown menu changes according to Last page");
			
			String objArr[] = object.split(Constants.DATA_SPLIT);
			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];
			final String XPATH3 = objArr[2];
					
			// validate the parameters
			if (XPATH1 == null || XPATH2 == null || XPATH3 == null ) {
				return Constants.KEYWORD_FAIL+ " Either Xpath1 or Xpath2 is null. Please check the xpath";
			}
			
			String verifyLocation=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(XPATH2)))).getText();
			String CRSPresent=verifyLocation.split("of ")[1];
			logger.debug("Total Courses Displayed :"+CRSPresent);

			wait.until(explicitWaitForElement(By.xpath(OR.getProperty(XPATH3)))).click();
			Thread.sleep(5000);
			
			String orignalLocation=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(XPATH1)))).getText();
			String actualTotalCRS=orignalLocation.split("of ")[1];
			logger.debug("Total Courses are :"+actualTotalCRS);
			
			if (actualTotalCRS.equals(CRSPresent)) {
				return Constants.KEYWORD_PASS+ " -- All Courses are Present ";
			} else {
				return Constants.KEYWORD_FAIL+ "  -- All Courses are not Present";
			}
		}
		
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+ " -Some Exception occured "+ e.getMessage();
		}

	}
	/*02/01/2014 Added By Pallavi Singla This method is used to
     *Move all Elements at desired location.
     *Elements move util all are not move.
     */
   
    public String moveElementsUntilnotMove(String object,String data)
    {
        try{

            String[] objects=object.split(",") ;
            String checkboxes=objects[0];
            String drpdown=objects[1];
           
            while(true)
            {
               
                int element_count=explictWaitForElementSize(checkboxes);

                if(element_count>0)
                {
                    new KeywordEventsUtill().checkAllCheckBox(checkboxes, data);
                    new KeywordEventsUtill().selectList(drpdown, data);
                   
                    Thread.sleep(3000);
                }
                else{
                    return Constants.KEYWORD_PASS+" all elements have been removed";               
                }
            }
        }catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
        catch(Exception e)
        {
            return Constants.KEYWORD_FAIL+"-"+e.getMessage();

        }                   

    }
   
	
	/**Created by Pooja Sharma On 17 January 2014 This method is used to verify the Drop down Value on clicking the Previous Pagination Link
	 * @param object
	 * @param data:page_drop_down|pagination_previous_lnk
	 * @return
	 * 
	 */
	 
	
	public String verifyDropDownValue(String object, String data) {

        try {
            logger.debug("entered into verifyDropDownValue");
            String objArr[] = object.split(Constants.DATA_SPLIT);
            final String dd_value = objArr[0];// xpath of Page drop down
            final String pagination_previous_lnk = objArr[1];  //xpath of Previous Pagination Link.

            WebElement dropdown =wait.until(explicitWaitForElement(By.xpath(OR.getProperty(dd_value))));
            List<WebElement> options = dropdown.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
            int size = options.size();
            int previous_page_no=size-1; //get the value of previous page from Last ,from the Page Drop down.
            driver.findElement(By.xpath(OR.getProperty(pagination_previous_lnk))).click(); // click on the Previous pagination link.

            WebElement dropdown_value = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(dd_value))));
            Select sel = new Select(dropdown_value);
            String SelectedVal = sel.getFirstSelectedOption().getText();////get the value of previous page from Last ,from the Page Drop down,on clicking Previous Pagination Link.

            int aftclick_size = Integer.parseInt(SelectedVal);
            if (previous_page_no == aftclick_size) {
                return Constants.KEYWORD_PASS+ " -- Value is changing accordingly ";
            } else {
                return Constants.KEYWORD_FAIL+ "  -- Value is not changing accordingly";
            }

        }catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		} catch (Exception e) {
            return Constants.KEYWORD_FAIL + " - Could not select from list. "+ e.getMessage();

        }

    }

	public String verifyDisabledContentInPrintWindow(String object,String data) 
	{
		logger.debug("Inside verifyDisabledContentInPrintWindow() ");
		try{
			/*boolean flag=false;
			boolean flag2=false;*/
			String mainwindow="";
			String newwindow="";
			Set <String> windowids = driver.getWindowHandles();
			Iterator<String> ite=windowids.iterator();
			mainwindow=ite.next();
			newwindow=ite.next();		
			driver.switchTo().window(newwindow);
			Thread.sleep(5000);
			
			String objArr[]=object.split(",");
			
			for(int i=0;i<objArr.length;i++){
				if(driver.findElements(By.xpath(OR.getProperty(objArr[i]))).size()>0){
					continue;
				}
				else{
					driver.close();
					driver.switchTo().window(mainwindow);
                    return Constants.KEYWORD_FAIL+" -elements in printable window are not disabled" ;

				}
			}
		
						
			String url = driver.getCurrentUrl();
			driver.close();		
			driver.switchTo().window(mainwindow);
			if(url.contains(data))
			{
				return Constants.KEYWORD_PASS+" printable window opens and elements are disabled";
			}
			else{
				return Constants.KEYWORD_FAIL+" printable window does not open" ;
			}

		}catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}catch (Exception nse ) {
			// TODO Auto-generated catch block
			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		} 
	}
	
	/**
	 * Pooja Sharma 7,feb, 2014, This method verifies the count of organizations
	   present in the Administration>organizations is same as present in the 
	   courses>Assignment Template Builder>create>select button of Organization * 
	 * @param object
	 * @param data
	 * @return
	 */
	
		public String admin_Expand_org_count(String object, String data)throws Exception {
		logger.debug("Entered into admin_Expand_org_count()");

		
		try {
			List <WebElement> expected=explictWaitForElementList(object);
			admin_Org_size=expected.size();
			
			if(admin_Org_size==courses_org_size){
				return Constants.KEYWORD_PASS+" All the organization folders are expanded";
			}
						
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
			return Constants.KEYWORD_FAIL;
	}
		
		/**
		 * Pooja Sharma 7,feb, 2014, This method verifies the count of organizations
		   present in the courses>Assignment Template Builder>create>select button of Organization * 
		 * @param object
		 * @param general xpath for checkboxes corresponding to all the organizations.
		 * @return
		 */
	
	public String courses_Expand_org_count(String object, String data)throws Exception {
		logger.debug("Entered into courses_Expand_org_count()");
		
		try {
			List <WebElement> expected=explictWaitForElementList(object);
			courses_org_size=expected.size();
			return Constants.KEYWORD_PASS+"  size="+(courses_org_size);
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
	
	}
	
	
	
	
	
	
	
	public String more_link_org_count(String object, String data)throws Exception {
		logger.debug("Entered into admin_Expand_org_count()");

		
		try {
			    List<String> expected = new ArrayList<String>();
			 while (true) {

		            WebElement expected_ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
		            String[] exp=expected_ele.getText().split(", ");
		            
		            for (int i = 0; i < exp.length; i++) {
		                if (exp[i].trim().length() != 0) {
		                    
		                  expected.add(exp[i].trim());
		                }
		            }
		            break;
		        }
			
			admin_Org_size=expected.size();
			
			if(admin_Org_size==courses_org_size){
				return Constants.KEYWORD_PASS+" All the organization folders are expanded";
			}
						
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
			return Constants.KEYWORD_FAIL;
	}

	
	
	
	
	
	
	/**
	 * Pooja Sharma 7,feb, 2014, This method verifies that on clicking yes button of alert
	 * the checkboxes corresponding to all the organizations get selected
	 * @param object
	 * @param general xpath for checkboxes corresponding to all the organizations.
	 * @return
	 */

	public String verifyAllCbSelected(String object, String data)throws Exception {
 		logger.debug("Entered into verifyAllCbSelected()");
		
		try {
			List <WebElement> expected=explictWaitForElementList(object);
			int unchecked_cb_size=expected.size();
			boolean value;
			int count=0;
			for(int i=0;i<unchecked_cb_size;i++){
				value=expected.get(i).isSelected();
				count++;
				if(value!=true)
				{
					count=0;
					break;
				}
			}
			if(count==unchecked_cb_size)
			{
				return Constants.KEYWORD_PASS+"All the checkboxes are selected  ";	
			}
			else{
				return Constants.KEYWORD_FAIL+"All the checkboxes are not selected  ";
			}
			
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
	

	}
	
	/**
	 * Pooja Sharma 7,feb, 2014, This method verifies that the selected organizations i.e. present in a single row, are in alphabetical order.
	 * @param object
	 * @param general xpath for checkboxes corresponding to all the organizations.
	 * @return
	 */
	
	
	@SuppressWarnings("unchecked")
	public String verifySelectedOrgSorted(String object, String data)throws Exception {
		logger.debug("Entered into verifySelectedOrgSorted()");
		
		try {
			WebElement expected=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String orgs=expected.getText();
			@SuppressWarnings("rawtypes")
			List actual_value=new ArrayList<String>();
			@SuppressWarnings("rawtypes")
			List expected_value=new ArrayList<String>();

			String[] values=orgs.split(",");
			int length=values.length;
			for(int i=0;i<length;i++){
				actual_value.add(values[i]);
				expected_value.add(values[i]);
			}
			
			Collections.sort(expected_value, String.CASE_INSENSITIVE_ORDER);	
			logger.debug("actual is as follows-- " + actual_value);
			logger.debug("expected is as follows-- " + expected_value);

			if (expected_value.equals(actual_value)) {
				return Constants.KEYWORD_PASS + " -- Elements are sorted";
			} else
				return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
			
			
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
	
	}
	
	
	/**
	 * Pooja Sharma 7,feb, 2014, This method verifies that on clicking no button of alert
	 * not any of the checkboxes corresponding to all the organizations is selected.
	 * @param object
	 * @param general xpath for checkboxes corresponding to all the organizations.
	 * @return
	 */

	
	public String verifyAllCbNotSelected(String object, String data)throws Exception {
 		logger.debug("Entered into verifyAllCbNotSelected()");
		
		try {
			List <WebElement> expected=explictWaitForElementList(object);
			int unchecked_cb_size=expected.size();
			boolean value;
			int count=0;
			for(int i=0;i<unchecked_cb_size;i++){
				value=expected.get(i).isSelected();
				
				if(value==false)
				{
					count++;
				}
				if(value==true){
					break;
				}
			}
			if(count==unchecked_cb_size)
			{
				return Constants.KEYWORD_PASS+"All checkboxes are not selected  ";	
			}
			else{
				return Constants.KEYWORD_FAIL+"Any checkbox is selected  ";
			}
			
		}
		catch(TimeoutException ex){
			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
		}
	

	}
	
	 /**
	 	 *  @since  01/10/2014
	 	 *  @author Surender
	 	 *  This method Saves All the Elements of the dropdown.
	 	 *  @param object : Xpath of Dropdown upto <option> tag.
	 	 *  @param data: Not Required
	 	 */
	 	
	 	public String saveAllDropdownElements(String object, String data)throws Exception {
	 		logger.debug("Entered into saveAllDropdownElements()");
	 		try {
	 		    if(!allDropdownElements.isEmpty()){
	 			allDropdownElements.clear();
	 		    }
	 				List<WebElement> expected =explictWaitForElementList(object);
	 			    for(int i=0;i<expected.size();i++){
	 				if(expected.get(i).getText().trim().length()!=0){
	 				    logger.debug("UGs:-"+expected.get(i).getText().trim());
	 				    allDropdownElements.add(expected.get(i).getText().trim());
	 				}
	 		    }
	 		    return Constants.KEYWORD_PASS + " -- All Dropdown elements have been Stored";
	 		} 
	 		catch(TimeoutException ex){
	 			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
	 		}
	 		catch (Exception e) {
	 		    return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
	 		}
	 	}
	 	
	 	/**
	 	 *  @since  01/10/2014 
	 	 *  @author Surender
	 	 *  This method verify all the dropdwon values saved in saveallDropdownElements method with all the links present .
	 	 *  @param object : Commeon xpath of links.
	 	 *  @param data: Not Required
	 	 */
	 	public String verifyAllDropdownElements(String object, String data)throws Exception {
	 		logger.debug("Entered into verifyAllDropdownElements()");
	 		try {
	 		    List<String> actual=new ArrayList<String>();
	 		    int count=0;
	 		    while(true){
	 			int size=driver.findElements(By.linkText(OR.getProperty("next_link"))).size();
	 			List<WebElement> expected= explictWaitForElementList(object);
	 			if(size>0){
	 			    for(int i=0;i<expected.size();i++){
	 				if(expected.get(i).getText().trim().length()!=0){
	 				    logger.debug("Links:-"+expected.get(i).getText().trim());
	 				    actual.add(expected.get(i).getText().trim());
	 				}
	 			    }
	 			    driver.findElement(By.linkText(OR.getProperty("next_link"))).click();
	 			}
	 			else{
	 			    expected = explictWaitForElementList(object);
	 			    for(int i=0;i<expected.size();i++){
	 				if(expected.get(i).getText().trim().length()!=0){
	 				    logger.debug("Links:-"+expected.get(i).getText().trim());
	 				    actual.add(expected.get(i).getText().trim());
	 				}
	 			    }
	 			    if(driver.findElements(By.linkText(OR.getProperty("next_link"))).size()==0){
	 				break;
	 			    }
	 			}
	 		    }
	 		    for (int i=0;i<this.allDropdownElements.size();i++) {
	 			for(int j=0;j<actual.size();j++){
	 			    if(allDropdownElements.get(i).trim().equals(actual.get(j).trim())){
	 				count++;
	 				break;
	 			    }
	 			}
	 		    }
	 		    if(count==actual.size()&&count==allDropdownElements.size()&& allDropdownElements.size()==actual.size()){
	 			return Constants.KEYWORD_PASS + " -- All Dropdown elements are  Verified";
	 		    }else{
	 			return Constants.KEYWORD_FAIL + " -- All Elements not verified";
	 		    }
	 		} 
	 		catch(TimeoutException ex){
	 			
	 			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
	 		}
	 		catch (Exception e) {
	 		    return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
	 		}
	 	}
	 	
	 	/**
	 	 *  @since  15/10/2014 
	 	 *  @author Pankaj Dogra
		 * This method use to Get the Total Count of Courses in a table
		 * @param object:Common xpath of Rows under Table
		 * @param data : Does not Require
		 * @return
		 */

		public String countAllCourses(String object, String data)throws Exception {
			logger.debug("Entered into countAllCourses()");
			try {
				allRows= explictWaitForElementList(object);
				allRows.size();
				return Constants.KEYWORD_PASS + "---The Total Count of Courses is:"+(allRows.size());
			} 
			catch(TimeoutException ex){
				
				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			
			catch (Exception e) {
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}

		}
		/**
	 	 *  @since  15/10/2014 
	 	 *  @author Pankaj Dogra
		 * This method is used to Match either check box is display all courses having
		 *  display check corresponding each courses.
		 * @param object:Common xpath of Rows under Table
		 * @param data : Does not Require
		 * @return
		 */
		public String verifyRowCBCount(String object, String data)throws Exception {
			logger.debug("Entered into verifyRowCBCount()");
			try {
				
				allCBs= explictWaitForElementList(object);
				allCBs.size();
				logger.debug("the count of checkboxes is:"+(allCBs.size()));
			       if(allCBs.size()==(allRows.size()))
					return Constants.KEYWORD_PASS + " -- The Count of Checkbox and Courses are same ";
				else {
					return Constants.KEYWORD_FAIL + " -- The Count of Checkbox and Courses are not same  ";
				}
			}
	catch(TimeoutException ex){
				
				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			
			catch (Exception e) {
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}

		}
		 /**@author Pankaj Dogra 20/10/2014
		 * This method will save the Total counts of Subject under Administration->Meta data->List Domains->Subjects
		 * @param object:single xpath of all the
		 * @return
		 */
		public String listDomainSubjectCount(String object, String data)throws Exception {
			logger.debug("Entered into SecurityRolesCount()");
			try {
				
				
				List <WebElement> expected=explictWaitForElementList(object);
				for (WebElement element : expected) {
					allListDomainSubject.add(element.getText().trim());
				}
				
				
				return Constants.KEYWORD_PASS+"--size="+(allListDomainSubject.size());
			}
			catch(TimeoutException ex){
				
				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			
			catch (Exception e) {
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}

		}

		/**@author Pankaj Dogra 20/10/2014
		 * This method will verify whether all subject under Administration->Meta data->List Domains->Subjects
		 * contains same value for Courses->Course work->Project->Send->Add plus icn->Subject Drop down
		 * @param object:xpath of the column containing roles
		 * @param data
		 * @return
		 */

		public String dropDownSubjectCount(String object, String data)throws Exception {
			logger.debug("Entered into DDRolesCount()");
			try {
				int i=0;
				
				List <WebElement> expected=explictWaitForElementList(object);
				for (WebElement element : expected) {
					allSubjects.add(element.getText().trim());
				}
				for (String stu : allSubjects) {
					if((stu.equals(OR.getProperty("any").trim()))){
						allSubjects.remove(i);
						break;
					}
					i++;
					
					
				}
				logger.debug("count of Subjects under List Domain :-"+allListDomainSubject.size());
				logger.debug("count of Subjects Under Drop down :-"+allSubjects.size());
				int  security_roles_count=allListDomainSubject.size();
				int dd_roles_count=allSubjects.size();
				if(security_roles_count==dd_roles_count){
					return Constants.KEYWORD_PASS+"Contains same values";
				}else{
					return Constants.KEYWORD_FAIL+"does not contains same values";
				}

			} 
			catch(TimeoutException ex){
				
				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			
			catch (Exception e) {
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}

		}

		

		/**
	 	 *  @since  16/06/2015 
	 	 *  @author Pankaj Dogra
		 *           This method is used to save count of Common Elements
		 * @param object: Common Xpath of all elements
		 * @param data : Does not require
		 * @return : Pass if object present.
		 */
		
		public String saveCount(String object, String data)
		{
			logger.debug("Entered into saveCount()");
			try{
				List <WebElement> expected=explictWaitForElementList(object);
				titleCount=expected.size();
				logger.debug("No. of Count Present : "+titleCount);
				return Constants.KEYWORD_PASS+ "--Save Total Number of matching nodes :"+titleCount;
			}
			catch(TimeoutException ex){
				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			catch(Exception e){
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}	
		}
		/**
	 	 *  @since  16/06/2015 
	 	 *  @author Pankaj Dogra
		 *           This method is used to verify whether Incremented value is present in text box
		 * @param object: xpath of Input Field
		 * @param data : it will accept the constant word of Input Field i.e  Video Artifact
		 * @return : Pass if incremented data display in input Fields
		 */
		public String verifyIncrementedCount(String object, String data)
		{
			logger.debug("Entered into verifyIncrementedCount()");
			try{
				titleCount=titleCount+1;
				String value=data.trim();
				value=value+" "+titleCount;
				logger.debug("Value present in given xpath is : "+value);
				String result=keyUtil.verifyTextinInput(object, String.valueOf(value));
			    if(result.contains(Constants.KEYWORD_PASS)){
				return Constants.KEYWORD_PASS + " -- Video Artifact is present with : "+value;
			    }
			    else{
				return Constants.KEYWORD_FAIL + " -- Incremented value is not present";
			    }
			}
			catch(TimeoutException ex){



		

				return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
			}
			catch(Exception e){
				return Constants.KEYWORD_FAIL+" -- Object Not Found"+e.getMessage();
			}
			
		}
		
		/**
		 * Added By Naincy Saini Date: 06/07/2015
		 * 		 * 
		 * This method is used to verify Date Time Format after splitting the String at "on"
		 * 
		 * @param object
		 *            : xpath of String containing date time.
		 * 
		 * @param data
		 *            : Pass Date-Time format as MM/dd/yyyy HH:mm:ss aaa
		 * */
		
		public String verifyDateAndTimeFormatUsingSplit(String object, String data) {
			logger.debug("entered into verifyDateAndTimeFormatUsingSplit");

			String actual_pattern = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String values[] = actual_pattern.split("on");
			String actualValue = values[1].trim().substring(0, 23);
			SimpleDateFormat sdf = new SimpleDateFormat(data.trim());
			boolean flag = false;
			try {
				sdf.setLenient(false);
				sdf.parse(actualValue);
				flag = true;
			} catch (TimeoutException ex)

			{
				return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
			} catch (Exception e) {
				e.printStackTrace();
				return Constants.KEYWORD_FAIL + " -- date is not in correct format";
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- date is in correct format";
			} else {
				return Constants.KEYWORD_FAIL
						+ " -- date and time is not in correct format";

			}
		}
		
		/**
		 * Added By Naincy Saini Date: 24/09/2015
		 * 		 * 
		 * This method is used to verify Date Time Format 
		 * 
		 * @param object
		 *            : xpath of Date
		 * 
		 * @param data
		 *            : none
		 * * */          
		
		
		public String verifyDate(String object, String data) {
			logger.debug("verifying the date format");
			try {
				date_text = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
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

		/**
		 * Added By Naincy Saini Date: 24/09/2015
		 * 		 * 
		 * This method is used to verify Date Time Format after modification and verifies that it is different 
		 * 
		 * @param object
		 *            : xpath of String containing date time.
		 * 
		 * @param data
		 *            : None
		 * */
		
		public String verifyModifiedDate(String object, String data) {
			logger.debug("verifying the date format");
			try {
				Modifieddate_text = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
				String date[] = Modifieddate_text.split("/");
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
				if (flag && Modifieddate_text.equals(date_text)) {
					
					return Constants.KEYWORD_FAIL + "--date is not in correct format";
					
				}

				else {
					return Constants.KEYWORD_PASS + "--date is in correct format and is modified";				}

			} 
	catch(TimeoutException ex)
			
			{
				return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
			}
			catch (Exception e) {
			
				return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
			}
		}

		
}



