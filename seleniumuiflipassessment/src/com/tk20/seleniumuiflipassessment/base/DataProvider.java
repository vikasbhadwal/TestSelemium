package com.tk20.seleniumuiflipassessment.base;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tk20.seleniumuiflipassessment.accessibility.AccessibilityMethods;
import com.tk20.seleniumuiflipassessment.datareader.TestCaseInputReader;
import com.tk20.seleniumuiflipassessment.util.ApplicationSpecificKeywordEventsUtil;
import com.tk20.seleniumuiflipassessment.util.DashBoard;
import com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill;
import com.tk20.seleniumuiflipassessment.util.ReportUtil;
public class DataProvider {

	public static Logger logger, accessibilityLogger;
	// suite.xlsx
	public  TestCaseInputReader inputReader;
	public int suiteID;
	public int timeSuiteID;
	public static String testSuite;
	public static String moduleName;
	// current test suite
	public static TestCaseInputReader testInputReader;
	public static int testCaseID;
	public static String testCaseName;
	public static int testStepID;
	public static String keyword;
	public static int testDataInputID = 2;
	public static Method[] appSpecificUtilMethod; // variable to hold application specific common methods
	public static Method method[];
	public static Method util_method[];
	public static Method capturescreenShot_method;
	public static Method checkAccessibilityError;
	public static KeywordEventsUtill utilKeyEvents;
	public static ApplicationSpecificKeywordEventsUtil appSpecificKeyEvents;
	public static AccessibilityMethods accessibilityKeyEvents;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;
	public static String data;
	public String description;
	public static String object;
	public static Properties CONFIG;
	public static Properties OR = null, OR_specific = null, tab_OR = null;
	public static FileInputStream fs;
	public static int x = 2;
	public static long executionTime;
	public static int y = 2;
	public int a = 2;
	// Added by Sanjay On 23rd july 2014
	public int xpathNotFoundCounter;
	public int methodNotFoundCounter;
	public int numberOfXpathsNotFound = 0;
	public int numberOfMethodsNotFound = 0;	
	String suite_runmode_status = null;
	public static Method[] allClassSpecificsMethods;// pankaj
	public File[] files;
	public String normalPackagePath;
	public List<String> appSpecificMethodList = new ArrayList<String>();
	public List<String> util_list = new ArrayList<String>();
	public List<String> specific_Method_List = new ArrayList<String>();
	public Object currentInstance;
	public ArrayList specificClassMethodList = new ArrayList();
	public Method[] particularClassMethods;
	public ArrayList alradyCreatedObjectRefList = new ArrayList();
	public ArrayList alreadyCreatedInstanceList = new ArrayList();
	String duplicacy = null;
	String common_duplicacy = null;
	public static long[] suiteTime;// sumit
	// Added by Kritika
	Boolean flag = false;
	public TestCaseInputReader inputReaderXPath;
	public static String module_Name;
	public String test_Suite;	
	public static String buildNum; //Karan Sood
	public static int failCount = 0;  //Vikas
	public static DashBoard dashboard;
	public static int TotalSuiteCount = 0;
	public int total_lines_of_code = 0;
	public static int testsStepID;
	public static int testDataInputIDs;
	int total_lines_of_code_m = 0;
	int current_module_reaming_line = 0;
	int suite_lines[];
	int iterations;
	int currentModuleLineCount;	
	static Properties prop=null;//Nitin
	String log4jpropertyPath = System.getProperty("user.dir")+ "/externalFiles/config/log4j.properties";
	String mylog4jpropertyPath = System.getProperty("user.dir")+ "/externalFiles/config/Mylog4j.properties";
	public static Set<String> folderInitials= new HashSet<String>();

	//Added by Surender on 14/10/2015 for displaying progress bar.
	    public static float pvalue=0.000f;
	   public static int pcount=1;
	  public static int lines_executed=0;
	  public static int current_suite_total_lines = 0;
	  public static  JProgressBar progressBar = null;
   
	public DataProvider() throws NoSuchMethodException, SecurityException,
	IOException {
		String log4jpropertyPath = System.getProperty("user.dir")
				+ "/externalFiles/config/log4j.properties";
		prop = new Properties();
		prop.load(new FileInputStream(log4jpropertyPath));
		PropertyConfigurator.configure(prop);
		logger = Logger.getLogger("testenvLogger");
		logger.debug("Properties loaded. Starting testing");
		if (CONFIG.getProperty("performAcceessibilityTest").equals(Constants.RUNMODE_YES)) {   //Added Accessibility Code
			accessibilityLogger = Logger.getLogger("accessibilityLogger");
			accessibilityLogger.debug("Properties loaded. Starting testing");
		}
		accessibilityKeyEvents = new AccessibilityMethods();
		checkAccessibilityError = accessibilityKeyEvents.getClass().getMethod("checkAccessibilityError", String.class, String.class);
		utilKeyEvents = new KeywordEventsUtill();
		appSpecificKeyEvents = new ApplicationSpecificKeywordEventsUtil();
		util_method = utilKeyEvents.getClass().getMethods();
		appSpecificUtilMethod = appSpecificKeyEvents.getClass().getMethods();
		capturescreenShot_method = utilKeyEvents.getClass().getMethod("captureScreenshot", String.class, String.class,Integer.class); //added fourth argument to get screenshot in html report and log.
	}

	public static void main(String[] args) throws IllegalAccessException,IllegalArgumentException, InvocationTargetException, IOException,
	NoSuchMethodException, SecurityException, ClassNotFoundException {
		
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long totalTime = 0;
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+ "/externalFiles/config/config.properties");
		CONFIG = new Properties();
		CONFIG.load(fs);
		DataProvider provider = new DataProvider();
		provider.readXLS();
		dashboard.stopTotalTime();    // Added by vikas to stop the dashboard timer.
		endTime = System.currentTimeMillis();
		executionTime = ((endTime - startTime) / 1000) / 60;

		JOptionPane.showMessageDialog(null, "Done\n Total Execution Time is "+ executionTime + " Minutes", "Success..!",JOptionPane.INFORMATION_MESSAGE);

