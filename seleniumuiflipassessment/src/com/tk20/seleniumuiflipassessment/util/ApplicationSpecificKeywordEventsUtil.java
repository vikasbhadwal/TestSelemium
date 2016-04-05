
package com.tk20.seleniumuiflipassessment.util;

import static com.tk20.seleniumuiflipassessment.base.DataProvider.CONFIG;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.OR;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.logger;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.moduleName;
import static com.tk20.seleniumuiflipassessment.base.DataProvider.accessibilityKeyEvents;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.driver;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForAlert;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitWaitForElement;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explicitwaitTime;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementList;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementSize;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementUsingFluent;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.pollingTime;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.wait;
import static com.tk20.seleniumuiflipassessment.util.ReportUtil.result_FolderName;
import static com.tk20.seleniumuiflipassessment.util.KeywordEventsUtill.explictWaitForElementListByLinkText;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlink;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation.PdfImportedLink;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.base.DataProvider;
import com.tk20.seleniumuiflipassessment.datareader.TestCaseInputReader;

/**
 * @since 10/23/2013
 * @author Tarun Sharma
 * 
 *         This class contains the application specific methods which are common
 *         for other modules.
 * 
 */
public class ApplicationSpecificKeywordEventsUtil {
	private Pattern patternTag;
	private Matcher matcherTag;
	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_TAG_PATTERN = "<('[^']*'|'[^']*'|[^''>])*>";
	String linkText;
	String allrowsCount="";
	String number = "";
	KeywordEventsUtill keyUtil = (KeywordEventsUtill) KeywordEventsUtill
			.keyUtillFactory();
	String allUsersCount = "";
	String username = "";
	BufferedWriter bw = null;
	int Ycount = 0;
	int Ncount = 0;
	boolean Flag = true;
	private RandomAccessFile randomAccess;
	private BufferedReader br;
	private BufferedReader common_br;
	private BufferedReader common_br2;
	String dirName = System.getProperty("user.dir") + File.separator
			+ "externalFiles" + File.separator + "downloadFiles";
	List<String> resultList = new ArrayList<String>();
	File Folder_File = null;
	List<WebElement> allRows = new ArrayList<WebElement>();
	List<WebElement> allCBs = new ArrayList<WebElement>();
	List<String> allDropdownElements=new ArrayList<String>();	
	Date currentMonth=null;
	Date currentYear=null;    
	
	/*
	 * 2/13/13 Balkrishan Bhola This method is used to click on the Current FE
	 * by matching Student name and FE name and then clicks on corresponding
	 * Student name.
	 */

	public String clickOnCurrFE(String object, String data)
			throws InterruptedException {
		int rows = explictWaitForElementSize(object);
		logger.debug("Entered into clickOnCurrFE()");
		try {
			for (int i = 2; i <= rows; i++) {
				String feName = driver.findElement(
						By.xpath(OR.getProperty(object)
								+ OR.getProperty("fe_table_start") + i
								+ OR.getProperty("fe_prev_fe_name_end")))
								.getText();
				String actual_fe_name = OR.getProperty("fe_name");
				logger.debug("TEXT " + feName);
				String stuName = driver.findElement(
						By.xpath(OR.getProperty(object)
								+ OR.getProperty("fe_table_start") + i
								+ OR.getProperty("fe_prev_fe_stu_nam_end")))
								.getText();
				logger.debug("TEXT " + stuName);
				if (feName.equals(actual_fe_name) && stuName.equals(data)) {
					driver.findElement(
							By.xpath(OR.getProperty(object)
									+ OR.getProperty("fe_table_start")
									+ i
									+ OR.getProperty("fe_prev_fe_stu_nam_lnk_end")))
									.click();
					return Constants.KEYWORD_PASS;
				}
			}
		} catch (NoSuchElementException nse) {
			// TODO Auto-generated catch block
			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		}
		return Constants.KEYWORD_FAIL + " -- could not click on fe";
	}

	/*
	 * Date:07/11/2013 Anil Reddy: selectCourseEvalForm method is used to select
	 * course evaluation form for Course Evaluation Tab.
	 */

