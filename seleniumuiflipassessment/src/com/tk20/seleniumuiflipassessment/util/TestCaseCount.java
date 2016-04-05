package com.tk20.seleniumuiflipassessment.util;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.tk20.seleniumuiflipassessment.datareader.TestCaseInputReader;
import com.tk20.seleniumuiflipassessment.base.Constants;

public class TestCaseCount{
	static String files=null;
	static String current_folder=null;
	int count_per_submodule=0;
	static FileWriter filewriter;
	static BufferedWriter  bufferWriter;
	static String dirpath = "";
	static int  automatedCases=0;
	static TestCaseInputReader currentSuite;
	static	String tc_pattern=".*[tT][cC].*-*.*\\d+(.*\\n*.*)*";
	static String Comma_Pattern=".*\\[+.*[tT][cC].*\\d+.*,+.*\\d+.*\\]+(.*\\n*.*)*";
    static int current_module;
	int flag=1;
	static String name_pattern;
	static	int name_index=0;
    static String  suite_names[]=new String[100];
	public static Boolean matchPattern(String Desc,String tc_pattern)
	{
		String pattern=tc_pattern;
		Boolean t=Desc.matches(pattern);
		return t;
	}
	 
	int change_color=0;


public void countCases(String directoryName) throws IOException{
      current_module=0;
      File directory = new File(directoryName);
      current_folder=directory.toString();
      File[] fList = directory.listFiles();
      int index=1;
       for (int i = 0; i < fList.length; i++){
		  if (fList[i].isFile()) {
	       files = fList[i].getName();
		   if (files.endsWith(".xls") || files.endsWith(".XLS")){
			   String filespath=fList[i].getAbsolutePath();
    	       String address= "\"" +filespath + "\"";
               int suite_wise_count=0;
               currentSuite=new TestCaseInputReader(filespath);
                                        if(change_color%2==0){
                                        	bufferWriter.write("<tr bgcolor=#E6E6FA>");
                                        	}
                                         	else{
                                         		bufferWriter.write("<tr bgcolor=#E0FFFF>");
                                        		}
                                              if(!files.equals("Suite.xls")){                                     	
                                               bufferWriter.write("<td width=30 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + index + "." + "</td>");
                                        	   index++;
	                                           bufferWriter.write("<td width=400><a target='_blank' href="+ address +">" +  files+ " </a></td>");
                                        	}
	int rows= currentSuite.getRowCount(Constants.TEST_STEPS_SHEET);
	for(int rowNum=2;rowNum<=rows;rowNum++){
		  	int commas=0;
			String TC_DESCRIPTION=currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum);
		    if(matchPattern(TC_DESCRIPTION,Comma_Pattern) && currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals("")){
			       if((!currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum-1).trim().equals("")) &&(!matchPattern(currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)))
				   {
					 for(int ix=0;i<TC_DESCRIPTION.length();ix++)
					   {
						if(TC_DESCRIPTION.charAt(ix)==',')
						commas++;
						if(TC_DESCRIPTION.charAt(ix)==']')
						break;
					}
					if(commas>0)
						suite_wise_count=suite_wise_count+commas;
				}
			}
			 if(matchPattern(TC_DESCRIPTION,tc_pattern)&& currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum).trim().equals(""))
			{
				if(!(matchPattern(currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.DESCRIPTION,rowNum-1),tc_pattern)&&currentSuite.getCellData(Constants.TEST_STEPS_SHEET,Constants.TCID,rowNum-1).trim().equals("")))
				{
					suite_wise_count=suite_wise_count+1;
				}
			}
	}
 	bufferWriter.write("<td width=400>" + suite_wise_count + "</td></tr>");
	System.out.println(files +"  : "  + suite_wise_count);
    automatedCases+= suite_wise_count;
    current_module+=suite_wise_count;
    }
      
   } 
	
     else if (fList[i].isDirectory()){
    	 files = fList[i].getName();
    	 String filespath=fList[i].getAbsolutePath();
          if(!files.contains("CVS")){
    		System.out.println(files);
    	if(current_module!=0)
    	{
    		try{ 
    		
    		 bufferWriter.write("<tr><td width=400>" + "" + "</td>");
        	 bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + "<b>Total</b>" + "." + "</td>");
        	 
        	 if(current_module==0)
        	 {
        	 bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + "" + "</td></tr>");
        	 
        	 }else
        	 {
        		 
        		 bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + "<b>" +(current_module) + "</b>"  + "</td></tr>");
        		 
        	 }
        
    	} 
    		catch (Exception e) {
    			System.out.print("Problem with file   "+files);
    		}
        	 
    		 bufferWriter.write("<tr bgcolor=#ABA1FF><td width=400>" + "" + "</td>");
    	 bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + files + "." + "</td>");
    	 bufferWriter.write("<td width=400>" + "" + "</td></tr>");
    	 change_color++;
    	}
    	
 	}
    	 
    	 flag++;
    	 if(!files.contains("CVS"))
    		 countCases(filespath);
    	 
    	 }
}

}