		/**
		 * the following code will generate the HTML reports if generate report
		 * property is Y in config.properties file.
		 */
		if (CONFIG.getProperty("generate_report").equals(Constants.RUNMODE_YES)) {
			ReportUtil report = new ReportUtil();
			try {
				startTime = System.currentTimeMillis();
				report.generateHTMLReport(suiteTime, buildNum); // Modified by Sumit on 04/03/2014 Passed long type array in method having all suites execution time.
				//Modified by Karan Sood on 2nd December 2014, Passes another argument buildNum to method generateHTMLReport

				// get the end time of the execution
				// calculate the total execution time of execution.
				endTime = System.currentTimeMillis();

				//Added by Nitin Gupta on 19th March 2015 to createZipFile
				String zipFileName=appSpecificKeyEvents.createZipFile();

				totalTime = (endTime - startTime) / 1000;
				JOptionPane.showMessageDialog(null,"Reports generated Successfully...! \n "
						+ "Total execution time is "+ totalTime + " seconds.", "Success..!",JOptionPane.INFORMATION_MESSAGE);

				//Added by Nitin Gupta on 17th Feb 2015 to sendEmail and delete Mylog4j.properties file
				if(CONFIG.getProperty("sendEmail").equals(Constants.RUNMODE_YES))
				{
					sendMail.mail(zipFileName);

				}
				appSpecificKeyEvents.deleteFileRecursively(new File(System.getProperty("user.dir")+ "/externalFiles/config/" +"Mylog4j.properties"));
				logger.debug("Mylog4j.properties file deleted");

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Oops...! Some Error in generating reports...! \n"
						+ " Total execution time is "+ totalTime + " seconds.", "Error...!",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void readXLS() throws IllegalAccessException,
	IllegalArgumentException, InvocationTargetException,
	NoSuchMethodException, SecurityException, FileNotFoundException,
	IOException, ClassNotFoundException {

		logger.debug("Properties loaded. Starting testing");
		// 1) check the runmode of test Suite
		// 2) Runmode of the test case in test suite
		// 3) Execute keywords of the test case serially
		// 4) Execute Keywords as many times as
		// number of data sets - set to Y
		logger.debug("Intialize Suite xlsx");

		// Added by Pankaj Sharma.22 November, 2013
		// This verifies that the Suite.xls file is Open or Not.
		appSpecificKeyEvents.isFileOpen(System.getProperty("user.dir")+ "/externalFiles/xls/" + "Suite.xls");

		inputReader = new TestCaseInputReader(System.getProperty("user.dir")+ "/externalFiles/xls/" + "Suite.xls");

		/**
		 * Added by Pankaj Dogra Modified on 23rd July 2014 This code will check
		 * whether all the config entries regarding module name should be
		 * present or not in config.properties and also check whether all the
		 * config entries are correct or not.An alert will be open;if you have
		 * missed any entry and if any entry has given wrong name.Application
		 * log will provide proper details for missing and Wrong entries in
		 * config.properties file.
		 * */

		ArrayList<String> missedModuleList = new ArrayList<String>();
		ArrayList<String> missedFolderList = new ArrayList<String>();
		ArrayList<String> wrongModuleList = new ArrayList<String>();
		ArrayList<String> wrongFolderList = new ArrayList<String>();
		String finalFold = null;
		String finalFold1 = null;
		int configSize = 0, configSize1 = 0;
		StringBuffer popUp_msg1 = new StringBuffer();
		for (suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++) {
			String suite_Name = inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID,suiteID);
			String empty = "null";
			String[] split_Suite_Name = suite_Name.split("_Suite");
			testSuite = split_Suite_Name[0];
			moduleName = split_Suite_Name[0].split("_")[0];
			String filePath = System.getProperty("user.dir")+ "/externalFiles/xls/" + CONFIG.getProperty(moduleName);
			String arr[] = filePath.split("/");
			int arrSize = arr.length;
			String configName = arr[arrSize - 1];
			File fs = new File(filePath);
			if (configName.equals(empty) || configName.equals("xls")) {
				suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);
				if (suite_runmode_status.equals("Y")) {
					finalFold = appSpecificKeyEvents.getModuleName(suite_Name);
					if (!(missedModuleList.contains(moduleName) || (missedFolderList.contains(finalFold)))) {
						missedModuleList.add(moduleName);
						missedFolderList.add(finalFold);
					}
				}
			} else if (!(fs.exists())) {
				suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);
				if (suite_runmode_status.equals("Y")) {
					finalFold1 = appSpecificKeyEvents.getModuleName(suite_Name);
					if (!(wrongModuleList.contains(moduleName) || (wrongFolderList.contains(finalFold1)))) {
						wrongModuleList.add(moduleName);
						wrongFolderList.add(finalFold1);
					}
				}
			}

		}
		int j = 0, k = 0;
		for (int i = 0; i < missedModuleList.size(); i++) {
			for (; j < missedFolderList.size();) {
				if (j == 0)
					logger.warn("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Some Entries are Missing In Config.Properties Files<br>Please Fill Following Entries In Config.Properties Files....</b></FONT>");
				logger.debug("<FONT FACE=AriaL SIZE=2><b>"
						+ missedModuleList.get(i) + "="
						+ missedFolderList.get(j) + "</b></FONT>");
				j++;
				break;
			}
		}
		for (int i = 0; i < wrongModuleList.size(); i++) {
			for (; k < wrongFolderList.size();) {
				if (i == 0)
					logger.warn("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>You have Entered Wrong Entries In Config.Properties Files<br>Please Fill Correct Entries that are Shown Below....</b></FONT>");
				logger.debug("<FONT FACE=AriaL SIZE=2><b>"
						+ wrongModuleList.get(i) + "=" + wrongFolderList.get(k)
						+ "</b></FONT>");
				k++;
				break;
			}
		}
		configSize = missedFolderList.size();
		configSize1 = wrongFolderList.size();
		if ((configSize1 > 0) || (configSize > 0)) {
			popUp_msg1
			.append("Oops...! Some Config Entries are missing or Incorrect !\n Please Refer Application.html file for More Details");
			JOptionPane.showMessageDialog(null, popUp_msg1);
			Thread.currentThread().stop();
		}

		/**
		 * Added by Karan Sood on 10th March 2015
		 * This code will check whether Runmode for Following  is (Y/N) :
		 * 1) All Enteries in Suite.xls file
		 * 2) All Modules of Suites to execute
		 * 3) All Data Sheets [Added on 6th April 2015]
		 * 3) Key 'generate_report'  in config.properties file
		 * Null Columns in Suite.xls and All Sheets of executing xls [Added on 6th April 2015]
		 **/

		ArrayList<String> commonList=new ArrayList<String>();
		ArrayList<String> sheetRunmodeBlank= new ArrayList<String>();
		StringBuffer nullColumn_Msg = new StringBuffer();
		StringBuffer blankRunMode_msg= new StringBuffer();
		String lastSuiteName=null;
		int countN=1; 		

