package com.tk20.seleniumuiflipassessment.util;
// reads the xls files and generates corresponding html reports
// Calls sendmail - mail
import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
//import org.jfree.ui.RefineryUtilities;
import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.datareader.TestCaseInputReader;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.map;//added by 	Vikas Bhadwal
public class ReportUtil
{
	public static String result_FolderName=null;
	public String currentModule;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private Workbook  workbook = null;
	private Sheet sheet = null;
	private Row row   =null;
	private Cell cell = null;
	public String current_suite_path;
	private Cell build_cell= null;
	
	String release;
	public  void generateHTMLReport(long[] totalTime, String buildVersion) throws Exception //modified by Sumit on 04/03/2014 long array is passed in method having per suite execution time
	{																						// Modified By Karan Sood on 2nd December 2014, Added another Parameter of String Type in method generateHTMLReport()
		// read suite.xls
		System.out.println("executing");
		Date d = new Date();
		String date=d.toString().replaceAll(" ", "_");
		date=date.replaceAll(":", "_");
		date=date.replaceAll("\\+", "_");
		System.out.println(date);
		result_FolderName="Reports"+"_"+date;
		//String reportsDirPath=System.getProperty("user.dir")+"\\Reports";
		new File(result_FolderName).mkdirs();
		new File(result_FolderName+"/Pie_Charts").mkdirs();//Create folders for pie chart images
		File jsFile=new File(result_FolderName+"/Pie_Charts/jsFiles");
		jsFile.mkdirs();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"/externalFiles/config/config.properties");
		Properties CONFIG= new Properties();
		CONFIG.load(fs);
		String environment=CONFIG.getProperty("environment");
		release=buildVersion; // Added By Karan Sood - 2nd December 2014
		TestCaseInputReader suiteXLS = new TestCaseInputReader(System.getProperty("user.dir")+"/externalFiles/xls/Suite.xls");        
		String tc_pattern=".*[tT][cC].*-*.*\\d+(.*\\n*.*)*";
		String Comma_Pattern=".*\\[+.*[tT][cC].*\\d+.*,+.*\\d+.*\\]+(.*\\n*.*)*";
		String result[]={"Pass", "Fail", "Skip"};
		//modified by Sumit on 04/03/2014 to parse long array to string
		int timeSize=totalTime.length;
		String strTime[]= new String[timeSize];
		for(int i=1;i<timeSize;i++)
		{
			strTime[i]=String.valueOf(totalTime[i]);
		}
		// create index.html
		/**Tarun Sharma
		 * updated path separator from "\\" to "/" 
		 * to resolve compatibility issue with linux.
		 */
		String indexHtmlPath=result_FolderName+"/index.html";
		new File(indexHtmlPath).createNewFile();
		//added by Sumit on 25/06/2014 to create html file for pass, fail, skip cases
		String passCases=result_FolderName+"/Pie_Charts/PassCases.html";
		new File(passCases).createNewFile();
		String failCases=result_FolderName+"/Pie_Charts/FailCases.html";
		new File(failCases).createNewFile();
		String skipCases=result_FolderName+"/Pie_Charts/SkipCases.html";
		new File(skipCases).createNewFile();
		try
		{  
			File source = new File("externalFiles/log/Application.html");
			//added by Sumit on 25/06/2014 to create separate log.html file for each report
			File dest = new File(result_FolderName+"/log.html");
			copyFileUsingFileChannels(source, dest);
			String[] jsSrcDest={"pie_1.js","pie_2.js","pie-chart.js","pie-min.js"};
			for(String srcDest: jsSrcDest)
			{
				File jsSource = new File("externalFiles/js_files/"+srcDest);
				File jsDest = new File(result_FolderName+"/Pie_Charts/jsFiles/"+srcDest);
				copyFileUsingFileChannels(jsSource, jsDest);
				hide(jsDest);
			}
			hide(jsFile); 
			FileWriter fstream = new FileWriter(indexHtmlPath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<html><HEAD> <TITLE>Automation Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4><table  border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(d.toString());
			out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Environment</b></td><td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
			out.write(environment);
			out.write("</b></td></tr><tr><td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td><td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>");
			out.write(release);
			out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>TAB NAME</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>NO. OF CASES</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>FAIL CASES</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>FAIL SUITE COUNT</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");          
			int totalTestSuites=suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET);
			int automatedCases=0;
			int Total_Cases=0;
			int TC_Fail_Count=0;
			int TC_Skip_Count=0;
			int TC_Pass_Count=0;
			int failCounter=0;
			int moduleFailCount=0;
			int currentSuiteID=0;
			String currentTestSuite=null;
			TestCaseInputReader current_suite_xls=null;
			String suite_result="";
			// modified by Sumit Aggarwal on 27/11/2013. this will create a set of modules present in suite.
			Set<String> al = new HashSet<String>();
			for(int suiteID=2;suiteID<= totalTestSuites;suiteID++)
			{
				currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,suiteID);
				currentModule=currentTestSuite.split("_")[0];
				String module=CONFIG.getProperty(currentModule);
				al.add(module);
			}
			// modified by Sumit Aggarwal on 27/11/2013. this will create a specific module.html files with corresponding suite details in it.
			for(Iterator<String> it = al.iterator(); it.hasNext();)
			{
				List<Double> Result_Values=new ArrayList<Double>();
				int failSuite=0;
				int passSuite=0;
				int Total_suit_cases=0;
				int TotalCase_Pass_Count=0;
				int TotalCase_Fail_Count=0;
				int TotalCase_Skip_Count=0;
				List<String> FailCases= new ArrayList<String>();
				List<String> PassCases= new ArrayList<String>();
				List<String> SkipCases= new ArrayList<String>();
				BufferedWriter cases[]=new BufferedWriter[3];
				String suiteName = it.next();
				char first = Character.toUpperCase(suiteName.charAt(0));
				String moduleName = first + suiteName.substring(1);	  
				String indexHtmlPath1=result_FolderName+"/"+moduleName+"_Module.html";
				new File(indexHtmlPath1).createNewFile();
				FileWriter moduleFile = new FileWriter(indexHtmlPath1);
				//added by Sumit on 26/06/2014 to add data to different html files
				BufferedWriter module_out = new BufferedWriter(moduleFile);
				FileWriter pstream = new FileWriter(passCases);
				cases[0] = new BufferedWriter(pstream);
				FileWriter fastream = new FileWriter(failCases);
				cases[1]  = new BufferedWriter(fastream);
				FileWriter sstream = new FileWriter(skipCases);
				cases[2]  = new BufferedWriter(sstream);
				//added by Sumit on 25/06/2014 to create pie chart using javascript
				for(BufferedWriter bw: cases){
					bw.write("<html><HEAD> <TITLE>"+moduleName+" Test Results</TITLE><script src=\"jsFiles/pie_1.js\"></script>" +
						"<script src=\"jsFiles/pie_2.js\"></script><script src=\"jsFiles/pie-min.js\"></script><script src=\"jsFiles/pie-chart.js\"></script></HEAD><body class=\"raphael\"><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=5><b><u> "+moduleName+" Tab Pie Chart Results</u></b></h4>");
				}
				module_out.write("<html><HEAD> <TITLE>"+moduleName+" Test Results</TITLE><script src=\"Pie_Charts/jsFiles/pie_1.js\"></script>" +
						"<script src=\"Pie_Charts/jsFiles/pie_2.js\"></script><script src=\"Pie_Charts/jsFiles/pie-min.js\"></script><script src=\"Pie_Charts/jsFiles/pie-chart.js\"></script></HEAD><body class=\"raphael\"><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> "+moduleName+" Tab Detailed Test Results</u></b></h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
				//modified by Sumit on 04/03/2014 added entry for execution time.
				//edited by vikas
				module_out.write("</b></td></tr></table><h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>Suite Report :</u></h4><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>SUITE NAME</b></td><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>DESCRIPTION</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>FAIL TEST CASES</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>TOTAL CASES</b></td><td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION TIME (Min.)</b></td><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>EXECUTION RESULT</b></td></tr>");
				for(currentSuiteID =2;currentSuiteID<= totalTestSuites;currentSuiteID++)
				{
					FailCases.clear();
					PassCases.clear();
					SkipCases.clear();
					boolean firstCase=true;
					int caseNum=0;
					boolean secondCase=false;
					int caseNum1=0;
					suite_result="";
					currentTestSuite=null;
					current_suite_xls=null;
					currentModule=null;
					failCounter=0;
					automatedCases=0;
					//Modified by Sandeep dhamija. 9 July, 2013
					currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.SUITE_ID,currentSuiteID);
					currentModule=currentTestSuite.split("_")[0]; 
					String suiteTestName=CONFIG.getProperty(currentModule);
					String currentTestName=null;
					String currentTestRunmode=null;
					String currentTestDescription=null;
					if(suiteName.equals(suiteTestName))
					{//Modified by Mayank Saini
						String path=suiteXLS.getXlSPath(System.getProperty("user.dir")+"/externalFiles/xls/"+suiteName, currentTestSuite+".xls");
                        //Added by vikas to set blank value to cell displaying total Number of cases.
                        
                                
                                fis = new FileInputStream(path);
                            workbook = WorkbookFactory.create(fis);
                            int index = workbook.getSheetIndex(Constants.TEST_STEPS_SHEET);
                                sheet = workbook.getSheetAt(index);
                                row=sheet.getRow(Constants.TOTAL_CASE_ROW);
                                cell = row.createCell(Constants.DESCRIPTION_COL);
                                build_cell=row.createCell(Constants.TEST_CASE_COL);
                                build_cell.setCellValue("");
                                cell.setCellValue(" ");
                        fileOut = new FileOutputStream(path);
                            workbook.write(fileOut);
                    fileOut.close();        
                                current_suite_path=path;
                                current_suite_xls=new TestCaseInputReader(path);

						for(int currentTestCaseID=2;currentTestCaseID<=current_suite_xls.getRowCount(Constants.TEST_CASES_SHEET);currentTestCaseID++)
						{
							int incremental_value=Constants.INCREMENTAL_VALUE;//vikas
							List<Integer> report_row = new ArrayList<Integer>();//vikas
							FailCases.clear();
							PassCases.clear();
							SkipCases.clear();
							caseNum=0;
							caseNum1=0;
							firstCase=true;
							secondCase=false;
							currentTestName=null;
							currentTestDescription=null;
							currentTestRunmode=null;
							moduleFailCount=0;
							TC_Fail_Count=0;
							TC_Skip_Count=0;
							TC_Pass_Count=0;
							Total_Cases=0;
							currentTestName = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, currentTestCaseID);
							currentTestDescription = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.DESCRIPTION, currentTestCaseID);
							currentTestRunmode = current_suite_xls.getCellData(Constants.TEST_CASES_SHEET, Constants.RUNMODE, currentTestCaseID);
							System.out.println(currentTestSuite + " -- "+currentTestName );
							/**Tarun Sharma
							 * updated path separator from "\\" to "/" 
							 * to resolve compatibility issue with linux.
							 */
							// make the file corresponding to test Steps
							String testSteps_file=result_FolderName+"/"+currentTestSuite+"_steps.html";
							new File(testSteps_file).createNewFile();
							int rows= current_suite_xls.getRowCount(Constants.TEST_STEPS_SHEET);
							int cols = current_suite_xls.getColumnCount(Constants.TEST_STEPS_SHEET);
							FileWriter fstream_test_steps= new FileWriter(testSteps_file);
							BufferedWriter out_test_steps= new BufferedWriter(fstream_test_steps);
							/*Added By Anil kumar Mishra  Date:12/12/13
							 * Added js files path used for pagination, sorting and filter data in  Html Report 
							 * */
							out_test_steps.write("<html><HEAD><script src=\"../externalFiles/js_files/jquery_004.js\"></script><script src=\"../externalFiles/js_files/jquery_003.js\"></script>" +
									"<script src=\"../externalFiles/js_files/jquery.js\"></script><link rel=\"stylesheet\" href=\"../externalFiles/js_files/jquery.css\">" +
									"<script src=\"../externalFiles/js_files/jquery_002.js\"></script><script src=\"../externalFiles/js_files/jquery.filtertable.js\"></script><script src=\"../externalFiles/js_files/script.js\"></script> <TITLE>"+currentTestSuite+" Test Results</TITLE></HEAD><body><h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=5><b><u> "+currentTestSuite+" Detailed Test Results</u></b></h4><h6 align=left><FONT COLOR=660066 FACE=AriaL SIZE=4><b>Execution Time (Min) : "+strTime[currentSuiteID-1]+"</b></h6>" +
									"<div class=\"pager\"><img src=\"../externalFiles/js_images/first.png\" class=\"first\" alt=\"First\">"+
									"<img src=\"../externalFiles/js_images/prev.png\" class=\"prev\" alt=\"Prev\"><font face=\"Times New Roman\" SIZE=5><span class=\"pagedisplay\"></span></font><img src=\"../externalFiles/js_images/next.png\" class=\"next\" alt=\"Next\">" +
									"<img src=\"../externalFiles/js_images/last.png\" class=\"last\" alt=\"Last\"><font face=\"Times New Roman\" SIZE=5>&nbsp;PageSize:</font> <select class=\"pagesize\" title=\"Select page size\">" +
									"<option selected=\"selected\" value="+Constants.DD_VALUE_10+">"+Constants.DD_VALUE_10+"</option><option value="+Constants.DD_VALUE_20+">"+Constants.DD_VALUE_20+"</option><option value="+Constants.DD_VALUE_30+">"+Constants.DD_VALUE_30+"</option><option value="+Constants.DD_VALUE_40+">"+Constants.DD_VALUE_40+"</option><option value="+Constants.DD_VALUE_50+">"+Constants.DD_VALUE_50+"</option>" +
									"</select><font face=\"Times New Roman\" SIZE=5>&nbsp;PageNumber:</font><select class=\"gotoPage\" title=\"Select page number\"></select></div>" +
							"<table class=\"tablesorter\" width=100% border=1 cellspacing=1 cellpadding=1 >");
							out_test_steps.write("<thead><tr>");
							for(int colNum=0;colNum<cols;colNum++)
							{
								/*Added By Anil Kumar Mishra  Date:12/12/13
								 * Added class for enable sorting on only first index (TCID)
								 * */
								if(colNum==0)
									out_test_steps.write("<th align= center bgcolor=#153E7E><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								else
									out_test_steps.write("<th align= center bgcolor=#153E7E class=\"sorter-false\"><FONT COLOR=#ffffff FACE= Arial  SIZE=2><b>");
								out_test_steps.write(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1));
							}
							out_test_steps.write("</b></tr></thead><tbody>");
							// fill the whole sheet
							int Tc_Pass_Var=0;
							int Tc_Fail_Var=0;
							int Tc_Skip_Var=0;
							int Tc_row_num=0;
							int color_changer=0; //vikas
							boolean result_col=false;
							int prev_commas=0;
							for(int rowNum=2;rowNum<=rows;rowNum++)
							{
								int commas=0;
								String TC_DESCRIPTION=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum);
								if(matchPattern(TC_DESCRIPTION,Comma_Pattern) && current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals(""))
								{
									if(secondCase)
									{
										caseNum1=rowNum;
									}
									if(firstCase)
									{
										caseNum=rowNum;
										secondCase=true;
										firstCase=false;
									}
									if((!matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)))
									{
									for(int i=0;i<TC_DESCRIPTION.length();i++)
									{
										if(TC_DESCRIPTION.charAt(i)==',')
											commas++;
										if(TC_DESCRIPTION.charAt(i)==']')
											break;
									}
									if(commas>0)
										Total_Cases=Total_Cases+commas;
									}
								}
								//created by sohal bansal -23/12/2013 this will count the total test cases by matching the test cases descriptipn
								if(matchPattern(TC_DESCRIPTION,tc_pattern)&& current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals(""))
								{
									if(secondCase)
									{
										caseNum1=rowNum;
									}
									if(firstCase)
									{
										caseNum=rowNum;
										secondCase=true;
										firstCase=false;
									}
									
									
										if(!(matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)&&current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum-1).trim().equals("")))
										{
											
									if(matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,Tc_row_num),Comma_Pattern)&&current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,Tc_row_num).trim().equals(""))
									{
										if(Tc_Fail_Var>0)
											TC_Fail_Count=TC_Fail_Count+prev_commas;
										else if(Tc_Skip_Var>0)
											TC_Skip_Count=TC_Skip_Count+prev_commas;
										else if(Tc_Pass_Var>0)
											TC_Pass_Count=TC_Pass_Count+prev_commas;
									}
									prev_commas=commas;
									Tc_row_num=rowNum;
										boolean flag=false;
										for(int i=2;i<=rowNum-1;i++)
										{
											if(matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,i),tc_pattern)&& current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,i).trim().equals(""))
											{
												flag=false;
												break;

											}
											else
											{
												flag=true;
											}
										}
										if(flag)
										{
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										if(Tc_Fail_Var>0)
										{
											TC_Fail_Count++;
											//added by Sumit on 25/06/2014 to create ArrayList for Fail cases
											FailCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											caseNum=caseNum1;
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										if(Tc_Fail_Var==0 && Tc_Skip_Var>0)
										{
											TC_Skip_Count++;
											//added by Sumit on 25/06/2014 to create ArrayList for Skip cases
											SkipCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											caseNum=caseNum1;
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										if(Tc_Fail_Var==0 && Tc_Pass_Var>0 && Tc_Skip_Var==0)
										{
											TC_Pass_Count++;
											//added by Sumit on 25/06/2014 to create ArrayList for Pass cases
											PassCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											caseNum=caseNum1;
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										Total_Cases=Total_Cases+1;
									}
									
								}
								out_test_steps.write("<tr>");  
								for(int colNum=0;colNum<cols;colNum++){
									String data=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, rowNum);
									result_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT);
									boolean test_case_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.TCID);
									boolean result_col_1st=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).equals(Constants.RESULT1);//modified by sdhamija.24 oct, 2013. Find the first result column in case multiple result columns are present[happens in sheets having parameterized modules.]
									if(data.isEmpty())
									{
										if(result_col && result_col_1st)
											data="NA";  //Modified by Puneet. 10 July, 2013//modified by sdhamija.24 oct, 2013. write 'NA' for empty cell in first result column.
										else if(result_col && !result_col_1st)//modified by sdhamija.24 oct, 2013. If the cell is empty and it is not the first result column, leave the cell blank
											data="";
									}
									
									
									// Added by Vikas Bhadwal on 16/03/2015 it will present different background colors for testcases alernatively.
									
                                        if (test_case_col&& data.trim().length() == 0) {
										color_changer++;
									}
									if (test_case_col&& data.trim().length() != 0) {

										while (colNum < 7) {
											String test_col_data = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,colNum, rowNum);
											
										// editted by Vikas to set blank value to cell displaying total cases and execution time on 16/04/2015
											if(test_col_data.contains("Total Cases"))
											{
												test_col_data="";
										    }
											
										     	if (color_changer % 2 == 0) {
												out_test_steps.write("<td align=center bgcolor=#E6E6FA><FONT COLOR=black FACE= Arial  SIZE=1>");
												out_test_steps.write(test_col_data);
												colNum++;
											}
											else {
												out_test_steps.write("<td align=center bgcolor=#E0FFFF><FONT COLOR=black FACE= Arial  SIZE=1>");
												out_test_steps.write(test_col_data);
												colNum++;
											}

											data = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,colNum, rowNum);
											result_col = current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,colNum, 1).startsWith(Constants.RESULT);
										}
									}
									
									// Modified By Mayank Saini on 15/11/2013. this will present test case description in colored format.
									if(test_case_col&&data.trim().length()==0)
									{
										while(true)
										{
											String test_col_data=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, rowNum);
											result_col=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT);
											if(!result_col&&colNum>6)
											{
												break;
											}
											if(test_col_data.trim().isEmpty())
											{
												if(result_col )
													test_col_data="NA";  
											}
											if(test_col_data.trim().isEmpty())
											{
												out_test_steps.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>");
												out_test_steps.write(test_col_data);
												colNum++;
											} 
											else if(test_col_data.trim().length()!=0&&!test_col_data.equals("NA"))
											{
												out_test_steps.write("<td align=center bgcolor=#ffffff width='70%' ><FONT COLOR=#DC143C FACE= Arial SIZE=1>"); 
												out_test_steps.write(test_col_data);
												colNum++;
											}
											else if(test_col_data.equals("NA")&&result_col)
											{
												out_test_steps.write("<td align=center bgcolor=#2EFEF7><FONT COLOR=#000000 FACE= Arial  SIZE=1><b>");
												out_test_steps.write(test_col_data);
												break;
											}
										}
									}
									else if((data.startsWith("Pass") || data.startsWith("PASS")) && result_col)
									{
										out_test_steps.write("<td align=center bgcolor=green><FONT COLOR=#000000 FACE= Arial  SIZE=1>"+data);
										Tc_Pass_Var++;
									}
									else if((data.startsWith("Fail") || data.startsWith("FAIL")) && result_col)
									{
									  File f;
									  if(report_row.contains(rowNum)){
									    f=map.get(rowNum+incremental_value);
									    incremental_value++;
									}
									else{
									     f= map.get(rowNum);
									     report_row.add(rowNum);
										}
												
									out_test_steps.write("<td align=center bgcolor=red width='70%'><FONT COLOR=#000000 FACE= Arial  SIZE=1>" + data +"<a href="+ f+ "><button>    View Screenshot</button></a></td>");
								    moduleFailCount++;
									Tc_Fail_Var++;
									if(suite_result.equals(""))
									suite_result="FAIL";
								}
									else if((data.startsWith("Skip") || data.startsWith("SKIP")) && result_col)
									{
										out_test_steps.write("<td align=center bgcolor=yellow><FONT COLOR=#000000 FACE= Arial  SIZE=1>"+data);
										Tc_Skip_Var++;
									}
									else if((data.equals("")) && result_col && !result_col_1st)//modified by sdhamija.24 oct, 2013. set white background color for all empty cells in sheet except 1st result column.
										out_test_steps.write("<td align=center bgcolor=white><FONT COLOR=#000000 FACE= Arial  SIZE=1><b>"+data);
									else if((data.equals("NA")) && result_col && result_col_1st)//modified by sdhamija.24 oct, 2013. set white background color for all empty cells in sheet except 1st result column.
										out_test_steps.write("<td align=center bgcolor=#2EFEF7><FONT COLOR=#000000 FACE= Arial  SIZE=1><b>"+data);
									else 
										out_test_steps.write("<td align= center bgcolor=#ffffff><FONT COLOR=#000000 FACE= Arial  SIZE=1>"+data);     
								}
								if(rowNum==rows)
								{
									if(matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,Tc_row_num),Comma_Pattern)&& current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,Tc_row_num).trim().equals(""))
									{
										if(Tc_Fail_Var>0)
											TC_Fail_Count=TC_Fail_Count+prev_commas;
										else if(Tc_Skip_Var>0)
											TC_Skip_Count=TC_Skip_Count+prev_commas;
										else if(Tc_Pass_Var>0)
											TC_Pass_Count=TC_Pass_Count+prev_commas;
									}
									if(Tc_row_num>0)
									{
										if(Tc_Fail_Var>0)
										{
											TC_Fail_Count++;
											FailCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										if(Tc_Fail_Var==0 && Tc_Skip_Var>0)
										{
											TC_Skip_Count++;
											SkipCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
										if(Tc_Fail_Var==0 && Tc_Pass_Var>0 && Tc_Skip_Var==0)
										{
											TC_Pass_Count++;
											PassCases.add(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,caseNum));
											Tc_Pass_Var=0;
											Tc_Fail_Var=0;
											Tc_Skip_Var=0;
										}
									}
									else
									{
										Tc_Pass_Var=0;
										Tc_Fail_Var=0;
										Tc_Skip_Var=0;
									}
								}
								out_test_steps.write("</tr></b>");
							}
							out_test_steps.write("</tr>");
							out_test_steps.write("</table>");  
							out_test_steps.close();
						}
						int rows= current_suite_xls.getRowCount(Constants.TEST_STEPS_SHEET);
		            	//created by sohal bansal -23/12/2013 this will count the total test cases by matching the test cases description
		            	for(int rowNum=2;rowNum<=rows;rowNum++)
		            	{
		            		  	int commas=0;
								String TC_DESCRIPTION=current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum);
								if(matchPattern(TC_DESCRIPTION,Comma_Pattern) && current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals(""))
								{
									if((!current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum-1).trim().equals("")) &&(!matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)))
									{
										for(int i=0;i<TC_DESCRIPTION.length();i++)
										{
											if(TC_DESCRIPTION.charAt(i)==',')
											commas++;
											if(TC_DESCRIPTION.charAt(i)==']')
											break;
										}
										if(commas>0)
										automatedCases=automatedCases+commas;
									}
								}
								//created by sohal bansal -23/12/2013 this will count the total test cases by matching the test cases description
								if(matchPattern(TC_DESCRIPTION,tc_pattern)&& current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals(""))
								{
									if(!(matchPattern(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)&&current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum-1).trim().equals("")))
									{
										automatedCases=automatedCases+1;
									}
								}
		            	}
						// modified by Sumit Aggarwal on 27/11/2013. this will add suite details in specific module.html file with fail count present in suite.
						failCounter=failCounter+moduleFailCount;
						String strFailCount = String.valueOf(TC_Fail_Count);
						String strTotalCases= String.valueOf(automatedCases);
						module_out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						module_out.write("<a href="+currentTestSuite.replace(" ", "%20")+"_steps.html>"+currentTestSuite+"</a>");
						module_out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						module_out.write(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.DESCRIPTION,currentSuiteID));
						module_out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						module_out.write(strFailCount);
						//added by Sumit on 07/03/2014. this displays total automated cases in each suite.
		                module_out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
		                module_out.write(strTotalCases);
		                module_out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");  //added by vikas to write execution time.
		                
		                module_out.write(strTime[currentSuiteID-1]);  //added by vikas to write execution time.
		                
						module_out.write("</b></td><td width=10% align=center  bgcolor=");
						
					if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE,currentSuiteID).equalsIgnoreCase(Constants.RUNMODE_YES))
							if(suite_result.equals("FAIL"))
							{
								failSuite++;
								module_out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
							}
							else
							{
								passSuite++;
								module_out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
							}
						else
							module_out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");
						int cols = current_suite_xls.getColumnCount(Constants.TEST_STEPS_SHEET);
						boolean flag=false;
						//By Sohal Bansal-18/04/2014
						//this code is used to calculate the skip cases if result column is not presen in test steps sheet of Suie
						for(int colNum=0;colNum<cols;colNum++)
						{
							if(current_suite_xls.getCellData(Constants.TEST_STEPS_SHEET, colNum, 1).startsWith(Constants.RESULT))							{
								flag=true;
							}
						}
						if(!flag)
						{
							TC_Pass_Count=0;
							TC_Fail_Count=0;
							TC_Skip_Count=Total_Cases;
						}
						Total_suit_cases=Total_suit_cases+Total_Cases;
						TotalCase_Pass_Count=TotalCase_Pass_Count+TC_Pass_Count;
						TotalCase_Fail_Count=TotalCase_Fail_Count+TC_Fail_Count;
						TotalCase_Skip_Count=TotalCase_Skip_Count+TC_Skip_Count;
						setCaseCount(strTotalCases,current_suite_path,strTime[currentSuiteID-1],(TotalCase_Pass_Count+TotalCase_Fail_Count));//vikas bhadwal to set total test cases,Executed test case count and execution time.
					}
				}
				String strFailSuite = String.valueOf(failSuite);
				String strTotal_suit_cases=String.valueOf(Total_suit_cases);
				String strTotalCase_Fail_Count=String.valueOf(TotalCase_Fail_Count);
				String strTotalCase_Pass_Count=String.valueOf(TotalCase_Pass_Count);
				String strTotalCase_Skip_Count=String.valueOf(TotalCase_Skip_Count);
				Result_Values.add((double) TotalCase_Skip_Count);
				Result_Values.add((double) TotalCase_Pass_Count);
				Result_Values.add((double) TotalCase_Fail_Count);
				//added by Sumit on 25/06/2014 to add pie chart and list of cases in html files
				for(BufferedWriter bw: cases){
					bw.write("<html><HEAD><script>var flag = "+true+"</script><script>var Fail = "+TotalCase_Fail_Count+"</script><script>var Pass= "+TotalCase_Pass_Count +"</script><script>var Skip= "+TotalCase_Skip_Count+"</script></HEAD>" +
				"<table cellspacing=20><tr><td><table border=\"0\"><tr><td><div id=\"holder\" align=\"center\" style=\"width:500px; height:400px;\"></div></td></tr></table></td>");
					bw.write("<td><table align=center border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Cases Detail :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
					bw.write(strTotal_suit_cases);
					bw.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Pass Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
					bw.write(strTotalCase_Pass_Count);
					bw.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Fail Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
					bw.write(strTotalCase_Fail_Count);
					bw.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Skip Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
					bw.write(strTotalCase_Skip_Count+"</b></td></tr>");
					bw.write("</table></table>");
				}
				for(int k=0;k<=2;k++)
				{
				cases[k].write("<body><h4 align=center><FONT COLOR=660000 FACE= Arial SIZE=4.5>List of "+result[k]+" Cases in "+moduleName+" Tab</h4><table width=100% border=1 cellspacing=1 cellpadding=1 >");
				cases[k].write("</b></td></tr></table><table  border=1 cellspacing=1 cellpadding=1 width=100%><tr><td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Sr. No</b></td><td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Case Description</b></td></tr>");
				}
				int i=0,f=0,sk=0;
				
				if(PassCases.size()==0)
				{
					cases[0].write("<h4 align=center><FONT COLOR=660000 FACE= Arial SIZE=4.5>No Case Found.</h4>");
				}
				else{
					for(String s : PassCases)
					{
						cases[0].write("<tr><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						String SrCount=String.valueOf(i+1);
						cases[0].write(SrCount);
						cases[0].write("</b></td><td width=40% align= justify><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						cases[0].write(s+"</b></td></tr>");
						i++;
					}
				}
				cases[0].write("</table><br><br><br><html>");
				cases[0].close();
				
				if(FailCases.size()==0)
				{
					cases[1].write("<h4 align=center><FONT COLOR=660000 FACE= Arial SIZE=4.5>No Case Found.</h4>");
				}
				else{
					for(String s : FailCases)
					{
						cases[1].write("<tr><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						String SrCount=String.valueOf(sk+1);
						cases[1].write(SrCount);
						cases[1].write("</b></td><td width=40% align= justify><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						cases[1].write(s+"</b></td></tr>");
						sk++;
					}
				}
					cases[1].write("</table><br><br><br><html>");
					cases[1].close();
					
				if(SkipCases.size()==0)
				{
					cases[2].write("<h4 align=center><FONT COLOR=660000 FACE= Arial SIZE=4.5>No Case Found.</h4>");
				}	
				else{
					for(String s : SkipCases)
					{
						cases[2].write("<tr><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						String SrCount=String.valueOf(f+1);
						cases[2].write(SrCount);
						cases[2].write("</b></td><td width=40% align= justify><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
						cases[2].write(s+"</b></td></tr>");
						f++;
					}
				}
				cases[2].write("</table><br><br><br><html>");
				cases[2].close();
				//added by Sumit on 26/06/2014 to display pie chart using javascript 
				module_out.write("<html><HEAD><script>var flag = "+false+"</script><script>var Fail = "+TotalCase_Fail_Count+"</script><script>var Pass= "+TotalCase_Pass_Count +"</script><script>var Skip= "+TotalCase_Skip_Count+"</script></HEAD>" +
						"<table cellspacing=40><tr><td><table border=\"1\"><tr><td><div id=\"holder\" align=\"center\" style=\"width:500px; height:400px;\"></div></td></tr></table></td>");
				module_out.write("<td><table align=center border=1 cellspacing=1 cellpadding=1 ><tr><h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Cases Detail :</u></h4><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
				module_out.write(strTotal_suit_cases);
				module_out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Pass Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
				module_out.write(strTotalCase_Pass_Count);
				module_out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Fail Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
				module_out.write(strTotalCase_Fail_Count);
				module_out.write("</b></td></tr><tr><td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Skip Cases</b></td><td width=50 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>");
				module_out.write(strTotalCase_Skip_Count+"</b></td></tr>");
				module_out.write("</table></table>");
				module_out.write("</table>");
				module_out.close();
				// modified by Sumit Aggarwal on 27/11/2013. this will show tab name in link form in index.html and its details with fail suite count in tab. 
				out.write("<tr><td width=20% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("<a href="+moduleName.replace(" ", "%20")+"_Module.html>"+moduleName+"</a>");
				out.write("</b></td><td width=40% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write("This Tab contains all sheets related to "+ moduleName);
				out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write(strTotal_suit_cases);
				out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write(strTotalCase_Fail_Count);
				out.write("</b></td><td width=10% align= center><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>");
				out.write(strFailSuite);
				out.write("</b></td><td width=10% align=center  bgcolor=");
				if(failSuite>0)
					out.write("red><FONT COLOR=153E7E FACE=Arial SIZE=2><b>FAIL</b></td></tr>");
				else if(failSuite==0 && passSuite==0)
					out.write("yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>SKIP</b></td></tr>");   	  
				else
					out.write("green><FONT COLOR=153E7E FACE=Arial SIZE=2><b>PASS</b></td></tr>");
			}
			//Close the output stream
			out.write("</table></br></br>");
			out.write("<a href=log.html target=_blank style=margin-left:600;margin-right:600;display:block;><button style=width:150;height:35;><b style=font-size:15px;>View Log Details</b></button></a>");
			out.close();
		}
		catch (Exception e)
		{//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * Created by sohal bansal-23/12/2013
	 * this method will match the pattern with test case description 
	 */
	Boolean matchPattern(String Desc,String tc_pattern)
	{
		String pattern=tc_pattern;
		Boolean t=Desc.matches(pattern);
		return t;
	}
	
	/*
	 * Created by Sumit Aggarwal-25/06/2014
	 * this method copy file from source to destination
	 */
	private static void copyFileUsingFileChannels(File source, File dest)throws IOException 
	{
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	/*
	 * Created by Sumit Aggarwal-25/06/2014
	 * this method hide file in window system
	 */
	void hide(File src) throws InterruptedException, IOException {
	    Process p = Runtime.getRuntime().exec("attrib +h " + src.getPath());
	    p.waitFor(); // p.waitFor() important, so that the file really appears as hidden immediately after function exit.
	}
	
	/*
     * Added by Vikas Bhadwal on 16/03/2015
     * this method updates the total test cases and execution time in xls sheets.
     * Modified on 20/05/2015 to add Build Number and Total Executed Test Cases.
     */
         public boolean setCaseCount(String totalCases,String path,String executionTime,int Total_Executed_Cases){
        try{
       
            fis = new FileInputStream(path);
            workbook = WorkbookFactory.create(fis);
            int index = workbook.getSheetIndex(Constants.TEST_STEPS_SHEET);
            sheet = workbook.getSheetAt(index);
            row=sheet.getRow(Constants.TOTAL_CASE_ROW);
            cell = row.createCell(Constants.DESCRIPTION_COL);
            build_cell=row.createCell(Constants.TEST_CASE_COL);
            CellStyle  style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            font.setFontName("Calibri");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.RED.index);
            style.setFont(font);
            cell.setCellStyle(style);
            cell.setCellValue( "Total Test Cases - " + totalCases + " , Executed Test Cases - " +Total_Executed_Cases +", Execution Time - " +executionTime + " min.");
            build_cell.setCellStyle(style);
            build_cell.setCellValue("Build Version : " +release);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();   

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}