public static void main (String[] args){
	
	TestCaseCount testCount = new TestCaseCount();
	 System.out.println("Please give the directory path:");
	 Scanner scanner = new Scanner(System.in);
	  String directoryWindows = scanner.nextLine();
	  File check_FILE = new File(directoryWindows);
	  if (!(check_FILE.isDirectory())) {
		  
		  System.out.println("No directory Exist for given directory path");
	     System.exit(0);
	  }
	
	 String path;
     String moduleName;
     String module[];
     File directory_name;
     File f1;
     module=directoryWindows.split("xls");
     if(module.length!=1)
     {
	   path=module[0];
	 moduleName=module[1];
	 moduleName= moduleName.replace("\\", "");
     directory_name = new File("" + path + "/HTML_File");
	 f1 = new File("" + path + "/HTML_File/" + moduleName + "_count.html");
     }
	
     else{
    	    path=module[0];
    	    moduleName="Lotus";
            directory_name = new File("" + path + "/HTML_File");
    	    f1 = new File("" + path + "/HTML_File/" + " Assessment" + "_count.html");
    	
     }
     
directory_name.mkdir();
try {
    f1.createNewFile();
   
    filewriter = new FileWriter(f1.getAbsoluteFile());
bufferWriter = new BufferedWriter(filewriter);
bufferWriter.write("<html><HEAD></META><TITLE>Test Cases Count</TITLE></HEAD>");
bufferWriter.write("<body><table align='Center'><tr><td align='Center'><h5 align='Center'><FONT COLOR=980000 FACE=Cambria SIZE=4><b><u>Test Case Count " +moduleName+ "</u></b></h5></td></tr><tr>");
bufferWriter.write("<table border=1 width=800 bgcolor=D8D8D8 align='Center'>");
bufferWriter.write("<tr bgcolor=3399FF><th width=30><align=left><FONT COLOR=000066 FACE=Cambria SIZE=4>S.NO</th>");
bufferWriter.write("<th width=400><FONT COLOR=000066 FACE=Cambria SIZE=4>Suite Name</th>");
bufferWriter.write("<th width=400><FONT COLOR=000066 FACE=Cambria SIZE=4>Test count</th></tr>");

testCount.countCases(directoryWindows);

System.out.println("" + automatedCases);
bufferWriter.write("<tr><td width=400>" + "" + "</td>");
bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=3>" + "<b>Total</b>" + "." + "</td>");

if(current_module==0)
{
bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=3>" + "" + "</td></tr>");

}else
{
	 
	 bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=3>" + "<b>"+(current_module)+"</b>"   + "</td></tr>");
	 
}

bufferWriter.write("<tr><td width=400>" + "" + "</td>");
bufferWriter.write("<td width=50 align='Center'><FONT COLOR=000066 FACE=Cambria SIZE=4>" + "<b>Gross Total</b>" + "." + "</td>");
bufferWriter.write("<td width=400>" + "<b>"+automatedCases +"</b>"+ "</td>");
bufferWriter.write("</tr></table></tr>");
bufferWriter.write("</table></body></html>");
bufferWriter.close();
Desktop.getDesktop().open(f1);

}

catch(Exception ex)
{
	System.out.print("Problem with file \""+files+ "\" at location " + current_folder);
}
}

}