		for (suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++)
		{        	            
			inputReader.getAllDataColumnName(Constants.TEST_SUITE_SHEET); 

			if(TestCaseInputReader.nullColumnSheetName.size()>0)													// Check Null Columns in Suite.xls
			{
				JOptionPane.showMessageDialog(null, "Delete Null Column in Suite.xls");
				Thread.currentThread().stop();
			}
			suite_runmode_status=inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, suiteID);		
			if(!(suite_runmode_status.equals(Constants.RUNMODE_YES) || suite_runmode_status.equals (Constants.RUNMODE_NO)))
			{
				JOptionPane.showMessageDialog(null, "Specify Runmode (Y/N) for All Entries in Suite.xls");			// Check Blank Runmode in Suite.xls
				Thread.currentThread().stop();
			} 

			String suite_Name = inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID,suiteID);
			String[] split_Suite_Name = suite_Name.split("_Suite");
			testSuite = split_Suite_Name[0];
			moduleName = split_Suite_Name[0].split("_")[0];
			String pathxls=inputReader.getXlSPath(System.getProperty("user.dir")+"/externalFiles/xls/"+CONFIG.getProperty(moduleName), testSuite+"_Suite.xls");

			appSpecificKeyEvents.isFileOpen(pathxls); //Added by Pankaj Sharma This verifies that the particular Executing xls file is Open or Not.

			testInputReader=new TestCaseInputReader(pathxls);

			suiteRunmodeCheck:
				if(suite_runmode_status.equals(Constants.RUNMODE_YES))
				{                      	
					int numSpreadSheets=testInputReader.getSpreadSheetsCount(suite_Name);		

					for(int i=0;i<=numSpreadSheets-1;i++)
					{
						String spreadSheetName=testInputReader.getSheetNameAtIndex(i);
						testInputReader.getAllDataColumnName(spreadSheetName);					
					}

					if(TestCaseInputReader.nullColumnSheetName.size()>0)								// Check Null Columns in Executing Suite
					{  
						commonList.add(" >>> In Following Sub-Sheets of Suite : \"" +suite_Name+"\"");
						for(int i=0;i<TestCaseInputReader.nullColumnSheetName.size();i++)
						{
							commonList.add(TestCaseInputReader.nullColumnSheetName.get(i));
						}
						TestCaseInputReader.nullColumnSheetName.clear();
						break suiteRunmodeCheck;							
					}

					for(int i=0;i<=numSpreadSheets-1;i++)
					{
						String spreadSheetName=testInputReader.getSheetNameAtIndex(i);
						if(!(spreadSheetName.equals("Test Steps")))
						{

							for (testDataInputIDs = 2; testDataInputIDs <= testInputReader.getRowCount(spreadSheetName); testDataInputIDs++) 
							{
								String dataSheetRunmodeStatus=testInputReader.getCellData(spreadSheetName, Constants.RUNMODE, testDataInputIDs);
								if(!(dataSheetRunmodeStatus.equals(Constants.RUNMODE_YES) || dataSheetRunmodeStatus.equals (Constants.RUNMODE_NO))) 
								{
									if(!suite_Name.equals(lastSuiteName))
									{
										sheetRunmodeBlank.add(" >>> In Following Sub-Sheets of Suite : \""+ suite_Name+"\"");				
									}
									lastSuiteName=suite_Name;
									sheetRunmodeBlank.add(spreadSheetName);					// Check Blank Runmode in All Sub Sheets
									break;
								}
							}
						}						
					}				
				}           
			if(suite_runmode_status.equals(Constants.RUNMODE_NO)) 
			{
				countN = countN+1;													
			}           
		}

		if(commonList.size()>0)
		{
			for(String abc:commonList)
			{
				nullColumn_Msg.append(System.getProperty("line.separator"));
				nullColumn_Msg.append(abc);
			}
			JOptionPane.showMessageDialog(null, "Delete Null Columns !! " + nullColumn_Msg );
			Thread.currentThread().stop();
		}

		if(sheetRunmodeBlank.size()>0)
		{
			for(String abc:sheetRunmodeBlank)
			{
				blankRunMode_msg.append(System.getProperty("line.separator"));
				blankRunMode_msg.append(abc);             
			}            
			JOptionPane.showMessageDialog(null, "Specify Runmode (Y/N) for all Iterations !! " + blankRunMode_msg);
			Thread.currentThread().stop();
		}

		if(!(CONFIG.getProperty("generate_report").equals(Constants.RUNMODE_YES) || CONFIG.getProperty("generate_report").equals(Constants.RUNMODE_NO)))
		{
			JOptionPane.showMessageDialog(null, "Specify  Runmode  (Y/N)  for  key  \"generate_report\"  in  config.properties  file");
			Thread.currentThread().stop();
		}
		else if (countN == inputReader.getRowCount(Constants.TEST_SUITE_SHEET) && (CONFIG.getProperty("generate_report").equals(Constants.RUNMODE_YES)))
		{
			buildNum="N.A.";       //Set Value N.A. to buildNum if in Suite.xls Runmode Status for All Suites is N.
		}

		/**----------------------------------Code Start For Validation Checked---------------------------------------------
		/*-Here it Will Check the  Existence Of All SubSheets Under Suite; Objects and Keywords Used in Suite,Unused Column Under Suite,
		/*-Existence of Duplicate Entries Under Common OR File and Specific OR Files and Tab Specific OR File                    
		 *
		 * Modified By Pankaj Dogra on 20/02/2015
		 * Remove Unused line and Merge Code for All Validations.
		 */
		StringBuffer pop_msg=new StringBuffer();
		String xpath;
		int counter=0;
		String methodName;
		ArrayList<String> xls_sheet=new ArrayList<String>();   
		String arrtestCaseName;
		ArrayList<String> xls_sheet_exist=new ArrayList<String>();
		int suite_count=inputReader.getRowCount(Constants.TEST_SUITE_SHEET);
		ArrayList<String> xPathsNotFound=new ArrayList<String>();
		ArrayList<String> methodNotFound=new ArrayList<String>();
		ArrayList<String> allValInDataSheet;
		StringBuffer missingDataSet=new StringBuffer();
		StringBuffer extraDataSet=new StringBuffer();
		ArrayList<String> resultData=new ArrayList<String>();
		ArrayList<String> resultDataDataSheet=new ArrayList<String>();
		ArrayList<String> suite_with_duplicate_entries=null;
		suite_with_duplicate_entries = new ArrayList<String>();
		int countNo=1; // Added By Karan
		for( int suiteID=2;suiteID<=suite_count;suiteID++)
		{
			String suite_Name=inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID, suiteID);
			String [] split_Suite_Name=suite_Name.split("_Suite");
			testSuite=split_Suite_Name[0] ;
			String xls_suite=suite_Name.concat(".xls");
			String oRname=split_Suite_Name[0].concat("_OR.properties");
			moduleName=split_Suite_Name[0].split("_")[0];
			StringBuffer SpecificFile=new StringBuffer();
			StringBuffer Specific_Method=new StringBuffer();

			suite_runmode_status=inputReader.getCellData("Test Suite", "Runmode", suiteID);	

			int sr_no=1,num=1;

			if(suite_runmode_status.equals("Y"))
			{
				/** Modified By Mayank Saini 06/05/2014
				code for getting specific OR path and passing it to arguments of isDuplicateEntry*/
				String pathxls=inputReader.getXlSPath(System.getProperty("user.dir")+"/externalFiles/xls/"+CONFIG.getProperty(moduleName), testSuite+"_Suite.xls");
				inputReader.getXlSPath(System.getProperty("user.dir")+"/externalFiles/xls/"+CONFIG.getProperty(moduleName), testSuite+"_Suite.xls");
				String path=inputReader.getORPath();
				String appSpecificOrPath = System.getProperty("user.dir")+ "/externalFiles/Object_Repository/"+ CONFIG.getProperty(moduleName) + File.separator+ moduleName + "_Common_OR.properties";
				String commonOrPath=System.getProperty("user.dir")+ "/externalFiles/Object_Repository/Common"+ "_OR.properties";
				/** 
				 *  Added by Kritika
				 *  Modified on 11th June'2014
				 *  This code will check whether the Sub sheet for modules in Test Case Sheet Exists,if not exists then
				 *  it will generate a pop up that Add Sub sheets first
				 * **/
				xls_sheet_exist.clear();
				xls_sheet.clear();
				testInputReader=new TestCaseInputReader(pathxls);
				for(testCaseID=2;testCaseID<=testInputReader.getRowCount("Test Cases");testCaseID++){
					arrtestCaseName=testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, testCaseID);
					if(!arrtestCaseName.equals("")){  //Ignores the empty lines and add all the modules in array	
						if(testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, testCaseID).equals(Constants.RUNMODE_YES)|| testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, testCaseID).equals(Constants.RUNMODE_NO))
						{
							xls_sheet.add(arrtestCaseName);
						}
					}
				}
				for(int sheetsize=0;sheetsize<=xls_sheet.size()-1;sheetsize++){
					if(!testInputReader.isSheetExist(xls_sheet.get(sheetsize))){   //Verifies all the sheets present and if not then add them to array

						String testcasesubsheet=xls_sheet.get(sheetsize);
						xls_sheet_exist.add(testcasesubsheet);
						flag=true;
					}	
				}
				if(xls_sheet_exist.size()>=1){
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Following Sub Sheets are not Present Under :-"+suite_Name + ":--</b></FONT>\n");  //Prints the suite name	
				}
				for(int i=0;i<xls_sheet_exist.size();i++){
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+(i+1)+". "+xls_sheet_exist.get(i)+"</b></FONT>");

				}

				/**
				 * @date 23rd July 2014
				 * @author Sanjay Sharma
				 * Verifies whether XLS Objects(Xpaths) are present in their corresponding OR Files 
				 * Verifies whether All Used Keyword in XLS file Either Present in any Java File or not.
				 */
				xPathsNotFound.clear();
				methodNotFound.clear();

				inputReaderXPath = new TestCaseInputReader(pathxls);
				Set<String> xPaths=inputReaderXPath.getAllXPathsFromXls(Constants.TEST_STEPS_SHEET,Constants.OBJECT,suite_Name,suite_count,suiteID);//getting all xpaths from xls sheet
				Iterator<String> ite=xPaths.iterator();
				List<String> allXpathsOfOR=appSpecificKeyEvents.getXPathsFromOR(path);//getting all xpaths from OR.properties Files
				while(ite.hasNext()){
					xpath=ite.next();
					if(!allXpathsOfOR.contains(xpath.trim()))
						xPathsNotFound.add(xpath);//Add name of Objects to ArrayList , which are not present in OR.properties file
				}
				Set<String> allKeywords=inputReaderXPath.getAllKeywordsFromXls(Constants.TEST_STEPS_SHEET,Constants.KEYWORD,suite_Name,suite_count,suiteID);//getting all Keywords from Xls Sheet
				Iterator<String> ite_method=allKeywords.iterator();
				ArrayList<String> allMethodsOfFile=appSpecificKeyEvents.getAllMethodsFromFile(moduleName);//getting all files method names
				while(ite_method.hasNext()){
					methodName=ite_method.next();
					if(!allMethodsOfFile.contains(methodName.trim()))
						methodNotFound.add(methodName);//Add name of methods to ArrayList , which are not  present in KeyWordevents
				}
				SpecificFile.setLength(0);
				Specific_Method.setLength(0);
				numberOfXpathsNotFound=xPathsNotFound.size();
				if ( numberOfXpathsNotFound> 0){
					xpathNotFoundCounter++;
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Object(Xpaths) not found inside  :-" + oRname + " :--</b></FONT>");
					for(String keys1 : xPathsNotFound){
						SpecificFile.append("<b>" + sr_no + ".  " + keys1 + "</b>");
						SpecificFile.append("<br>");
						sr_no++;
					}
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+SpecificFile+"</b></FONT>");
				}
				numberOfMethodsNotFound=methodNotFound.size();
				if ( numberOfMethodsNotFound> 0) {
					methodNotFoundCounter++;
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Keywords(Methods) not found in Any Java File For " + xls_suite + " :- </b></FONT>");
					for (String keys2 : methodNotFound){
						Specific_Method.append("<b>" + num + ".  " + keys2 + "</b>");
						Specific_Method.append("<br>");
						num++;
					}
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+Specific_Method+"</b></FONT>");
				}
				/**
				 * Modified By Pankaj Dogra on 20/02/2015
				 * Added argument to check duplicacy in Tab Common OR File
				 */
				duplicacy = appSpecificKeyEvents.isDuplicateEntry(path,appSpecificOrPath,commonOrPath);
				if(duplicacy!=null){
					suite_with_duplicate_entries.add("1");
				}

				/** 
				 * Added by Karan Sood on 3rd July 2014
				 * This code will check whether All Data Columns Used in TestSteps Sheet are Present 
				 * in their Respective Data Sheets for Each Module.
				 * Also,It will Tell us about the UnUsed Columns Present in the DataSheets.
				 **/
				resultData.clear();
				resultDataDataSheet.clear();
				missingDataSet.setLength(0);
				extraDataSet.setLength(0);

				for (int moduleID = 2; moduleID <= testInputReader.getRowCount(Constants.TEST_CASES_SHEET); moduleID++){ // Read TestCases Sheet
					String moduleName = testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, moduleID);
					HashSet<String> hs = new HashSet<String>();							
					String moduleRunmodeStatus = testInputReader.getCellData(Constants.TEST_CASES_SHEET, "Runmode", moduleID);
					if (moduleRunmodeStatus.equals(Constants.RUNMODE_YES)){   // Check For Runmode in TestCases Sheet
						for (int testtStepID = 2; testtStepID <= testInputReader.getRowCount(Constants.TEST_STEPS_SHEET); testtStepID++){
							if (moduleName.equals(testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, testtStepID))){
								data = testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.DATA, testtStepID);
								if (data.startsWith(Constants.DATA_START_COL)){
									if(data.contains("|")){
										data = data.split(Constants.DATA_SPLIT)[1];
										hs.add(data);
									}
								}
							}
						}
						Iterator<String> ite_col=hs.iterator();
						allValInDataSheet=testInputReader.getAllDataColumnName(moduleName);

						String lastname=null;										
						for(int x=0;x<hs.size();x++){
							String tem=ite_col.next();
							if(!(allValInDataSheet.contains(tem))){  //Checking whether data sheet contains columns present in testSteps sheet
								if(!moduleName.equals(lastname)){
									resultData.add("<b>For module </b>\"<FONT FACE=AriaL SIZE=2 COLOR=RED>"+ moduleName +"</FONT>\"<b> are :- </b>");
								}
								lastname=moduleName;	
								resultData.add(tem);
								counter++;
							}
						}
						lastname=null;
						for(int y=0;y<allValInDataSheet.size();y++){
							String s=allValInDataSheet.get(y);

							if(s.equals(Constants.RUNMODE)||s.equals(Constants.RESULT))
								continue;
							if(!(hs.contains(s))){  //Checking whether testSteps Sheet Contains Columns Present in Data Sheet
								if(!moduleName.equals(lastname))
								{
									resultDataDataSheet.add("<b>For module </b>\"<FONT FACE=AriaL SIZE=2 COLOR=RED>"+ moduleName +"</FONT>\"<b> are :- </b>");
								}
								lastname=moduleName;
								resultDataDataSheet.add(s);
								counter++;
							}
						}
					} //end of TestCases sheet Runmode Loop

					if(moduleRunmodeStatus.equals(Constants.RUNMODE_NO)) // Added By Karan on 20/04/2015
					{
						countNo = countNo+1;
					}
				}// end of TestCases Sheet for loop
			}// end of suite.xls Runmode Loop
			if(resultData.size()>0){
				missingDataSet.append("<FONT FACE=AriaL SIZE=2 COLOR=RED>Missing Columns in DataSheet for sheet :--"+suite_Name +"-- are as follows :- </FONT>");
				missingDataSet.append("<Br>");
				for(String abc:resultData){
					missingDataSet.append("<Br>");
					missingDataSet.append(abc);
					missingDataSet.append("<Br>");	
				}
				missingDataSet.append("<Br>");
				missingDataSet.append("<FONT FACE=AriaL SIZE=2> !! Add above entries to respective Data Sheets </font>");
				missingDataSet.append("<Br>");
				logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+missingDataSet+" </b></font>");
			}	
			if(resultDataDataSheet.size()>0){
				extraDataSet.append("<FONT FACE=AriaL SIZE=2 COLOR=RED>Unused Column in DataSheet for sheet :-- "+suite_Name +"-- are as follows :- </FONT>");
				extraDataSet.append("<Br>");

				for(String abc:resultDataDataSheet){ 
					extraDataSet.append("<Br>");
					extraDataSet.append(abc);
					extraDataSet.append("<Br>");
				}
				extraDataSet.append("<Br>");
				extraDataSet.append("<FONT FACE=AriaL SIZE=2> !! Either use above column names in test steps sheet OR delete them from DataSheets .</FONT>");
				extraDataSet.append("<Br>");
				logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+extraDataSet+" </b></font>");
			}
		}
		if (countNo == testInputReader.getRowCount(Constants.TEST_CASES_SHEET) && (CONFIG.getProperty("generate_report").equals(Constants.RUNMODE_YES)))
		{
			buildNum="N.A.";    // Added By Karan on 20/04/2015, Set Value N.A.  to buildNum if in Test Cases Sheet Runmode Status for All Modules is N.
		}	

		if((suite_with_duplicate_entries.size()>0)||(xpathNotFoundCounter!=0) || (methodNotFoundCounter>0) || (flag)|| (resultData.size()>0) ||(resultDataDataSheet.size()>0) ||(counter>0)){
			pop_msg.append("    Oops...!..Some Error has Occured.\nPlease Refer Application.html file for More Details");
			JOptionPane.showMessageDialog(null,pop_msg);
			Thread.currentThread().stop();
		}
		/*-------------------------------------------Code End Here For Validations----------------------------------------------------------*/

		suiteTime = new long[inputReader.getRowCount(Constants.TEST_SUITE_SHEET)];// Added by Sumit initialize array
		dashboard = new DashBoard();   //Added by vikas to start the dashboard timer.
		dashboard.startTotalTime();

		/**
		 * Added code for Dashboard by Vikas Bhadwal on 04/02/2015
		 * This code identify total number
		 * of lines whose corresponding module's Runmode is 'Y'.
		 **/

		suite_lines = new int[inputReader.getRowCount(Constants.TEST_SUITE_SHEET)];
		// to get total Number of Suites with Runmode 'Y'
		for (suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++){
			suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);
			if (suite_runmode_status.equals("Y")) {
				TotalSuiteCount++;
			}
		}
		dashboard.setSuiteCount(TotalSuiteCount);
		dashboard.setRemaingModuleCount(TotalSuiteCount);
		String module = CONFIG.getProperty(moduleName); //to get and set module name.
		dashboard.SetModule(module);

		for (suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++) {
			total_lines_of_code_m = 0;
			suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);
			if (suite_runmode_status.equals("Y")){
				String suite_name = inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID,suiteID);
				suite_runmode_status =inputReader.getCellData("Test Suite","Runmode", suiteID);
				String[] split_Suite_name = suite_name.split("_Suite");
				test_Suite = split_Suite_name[0];
				module_Name = split_Suite_name[0].split("_")[0];
				String path_new = inputReader.getXlSPath(System.getProperty("user.dir") + "/externalFiles/xls/"+ CONFIG.getProperty(module_Name), test_Suite+ "_Suite.xls");
				testInputReader = new TestCaseInputReader(path_new);
				for (testCaseID = 2; testCaseID <= testInputReader.getRowCount("Test Cases"); testCaseID++) {
					testCaseName = testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,testCaseID);

					//Added by vikas to get total number of lines to be executed.
					if (testInputReader.getCellData(Constants.TEST_CASES_SHEET,Constants.RUNMODE, testCaseID).equals(Constants.RUNMODE_YES))
					{
						iterations = 0;
						currentModuleLineCount = 0;						
						for (testsStepID = 2; testsStepID <= testInputReader.getRowCount(Constants.TEST_STEPS_SHEET); testsStepID++){ 
							if (testCaseName.equals(testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, testsStepID))) {
								currentModuleLineCount++;
							}
						}
						for (testDataInputIDs = 2; testDataInputIDs <= testInputReader.getRowCount(testCaseName); testDataInputIDs++) {
							if(testInputReader.getCellData(testCaseName, Constants.RUNMODE, testDataInputIDs).equals(Constants.RUNMODE_YES)) {
								iterations++;
							}
						}
						total_lines_of_code_m += currentModuleLineCount * iterations;
					}
				}
				suite_lines[suiteID - 1] = total_lines_of_code_m;
			}
		}			


		for (int suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++){
			total_lines_of_code += suite_lines[suiteID - 1];
		}
		
		for (suiteID = 2; suiteID <= inputReader.getRowCount(Constants.TEST_SUITE_SHEET); suiteID++){
			long startTime = System.currentTimeMillis();// Added by Sumit on 04/03/2014 to get start time of execution
			dashboard.click(); // Added by vikas to start the timer for current suite.
			current_module_reaming_line = suite_lines[suiteID - 1];
			int static_current_suite_line=current_module_reaming_line;//added by vikas to get line of code in current suite.
			dashboard.setLineOfCodeCurrentSuite(current_module_reaming_line);

			//Added by Surender on 14/10/2015 for displaying progress bar.
	                       current_suite_total_lines=current_module_reaming_line;
		                      pvalue=((float)(current_suite_total_lines)/100);
						      progressBar.setValue(0);
						      pcount=1;
						      progressBar.setToolTipText("In Progress");
		           

			if (!alreadyCreatedInstanceList.isEmpty()
					|| (!alradyCreatedObjectRefList.isEmpty())|| (!util_list.isEmpty())
					|| (!specific_Method_List.isEmpty())|| (!appSpecificMethodList.isEmpty())) {
				alreadyCreatedInstanceList.clear();
				alradyCreatedObjectRefList.clear();
				util_list.clear();

				specific_Method_List.clear();
				appSpecificMethodList.clear();
			}

			String suite_Name = inputReader.getCellData(Constants.TEST_SUITE_SHEET, Constants.Test_Suite_ID,suiteID);
			String[] split_Suite_Name = suite_Name.split("_Suite");
			testSuite = split_Suite_Name[0];
			// Modified by Sandeep Dhamija. 9 July, 2013
			moduleName = split_Suite_Name[0].split("_")[0];

			//Added by Nitin Gupta on 19/03/2015 to set executing testSuite names in a set
			folderInitials.add(suite_Name);

			// Added by Vikas Bhadwal to set suite name. on 04/03/2015
			suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);
			if (suite_runmode_status.equals("Y")) {
				dashboard.setName(suite_Name,static_current_suite_line);
				failCount = 0;
				dashboard.setfail(failCount);
			}

			// Modified by Pallavi Singla. 13 December,2013
			suite_runmode_status = inputReader.getCellData("Test Suite","Runmode", suiteID);

			/*
			 * Modified By Mayank Saini 06/05/2014 code for getting xls path and
			 * passing it to constructor Of TestCaseInputReader class
			 */
			String path = inputReader.getXlSPath(System.getProperty("user.dir")+ "/externalFiles/xls/"
					+ CONFIG.getProperty(moduleName),testSuite + "_Suite.xls");
			if (path == null) {
				throw new FileNotFoundException();
			}
			testInputReader = new TestCaseInputReader(path);
			testInputReader.deleteResultColumns(Constants.TEST_STEPS_SHEET,Constants.RESULT);
			if (inputReader.getCellData(Constants.TEST_SUITE_SHEET,Constants.RUNMODE, suiteID).equals(Constants.RUNMODE_YES)) {

				// execute the test cases in the suite
				if (suite_runmode_status.equals("Y")){

					/* Modified by Nitin Gupta on  25/07/2014 ReInitialize the log4j
					 * again as the suite starts executing and suite is ready to run
					 * and also displays all the module run mode status of
					 * corresponding suite
					 */

					if (suite_runmode_status.equals("Y")){                              

						if(suiteID>2)
						{
							prop.setProperty("log4j.appender.dest1.Append","true");
							prop.store(new FileOutputStream(mylog4jpropertyPath), "Creating Mylog4j.properties");
							prop.load(new FileInputStream(mylog4jpropertyPath));
							PropertyConfigurator.configure(prop);
							testStepID=0;
							keyword=null;
							object=null;
							logger = Logger.getLogger("testenvLogger");
						}
						else
						{
							prop.load(new FileInputStream(log4jpropertyPath));
							PropertyConfigurator.configure(prop);
							logger = Logger.getLogger("testenvLogger");
						}



						TotalSuiteCount--; // added by Vikas Bhadwal to set remaining Suite Count.
						dashboard.setRemaingModuleCount(TotalSuiteCount);
					}

					logger.debug("******Executing the Suite******"+ inputReader.getCellData(Constants.TEST_SUITE_SHEET,Constants.Test_Suite_ID, suiteID));


					//Added By Nitin Gupta on 19/03/2015 to Print Sheet name in logger 
					logger.debug(inputReader.getCellData(Constants.TEST_SUITE_SHEET,Constants.Test_Suite_ID, suiteID)
							+ " -- "+ inputReader.getCellData("Test Suite", "Runmode", suiteID));
					// test suite name = test suite xls file having test cases

				}

				/* code begins for loading Common_OR property file */

				fs = new FileInputStream(System.getProperty("user.dir")
						+ "/externalFiles/Object_Repository/Common"
						+ "_OR.properties");
				OR = new Properties();
				OR.load(fs);

				/* code ends for loading Common_OR property file */

				// Added by Vikas Bhadwal on 06/10/2014
				/* code for loading Common_OR property file for Specific Tab */
				fs = new FileInputStream(System.getProperty("user.dir")+ "/externalFiles/Object_Repository/"
						+ CONFIG.getProperty(moduleName) + File.separator+ moduleName + "_Common_OR.properties");
				tab_OR = new Properties();
				tab_OR.load(fs);

				/*
				 * code ends for loading Common_OR property file for Specific
				 * Tab
				 */

				/*
				 * Modified By Mayank Saini 06/05/2014 code for loading specific
				 * OR file
				 */
				String orPath = testInputReader.getORPath();

				if (orPath == null) {
					throw new FileNotFoundException();
				}
				fs = new FileInputStream(orPath);
				OR_specific = new Properties();
				OR_specific.load(fs);
				OR.putAll(tab_OR);
				OR.putAll(OR_specific);

				/* code ends for loading specific OR property file */

				/**
				 * Added by Pankaj Sharma. 23 Dec, 2013 This code identify that
				 * which package should be Use. And which class file of that
				 * package should be use.
				 **/
				Package[] pac = Package.getPackages();
				ArrayList<String> paclist = new ArrayList<String>();
				String normalPacPath = null;
				for (int i = 0; i < pac.length; i++) {
					if (pac[i].getName().endsWith("base")&& pac[i].getName().startsWith("com.tk20")) {
						paclist.add(pac[i].getName());
						normalPacPath = pac[i].getName();
						break;
					}
				}
				normalPacPath = normalPacPath.substring(0,normalPacPath.length() - 4);
				normalPacPath = normalPacPath.concat("test");
				normalPacPath = normalPacPath.replace(".", "/");
				File file1 = new File(System.getProperty("user.dir") + "/src/"+ normalPacPath);
				normalPacPath = normalPacPath.replace("/", ".");
				String[] directories = file1.list(new FilenameFilter() {

					public boolean accept(File dir, String name) {
						return new File(dir, name).isDirectory();
					}
				});
				String name = CONFIG.getProperty(moduleName).toLowerCase();
				for (String dir : directories) {
					name = name.replaceAll("_", "");
					if (dir.equalsIgnoreCase(name)) {
						normalPacPath = normalPacPath.concat(".");
						normalPacPath = normalPacPath.concat(dir);
						break;
					}
				}
				normalPackagePath = normalPacPath.replace(".", "/");
				method = (Method[]) ArrayUtils.addAll(util_method,appSpecificUtilMethod);

				String abosolutePackagePath = System.getProperty("user.dir")+ "/src/" + normalPackagePath;

				File f = new File(abosolutePackagePath);
				files = f.listFiles();
				Method[] singleClassSpecificMethods;
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().endsWith(".java")) {
						String className = normalPackagePath+ "/"+ files[i].getName().substring(0,files[i].getName().length() - 5);
						className = className.replace("/", ".");
						singleClassSpecificMethods = Class.forName(className).getMethods();
						allClassSpecificsMethods = ArrayUtils.addAll(allClassSpecificsMethods,singleClassSpecificMethods);
					}
				}
				method = (Method[]) ArrayUtils.addAll(method,allClassSpecificsMethods);

				/**
				 * Added by Tarun holds application specific common method
				 * names.
				 **/
				for (int i = 0; i < appSpecificUtilMethod.length; i++) {
					appSpecificMethodList.add(appSpecificUtilMethod[i].getName());
				}

				for (int i = 0; i < util_method.length; i++) {
					util_list.add(util_method[i].getName());
				}
				for (int i = 0; i < allClassSpecificsMethods.length; i++) {
					specific_Method_List.add(allClassSpecificsMethods[i].getName());
				}

				for (testCaseID = 2; testCaseID <= testInputReader.getRowCount("Test Cases"); testCaseID++) {
					logger.debug(testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,testCaseID)+ " -- "+ testInputReader.getCellData("Test Cases","Runmode", testCaseID));
					testCaseName = testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID,testCaseID);

					if (testInputReader.getCellData(Constants.TEST_CASES_SHEET,Constants.RUNMODE, testCaseID).equals(Constants.RUNMODE_YES)) {
						logger.debug("Executing the test case -> "+ testCaseName);
						dashboard.setTestCaseName(testCaseName);// vikas: to set current module Name
						if (testInputReader.isSheetExist(testCaseName)) {
							// RUN as many times as number of test data sets
							// with runmode Y
							for (testDataInputID = 2; testDataInputID <= testInputReader.getRowCount(testCaseName); testDataInputID++) {
								resultSet = new ArrayList<String>();
								logger.debug("Iteration number "+ (testDataInputID - 1));
								// checking the runmode for the current data set
								if (testInputReader.getCellData(testCaseName,Constants.RUNMODE, testDataInputID).equals(Constants.RUNMODE_YES)) {
									// iterating through all keywords
									executeKeywords(); // multiple sets of data
								}
								generateXLSReport();

							}
						} else {
							// iterating through all keywords
							resultSet = new ArrayList<String>();
							executeKeywords();// no data with the test
							generateXLSReport();

						}
						if (suite_runmode_status.equals("Y"))
							appSpecificKeyEvents.generateModuleStatus(testCaseName, testInputReader, testCaseID);
					}
					// 1)skip the test script if the runmode of test suite and
					// test case is N.
					// 2)skip the test script if the runmode is test suite is NO
					// and runmode of testcase is YES
					else if (testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE,testCaseID).equals(Constants.RUNMODE_NO)) {

						if (testInputReader.isSheetExist(testCaseName)) {
							// RUN as many times as number of test data sets
							// with runmode N
							for (testDataInputID = 2; testDataInputID <= testInputReader.getRowCount(testCaseName); testDataInputID++) {
								resultSet = new ArrayList<String>();
								logger.debug("Iteration number "+ (testDataInputID - 1));

								if (testInputReader.getCellData(testCaseName,Constants.RUNMODE, testDataInputID).equals(Constants.RUNMODE_NO)) {

									// iterating through all keywords
									generateXLSReport();

								}
								generateXLSReport();

							}
						}
						if (suite_runmode_status.equals("Y"))
							appSpecificKeyEvents.generateModuleStatus(testCaseName, testInputReader, testCaseID);
					}

				}
				long endTime = System.currentTimeMillis();// modified by Sumit on 04/03/2014 to get endTime of execution
				suiteTime[suiteID - 1] = ((endTime - startTime) / 1000) / 60;// modified by Sumit on 04/03/2014 to store
				//  execution time of per suite
			}
			dashboard.stop();// added by Vikas to stop timer for Current Suite.
		}}


	public void executeKeywords() throws IllegalAccessException,IllegalArgumentException, InvocationTargetException,NoSuchMethodException, SecurityException {
		// iterating through all keywords
		for (testStepID = 2; testStepID <= testInputReader.getRowCount(Constants.TEST_STEPS_SHEET); testStepID++) {
			// checking TCID
			if (testCaseName.equals(testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, testStepID))) {
				boolean outerLoopFlag = false;
				data = testInputReader.getCellData(Constants.TEST_STEPS_SHEET,Constants.DATA, testStepID);
				if (data.startsWith(Constants.DATA_START_COL)) {
					// read actual data value from the corresponding column
					data = testInputReader.getCellData(testCaseName,data.split(Constants.DATA_SPLIT)[1],testDataInputID);
				} else if (data.startsWith(Constants.CONFIG)) {
					// read actual data value from config.properties
					data = CONFIG.getProperty(data.split(Constants.DATA_SPLIT)[1]);
				} else {
					// by default read actual data value from or.properties
					data = OR.getProperty(data);
				}
				object = testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.OBJECT,testStepID);
				keyword = testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.KEYWORD,testStepID);
				logger.debug(keyword);
				description = testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.DESCRIPTION,testStepID);
				dashboard.setCurrentLine(description,testStepID);//added by vikas to set current line executing 
				// modified by Sandeep Dhamija. Send row number of method to logger
				// modified by Puneet. Send Description of method to logger
				logger.debug(keyword + "-" + testStepID + ": " + description);

				for (x = 0; x < method.length; x++) {

					if (outerLoopFlag) {
						break;
					}
					if (checkAccessibilityError.getName().equals(keyword)) {    // Accessibility Code
						keyword_execution_result = (String) checkAccessibilityError.invoke(accessibilityKeyEvents, "body", "");
						break;
					}

					if (method[x].getName().equals(keyword)) {
						try {
							if (util_list.contains(keyword)) {
								keyword_execution_result = (String) method[x].invoke(utilKeyEvents, object, data);
								break;
							} else if (appSpecificMethodList.contains(keyword)) {
								keyword_execution_result = (String) method[x].invoke(appSpecificKeyEvents, object,data);
								break;
							}

							/**
							 * Added by Pankaj Sharma. 23 Dec, 2013 This code
							 * identify that which Class's method should be
							 * call.
							 **/
							else if (specific_Method_List.contains(keyword)) {
								for (int i = 0; i < files.length; i++) {
									specificClassMethodList.clear();
									if (files[i].getName().endsWith(".java")) {
										String className = normalPackagePath+ "/"+ files[i].getName().substring(0,files[i].getName().length() - 5);
										className = className.replace("/", ".");

										currentInstance = Class.forName(className).newInstance();
										String alredyCreatedInstance = (String) currentInstance.toString();
										alredyCreatedInstance = alredyCreatedInstance.substring(0,alredyCreatedInstance.indexOf("@"));
										for (int k = 0; k < alreadyCreatedInstanceList.size(); k++) {
											if (alredyCreatedInstance.equals(alreadyCreatedInstanceList.get(k))) {
												currentInstance = alradyCreatedObjectRefList.get(k);
												break;
											}
										}
										if (!alreadyCreatedInstanceList.contains(alredyCreatedInstance)) {
											alreadyCreatedInstanceList.add(alredyCreatedInstance);
											alradyCreatedObjectRefList.add(currentInstance);
										}
										particularClassMethods = currentInstance.getClass().getMethods();

										for (int j = 0; j < particularClassMethods.length; j++) {
											specificClassMethodList.add(particularClassMethods[j].getName());
										}
										if (specificClassMethodList.contains(keyword)) {
											keyword_execution_result = (String) method[x].invoke(currentInstance,object, data);
											outerLoopFlag = true;
											break;
										}
									}
								}
							} else {
								keyword_execution_result = "Method name did not match";
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
							keyword_execution_result = Constants.KEYWORD_FAIL+ " Some exception in executing the Method \n"+ e.toString();
							outerLoopFlag = true;
						}
					}
				}
				
				if(keyword.equals("validateDisplayUserName"))	{										   // Added By Karan Sood - 3rd June 2015
					if(keyword_execution_result.contains("java.lang.reflect.InvocationTargetException"))   // Code to Generate Pop-up, When Login with Wrong User
					{
					JOptionPane.showMessageDialog(null, "You Login With Wrong User");
	            	Thread.currentThread().stop();
					}
					
					buildNum=keyword_execution_result.split(Constants.DATA_SPLIT)[1];						// Added By Karan Sood - 27th July 2015
				}																							// Code to Get Build Version 
				
				resultSet.add(keyword_execution_result);
				capturescreenShot_method.invoke(utilKeyEvents, testSuite + "_Suite" + "_"+ testCaseName + "_TS" + testStepID + "_"+ (testDataInputID - 1),keyword_execution_result,testStepID);  //edited by Vikas Bhadwal added fourth argument.
				logger.debug(keyword_execution_result);               

				// added by vikas Bhadwal to increment fail count and set to dashboard on 04/03/2015
				if (keyword_execution_result.contains("Fail")|| keyword_execution_result.contains("FAIL")) {
					failCount++;
					dashboard.setfail(failCount);                   
				}   

				// added by vikas Bhadwal to set remaining line of code to dashboard.
				total_lines_of_code--;
				current_module_reaming_line--;
			    dashboard.setLineOfCodeCurrentSuite(current_module_reaming_line);
				
			  //Added by Surender on 14/10/2015 for displaying progress bar.
					        lines_executed=current_suite_total_lines-current_module_reaming_line;
					          if(lines_executed>=(int)(pvalue*pcount))
				           {
				               int roundOffValue=Math.round((float)lines_executed/pvalue);
					           progressBar.setValue(roundOffValue);
				               pcount++;
				            
			              if( progressBar.getValue()==100 || current_module_reaming_line==0)
			               {
				                   progressBar.setToolTipText("Completed");
			                  
				                   progressBar.setValue(100);
				               }
				           }
				        
				keyword_execution_result = "Method not found";
			}
		}
	}


	public void generateXLSReport() {

		String colName = Constants.RESULT + (testDataInputID - 1);
		boolean isColExist = false;
		// int c;
		for (int c = 0; c < testInputReader.getColumnCount(Constants.TEST_STEPS_SHEET); c++) {
			if (testInputReader.getCellData(Constants.TEST_STEPS_SHEET, c, 1).equals(colName)) {
				isColExist = true;
				break;
			}
		}
		if (!isColExist)
			testInputReader.addColumn(Constants.TEST_STEPS_SHEET, colName);

		int index = 0;
		for (int i = 2; i <= testInputReader.getRowCount(Constants.TEST_STEPS_SHEET); i++) {
			if (testCaseName.equals(testInputReader.getCellData(Constants.TEST_STEPS_SHEET, Constants.TCID, i))) {
				if (resultSet.size() == 0)
					testInputReader.setCellData(Constants.TEST_STEPS_SHEET,colName, i
							, Constants.KEYWORD_SKIP);
				else
					testInputReader.setCellData(Constants.TEST_STEPS_SHEET,colName, i, resultSet.get(index));
				index++;
			}

		}
		if (resultSet.size() == 0) {
			// skip
			testInputReader.setCellData(testCaseName, Constants.RESULT,testDataInputID, Constants.KEYWORD_SKIP);
			return;
		} 
		else {
			for (int i = 0; i < resultSet.size(); i++) {
				if (!resultSet.get(i).equals(Constants.KEYWORD_PASS)) {
					testInputReader.setCellData(testCaseName, Constants.RESULT,testDataInputID, resultSet.get(i));
					return;
				}
			}
		}
		testInputReader.setCellData(testCaseName, Constants.RESULT,testDataInputID, Constants.KEYWORD_PASS);
	}



}