	public String selectCourseEvalForm(String object, String data) {
		logger.debug("selecting cou eval form.....");
		try {

			List<WebElement> formName = explictWaitForElementList(object);
			List<WebElement> radioButtons = explictWaitForElementList("ce_cou_eval_form_rb_rows");
			boolean flag = false;
			String temp = data.trim();
			for (int i = 0; i < formName.size(); i++) {
				String form_name = formName.get(i + 1).getText().trim();
				if (form_name.equals(temp)) {
					radioButtons.get(i).click();
					flag = true;
					break;
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + "--" + data
						+ "-- is found to select";
			}
		} catch (NoSuchElementException nse) {
			// TODO Auto-generated catch block
			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		}
		return Constants.KEYWORD_FAIL + "--" + data
				+ "-- is not found to select";
	}

	/**
	 * 
	 * This method enters tomorrow's date in the date input. <br>
	 * It enters the date according to server time.
	 * 
	 * @author Tarun Sharma <br>
	 * @since 20 August, 2013 <br>
	 * 
	 * @param object
	 *            which is xpath of the input where date has to be entered.
	 * 
	 * @param data
	 *            no data is required.
	 * 
	 * @return <b>PASS</b> on entering the tomorrow's date
	 */
	public String enterTomorrowDate(String object, String data) {
		logger.debug("Entering Tomorrow's date");
		try {

			int flag = 0;
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			calendar.add(Calendar.DATE, 1);
			String dueDate = timeFormat.format(calendar.getTime());
			wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))))
			.sendKeys(dueDate);
			flag = 1;
			if (flag == 1) {
				return Constants.KEYWORD_PASS + "--dueDate has been Entered.. ";
			} else {
				return Constants.KEYWORD_FAIL
						+ "--dueDate has not been Entered.. ";
			}
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * @since 10/30/13
	 * @author Balkrishan Bhola This method Saves All the UserGroups
	 */
	public String countAllUsers(String object, String data) throws Exception {
		logger.debug("Entered into countAllUsers()");
		try {

			if (allUsersCount.length() != 0) {
				allUsersCount = "";
			}
			String[] allExpectedUsers = wait
					.until(explicitWaitForElement(By.xpath(OR
							.getProperty(object)))).getText()
							.split(OR.getProperty("OF_SEPARATOR"));
			allUsersCount = allExpectedUsers[1].trim();
			return Constants.KEYWORD_PASS + " -- All Users have been Stored";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 10/30/13
	 * @author Balkrishan Bhola This method Saves All the UserGroups
	 */
	public String verifyAllUsersCount(String object, String data)
			throws Exception {
		logger.debug("Entered into verifyAllUsersCount()");
		try {

			String[] allExpectedUsers = wait
					.until(explicitWaitForElement(By.xpath(OR
							.getProperty(object)))).getText()
							.split(OR.getProperty("OF_SEPARATOR"));
			if (allExpectedUsers[1].trim().equals(allUsersCount.trim())) {
				return Constants.KEYWORD_PASS + " -- All Users present";
			} else {
				return Constants.KEYWORD_FAIL
						+ " -- All Users count not matched";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 11/08/13
	 * @author Balkrishan Bhola This method Saves the username
	 */
	public String saveFirstUsername(String object, String data)
			throws Exception {
		logger.debug("Entered into saveFirstUsername()");
		try {

			if (username.length() != 0) {
				username = "";
			}
			List<WebElement> allNames = explictWaitForElementList(object);
			for (WebElement webElement : allNames) {
				username = webElement.getText().trim();
				logger.debug("Username:-" + username);
				break;
			}
			return Constants.KEYWORD_PASS + " -- " + username
					+ " has been saved";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 11/08/13
	 * @author Balkrishan Bhola This method verifies the username/data
	 */
	public String verifyFirstUsername(String object, String data)
			throws Exception {
		logger.debug("Entered into verifyFirstUsername()");
		try {
			String result = keyUtil.verifyTextinInput(object, username);
			if(result.contains("null"))// modify by sanjay on 07/05/2015
			{
				result = keyUtil.verifyText(object, username);
			}
			if (result.contains(Constants.KEYWORD_PASS)) {
				return Constants.KEYWORD_PASS + " -- " + username + " present";
			} else {
				return Constants.KEYWORD_FAIL + " -- " + username
						+ " not present";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 11/08/13
	 * @author Balkrishan Bhola This method Saves the username
	 */
	public String verifySearchedUsername(String object, String data)
			throws Exception {
		logger.debug("Entered into verifySearchedUsername()");
		try {
			String[] usernamePid = username.split(Constants.Name_SPLIT);
			String pid = usernamePid[2];

			String result = keyUtil.verifyElementIsPresent(object, pid);
			if (result.contains(Constants.KEYWORD_PASS)) {
				return Constants.KEYWORD_PASS + " -- " + username + " present";
			} else {
				return Constants.KEYWORD_FAIL + " -- " + username
						+ " not present";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Added By Surender This method is used to count all rows. For Ex : All
	 * roles in Admin Tab > Users >Change Password. It takes the common xpath of
	 * the all rows.
	 * */
	public String countAllRows(String object, String data) throws Exception {
		logger.debug("Entered into countAllRows()");
		try {

			if (allUsersCount.length() != 0) {
				allUsersCount = "";
			}
			int allExpectedUsers = explictWaitForElementSize(object);
			allUsersCount = String.valueOf(allExpectedUsers);
			return Constants.KEYWORD_PASS + " -- Rows Count Stored."
			+ " Count=" + allUsersCount;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Added By Surender This method is used to verify all rows count stored in
	 * above method countAllRows(). For Ex: All roles with checkboxes
	 * corresponding to them in Admin Tab > Users >Change Password.
	 * */
	public String verifyAllRows(String object, String data) throws Exception {
		logger.debug("Entered into verifyAllRows()");
		try {

			int allExpectedUsers = explictWaitForElementSize(object);
			if (allExpectedUsers == Integer.parseInt(allUsersCount)) {
				return Constants.KEYWORD_PASS + " -- Rows Count Verified."
						+ " Count=" + allExpectedUsers;
			} else {
				return Constants.KEYWORD_FAIL + " -- Rows Count not matched."
						+ " Count=" + allExpectedUsers;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Added By Vikas Bhadwal This method is used to verify RadioButton is
	 * present corresponding to all searched role It takes the common xpath of
	 * the all RadioButton
	 **/

	public String verifyAllUsersWithRadioButton(String object, String data)
			throws Exception {
		logger.debug("Entered into verifyAllRolesWithCheckbox()");
		try {
			int user_with_radio_button_count = 0;
			int allUsers_Count = Integer.parseInt(allUsersCount);

			user_with_radio_button_count = explictWaitForElementSize(object);
			while (true) {

				int size = driver.findElements(By.linkText("Next >")).size();

				if (size > 0) {

					driver.findElement(By.linkText("Next >")).click();
					user_with_radio_button_count += driver.findElements(
							By.xpath(OR.getProperty(object))).size();

				} else {

					user_with_radio_button_count += driver.findElements(
							By.xpath(OR.getProperty(object))).size();

				}
				if (driver.findElements(By.linkText("Next >")).size() == 0) {
					break;
				}
			}

			if (allUsers_Count == user_with_radio_button_count) {

				return Constants.KEYWORD_PASS
						+ " -- Radio button is present corresponding to each All Users "
						+ " " + user_with_radio_button_count;
			} else {
				return Constants.KEYWORD_FAIL
						+ " -- Radio button is not present corresponding to each All Users";

			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Created by Sandeep Dhamija, 3 January, 2013
	 * 
	 * @param object
	 *            - common xpath of html <td>attributes of all the searched
	 *            results
	 * @param data
	 *            - First name of user to be searched
	 * @return - PASS if all the searched results contain same First name as
	 *         passed in 'data' parameter
	 * @return - FAIL if all the searched results do not contain same First name
	 *         as passed in 'data' parameter
	 */

	public String verifySearchByFirstName(String object, String data) {
		logger.debug("inside verifySearchByFirstName");
		try {

			List<WebElement> search_Result = explictWaitForElementList(object);
			if (search_Result.size() == 0) {
				return Constants.KEYWORD_FAIL
						+ " no users found. Enter a correct name";
			}

			String expected_First_Name = data.trim().toLowerCase();
			for (int i = 0; i < search_Result.size(); i++) {
				String searched_Name = search_Result.get(i).getText();
				String actual_First_Name = searched_Name.split(",")[1].trim()
						.toLowerCase();
				logger.debug("expected first name- " + expected_First_Name);
				logger.debug("actual first name- " + actual_First_Name);
				if (actual_First_Name.contains(expected_First_Name)) {
					continue;
				} else {
					return Constants.KEYWORD_FAIL + "expected first name- "
							+ expected_First_Name + " actual first name- "
							+ actual_First_Name;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Created by Sandeep Dhamija, 3 January, 2013
	 * 
	 * @param object
	 *            - common xpath of html <td>attributes of all the searched
	 *            results
	 * @param data
	 *            - Last name of user to be searched
	 * @return - PASS if all the searched results contain same Last name as
	 *         passed in 'data' parameter
	 * @return - FAIL if all the searched results do not contain same Last name
	 *         as passed in 'data' parameter
	 */

	public String verifySearchByLastName(String object, String data) {
		logger.debug("inside verifySearchByLastName");
		try {

			List<WebElement> search_Result = explictWaitForElementList(object);
			if (search_Result.size() == 0) {
				return Constants.KEYWORD_FAIL
						+ " no users found. Enter a correct name";
			}

			String expected_Last_Name = data.trim().toLowerCase();
			for (int i = 0; i < search_Result.size(); i++) {
				String searched_Name = search_Result.get(i).getText();
				String actual_Last_Name = searched_Name.split(",")[0].trim()
						.toLowerCase();
				logger.debug("expected last name- " + expected_Last_Name);
				logger.debug("actual last name- " + actual_Last_Name);
				if (actual_Last_Name.contains(expected_Last_Name)) {
					continue;
				} else {
					return Constants.KEYWORD_FAIL + "expected last name- "
							+ expected_Last_Name + " actual last name- "
							+ actual_Last_Name;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/*
	 * Pallavi singla, 29/10/2013 remove all survey forms pass two arguments in
	 * object, 1st is "select all" checkbox, 2nd is delete/revoke img
	 * Modified by Shweta Gupta ,11/17/2014
	 * Modified method name as deleteItems instead of deleteSurveys 
	 * Modified by Karan Sood on 08/27/2015
	 * Remove Thread.sleep() after isInConsistentAlert and added verifyCheckBoxSelected method
	 */
	public String deleteItems(String object, String data) {

		try {

			String[] objects = object.split(",");
			String checkboxes = objects[0];
			String revoke_img = objects[1];

			while (true) {

				int element_count = explictWaitForElementSize(checkboxes);

			test : if (element_count > 0) {				
					String alreadyChecked=new KeywordEventsUtill().verifyCheckBoxSelected(checkboxes, data);
					if(!(alreadyChecked.contains("FAIL Check box is Not selected")))
						break test;
					new KeywordEventsUtill().checkAllCheckBox(checkboxes, data);
					Thread.sleep(3000);
					wait.until(explicitWaitForElement(By.xpath(OR.getProperty(revoke_img)))).click();
					Thread.sleep(1500);
					new KeywordEventsUtill().isInConsistentAlert("", "");						
				} 
				
			else {
					return Constants.KEYWORD_PASS + " all"+data+" elements have been removed";
				 }
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + "-" + e.getMessage();
		}
	}

	/**
	 * @since 08/Nov/13
	 * @author Karan Sood This method will Pass even if there is no Text box
	 *         displaying to write data in a 2 operation scenario like in 1st
	 *         scenario textbox is displayed and in 2nd scenario textbox is not
	 *         displaying but is present there
	 */

	public String writeInInputIfPresent(String object, String data) {
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try {

			boolean textBoxPresent = false;
			WebElement element = explictWaitForElementUsingFluent(object);
			textBoxPresent = element.isDisplayed();
			if (textBoxPresent == true) {

				element.clear();
				element.sendKeys(data);
				return Constants.KEYWORD_PASS + "--" + "Textbox Found" + data;
			} else {
				return Constants.KEYWORD_PASS
						+ " "
						+ "Input Textbox not found becasue user has selected some other operation";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Unable to write "
			+ e.getLocalizedMessage();
		}
	}

	/**
	 * @since 08/Nov/13
	 * @author Karan Sood This method will Pass even if there is no button
	 *         displaying in a 2 operation scenario like in 1st scenario button
	 *         is displayed and in 2nd scenario button is not displaying but is
	 *         present there
	 */

	public String clickButtonIfPresent(String object, String data) {
		logger.debug("Clicking on button ");
		try {

			boolean elementPresent = false;
			WebElement element = explictWaitForElementUsingFluent(object);
			elementPresent = element.isDisplayed();
			if (elementPresent == true) {
				element.click();
				return Constants.KEYWORD_PASS + " "
				+ "Clicking button displaying";
			} else {
				return Constants.KEYWORD_PASS
						+ " "
						+ "button not displaying because user has selected other scenario ";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on Button"
					+ e.getMessage();
		}
	}

	/*
	 * @since 30/December/2013
	 * 
	 * @author Anil Reddy This method is used to upload either single or
	 * multiple files(Including Videos) at a time by using a third party tool
	 * AutoIt.
	 * 
	 * @param data required is name of the .exe file and file names [
	 * Ex:uploadFilesUsingAutoIt.exe,Desert.jpg bloosom.jpg ]
	 * 
	 * Modified By Mayank Saini solved cross browser issue
	 */

	public String uploadFilesUsingAutoIt(String object, String data)
			throws IOException {
		try {
			String file_multi = "";
			String final_path = "";

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp[] = data.split(",");
			String obj[] = temp[1].split(Constants.DATA_SPLIT);
			String path_exe = currDir + sep + "externalFiles" + sep+ "uploadFiles" + sep + temp[0].trim();

			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles";
			if (CONFIG.getProperty("browserType").equals("Mozilla")) {
				new ProcessBuilder(path_exe, file_exist_path, "File Upload").start();
			} else if (CONFIG.getProperty("browserType").equals("IE")) {
				new ProcessBuilder(path_exe, file_exist_path,"Choose File to Upload").start();
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				new ProcessBuilder(path_exe, file_exist_path, "Open").start();
			}
			Thread.sleep(5000);

			for (int i = 0; i < obj.length; i++) {
				String file_path = obj[i].trim();
				String file = file_path;
				file_multi = file_multi + "\"" + file + "\"";

				final_path = "'" + file_multi + "'";
			}
			if (CONFIG.getProperty("browserType").equals("Mozilla")) {
				new ProcessBuilder(path_exe, final_path, "File Upload").start();
			} else if (CONFIG.getProperty("browserType").equals("IE")) {
				new ProcessBuilder(path_exe, final_path,"Choose File to Upload").start();
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				new ProcessBuilder(path_exe, final_path, "Open").start();
			}

			Thread.sleep(5000);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to upload Files/Videos"
					+ e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "-- Files/Videos Uploaded sucessfully.";
	}

	/**
	 * This method gives the Alert if Suite.xls File is Open or any other xls
	 * file whose entry is <Br>
	 * done in the Suite.xls file
	 * 
	 * @author Pankaj Sharma
	 * @since 22 November, 2013
	 * 
	 * @param object
	 *            : It accepts absolute path of the xls file.
	 * 
	 * @return It Returns an Alert if the xls file is Open ,And If the xls File
	 *         is not Open then Returns nothing. For Example :
	 *         Admin_Automated_Scheduling_Browse_Suite is already Open , First
	 *         Close it .
	 **/
	/*
	 * Modified By : Pankaj Shrama on 06 December ,2013 <Br> Now This Method
	 * also verify if any Entry for any module in config.properties file is
	 * missing or not. If entry is missing then It Gives proper Alert like
	 * "In  config.properties   file  Entry  for  FE Module  is  Missing ."
	 */
	@SuppressWarnings("deprecation")
	public void isFileOpen(String object) {

		logger.debug("Executing the isFileOpen method");
		String objArr[] = object.split("/");
		int arrSize = objArr.length;
		String fileName = objArr[arrSize - 1];
		String actualFolder = objArr[arrSize - 2];
		String Empty = "null";

		/*
		 * This if verify that In config.properties file Entry for module Module
		 * is Missing or not.
		 */
		if (actualFolder.equals(Empty)) {
			String moduleArr[] = fileName.split("_");
			String module = moduleArr[0];
			System.out.println("\n In  config.properties   file  Entry  for  "
					+ module + " Module  is  Missing .");
			JOptionPane.showMessageDialog(null,
					"In  config.properties   file  Entry  for  Module  "
							+ module + "  is  Missing .");
			Thread.currentThread().stop();
		}
		logger.debug("Verify that xls file " + fileName + " is Open or Close ");
		try {
			randomAccess = new RandomAccessFile(object, "rw");
			FileChannel channel = randomAccess.getChannel();
			FileLock lock = channel.lock();
			lock.release();
		} catch (IOException e) {
			// If File is Open
			// e.printStackTrace();
			System.out.println("\n" + fileName
					+ "  is  already  Open  ,  First  Close  it .");
			JOptionPane.showMessageDialog(null, fileName
					+ "  is  already  Open  ,  First  Close  it .");
			Thread.currentThread().stop();
		}
	}

	/*
	 * 9/12/2013 Added By Pallavi Singla This method is used to genarate
	 * executed or skipped status of modules in log Modified By Mayank Saini
	 */
	public void generateModuleStatus(String testCaseName,
			TestCaseInputReader testInputReader, int testCaseID)
					throws IOException {

		if (testInputReader.getCellData("Test Cases", "Runmode", testCaseID)
				.contains("Y")) {
			logger.debug(testCaseName + ":$Executed");
		} else {
			logger.debug(testCaseName + ":$Skipped");
		}

	}

	/**
	 * 06/01/2014 Added By Mayank Saini
	 * 
	 * This method is used to click on mail link shared in the email Provide the
	 * username of email from OR and subject of email as data.
	 * 
	 * @throws InterruptedException
	 * */
	public String checkMailsLink(String object, String data)
			throws IOException, InterruptedException {
		Boolean flag = false;
		String host = "imap.gmail.com";
		String port = "993";
		String userName = OR.getProperty(object);
		String password = "t3stingte@m";
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			// fetches new messages from server
			while (true) {
				Message[] arrayMessages = folderInbox.getMessages();

				for (int i = 0; i < arrayMessages.length; i++) {
					Message message = arrayMessages[i];
					String subject = message.getSubject();

					if (subject.trim().contains(data.trim())) {
						logger.debug("Actual Subject " + subject);
						logger.debug("Expected Subject " + data);
						Multipart mp = (Multipart) message.getContent();
						BodyPart bp = mp.getBodyPart(0);
						String messageContent = bp.getContent().toString();
						patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
						matcherTag = patternTag.matcher(messageContent);
						while (matcherTag.find()) {

							linkText = matcherTag.group(2); // link text

						}
						flag = true;
						message.setFlag(Flags.Flag.DELETED, true);
						break;
					}

				}
				if (flag)
					break;
				else
					Thread.sleep(10000);
			}

			// expunges the folder to remove messages which are marked deleted
			boolean expunge = true;
			folderInbox.close(expunge);
			store.close();
			if (flag) {
				driver.navigate().to(linkText);
				return Constants.KEYWORD_PASS
						+ "Mail Recieved and Clicked on link";
			} else
				return Constants.KEYWORD_FAIL + "Mail not recieved";

		} catch (NoSuchProviderException ex) {
			return Constants.KEYWORD_FAIL + "No Provider";

		} catch (MessagingException ex) {
			return Constants.KEYWORD_FAIL
					+ "Could not connect to the message store.";
		}
	}

	/**
	 * 06/01/2014 Added By Mayank Saini
	 * 
	 * This method is used to check that whether mail is recieved Provide the
	 * username of email from OR and subject of email as data
	 * 
	 * @throws InterruptedException
	 * */

	public String checkMails(String object, String data)
			throws InterruptedException {
		Boolean flag = false;
		String host = "imap.gmail.com";
		String port = "993";
		String userName = OR.getProperty(object);
		String password = "t3stingte@m";
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);
			while (true) {
				// fetches new messages from server
				Message[] arrayMessages = folderInbox.getMessages();

				for (int i = 0; i < arrayMessages.length; i++) {
					Message message = arrayMessages[i];
					String subject = message.getSubject();

					if (subject.trim().contains(data.trim())) {
						logger.debug("Actual Subject " + subject);
						logger.debug("Expected Subject " + data);
						message.setFlag(Flags.Flag.DELETED, true);
						flag = true;
						break;
					}

				}

				if (flag)
					break;
				else
					Thread.sleep(10000);

			} // expunges the folder to remove messages which are marked deleted
			boolean expunge = true;
			folderInbox.close(expunge);
			store.close();
			if (flag)
				return Constants.KEYWORD_PASS
						+ "Mail Recieved and Now its Deleted";
			else
				return Constants.KEYWORD_FAIL + "Mail not recieved";
		} catch (NoSuchProviderException ex) {
			return Constants.KEYWORD_FAIL + "No Provider";

		} catch (MessagingException ex) {
			return Constants.KEYWORD_FAIL
					+ "Could not connect to the message store.";
		}
	}

	/**
	 * 06/01/2014 Added By Mayank Saini This method is used to check mail is not
	 * recieved at the reciever end
	 * 
	 * Pass the username using OR and subject of email as data
	 * 
	 * */
	public String checkMailsNotExist(String object, String data) {
		Boolean flag = false;
		String host = "imap.gmail.com";
		String port = "993";
		String userName = OR.getProperty(object);
		String password = "t3stingte@m";
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			// fetches new messages from server
			Message[] arrayMessages = folderInbox.getMessages();

			for (int i = 0; i < arrayMessages.length; i++) {
				Message message = arrayMessages[i];
				String subject = message.getSubject();

				if (subject.trim().equals(data.trim())) {
					logger.debug("Actual Subject " + subject);
					logger.debug("Expected Subject " + data);
					message.setFlag(Flags.Flag.DELETED, true);
					flag = true;
					break;
				}

			}

			// expunges the folder to remove messages which are marked deleted
			boolean expunge = true;
			folderInbox.close(expunge);
			store.close();
			if (flag)
				return Constants.KEYWORD_FAIL + "Mail Recieved ";
			else
				return Constants.KEYWORD_PASS + "Mail not recieved";
		} catch (NoSuchProviderException ex) {
			return Constants.KEYWORD_FAIL + "No Provider";

		} catch (MessagingException ex) {
			return Constants.KEYWORD_FAIL
					+ "Could not connect to the message store.";
		}
	}

	/**
	 * 06/01/2014 Added By Mayank Saini
	 * 
	 * This method is used to check the mail message shared in the email Provide
	 * the username of email from OR and subject of email and message of the
	 * email as data.
	 * 
	 * @throws InterruptedException
	 * */
	public String checkMailMessage(String object, String data)
			throws IOException, InterruptedException

			{

		Boolean flag = false;
		String host = "imap.gmail.com";
		String port = "993";
		String userName = OR.getProperty(object);
		String password = "t3stingte@m";
		Properties properties = new Properties();

		// server setting
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);

		// SSL setting
		properties.setProperty("mail.imap.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.port",
				String.valueOf(port));

		Session session = Session.getDefaultInstance(properties);

		String temp[] = data.split(",");
		try {
			// connects to the message store
			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);
			while (true) {
				// fetches new messages from server
				Message[] arrayMessages = folderInbox.getMessages();

				for (int i = 0; i < arrayMessages.length; i++) {
					Message message = arrayMessages[i];
					String subject = message.getSubject();

					if (subject.trim().contains(temp[0].trim())) {
						logger.debug("Actual Subject " + subject);
						logger.debug("Expected Subject " + temp[0]);
						Multipart mp = (Multipart) message.getContent();
						BodyPart bp = mp.getBodyPart(0);
						String messageContent = bp.getContent().toString();
						String newMEssageContent = org.apache.log4j.helpers.Transform
								.escapeTags(messageContent);
						if (newMEssageContent.contains(temp[1].trim())) {
							message.setFlag(Flags.Flag.DELETED, true);
							flag = true;
							break;
						}
					}

				}

				if (flag)
					break;
				else
					Thread.sleep(10000);// expunges the folder to remove
				// messages which are marked deleted
			}
			// expunges the folder to remove messages which are marked deleted

			boolean expunge = true;
			folderInbox.close(expunge);
			store.close();
			if (flag)
				return Constants.KEYWORD_PASS
						+ "Mail Recieved and Message contains the input String";

			else
				return Constants.KEYWORD_FAIL
						+ "Mail recieved and message not matched";

		}

		catch (NoSuchProviderException ex) {
			return Constants.KEYWORD_FAIL + "No Provider";

		} catch (MessagingException ex) {
			return Constants.KEYWORD_FAIL
					+ "Could not connect to the message store.";
		}
			}

	/**
	 * 23/01/2014 Added By Sanjay Sharma This method is used to check the Alert
	 * message using contains.
	 * */
	public String verifyAlertTextUsingContains(String object, String data) {
		logger.debug("Verify the text of Alert pop up ");
		try {

			Alert alert = explicitWaitForAlert();
			String actual = alert.getText().trim();
			String expected = data.trim();

			logger.debug("actual: " + actual);
			logger.debug("expected: " + expected);

			if (actual.trim().contains(expected.trim())) {
				logger.debug("Alert detected and Text has been verified");
				return Constants.KEYWORD_PASS + " " + "--" + actual;
			} else {
				return Constants.KEYWORD_FAIL + " " + actual;
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Alert not found" + e.getMessage();
		}
	}

	/**
	 * verifySortingInAscend method ,checks Whether Courses are in sorting order
	 * Separated by Comma
	 * 
	 * @author Sanjay Sharma
	 * @since 13/11/2013
	 * @param object
	 * @param data
	 *            , As a Separator
	 * @return
	 * @throws Exception
	 */
	public String verifySortingInAscend(String object, String data)
			throws Exception {
		logger.debug("Entered into verifySortingInAscending()");
		try {
			List<String> al = new ArrayList<String>();
			List<String> ar = new ArrayList<String>();

			String Str = wait.until(
					explicitWaitForElement(By.xpath(OR.getProperty(object))))
					.getText();
			int index = Str.indexOf(data);
			String result = Str.substring(0, index);

			String actualArr[] = result.split(Constants.Object_SPLIT);

			for (int k = 0; k < actualArr.length; k++) {
				al.add(actualArr[k].trim());
				ar.add(actualArr[k].trim());
			}

			Collections.sort(al, String.CASE_INSENSITIVE_ORDER);

			logger.debug("actual is as follows-- " + ar);
			logger.debug("Expected is as follows-- " + al);
			if (al.equals(ar)) {
				return Constants.KEYWORD_PASS + " -- Elements are sorted";
			} else
				return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Created by Sandeep Dhamija, 3 January, 2013
	 * 
	 * @param object
	 *            - common xpath of html <td>attributes of all the searched
	 *            results
	 * @param data
	 *            - PID of user to be searched
	 * @return - PASS if all the searched results contain same PID as passed in
	 *         'data' parameter
	 * @return - FAIL if all the searched results do not contain same PID as
	 *         passed in 'data' parameter
	 */

	public String verifySearchByPID(String object, String data) {
		logger.debug("inside verifySearchByPID");
		try {

			List<WebElement> search_Result = explictWaitForElementList(object);
			if (search_Result.size() == 0) {
				return Constants.KEYWORD_FAIL
						+ " no users found. Enter a correct PID";
			}

			String expected_PID = data.trim().toLowerCase();
			for (int i = 0; i < search_Result.size(); i++) {
				String searched_PID = search_Result.get(i).getText();
				String actual_PID = searched_PID.trim();
				logger.debug("expected PID- " + expected_PID);
				logger.debug("actual PID- " + actual_PID);
				if (actual_PID.contains(expected_PID)) {
					continue;
				} else {
					return Constants.KEYWORD_FAIL + "expected PID- "
							+ expected_PID + " actual PID- " + actual_PID;
				}
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Dated 07/01/2014 Added By Mayank This method is used to press enter from
	 * keyboard. It takes the xpath of input field.
	 * */
	public String pressKeysFromKeyboard(String object, String data) {
		logger.debug("entered into pressEnterFromKeyboard");
		try {

			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			ele.clear();
			Actions action = new Actions(driver);
			Action pressKeys = action.sendKeys(ele, Keys.chord(data)).build();
			pressKeys.perform();
			return Constants.KEYWORD_PASS;

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * Added By Pooja Sharma on 08/01/2014 This method Opens a Pop-Up message
	 * window if Duplicate Entries exist within the Specific_OR files or within
	 * specic_OR and Common_OR file
	 * 
	 * * Modified By Pankaj Dogra : Added Code to Check Duplicate entries between
	 * Tab Specific Common OR  and Compare Tab Specific Common OR  with Common OR
	 *  and Specific OR Files.
	 * 
	 * Remove isDuplicateEntryInCommon() as it will also check duplicate Entries for 
	 * Common OR also.
	 **/

	@SuppressWarnings({ "unused", "unchecked" })
	public String isDuplicateEntry(String object,String tabSpecificOR_Path,String commonOrPath) throws IOException {
		ArrayList<String> all_file_name = new ArrayList<String>();
		List<String> common_keys = new ArrayList<String>();
		List<String> tab_keys = new ArrayList<String>();
		List<String> keys = new ArrayList<String>();
		List<String> allArray=new ArrayList<String>();
		ArrayList<String> file_keys = new ArrayList<String>();          // Intialize array to Store all duplicate keys of Specific_OR file
		ArrayList<String> both_file_keys = new ArrayList<String>();     // Intialize array to Store all duplicate keys of Common_OR file and Specific OR
		ArrayList<String> specific_file_keys=new ArrayList<String>();  // Intialize array to Store all duplicate keys of Tab_Specific_OR file and Specific OR
		ArrayList<String> tab_common_file_keys=new ArrayList<String>(); // Intialize array to Store all duplicate keys of Tab_Specific_OR file and Common_OR
		all_file_name.add(object);
		all_file_name.add(tabSpecificOR_Path);
		all_file_name.add(commonOrPath);
		String fileName=null;
		String Common_fileName=null;
		String tabFile_Name=null;
		for(int i=0;i<all_file_name.size();i++){
			String objArr[]= all_file_name.get(i).split("/");
			int arrSize = objArr.length;
			if(i==0)
				fileName = objArr[arrSize - 1];
			else if(i==1)
				tabFile_Name = objArr[arrSize - 1];
			else
				Common_fileName = objArr[arrSize - 1];
		}
		tabFile_Name=tabFile_Name.replace("\\", ",");
		String[] tab=tabFile_Name.split(",");
		tabFile_Name=tab[1];  
		int duplicate_keys=0,duplicate_keys_both=0,check_specific_duplicay=0;
		int check_common_duplicay=0,check_tab_duplicate=0,tab_specific_duplicate_key=0,tab_common_duplicate_key=0;
		int sr_no=1,sr_number=1,sr_nbr=1,tab_spec_sr_number=1,tab_common_number=1;
		// Initiallize String Buffers
		StringBuffer bothFiles = new StringBuffer();
		StringBuffer SpecificFile = new StringBuffer();
		StringBuffer tab_specific_buffer = new StringBuffer();
		StringBuffer tab_common_buffer = new StringBuffer();
		StringBuffer commonBufferFile= new StringBuffer();
		try {

			for(int i=0;i<all_file_name.size();i++)
			{	
				allArray.clear();
				File file=new File(all_file_name.get(i));

				// ******* TO READ SPECIFIC COMMON OR FILE,TAB SPECIFIC FILE AND COMMON OR FILE *******************/
				FileReader fr = new FileReader(file);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(fr);
				String line;		
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (line.startsWith("#") || line.isEmpty()|| line.startsWith("!") || line.startsWith("<")
							|| line.startsWith(">") || line.startsWith("=")) {
						keys.remove(line);
					} else {
						String split2[] = line.split("=");// splits the line read on// the basis of "="and put the keys in the ArrayList named keys.
						allArray.add(split2[0]);
						if(i==0)
							keys.add(split2[0]);
						if(i==1)
							tab_keys.add(split2[0]);
						if(i==2)
							common_keys.add(split2[0]);
					}
				}
				// *************COMPARE EACH KEY OF SPECIFIC OR WITH EVERY KEY OF ITSELF,Tab With Itself and Common With Itself *********************/
				for (int k = 0; k < allArray.size(); k++) {
					for (int j = 0; j < allArray.size(); j++) {
						if ((allArray.get(k)).equals(allArray.get(j)) && k != j) {
							String own_duplicate = (String) allArray.get(j);

							if (file_keys.contains(own_duplicate)) {
								continue;
							} else {
								file_keys.add(own_duplicate);// add the duplicate entries in the arrayList named file_Keys.
								duplicate_keys++;// count the number of duplicate entries in the Specific_OR file.
							}
						}
					}
				}
				if (duplicate_keys > 0) {
					for (String keys1 : file_keys) {
						SpecificFile.append("<b>" + sr_no + ".  " + keys1 + "</b>");
						SpecificFile.append("<br>");
						sr_no++;
					}
					if(i==0){
						check_specific_duplicay=duplicate_keys;
						logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Duplicate KEYS in :-  "+ fileName+ "</b></FONT>");
					}
					if(i==1){
						check_common_duplicay=duplicate_keys;
						logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Duplicate KEYS in :- "+ tabFile_Name+ "</b></FONT>");
					}
					if(i==2)
					{
						check_tab_duplicate=duplicate_keys;
						logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Duplicate KEYS in :- "+ Common_fileName+ "</b></FONT>");
					}
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+SpecificFile+"</b></FONT>");
				}			


				file_keys.clear();
				SpecificFile.setLength(0);
				duplicate_keys=0;
				sr_no=1;
			}
			// **************COMPARE EARCH KEY OF COMMON_OR WITH SPECIFIC_OR FILE***********/
			for (int h = 0; h < common_keys.size(); h++) {
				for (int r = 0; r < keys.size(); r++) {
					if ((common_keys.get(h)).equals(keys.get(r))) {
						String com_specific_Duplicate = (String) keys.get(r);

						if (both_file_keys.contains(com_specific_Duplicate)) {
							continue;
						} else {
							both_file_keys.add(com_specific_Duplicate);// add the duplicate entries in the arrayList named both_file_keys.
							duplicate_keys_both++;
						}
					}
				}
			}
			/* ******** COMPARE EACH KEY OF TAB SPICIFIC OR FILE WITH EVERY KEY OF  SPECIFIC OR FILE ********************** */

			for (int l = 0; l < tab_keys.size(); l++) {
				for (int m = 0; m < keys.size(); m++) {
					if ((tab_keys.get(l).trim()).equals(keys.get(m))) {
						String tab_specific_duplicate = (String) keys.get(m);

						if (specific_file_keys.contains(tab_specific_duplicate)) {

							continue;
						} else {
							specific_file_keys.add(tab_specific_duplicate);// add the duplicate entries in the arrayList named specific_file_keys.
							tab_specific_duplicate_key++; // count the number of duplicate entries in  Specific OR and Tab Specific_OR file.
						}
					}
				}
			}
			/* *************************** COMPARE EACH KEY OF TAB_SPECIFIC_OR FILE WITH EVERY KEY OF COMMON_OR FILE ********************** */
			for (int h = 0; h < tab_keys.size(); h++) {
				for (int r = 0; r < common_keys.size(); r++) {
					if ((tab_keys.get(h).trim()).equals(common_keys.get(r))) {
						String tab_common_duplicate = (String) common_keys.get(r);

						if (tab_common_file_keys.contains(tab_common_duplicate)) {

							continue;
						} else {
							tab_common_file_keys.add(tab_common_duplicate);   // add the duplicate entries in the arrayList named both_file_keys.
							tab_common_duplicate_key++;     // count the number of duplicate entries in  Common OR and Tab Specific_OR file.
						}
					}
				}
			}
			/* ************ WRITE THE DUPLICATES ENTRIES OF TAB SPECIFIC COMMON OR,SPECIFIC OR AND COMMON OR IN APPLICATION LOG FILE ********/	
			if((both_file_keys.containsAll(specific_file_keys) && both_file_keys.size()==specific_file_keys.size() 
					&& both_file_keys.size()>0 && specific_file_keys.size()>0)&&(both_file_keys.containsAll(tab_common_file_keys)
							&& both_file_keys.size()==tab_common_file_keys.size() && tab_common_file_keys.size() >0)) {
				for (String keys1 : both_file_keys) {
					commonBufferFile.append("<b>" + sr_nbr + ".  " + keys1 + "</b>");
					commonBufferFile.append("<br>");
					sr_nbr++;
				}
				logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED><b>Duplicate KEYS in :- " + fileName +" </b></Font><b><FONT FACE=AriaL SIZE=2> and</b></Font> <b> <FONT FACE=AriaL SIZE=2 COLOR=RED>"+  tabFile_Name +"</FONT></b><b><FONT FACE=AriaL SIZE=2> and </FONT></b><FONT FACE=AriaL SIZE=2 COLOR=RED><b> Common_OR file</b></FONT>");
				logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+commonBufferFile+"</b></FONT>");
			}
			else
			{	
				if (duplicate_keys_both > 0) {
					for (String keys1 : both_file_keys) {
						bothFiles.append("<b>" + sr_number + ".  " + keys1 + "</b>");
						bothFiles.append("<br>");
						sr_number++;
					}
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED> <b>Duplicate KEYS in :- "+ fileName+ "</b></FONT><FONT FACE=AriaL SIZE=2><b> and </b></FONT><FONT FACE=AriaL SIZE=2 COLOR=RED><b>Common_OR file:--</b></FONT>");
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+bothFiles+"</b></FONT>");
				}
				// write the Duplicate entries found within the Tab Specific OR File and Specific OR File on the Log File.
				if (tab_specific_duplicate_key > 0) {
					for (String keys1 : specific_file_keys) {
						tab_specific_buffer.append("<b>" + tab_spec_sr_number + "." + keys1 + "</b>");
						tab_specific_buffer.append("<br>");
						tab_spec_sr_number++;
					}
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED> <b>Duplicate KEYS in :- "+ tabFile_Name+" </b></FONT><FONT FACE=AriaL SIZE=2><b> and </b></FONT> <FONT FACE=AriaL SIZE=2 COLOR=RED> <b> " +fileName+"--</b></FONT>");
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+tab_specific_buffer+"</b></FONT>");
				}
				// write the Duplicate entries found within the Tab Specific OR File and Common OR File on the Log File.

				if (tab_common_duplicate_key > 0) {
					for (String keys1 : tab_common_file_keys) {
						tab_common_buffer.append("<b>" + tab_common_number + ".  " + keys1 + "</b>");
						tab_common_buffer.append("<br>");
						tab_common_number++;
					}
					logger.debug("<FONT FACE=AriaL SIZE=2 COLOR=RED> <b>Duplicate KEYS in :-  "+ tabFile_Name+"</b></FONT><FONT FACE=AriaL SIZE=2><b> and </b></FONT> <FONT FACE=AriaL SIZE=2 COLOR=RED> <b>Common_OR File</b></FONT>");
					logger.debug("<FONT FACE=Times New Roman SIZE=2><b>"+tab_common_buffer+"</b></FONT>");
				}
			}
			if (check_specific_duplicay >0 || check_common_duplicay>0||check_tab_duplicate>0|| duplicate_keys_both > 0 || tab_specific_duplicate_key > 0 || tab_common_duplicate_key > 0) {
				return "dulicate entries exist";
			} 

			else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to check for the duplicate entries:--"+ e);
			return null;
		}
	}

	/**
	 * 19/03/2015 Added by Nitin Gupta 
	 * This method is used to create Zip folder of the Report Generated
	 **/
	public String createZipFile() throws IOException {

		String outputFile = result_FolderName + ".zip";
		FileOutputStream fos = new FileOutputStream(outputFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		packCurrentDirectoryContents(result_FolderName, zos);
		zos.closeEntry();
		zos.close();
		fos.close();
		return outputFile;
	}


	public  void packCurrentDirectoryContents(String directoryPath, ZipOutputStream zos) throws IOException {

		for (String dirElement: new File(directoryPath).list()) {

			String dirElementPath = directoryPath+"/"+dirElement;

			if (new File(dirElementPath).isDirectory()) {
				packCurrentDirectoryContents(dirElementPath, zos);

			} 
			else {
				ZipEntry ze= new ZipEntry(dirElementPath.replaceAll(result_FolderName+"/", ""));
				zos.putNextEntry(ze);

				FileInputStream fis = new FileInputStream(dirElementPath);
				byte[] bytesRead = new byte[512];

				int bytesNum;
				while ((bytesNum = fis.read(bytesRead)) > 0) {
					zos.write(bytesRead, 0, bytesNum);
				}

				fis.close();
			}
		} 
	}
	/**
	 * 21/01/2013 Added By Mayank Saini This method is used to click element
	 * present at position with multiple xpaths
	 * @param object
	 *            :Require 2 xpaths splitted with comma i.e Start And End Xpath for check box
	 * @param data
	 *            : Data/Name(s) that should be pass to splitted xpath (using "Data_SPLIT),if required
	 *          
	 *            MOdified by sanjay Sharma on 03/03/2015 , Added Multiple Data functionality
	 **/

	public String clickElementWithIndex(String object, String data) {
		WebElement ele = null;
		try {
			String temp[] = object.split(",");
			String element[] = data.split(Constants.DATA_SPLIT);
			for(int i=0;i<element.length;i++)
			{
				ele = driver.findElement(By.xpath(OR.getProperty(temp[0]) + element[i]+ OR.getProperty(temp[1])));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].scrollIntoView(true);", ele);
				executor.executeScript("arguments[0].click();", ele);
			}
			return Constants.KEYWORD_PASS + " Element Clicked on given positions ";
		} 
		catch (WebDriverException ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}

	}

	/**
	 * 13/01/2014 Added By Mayank Saini This method is used to hover the message
	 * over webelement and check the message
	 * */
	public String mouseOverCheckMessage(String object, String data) {
		try {
			String temp[] = object.split(",");
			Actions action = new Actions(driver);
			WebElement we = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(temp[0]))));
			action.clickAndHold(we).build().perform();
			String getText = wait.until(
					explicitWaitForElement(By.xpath(OR.getProperty(temp[1]))))
					.getText();
			action.release(we).build().perform();
			if (getText.equals(data))
				return Constants.KEYWORD_PASS + "Mouse hover and text="+data+" matched";
			else
				return Constants.KEYWORD_FAIL
						+ "Either Mouse not hover or text not matched";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getMessage();
		}

	}

	/**
	 * 13/01/2014
	 * 
	 * Added By Mayank Saini This method is used to verify whether elements in
	 * drop down are selected in alphabetic order
	 * */

	public String verifyAlphabeticOrder(String object, String data) {
		try {
			Boolean flag = false;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			Select sel = new Select(select);
			String selectedValue = sel.getFirstSelectedOption().getText();
			List<WebElement> options = select.findElements(By.tagName(OR
					.getProperty("OPTION_TAG").trim()));
			for (WebElement option : options) {

				String value = option.getText().trim();
				logger.debug("Actual > " + value);
				if (selectedValue.trim().compareToIgnoreCase(value.trim()) < 0) {
					flag = true;
				} else if (selectedValue.trim().compareToIgnoreCase(
						value.trim()) < 0) {
					flag = true;
				}

				else {
					flag = false;
					break;
				}

			}
			if (flag)
				return Constants.KEYWORD_PASS
						+ "Value is Alphabetically Selected";
			else
				return Constants.KEYWORD_FAIL
						+ "Value is Alphabetically Selected";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();

		}
	}

	/**
	 * @since 02/05/14
	 * @author Anil Kumar Mishra This method is used to enter input in text box
	 *         using send keys.
	 */

	public String enterInput(String object, String data) {
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try {

			WebElement ele =wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			ele.clear();
			ele.sendKeys(data);
			return Constants.KEYWORD_PASS + "Data Entered--" + data;
		}

		catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Unable to write "
			+ e.getLocalizedMessage();
		}
	}



	/**
	 * 03/03/2014 Added By Mayank Saini `
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickLinkUsingJs(String object, String data) {
		logger.debug("Clicking on link ");
		try {

			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			executor.executeScript("arguments[0].click();", ele);

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link"
					+ e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * This method generate PID Randomly and click on Save button or any other
	 * sub tab as per user requirement.
	 * 
	 * @author Pankaj Dogra
	 * @since 20 February, 2014
	 * @param object
	 *            : It Accepts Three xpath First is --> Input box of Personal ID
	 *            number Second is---> xpath of save button or any sub tab..for
	 *            example : Detail,Assessment,Courses etc. Third is ---> xpath
	 *            of Error message i.e pid is already exist.
	 * 
	 * @param data
	 *            : User doesn't need to pass any data.
	 */

	public String enterRandomNumber(String object, String data) {
		logger.debug("enterRandomNumber method");
		try {
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String objArr[] = object.split(Constants.Object_SPLIT);
			final String input = objArr[0];
			final String button = objArr[1];
			final String error_msg = objArr[2];
			number = String.valueOf((long) ((Math.random() * (Constants.upper-Constants.lower)) + Constants.lower));
			driver.findElement(By.xpath(OR.getProperty(input))).clear();
			driver.findElement(By.xpath(OR.getProperty(input))).sendKeys(number);
			driver.findElement(By.xpath(OR.getProperty(button))).click();
			Thread.sleep(new Long(CONFIG.getProperty("method_delay_time")));
			List<WebElement> error = driver.findElements(By.xpath(OR.getProperty(error_msg)));
			while (error.size() > 0) {
				number = String.valueOf((long) ((Math.random() * (Constants.upper-Constants.lower)) + Constants.lower));
				driver.findElement(By.xpath(OR.getProperty(input))).clear();
				driver.findElement(By.xpath(OR.getProperty(input))).sendKeys(number);
				driver.findElement(By.xpath(OR.getProperty(button))).click();
				Thread.sleep(new Long(CONFIG.getProperty("method_delay_time")));
				error = driver.findElements(By.xpath(OR.getProperty(error_msg)));
			}

			return Constants.KEYWORD_PASS + "-- " + " User/Course Number has created Successfully with" + number ;

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * This method enter the last generated random number by
	 * "enterRandomNumber()".
	 * 
	 * @author Pankaj Dogra
	 * @since 20 February, 2014
	 * @param object
	 *            : It Accepts one xpath i.e is Input box of Personal ID.
	 * @param data
	 *            : User doesn't need to pass data.
	 */

	public String enterLastGeneratedPId(String object, String data) {
		try {
			if(object== null){
				return Constants.KEYWORD_FAIL + " -  Xpath not found";
			}
			logger.debug("getting pid=" + number);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(number);
			return Constants.KEYWORD_PASS + " -  Last Generated PID has Entered";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable Enter " + e.getLocalizedMessage();
		}
	}


/**
	 * This method compares the last generated random number by searched number
	 * 
	 * @author Sanjay Sharma
	 * @since 09 July, 2015
	 * @param object
	 *            : It Accepts one xpath i.e Searched Course or User xpath
	 * @param data
	 *            : User doesn't need to pass data.
	 */
	public String verifyGeneratedPid(String object, String data) {
		logger.debug("Verifying the text");
	
			try {
				if(object== null){
					return Constants.KEYWORD_FAIL + " -  Xpath not found";
				}
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String actual = ele.getText();
			logger.debug("data" + number);
			logger.debug("act" + actual);
			if (actual.trim().equals(number))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified Actual text = " + actual + " -- Expected Text = " + number;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
	
			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	/**
	 * This method can be use to enter current (i.e closest) time <Br>
	 * 
	 * @author Pankaj Sharma
	 * @since 14 October, 2013
	 * 
	 * @param object
	 *            : In object parameter This method accepts four xpaths that are
	 *            separated by '|' . <Br>
	 *            First xpath is of Hour drop down ,Second xpath is of Minute
	 *            drop down , <Br>
	 *            Third xpath is of AM_PM drop down and fourth xpath is of text
	 *            field. Restriction: Do not Change the order of xpaths in this
	 *            parameter, <br>
	 *            It should be like this =
	 *            hr_drop_down|min_drop_down|am_pm_drop_down|Date_txt_fld
	 * 
	 * @param data
	 *            : Appropriate Time Interval is passed from this parameter.
	 * 
	 * @return <b>PASS</b> if the current time(i.e. closest time) is selected
	 *         successfully. <Br>
	 *         <b>FAIL</b> otherwise.
	 */

	public String enterDueTimeAfterRequiredInterval(String object, String data) {
		logger.debug("Entering Current Start date");
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String objArr[] = object.split(Constants.DATA_SPLIT);

			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];
			final String XPATH3 = objArr[2];
			final String XPATHDATE = objArr[3];
			int timeInterval = Integer.parseInt(data.trim());
			// validate the parameters
			if (XPATH1 == null || XPATH2 == null || XPATH3 == null) {
				return Constants.KEYWORD_FAIL
						+ " Either Xpath1 or Xpath2 is null. Please check the xpath";
			}

			Calendar calendarDate = Calendar.getInstance();
			SimpleDateFormat timeFormatDate = new SimpleDateFormat("MM/dd/yyyy");
			timeFormatDate.setTimeZone(TimeZone.getTimeZone("US/Central"));
			calendarDate.add(Calendar.DATE, 0);
			String dueDate = timeFormatDate.format(calendarDate.getTime());

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
			timeFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String startDate_dueDate = timeFormat.format(calendar.getTime());

			String timeArr[] = startDate_dueDate.split(":");
			String timeHr = timeArr[0];
			String timeMin = timeArr[1];
			String timeSec = timeArr[2];

			String timeAM[] = timeSec.split(" ");
			String AM_PM = timeAM[1];

			int MIN = Integer.parseInt(timeMin);
			int newMIn;

			if (MIN % 5 == 0) {
				newMIn = MIN + timeInterval;
			} else {
				int n = MIN / 5;
				newMIn = (n * 5) + timeInterval;
			}
			timeMin = String.valueOf(newMIn);
			if (newMIn == 5) {
				String m = "0";
				timeMin = String.valueOf(newMIn);
				timeMin = m.concat(timeMin);
			} else if (newMIn == 60) {
				timeMin = "00";
				int hr = Integer.parseInt(timeHr);
				hr = hr + 1;
				String h1 = "0";
				if (hr == 12) {
					timeHr = String.valueOf(hr);
				} else {
					timeHr = String.valueOf(hr);
					timeHr = h1.concat(timeHr);
				}

				if (hr == 13) {
					hr = 1;
					String h2 = "0";
					timeHr = String.valueOf(hr);
					timeHr = h2.concat(timeHr);
					if (AM_PM.equals("AM")) {
						AM_PM = "PM";
					} else if (AM_PM.equals("PM")) {
						AM_PM = "AM";
						calendarDate.add(Calendar.DATE, 1);
						dueDate = timeFormatDate.format(calendarDate.getTime());
					}

				}
			}
			driver.findElement(By.xpath(OR.getProperty(objArr[3]))).clear();
			driver.findElement(By.xpath(OR.getProperty(objArr[3]))).sendKeys(
					dueDate);

			// Following Lines select the Hour from the Hour drop down List
			WebElement elementHr = driver.findElement(By.xpath(OR
					.getProperty(objArr[0])));
			Select selectHr = new Select(elementHr);
			logger.debug("Hour is > " + timeHr);
			selectHr.selectByValue(timeHr);

			// Following Lines select the Minutes from the Minute drop down List
			WebElement elementMin = driver.findElement(By.xpath(OR
					.getProperty(objArr[1])));
			Select selectMin = new Select(elementMin);
			logger.debug("Minute is > " + timeMin);
			selectMin.selectByValue(timeMin);

			logger.debug("AM or PM is > " + AM_PM);

			// Following Line select the AM or PM from the AM_PM drop down List
			keyUtil.selectList(objArr[2], AM_PM);

			// This if verify that the All three Hour , Minute and AM or PM are
			// Selected successfully or not
			int flag = 1;
			if (flag == 1) {
				return Constants.KEYWORD_PASS
						+ "---start/due Time(HR : MIN : AP_PM) has been Entered.. ";
			} else {
				return Constants.KEYWORD_FAIL
						+ "---start/due Time(HR : MIN : AP_PM) has not been Entered.. ";
			}
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * 03/03/2014 Added By Mayank Saini
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickJs(WebElement ele) {
		logger.debug("Clicking on link ");
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
			return Constants.KEYWORD_PASS;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL;
		}

	}

	/**
	 * Waits for the completion of Ajax jQuery processing by checking
	 * "return jQuery.active == 0" condition.
	 * 
	 * @param WebDriver
	 *            - The driver object to be used to wait and find the element
	 * @param int - The time in seconds to wait until returning a failure
	 * 
	 * @return boolean true or false(condition fail, or if the timeout is
	 *         reached)
	 * */
	public static boolean waitForJQueryProcessing(final WebDriver driver,
			int timeOutInSeconds) {
		boolean jQcondition = false;
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
			// implicitlyWait()
			new WebDriverWait(driver, 30) {
			}.until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver arg0) {
					// TODO Auto-generated method stub
					return (Boolean) ((JavascriptExecutor) driver)
							.executeScript("return jQuery.active == 0");
				}
			});
			jQcondition = (Boolean) ((JavascriptExecutor) driver)
					.executeScript("return jQuery.active == 0");

			return jQcondition;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jQcondition;
	}

	/**
	 * 07/02/2014 Mayank Saini This method is used to check if radio button is
	 * selected or not
	 */
	public String isRadioNotSelected(String object, String data) {
		logger.debug("Verifying checkbox selected");
		try {
			boolean checked =explictWaitForElementUsingFluent(object).isSelected();
			if (!checked)
				return Constants.KEYWORD_PASS
						+ "-- RadioButton is not selected";
			else
				return Constants.KEYWORD_FAIL + " -  RadioButton selected";

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not find RadioButton";

		}
	}

	/**
	 * 03/03/2014 Added By Mayank Saini
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String clickUsingActions(String object, String data) {
		logger.debug("Clicking on link ");
		WebElement ele = null;
		try {

			ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			new Actions(driver).click(ele).build().perform();
			new KeywordEventsUtill().browserSpecificPause(object, data);

		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link"+data
					+ ex.getCause();
		} catch (WebDriverException ex) {
			try {
				String exceptionMessage = ex.getMessage();
				if (exceptionMessage
						.contains("Element is not clickable at point")) {
					if (new ApplicationSpecificKeywordEventsUtil().clickJs(ele)
							.equals(Constants.KEYWORD_PASS))
						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL;
				} else
					return Constants.KEYWORD_FAIL + "not able to Click"
					+ ex.getMessage();
			} catch (Exception e) {
				return Constants.KEYWORD_FAIL + e.getMessage();
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link"
					+ e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * 15/04/2014 Added By Mayank Saini
	 * 
	 * This method is used to check that whether mail is received and name of
	 * file attached with mail Provide the username of email from OR and subject
	 * of email as data
	 * 
	 * @throws InterruptedException
	 * */

	public String verifyFileNameOfEmail(String object, String data)
			throws IOException, InterruptedException {
		try {
			Boolean flag = false;
			String temp[] = data.split(",");
			String host = "imap.gmail.com";
			String port = "993";
			String userName = OR.getProperty(object);
			String password = "t3stingte@m";
			Properties properties = new Properties();

			// server setting
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);

			// SSL setting
			properties.setProperty("mail.imap.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.imap.socketFactory.fallback", "false");
			properties.setProperty("mail.imap.socketFactory.port",
					String.valueOf(port));

			Session session = Session.getDefaultInstance(properties);

			// connects to the message store

			Store store = session.getStore("imap");
			store.connect(userName, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

			// fetches new messages from server
			while (true) {

				Message[] arrayMessages = folderInbox.getMessages();
				for (int i = 0; i < arrayMessages.length; i++) {
					Message message = arrayMessages[i];

					String subject = message.getSubject();

					if (subject.trim().equals(temp[0].trim())) {

						String contentType = message.getContentType();

						if (contentType.contains("multipart")) {

							Multipart multiPart = (Multipart) message
									.getContent();

							for (int j = 0; j < multiPart.getCount(); j++) {

								MimeBodyPart part = (MimeBodyPart) multiPart
										.getBodyPart(j);

								if (Part.ATTACHMENT.equalsIgnoreCase(part
										.getDisposition())) {

									String fileName = part.getFileName();
									if (fileName.equals(temp[1])) {
										message.setFlag(Flags.Flag.DELETED,
												true);
										flag = true;
										break;
									} else
										flag = false;
									// saveFile(part.getFileName(),
									// part.getInputStream());

								}
							}
						}

						break;

					}

				}

				if (flag) {
					break;
				} else {
					Thread.sleep(10000);
				}
			}
			// expunges the folder to remove messages which are marked deleted

			boolean expunge = true;

			folderInbox.close(expunge);

			store.close();

			if (flag)

				return Constants.KEYWORD_PASS
						+ "Mail Recieved And File name matched";

			else

				return Constants.KEYWORD_FAIL
						+ "Either Mail not Recieved or File name not matched";

		} catch (NoSuchProviderException ex) {

			return Constants.KEYWORD_FAIL + ex.getMessage();

		} catch (MessagingException ex) {

			return Constants.KEYWORD_FAIL + ex.getMessage();

		}
	}

	/**
	 * This method is used to verify that the Web Element is present .
	 * @author : Pankaj Sharma
	 * @since :08/05/2014
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter
	 * @return PASS : If Element is Present on the Web Page.
	 * @return Fail : Otherwise.
	 * Modified on 02/07/2015 by Naincy Saini : Modified return statement to print data
	 **/
	public String isWebElementPresentUsingData(String object, String data) {
		logger.debug("Entered into isWebElementPresentUsingData()");
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			// validates the parameter
			if (xpathStart == null || xpathEnd == null) {
				return Constants.KEYWORD_FAIL + "Object is not passed properly";
			}
			if (data == null) {
				return Constants.KEYWORD_FAIL + "Data is empty";
			}
			logger.debug(xpathStart + data.trim() + xpathEnd);
			int element = driver.findElements(By.xpath(xpathStart + data + xpathEnd)).size();
			if (element == 0) {
				logger.debug("webElement not present.." + element);
				return Constants.KEYWORD_FAIL + " -- No webElement present";
			} else
				return Constants.KEYWORD_PASS + " -- webElement="+data+" is present -- "+element+"times";
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "
					+ e.getMessage();
		}
	}


	/**
	 * Added By Pallavi Singla
	 * 
	 * This method is used to hit up arrow key from keyboard
	 * 
	 * @throws IOException
	 * */

	public String writeInInputHitUpward(String object, String data) {
		logger.debug("Writing in text box");

		try {

			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			ele.clear();
			ele.sendKeys(data + Keys.ARROW_RIGHT + Keys.ARROW_RIGHT
					+ Keys.ARROW_RIGHT);

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to write "
					+ e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + data;

	}

	/**
	 * This method is used to verify that the Web Element is not present
	 * @author : Pankaj Sharma
	 * @since :08/05/2014
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *            object parameter
	 * @return PASS : If Element is not Present on the Web Page.
	 * @return Fail : Otherwise.

	 **/
	public String isWebElementNotPresentUsingData(String object, String data) {
		logger.debug("Entered into isWebElementNotPresentUsingData()");
		try {
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			// validates the parameter
			if (xpathStart == null || xpathEnd == null) {
				return Constants.KEYWORD_FAIL + "Object is not passed properly";
			}
			if (data == null) {
				return Constants.KEYWORD_FAIL + "Data is empty";
			}
			logger.debug(xpathStart + data.trim() + xpathEnd);
			int element = driver.findElements(
					By.xpath(xpathStart + data.trim() + xpathEnd)).size();
			if (element == 0) {
				logger.debug("webElement not present.." + element);
				return Constants.KEYWORD_PASS + " -- No webElement present";
			} else {
				logger.debug("webElement present.." + element);
				return Constants.KEYWORD_FAIL + " -- webElement present -- ";
			}
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "
					+ e.getMessage();
		}
	}
	/**
	 * This method Select all the text in the FCK Editer and clicks B,I,U from
	 * the rich text field <Br>
	 * 
	 * @author Pankaj Sharma
	 * @since 13 January, 2014
	 * 
	 * @param object
	 *            : It Accepts three xpaths. First Xpath is of FCk iframe,
	 *            Second is of Rich Text Formatting link <Br>
	 *            and third of B/I/U button. For Example :
	 *            admn_pwd_msg_fck|admn_rtf_lnk|admn_rtf_italic_btn
	 * 
	 * @param data
	 *            : Do not Required any Data.
	 * 
	 * @return <b>PASS</b> If Method select the text any click on the any Button
	 *         among B/I/U <br>
	 *         <b>FAIL</b> otherwise.
	 */

	public String selectAllAndBoldText(String object, String data)
			throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			String objArr[] = object.split(Constants.DATA_SPLIT);
			String FRAME_XPATH = objArr[0];
			String FORMAT_TEXT_XPATH = objArr[1];
			String FONT_XPATH = objArr[2];
			WebElement frame = driver.findElement(By.xpath((OR
					.getProperty(FRAME_XPATH))));
			driver.switchTo().frame(frame);
			WebElement editable = driver.switchTo().activeElement();
			editable.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
			driver.findElement(By.xpath(OR.getProperty(FORMAT_TEXT_XPATH)))
			.click();
			driver.findElement(By.xpath(OR.getProperty(FONT_XPATH))).click();
			Thread.sleep(1000);
			Set<String> availableWindows = driver.getWindowHandles();
			logger.debug("Handle Size:" + availableWindows.size());
			// Retreive all the window handles into variables
			String WindowIDparent = null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1) {
					logger.debug(Integer.toString(counter) + " " + windowId);
					WindowIDparent = windowId;
				}
				counter++;
			}
			// Navigate to Parent window
			driver.switchTo().window(WindowIDparent);
			logger.debug("In the Parent");

		} catch (WebDriverException e) {
			return Constants.KEYWORD_FAIL + " - Could not enter the message "
					+ e.getMessage();
		}

		return Constants.KEYWORD_PASS;

	}

	/**
	 * Sohal Bansal 19/05/2014 verifyValueNotPresentInDropdown() this method
	 * verifies a particular value is not present in the dropdown list
	 */
	public String verifyValueNotPresentInDropdown(String object, String data) {
		try {
			logger.debug("looking up the list");
			boolean flag = false;
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			List<WebElement> options = select
					.findElements(By.tagName("option"));
			String expected = data.trim();
			for (int i = 0; i < options.size(); i++) {
				String actual = options.get(i).getText().trim();
				logger.debug("actual --" + actual);
				logger.debug("expected --" + expected);
				if (actual.equals(expected)) {
					flag = true;
					break;
				} else {
					flag = false;

				}
			}
			if (flag) {
				return Constants.KEYWORD_FAIL + " -- " + expected
						+ " present in dropdown";
			} else {
				return Constants.KEYWORD_PASS + " -- " + expected
						+ " not present in dropdown";
			}
		} catch (Exception e) {

			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/**
	 * 30/04/2014 Added By Mayank Saini This method is used to unzip the
	 * downloaded file in downloads Folder
	 */
	public String unzipFile(String object, String data) {
		byte[] buffer = new byte[1024];
		try {

			String zipFile = data;
			String outPutFolder = System.getProperty("user.dir")
					+ File.separator + "externalFiles" + File.separator
					+ "downloadFiles";
			File f = new File(outPutFolder);
			f.mkdir();

			ZipInputStream zis = new ZipInputStream(new FileInputStream(
					System.getProperty("user.dir") + File.separator
					+ "externalFiles" + File.separator
					+ "downloadFiles" + File.separator + zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outPutFolder + File.separator
						+ fileName);

				logger.debug("file unzip : " + newFile.getAbsoluteFile());

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
			return Constants.KEYWORD_PASS + "File unzipped succesfully";

		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}
	}

	/**
	 * 01/05/2014 Added By Mayank Saini
	 * 
	 * This method will search for file in downloads folder as well in every
	 * child folder
	 * */
	public String checkFileExistance(String object, String data) {

		try {
			if (data == null)
				return Constants.KEYWORD_FAIL + "Data is Required";

			if (object.equals(""))
				object = dirName;

			File dir = new File(object);
			if (dir.exists()) {
				File[] allFiles = dir.listFiles();
				for (int i = 0; i < allFiles.length; i++) {
					if (allFiles[i].isDirectory()) {
						String newDir = object + File.separator
								+ allFiles[i].getName();
						String result = checkFileExistance(newDir, data);
						resultList.add(result);
					} else if (allFiles[i].getName().equals(data)) {

						Folder_File = allFiles[i].getParentFile();
						return Constants.KEYWORD_PASS;

					}

				}

			} else {
				throw new FileNotFoundException();
			}
			if (resultList.contains(Constants.KEYWORD_PASS)
					&& object.equals(dirName)) {
				resultList = new ArrayList<String>();
				return Constants.KEYWORD_PASS + "File Found present in"
				+ Folder_File;

			} else
				return Constants.KEYWORD_FAIL + "File Not Found";
		} catch (FileNotFoundException ex) {
			return Constants.KEYWORD_FAIL + ex.getMessage();
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getMessage();
		}

	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will download the file
	 * according to different browsers
	 * */
	public String downloadFile(String object, String data) {
		try {
			File dir = new File(dirName);

			if (dir.exists()) {
				logger.debug("File Exits");
			} else {
				dir.mkdir();
			}

			if (CONFIG.getProperty("browserType").equals("Mozilla")) {
				String autoItpath = System.getProperty("user.dir")
						+ File.separator + "externalFiles" + File.separator
						+ "uploadFiles" + File.separator + "Save_Dialog_FF.exe";
				String[] dialog = new String[] { autoItpath, "Opening", "Save",
				"Save File" };
				Runtime.getRuntime().exec(dialog);
				Thread.sleep(5000);
				Set<String> wids = driver.getWindowHandles();

				if (wids.size() > 1)
					keyUtil.handleWindows(object, data);
				else
					keyUtil.moveToMainWindow(object, data);

				return Constants.KEYWORD_PASS + "Files are downloaded";
			} else if (CONFIG.getProperty("browserType").equals("IE")) {
				return Constants.KEYWORD + "Files cant be  downloaded";
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				Set<String> wids = driver.getWindowHandles();

				if (wids.size() > 1)
					keyUtil.handleWindows(object, data);
				else
					keyUtil.moveToMainWindow(object, data);

				return Constants.KEYWORD_PASS + "Files are downloaded";
			}
			return Constants.KEYWORD + "Files cant be  downloaded";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will check the pdfHyperLinks
	 * on the pdf file
	 * 
	 * * @param object : not require.
	 * 
	 * @param data
	 *            :It accepts 4 parameter with comma separated.1) File path
	 *            after download Files 2)Particular Page number that you want to
	 *            access 3)Position of Link 4)File name behind the link
	 * @throws IOException
	 */
	/** Modified by Sumit Aggarwal to verify File behind the link opens **/

	public String pdfHyperLinks(String object, String data) throws IOException {
		logger.debug("Verifying PDF text File");
		PdfReader pdfreader = null;
		try {
			int count = 0;
			Boolean flag = false;
			String fileOpen = null;
			String temp[] = data.split(",");
			int page = Integer.parseInt(temp[1]);

			String path = dirName + File.separator + temp[0];
			pdfreader = new PdfReader(path);
			PdfDictionary PageDictionary = pdfreader.getPageN(page);

			// Get all of the annotations for the current page
			PdfArray Annots = PageDictionary.getAsArray(PdfName.ANNOTS);
			for (PdfObject A : Annots) {
				// Convert the itext-specific object as a generic PDF object
				// skip all annotations that aren't of the /Subtype /Link or
				// don't have an /A key
				PdfDictionary AnnotationDictionary = (PdfDictionary) PdfReader
						.getPdfObject(A);

				// Make sure this annotation has a link
				if (!AnnotationDictionary.get(PdfName.SUBTYPE).equals(
						PdfName.LINK))
					continue;
				// Make sure this annotation has an ACTION
				if (AnnotationDictionary.get(PdfName.A) == null)
					continue;
				// Get the ACTION for the current annotation
				PdfObject a = AnnotationDictionary.get(PdfName.A);
				if (a.isDictionary()) {
					count++;
					if (count == Integer.parseInt(temp[2])) {
						PdfDictionary AnnotationAction = (PdfDictionary) a;
						if (AnnotationAction.get(PdfName.S).equals(
								PdfName.GOTOR)) {
							String destination_file = AnnotationAction.get(
									PdfName.F).toString();
							String objArr[] = destination_file.split("/");
							int arrSize = objArr.length;
							String fileName = objArr[arrSize - 1];
							if (fileName.equals(temp[3])) {
								flag = true;
								Desktop.getDesktop().open(
										new File(dirName + File.separator
												+ destination_file));
								new KeywordEventsUtill().browserSpecificPause(
										object, data);
								fileOpen = verifyFileIsOpen(object,
										destination_file);// Added by Sumit
								// Aggarwal
								break;
							}
						}
					}
				}
			}

			if (flag && fileOpen.contains("PASS"))
				return Constants.KEYWORD_PASS + " "
				+ "Link is Present and file is open";
			else
				return Constants.KEYWORD_FAIL + " " + "Link is not Present";
		} catch (FileNotFoundException e) {

			return Constants.KEYWORD_FAIL + "File not found";
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Unable to write "
			+ e.getLocalizedMessage();
		} finally {
			pdfreader.close();
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will open the pdf file with
	 * the default type of file
	 * */
	public String openFile(String object, String data) {
		try {
			String path = dirName + File.separator + data;
			File f = new File(path);
			Desktop.getDesktop().open(f);
			new KeywordEventsUtill().browserSpecificPause(object, data);
			return Constants.KEYWORD_PASS;
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will delete the files as
	 * well as the folders in download files
	 * */

	public String deleteFile(String object, String data) {
		try {

			deleteFileRecursively(new File(dirName));

			return Constants.KEYWORD_PASS;
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getMessage();
		}

	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will delete the files as
	 * well as the folders in download files
	 * */
	public void deleteFileRecursively(File file) {
		try {

			if (file.isDirectory()) {

				// directory is empty, then delete it
				if (file.list().length == 0) {

					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());

				} else {

					// list all the directory contents
					String files[] = file.list();

					for (String temp : files) {
						// construct the file structure
						File fileDelete = new File(file, temp);

						// recursive delete
						deleteFileRecursively(fileDelete);
					}

					// check the directory again, if empty then delete it
					if (file.list().length == 0) {
						file.delete();
						logger.debug("File is deleted : "
								+ file.getAbsolutePath());

					}
				}

			} else {
				// if file, then delete it
				FileDeleteStrategy.FORCE.delete(file);

				logger.debug("File is deleted : " + file.getAbsolutePath());
			}

		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will close the file
	 * */
	public String closeFile(String object, String data) {
		try {

			Process p = Runtime.getRuntime().exec("taskkill /f /im " + data);
			p.waitFor();
			new KeywordEventsUtill().browserSpecificPause(object, data);
			return Constants.KEYWORD_PASS + "File closed successfully";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL;
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            :It accepts 5 parameter with comma separated.1) File path
	 *            after download Files 2)Particular Page number that you want to
	 *            access 3)Position of Link 4) Page that will open from this
	 *            link 5)File name behind the link
	 * @throws IOException
	 * */

	public String pdfHyperLinksWithPage(String object, String data)
			throws IOException {
		logger.debug("Verifying PDF text File");
		PdfReader pdfreader = null;
		try {
			int count = 0;
			int openPage = 0;
			Boolean flag = false;
			String temp[] = data.split(",");
			int page = Integer.parseInt(temp[1]);

			String path = dirName + File.separator + temp[0];
			pdfreader = new PdfReader(path);
			PdfDictionary PageDictionary = pdfreader.getPageN(page);

			// Get all of the annotations for the current page
			PdfArray Annots = PageDictionary.getAsArray(PdfName.ANNOTS);

			for (PdfObject A : Annots) {
				// Convert the itext-specific object as a generic PDF object
				// skip all annotations that aren't of the /Subtype /Link or
				// don't have an /A key
				PdfDictionary AnnotationDictionary = (PdfDictionary) PdfReader
						.getPdfObject(A);

				// Make sure this annotation has a link
				if (!AnnotationDictionary.get(PdfName.SUBTYPE).equals(
						PdfName.LINK))
					continue;
				// Make sure this annotation has an ACTION
				if (AnnotationDictionary.get(PdfName.A) == null)
					continue;
				// Get the ACTION for the current annotation
				PdfObject a = AnnotationDictionary.get(PdfName.A);
				if (a.isDictionary()) {
					count++;
					if (count == Integer.parseInt(temp[2])) {
						PdfDictionary AnnotationAction = (PdfDictionary) a;
						if (AnnotationAction.get(PdfName.S).equals(
								PdfName.GOTOR)) {
							String destination_file = AnnotationAction.get(
									PdfName.F).toString();
							String objArr[] = destination_file.split("/");
							int arrSize = objArr.length;
							String fileName = objArr[arrSize - 1];
							if (fileName.equals(temp[4])) {
								Desktop.getDesktop().open(
										new File(dirName + File.separator
												+ destination_file));
								new KeywordEventsUtill().browserSpecificPause(
										object, data);
								if (AnnotationAction.contains(PdfName.D))

								{
									PdfObject obj = AnnotationAction
											.get(PdfName.D);
									String destinationPage = obj.toString()
											.substring(1, 2);
									openPage = Integer
											.parseInt(destinationPage) + 1;
									if (openPage == Integer.parseInt(temp[3])) {
										flag = true;
										break;

									}
								}
							}
						}

					}
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS + " "
				+ "Link is Present and File opened on" + openPage;
			else
				return Constants.KEYWORD_FAIL + " " + "Link is not Present";

		} catch (FileNotFoundException e) {

			return Constants.KEYWORD_FAIL + "File not found";
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Unable to write "
			+ e.getLocalizedMessage();
		} finally {
			pdfreader.close();
		}

	}

	/**
	 * Added By Karan Sood Date : 12/03/2014 This Method clicks an value from
	 * drop-down while pressing Control key to de-select that value from
	 * drop-down
	 */
	public String clickPressingControlKey(String object, String data) {
		logger.debug("Clicking on any element");
		try {
			Actions act = new Actions(driver);
			Action pressControlKey = act.keyDown(Keys.CONTROL).build();
			pressControlKey.perform();
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			ele.click();
			pressControlKey = act.keyUp(Keys.CONTROL).build();
			pressControlKey.perform();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " " + e.getMessage()
					+ " Not able to click";
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * Added By Karan Sood Date : 12/03/2014 This Method verifies there are No
	 * Selected Values in drop-down
	 */
	public String verifyNoSelectedValueInDD(String object, String data) {

		logger.debug("Entered into verifyNoSelectedValueInDD()");
		try {
			WebElement list = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			Select sel = new Select(list);
			List<WebElement> allOptions = sel.getAllSelectedOptions();
			int size = allOptions.size();
			if (size > 0) {
				return Constants.KEYWORD_FAIL
						+ " -- Already Selected Value in Drop-down  ";
			} else {
				return Constants.KEYWORD_PASS
						+ " -- No Selected Value in Drop-Down ";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " - Could not select from list. "
					+ e.getMessage();
		}
	}

	/**
	 * This method clicks on Select File(Upload File) button in Mozilla
	 * 
	 * Added By Sumit Aggarwal 29/05/2014
	 * 
	 * @param object
	 *            : Element xpath is required.
	 * 
	 * @param data
	 *            :not require
	 * 
	 * 
	 * @return <b>PASS</b> if element is clicked <b>FAIL</b> otherwise.
	 * */

	public String uploadClick(String object, String data) {
		try {
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			element.click();
			return Constants.KEYWORD_PASS + " element is clicked.";
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            :It accepts Three parameter with comma separated. 1) File path
	 *            after download Files 2) Evidence 3) File name that you have
	 *            uploaded
	 * 
	 * 
	 * @throws IOException
	 * */
	/*
	  public String VerifyTextUnderHeader(String object,String data) throws
	  IOException { FileInputStream fis=null; try{ String
	  temp[]=data.split(","); String path=dirName+File.separator+temp[0];

	  File file=new File(path); fis=new
	  FileInputStream(file.getAbsolutePath());

	  XWPFDocument doc = new XWPFDocument(fis);

	  XWPFHyperlink[] links=doc.getHyperlinks(); if(links.length>0) return
	  Constants.KEYWORD_FAIL+"Links Are Present in File";

	  XWPFParagraph[] p= doc.getParagraphs(); int i= p.length; for(int
	  j=0;j<i;j++){ String st= p[j].getParagraphText();
	  if(st.trim().equals(temp[1].trim())){ for(int k=j+1;k<i;k++){ String s1t=
	  p[k].getParagraphText(); logger.debug("Header Text"+s1t);
	  if(p[k].getCTP().getHyperlinkArray().length<1)
	  if(s1t.trim().equals(temp[2].trim())){ logger.debug("text Found"); {

	  return
	  Constants.KEYWORD_PASS+"Text is Present Under Evidence and is not hyperLink "
	  ; }

	 } } } }


	  return Constants.KEYWORD_FAIL+"Text is not present"; } catch(Exception
	  ex){ return Constants.KEYWORD_FAIL; } finally{ fis.close(); } }
	 */
	/**
	 * Added By Pankaj Dogra 27/05/2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            :It accepts Three parameter with comma separated. 1) File path
	 *            after download Files 2) Enter text you have entered in
	 *            Narrative Pane 3) File name that you have uploaded
	 * 
	 * 
	 * @throws IOException
	 * */
	/*

	 * public String VerifyTextWithHeader(String object,String data) throws
	 * IOException{ FileInputStream fis=null; try{ String
	 * temp[]=data.split(","); String path=dirName+File.separator+temp[0];
	 * 
	 * File file=new File(path); fis=new
	 * FileInputStream(file.getAbsolutePath());
	 * 
	 * XWPFDocument doc = new XWPFDocument(fis);
	 * 
	 * XWPFParagraph[] p= doc.getParagraphs();
	 * 
	 * 
	 * XWPFHyperlink[] links=doc.getHyperlinks(); if(links.length>0) return
	 * Constants.KEYWORD_FAIL+"Links Are Present in File"; int i= p.length;
	 * for(int j=0;j<i;j++) { String st= p[j].getParagraphText();
	 * 
	 * if(p[j].getCTP().getHyperlinkArray().length<1)
	 * if(st.trim().contains(temp[1].trim())&&st.trim().contains(temp[2]))
	 * return
	 * Constants.KEYWORD_PASS+"Text is Present and there is no hyperLink";
	 * 
	 * } return Constants.KEYWORD_FAIL+"Text is Not Present";
	 * 
	 * 
	 * 
	 * } catch(Exception ex){ return Constants.KEYWORD_FAIL; }
	 * 
	 * }

	 */
	/**
	 * Added By Mayank Saini 02/05/2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            :It accepts 1 parameter with comma separated. 1) File path
	 *            after download Files
	 * @throws IOException
	 */

	public String verifyLinksInDoc(String object, String data)
			throws IOException {
		FileInputStream fis = null;
		try {

			String path = dirName + File.separator + data;

			File file = new File(path);
			fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument doc = new XWPFDocument(fis);
			XWPFHyperlink[] links = doc.getHyperlinks();

			if (links.length > 0)
				return Constants.KEYWORD_FAIL + "Links are Present";
			else
				return Constants.KEYWORD_PASS + "Links are not Present";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getMessage();
		} finally {
			fis.close();
		}
	}

	/**
	 * This method verifies that a particular file is open
	 * 
	 * Added By Sumit Aggarwal 29/05/2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            :path of file after downloadFiles folder
	 * 
	 * 
	 * @return <b>PASS</b> if file is open <b>FAIL</b> otherwise.
	 * 
	 * @throw FileNotFoundException
	 * */

	public String verifyFileIsOpen(String object, String data) {

		String filePath = dirName + File.separator + data;

		try {
			randomAccess = new RandomAccessFile(filePath, "rw");
			FileChannel channel = randomAccess.getChannel();
			FileLock lock = channel.lock();
			lock.release();
			channel.close();
			return Constants.KEYWORD_FAIL + "File not open";
		} catch (FileNotFoundException e) {
			// If File is Open
			// e.printStackTrace();
			return Constants.KEYWORD_PASS + "File is open";
		} catch (Exception e1) {
			// If File is Open
			e1.printStackTrace();
			return Constants.KEYWORD_FAIL + "File not found";
		}

	}

	/*
	 * Created By Pankaj dogra on January 07,2013 This method verifies either
	 * Role/users is already created or not. For Verification of Roles : This
	 * method used xpath of Message text and roles header . And for verificatio
	 * of Users , This method needs xpath of Browse header and Message text
	 * which populate when pid has already used .
	 */
	public String verifyCreateRoleAndUser(String object, String data) {
		logger.debug("verifyCreateUser method");
		try {

			String objArr[] = object.split(Constants.Object_SPLIT);
			final String errMsg = objArr[0];
			final String brwseHdr = objArr[1];

			String expected = data.trim();
			List<WebElement> element = explictWaitForElementList(errMsg);
			List<WebElement> element1 = explictWaitForElementList(brwseHdr);
			if (element.size() > 0) {

				return Constants.KEYWORD_PASS + "--" + "User with pid/Role "
						+ expected + " has already created";
			} else if (element1.size() > 0) {
				return Constants.KEYWORD_PASS + "-- "
						+ " User has created Successfully with " + expected
						+ " pid/Role";
			}

			else {
				return Constants.KEYWORD_FAIL + "-- "
						+ " User has not  created Successfully with "
						+ expected + " pid/Role";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to create user"
					+ e.getMessage();
		}
	}

	/**
	 * Added By Mayank Saini 02/05/2014 This method will check the pdfHyperLinks
	 * on the pdf file
	 * 
	 * * @param object : not require.
	 * 
	 * @param data
	 *            :It accepts 3 parameter with comma separated.1) File path
	 *            after download Files 2)Particular Page number that you want to
	 *            access 3)Text Of the Link
	 * @throws IOException
	 */

	public String getHyperLinkText(String object, String data) {
		PdfReader pdfreader = null;
		ArrayList<Float> l5 = new ArrayList<Float>();
		List<String> links = new ArrayList<String>();
		try {
			String temp[] = data.split(",");
			int page = Integer.parseInt(temp[1]);

			String path = dirName + File.separator + temp[0];
			pdfreader = new PdfReader(path);
			ArrayList<PdfImportedLink> l1 = pdfreader.getLinks(page);

			int total_link = l1.size();
			while (total_link > 0) {
				Boolean flag = false;
				int xposn = 0;
				int yposn = 0;
				String str = l1.get(total_link - 1).toString();
				for (int i = 0; i < str.length(); i++) {
					char c = str.charAt(i);
					if (c == '[') {
						xposn = i + 1;
						for (int j = i; j < str.length(); j++) {
							char m = str.charAt(j);
							if (m == ']') {
								yposn = j;
								flag = true;
								break;
							}
						}
					}
					if (flag) {
						break;
					}
				}
				logger.debug("1st subsplit: " + xposn + "//" + "2nd subsplit: "
						+ yposn);
				str = str.substring(xposn, yposn);

				String all_cordinates[] = str.split(" ");
				for (int m = 0; m < all_cordinates.length; m++) {
					String j = all_cordinates[m];
					float coordinates = Float.parseFloat(j);
					l5.add(coordinates);

				}

				Rectangle rect = new Rectangle(l5.get(0), l5.get(1), l5.get(2),
						l5.get(3));
				l5 = new ArrayList<Float>();
				RenderFilter filter = new RegionTextRenderFilter(rect);
				TextExtractionStrategy strategy;

				strategy = new FilteredTextRenderListener(
						new LocationTextExtractionStrategy(), filter);
				String text = PdfTextExtractor.getTextFromPage(pdfreader, page,
						strategy);
				links.add(text);
				total_link--;
			}

			Iterator<String> ite = links.iterator();
			while (ite.hasNext()) {
				String text = ite.next();
				logger.debug("Actual text" + text);

				if (text.trim().equals(temp[3])) {
					logger.debug("Expected text" + temp[3]);
					return Constants.KEYWORD_PASS + "Links Text Matched";
				}
			}

			return Constants.KEYWORD_FAIL + "Links Text Not Matched";

		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + " -- Not able to Find Link"
					+ ex.getMessage();
		} finally {
			pdfreader.close();
		}
	}

	/**
	 * dragAndDropFileInNarrative this method is used to drag and drop file in
	 * narrative text area
	 * 
	 * Anil Kumar Mishra Date: 06/25/14
	 * 
	 * @param object
	 *            Order of xpath should be this ---
	 *            xpath[0]=allfilename_lnk(xpath which contains all file name in
	 *            evidence which are added by you) xpath[1]=richtext_link
	 *            xpath[2]=source_btn icon xpath[3]=frame_textarea TextArea
	 *            where file dropped xpath[4]=Plain Text link
	 * @param data
	 *            in which we pass text and file name in which file name comma
	 *            seprated i.e text |File1,File2 means text and file are
	 *            seprated with pipe
	 * @return
	 */
	public String dragAndDropFileInNarrative(String object, String data) {
		logger.debug("drag and drop narrative text");
		logger.debug("Data: " + data);
		try {
			StringBuffer finalValue = new StringBuffer("");
			String xpath[] = object.split(Constants.Object_SPLIT);
			String data_split[] = data.split(Constants.DATA_SPLIT);
			String textname = data_split[0];
			String arr[] = data_split[1].split(Constants.Object_SPLIT);
			ArrayList<String> all_Files = new ArrayList<String>();
			WebElement foundElement = null;
			int flag = 0;
			List<WebElement> allfiles = driver.findElements(By.xpath(OR
					.getProperty(xpath[0])));
			int allfilesize = allfiles.size();
			for (int i = 0; i <= allfilesize - 1; i++) {
				all_Files.add(allfiles.get(i).getText().trim());
			}

			if (allfilesize > 0) {

				wait.until(
						explicitWaitForElement(By.xpath(OR
								.getProperty(xpath[1])))).click();
				wait.until(
						explicitWaitForElement(By.xpath(OR
								.getProperty(xpath[2])))).click();
				for (int j = 0; j <= arr.length - 1; j++) {
					if (all_Files.contains(arr[j].trim())) {
						for (int i = 0; i <= allfilesize - 1; i++) {
							if (allfiles.get(i).getText()
									.equalsIgnoreCase(arr[j].trim())) {
								foundElement = allfiles.get(i);
								flag = 1;
								break;
							}
						}
						if (flag == 1) {
							String filehref = foundElement.getAttribute("href");
							String onstarttagevent = foundElement
									.getAttribute("ondragstart");
							String id = foundElement.getAttribute("id");
							System.out.println(onstarttagevent);
							System.out.println(id);
							StringBuffer dropfilename = new StringBuffer(
									"<a href=\"" + filehref + "\"" + " id=\""
											+ id + "\" ondragstart=\""
											+ onstarttagevent + "\">" + arr[j]
													+ "</a>");

							finalValue.append(dropfilename);
						}

					}
				}
				System.out.println(finalValue);
				wait.until(
						explicitWaitForElement(By.xpath(OR
								.getProperty(xpath[3])))).clear();
				new KeywordEventsUtill().writeInInput(xpath[3], "<p>"
						+ textname + finalValue + "</p>");
				wait.until(
						explicitWaitForElement(By.xpath(OR
								.getProperty(xpath[2])))).click();
				wait.until(
						explicitWaitForElement(By.xpath(OR
								.getProperty(xpath[4])))).click();
			} else {
				return Constants.KEYWORD_FAIL + " No File Found ";
			}
			return Constants.KEYWORD_PASS + "dropped successfully";
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to dropped file "
					+ e.getMessage();
		}

	}

	/**
	 * @author Anil Reddy[10th July, 2014] This method verifies whether a
	 *         weblement is present on the page or not without handling any
	 *         pagination
	 * 
	 * @param object
	 * @param data
	 * @return
	 */
	public String verifyElement(String object, final String data) {
		logger.debug("inside verifyElementIsPresent..");
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			final String element_Start = element[0];
			final String element_End = element[1];

			while (true) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(explicitwaitTime, TimeUnit.SECONDS)
						.pollingEvery(pollingTime, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class);

				List<WebElement> element_List = wait
						.until(new Function<WebDriver, List<WebElement>>() {

							@Override
							public List<WebElement> apply(WebDriver driver) {
								return driver.findElements(By.xpath(OR
										.getProperty(element_Start)
										+ data
										+ OR.getProperty(element_End)));
							}

						});

				if (element_List.size() > 0) {
					return Constants.KEYWORD_PASS + " element is present";
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}

			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}

	/**
	 * @author Anil Reddy[11th July, 2014] This method is used to copy a text
	 *         from a file and paste it in a text field or input box
	 * @param object
	 *            [Pass object as location of the text area or input box]
	 * @param data
	 *            [File name]
	 * @return
	 */
	/* Modified by Shweta Gupta on 10/07/2015 to clear the input */
	
	public String copyAndPasteText(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {

			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp = data;
			String file_exist_path = currDir + sep + "externalFiles" + sep
					+ "uploadFiles" + sep + temp.trim();
			String file_content = null;

			File file = new File(file_exist_path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			file_content = stringBuffer.toString();

			StringSelection copyText = new StringSelection(file_content);
			Clipboard clipboard = Toolkit.getDefaultToolkit()
					.getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			element.clear();
			element.sendKeys(Keys.LEFT_CONTROL + "v");
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			// e.printStackTrace();

			return Constants.KEYWORD_FAIL + " Unable to copy&paste"
			+ e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}

	/**
	 * @author Anil Reddy[11th July, 2014] This method is used to get text from
	 *         afile and verify it on a particular location
	 * @param object
	 *            [Pass object as location of the text area or input box]
	 * @param data
	 *            [File name]
	 * @return
	 */
	public String verifyTextFromFile(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {

			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp = data;
			String file_exist_path = currDir + sep + "externalFiles" + sep
					+ "uploadFiles" + sep + temp.trim();
			String file_content = null;

			File file = new File(file_exist_path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			file_content = stringBuffer.toString();

			String actual_txt = element.getAttribute(OR
					.getProperty("ATTRIBUTE_VALUE"));
			if (!(actual_txt == null)) {
				if (actual_txt.equals(file_content.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";
			} else {
				actual_txt = element.getText().trim();
				if (actual_txt.equals(file_content.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";
			}

		}

		catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			// e.printStackTrace();

		}
		return Constants.KEYWORD_FAIL + "--Text not matched";
	}

	/**
	 * Added By Pankaj Dogra 09/07/2014 This method will read the XLSX file and
	 * match the content of the file with given data
	 * 
	 * @param object
	 *            : not require
	 * 
	 * @param Data
	 *            : From data; you can verify multiple values with comma
	 *            seprated. First name will contains name of the file then you
	 *            can use Multiple values with comma seprated. For ex :
	 *            Report.xlsx,Firstvalue,Secondvalue,Third value
	 * @throws IOException
	 * 
	 */

	public String verifyTextInXlsxFile(String object, String data)
			throws IOException {
		FileInputStream fis = null;
		try {

			int counter = 0;
			ArrayList<String> ar = new ArrayList<String>();
			String temp[] = data.split(",");
			int size = (temp.length - 1);
			String path = dirName + File.separator + temp[0];
			File file = new File(path);
			fis = new FileInputStream(file.getAbsolutePath());
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet worksheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = worksheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cell.getStringCellValue();
					ar.add(cell.toString());
				}
			}
			for (int j = 0; j < size; j++) {
				for (int i = 0; i < ar.size(); i++) {
					if (temp[j + 1].trim().toString().equals(ar.get(i))) {
						counter++;
						break;
					} else {
						continue;
					}
				}
			}

			if (counter == size) {
				return Constants.KEYWORD_PASS + "---All Texts are prent";
			}
		}

		catch (Exception e) {
			System.out.println(e);
		} finally {
			fis.close();
		}
		return Constants.KEYWORD_FAIL;
	}

	/**
	 * Added By Pankaj Dogra on 09/07/2014
	 *  This method will check the "write"
	 * permission in the XLSX file and then Write the data under XLSX file and
	 * also check whether the data write in the sheet or not.
	 * 
	 * @param object
	 *            : not require
	 * 
	 * @param Data
	 *            : Two values should be pass using comma seprated. First will
	 *            contains name of the file Second should be what data you want
	 *            to write in sheet For ex : Report.xlsx,Firstvalue Here
	 *            "Report.xlsx" is the file name and "Firstvalue" is data that
	 *            will write under First row and First cell.
	 * @throws IOException
	 * @throws Exception
	 *             if File is not in write mode.
	 */
	public String writeInXlsxFile(String object, String data)
			throws IOException {
		FileInputStream fis = null;
		try {

			XSSFWorkbook workbook = null;
			XSSFSheet my_worksheet = null;
			Cell cell = null;
			String temp[] = data.split(",");
			String path = dirName + File.separator + temp[0];
			File file = new File(path);
			if (file.canWrite()) {
				fis = new FileInputStream(file.getAbsolutePath());
				workbook = new XSSFWorkbook(fis);
				my_worksheet = workbook.getSheetAt(0);
				cell = my_worksheet.getRow(0).getCell(0);
				cell.setCellValue(temp[1]);
				fis.close();
			}
			FileOutputStream outFile = new FileOutputStream(
					file.getAbsolutePath());
			workbook.write(outFile);
			outFile.close();

			fis = new FileInputStream(file.getAbsolutePath());
			workbook = new XSSFWorkbook(fis);
			my_worksheet = workbook.getSheetAt(0);
			cell = my_worksheet.getRow(0).getCell(0);
			if (cell.getStringCellValue().toString().trim()
					.equals(temp[1].trim())) {
				return Constants.KEYWORD_PASS + "---Text " + temp[1]
						+ "--has been written in sheet";
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			fis.close();
		}
		return Constants.KEYWORD_FAIL;
	}

	/**
	 * This method is used to Verify the Character Length of a textbox to verify
	 * that not more than 255 characters can be entered in a textbox if
	 * "maxlength" attribute of object is "255". This method takes two arguments
	 * "Object" and "Data" Data should be same data that we have used earlier to
	 * write in a tetxbox (ie. more than "255" characters), as this method is
	 * not writing any data to textbox. It compares current characters in
	 * textbox (ie. 255) with the data we pass (ie. more than 255)
	 * 
	 * @uthor:Sanjay Sharma
	 * @Date: 7 july 2014
	 * @param object
	 * @param data
	 * @return
	 */

	public String verifyMaxinInput(String object, String data) {
		try {

			logger.debug("Count Characters inside Input box");

			int expectedlength = data.length();

			// It takes actual value from the value attribute

			String actual = wait.until(
					explicitWaitForElement(By.xpath(OR.getProperty(object))))
					.getAttribute("value");
			int actuallength = actual.length();

			logger.debug("actuallength: " + actuallength);
			logger.debug("expectedlength: " + expectedlength);

			if ((actuallength) >= (expectedlength)) {
				return Constants.KEYWORD_FAIL + "-- You Can enter more than "
						+ expectedlength + " characters";
			} else
				return Constants.KEYWORD_PASS
						+ "-- You Cann't enter more than " + actuallength
						+ " characters";
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " --object not found"
					+ e.getMessage();
		}
	}

	/**
	 * Added By Karan Sood Date: 7th July 2014
	 * 
	 * This method is used to hit up arrow key from keyboard
	 * 
	 * @param object
	 *            : Element xPath is Required
	 * 
	 * @param data
	 *            : not require.
	 * */

	public String hitUpwardArrow(String object, String data) {
		logger.debug("Click Upward Arrow in text box");

		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR
					.getProperty(object))));
			ele.sendKeys(Keys.ARROW_RIGHT);
		}

		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		}

		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " " + e.getLocalizedMessage();
		}

		return Constants.KEYWORD_PASS;

	}

	/**
	 * 07/15/2014 Added By Mayank Saini This method is used to change the file
	 * name as well as extension programitcally
	 * **/
	public String changeFileName(String object, String data) {
		try {
			String temp[] = data.split(Constants.DATA_SPLIT);
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String file_exist_path = currDir + sep + "externalFiles" + sep
					+ "uploadFiles" + sep;
			File oldFile = new File(file_exist_path + temp[0]);
			File newFile = new File(file_exist_path + temp[1]);

			if (!oldFile.exists())
				return Constants.KEYWORD_FAIL;

			if (oldFile.renameTo(newFile))
				return Constants.KEYWORD_PASS
						+ "File name changed successfully";

			else
				return Constants.KEYWORD_FAIL + "Error in changing fileName";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + " " + ex.getLocalizedMessage();
		}
	}

	/**
	 * @date 23rd July 2014
	 * @author Sanjay Sharma
	 * @param object
	 *            OR file
	 * @return all xpaths of the given OR (Common + Specific)
	 * @throws IOException
	 */
	public List<String> getXPathsFromOR(String object) throws IOException {
		List<String> allxPathkeys = new ArrayList<String>();
		try {
			// To read Specific_OR file
			FileReader fr = new FileReader(object);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#") || line.isEmpty()
						|| line.startsWith("!") || line.startsWith("<")
						|| line.startsWith(">") || line.startsWith("=")) {
					allxPathkeys.remove(line);

				} else {
					String split2[] = line.split("=");// splits the line read on
					// the basis of "="and
					// put the keys in the
					// ArrayList named keys.
					allxPathkeys.add(split2[0]);
				}
			}

			// Modified by Sanjay Sharma on 06/10/2014 to check xpaths for
			// Specific Tab Common OR
			// To read Specific common_OR file
			FileReader sp_common_fr = new FileReader(
					System.getProperty("user.dir")
					+ "/externalFiles/Object_Repository/"
					+ CONFIG.getProperty(moduleName) + File.separator
					+ moduleName + "_Common_OR.properties");
			BufferedReader sp_common_br = new BufferedReader(sp_common_fr);
			String sp_common_line;
			while ((sp_common_line = sp_common_br.readLine()) != null) {
				sp_common_line = sp_common_line.trim();
				if (sp_common_line.startsWith("#") || sp_common_line.isEmpty()
						|| sp_common_line.startsWith("!")
						|| sp_common_line.startsWith("<")
						|| sp_common_line.startsWith(">")
						|| sp_common_line.startsWith("=")) {
					allxPathkeys.remove(sp_common_line);
				} else {
					String common_split2[] = sp_common_line.split("=");
					allxPathkeys.add(common_split2[0]);
				}
			}
			sp_common_br.close();

			// To read common_OR file
			FileReader common_fr = new FileReader(""
					+ System.getProperty("user.dir")
					+ "/externalFiles/Object_Repository/Common"
					+ "_OR.properties");
			BufferedReader common_br = new BufferedReader(common_fr);
			String common_line;
			while ((common_line = common_br.readLine()) != null) {
				common_line = common_line.trim();
				if (common_line.startsWith("#") || common_line.isEmpty()
						|| common_line.startsWith("!")
						|| common_line.startsWith("<")
						|| common_line.startsWith(">")
						|| common_line.startsWith("=")) {
					allxPathkeys.remove(common_line);
				} else {
					String common_split2[] = common_line.split("=");
					allxPathkeys.add(common_split2[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allxPathkeys;
	}

	/**
	 * @date 23rd July 2014
	 * @author Sanjay Sharma
	 * @param moduleName Tabspecific module
	 * @return All method names which exists in the Java file
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException 
	 */
	public ArrayList<String> getAllMethodsFromFile(String moduleName)throws SecurityException, ClassNotFoundException, NoSuchMethodException {
		String normalPackagePath;
		File[] files;
		Method[] method;
		Method[] allClassSpecificsMethods = null;
		Package[] pac = Package.getPackages();
		ArrayList<String> paclist = new ArrayList<String>();
		String normalPacPath = null;
		for (int i = 0; i < pac.length; i++) {
			if (pac[i].getName().endsWith("base")
					&& pac[i].getName().startsWith("com.tk20")) {
				paclist.add(pac[i].getName());
				normalPacPath = pac[i].getName();
				break;
			}
		}
		normalPacPath = normalPacPath.substring(0, normalPacPath.length() - 4);
		normalPacPath = normalPacPath.concat("test");
		normalPacPath = normalPacPath.replace(".", "/");
		File file1 = new File(System.getProperty("user.dir") + "/src/"+ normalPacPath);
		normalPacPath = normalPacPath.replace("/", ".");
		String[] directories = file1.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return new File(dir, name).isDirectory();
			}
		});
		String name = DataProvider.CONFIG.getProperty(moduleName).toLowerCase();
		for (String dir : directories) {
			name = name.replaceAll("_", "");
			if (dir.equalsIgnoreCase(name)) {
				normalPacPath = normalPacPath.concat(".");
				normalPacPath = normalPacPath.concat(dir);
				break;
			}
		}
		normalPackagePath = normalPacPath.replace(".", "/");
		method = (Method[]) ArrayUtils.addAll(DataProvider.util_method,	DataProvider.appSpecificUtilMethod);
		method = (Method[]) ArrayUtils.addAll(method,accessibilityKeyEvents.getClass().getMethod("checkAccessibilityError", String.class,String.class)); // Code to Add Method checkAccessibilityError 

		String abosolutePackagePath = System.getProperty("user.dir") + "/src/"+ normalPackagePath;
		File f = new File(abosolutePackagePath);
		files = f.listFiles();
		Method[] singleClassSpecificMethods;
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith(".java")) {
				String className = normalPackagePath+ "/"+ files[i].getName().substring(0,files[i].getName().length() - 5);
				className = className.replace("/", ".");
				singleClassSpecificMethods = Class.forName(className).getMethods();
				allClassSpecificsMethods = ArrayUtils.addAll(allClassSpecificsMethods, singleClassSpecificMethods);
			}
		}
		method = (Method[]) ArrayUtils.addAll(method, allClassSpecificsMethods);
		ArrayList<String> allMethods = new ArrayList<String>();
		for (int i = 0; i < method.length; i++) {
			allMethods.add(method[i].getName().trim());
		}
		return allMethods;
	}
	/**
	 * Added By Pankaj Dogra 23/07/2014 This method return the folder name of
	 * the file.
	 * 
	 * @param object
	 *            : not require
	 * 
	 */
	public String getModuleName(String suite_Name) {
		String suiteFullName = suite_Name + ".xls";
		String sm = suite_Name.split("_")[0];
		char[] md;
		// String empty = "null";
		ArrayList<String> probFolder = new ArrayList<String>();
		String finalFold = null;
		md = sm.toLowerCase().toCharArray();
		File dirFile = new File(System.getProperty("user.dir")+ "/externalFiles/xls");
		int size = md.length;
		int counter = 0;
		File[] files = dirFile.listFiles();
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		files = dirFile.listFiles(fileFilter);
		// System.out.println(files.length);
		if (files.length == 0) {
			System.out
			.println("Either dir does not exist or is not a directory");
		} else {
			for (int i = 0; i < files.length; i++) {
				File filename = files[i];
				String path = filename.toString().replace("\\", "/");
				String path1[] = path.split("/");
				int arrSize1 = path1.length;
				String folderName = path1[arrSize1 - 1];
				String newfolder = folderName.toLowerCase();
				// System.out.println(filename.toString());
				counter = 0;
				for (int c = 0; c < size; c++) {
					char a = md[c];
					if (!(newfolder.indexOf(a) == -1)) {
						counter++;
					}

				}
				if (counter == size) {
					probFolder.add(folderName);
				}
			}
			// System.out.println(probFolder);
			boolean abcd = false;

			if (probFolder.size() > 0) {
				for (int l = 0; l < probFolder.size(); l++) {
					dirFile = new File(System.getProperty("user.dir")
							+ "/externalFiles/xls/" + probFolder.get(l));
					String[] directories = dirFile.list(new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return new File(dir, name).isDirectory();
						}
					});
					if (directories.length > 1) {
						for (String dir : directories) {
							if (new File(dirFile + "/" + dir + "/"
									+ suiteFullName).exists()) {
								abcd = true;
								finalFold = probFolder.get(l);
								return finalFold;
							}
						}
					} else {
						if (new File(dirFile + "/" + suiteFullName).exists()) {
							finalFold = probFolder.get(l);
							abcd = true;
							return finalFold;

						}
					}
					if (abcd) {
						finalFold = probFolder.get(l);
						return finalFold;
					}
				}
			}
		}

		return finalFold;
	}

	/**
	 * Mayank Saini 14/05/2014
	 * 
	 * This method is used to get the text of input box if the text box has no
	 * value attribute
	 * */
	public String verifyTextUsingJs(String object, String data) {
		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String result = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);
			if (result.trim().equals(data.trim())) // modified Added trim()
				return Constants.KEYWORD_PASS + data+" -- Value is present -- ";
			else
				return Constants.KEYWORD_FAIL + data+" -- Value is not present -- ";
		}

		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + ex.getCause();
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + " Object not found "
					+ ex.getMessage();
		}
	}

	/**
	 * Added By Vikas Bhadwal 24/07/2014
	 * 
	 * @param object
	 *            :Comman xpath for all the entries.
	 * 
	 * 
	 * @param data
	 *            :None
	 * 
	 */

	public String verifyAlphabeticOrderList(String object, String data) {
		try {
			Boolean flag = true;

			List<WebElement> list = explictWaitForElementList(object);

			for (int i = 0; i < list.size() - 1; i++) {

				if ((list.get(i).getText().trim()).compareToIgnoreCase((list
						.get(i + 1)).getText().trim()) > 0) {
					flag = false;
					break;
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS
						+ "Values are in  Alphabetically order";
			else
				return Constants.KEYWORD_FAIL
						+ "Values are not in Alphabetically Selected";
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();

		}
	}

	/**
	 * Added By Vikas Bhadwal 30/07/2014
	 * 
	 * @param object
	 *            :xpath of Input Box
	 * 
	 * 
	 * @param data
	 *            :Maximum input size.
	 * 
	 */


	public String verifyTextLenghtInInput(String object, String data) {
		logger.debug("Verifying the text in input box");
		try {

			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String actual = ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
			int max_size = Integer.parseInt(data);
			int actual_size = actual.length();
			logger.debug("actual: " + actual_size);
			logger.debug("expected: " + max_size);
			if (max_size == actual_size) {
				return Constants.KEYWORD_PASS;
			} else {
				logger.debug("actual- " + actual);
				return Constants.KEYWORD_FAIL + " Not matching ";
			}


		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to find input box "
					+ e.getMessage();

		}
	}
	/**
	 * Added By Karan Sood Date: 28th July 2014
	 * 
	 * This method is used copy a url and open it in new tab.
	 * 
	 * @param object
	 *            : Element xPath is Required from where the url to be copied
	 * 
	 * @param data
	 *            : not require.
	 * */

	public String copyLinkAndOpenInNewTab(String object, String data) {

		try {

			WebElement ele=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String url = ele.getText();
			if(url.isEmpty())//Added by Sanjay on 09/06/2015, for Attribute value
			{
				url = ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
			}
			if (CONFIG.getProperty("browserType").equals("Mozilla")
					|| CONFIG.getProperty("browserType").equals("IE")) {
				String ctrlT = Keys.chord(Keys.CONTROL, "t");
				wait.until(explicitWaitForElement(By.cssSelector("body")))
				.sendKeys(ctrlT);
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				((JavascriptExecutor) driver).executeScript("window.open()");
				keyUtil.moveToNewWindow(object, data);

			}

			driver.get(url);
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to open new Tab "
					+ e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--" ;

	}

	/**
	 * Added By Karan Sood Date: 28th July 2014
	 * 
	 * This method is used Close the opened tab and get back focus on previous
	 * tab.
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            : not require.
	 * */

	public String closeOpenedTab(String object, String data) {

		try {

			if (CONFIG.getProperty("browserType").equals("Mozilla")
					|| CONFIG.getProperty("browserType").equals("IE")) {
				String ctrlW = Keys.chord(Keys.CONTROL, "w");
				wait.until(explicitWaitForElement(By.cssSelector("body")))
				.sendKeys(ctrlW);
			}

			else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				keyUtil.handleWindows(object, data);
			}
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to Close opened Tab "
					+ e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--" + data;

	}

	/**
	 * @since 24/Jan/14
	 * @author Sandeep Dhamija click on a webelement if element is present,
	 *         proceed otherwise.
	 * @object - xpath of element
	 * @data - none
	 */

	public String clickElementIfPresent(String object, String data) {
		logger.debug("Clicking on element ");
		try {

			List<WebElement> element = explictWaitForElementList(object);

			if (element.size() > 0) {
				element.get(0).click();

				return Constants.KEYWORD_PASS + " " + "Clicked on element :";
			} else {
				return Constants.KEYWORD_PASS + " "
						+ "element is not present, proceed to next step ";
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL
					+ " -- Not able to click on element -" + e.getMessage();
		}
	}

	/**
	 * Added By Sanjay Sharma Date: 22nd Sep 2014
	 * 
	 * This method is used to verify Date Time Format after splitting the date
	 * time.
	 * 
	 * @param object
	 *            : xpath of cell contains date time.
	 * 
	 * @param data
	 *            : Time format you want to verify. e.g mm/dd/yyyy
	 * */
	public String verifyDateTimeFormatBySplit(String object, String data) {
		logger.debug("verifying the date-time format");

		String actual_pattern = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
		String values[] = actual_pattern.split(Constants.NEW_LINE);
		String actualValue = values[0].trim();
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
	 * Added By Sanjay Sharma Date: 22nd Sep 2014
	 * 
	 * This method is used to Check checkbox after verifying the row attribute
	 * row-selected.
	 * 
	 * @param object
	 *            : it take Two Xpaths 1)Row that contains checkbox. 2)xpath of
	 *            checkbox you want to select
	 * 
	 * @param data
	 *            : not require.
	 * */
	public String checkCheckBoxWithDiv(String object, String data) {
		logger.debug("Checking checkCheckBoxWithDiv");
		WebElement element = null;
		WebElement element1 = null;
		try {
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			element = driver.findElement(By.xpath((xpathStart)));
			element1 = driver.findElement(By.xpath((xpathEnd)));
			String checked = element.getAttribute(OR
					.getProperty("ATTRIBUTE_ROW_SELECTED"));

			if (checked == null) { // checkbox is unchecked
				element1.click();
				return Constants.KEYWORD_PASS + " clicked on check-box";
			} else {
				return Constants.KEYWORD_PASS + " check-box is already checked";
			}
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (WebDriverException ex) {
			try {
				String exceptionMessage = ex.getMessage();
				if (exceptionMessage
						.contains("Element is not clickable at point")) {
					if (new ApplicationSpecificKeywordEventsUtil().clickJs(
							element1).equals(Constants.KEYWORD_PASS))
						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL;
				} else
					return Constants.KEYWORD_FAIL + "not able to Click"
					+ ex.getMessage();
			} catch (Exception e) {
				return Constants.KEYWORD_FAIL + e.getMessage();
			}

		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link"
					+ e.getMessage();
		}
	}

	/**
	 * This method is used to press BackSpace key from keyboard
	 * 
	 * @author Sanjay Sharma
	 * 
	 * @param object
	 *            : Not Reqiured
	 * 
	 * @param data
	 *            : Not Reqiured
	 * 
	 * @since 29 sep, 2014
	 */
	public String pressBackSpaceFromKeyboard(String object, String data) {
		logger.debug("entered into pressBackSpaceFromKeyboard");
		try {
			Actions act = new Actions(driver);
			Action pressKey = act.sendKeys(Keys.BACK_SPACE).build();
			pressKey.perform();
			return Constants.KEYWORD_PASS + "--" + "BackSpace key pressed.";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * This Method used to verify current window URL
	 * 
	 * @author Pankaj Sharma
	 * @since 2 April, 2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            : Expected URL.
	 * 
	 * @return <b>PASS</b> if successfully moved to specified window.
	 *         <b>FAIL</b> otherwise.
	 */
	public String verifyCurrentWindowURL(String object, String data)
			throws InterruptedException {
		logger.debug("Move to Specific Windows......");
		try {
			// validate the parameters
			if (data == null) {
				return Constants.KEYWORD_FAIL + " Data is Emplty";
			}
			String currentWindowUrl = driver.getCurrentUrl();
			logger.debug("Actual URL : " + currentWindowUrl.trim());
			logger.debug("Expected URL : " + data.trim());

			if (currentWindowUrl.contains(data.trim())) {
				return Constants.KEYWORD_PASS
						+ "Actual URL contains the Expected URL";
			} else {
				return Constants.KEYWORD_FAIL
						+ "Actual URL do not contains the Expected URL";
			}

		} catch (Exception nse) {

			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		}
	}

	/**
	 * This method is used to press Enter key from keyboard
	 * 
	 * @author Sanjay Sharma
	 * 
	 * @param object
	 *            : Not Reqiured
	 * 
	 * @param data
	 *            : Not Reqiured
	 * 
	 * @since 07 Oct, 2014
	 */
	public String pressEnter(String object, String data) {
		logger.debug("entered into pressEnter");
		try {
			Actions act = new Actions(driver);
			Action pressKey = act.sendKeys(Keys.ENTER).build();
			pressKey.perform();
			return Constants.KEYWORD_PASS + "--" + "Enter key pressed.";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * @since 16/10/2014
	 * @author Pankaj Dogra This method is used to verify either all elements
	 *         having same xpaths is in descending order or not .This method
	 *         will not check Pagination Links on the applications.
	 * @param object
	 *            :Require A common Xpath of all the Elements
	 * @param data
	 *            : Does not Require
	 * @return Pass if all the element sorted in descending order else return
	 *         Fail.
	 */

	public String verifySortingInDescendingWithoutPagination(String object,
			String data) throws Exception {
		logger.debug("Entered into verifySortingInDescendingWithoutPagination()");

		try {
			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			while (true) {
				List<WebElement> expected = explictWaitForElementList(object);
				for (int i = 0; i < expected.size(); i++) {
					if (expected.get(i).getText().trim().length() != 0) {
						actual.add(expected.get(i).getText().trim());
						real.add(expected.get(i).getText().trim());
					}
				}
				break;
			}
			Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(actual);

			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);

			if (real.equals(actual)) {
				return Constants.KEYWORD_PASS
						+ " -- Elements are sorted in Descending Order";
			} else
				return Constants.KEYWORD_FAIL
						+ " -- Elements are not sorted in Descending Order";
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * @since 21/10/2014
	 * @author Sanjay Sharma This method is used to verify Sorting in Ascending
	 *         Order
	 * @param object
	 *            :Require 2 xpaths splitted with comma i.e Start And End Xpath
	 *            of Column values
	 * @param data
	 *            : Data Column Title/Header
	 * @return Pass if all the element sorted in Ascending order else return
	 *         Fail.
	 */

	public String verifySortingInAscendingWithSplit(String object, String data)
			throws Exception {
		logger.debug("Entered into verifySortingInAscendingWithSplit()");

		try {
			String objArr[] = object.split(Constants.Object_SPLIT);

			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			while (true) {

				List<WebElement> expected = driver.findElements(By.xpath(OR
						.getProperty(objArr[0])
						+ data
						+ OR.getProperty(objArr[1])));
				for (int i = 0; i < expected.size(); i++) {
					if (expected.get(i).getText().trim().length() != 0) {
						actual.add(expected.get(i).getText().trim());
						real.add(expected.get(i).getText().trim());
					}
				}
				break;
			}

			Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);

			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);

			if (real.equals(actual)) {
				return Constants.KEYWORD_PASS + " -- Elements are sorted";
			}

			else {
				return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}

	}

	/**
	 * @since 21/10/2014
	 * @author Sanjay Sharma This method is used to verify Sorting in Descending
	 *         Order
	 * @param object
	 *            :Require 2 xpaths splitted with comma i.e Start And End Xpath
	 *            of Column values
	 * @param data
	 *            : Data Column Title/Header
	 * @return Pass if all the element sorted in descending order else return
	 *         Fail.
	 */

	public String verifySortingInDescendingWithSplit(String object, String data)
			throws Exception {
		logger.debug("Entered into verifySortingInDescendingWithSplit()");

		try {
			String objArr[] = object.split(Constants.Object_SPLIT);

			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();
			while (true) {

				List<WebElement> expected = driver.findElements(By.xpath(OR
						.getProperty(objArr[0])
						+ data
						+ OR.getProperty(objArr[1])));
				for (int i = 0; i < expected.size(); i++) {
					if (expected.get(i).getText().trim().length() != 0) {
						actual.add(expected.get(i).getText().trim());
						real.add(expected.get(i).getText().trim());
					}
				}
				break;
			}

			Collections.sort(actual, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(actual);

			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);

			if (real.equals(actual)) {
				return Constants.KEYWORD_PASS + " -- Elements are sorted";
			}

			else {
				return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}

	}


	/**
	 * @since 31/10/2014
	 * @author Pankaj Dogra
	 * @Discription:This method use to Get the Total Count of any data under
	 *                   table row and get total count of Check boxes under
	 *                   table row and
	 * @param object
	 *            :Two xpaths :Common xpaht of all Check boxes under table rows
	 *            Common xpath of all element under table rows and
	 * @param data
	 *            : Does not Require
	 * @return Pass if Total count of Check boxes and Elements under table rows
	 *         are same
	 * @return Fail if Total count of Check boxes and Elements under table rows
	 *         are not same
	 */
	public String checkboxAndDataCount(String object, String data)
			throws Exception {
		logger.debug("Entered into checkboxAndDataCount()");
		try {
			String obj[] = object.split(Constants.Object_SPLIT);
			String checkbox = obj[0];
			String dataCount = obj[1];
			allRows = explictWaitForElementList(dataCount);
			allRows.size();
			allCBs = explictWaitForElementList(checkbox);
			allCBs.size();
			logger.debug("the count of checkboxes is:" + (allCBs.size()));
			if (allCBs.size() == (allRows.size()))
				return Constants.KEYWORD_PASS + "-The Count of Checkbox is-"
				+ allCBs.size() + "-and data is-" + allRows.size()
				+ "-which is same ";
			else {
				return Constants.KEYWORD_FAIL + "-The Count of Checkbox is-"
						+ allCBs.size() + "-and data is-" + allRows.size()
						+ "-which is not same  ";
			}

		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Object Not Found"
					+ e.getMessage();
		}
	}

	/*
	 * Added by Nitin Gupta on 03/11/2014 This method is used to check that
	 * parent element does not have child element
	 * 
	 * @param object:xpath of the element
	 * 
	 * @param data:no data
	 */

	public String parentWithNoChildElement(final String object, String data) {
		logger.debug("Checking for the child element");
		try {

			WebElement rootWebElement = explictWaitForElementUsingFluent(object);

			List<WebElement> childSize =rootWebElement.findElements(By.xpath("//*"));

			if (childSize.size() == 0) {
				logger.debug("Parent has no child");
				return Constants.KEYWORD_PASS + "---"+ "No child exist for parent";
			} else {
				System.out.println(childSize.size());
				return Constants.KEYWORD_FAIL + "--" + "Parent has "+ childSize.size() + " child";
			}
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not traverse webElement "
					+ e.getMessage();
		}

	}
	/**
	 * This method is used to enter Input and Hit Instant Search
	 *
	 * @author Anil Mishra
	 *
	 * @param object
	 *            : xpath of Input
	 *
	 * @param data
	 *            : Value
	 *           
	 * @since 07 Oct, 2014
	 */
	public String instantSearchHitEnter(String object, String data)
	{
		logger.debug("entered into instantSearchHitEnter");

		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			ele.clear();
			ele.sendKeys(data);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, data);
			ele.sendKeys(Keys.RETURN);
		}catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS + "--" + "Enter key pressed.";

	}

	/**
	 * This Method used to move on the specific window .
	 * 
	 * @author Pankaj Sharma
	 * @since 2 April, 2014
	 * 
	 * @param object
	 *            : not require.
	 * 
	 * @param data
	 *            : It accepts the no of window that you want to move.
	 * 
	 * @return <b>PASS</b> if successfully moved to specified window.
	 *         <b>FAIL</b> otherwise.
	 */
	public String moveToSpecificWindow(String object, String data) throws InterruptedException {
		logger.debug("Move to Specific Windows......");
		try {
			// validate the parameters
			if (data == null) {
				return Constants.KEYWORD_FAIL + "Specific Window number is empty";
			}
			int switchToWindow = Integer.parseInt(data);
			String newWindow = "";
			Set<String> windowids = driver.getWindowHandles();
			Iterator<String> ite = windowids.iterator();
			for (int i = 0; i < switchToWindow; i++) {
				newWindow = ite.next();
			}
			driver.switchTo().window(newWindow);
			return Constants.KEYWORD_PASS;
		} catch (NoSuchElementException nse) {

			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();
		}
	}

	/**
	 * Karan Sood 5 November, 2014   This method Before uploading a File Checks whether the same file is 
	 * 									present there or not and if file is not present only then it uploads the file. 
	 * 
	 * @param object: It accepts Five xpaths that are separated by '|'. 
	 * 				  First xpath is of frame where the uploaded files are displayed <br>
	 *                And Second xpath is of the file name Link displaying in above <br>
	 *                     frame [we have to split this xpath by ',' with start and end values]<br>
	 *                And Third xpath is of the other frame where the file will be uploaded<br>
	 *                And Fourth xpath is of the browse button <br>
	 *                And Fifth xpath of upload Button <br>
	 *                
	 *                example : resourceListFrame|fileLink_start,fileLink_end|uploadFileFrame|click_browse|uploadBtn
	 *                
	 * @param data : Have to pass name of the File to be Uploaded [eg- car.jpg]
	 * @return
	 */

	public String firstVerifyThenUpload(String object, String data) {
		try {
			logger.debug("Verifing the File to be uploaded is already present or not");

			String objArr[] = object.split(Constants.DATA_SPLIT);
			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];
			final String XPATH3 = objArr[2];
			final String XPATH4 = objArr[3];
			final String XPATH5 = objArr[4];

			// validate the parameters
			if (XPATH1 == null || XPATH2 == null || XPATH3 == null || XPATH4 == null || XPATH5 == null) {
				return Constants.KEYWORD_FAIL+ " Some Xpath is null. Please check the xpath";
			}

			keyUtil.moveToFrameByXpath(XPATH1, data);
			String res=keyUtil.verifyElementIsPresent(XPATH2, data);
			keyUtil.moveToDefaultFrame(object, data);

			if(res.startsWith(Constants.KEYWORD_PASS))
				return Constants.KEYWORD_PASS + "File Already Present.";
			else
			{
				keyUtil.moveToFrameByXpath(XPATH3, data);
				keyUtil.getFilePath(XPATH4, data);
				keyUtil.clickButton(XPATH5, data);
				keyUtil.moveToDefaultFrame(object, data);
				return Constants.KEYWORD_PASS + "File Uploaded Successfully...";
			}			
		}	

		catch(TimeoutException ex){			
			return Constants.KEYWORD_FAIL+"Cause: "+ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+ " -Some Exception occured "+ e.getMessage();
		}	
	}
	/**
	 * Added By Surender
	 * Date: 12th November 2014
	 * This method enters message(using sendKeys) in FCKeditor box and then switches back to default frame.
	 * @param object : xpath path of FCKeditor box.
	 * @param data : data to be entered in FCKeditor box.
	 * */
	public String enterMessageInFCKeditor(String object, String data) throws InterruptedException
	{
		try {
			logger.debug("entered into enterMessageInFCKeditor()");
			keyUtil.moveToFrameByXpath(object, data);
			WebElement editable = driver.switchTo().activeElement();
			editable.clear();
			editable.sendKeys(data);
			keyUtil.moveToDefaultFrame(object, data);
			logger.debug("In default frame");

		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} 
		catch (Exception e) 
		{
			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
		}
		return Constants.KEYWORD_PASS+ " --"+data;
	}

	/**
	 * Shweta Gupta 12 November,2014 
	 *  This method is used to verify that uiblocker is present or not.
	 *	@param object : xpath path of blocker .
	 * @param data : No data
	 */
	public String verifyBlockerPresent(String object, String data) {
		logger.debug("Verifying the blocker");
		try {
			int actual = driver.findElements(By.xpath((OR.getProperty(object)))).size();

			if (actual>0)
				return Constants.KEYWORD_PASS+"Blocker Present";
			else
				return Constants.KEYWORD_FAIL + "Blocker Not Present";
		} catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}


	/**
	 * This method is used to verify that the Web Element is present .
	 * 
	 * @author : Pankaj Dogra
	 * @since :11/11/2014
	 * 
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br>
	 *            are given as object parameter that are splitted by ","
	 * 
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *            object parameter and if multiple data then we have to write i.e A|B|C
	 * 
	 * @return PASS : If Check box is Selected.
	 * 
	 * @return Fail : Otherwise.
	 */
	public String verifyCheckBoxSelectedUsingData(String object, String data) {
		logger.debug("Entered into isWebElementPresentUsingData()");
		try {
			boolean checked=false,flag=true;
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter

			if (xpathStart == null || xpathEnd == null||data == null) {
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			}

			for(int i=0;i<temp.length;i++) //Modified by sanjay on 04/12/2014
			{
				WebElement ele = driver.findElement(By.xpath((xpathStart) + temp[i] + (xpathEnd)));
				checked = ele.isSelected();
				if (checked)
					flag = true;
				else
				{
					flag = false;
					break;
				}
			}

			if (flag) {
				return Constants.KEYWORD_PASS + " All checkboxes checked";
			} else
				return Constants.KEYWORD_FAIL + "  All checkkboxes not checked";
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}

	}
	/** Added by Shweta Gupta, 11/24/2014
	 * This method will verify that the elements list x-path is not in link form.
	 * 
	 * @param object x-path of the list
	 * @param data tag name of the element. 
	 * 
	 * */
	public String verifyListIsNotLink(String object, String data) {
		logger.debug("Entered into verifyTextIsNotLink()");

		try {
			long implicitWaitTime=Integer.parseInt(CONFIG.getProperty("implicitwait"));
			List<WebElement> element = explictWaitForElementList(object);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			for(int i=0;i<element.size();i++)
			{

				List<WebElement> element1=element.get(0).findElements(By.xpath(OR.getProperty(object)+"//*"));
				for(int j=0;j<element1.size();j++)
				{
					String s=element1.get(0).getTagName();
					if(s.equals(data.trim())){
						driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
						return Constants.KEYWORD_FAIL + " -- Text contains mentioned tag i.e.-- "+data;
					}
				}
			}
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			return Constants.KEYWORD_PASS + " -- Text doesn not contains mentioned tag i.e.-- "+data;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * @since 24/11/2014
	 * @author Sanjay Sharma 
	 * checkCheckBoxUsingData() ,This method is used to check Multiple check boxes By using Data
	 * @param object
	 *            :Require 2 xpaths splitted with comma i.e Start And End Xpath for check box
	 * @param data
	 *            : Data/Name(s) that should be pass to splitted xpath (using "Data_SPLIT)
	 * @return Pass
	 *         Fail.
	 */
	public String checkCheckBoxUsingData(String object,String data) throws InterruptedException {

		logger.debug("Enter Inside 'checkCheckBoxUsingData' method");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter
			if (element_Start == null || element_End == null||data == null) {
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			}
			// Check the Checkbox(es)
			for(int i=0;i<temp.length;i++)
			{
				WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[i] + OR.getProperty(element_End)));
				boolean checked = ele.isSelected();
				if (!checked )
				{
					WebElement ele1 = ele.findElement(By.xpath(OR.getProperty(element_Start) + temp[i] + OR.getProperty(element_End)+"/following-sibling::*"));
					if(ele1.getTagName().equals(Constants.LABEL)){
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
						executor.executeScript("arguments[0].click();", ele1);
					}
				}
			}
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
		return Constants.KEYWORD_PASS + " checkboxes have been checked";
	}

	/**
	 * Added by Sanjay Sharma on 25/11/2014 ,This method is used to check that
	 * parent element has child element.
	 * @param object:xpath of the element
	 * @param data:the Tagname you want to count & value e.g a|Folders ,
	 *  a is the name of the tag ,Folders is the name of child element.
	 */

	public String parentHasChildElement(final String object, String data) {
		logger.debug("Checking for the child element");
		try {

			WebElement rootWebElement = explictWaitForElementUsingFluent(object);
			String temp[] = data.split(Constants.DATA_SPLIT);
			List<WebElement> childSize = rootWebElement.findElements(By.xpath(".//"+temp[0]));
			WebElement[] child =childSize.toArray(new WebElement[0]);

			if (child.length == 0) {
				logger.debug("Parent has no child");
				return Constants.KEYWORD_FAIL + "---"+ "No child exist for parent";
			} 
			else
			{
				logger.debug(childSize.size());
				for(int i=0;i<child.length;i++)
				{

					if((temp[i+1].trim()).equals(child[i].getText().trim()))
					{
						logger.debug(child[i].getText());
					}
					else
					{
						return Constants.KEYWORD_FAIL + "---"+ "Element not Matched";
					}
				}
				return Constants.KEYWORD_PASS + "--" + "Parent has "+ childSize.size() + " child and Matched"; 
			}
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not traverse webElement "+ e.getMessage();
		}

	}
	/**
	 * Added by Sanjay Sharma on 25/11/2014 ,This method is used to match Column Values with given value
	 * @param object: xpath of the column values
	 * @param data: Value with Which you want to match Column Values
	 */
	public String verifyColumnValues(String object, String data) {
		try {

			logger.debug("Entered into verifyColumnValues()");

			List<WebElement> expected = explictWaitForElementList(object);

			expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
				if ((expected.get(i).getText().trim()).contains(data)) //modified on 13/05/2015 replace equal with contains.
				{
					logger.debug((expected.get(i).getText().trim()));
				}
				else
				{
					return Constants.KEYWORD_FAIL + "---"+ "Element not Matched";
				}
			}
			return Constants.KEYWORD_PASS + "--" + "All Elements Matched"; 
		} 
		catch (TimeoutException ex) 
		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} 
		catch (Exception e) 
		{

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * @since 27/11/2014
	 * @author Sanjay Sharma 
	 * unCheckCheckBoxUsingData() ,This method is used to Uncheck Multiple check boxes By using Data
	 * @param object
	 *            :Require 2 xpaths splitted with comma i.e Start And End Xpath for check box
	 * @param data
	 *            : Data/Name(s) that should be pass to splitted xpath (using "Data_SPLIT)
	 * @return Pass
	 *         Fail
	 */
	public String unCheckCheckBoxUsingData(String object,String data) throws InterruptedException {

		logger.debug("Enter Inside 'unCheckCheckBoxUsingData' method");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];			
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter
			if (element_Start == null || element_End == null||data == null) {
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			}
			// UnCheck the Checkbox(es)
			for(int i=0;i<temp.length;i++)
			{
				WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[i] + OR.getProperty(element_End)));
				boolean checked = ele.isSelected();
				if (checked ){
					WebElement ele1 = ele.findElement(By.xpath(OR.getProperty(element_Start) + temp[i] + OR.getProperty(element_End)+"/following-sibling::*"));
					if(ele1.getTagName().equals(Constants.LABEL)){
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].scrollIntoView(true);", ele1);
						executor.executeScript("arguments[0].click();", ele1);
					}}	
			}
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
		return Constants.KEYWORD_PASS + " checkboxes have been Unchecked";
	}
	/**
	 * This method is used to verify that the Checkbox is not selected.
	 * 
	 * @author : Surender
	 * @since :08/12/2014
	 * 
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts  are given as object parameter that are splitted by ","
	 * 
	 * @param data
	 *            : It accept data required to complete the xpath given from the object parameter
	 * 
	 * @return PASS : If Checkbox(s) is Not Selected.
	 * 
	 * @return Fail : Otherwise.
	 */
	public String verifyCheckBoxNotSelectedUsingData(String object, String data) {
		logger.debug("Entered into verifyCheckBoxNotSelectedUsingData()");
		try {
			boolean checked=false,flag=true;
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter

			if (xpathStart == null || xpathEnd == null||data == null) {
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			}

			for(int i=0;i<temp.length;i++)
			{
				WebElement ele = driver.findElement(By.xpath((xpathStart) + temp[i] + (xpathEnd)));
				checked = ele.isSelected();
				if (!checked)
					flag = true;
				else
				{
					flag = false;
					break;
				}
			}

			if (flag) {
				return Constants.KEYWORD_PASS + "--All checkboxes not checked";
			} else
				return Constants.KEYWORD_FAIL + "--All checkboxes checked";
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}

	}	
	/**
	 * This method is used to verify current date along with time zone.
	 * @author : Surender
	 * @since :08/12/2014
	 * 
	 * @param object : Xpath of expected webelement
	 * 
	 * @param data: None
	 *           
	 * @return PASS : If current date and time zone matches.
	 * 
	 * @return Fail : Otherwise.
	 */
	public String verifyCurrentDateWithTimeZone(String object, String data) {

		try {
			logger.debug("entered into verifyCurrentDateWithTimeZone()");
			String date = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim();
			logger.debug("actual date:"+date);
			String actualDate[] = date.split(Constants.Name_SPLIT);
			String actualdayName=actualDate[0];
			String actualmonth=actualDate[1];
			String actualday=actualDate[2];
			String actualzone=actualDate[4];
			String actualyear=actualDate[5];
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat timeformat = new SimpleDateFormat(Constants.DATE_TIME_ZONE);
			timeformat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String currentDate=timeformat.format(cal.getTime());
			logger.debug("expected date:"+currentDate);
			String expectedDate[]=currentDate.split(Constants.Name_SPLIT);
			String expecteddayName=expectedDate[0];
			String expectedmonth=expectedDate[1];
			String expectedday=expectedDate[2];
			String expectedzone=expectedDate[4];
			String expectedyear=expectedDate[5];
			if(actualdayName.equals(expecteddayName) && actualmonth.equals(expectedmonth) && actualday.equals(expectedday) && actualzone.equals(expectedzone) && actualyear.equals(expectedyear))
			{
				return Constants.KEYWORD_PASS + "--Current Date verified with time zone" ;
			}
			else
			{
				return Constants.KEYWORD_FAIL + "--Current Date not verified with time zone" ;
			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL  + e.getLocalizedMessage();
		}
	}	



	/*
	 * Added by Vikas Bhadwal on 02/12/2014
	 *  This method is used to select last value of Drop Down

	 *@param object:xapth of Drop down
	 * @param data:None
	 */
	public String selectDropdownLastValue(String object, String data) {
		logger.debug("Entered into verifyDropdownLastValue()");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG").trim()));
			int lastvalue = options.size();
			logger.debug(lastvalue);
			WebElement option=options.get(lastvalue-1);
			logger.debug("Last Value > " + lastvalue);
			option.click();
			return Constants.KEYWORD_PASS + "--" + lastvalue + " - selected";


		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}



	/*
	 * Added by Vikas Bhadwal on 02/12/2014
	 *  This method is used to copy current URL And paste it to new tab.

	 */

	public String copyUrlAndOpenInNewTab(String object, String data) {

		try {

			String url= driver.getCurrentUrl();

			if (CONFIG.getProperty("browserType").equals("Mozilla")
					|| CONFIG.getProperty("browserType").equals("IE")) {
				String ctrlT = Keys.chord(Keys.CONTROL, "t");
				wait.until(explicitWaitForElement(By.cssSelector("body")))
				.sendKeys(ctrlT);
			} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
				((JavascriptExecutor) driver).executeScript("window.open()");
				keyUtil.moveToNewWindow(object, data);

			}

			driver.get(url);
		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to open new Tab "
					+ e.getLocalizedMessage();
		}
		return Constants.KEYWORD_PASS ;

	}



	/*
	 * Added by Vikas Bhadwal on 01/12/2014
	 * This method is used to verify sorting(Date) in
	 * Descending order ,pass DATE FORMAT from Excel.
	 * @param object
	 * @param data:DATE FORMAT(Ex. MM/dd/yyyy)
	 *        
	 */


	public String verifyDateInDescendingWithOutPagination(String object, String data) {
		try {

			logger.debug("Entered into verifyDateInAscendingWithOutPagination()");
			List<String> actual = new ArrayList<String>();
			List<String> real = new ArrayList<String>();

			List<WebElement> expected = explictWaitForElementList(object);

			expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
				if (expected.get(i).getText().trim().length() != 0) {
					actual.add(expected.get(i).getText().trim());
					real.add(expected.get(i).getText().trim());
				}



			}
			final DateFormat df = new SimpleDateFormat(data.trim());
			Collections.sort(actual, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					try {
						return df.parse(o2).compareTo(df.parse(o1));

					} catch (Exception e) {
						throw new IllegalArgumentException(e);
					}
				}
			});
			logger.debug("actual is as follows-- " + actual);
			logger.debug("real is as follows-- " + real);
			if (real.equals(actual)) {
				return Constants.KEYWORD_PASS + " -- Dates are sorted";
			} else
				return Constants.KEYWORD_FAIL + " -- Dates are not sorted";
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}






	/**
	 * @author Anil Mishra
	 * This method is used to copy a text from a DOC file and paste it in a text field or input box
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 */
	// Modified by Karan Sood on 18/02/2015 - Added clear()

	public String copyAndPasteTextInDoc(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);
			String copytextvalue=we.getText();
			logger.debug(copytextvalue);
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			element.clear();
			element.sendKeys(Keys.LEFT_CONTROL + "v");
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}


	/**
	 * @author Anil Mishra
	 * This method is used to copy a text from a PDF file and paste it in a text field or input box
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 */
	// Modified by Karan Sood on 18/02/2015 - Added clear()

	public String copyAndPasteTextInPdf(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			PdfReader text=new PdfReader(fis);
			String copytextvalue=PdfTextExtractor.getTextFromPage(text, 1);
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			element.clear();
			element.sendKeys(Keys.LEFT_CONTROL + "v");
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}


	/**
	 * @author Anil Mishra
	 * This method is used to copy a text from a XLSX file and paste it in a text field or input box
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 * Modified by Karan Sood on 18/02/2015 - Added clear()
	 * Modified By Pankaj Dogra on 27/04/2015 Now it will copy the Content both of xls and xlsx file
	 **/ 

	public String copyAndPasteTextInXls(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String copytextvalue=null;
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<?> ite = sheet.rowIterator();
			while(ite.hasNext()){
				Row row = (Row) ite.next();
				Iterator<Cell> cite = row.cellIterator();
				while(cite.hasNext()){
					Cell c = cite.next();
					logger.debug(c.toString() +"  ");
					copytextvalue=c.getStringCellValue();
				}
			}
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			element.clear();
			element.sendKeys(Keys.LEFT_CONTROL + "v");
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}   



	/*
	 * Added by Vikas Bhadwal on 31/12/2014
	 * This method is used to enter multiple lines in to ckeditor.
	 * 
	 * @param object:xpath of ckeditor iframe.
	 * @param data:Lines Separated by | e.g: This is first line|This is second line.
	 */        


	public String setFCKeditorMessageWithMultipleLines(String object,String data) throws InterruptedException {

		logger.debug("Inside 'setFCKeditorMessageWithMultipleLines' method");
		try {
			String temp[] = data.split(Constants.DATA_SPLIT);
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			driver.switchTo().frame(ele);
			WebElement editable = driver.switchTo().activeElement();
			Set<String> availableWindows = driver.getWindowHandles();
			for(int i=0;i<temp.length;i++)
			{
				editable.sendKeys(temp[i]);
				pressEnter("","");

			}
			String WindowIDparent = null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1) {
					logger.debug(Integer.toString(counter) + " " + windowId);
					WindowIDparent = windowId;
				}
				counter++;
			}
			// Navigate to Parent window
			driver.switchTo().window(WindowIDparent);


		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
		return Constants.KEYWORD_PASS + "Lines Entered";
	}

	/**
	 * This method is used to Enter Current date,Previous date,Future date
	 * @author :Pankaj Dogra
	 * @since :31/12/2014
	 * @param object : Xpath of expected Input Field or Date Field
	 * @param data: if You want to enter date for Next two
	 *                             days then pass 2 from xls and if you                          
	 * want to enter date of Previous 5 days ,then pass -5 from xls.
	 * If You want to enter Current date then Pass 0 Form xls and respectively .
	 */
	public String enterDateBeforORAfterCurrentDate(String object, String data) {
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,(Integer.parseInt(data)));
			WebElement ele=driver.findElement(By.xpath(OR.getProperty(object)));
			String month=String.valueOf(cal.get(Calendar.MONTH)+1);
			String date=String.valueOf(cal.get(Calendar.DATE));
			if(month.length()==1)
				month="0"+month;
			if(date.length()==1)
				date="0"+date;
			String val= month + "/"+ date + "/" + cal.get(Calendar.YEAR);

			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, val);
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getCause() ;
		}
		return Constants.KEYWORD_PASS;
	}

	/**
	 * This method is used to Verify Entered Current date,Previous date,Future date
	 * @author :Pankaj Dogra
	 * @since :02/01/2015
	 * @param object : Xpath of expected Input Field or Date Field
	 * @param data: if You want to Verify date for Next two
	 *                             days then pass 2 from xls and if you                          
	 * want to Verify date of Previous 5 days ,then pass -5 from xls.
	 * If You want to Verify Current date then Pass 0 Form xls and respectively  .
	 */
	public String verifyBeforORAfterCurrentDate(String object, String data) {
		try {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,(Integer.parseInt(data)));
			String month=String.valueOf(cal.get(Calendar.MONTH)+1);
			String date=String.valueOf(cal.get(Calendar.DATE));
			if(month.length()==1)
				month="0"+month;
			if(date.length()==1)
				date="0"+date;
			String val= month + "/"+ date + "/" + cal.get(Calendar.YEAR);
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String actual=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);
			if(val.equals(actual))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;

		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getCause() ;
		}
	}

	/** this method moves the mouse over the element specified in the object Using Data
	 * @author :Pankaj Dogra
	 * @since :02/01/2015
	 * @param object : It accept one xpath that is divided into two parts and both
	 *                 parts are given as object parameter that are splitted by ","
	 * @param data : It accept data required to complete the xpath.
	 * @return PASS : When ever mouse hover on expected Elements
	 * @return Fail : Otherwise.
	 */
	public String mouseOverUsingData(String object, String data) {
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			// validates the parameter
			if (xpathStart == null || xpathEnd == null) {
				return Constants.KEYWORD_FAIL + "Object is not passed properly";
			}
			if (data == null) {
				return Constants.KEYWORD_FAIL + "Data is empty";
			}
			logger.debug(xpathStart + data.trim() + xpathEnd);
			WebElement element = driver.findElement(By.xpath(xpathStart + data.trim() + xpathEnd));
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found";
		}
	}



	/** Added By Karan Sood on 07/01/2015
	 *
	 *This method will search for file whose name contains Current Date in downloads folder as well in every child folder
	 *
	 *@param object :Do not Required any Object
	 *
	 * @param data : It accepts Two values splits by "|" symbol.
	 *               For Example : Downloaded File Name is "FacultyTermRecords_Roshana_Johnson_01072015.xlsx"
	 *               First Value is :  "FacultyTermRecords_Roshana_Johnson_"
	 *               Second Value is :  ".xlsx"
	 **/
	public String checkFileExistanceWithCurrentDate(String object,String data){

		try
		{
			if(data==null)
				return Constants.KEYWORD_FAIL+"Data is Required";

			DateFormat DtFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			DtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String currentdate = DtFormat.format(date).toString().trim().replaceAll("/", "");
			logger.debug(currentdate);

			if(object.equals(""))
				object=dirName;

			File dir=new File(object);
			if(dir.exists())
			{   
				File[] allFiles=dir.listFiles();
				for(int i=0;i<allFiles.length;i++)
				{
					if(allFiles[i].isDirectory())
					{
						String newDir=object+File.separator+allFiles[i].getName();
						String result=checkFileExistanceWithCurrentDate(newDir, data);
						resultList.add(result);
					}
					else if(allFiles[i].getName().equals(data.split(Constants.DATA_SPLIT)[0]+ currentdate +data.split(Constants.DATA_SPLIT)[1]))
					{
						Folder_File=allFiles[i].getParentFile();
						return Constants.KEYWORD_PASS;
					}
				}
			}
			else
			{
				throw new FileNotFoundException();
			}

			if(resultList.contains(Constants.KEYWORD_PASS)&&object.equals(dirName))
			{
				resultList=new ArrayList<String>();
				return  Constants.KEYWORD_PASS+"File Found present in"+Folder_File;
			}
			else
				return  Constants.KEYWORD_FAIL+"File Not Found";   
		}
		catch(FileNotFoundException ex)
		{
			return Constants.KEYWORD_FAIL+ex.getMessage();
		}
		catch(Exception ex)
		{
			return Constants.KEYWORD_FAIL+ex.getMessage();
		}
	}


	/**
	 * Added By Karan Sood on 07/01/2015
	 * 
	 * This method will read the XLSX file and match the content of the file
	 * with given data
	 * @param object: not require
	 * 
	 * @param Data  : From data; you can verify multiple values with comma seprated.
	 *                First name will contains name of the file split by '|' then you can use
	 *                Multiple values with comma seprated.
	 * For ex    : FacultyTermRecords_Roshana_Johnson_|.xlsx,Firstvalue,Secondvalue,Third value  
	 * 				[When File name is  "FacultyTermRecords_Roshana_Johnson_01072015.xlsx" containing current date]             
	 * @throws IOException 
	 *            
	 */

	public String verifyTextInXlsxFileWithCurrentdate(String object,String data) throws IOException{
		FileInputStream fis=null;
		try{

			DateFormat DtFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			DtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String currentdate = DtFormat.format(date).toString().trim().replaceAll("/", "");
			logger.debug(currentdate);

			int counter=0;
			ArrayList<String> ar= new ArrayList<String>();
			String temp[]=data.split(",");
			String fileNameStrt=temp[0].split(Constants.DATA_SPLIT)[0];
			String fileNameEnd=temp[0].split(Constants.DATA_SPLIT)[1];
			int size=(temp.length-1);
			String path=dirName+File.separator+fileNameStrt+currentdate+fileNameEnd;
			File file=new File(path);
			fis=new FileInputStream(file.getAbsolutePath());
			Workbook workbook = WorkbookFactory.create(fis);  
			Sheet worksheet = workbook.getSheetAt(0); 
			Iterator<Row> rowIterator = worksheet.iterator(); 
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();      
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next(); 
					cell.getStringCellValue();
					ar.add(cell.toString());
				}
			}
			for(int j=0;j<size;j++){
				for(int i=0;i<ar.size();i++){
					if(temp[j+1].trim().toString().equals(ar.get(i).trim())){
						counter++;
						break;
					}
					else{
						continue;
					}
				}
			}

			if(counter==size){
				return Constants.KEYWORD_PASS+"---All Texts are present";
			} 
		}

		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			fis.close();
		}
		return Constants.KEYWORD_FAIL;
	}

	/**
	 * Added By Shweta Gupta on 14/01/2015
	 * 
	 * This method will verify that the element is present or not with the given data when contains is used in the x-path.
	 * 
	 * @param object
	 *        :Require 2 xpaths splitted with comma i.e Start And End Xpath.
	 *        :x-path must be made using contains only.
	 * @param data
	 *        : Data that should be passed to splitted xpath (using "Object_SPLIT")
	 * 
	 */

	public String verifyElementTextWithData(String object, final String data) {
		logger.debug("inside verifyElementIsPresent..");
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			final String element_Start = element[0];
			final String element_End = element[1];

			while (true) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(explicitwaitTime, TimeUnit.SECONDS)
						.pollingEvery(pollingTime, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class);

				List<WebElement> element_List = wait.until(new Function<WebDriver, List<WebElement>>() {

					@Override
					public List<WebElement> apply(WebDriver driver) 
					{
						return driver.findElements(By.xpath(OR.getProperty(element_Start)+ data+ OR.getProperty(element_End)));
					}

				});


				if (element_List.size() == 1)

				{								
					String actual = element_List.get(0).getText().trim();

					logger.debug("act" + actual);	

					String expected = data.trim();

					logger.debug("data" + data);

					if (actual.equals(expected))

						return Constants.KEYWORD_PASS +data+ "Element present";

					else 							
						return Constants.KEYWORD_FAIL + data+" Element is not present";


				}
				else
				{
					logger.debug("WebElement with this x-path not present.." + element);

					return Constants.KEYWORD_FAIL + " -- No webElement present with this x-path ";
				}

			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}

	/**
	 * Added By Shweta Gupta on 15/01/2015
	 * 
	 * This method will verify that the more link is present or not with the given data .
	 * 
	 * @param object		  
	 *        Require 3 xpaths splitted with comma i.e 
	 *        1)Start Xpath of text under Organization column
	 *        2)End Xpath of text under Organization column
	 *        3)x-path of More link.
	 *               
	 * @param data
	 *        : Pass text under Organization column as Data
	 *        
	 * @return - PASS if the more link is displayed for data containing more than 50 characters.
	 * 		   - PASS if the data contains less than 50 characters and more link is NOT displayed.
	 *         
	 * @return - FAIL if More link is not present for data containing more than 50 characters.
	 *         - FAIL if the object is not passed properly.
	 *         - FAIL if the data contains less than 50 characters and more link is displayed.
	 */					

	public String verifyMoreLinkWith50Char(String object, final String data) 
	{
		logger.debug("inside verifyMoreLinkWith50Char..");
		try
		{
			String[] element = object.split(Constants.Object_SPLIT);
			final String element_Start = element[0];
			final String element_End = element[1];
			final String element_more = element[2];

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(explicitwaitTime, TimeUnit.SECONDS).pollingEvery(pollingTime, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);

			List<WebElement> element_List = wait.until(new Function<WebDriver, List<WebElement>>() {

				@Override
				public List<WebElement> apply(WebDriver driver) 
				{
					return driver.findElements(By.xpath(OR.getProperty(element_Start)+ data + OR.getProperty(element_End)));
				}

			});

			if (element_List.size() == 1 )

			{								
				int count51= Constants.VALUE_51;						

				int countFromData= data.length();

				if(countFromData >= count51 )
				{																		
					List<WebElement> Result = explictWaitForElementList(element_more);

					if (Result.size() == 1)
					{									

						return Constants.KEYWORD_PASS +" More link is present ";
					}
					else
					{
						logger.debug("More link is not present.." );

						return Constants.KEYWORD_FAIL + " -- More link is not present ";
					}
				}
				else
				{								
					List<WebElement> Result = explictWaitForElementList(element_more);

					if (Result.size() == 0)
					{								
						logger.debug("Count less than 50 and therefore More link is not present.." );

						return Constants.KEYWORD_PASS +" More link is not present ";										

					}	
					else
					{
						logger.debug("Count less than 50 and More link is  present.." );

						return Constants.KEYWORD_FAIL + " -- More link is present ";
					}								

				}

			}
			else
			{
				logger.debug("WebElement with this x-path not present..");

				return Constants.KEYWORD_FAIL + " -- No webElement present with this x-path ";
			}

		}


		catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} 
		catch (Exception e) 
		{
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}	


	/**
	 * @author Karan Sood on 16/01/2015
	 * This method is used to copy a text from a file and paste it in a CK Editor
	 * @param object [Pass object as location of the CK Editor]
	 * @param data [File name]
	 * @return
	 */

	public String copyAndPasteTextInFCKeditor(String object, String data) {
		logger.debug("copying and pasting text... in Ck Editor");
		logger.debug("Data: " + data);
		try {

			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			String file_content= null;

			File file = new File(file_exist_path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			file_content=stringBuffer.toString();


			driver.switchTo().frame(element);
			WebElement editable = driver.switchTo().activeElement();
			editable.clear();
			StringSelection copyText = new StringSelection(file_content);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			editable.sendKeys(Keys.LEFT_CONTROL + "v");
			driver.switchTo().defaultContent();

		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully in Ck Editor";
	}

	/**
	 * Added By Shweta Gupta on 03/02/2015
	 * 
	 * This method will verify that element is not present with the given data when 'contains' is used in the x-path.
	 * 
	 * @param object
	 *        :Require 2 xpaths splitted with comma i.e Start And End Xpath.
	 *        :x-path must be made using contains only.
	 * @param data
	 *        : Data that should be passed to splitted xpath (using "Object_SPLIT")
	 *         
	 */
	public String verifyElementTextNotPresent(String object, String data) {
		logger.debug("Entered into verifyElementTextNotPresent...");
		try {
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			// validates the parameter
			if (xpathStart == null || xpathEnd == null || data == null)
			{
				return Constants.KEYWORD_FAIL + "Object or Data is not passed properly";
			}    			
			logger.debug(xpathStart + data.trim() + xpathEnd);

			List <WebElement> element_list = driver.findElements(By.xpath(xpathStart + data.trim() + xpathEnd));
			if(element_list.size()>0)
			{
				String actual = element_list.get(0).getText().trim();
				logger.debug("act" + actual);	
				String expected = data.trim();
				logger.debug("data" + data);

				if (!(actual.equals(expected)))
				{			
					logger.debug("WebElement not present..");
					return Constants.KEYWORD_PASS + "Element Not present"; 					
				} 
				else 
				{
					logger.debug("webElement present.." );
					return Constants.KEYWORD_FAIL + " -- webElement present -- ";
				}
			}
			else 
			{
				logger.debug("No WebElement present.." );
				return Constants.KEYWORD_PASS + " -- webElement not present -- ";
			}
		}
		catch (TimeoutException ex) 
		{    		
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} 
		catch (Exception e) 
		{
			return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
		}
	}

	/**
	 * This method is used to press Tab key from keyboard
	 * @author Sanjay Sharma
	 * @param object  : Not Reqiured
	 * @param data  : Not Reqiured
	 * @since 12 Feb, 2015
	 */
	public String pressTab(String object, String data) {
		logger.debug("entered into pressTab");
		try {
			Actions act = new Actions(driver);
			Action pressKey = act.sendKeys(Keys.TAB).build();
			pressKey.perform();
			return Constants.KEYWORD_PASS + "--" + " Key pressed.";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}

	/**
	 * This method is used to press Space key from keyboard
	 * @author Sanjay Sharma
	 * @param object  : Not Reqiured
	 * @param data  : Not Reqiured
	 * @since 12 Feb, 2015
	 */
	public String pressSpace(String object, String data) {
		logger.debug("entered into pressSpace");
		try {
			Actions act = new Actions(driver);
			Action pressKey = act.sendKeys(Keys.SPACE).build();
			pressKey.perform();
			return Constants.KEYWORD_PASS + "--" + " Key pressed.";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}
	}


	/**
	 * @author Karan Sood on 18/02/2015
	 * This method is used to verify text in input box with text from a PDF file
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 */
	public String verifyTextFromPdf(String object, String data) {
		logger.debug("Verifying text from Pdf...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String actual = element.getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			PdfReader text=new PdfReader(fis);
			String expected=PdfTextExtractor.getTextFromPage(text, 1);
			fis.close();
			if (!(actual == null)) {
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if ((actual.trim()).equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";    				
			} 
			else {
				actual = element.getText().trim();
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if (actual.equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";
			}

			return Constants.KEYWORD_FAIL + " Not matching ";    			

		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable Verify text" + e.getMessage();
		}

	}

	/**
	 * @author Karan Sood on 18/02/2015
	 * This method is used to verify text in input box with text from a Doc file
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 */

	public String verifyTextFromDoc(String object, String data) {
		logger.debug("Verifying text from Doc...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String actual = element.getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);

			String expected=we.getText().trim();
			fis.close();
			if (!(actual == null)) {
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if ((actual.trim()).equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";    				
			} 
			else {
				actual = element.getText().trim();
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if (actual.equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";
			}

			return Constants.KEYWORD_FAIL + " Not matching ";



		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable Verify text" + e.getMessage();
		}


	}

	/**
	 * @author Karan Sood on 18/02/2015
	 * This method is used to verify text in input box with text from a Xlsx file
	 * @param object[Pass object as location of the text area or input box]
	 * @param data[File name]
	 * @return
	 */
	public String verifyTextFromXls (String object, String data) {
		logger.debug("Verifying text from Xlsx....");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String actual = element.getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));

			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String expected=null;
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<?> ite = sheet.rowIterator();
			while(ite.hasNext()){
				Row row = (Row) ite.next();
				Iterator<Cell> cite = row.cellIterator();
				while(cite.hasNext()){
					Cell c = cite.next();
					logger.debug(c.toString() +"  ");
					expected=c.getStringCellValue();
				}
			}
			fis.close();
			if (!(actual == null)) {
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if ((actual.trim()).equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";    				
			} 
			else {
				actual = element.getText().trim();
				logger.debug("actual: " + actual);
				logger.debug("expected: " + expected);
				if (actual.equals(expected.trim()))
					return Constants.KEYWORD_PASS + "--Text matched";
			}

			return Constants.KEYWORD_FAIL + " Not matching ";  	

		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable Verify text " + e.getMessage();
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
	 * This method is used to verify that the Web Element is present .
	 * @author : Pankaj Dogra
	 * @since :24/02/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.You can pass multiple data form data sheet using
	 *              data_split (|).
	 * @return PASS : If Elements are Present on the Web Page.
	 * @return Fail : Otherwise.
	 **/
	public String isMultipleElementPresentUsingData(String object, String data) {
		logger.debug("Entered into isWebElementPresentUsingData()");
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);
			int element=0,counter=0;
			boolean flag=true;
			// validates the parameter
			if (xpathStart == null || xpathEnd == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			for(int i=0;i<temp.length;i++){ 
				logger.debug(xpathStart +temp[i]+ xpathEnd);
				element = driver.findElements(By.xpath((xpathStart) + temp[i] + (xpathEnd))).size();
				if (element== 0){
					flag=false;
					break;
				}
				counter++;
			} 
			if(flag){
				if(counter==1)
					return Constants.KEYWORD_PASS + " -- Single webElement present";
				else
					return Constants.KEYWORD_PASS+"--"+ counter + "--webElements present";
			}
			else
				return Constants.KEYWORD_FAIL + " -- No webElements is present";
		}
		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
		}
	}


	/**
	 * This method is used to enter the text in editor without clearing the previous text.
	 * @author Sanjay Sharma
	 * @param object : Editor xpath
	 * @param data : Text
	 * @since 20 Feb, 2015
	 */
	public String setFCKeditorMessageWithoutClear(String object, String data) throws InterruptedException

	{
		try {
			logger.debug("enter the message in FCKeditor");
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			driver.switchTo().frame(ele);
			WebElement editable = driver.switchTo().activeElement();	
			editable.sendKeys(data);
			Set<String> availableWindows = driver.getWindowHandles();
			logger.debug("Handle Size:" + availableWindows.size());
			// Retreive all the window handles into variables
			String WindowIDparent = null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1) {
					logger.debug(Integer.toString(counter) + " " + windowId);
					WindowIDparent = windowId;
					break;
				}
			}
			// Navigate to Parent window
			driver.switchTo().window(WindowIDparent);
			logger.debug("In the Parent");

		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		} 
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}


	/**
	 * @author Anil Mishra on 13/3/2015
	 * This method is used to copy a text from a DOC file and paste it in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */


	public String copyAndPasteTextFromDocInFckEditor(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);

			String copytextvalue=we.getText();
			logger.debug(copytextvalue);
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			ele.clear();
			ele.sendKeys(Keys.LEFT_CONTROL + "v");
			driver.switchTo().defaultContent();
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			System.out.println(e);
			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();

		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}


	/**
	 * @author Anil Mishra on 13/3/2015
	 * This method is used to copy a text from a PDF file and paste it in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */


	public String copyAndPasteTextFromPdfInFckEditor(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			PdfReader text=new PdfReader(fis);
			String copytextvalue=PdfTextExtractor.getTextFromPage(text, 1);
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			ele.clear();
			ele.sendKeys(Keys.LEFT_CONTROL + "v");
			driver.switchTo().defaultContent();
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}


	/**
	 * @author Anil Mishra on 13/3/2015
	 * This method is used to copy a text from a XLS file and paste it in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 * Modified By Sudhir Kumar on 20/06/2015 Now it will copy the Content both of xls and xlsx file
	 */          

	public String copyAndPasteTextFromXlsInFckEditor(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String copytextvalue=null;
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<?> ite = sheet.rowIterator();
			while(ite.hasNext()){
				Row row = (Row) ite.next();
				Iterator<Cell> cite = row.cellIterator();
				while(cite.hasNext()){
					Cell c = cite.next();
					logger.debug(c.toString() +"  ");
					copytextvalue=c.getStringCellValue();
				}
			}
			fis.close();
			StringSelection copyText = new StringSelection(copytextvalue.trim());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(copyText, copyText);
			ele.clear();
			ele.sendKeys(Keys.LEFT_CONTROL + "v");
			driver.switchTo().defaultContent();
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to copy&paste" + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--Text pasted successfully";
	}  

	/**
	 * @author Kritika on 13/03/2015
	 * This method is used to Click any element using click method with splitted xpath
	 * @param object:Require 2 xpaths splitted with comma i.e Start And End Xpath
	 * @param data: Data/Name(s) that should be pass to splitted xpath
	 * @return
	 */
	public String clickElementUsingClick(String object, String data) {
		logger.debug("Clicking on Element ");
		WebElement ele=null;
		try { 
			String temp[] = object.split(Constants.Object_SPLIT);
			ele = driver.findElement(By.xpath(OR.getProperty(temp[0]) + data+ OR.getProperty(temp[1])));
			ele.click();
			keyUtil.browserSpecificPause(object, data);
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL + " -- Not able to click on Element" + ex.getCause();
		}
		catch(WebDriverException ex)
		{
			try{
				String exceptionMessage=ex.getMessage();
				if(exceptionMessage.contains("Element is not clickable at point"))
				{
					if(new ApplicationSpecificKeywordEventsUtil().clickJs(ele).equals(Constants.KEYWORD_PASS))
						return Constants.KEYWORD_PASS;
					else
						return Constants.KEYWORD_FAIL;
				}
				else
					return Constants.KEYWORD_FAIL+"not able to Click"+ex.getMessage();
			}
			catch(Exception e){
				return Constants.KEYWORD_FAIL+e.getMessage();
			}

		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + " -- Not able to click on link" + e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

	/**
	 * @author Pankaj Dogra on 16/3/2015
	 * This method is used to copy a text from a DOC file and compare text with CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */


	public String verifyCopiedTextFromDocWithFckEditor(String object, String data) {
		logger.debug("copying and pasting text...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();
			String actual=ele.getText().trim();
			driver.switchTo().defaultContent();
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor we = new WordExtractor(doc);
			String copytextvalue=we.getText().trim();

			logger.debug("Value From Doc File-- "+copytextvalue);
			logger.debug("Value From Ck Editor -- "+actual);
			fis.close();
			if(actual.equals(copytextvalue))
				return Constants.KEYWORD_PASS + "--Same Value Present in Fck Editor";
			else
				return Constants.KEYWORD_FAIL +"---Both Values are different";
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to get text from DOC file" + e.getMessage();

		}
	}


	/**
	 * This method is used to verify that the Web Element is not present .
	 * @author : Shweta Gupta
	 * @since :16/03/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the
	 *              object parameter.You can pass multiple data form data sheet using
	 *              data_split (|).
	 * @return PASS : If Elements are not present on the Web Page.
	 * @return Fail : Otherwise.
	 **/
	public String isMultipleElementNotPresentUsingData(String object, String data)
	{
		logger.debug("Entered into isWebElementPresentUsingData()");
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);
			int element=0,counter=0;
			boolean flag=true;
			// validates the parameter
			if (xpathStart == null || xpathEnd == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";

			for(int i=0;i<temp.length;i++)
			{
				logger.debug(xpathStart +temp[i]+ xpathEnd);
				element = driver.findElements(By.xpath((xpathStart) + temp[i] + (xpathEnd))).size();
				if (element > 0)
				{
					flag=false;
					break;
				}
				counter++;
			}
			if(flag)
			{
				if(counter==1)
					return Constants.KEYWORD_PASS + " -- Single webElement is not present. ";
				else
					return Constants.KEYWORD_PASS+"--"+ counter + "--webElements are not present";
			}
			else
				return Constants.KEYWORD_FAIL + " -- webElements are present";
		}
		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
		}
	}

	/**
	 * Created by Sanjay Sharma On 30 March 2015 This method is used to Verify
	 * current month and year in (MMMM yyyy) Format according to US/Central Time Zone 
	 * Example: to verify Month and year of Calendar Popup i.e March 2015 
	 * @param object
	 *            :This method accepts One xpath,i.e location where the month and year is displayed.
	 * @param data
	 *            : There is no need to pass any data
	 * @return:PASS , if displayed month matches with current (MMMM yyyy) Format. 
	 * FAIL: otherwise.
	 */
	public String verifyCurrentMonth(String object, String data) {
		logger.debug("Verifying the Current month and date");
		try {
			String actual =wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim();
			DateFormat DtFormat = new SimpleDateFormat("MMMM yyyy");
			Date date = new Date();
			DtFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			String expected = DtFormat.format(date).toString().trim();

			logger.debug("expected" + expected);
			logger.debug("act" + actual);
			if (actual.trim().contains(expected)) 
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --current month is not displayed, actual: " + actual + " -- Expected  = " + expected + " ";
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Added By Pooja 01/04/2015
	 * This Method verifies Date Format.
	 * @param object
	 *            :xpath of the Webelement from where the date is to be verified.
	 * @param data
	 *            :Date Format , for example:-MM/DD/YYY
	 *             
	 */

	public String verifyDateFormatUsingJs(String object, String data) {
		logger.debug("verifying the date format");
		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String result = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);

			String date[] = result.split("/");
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
	/** Added By Pooja 01/04/2015
	 * This Method verifies the text using Contains.
	 * @param object
	 *            :xpath of the Webelement from where the data is to be verified.
	 * @param data
	 *            :data to be verified.
	 *             
	 **/


	public String verifyTextUsingJsWithContains(String object, String data) {
		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String result = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);
			if (result.trim().contains(data.trim())) 				
				return Constants.KEYWORD_PASS +data+ " -- Value is present -- ";
			else
				return Constants.KEYWORD_FAIL + data+ " -- Value is not present -- ";
		}

		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + ex.getCause();
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + " Object not found "
					+ ex.getMessage();
		}
	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to save current month with year(MMMM yyyy format).
	 * Example: to save Month and year of Calendar Popup say March 2015 
	 * @param object
	 *            :This method accepts One xpath,i.e location where the month and year is displayed.
	 * @param data
	 *            : None
	 * @return:PASS , if saves current month with year
	 * FAIL: otherwise.
	 */
	public String saveCurrentMonth(String object, String data) {
		logger.debug("entered into saveCurrentMonth()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("MMMM yyyy");
			currentMonth= DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			logger.debug("Current Month and Year:"+DtFormat.format(currentMonth));
			return Constants.KEYWORD_PASS + "--Current Month Saved";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}


	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to verify number difference of month(s) (MMMM yyyy format) we get on clicking next month icon of calender with the value saved in saveCurrentMonth() method.
	 * @param object
	 *            :This method accepts One xpath,i.e location where the month and year is displayed.
	 * @param data
	 *            :Number difference of months say April 2015 and March 2015 i.e one.
	 * @return:PASS , if verified
	 * FAIL: otherwise.
	 */
	public String verifyNextMonth(String object, String data) {
		logger.debug("entered into verifyNextMonth()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("MMMM yyyy");
			Date nextMonth = DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			logger.debug("Next Month:"+DtFormat.format(nextMonth));
			@SuppressWarnings("deprecation")
			int diffMonth = ((nextMonth.getYear()-currentMonth.getYear()) * 12) + nextMonth.getMonth() - currentMonth.getMonth();
			if(diffMonth==Integer.parseInt(data))
				return Constants.KEYWORD_PASS + "--Next Month(s) Verified";
			else
				return Constants.KEYWORD_FAIL + "--Next Month(s) Not Verified";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to verify number difference of month(s) (MMMM yyyy format) we get on clicking prev month icon of calender with the value saved in saveCurrentMonth() method.
	 * @param object
	 *            :This method accepts One xpath,i.e location where the month and year is displayed.
	 * @param data
	 *            :Number difference of months say March 2015 and Feb 2015 i.e one.
	 * @return:PASS , if verified
	 * FAIL: otherwise.
	 */
	public String verifyPreviousMonth(String object, String data) {
		logger.debug("entered into verifyPreviousMonth()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("MMMM yyyy");
			Date prevMonth = DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			logger.debug("Previous Month:"+DtFormat.format(prevMonth));
			@SuppressWarnings("deprecation")
			int diffMonth = ((currentMonth.getYear()-prevMonth.getYear()) * 12) + currentMonth.getMonth() - prevMonth.getMonth();
			if(diffMonth==Integer.parseInt(data))
				return Constants.KEYWORD_PASS + "--Previous Month(s) Verified";
			else
				return Constants.KEYWORD_FAIL + "--Previous Month(s) Not Verified";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to save and verify year(yyyy format) displayed on calender popup with current year.
	 *  Example: To save and verify current year say 2015
	 * @param object
	 *            :This method accepts One xpath,i.e location where the  year is displayed.
	 * @param data
	 *            :None
	 * @return:PASS , if saved and verified
	 * FAIL: otherwise.
	 */
	public String saveAndVerifyCurrentYear(String object, String data) {
		logger.debug("entered into saveAndVerifyCurrentYear()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("yyyy");
			currentYear = DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			Date date = new Date();
			logger.debug("Actula Current Year "+DtFormat.format(currentYear));
			if(DtFormat.format(date).toString().trim().equals(DtFormat.format(currentYear)))
				return Constants.KEYWORD_PASS + "--Current Month Saved and verified";
			else
				return Constants.KEYWORD_FAIL + "--Current Month Not Verified";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to verify number difference of year(s) (yyyy format) we get on clicking prev year icon of calender with the value saved in saveAndVerifyCurrentYear() method.
	 * @param object
	 *            :This method accepts One xpath,i.e location where the  year is displayed.
	 * @param data
	 *            :Number difference of years say 2015 and 2014 i.e one.
	 * @return:PASS , if  verified
	 * FAIL: otherwise.
	 */
	public String verifyPreviousYear(String object, String data) {
		logger.debug("entered into verifyPreviousYear()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("yyyy");
			Date prevYear = DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			logger.debug("Previous Year:"+DtFormat.format(prevYear));
			@SuppressWarnings("deprecation")
			int diffYear = (currentYear.getYear()-prevYear.getYear());
			if(diffYear==Integer.parseInt(data))
				return Constants.KEYWORD_PASS + "--Previous Year(s) Verified";
			else
				return Constants.KEYWORD_FAIL + "--Previous Year(s) Not Verified";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Created by Surender On 01/04/2015
	 *  This method is used to verify number difference of year(s) (yyyy format) we get on clicking next year icon of calender with the value saved in saveAndVerifyCurrentYear() method.
	 * @param object
	 *            :This method accepts One xpath,i.e location where the year is displayed.
	 * @param data
	 *            :Number difference of years say 2016 and 2015 i.e one.
	 * @return:PASS , if  verified
	 * FAIL: otherwise.
	 */
	public String verifyNextYear(String object, String data) {
		logger.debug("entered into verifyNextYear()");
		try {
			SimpleDateFormat DtFormat = new SimpleDateFormat("yyyy");
			Date nextYear = DtFormat.parse(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText().trim());
			logger.debug("Next Year:"+DtFormat.format(nextYear));
			@SuppressWarnings("deprecation")
			int diffYear = (nextYear.getYear()-currentYear.getYear());
			if(diffYear==Integer.parseInt(data))
				return Constants.KEYWORD_PASS + "--Next Year(s) Verified";
			else
				return Constants.KEYWORD_FAIL + "--Next Year(s) Not Verified";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}

	}

	/**
	 * Added By : Pankaj Dogra on 08/04/2015
	 * Description This method is used to verify whether dropdowns selected
	 * value are present or not using value attribute.
	 */
	public String verifyListSelectedValue(String object, String data) {
		logger.debug("Entered into verifyDropdownSelectedValue()");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Select sel = new Select(select);
			String defSelectedVal = sel.getFirstSelectedOption().getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));
			logger.debug(defSelectedVal);
			logger.debug(data);
			if (defSelectedVal.trim().equals(data.trim())) {
				return Constants.KEYWORD_PASS + data+" -- Default value present ";
			} else {
				return Constants.KEYWORD_FAIL + data+" -- Default value not present ";
			}
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}

	/**
	 * Added by Navdeep Kaur on 16/04/2015
	 * This method is used to verify CssProperty of multiple elements
	 * @param object : common x-path of all elements
	 * @param data: common CSS property of all elements
	 *        
	 **/
	public String verifyCssPropertyForMultiple(String object, String data) 
	{
		try {
			String property[] = data.split(":",2);
			String exp_prop = property[0];
			String exp_value = property[1];
			boolean flag = false;
			String prop=null;
			logger.debug(exp_value);
			List<WebElement> allValues=explictWaitForElementList(object);
			if(allValues.size()>0)
			{
				for(int i=0;i<allValues.size();i++)
				{
					prop=allValues.get(i).getCssValue(exp_prop);
					logger.debug(prop);

					if (prop.trim().equals(exp_value.trim())) 
					{
						flag = true;
						logger.debug("--actvalue-" + prop + "--expectes-" + exp_value);
					}
					else{
						flag=false;
						break;
					}	
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS + " All emements have same CSS value "+prop;
			else
				return Constants.KEYWORD_FAIL;
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " " + e.getMessage();
		}

	}
	/**
	 * This method Verify presence of Element on page.
	 * This method also handle pagination
	 * @author Pankaj Dogra
	 * @since 1 December, 2014 
	 * @param object: It accepts two xpath by comma(,) split.
	 * @param data : it requires data to complete the xpath.
	 * @return It Returns Pass if element is present
	 *         else returns Fail.
	 **/
	@SuppressWarnings("static-access")
	public String findElementUsingDataWithPagination(String object, String data) {
		logger.debug("inside findElementUsingDataWithPagination()..");
		List<WebElement> next_Link_List=null;
		try {
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			// validates the parameter
			if (xpathStart == null || xpathEnd == null) {
				return Constants.KEYWORD_FAIL + "Object is not passed properly";
			}
			if (data == null) {
				return Constants.KEYWORD_FAIL + "Data is empty";
			}
			int page_no = 0;
			while (true) {
				page_no++;
				next_Link_List = keyUtil.explictWaitForElementList("pagination_next_link");
				int element = driver.findElements(By.xpath(xpathStart + data + xpathEnd)).size();
				if (element > 0) {
					return Constants.KEYWORD_PASS + " element is found on page - " + page_no;
				} else if (next_Link_List.size() > 0) {
					next_Link_List.get(0).click();
					Thread.sleep(4000);
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}
			}
		} 
		catch (Exception e) {
			return Constants.KEYWORD_FAIL+e.getMessage();
		}
	}
	/**
	 * This method is used to verify whether the text is equal or not .
	 * @author : Pankaj Dogra
	 * @since :23/04/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.Data Should be pass using (|) Split.
	 *              First Part of Data require to Complete the Xpath and Other part
	 *              of Data require to Compare what data you want to compare
	 * @return PASS : If text is present what you want to verify
	 * @return Fail : Otherwise.
	 **/

	public String verifyTextUsingData(String object, String data) {
		logger.debug("Verifying the text");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter
			if (element_Start == null || element_End == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[0] + OR.getProperty(element_End)));
			String actual = ele.getText();
			String expected = temp[1].trim();
			logger.debug("From Data ->" + temp[1]);
			logger.debug("actual ->" + actual);
			if (actual.trim().equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified Actual text = " + actual + " -- Expected Text = " + expected + " ";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	/**
	 * This method is used to verify text in input using split.
	 * @author : Pankaj Dogra
	 * @since :23/04/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
    	 @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.Data Should be pass using (|) Split.
	 *              First Part of Data require to Complete the Xpath and Other part
	 *              of Data require to Compare what data you want to compare
	 * @return PASS : If text is present what you want to verify
	 * @return Fail : Otherwise.
	 **/
	public String verifyTextinInputUsingData(String object, String data) {
		logger.debug("Verifying the text");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter
			if (element_Start == null || element_End == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[0] + OR.getProperty(element_End)));
			String actual = ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
			String expected = temp[1].trim();
			logger.debug("From Data ->" + temp[1]);
			logger.debug("actual ->" + actual);
			if (actual.trim().equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- text not verified Actual text = " + actual + " -- Expected Text = " + expected + " ";
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	/**
	 * This method is used to verify whether value is present in drop down.
	 * @author : Pankaj Dogra
	 * @since :23/04/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
    	 @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.Data Should be pass using (|) Split.
	 *              First Part of Data require to Complete the Xpath and Other part
	 *              of Data require to Compare what data you want to compare
	 * @return PASS : If Same Value is present under Drop down
	 * @return Fail : Otherwise.
	 **/
	public String verifyDropdownSelectedValueUsingData(String object, String data) {
		logger.debug("Entered into verifyDropdownSelectedValue()");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			String temp[] = data.split(Constants.DATA_SPLIT);
			// validates the parameter
			if (element_Start == null || element_End == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			WebElement select = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[0] + OR.getProperty(element_End)));
			Select sel = new Select(select);
			String defSelectedVal = sel.getFirstSelectedOption().getText();
			logger.debug("actual is ->"+defSelectedVal);
			logger.debug("data is ->"+temp[1]);
			if (defSelectedVal.trim().equals(temp[1].trim())) {
				return Constants.KEYWORD_PASS + " -- Default value present ";
			} else {
				return Constants.KEYWORD_FAIL + " -- Default value not present ";
			}
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}

	/**
	 * This method is used to verify blank value for Given Xpath.
	 * @author : Pankaj Dogra
	 * @since :23/04/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It require to Complete the xpath.
	 * @return PASS : If desired xpath does not contain any value
	 * @return Fail : Otherwise.
	 **/
	public String verifyBlankValueUsingData(String object, String data) {
		logger.debug("Verifying the text");
		try {
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			// validates the parameter
			if (element_Start == null || element_End == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			
			WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + data.trim() + OR.getProperty(element_End)));
			String actual = ele.getText().trim();
			logger.debug("act" + actual);
			if (actual.length() == 0)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " -- Some Value Present";
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}



	/**
	 * This method is used to verify text's Count is correct or not .
	 * @author : Pankaj Dogra
	 * @since :18/05/2015
	 * @param object :It accept one xpath of Given text
	 * @param data : It accept count(Number) that has to be matched.
	 * @return PASS : If Count get match.
	 * @return Fail :If not Matched
	 **/
	public String verifyTextLength(String object, String data) {
		logger.debug("Verifying the text in input box");
		try {
			int actual_size=0;
			int max_size = Integer.parseInt(data);
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			String actual =ele.getText().trim();
			actual_size = actual.length();
			logger.debug("actual: " + actual_size);
			logger.debug("expected: " + max_size);
			if (max_size == actual_size)
				return Constants.KEYWORD_PASS + "Length of Text is Equal to Given Count";
			else {
				logger.debug("actual- " + actual);
				return Constants.KEYWORD_FAIL + " Count is not Matching ";
			}
		} catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + " Unable to find Object "+ e.getMessage();

		}
	}


	/**
	 * Added by Naincy Saini on 22/05/2015
	 * This method is used to verify multiple Webelements using Splitted xpath corresponding to Fields
	 */

	public String verifyElementUsingSplit(String object, final String data) {
		logger.debug("inside verifyElementIsPresent..");
		try {

			String[] element = object.split(Constants.Object_SPLIT);
			final String element_Start = element[0];
			final String element_ele1 = element[1];
			final String element_ele2 = element[2];
			final String element_End = element[3];

			String[] dataElement = data.split(Constants.Object_SPLIT);
			final String data_Start = dataElement[0];
			final String data_ele1 = dataElement[1];
			final String data_ele2 = dataElement[2];	
			while (true) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(explicitwaitTime, TimeUnit.SECONDS)
						.pollingEvery(pollingTime, TimeUnit.MILLISECONDS)
						.ignoring(NoSuchElementException.class);

				List<WebElement> element_List = wait
						.until(new Function<WebDriver, List<WebElement>>() {

							@Override
							public List<WebElement> apply(WebDriver driver) {
								return driver.findElements(By.xpath(OR.getProperty(element_Start)+	data_Start
										+ OR.getProperty(element_ele1)+   data_ele1
										+ OR.getProperty(element_ele2)+   data_ele2+ OR.getProperty(element_End)));

							}

						});

				if (element_List.size() > 0) {
					return Constants.KEYWORD_PASS + " element is present";
				}

				else {
					return Constants.KEYWORD_FAIL + " Element not present";
				}

			}

		} catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getMessage();

		}
	}

	/**
	 * Added By Pooja This method is used to count all rows. For Ex : All
	 * roles in Admin Tab > Users >Change Password. It takes the common xpath of
	 * the all rows.
	 * */
	public String countAllRowsWithPagination(String object, String data) throws Exception {
		logger.debug("Entered into countAllRowsWithPagination()");
		int user_count = 0;
		try {

			if (allrowsCount.length() != 0) {
				allrowsCount = "";
			}
			user_count = explictWaitForElementSize(object);

			while (true) {

				int size = driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size();
				
				if (size == 0) {
					break;
				}
				if (size > 0) {

					driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
					Thread.sleep(5000);
					user_count += driver.findElements(
							By.xpath(OR.getProperty(object))).size();

				}else {

					user_count += driver.findElements(
							By.xpath(OR.getProperty(object))).size();
					break;

				}
				
			}
			allrowsCount=String.valueOf(user_count);
			return Constants.KEYWORD_PASS + " -- Rows Count Stored."
			+ " Count=" + user_count;
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * Added By Pooja This method is used to count all rows. For Ex : All
	 * roles in Admin Tab > Users >Change Password. It takes the common xpath of
	 * the all rows.
	 * */
	public String verifyAllRowsWithPagination(String object, String data) throws Exception {
		logger.debug("Entered into verifyAllRowsWithPagination()");
		int verify_user_count = 0;
		try {

			verify_user_count = explictWaitForElementSize(object);
		
			while (true) {
				int size=explictWaitForElementSize("pagination_next_link");
				
				if (size== 0) {
					break;
				}

				if (size > 0) {
					Thread.sleep(3000);
					WebElement ele=	wait.until(explicitWaitForElement(By.xpath(OR.getProperty("pagination_next_link"))));    				    
					ele.click();
					Thread.sleep(1000);

					verify_user_count += driver.findElements(By.xpath(OR.getProperty(object))).size();


				} 
				if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
					break;
				}

			else {

					verify_user_count += driver.findElements(By.xpath(OR.getProperty(object))).size();
					break;

				}
				

			}
			if(allrowsCount.equals(String.valueOf(verify_user_count))){
				return Constants.KEYWORD_PASS + " -- Rows count Matched."
						+ " Expected Count=" + verify_user_count+"--Actual Count="+allrowsCount;
			}
			else{
				return Constants.KEYWORD_FAIL + " -- Rows count does not Match."+ " Expected Count=" + verify_user_count+"--Actual Count="+allrowsCount;
			}
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}

	/**
	 * This method is used to verify that the Web Element is displayed .
	 * @author : Shweta Gupta
	 * @since :28/05/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.You can pass multiple data form data sheet using
	 *              data_split (|).
	 * @return PASS : If Elements are Displayed on the Web Page.
	 * @return Fail : Otherwise.
	 **/
	public String isMultipleElementDisplayedUsingData(String object, String data) 
	{
		logger.debug("Entered into isWebElementDisplayedUsingData()");
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);

			boolean element=true;    			
			int counter=0;    			
			boolean flag=true;

			// validates the parameter
			if (xpathStart == null || xpathEnd == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";

			for(int i=0;i<temp.length;i++)
			{ 
				logger.debug(xpathStart +temp[i]+ xpathEnd);
				element = driver.findElement(By.xpath((xpathStart) + temp[i] + (xpathEnd))).isDisplayed();
				if (!element)
				{
					flag=false;
					break;
				}
				counter++;
			} 
			if(flag)
			{
				if(counter==1)
					return Constants.KEYWORD_PASS + " -- Single webElement displayed";
				else
					return Constants.KEYWORD_PASS+"--"+ counter + "--webElements displayed";
			}
			else
				return Constants.KEYWORD_FAIL + " -- No webElements is displayed";
		}
		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
		}
	}

	/* @since 02/05/14
	 * @author Anil Kumar Mishra This method is used to enter input in text box
	 *         using send keys without clear text field.
	 */

	public String enterInputWithoutClear(String object, String data) {
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try {

			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			ele.sendKeys(data);
			return Constants.KEYWORD_PASS + "--" + data;
		}

		catch (Exception e) {
			e.printStackTrace();
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getLocalizedMessage();
		}
	}



	/**
	 * This method is used to verify Css Property 
	 * @author Navdeep Kaur
	 * @since 08 June, 2015 
	 * @param object: It accepts two xpath by comma(,) split.
	 * @param data : data required to complete x-path passed first and Css Property is passed at the end.
	 * 				Data is splitted by '|' .Also we can pass multiple data values to verify Css Property.
	 * @return It Returns Pass if element is present
	 *         else returns Fail.
	 **/
	public String verifyCssPropertyUsingData(String object, String data) 
	{
		try 
		{
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);

			// validates the parameter
			if (xpathStart == null || xpathEnd == null ||data == null) {
				return Constants.KEYWORD_FAIL + "Object or Data is not passed properly";
			}

			String temp[]=data.split(Constants.DATA_SPLIT);

			String property[] = temp[temp.length-1].split(":");
			String exp_prop = property[0];
			String exp_value = property[1];
			boolean flag = true;                  
			String prop=null;

			for(int i=0;i<temp.length-1;i++)
			{
				object=xpathStart+temp[i]+xpathEnd;
				prop = driver.findElement(By.xpath(object)).getCssValue(exp_prop);
				logger.debug(prop);
				logger.debug(exp_value);
				if (!prop.trim().equals(exp_value.trim())) 
				{
					flag=false;
					logger.debug(temp[i]+"fail");
					break;
				}
			}

			if (flag)
				return Constants.KEYWORD_PASS + "--actvalue-" + prop + "--expectes-" + exp_value;
			else
				return Constants.KEYWORD_FAIL;

		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) 
		{
			return Constants.KEYWORD_FAIL + " " + e.getMessage();
		}
	}

	/**Date=15-06-2015
	 * Added By Pooja This method is used to enter data in all the textfields with same Xpath expressions.
	 * */

	public String writeInInputForAll(String object, String data) {
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try {

			List<WebElement> all_inputs = explictWaitForElementList(object);
			int size=explictWaitForElementSize(object);
			if(size==0)
				return Constants.KEYWORD_FAIL +"No Input Present";
			Thread.sleep(2000); 
			for(int i=0;i<size;i++){
				all_inputs.get(i).sendKeys(data);

			}

		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "--" + data;

	}	   


	/**
	 * This method is used to verify that the Web Element is not displayed .
	 * @author : Shweta Gupta
	 * @since :12/06/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts and both
	 *            parts<Br> are given as object parameter that are splitted by ","
	 * @param data
	 *            : It accept data required to complete the xpath given from the <Br>
	 *              object parameter.You can pass multiple data form data sheet using
	 *              data_split (|).
	 * @return PASS : If Elements are not Displayed on the Web Page.
	 * @return Fail : Otherwise.
	 **/
	public String isMultipleElementNotDisplayedUsingData(String object, String data) 
	{
		logger.debug("Entered into isMultipleElementNotDisplayedUsingData()");
		try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);

			boolean element=true;    			
			int counter=0;    			
			boolean flag=true;

			// validates the parameter
			if (xpathStart == null || xpathEnd == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";

			for(int i=0;i<temp.length;i++)
			{ 
				logger.debug(xpathStart +temp[i]+ xpathEnd);
				element = driver.findElement(By.xpath((xpathStart) + temp[i] + (xpathEnd))).isDisplayed();
				if (element)
				{
					flag=false;
					break;
				}
				counter++;
			} 
			if(flag)
			{
				if(counter==1)
					return Constants.KEYWORD_PASS + " -- Single webElement not displayed";
				else
					return Constants.KEYWORD_PASS+"--"+ counter + "--webElements not displayed";
			}
			else
				return Constants.KEYWORD_FAIL + " -- WebElements displayed";
		}
		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
		}
	}


	/**
	 * This method is used to verify that the input box is blank or not .
	 * @author : Shweta Gupta
	 * @since :16/06/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts splitted by ","
	 * @param data
	 *            : It accepts multiple data using (|) Split.
	 *              
	 * @return PASS : If desired input box is blank
	 * @return Fail : Otherwise.
	 **/
	public String verifyBlankValueForMultipleInput(String object, String data) {
		logger.debug("Verifying the blank values");
		try {
			keyUtil.browserSpecificPause(object, data);
			String element[] = object.split(Constants.Object_SPLIT);
			String element_Start = element[0];
			String element_End = element[1];
			String temp[] = data.split(Constants.DATA_SPLIT);
			boolean flag=true;
			int counter=0;

			if (element_Start == null || element_End == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";

			for(int i=0;i<temp.length;i++)
			{ 
				WebElement ele = driver.findElement(By.xpath(OR.getProperty(element_Start) + temp[i] + OR.getProperty(element_End)));   
				String actual=ele.getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
				logger.debug("act" + actual);
				if (actual.length() > 0)
				{
					flag=false;
					break; 
				}
				counter++;    						    					
			}     			
			if(flag)
			{
				return Constants.KEYWORD_PASS+"--"+ counter + "--inputs are blank";
			}
			else
				return Constants.KEYWORD_FAIL + " --Inputs are not blank";

		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}

	/**
	 * Created by Sanjay Sharma On 19/06/2015 This method is used to Verify
	 * Selected date in (MM/DD/YYYY) Format 
	 * 
	 * @param object
	 *            :This method accepts One xpath,i.e location of the Input field
	 * @param data
	 *            : Pass Selected date in number to data e.g 10
	 * @return:PASS , if displayed date matches with Selected date in
	 *              (MM/DD/YYYY) Format. FAIL: otherwise.
	 */

	public String verifySelectedDate(String object, String data) {
		logger.debug("Verifying the Selected Date");
		try {
			WebElement ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));        	
			String result = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);
			Calendar cal = Calendar.getInstance();
			String month=String.valueOf(cal.get(Calendar.MONTH)+1);
			if(month.length()==1)
			   month="0"+month;
			String expected= month + "/"+ data + "/" + cal.get(Calendar.YEAR);
			logger.debug("expected" + expected);
			logger.debug("act" + result);
			if (result.trim().equals(expected)) 
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " --Selected date is not displayed, actual: " + result + " -- Expected  = " + expected ;
		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	/**
	 * This method is used to Verify Entered Current date,Previous date,Future date
	 * @author :Navdeep Kaur
	 * @since :22/06/2015
	 * @param object : Xpath of expected Field
	 * @param data: if You want to Verify date for Next two
	 *                             days then pass 2 from xls and if you                          
	 * want to Verify date of Previous 5 days ,then pass -5 from xls.
	 * If You want to Verify Current date then Pass 0 Form xls and respectively  .
	 */
	public String verifyDateBeforOrAfterCurrentDate(String object, String data) {
		try {
			 
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,(Integer.parseInt(data)));
			String month=String.valueOf(cal.get(Calendar.MONTH)+1);
			String date=String.valueOf(cal.get(Calendar.DATE));
			if(month.length()==1)
				month="0"+month;
			if(date.length()==1)
				date="0"+date;
			String val= month + "/"+ date + "/" + cal.get(Calendar.YEAR);
			logger.debug(val);
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String actual=ele.getText().trim();
			actual=actual.split(" ")[0];
			logger.debug(actual);
			if(val.equals(actual))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL;
				
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL + e.getCause() ;
		}
	}
	
	/**
	 * Karan Sood 24 June, 2015
	 * This method is used to verify whether each row displaying 
	 * contains some specific data
	 * 
	 * @param object - :It accepts Three xpaths that are separated by ','
	 *           		First xpath is of All Rows
	 *            		Second xpath is _start Xpath for column containing particular data
	 *      		    Third xpath is _end Xpath for column containing particular data   
	 *         
	 * @param data  - : Pass Data Needed For Second and Third Xpath
	 * @return
	 */

	public String isAllRowsHasSpecificDataUsingData(String object, final String data) {
		logger.debug("isAllRowsHasSpecificDataUsingData..");

		try {
			String values[] = object.split(Constants.Object_SPLIT);
			String totalRows = values[0];
			final String specificVal_start = values[1];
			final String specificVal_end = values[2];
			
			List<WebElement> total_rows = explictWaitForElementList(totalRows);
			int i1 = total_rows.size();
			logger.debug("Total Rows :- " + i1);

			List<WebElement> total_rows_with_data = wait.until(new Function<WebDriver, List<WebElement>>() {

					@Override
					public List<WebElement> apply(WebDriver driver) 
					{
					return driver.findElements(By.xpath(OR.getProperty(specificVal_start)+ data+ OR.getProperty(specificVal_end)));
					}

				});

			int i2 = total_rows_with_data.size();
			logger.debug("Total Rows with Specific Data:- " + i2);
			
			if (i1 == i2)
				return Constants.KEYWORD_PASS + "--list contains Specific Data";
			else
				return Constants.KEYWORD_FAIL + "--list does not contains Specific Data";

			}
		
			catch(TimeoutException ex)	
			{
				return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
			}
			catch (Exception e) 
			{
				return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
			}

		}
	
	
	/**
	 * Karan Sood 24 June, 2015
	 * This method Clicks and verify Correct Checkbox is checked or Not Checked
	 *  in all the form links displayed using position of the link
	 * 
	 * @param object - :It accepts Five xpaths that are separated by ','
	 *           		First xpath is of All Form Links under Name column
	 *            		Second xpath is _start Xpath of All Form Links created using position() function
	 *      		    Third xpath is _end Xpath of All Form Links created using position() function	      			   
	 *         			Fourth xpath is of Checkbox which has to be verified to be checked.
	 *         			Fifth xpath is of cancel Link/Close Button
	 *         
	 * @param data  - : Pass -- verifyCheckBoxSelected  [if have to verify Checkbox is Checked]
	 * 					or
	 * 					Pass -- verifyCheckBoxIsNotSelected [if have to verify Checkbox is Not Checked]
	 * 
	 **/
	
	public String clickAndCheckFormDetails(String object, String data) {
		logger.debug("clickAndCheckFormDetails..");
		
		try {
			String[] objects = object.split(",");
			String form_links = objects[0];
			String formLink_start= objects[1];
			String formLink_end= objects[2];
			String checkbox = objects[3];
			String cancel = objects[4];
			String result_cb="";
			
			if (form_links == null || formLink_start == null || formLink_end == null || checkbox == null || cancel == null||data == null)
				return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
			
				int element_count = explictWaitForElementSize(form_links);
							
					if (element_count > 0) {
					for (int i=1; i<=element_count; i++) 
					{									
						driver.findElement(By.xpath(OR.getProperty(formLink_start) + i + OR.getProperty(formLink_end))).click();
						keyUtil.browserSpecificPause(object, data);
						
						if(data.contains("verifyCheckBoxSelected"))
						result_cb = keyUtil.verifyCheckBoxSelected(checkbox, data);
						if(data.contains("verifyCheckBoxIsNotSelected"))
						result_cb = keyUtil.verifyCheckBoxIsNotSelected(checkbox, data);
									
						driver.findElement(By.xpath(OR.getProperty(cancel))).click();
						keyUtil.browserSpecificPause(object, data);
						if(result_cb.contains("FAIL"))
						break;
					} 
					if(result_cb.contains("FAIL"))
					return Constants.KEYWORD_FAIL + "--" + "InCorrect Forms are displaying";
					return Constants.KEYWORD_PASS + "--" + "Correct Forms are displaying";
				}				
				else {
					return Constants.KEYWORD_FAIL + "No Form Links Found";
					}					
				} 
			catch (Exception e)
			{
			return Constants.KEYWORD_FAIL + "-" + e.getMessage();
			}
		}
	
	
	 /**Since 24/04/2015 Created By Pooja 
	 * write in input by finding xpath
	 * * @param object:xpath split by ','
	 * @param data:data split by '|', for example:data to complete xpath|data to be entered.
	 * @return
	 */
	/** Modified by Navdeep on 29/07/2015
	 * Added trim() for data values
	 */
	public String writeInInputUsingSplit(String object, String data) {
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try {
			String browser = CONFIG.getProperty("browserType");
			String value[]=object.split(Constants.Object_SPLIT);
			String xpathStart=OR.getProperty(value[0]);
			String xpathEnd=OR.getProperty(value[1]);

			String data_value[]=data.split(Constants.DATA_SPLIT);
			String dataStart=data_value[0].trim();
			String dataEnd=data_value[1].trim();

			WebElement ele = wait.until(explicitWaitForElement(By.xpath(xpathStart+dataStart+xpathEnd))); 
			Thread.sleep(2000); 
			((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", ele, dataEnd);
			if (browser.equals("IE")) {
				Thread.sleep(3000);
			} 


		} 
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
		return Constants.KEYWORD_PASS + "Entered Data--" + data;

	}
   

    /**Since 24/04/2015 Created By Pooja 
	 * Clears an Input Field
	 * @param object:xpath split by ','
	 * @param data:data
	 * @return
	 */
	
	public String clearInputUsingSplit(String object, String data) {
		try {
			String value[]=object.split(Constants.Object_SPLIT);
			String xpathStart=OR.getProperty(value[0]);
			String xpathEnd=OR.getProperty(value[1]);
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(xpathStart+data+xpathEnd)));

			ele.clear();
			return Constants.KEYWORD_PASS;
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}

		catch (Exception nse) {

			return Constants.KEYWORD_FAIL + nse.getLocalizedMessage();// .printStackTrace();
		}


	}
	
	
	/**
	 * Karan Sood 26 June, 2015
	 * This method is used to verify that the elements of drop down list are in sorted order
	 * Ignoring First option or as Many options as required through our Xpath
	 * 
	 * @param object - :It accepts Single xpath made till Option tag 
	 *           		         
	 * @param data  - : No Data Needed
	 * @return
	 */
	
	public String checkDDSortedIgnoringSomeElements(String object, String data) {
		try {
			logger.debug("checkDDSortedIgnoringSomeVal..");
			boolean flag = true;
			List<String> actual_list = new ArrayList<String>();
			List<String> sortedlist = new ArrayList<String>();
			
			List<WebElement> options = explictWaitForElementList(object);
			
			for (int i = 0; i < options.size(); i++) {
				String name = options.get(i).getText();
				actual_list.add(name);				
				sortedlist.add(name);
				}
			Collections.sort(sortedlist, String.CASE_INSENSITIVE_ORDER);
			logger.debug("Actual List :- " + actual_list);
			logger.debug("Sorted List :- " + sortedlist);
			
			for (int i = 0; i < sortedlist.size(); i++) {
				if (!sortedlist.get(i).equals(actual_list.get(i))) 
					{
					flag = false;
					break;
					}
				}
			if (flag)
				return Constants.KEYWORD_PASS + "--elements in drop down are in sorted order";
			else
				return Constants.KEYWORD_FAIL + "--elements in drop down are NOT in sorted order";
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
	 * This method is used to verify that the Web Element is present only once .
	 * @author : Sudhir Kumar Naphray
	 * @since :26/06/2015
	 * @param object
	 *            : It accept one xpath that is divided into two parts splitted by ","
	 * @param data
	 *            : Data should be pass to splitted xpath, Also work for 
	 *              Multiple data (using "Data_SPLIT), if required
	 *            
	 * @return PASS : If Element is Present once on the Web Page.
	 * @return Fail : Otherwise.
	 **/
	public String verifyElementsPresentOnceUsingData(String object, String data) {

		logger.debug("verifying the element is present once ");
               try {
			keyUtil.browserSpecificPause(object, data);
			String objArr[] = object.split(Constants.Object_SPLIT);
			String xpathStart = OR.getProperty(objArr[0]);
			String xpathEnd = OR.getProperty(objArr[1]);
			String temp[] = data.split(Constants.DATA_SPLIT);
			int element, counter=0;
           boolean flag=true;
			// validates the parameter
			if (xpathStart == null || xpathEnd == null || data == null) {
				return Constants.KEYWORD_FAIL + "Object is not passed properly";
			}
			logger.debug(xpathStart + data.trim() + xpathEnd);
           
			for(int i=0;i<temp.length;i++)
			{
			element = driver.findElements(By.xpath((xpathStart) + temp[i] + (xpathEnd))).size();
			
		         if (element != 1){
			        flag=false;
		            break;
	               }
	                counter++;
           } 
        if(flag){
	        if(counter==1)
			  return Constants.KEYWORD_PASS + "--element is present once";
		    else
			  return Constants.KEYWORD_PASS + "--more than one element is present once";
           }
       else
   	  return Constants.KEYWORD_FAIL + " -- Webelement is present more than once";
       } catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found "
					+ e.getMessage();
		}
	}
	/**
	 * This method is used to verify the text not present by using JavascriptExecutor
	 * @author : Sudhir Kumar Naphray
	 * @since :26/06/2015
	 * @param object
	 *            :xpath of the Webelement from where the data is to be verified.
	 * @param data
	 *            :data to be verified.
	 * @return PASS : If Text is not Present in the webelement.
	 * @return Fail : Otherwise
	 */
	public String verifyTextNotPresentUsingJs(String object, String data) {
		logger.debug("Verifying the text");
		try {
			WebElement ele = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			String text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", ele);
			if (text.trim().equals(data.trim())||text.trim().contains(data.trim())) // modified Added trim()
				return Constants.KEYWORD_FAIL + data+" Text present";
			else
				return Constants.KEYWORD_PASS + data+" -- Text is not present -- ";
		}

		catch (TimeoutException ex) {
			return Constants.KEYWORD_FAIL + ex.getCause();
		} catch (Exception ex) {
			return Constants.KEYWORD_FAIL + " Object not found "
					+ ex.getMessage();
		}
	}
	/**
	 * @author Shweta Gupta on 30/06/2015
	 * This method is used to copy a text from a file and compare with the text in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */

	public String verifyTextInFckEditorFromFile(String object, String data) 
	{
		logger.debug("verifying the pasted text in Fck editor...");
		logger.debug("Data: " + data);
		try {

			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();	
			
			String actual=ele.getText().trim();				
			driver.switchTo().defaultContent();		
			
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp = data;
			String file_exist_path = currDir + sep + "externalFiles" + sep+ "uploadFiles" + sep + temp.trim();
			String file_content = null;
			
			File file = new File(file_exist_path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) 
			{
				stringBuffer.append(line);
			}
			fileReader.close();
			file_content = stringBuffer.toString();
					
			if(actual.equals(file_content))
				return Constants.KEYWORD_PASS + "--Same text Present in Fck Editor";
			else
				return Constants.KEYWORD_FAIL +"---Values in file and Fck Editor are different";
		}

		catch (TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
		} catch (Exception e) {
	}
		return Constants.KEYWORD_FAIL + "--Text not matched";
}



	/**
	 * @author Shweta Gupta on 30/06/2015
	 * This method is used to copy a text from a Pdf file and compare with the text in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */

	public String verifyTextInFckEditorFromPdf(String object, String data) 
	{
		logger.debug("verifying the pasted text from Pdf in Fck editor...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();	
			
			String actual=ele.getText().trim();			
			driver.switchTo().defaultContent();
			
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();			
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			
			PdfReader text=new PdfReader(fis);
			String expected=PdfTextExtractor.getTextFromPage(text, 1);

			logger.debug("Value From Pdf File-- "+expected);
			logger.debug("Value From Ck Editor -- "+actual);
			
			fis.close();
			
			if(actual.equals(expected))
				return Constants.KEYWORD_PASS + "--Same Value Present in Fck Editor";
			else
				return Constants.KEYWORD_FAIL +"---Both Values are different";
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to get text from DOC file" + e.getMessage();

		}
	}


	/**
	 * @author Shweta Gupta on 30/06/2015
	 * This method is used to copy a text from a Xls file and compare with the text in CK Editor
	 * @param object[Pass object as location of CK Editor]
	 * @param data[File name]
	 * @return
	 */

	public String verifyTextInFckEditorFromXls(String object, String data) 
{
		logger.debug("verifying the pasted text in Fck editor...");
		logger.debug("Data: " + data);
		try {
			WebElement element = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			driver.switchTo().frame(element);
			WebElement ele=driver.switchTo().activeElement();	
			
			String actual=ele.getText().trim();			
			driver.switchTo().defaultContent();
			
			String currDir = System.getProperty("user.dir");
			String sep = System.getProperty("file.separator");
			String expected=null;
			String temp= data;
			String file_exist_path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + temp.trim();			
			File file = new File(file_exist_path);
			FileInputStream fis=new FileInputStream(file);
			
			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<?> ite = sheet.rowIterator();
			while(ite.hasNext())
			{
				Row row = (Row) ite.next();
				Iterator<Cell> cite = row.cellIterator();
				while(cite.hasNext()){
					Cell c = cite.next();
					logger.debug(c.toString() +"  ");
					expected=c.getStringCellValue();
				}
			}
			fis.close();

			logger.debug("Value From Xls File-- "+expected);
			logger.debug("Value From Ck Editor -- "+actual);			
			
			if(actual.equals(expected))
				return Constants.KEYWORD_PASS + "--Same Value Present in Fck Editor";
			else
				return Constants.KEYWORD_FAIL +"---Both Values are different";
		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Unable to get text from DOC file" + e.getMessage();

		}
	}
	
	/**
	 * This method is used to verify that the input box is blank or not .
	 * @author : Shweta Gupta
	 * @since :01/07/2015
	 * @param object
	 *            : It accepts Single xpath of input box.
	 * @param data
	 *            : not required
	 * @return PASS : If desired input box is blank
	 * @return Fail : Otherwise.
	 **/
	public String verifyBlankInput(String object, String data) 
	{
		logger.debug("Verifying the input is blank or not...");
		try {
			
			keyUtil.browserSpecificPause(object, data);
			String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getAttribute(OR.getProperty("ATTRIBUTE_VALUE")).trim();
			logger.debug("act" + actual);
			if (actual.length() > 0)
				return Constants.KEYWORD_FAIL + " -- Some Value Present";	 
			else		   						    				  			
				return Constants.KEYWORD_PASS+"--"+ "--input is blank";
				}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}
	
	/**
	 * This method is used to verify that the WebElement is Blank or not
	 * @author : Shweta Gupta
	 * @since :01/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : not required	             
	 * @return PASS : If desired element to be checked is blank
	 * @return Fail : Otherwise.
	 **/
	public String verifyBlankValueForMultiple(String object, String data) 
	{
		logger.debug("Verifying the blank values...");
		try {
			keyUtil.browserSpecificPause(object, data);			
			List<WebElement> all = explictWaitForElementList(object);
			boolean flag=true;
			int counter=0;

			for(int i=0;i<=all.size();i++)
			{ 
				WebElement ele = driver.findElement(By.xpath(OR.getProperty(object)));   
				String actual=ele.getText().trim();
				if (actual.length() > 0)
				{
					flag=false;
					break; 
				}
				counter++;    						    					
			}     			
			if(flag)
			{
				return Constants.KEYWORD_PASS + "--Values are blank";
			}
			else
				return Constants.KEYWORD_FAIL + " --Values are not blank";

		}
		catch(TimeoutException ex)

		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + " Object not found " + e.getMessage();
		}
	}	
	
	/**
	 * This method is used to verify that the value from data is not the selected in the dropdown.
	 * @author : Shweta Gupta
	 * @since :03/07/2015
	 * @param object
	 *            : It accept xpath of the dropdown
	 * @param data
	 *            : Value to be verified	             
	 * @return PASS : If desired value is not the selected value in the dropdown
	 * @return Fail : Otherwise.
	 **/
	
	public String verifyValueNotSelectedInDropdown(String object, String data)
	{
		logger.debug("Entered into verifyValueNotSelectedInDropdown()..");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Select sel = new Select(select);
			String defSelectedVal = sel.getFirstSelectedOption().getText();
			logger.debug("Value selected in the dropdown - "+defSelectedVal);
			logger.debug("Data - "+ data);
			if (!defSelectedVal.trim().equals(data.trim())) 
			{
				return Constants.KEYWORD_PASS + " -- Default value not same as Data passed ";
			} else {
				return Constants.KEYWORD_FAIL + " -- Default value same as Data passed ";
			}
		} 
		catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}
	
	/**
	 * This method is used to verify Only First Name of Users
	 * @author : Pankaj Dogra
	 * @since :03/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyFirstName(String object, String data) {
		logger.debug("Verifying First Name Under Name column");
		try {
			boolean flag = false;
			String name=null,value=null;
			String[] first=new String[0];
			while (true) {
				int size = KeywordEventsUtill.explictWaitForElementList("pagination_next_link").size();
				List<WebElement> all_values = explictWaitForElementList(object);
				if (size > 0) {
					for (int i = 0; i< all_values.size(); i++) {
						value = all_values.get(i).getText();
						first=value.split(Constants.Object_SPLIT);
						name= first[first.length-1];
						if (name.toLowerCase().trim().contains(data.toLowerCase().trim())) {
							flag = true;
						}
						else {
							flag = false;
							break;
						}
					}
					driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
					Thread.sleep(4000);
				} 
				else {
					all_values = explictWaitForElementList(object);
					for (int i = 0; i < all_values.size(); i++) {
						value = all_values.get(i).getText();
						first=value.split(Constants.Object_SPLIT);
						name= first[first.length-1];
						if (name.toLowerCase().trim().contains(data.toLowerCase().trim())) {
							flag = true;
						}
						else {
							flag = false;
							break;
						}
					}
				}
				if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size() == 0) {
					break;
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- All First Name Text contains --"+data;
			} else
				return Constants.KEYWORD_FAIL +" -- All First Name Text does not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	

	/**
	 * This method is used to verify Only Last Name of Users
	 * @author : Pankaj Dogra
	 * @since :03/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyLastName(String object, String data) {
		logger.debug("Verifying last Name under Name Column");
		try {
			boolean flag = false;
			String name=null,value=null;
			String[] last=new String[0];
			while (true) {
				int size = KeywordEventsUtill.explictWaitForElementList("pagination_next_link").size();
				List<WebElement> all_values = explictWaitForElementList(object);
				if (size > 0) {
					for (int i = 0; i < all_values.size(); i++) {
						value = all_values.get(i).getText();
						last=value.split(Constants.Object_SPLIT);
						name= last[0];
						if (name.toLowerCase().trim().contains(data.toLowerCase().trim())) {
							flag = true;
						}
						else {
							flag = false;
							break;
						}
					}
					driver.findElement(By.xpath(OR.getProperty("pagination_next_link"))).click();
					Thread.sleep(4000);
				} 
				else {
					all_values = explictWaitForElementList(object);
					for (int i = 0; i < all_values.size(); i++) {
						value = all_values.get(i).getText();
						last=value.split(Constants.Object_SPLIT);
						name= last[0];
						if (name.toLowerCase().trim().contains(data.toLowerCase().trim())) {
							flag = true;
						} 
						else {
							flag = false;
							break;
						}
					}
				}
				if (driver.findElements(By.xpath(OR.getProperty("pagination_next_link"))).size()==0) {
					break;
				}
			}
			if (flag) {
				return Constants.KEYWORD_PASS + " -- All Last Name Text contains --"+data;
			} else
				return Constants.KEYWORD_FAIL +" -- All Last Name Text does not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	
	/**
	 * This method is used to verify Only First Name of Users without pagination
	 * @author : Pankaj Dogra
	 * @since :03/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyFirstNameWithoutPagination(String object, String data) {
		logger.debug("Verifying First Name Under Name column");
		try {
			boolean flag = false;
			String value=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
				String[] first=value.split(Constants.Object_SPLIT);
				String  name= first[first.length-1];
				if (name.toLowerCase().trim().contains(data.toLowerCase().trim()))
					flag = true;
			if (flag) 
				return Constants.KEYWORD_PASS + " -- First Name Text contains --"+data;
			else
				return Constants.KEYWORD_FAIL +" -- First Name Text does not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	
	/**
	 * This method is used to verify Only Last Name of Users without pagination
	 * @author : Pankaj Dogra
	 * @since :03/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyLastNameWithoutPagination(String object, String data) {
		logger.debug("Verifying last Name under Name Column");
		try {
			boolean flag = false;
			String value=wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
				String[] last=value.split(Constants.Object_SPLIT);
				String name= last[0];
				if (name.toLowerCase().trim().contains(data.toLowerCase().trim()))
					flag = true;
			if (flag)
				return Constants.KEYWORD_PASS + " -- Last Name Text contains --"+data;
			else
				return Constants.KEYWORD_FAIL +" -- Last Name Text does not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	
	/**
	 * This method is used to enter values in number of inputs
	 * @author : Navdeep Kaur
	 * @since :07/07/2015
	 * @param object
	 *            : It accept one common xpath for all input
	 * @param data
	 *            : Data should be passed, Work for 
	 *              Multiple data (using "Data_SPLIT).          
	 **/
	public String writeInInputDifferentValues(String object, String data) 
	{
		logger.debug("Writing in text box");
		logger.debug("Data: " + data);
		try 
		{
			List<WebElement> all_inputs = explictWaitForElementList(object);
			int inputsize=explictWaitForElementSize(object);
			logger.debug("No of Inputs are " +inputsize);
			String temp[]=data.split(Constants.DATA_SPLIT);
			int datasize=temp.length;
			logger.debug("No of Data variables are " +datasize);
			
			for(int i=0;i<datasize;i++)
			{
				all_inputs.get(i).sendKeys(temp[i]);
			}
		} 
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) 
		{
			return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
		}
			return Constants.KEYWORD_PASS + "--" + data;
	}  
	
	/**
	 * This method is used to verify Only First Name of Users without pagination
	 * @author : Sanjay Sharma
	 * @since :07/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyFirstUserNameWithoutPagination(String object, String data) {
		logger.debug("Verifying First Name Under column");
		try {
			boolean flag = false;

			List<WebElement> expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
				String value = expected.get(i).getText();
				String[] first=value.split(Constants.Name_SPLIT);
				String  name= first[0];
				if (name.toLowerCase().trim().contains(name.toLowerCase().trim())) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			
			if (flag) 
				return Constants.KEYWORD_PASS + " -- All First Name Text contains --"+data;
			else
				return Constants.KEYWORD_FAIL +" -- All First Name Text  not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	
	/**
	 * This method is used to verify Only Last Name of Users without pagination
	 * @author : Sanjay Sharma
	 * @since :07/07/2015
	 * @param object
	 *            : It accept one common xpath of all the webelements that need to be verified.
	 * @param data
	 *            : it Required data to Verify text of element	             
	 * @return PASS : If text passed through data is valid
	 * @return Fail : Otherwise.
	 **/
	public String verifyLastUserNameWithoutPagination(String object, String data) {
		logger.debug("Verifying last Name under Column");
		try {
			boolean flag = false;
			List<WebElement> expected = explictWaitForElementList(object);
			for (int i = 0; i < expected.size(); i++) {
				String value = expected.get(i).getText();
				String[] last=value.split(Constants.Name_SPLIT);
				String  name= last[1];
				if (name.toLowerCase().trim().contains(name.toLowerCase().trim())) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
			if (flag)
				return Constants.KEYWORD_PASS + " -- All Last Name Text contains --"+data;
			else
				return Constants.KEYWORD_FAIL +" -- All Last Name Text  not contains --"+data;
		} 
		catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}

	}
	
	
	/**
	 * This method is used to press BackSpace key multiple times from keyboard.
	 * @author Surender
	 * @param object
	 *            : Xpath of the field on which action is to be performed.
	 * @param data
	 *            : Number of times backspace key to be pressed.
	 * @since 10/07/2015
	 */
	public String pressBackSpaceKeyMultipleTimes(String object, String data) {
		logger.debug("entered into pressBackSpaceKeyMultipleTimes");
		try {
			Actions act = new Actions(driver);
			int count=Integer.parseInt(data);
			for(int i=1;i<=count;i++)
			{
			Action pressKey = act.sendKeys(wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))),Keys.BACK_SPACE).build();
			pressKey.perform();
			}
			return Constants.KEYWORD_PASS + "--" + "BackSpace key pressed "+ count+ " times";
		}catch(TimeoutException ex){
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception ex) {
			return Constants.KEYWORD_FAIL + ex.getLocalizedMessage();
		}
	}
	/**
	 * verifySortingWithCommas method ,checks Whether Organizations are in sorting order, Separated by Comma
	 * @author Sanjay Sharma
	 * @since 13/07/2015
	 * @param object: Elements xpath , on sorting will be performed
	 * @param data: No data needed
	 * @return : Pass, if matched Otherwise fail.
	 * @throws Exception
	 */
	public String verifySortingWithCommas(String object, String data)
			throws Exception {
		logger.debug("Entered into verifySortingWithCommas()");
		try {
			List<String> sortedElements = new ArrayList<String>();
			List<String> actualElements = new ArrayList<String>();
			String str = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
			String actualArr[] = str.split(Constants.Object_SPLIT);
			for (int k = 0; k < actualArr.length; k++) {
				sortedElements.add(actualArr[k].trim());
				actualElements.add(actualArr[k].trim());
			}
			Collections.sort(sortedElements, String.CASE_INSENSITIVE_ORDER);

			logger.debug("actual is as follows-- " + actualElements);
			logger.debug("Expected is as follows-- " + sortedElements);
			if (sortedElements.equals(actualElements)) {
				return Constants.KEYWORD_PASS + " -- Elements are sorted";
			} else
				return Constants.KEYWORD_FAIL + " -- Elements are not sorted";
		} catch (Exception e) {
			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
		}
	}
	/**
	 * This method is used to click on Popup window 'CANCEL' button
	 * by using a third party tool AutoIt.
	 * @author : Sudhir Kumar Naphray
	 * @since :16/07/2015
	 * @param object
	 *            :no required
	 * @param data required is name of the .exe file[
	 * Ex:clickCancelBtnUsingAutoIt.exe]
	 * @return PASS : If click on cancel button of popup window
	 * @return Fail : Otherwise
	 */
   public String clickCancelButtonUsingAutoIt(String object, String data)
				throws IOException {
			try {
				
				String currDir = System.getProperty("user.dir");
				String sep = System.getProperty("file.separator");				
				String path_exe = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + data.trim();				
				
				if (CONFIG.getProperty("browserType").equals("Mozilla")) {
					new ProcessBuilder(path_exe,"File Upload").start();
				} else if (CONFIG.getProperty("browserType").equals("IE")) {
					new ProcessBuilder(path_exe,"Choose File to Upload").start();
				} else if (CONFIG.getProperty("browserType").equals("Chrome")) {
					new ProcessBuilder(path_exe,"Open").start();
				}
				Thread.sleep(5000);		

			} catch (Exception e) {
				return Constants.KEYWORD_FAIL + " Unable to click on button"
						+ e.getLocalizedMessage();
			}
			return Constants.KEYWORD_PASS + "-- Clicked on button successfully.";
		}
	
	/**
	 * This method verifies Value in Page Number Input Changing according to
	 * Last page.
	 * @author Vikas Bhadwal
	 * @since 23 July, 2015
	 * @param object
	 * It accepts two xpaths [pagn_page_info_span,pagn_page_num_input]
	 * @param data:None
	 *           
 */
	public String verifyLastValueInPageNumberInput(String object, String data) {
		try {
			logger.debug("Verifing the Value in Page Number input a/c Last page");
            String objArr[] = object.split(Constants.Object_SPLIT);
			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];
           if (XPATH1 == null || XPATH2 == null) {
				return Constants.KEYWORD_FAIL + " Either Xpath1 or Xpath2 is null. Please check the xpath";
			}
			
	        WebElement page_input = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[1]))));
			String fullString = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[0])))).getText();
			String strArr[] = fullString.split("of ");   // strArr[] stores two strings by splitting the full label string e.g "of 15" by 'of '
			int totalPage = Integer.parseInt(strArr[1]);
		    logger.debug("Last page should be : " + totalPage);
            String totalPageValue = String.valueOf(totalPage);
		    String selectedPage = page_input.getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));;
			logger.debug("In Page Number input value is : " + selectedPage);

       if (totalPageValue.equals(selectedPage)) {
				return Constants.KEYWORD_PASS + " -- Value is changing accordingly to the Last page ";
			} else {
				return Constants.KEYWORD_FAIL + "  -- Value is not change accordingly to the Last page";
			}
		} 
         catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -Some Exception occured" + e.getMessage();
		}

	}

	/**
	 * clickMultipleTimes method ,Click on given button/link multiple times
	 * @author Sanjay Sharma
	 * @since 24/07/2015
	 * @param object: Element xpath , on click to be performed
	 * @param data: How many times click to be performed. E.g 50
	 * @return : Pass, 
	 * @throws Exception
	 */
	public String clickMultipleTimes(String object, String data) {
		logger.debug("Inside clickMultipleTimes");
		WebElement ele=null;
		try {
			int count=Integer.parseInt(data);
			ele = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));
			for(int i=0;i<count;i++)
			{
				ele.click();
				keyUtil.browserSpecificPause(object, data);
			} 
		}
		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch(Exception e){
			return Constants.KEYWORD_FAIL+e.getMessage();
		}

		return Constants.KEYWORD_PASS;
	}

		/*
	 * Created By Pooja Sharma since 27 July,2015 , This method is used to verify the functionality of Pagination Links
	 * objcet:xpath of pagn_page_info_span,pagn_page_num_input,enabled_pagination_lnk,double_bracket_end
	 * data:Next,Previous,Last,First
	 */
	
	public String verifyPagination(String object, String data) {
		logger.debug("Entered into verifyPaginationLinks()");
		Boolean Flag=false;
		try {

			String[] data_value=data.split(",");
			String temp[] = object.split(",");
			int size=driver.findElements(By.xpath(OR.getProperty("pagn_links"))).size();
			if(size==0){
				return Constants.KEYWORD_FAIL+"Pagination Links are not present";
			}

			for(int i=0;i<data_value.length;i++){
				WebElement	ele = driver.findElement(By.xpath(OR.getProperty(temp[2]) + data_value[i]+ OR.getProperty(temp[3])));
				new ApplicationSpecificKeywordEventsUtil().clickJs(ele);      // modified by vikas bhadwal
				new KeywordEventsUtill().browserSpecificPause(object, data);

				if(data_value[i].equals("Next")){
					
					WebElement first_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk")));
					WebElement prev_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_prev_lnk")));
					WebElement page_value = driver.findElement(By.xpath(OR.getProperty("pagn_page_num_input")));
					String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", page_value);
					if(first_lnk.isDisplayed()&& prev_lnk.isDisplayed()&& value.equals("2")){
						Flag=true;
					}else
						return Constants.KEYWORD_FAIL+"Next Pagination Link is not working Properly";
				}
				else if(data_value[i].equals("First") || data_value[i].equals("Previous")){

					WebElement first_lnk = driver.findElement(By.xpath(OR.getProperty("first_disabled_lnk")));
					WebElement prev_lnk = driver.findElement(By.xpath(OR.getProperty("prev_disabled_lnk")));
					WebElement next_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_next_link")));
					WebElement last_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_last_lnk")));
					WebElement page_value = driver.findElement(By.xpath(OR.getProperty("pagn_page_num_input")));
					String value = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", page_value);
					if(first_lnk.isDisplayed()&& prev_lnk.isDisplayed()&&last_lnk.isDisplayed()&&next_lnk.isDisplayed()&& value.equals("1")){
						Flag=true;
					}else
						return Constants.KEYWORD_FAIL+"First/Previous Pagination Link is not working Properly";
				}

				else if(data_value[i].equals("Last")){

					WebElement first_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk")));
					WebElement prev_lnk = driver.findElement(By.xpath(OR.getProperty("pagination_prev_lnk")));
					WebElement next_lnk = driver.findElement(By.xpath(OR.getProperty("next_disabled_lnk")));
					WebElement last_lnk = driver.findElement(By.xpath(OR.getProperty("last_disabled_lnk")));

					if(first_lnk.isDisplayed()&& prev_lnk.isDisplayed()&&last_lnk.isDisplayed()&&next_lnk.isDisplayed()){
						this.verifyLastValueInPageNumberInput(object, data);
						Flag=true;
					}else
						return Constants.KEYWORD_FAIL+"Last Pagination Link is not working Properly";
				}

			}
			if(Flag==true)
				return Constants.KEYWORD_PASS+"All Pagination Links are working Properly";
		}

		catch(TimeoutException ex)
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {

			return Constants.KEYWORD_FAIL + e.getMessage();
		}
		return Constants.KEYWORD_FAIL+"Pagination Links are not working Properly";
	}
	
	/**
	 * This method is used to verify whether first value is selected in dropdown
	 * @author Navdeep Kaur
	 * @since 27 July, 2015
	 * @param object
	 * @param data:None
	 *           
 */
	public String verifyDropdownSelectedFirstValue(String object, String data) {
		logger.debug("Entered into verifyDropdownSelectedFirstValue()");
		try {
			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
			Select sel = new Select(select);
			String firstValue=sel.getOptions().get(0).getText();
			String defSelectedVal = sel.getFirstSelectedOption().getText();
			logger.debug(defSelectedVal);
			logger.debug(firstValue);
			if (defSelectedVal.trim().equals(firstValue.trim())) {
				return Constants.KEYWORD_PASS + " -- First Value is Selected ";
			} else {
				return Constants.KEYWORD_FAIL + " -- First Value is not Selected ";
			}
		} 
catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
		}
	}  
	
	/**This method verifies Value in Page Number Input Changing according to
	 * Second Last page.
	 * @author Navdeep Kaur
	 * @since 27 July, 2015
	 * @param object
	 * It accepts two xpaths [pagn_page_info_span,pagn_page_num_input]
	 * @param data:None
	 *           
	 */
	public String verifySecondLastValueInPageNumberInput(String object, String data) {
		try {
			logger.debug("Verifing the Value in Page Number input a/c Last page");
          String objArr[] = object.split(Constants.Object_SPLIT);
			final String XPATH1 = objArr[0];
			final String XPATH2 = objArr[1];
         if (XPATH1 == null || XPATH2 == null) {
				return Constants.KEYWORD_FAIL + " Either Xpath1 or Xpath2 is null. Please check the xpath";
			}			
	        WebElement page_input = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[1]))));
			String fullString = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(objArr[0])))).getText();
			String strArr[] = fullString.split("of ");   // strArr[] stores two strings by splitting the full label string e.g "of 15" by 'of '
			int totalPage = Integer.parseInt(strArr[1]);
			int secondLast=totalPage-1;
		    logger.debug("Last page should be : " + secondLast);
          String SecondLastValue = String.valueOf(secondLast);
		    String selectedPage = page_input.getAttribute(OR.getProperty("ATTRIBUTE_VALUE"));;
			logger.debug("In Page Number input value is : " + selectedPage);

     if (SecondLastValue.equals(selectedPage)) {
				return Constants.KEYWORD_PASS + " -- Value is changing accordingly to the Second Last page ";
			} else {
				return Constants.KEYWORD_FAIL + "  -- Value is not change accordingly to the Second Last page";
			}
		} 
       catch(TimeoutException ex)
		
		{
			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
		}
		catch (Exception e) {
		
			return Constants.KEYWORD_FAIL + " -Some Exception occured" + e.getMessage();
		}
	}
	
	/**
	 * This method is used for writing in input(using sendKeys) by finding xpath.
	 * @author : Surender
	 * @since :30/07/2014
	 * @param object : It accept 2 xpaths  splitted by comma(,)
	 * @param data : Data is splitted by pipelin(|). First part of data is required to complete xpath and second part of data for write operation.
	 * @return PASS : If data is written.
	 * @return Fail : Otherwise.
	 */
		public String enterInputUsingSplit(String object, String data) {
			logger.debug("Writing in text box");
			logger.debug("Data: " + data);
			try {
				String value[]=object.split(Constants.Object_SPLIT);
				String xpathStart=OR.getProperty(value[0]);
				String xpathEnd=OR.getProperty(value[1]);
				String data_value[]=data.split(Constants.DATA_SPLIT);
				String dataStart=data_value[0];
				String dataEnd=data_value[1];
				WebElement ele = wait.until(explicitWaitForElement(By.xpath(xpathStart+dataStart+xpathEnd))); 
				ele.clear();
				ele.sendKeys(dataEnd);
			} 
			catch(TimeoutException ex)
			{
				return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
			}
			catch (Exception e) {
				return Constants.KEYWORD_FAIL + " Unable to write " + e.getMessage();
			}
			return Constants.KEYWORD_PASS + "--" + data;
		}	
	
		/**
		 * Added By Surender 24/08/2015
		 * This Method verifies current date in format 'EEEE, MMMM dd, yyyy'
		 * Exampl for date format: Monday, August 24, 2015
		 * @param object : Xpath of the Webelement from where the date is to be verified.
		 * @param data: None
		 */
		public String verifyDayMonthDateYearFormat(String object, String data) {
			logger.debug("entered into verifyDayMonthDateYearFormat()");
			try {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(Constants.DAY_MONTH_DATE_YEAR);
				sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
				String expected=sdf.format(calendar.getTime());
				String actual = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object)))).getText();
				if(actual.contains(expected)){
					return Constants.KEYWORD_PASS + "--- Day Month Date Year values matched";
				}
			} 
			catch(TimeoutException ex)
			{
				return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
			}
			catch (Exception e) {
				return Constants.KEYWORD_FAIL + "" + e.getMessage();
			}
			return Constants.KEYWORD_PASS + "--" + data;
		}	
		/**
        	 * Kritika
        	 * This method is used to select a value from dropdown with contains
        	 * 13th July'2015
        	 * @param object
        	 * @param data
        	 * @return
        	 */
        	public String selectListWithContains(final String object, String data) {
        		logger.debug("Selecting from list");
        		try {

        			WebElement select = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(object)))));

        			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG").trim()));
        			String expected = data.trim();
        			logger.debug("Expected is > " + expected);
        			for (WebElement option : options) {

        				String actual = option.getText().trim();
        				logger.debug("Actual > " + actual);
        				if (actual.contains(expected)) {
        					logger.debug("Selected..." + data);
        					option.click();
        					return Constants.KEYWORD_PASS + "--" + data + " - selected";
        				}
        			}
        		}
        		catch(TimeoutException ex)
        		{
        			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
        		}
        		catch (Exception e) {
        			
        			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
        		}
        		return Constants.KEYWORD_FAIL + " - Could not select from list. ";
        	}
        	/**
        	 * Kritika
        	 * 13th July' 2015
        	 * This method verifies all the values of a dropdown with contains
        	 * @param object
        	 * @param data
        	 * @return
        	 */
        	public String verifyAllVauesOfListWithContains(String object, String data) {
        		logger.debug("verifyAllVauesOfListWithContains");
        		try {

        			boolean flag = false;
        			WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
        			List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
        			String temp = data;
        			String allElements[] = temp.split(Constants.Object_SPLIT);
        			String actual;
        			for (int i = 0; i < allElements.length; i++) {
        				actual = options.get(i).getText().trim();
        				logger.debug("Actual--"+actual);
        				logger.debug("Expected--"+allElements[i]);
        				if (actual.contains(allElements[i].trim())) {
        					flag = true;
        				} else {
        					flag = false;
        					break;
        				}
        			}
        			if (flag) {
        				return Constants.KEYWORD_PASS + " -- Dropdown's all values are present";
        			} else {
        				return Constants.KEYWORD_FAIL + " -- Dropdown's all values are not present";
        			}
        		} catch(TimeoutException ex)
        		
        		{
        			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
        		}catch (Exception e) {
        		
        			return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();

        		}
        	}
        	/**
        	 * Kritika
        	 * 13th July' 2015
        	 * This method verify a single value of a dropdown with contains
        	 * @param object
        	 * @param data
        	 * @return
        	 */

        	public String verifyListSelectionWithContains(String object, String data) {
        		logger.debug("Verifying Whether correct value is selected in Drop down....");
        		try {

        			String expectedVal = data.trim();
        			WebElement droplist = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
        			List<WebElement> droplist_cotents = droplist.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
        			String actualVal = null;
        			for (int i = 0; i < droplist_cotents.size(); i++) {
        				String selected_status = droplist_cotents.get(i).getAttribute(OR.getProperty("ATTRIBUTE_SELECTED"));
        				if (selected_status != null) {
        					actualVal = droplist_cotents.get(i).getText().trim();
        					logger.debug("actual value is > " + actualVal);
        					if (actualVal.contains(expectedVal)) {
        						return Constants.KEYWORD_PASS + "--Correct value is selected";
        					}
        				}
        			}

        		} catch(TimeoutException ex)
        		
        		{
        			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
        		}catch (Exception e) {
        		
        			return Constants.KEYWORD_FAIL + " - Could not find list. " + e.getMessage();

        		}
        		return Constants.KEYWORD_FAIL + "--Wrong value is selected";
        	}
        	/**
        	 * This method is used to verify that the Web Element is present with pagination .
        	 * @author : Sudhir Kumar Naphray
        	 * @since :01/08/2015
        	 * @param object
        	 *            : It accept one xpath that is divided into two parts and both
        	 *            parts<Br> are given as object parameter that are splitted by ","
        	 * @param data
        	 *            : It accept data required to complete the xpath given from the <Br>
        	 *              object parameter.You can pass multiple data form data sheet using
        	 *              data_split (|).
        	 * @return PASS : If Elements are Present on the Web Page.
        	 * @return Fail : Otherwise.
        	 **/

        	public String isMultipleElementPresentUsingDataWithPagination(String object, String data) {
        	   logger.debug("Entered into isMultipleElementPresentUsingDataWithPagination()");
        	   try {
        	       keyUtil.browserSpecificPause(object, data);
        	       String objArr[] = object.split(Constants.Object_SPLIT);
        	       String xpathStart = OR.getProperty(objArr[0]);
        	       String xpathEnd = OR.getProperty(objArr[1]);
        	       String temp[] = data.split(Constants.DATA_SPLIT);
        	       int element=0,counter=0;
        	       boolean flag=true;
        	       // validates the parameter
        	       if (xpathStart == null || xpathEnd == null||data == null)
        	           return Constants.KEYWORD_FAIL + "--Either Object or data is not passed properly";
        	      
        	       List<WebElement> next_Link_List = null;
        	       for(int i=0;i<temp.length;i++){
        	           element=0;
        	           logger.debug(xpathStart +temp[i]+ xpathEnd);
        	           element = driver.findElements(By.xpath((xpathStart) + temp[i] + (xpathEnd))).size();
        	           counter+=element;
        	           while(element==0)
        	               {
        	               next_Link_List= explictWaitForElementList("pagination_next_link");
        	           
        	               if (next_Link_List.size() > 0) {
        	               next_Link_List.get(0).click();
        	               }
        	               element = driver.findElements(By.xpath((xpathStart) + temp[i] + (xpathEnd))).size();
        	            
        	               if(element!=0)
        	               {
        	                  
        	                   counter+=element;
        	                   driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
        	                   break;
        	               }
        	              
        	               if (next_Link_List.size() == 0)
        	               {
        	               driver.findElement(By.xpath(OR.getProperty("pagination_first_lnk"))).click();
        	                   break;
        	                  
        	               }
        	               }
        	          
        	          }
        	      
        	       if(counter!=temp.length)
        	       {
        	          
        	           flag=false;
        	       }
        	      
        	      
        	       if(flag){
        	           if(counter==1)
        	               return Constants.KEYWORD_PASS + " -- Single webElement present";
        	           else
        	               return Constants.KEYWORD_PASS+"--"+ counter + "--webElements present";
        	       }
        	       else
        	           return Constants.KEYWORD_FAIL + " -- No webElements is present";
        	   }
        	   catch (TimeoutException ex) {
        	       return Constants.KEYWORD_FAIL + "Cause: " + ex.getCause();
        	   } catch (Exception e) {

        	       return Constants.KEYWORD_FAIL + " Object not found "+ e.getMessage();
        	   }
        	}

        	 
        	/**
        	 * Added By Sudhir Kumar Naphray
        	 * Date: 1st August 2015
        	 * This method enters message(using sendKeys) in multiple FCKeditor box and then switches back to default frame.
        	 * @param object : common xpath path of FCKeditor box.
        	 * @param data : data to be entered in multiple FCKeditor box using Data_SPLIT i.e.(|)
        	 * */
        	public String enterMessageInFCKeditorForMultiple(String object, String data) throws InterruptedException
        	{
        		
        		
        				
        		try {
        			logger.debug("entered into enterMessageInFCKeditor()");
        			List<WebElement> all_iframe = explictWaitForElementList(object);
        		    int iframesize=explictWaitForElementSize(object);
        			logger.debug("No of iframes are " +iframesize);
        			String temp[]=data.split(Constants.DATA_SPLIT);
        			int datasize=temp.length;
        			for(int i=0;i<datasize;i++)
        			{
        			WebElement iframe= all_iframe.get(i);
        			logger.debug("iframe" + iframe);
        			driver.switchTo().frame(iframe);
        			keyUtil.pause(object, data);
        			WebElement editable = driver.switchTo().activeElement();
        			editable.clear();
        			editable.sendKeys(temp[i]);
        			keyUtil.moveToDefaultFrame(object, data);
        			logger.debug("In default frame");
        			}

        		}
        		catch(TimeoutException ex)
        		{
        			return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
        		} 
        		catch (Exception e) 
        		{
        			return Constants.KEYWORD_FAIL + " - Could not enter the message " + e.getMessage();
        		}
        		return Constants.KEYWORD_PASS+ " --";
        	}

        	/**
        	 * Added By Pooja This method is used to count all rows. For Ex : All
        	 * roles in Admin Tab > Users >Change Password. It takes the common xpath of
        	 * the all rows.
        	 * */
        	public String verifyAllRowsWithoutPagination(String object, String data) throws Exception {
        		logger.debug("Entered into verifyAllRowsWithPagination()");
        		int verify_user_count = 0;
        		
        		try {

        			verify_user_count= driver.findElements(By.xpath(OR.getProperty(object))).size();
        						
        			if(allrowsCount.equals(String.valueOf(verify_user_count))){
        				return Constants.KEYWORD_PASS + " -- Rows count Matched."
        						+ " Expected Count=" + verify_user_count+"--Actual Count="+allrowsCount;
        			}
        			else{
        				return Constants.KEYWORD_FAIL + " -- Rows count does not Match."+ " Expected Count=" + verify_user_count+"--Actual Count="+allrowsCount;
        			}
        		} catch (Exception e) {
        			return Constants.KEYWORD_FAIL + e.getLocalizedMessage();
        		}
        	}

        	/**Created BY Pooja on 07/08/2015
    		 * select a list from a dropdown
    		 * 
    		 * @param object:Split xpath using ","
    		 * @param data
    		 * @return
    		 */
    		public String selectListUsingSplit(final String object, String data) {
    			logger.debug("Selecting from list");
    			try {
    				String values[]=object.split(Constants.Object_SPLIT);
    				String data_values[]=data.split(Constants.DATA_SPLIT);
    				WebElement select = wait.until(explicitWaitForElement((By.xpath(OR.getProperty(values[0])+data_values[0]+OR.getProperty(values[1])))));

    				List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG").trim()));
    				String expected = data_values[1].trim();
    				logger.debug("Expected is > " + expected);
    				for (WebElement option : options) {

    					String actual = option.getText().trim();
    					logger.debug("Actual > " + actual);
    					if (actual.equals(expected)) {
    						logger.debug("Selected..." + data);
    						option.click();
    						return Constants.KEYWORD_PASS + "--" + data + " - selected";
    					}
    				}
    			}
    			catch(TimeoutException ex)
    			{
    				return Constants.KEYWORD_FAIL +"Cause: "+ ex.getCause();
    			}
    			catch (Exception e) {
    				
    				return Constants.KEYWORD_FAIL + " - Could not select from list. " + e.getMessage();
    			}
    			return Constants.KEYWORD_FAIL + " - Could not select from list. ";
    		}


    		/* Created By Naincy Saini 19/08/2015
    		 * Verifies sort of all drop-down elements except First.
    		 * @param object:xpath of the drop down.
    		 * @param data:not required
    		 * @return
    		 */
    		
    		
    		public String verifyDropDownValuesSortedExceptFirst(String object, String data) {
    			try {

    				WebElement select = wait.until(explicitWaitForElement(By.xpath(OR.getProperty(object))));
    				select.click();
    				boolean flag = false;
    				List<WebElement> options = select.findElements(By.tagName(OR.getProperty("OPTION_TAG")));
    				List<String> actual_list = new ArrayList<String>();
    				List<String> sortedlist = new ArrayList<String>();
    				for (int i = 1; i < options.size(); i++) {
    					String name = options.get(i).getText();
    					actual_list.add(name);
    					sortedlist.add(name);
    				}
    				Collections.sort(sortedlist, String.CASE_INSENSITIVE_ORDER);
    				for (int i = 1; i < sortedlist.size(); i++) {
    					if (sortedlist.get(i).equals(actual_list.get(i))) {
    						flag = true;
    					} else {
    						flag = false;
    						break;
    					}
    				}
    				if (flag)
    					return Constants.KEYWORD_PASS + "--elements in drop down are in sorted order";
    				else
    					return Constants.KEYWORD_FAIL + "--elements are not in sorted order";
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

        	
        	


