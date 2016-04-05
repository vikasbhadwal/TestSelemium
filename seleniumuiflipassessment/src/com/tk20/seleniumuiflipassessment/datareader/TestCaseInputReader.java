
package com.tk20.seleniumuiflipassessment.datareader;


import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tk20.seleniumuiflipassessment.base.Constants;


public class TestCaseInputReader {
	public  String path;
	public static String orPath;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row   =null;
	private Cell cell = null;
	public static ArrayList<String> nullColumnSheetName =new ArrayList<String>(); //Added By Karan Sood
	
	
	public TestCaseInputReader(String path) {

		this.path=path;
		try {
			fis = new FileInputStream(path);
			//Reader reader = new InputStreamReader( fis, "UTF-8");
			workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}

	}



	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
			String sValue=null;
			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);

			if(cell==null)
				return "";
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			{
				sValue=cell.getStringCellValue();
				return sValue;
			}
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC ){
				
				String cellText  = String.valueOf((new BigDecimal(cell.getNumericCellValue())));

				String[] cellvalue=cellText.split("\\.");
				String value=cellvalue[0];
				return value;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return ""; 
			else 
				return String.valueOf(cell.getBooleanCellValue());

		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	}
	// returns the data from a cell
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC ){
				String cellText  = String.valueOf(cell.getNumericCellValue());
				String[] cellvalue=cellText.split("\\.");
				String value=cellvalue[0];
				return value;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
			fis = new FileInputStream(path); 
			workbook = WorkbookFactory.create(fis);

			if(rowNum<=0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;


			sheet = workbook.getSheetAt(index);


			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);

			cell = row.getCell(colNum);	
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
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

	// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		
		try{				
			fis = new FileInputStream(path); 
			workbook = WorkbookFactory.create(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			sheet=workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			if(row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


	// find whether sheets exists	
	public boolean isSheetExist(String sheetName){

		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;

		return row.getLastCellNum();
	}

	// to run this on stand alone
	public static void main(String arg[]) throws IOException{
		//System.out.println(filename);
		TestCaseInputReader datatable = null;
		datatable = new TestCaseInputReader("E:\\Framework\\src\\Framework_XL_Files\\Controller.xlsx");
		for(int col=0 ;col< datatable.getColumnCount("TC5"); col++){
			System.out.println(datatable.getCellData("TC5", col, 1));
		}
	}

	/**
	 * This Method deletes all the Result Columns from the corresponding Suite
	 * 
	 * @author Pankaj Sharma
	 * @since 26 Feb, 2014
	 * 
	 * @param sheetName : Name of the sheet i.e. test steps should be passed as sheetName parameter
	 * 
	 * @param colName : Name of the column i.e Result is passed as a colName parameter
	 **/
	public void deleteResultColumns(String sheetName,String colName){
		try{

			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				
				if(row.getCell(i).getStringCellValue().trim().contains(colName.trim())){
					col_Num=i;
					java.util.Iterator<Row> rowIter= sheet.iterator();
					cell = row.getCell(col_Num);
					if(cell!=null){
						row.removeCell(cell);
					}
					while (rowIter.hasNext()) {
						row=rowIter.next();
						cell = row.getCell(col_Num);
						if(cell!=null){
							row.removeCell(cell);
						}
					}
					row=sheet.getRow(0);
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();	
		}			
		catch(Exception e){

		}
	}

	/** Mayank Saini 06/05/2014
	 *	This method set the OR path if the sheet is directly under module name folder
	 */

	public void setORPath(String suiteName,String moduleName)
	{
		String testSuite=suiteName.split("_Suite")[0];
		orPath=System.getProperty("user.dir")+"/externalFiles/Object_Repository/"+moduleName+"/"+testSuite+"_OR.properties";
	}
	/** Mayank Saini 06/05/2014
	 *	This method set the OR path if the sheet is further inside any directory of module name directory
	 */
	public void setORPath(String suiteName,String moduleName,String folder)
	{
		String testSuite=suiteName.split("_Suite")[0];
		orPath=System.getProperty("user.dir")+"/externalFiles/Object_Repository/"+moduleName+"/"+folder+"/"+testSuite+"_OR.properties";
	}
	/** Mayank Saini 06/05/2014
	 *	This method return the OR path of currently running suite
	 */

	public String getORPath()
	{
		return orPath;
	}
	/** Mayank Saini 06/05/2014
	 *	This method return the xls path depending on the module running
	 */

	public  String getXlSPath(String modulePath,String suiteName)
	{

		String filePath=modulePath+File.separator+suiteName;

		String arr[]=modulePath.split("/");
		int arrSize = arr.length;
		String moduleName = arr[arrSize - 1];
		File file=new File(filePath);

		if(file.exists())
		{
			setORPath(suiteName, moduleName);
			return filePath;
		}
		else
		{
			File dir=new File(modulePath);
			File[] allFiles=dir.listFiles();

			for(int i=0; i<allFiles.length;i++)
			{

				if(allFiles[i].isDirectory())
				{
					File[] mylookupFiles=allFiles[i].listFiles();
					for(int j=0;j<mylookupFiles.length;j++)
					{
						if(mylookupFiles[j].getName().equals(suiteName))
						{

							String	folder=allFiles[i].getName();
							String path2=modulePath+"/"+folder+"/"+suiteName;
							setORPath(suiteName, moduleName,folder);
							return path2;
						}

					}
				}


			}
		}
		return null;
	}
	
	/**
	* Added by Karan Sood on 3rd July 2014
	* This Method will return all Column Names of the DataSheet we passed as a parameter while
	* Calling this Method.
	*  **/

	public ArrayList<String> getAllDataColumnName(String sheetName)
	{		
	    ArrayList<String> allValues=new ArrayList<String>();
	    try {
	        int index = workbook.getSheetIndex(sheetName);
	        if (index == -1)
	            return allValues;

	        sheet = workbook.getSheetAt(index);
	        row = sheet.getRow(0);
	       
	           for (int i = 0; i < row.getLastCellNum(); i++)
	           {
	               Cell cell=row.getCell(i);
	               String s =cell.getStringCellValue().trim();
	               allValues.add(s);
	           }
	      	 }
	    catch (NullPointerException ne)
        {	    	
	    	nullColumnSheetName.add(sheetName);  //Added By Karan on 6th April 2015
        }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    return allValues;
	}
	
	
	/**
	 * @date 23rd July 2014
	 * @author Sanjay Sharma
	 * @param sheetName sheet name
	 * @param colName column name
	 * @return This Method will return all KeyWords(Methods) of the xls sheet.
	 */
	public Set<String> getAllKeywordsFromXls(String sheetName, String colName, String xls_Suite ,int total_suites ,int suiteid) {
		Set<String> methods = new HashSet<String>();
		int flag=0;	
		try {
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if(index == -1)
			{
				flag=1;
				logger.debug("Subsheet , "+"<b>'"+sheetName+"'</b>"+ "  is not properly named inside : "+xls_Suite+ ".xls");
			}	
			if((suiteid==total_suites)&&(flag==1)){
				StringBuffer pop_msg=new StringBuffer();
				pop_msg.append("Subsheet name is not correct");
				pop_msg.append("\n"); 
				pop_msg.append("       Please Refer Application.html file for More Details");
				JOptionPane.showMessageDialog(null,pop_msg);
				Thread.currentThread().stop();	
			}


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					java.util.Iterator<Row> rowIter = sheet.iterator();
					rowIter.next();
					while (rowIter.hasNext()) {
						row = rowIter.next();
						cell = row.getCell(col_Num);
						if (cell == null)
							continue;
						String methodName = cell.getStringCellValue().trim();

						if (!StringUtils.isEmpty(methodName))
							methods.add(methodName);
					}
					break;
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return methods;
	}
	  /**
     * @date 23rd July 2014
     * @author Sanjay Sharma
     * @param sheetName sheet name
     * @param colName column name
     * @return This Method will return all Objects(xpaths) of the xls sheet.
     */
    @SuppressWarnings({ "deprecation", "deprecation" })
    public Set<String> getAllXPathsFromXls(String sheetName, String colName, String xls_Suite , int total_suites, int suiteid) {
        Set<String> xpaths = new HashSet<String>();
        int flag=0;
        try {
            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if(index == -1)
            {
                flag=1;
                logger.debug("Subsheet , "+"<b>'"+sheetName+"'</b>"+ "  is not properly named inside : "+xls_Suite+ ".xls");
            }
            if((suiteid==total_suites)&&(flag==1)){
                StringBuffer pop_msg=new StringBuffer();
                pop_msg.append("Subsheet name is not correct");
                pop_msg.append("\n");
                pop_msg.append("             Please Refer Application.html file for More Details");
                JOptionPane.showMessageDialog(null,pop_msg);
                Thread.currentThread().stop();   
            }           
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
                    col_Num = i;
                    java.util.Iterator<Row> rowIter = sheet.iterator();
                    rowIter.next();
                    while (rowIter.hasNext()) {
                        row = rowIter.next();
                        cell = row.getCell(col_Num);
                        if (cell == null)
                            continue;
                        String xpathName = cell.getStringCellValue().trim();
                        if (!StringUtils.isEmpty(xpathName)) {
                            if (xpathName.contains(Constants.Object_SPLIT) || (xpathName.contains("|"))) { //Added code to check objects with "|"
                                xpathName=    xpathName.replace("|", ",");
                                String multipleXpaths[] = xpathName.split(Constants.Object_SPLIT);
                                for (int l = 0; l < multipleXpaths.length; l++)
                                    xpaths.add(multipleXpaths[l].trim());
                            } else
                                xpaths.add(xpathName);
                        }
                    }
                    break;
                }
            }       
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return xpaths;   
    }

    /**
	* Added by Karan Sood on 6th April 2015
	* This Method will return the Number of SpreadSheets in a sheet
	**/
 	public int getSpreadSheetsCount(String sheetName){
 		if(sheetName=="")
 			return 0;
 		else{
 			int number=workbook.getNumberOfSheets();
 			return number;
 			}
 		}
 	 	
 	
 	 	/**
 		* Added by Karan Sood on 6th April 2015
 		* This Method will return the Sheet Name at given Index
 		**/
 	public String getSheetNameAtIndex(int sheetIndex){
 		if(sheetIndex== -1)
			return "";
 			else{
 			String sheetName=workbook.getSheetName(sheetIndex);
 			return sheetName;
 			}
 		}
    
}

