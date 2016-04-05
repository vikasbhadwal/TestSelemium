/**31/10/2013
 * Added By Mayank Saini 
 *  This HTML Log Formatter is a simple replacement for the standard Log4J HTMLLayout formatter 
 *  It Display the keyword used per line in the Test cases Sheet as well the corresponding xpath used
 *  in the line
 */

package com.tk20.seleniumuiflipassessment.base;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.testCaseName;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.testInputReader;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.testStepID;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.testSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LoggingEvent;



import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.map;//added by 	Vikas Bhadwal

/**
 * This layout outputs events in a HTML table.
 *
 * Appenders using this layout should have their encoding
 * set to UTF-8 or UTF-16, otherwise events containing
 * non ASCII characters could result in corrupted
 * log files.
 *
 * 
 */
public class LogLayout extends org.apache.log4j.HTMLLayout {
	public static int incremental_value=Constants.INCREMENTAL_VALUE;
	 List<Integer> report_row = new ArrayList<Integer>();//vikas
	 String lastvalue;
	 int lastSheetLine;
	 int lastLine;
	 String lastXpath;
  protected final int BUF_SIZE = 256;
  protected final int MAX_CAPACITY = 1024;
  static String msg="....PLEASE REMOVE THE DUPLICATE ENTRIES....";


  static String TRACE_PREFIX = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";
//RegEx pattern looks for <tr> <td> nnn...nnn </td> (all whitespace ignored) 
	 
	private String timestampFormat = "yyyy-MM-dd-HH:mm:ss"; // Default format. Example: 2008-11-21-18:35:21.472-0800  

	private SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);  

	public LogLayout() {
	super();	// TODO Auto-generated constructor stub
	}
  // output buffer appended to when format() is invoked
  private StringBuffer sbuf = new StringBuffer(BUF_SIZE);

  /**
     A string constant used in naming the option for setting the the
     location information flag.  Current value of this string
     constant is <b>LocationInfo</b>.

     <p>Note that all option keys are case sensitive.

     @deprecated Options are now handled using the JavaBeans paradigm.
     This constant is not longer needed and will be removed in the
     <em>near</em> term.

  */
  public static final String LOCATION_INFO_OPTION = "LocationInfo";

  /**
     A string constant used in naming the option for setting the the
     HTML document title.  Current value of this string
     constant is <b>Title</b>.
  */
  public static final String TITLE_OPTION = "Title";

  // Print no location info by default
  boolean locationInfo = false;

  String title = "Application Log";


  public
  String format(LoggingEvent event) {
	  
	  
	  

    if(sbuf.capacity() > MAX_CAPACITY) {
      sbuf = new StringBuffer(BUF_SIZE);
    } else {
      sbuf.setLength(0);
    }
    String message=event.getRenderedMessage();
   
    if(message.equals(testCaseName+":$Executed")||message.equals(testCaseName+":$Skipped")||message.equals("Executing the test case -> "+testCaseName)){
    	
    	if(message.equals(testCaseName+":$Skipped")){
    		sbuf.append( "<tr bgcolor=F4F475>" + Layout.LINE_SEP);
    		
    	}
    	else{
    	sbuf.append( "<tr bgcolor=D8D8D8>" + Layout.LINE_SEP);
    	} 
    	sbuf.append("<td>");
    	 sbuf.append("</td>" + Layout.LINE_SEP);
    	 sbuf.append("<td>");
    	 sbuf.append("</td>" + Layout.LINE_SEP);
    	
    	 sbuf.append("<td align='Center'><FONT COLOR=000066 FACE=AriaL SIZE=4>" +Layout.LINE_SEP);
    	
    	if(message.equals(testCaseName+":$Executed"))
    	{
    		
    	sbuf.append("Module: "+testCaseName+" Executed");
    	}
    	else if(message.equals(testCaseName+":$Skipped"))
    	{

    	sbuf.append("Module: "+testCaseName+" Skipped");
    	}
    	else if(message.equals("Executing the test case -> "+testCaseName)){
    		
    		sbuf.append("Executing the Module -> "+testCaseName);
    	}
    	sbuf.append("</td>" + Layout.LINE_SEP);
   	 sbuf.append("<td>");
	 sbuf.append("</td>" + Layout.LINE_SEP);
    	return sbuf.toString();
    }
 sbuf.append(Layout.LINE_SEP + "<tr>" + Layout.LINE_SEP);
 String keyword=DataProvider.keyword;
 int currentline=testStepID;
    sbuf.append("<td>");
    sbuf.append(sdf.format(new Date(event.timeStamp)));
    sbuf.append("</td>" + Layout.LINE_SEP);

    //Printing the Keyword and line no to logger

    if(keyword==null&&currentline==0)
    {
    	sbuf.append("<td>");
    	
    }
    else  if(keyword.equals(lastvalue)&&currentline==lastLine){
    	sbuf.append("<td>");
    }
    else if(keyword.equals(lastvalue)&&currentline==testInputReader.getRowCount(Constants.TEST_STEPS_SHEET)+1){
    	sbuf.append("<td>");
    }
    else if(keyword.equals(lastvalue)&&currentline==lastSheetLine){
    	sbuf.append("<td>");
    }
     else {
    	 lastvalue=keyword;
    	 lastLine=currentline;	
    	 sbuf.append("<td>");
    	 sbuf.append(currentline+"/"+keyword);
    	 lastSheetLine=testInputReader.getRowCount(Constants.TEST_STEPS_SHEET)+1;
    	}
    
    sbuf.append("</td>" + Layout.LINE_SEP);
    //Printing the Object and Xpath to logger
    String xpath=DataProvider.object;
    
    if(xpath==null){
    	sbuf.append("<td>");
    }
    else if(xpath.equals(lastXpath)){
    	sbuf.append("<td>");
    }
    else{
    	
    	lastXpath=xpath;
    	String object=OR.getProperty(xpath);
    	if(object==null)
    	sbuf.append("<td>");	
    	else{
    		sbuf.append("<td>");
    	sbuf.append(xpath+" = "+object);
    }
    	}
    
    sbuf.append("</td>" + Layout.LINE_SEP);


     sbuf.append("<td title=\"Message\">");
 
     //Added By Vikas Bhadwal to get screenshot on fail. on 16/03/2016
         if(message.contains("FAIL")){  
        	 File f;
    	     if(report_row.contains(currentline))
			{
			 f=map.get(currentline+incremental_value);
			 incremental_value++;
			}
			else
			{
				f= map.get(currentline);
			    report_row.add(currentline);
				}
    	 
            sbuf.append("<FONT COLOR=#FF0000 FACE=AriaL SIZE=2><b>" + Layout.LINE_SEP);
			sbuf.append(event.getRenderedMessage());
			sbuf.append("        <a href=" +f+"><button>   View Screenshot</button></a>");
			sbuf.append("</td>" + Layout.LINE_SEP);
			sbuf.append("</tr>" + Layout.LINE_SEP);
		
  }
     else{

    	   sbuf.append(event.getRenderedMessage());
    	   
    	   sbuf.append("</td>" + Layout.LINE_SEP);
    	   sbuf.append("</tr>" + Layout.LINE_SEP);

    	    }
    
     
     /* Modified by Pooja Sharma on 08 January, 2014
      * Added code to write message in Red colour,on the Log file.
      * */
     
     
    if(message.equals(msg)){                  // if the message received is 'Please Remove the duplicate entries',then it will write the -
    	                                      //- message in Red colour on the log File
    	
    	sbuf.append("<FONT COLOR=#AF1E2D FACE=AriaL SIZE=2><b>" + Layout.LINE_SEP);
    	sbuf.append(event.getRenderedMessage());
    	sbuf.append("</td>" + Layout.LINE_SEP);
        sbuf.append("</tr>" + Layout.LINE_SEP);
    }
    

    if (event.getNDC() != null) {
      sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : xx-small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">");
      sbuf.append("NDC: " + Transform.escapeTags(event.getNDC()));
      sbuf.append("</td></tr>" + Layout.LINE_SEP);
    }

    String[] s = event.getThrowableStrRep();
    if(s != null) {
      sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : xx-small;\" colspan=\"6\">");
      appendThrowableAsHTML(s, sbuf);
      sbuf.append("</td></tr>" + Layout.LINE_SEP);
    }

    return sbuf.toString();
  }
  

 	/** Setter for timestamp format. Called if log4j.appender.<category>.layout.TimestampFormat property is specfied */  

	public void setTimestampFormat(String format)   
	{  
	    this.timestampFormat = format;  
	this.sdf = new SimpleDateFormat(format); // Use the format specified by the TimestampFormat property  
	}  

	/** Getter for timestamp format being used. */  

	public String getTimestampFormat()  
	{  
	return this.timestampFormat;  
	}  

 public  void appendThrowableAsHTML(String[] s, StringBuffer sbuf) {
    if(s != null) {
      int len = s.length;
      if(len == 0)
	return;
      sbuf.append(Transform.escapeTags(s[0]));
      sbuf.append(Layout.LINE_SEP);
      for(int i = 1; i < len; i++) {
	sbuf.append(TRACE_PREFIX);
	sbuf.append(Transform.escapeTags(s[i]));
	sbuf.append(Layout.LINE_SEP);

      }
    }
  }

  /**
     Returns appropriate HTML headers.
  */
  public
  String getHeader() {
    StringBuffer sbuf = new StringBuffer();
    sbuf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"  + Layout.LINE_SEP);
    sbuf.append("<html>" + Layout.LINE_SEP);
    sbuf.append("<head>" + Layout.LINE_SEP);

    sbuf.append("<title>" + title + "</title>" + Layout.LINE_SEP);
    sbuf.append("<style type=\"text/css\">"  + Layout.LINE_SEP);
    sbuf.append("<!--"  + Layout.LINE_SEP);
    sbuf.append("body, table {font-family: arial,sans-serif; font-size: x-small;}" + Layout.LINE_SEP);
    sbuf.append("th {background: #336699; color: #FFFFFF; text-align: left;}" + Layout.LINE_SEP);
    sbuf.append("-->" + Layout.LINE_SEP);
    sbuf.append("</style>" + Layout.LINE_SEP);
    sbuf.append("</head>" + Layout.LINE_SEP);
    sbuf.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">" + Layout.LINE_SEP);
    sbuf.append("<hr size=\"1\" noshade>" + Layout.LINE_SEP);
    sbuf.append("Log session start time " + new java.util.Date() + "<br>" + Layout.LINE_SEP);
    sbuf.append("<br>" + Layout.LINE_SEP);
	if(!(testSuite==null))
    {
		
		sbuf.append("<table align='Center'>"+Layout.LINE_SEP);
	 //"<tr><td align='Center'><h5 align='Center'><FONT COLOR=980000 FACE=AriaL SIZE=4><b><u>Modules Run Mode Status of "+ testSuite + "</u></b></h5></td></tr><tr>");
	sbuf.append("<tr>" + Layout.LINE_SEP);
	sbuf.append("<td align='Center'><h5 align='Center'><FONT COLOR=980000 FACE=AriaL SIZE=4><b><u>Modules Run Mode Status of "+ testSuite + "</u></b></h5></td>"+Layout.LINE_SEP);
    sbuf.append("<br>" + Layout.LINE_SEP);	
	
	sbuf.append("<table border=1 width=800 bgcolor=D8D8D8 align='Center'>"+Layout.LINE_SEP);
	sbuf.append("<tr>"+Layout.LINE_SEP);
	sbuf.append("<th width=50><align=left>S.NO</th>"+Layout.LINE_SEP);
	sbuf.append("<th width=400>TestCase Runmode Status</th>"+Layout.LINE_SEP);
	sbuf.append("</tr>" + Layout.LINE_SEP); 
	sbuf.append(getRunmodeCount(testSuite));
	sbuf.append("<br>" + Layout.LINE_SEP);
	
    }

    sbuf.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">" + Layout.LINE_SEP);
    sbuf.append("<br>" + Layout.LINE_SEP);
    sbuf.append("<tr>" + Layout.LINE_SEP);
    sbuf.append("<th>Time</th>" + Layout.LINE_SEP);
     sbuf.append("<th>Line No/KeyWord</th>" + Layout.LINE_SEP);
     sbuf.append("<th>Object/Value</th>" + Layout.LINE_SEP);
    if(locationInfo) {
      sbuf.append("<th>File:Line</th>" + Layout.LINE_SEP);
    }
    sbuf.append("<th>Message</th>" + Layout.LINE_SEP);
    sbuf.append("</tr>" + Layout.LINE_SEP);
    return sbuf.toString();
  }
/*
 * Added By Mayank Saini 25/07/2014
 * 
 * This will create the table at top of the log
 * **/
  
   private String getRunmodeCount(String testSuite) {
	  StringBuffer sbf=new StringBuffer();
	   for (int testCaseID = 2; testCaseID <= testInputReader.getRowCount("Test Cases"); testCaseID++) {
			String testCase = testInputReader.getCellData(Constants.TEST_CASES_SHEET, Constants.TCID, testCaseID) + " -- " + testInputReader.getCellData("Test Cases", "Runmode", testCaseID);
			
				sbf.append("<tr>"+Layout.LINE_SEP);
				sbf.append("<td width=50 align='Center'><FONT COLOR=000066 FACE=AriaL SIZE=4>" +Layout.LINE_SEP);
				sbf.append(	(testCaseID - 1) + ".");
				sbf.append("</td>" + Layout.LINE_SEP);
				sbf.append("<td width=400>" +Layout.LINE_SEP);
				sbf.append( testCase );
				sbf.append("</td>" + Layout.LINE_SEP);
			    sbf.append("</tr>" + Layout.LINE_SEP);
			

   }
	  return sbf.toString();
   }


public
  boolean ignoresThrowable() {
    return false;
  }
}
