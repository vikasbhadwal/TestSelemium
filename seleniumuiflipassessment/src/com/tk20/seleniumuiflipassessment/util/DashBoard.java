package com.tk20.seleniumuiflipassessment.util;
import java.awt.*;

import javax.swing.*;

import com.tk20.seleniumuiflipassessment.base.DataProvider;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class DashBoard implements ActionListener {
	
	private JLabel displayTimeLabel,displayTestCase,displaySuite,fail_count,displayTimeLabel_value,suite_Remaining,suite_Remaining_value;
	private JLabel displayTestCase_value,displaySuite_value,fail_count_value,total_loc,total_loc_value,module_name,suite_count,suite_count_value;
	private JLabel current_suite_lines,current_suite_lines_value,displayTimeTotalLabel_value,displayTimeTotalLabel;
    private long watchStart,watchTotalStart; 
	private Timer theChronometer;
	private Timer theChronometerTotal;
	private JButton activateTimerButton;
	private JButton stopTimerButton,activateTimerButtonTotal,stopTimerButtonTotal;
	JFrame frame;
	String currDir = System.getProperty("user.dir");
	String sep = System.getProperty("file.separator");
	Font largeFontPLAIN = new Font("Cambria", Font.PLAIN,16);

 public DashBoard()
 {
 frame = new JFrame();
 
//Added By Surender on 14/10/2015
frame.setTitle("Automation Dashboard");
Container content = frame.getContentPane();
DataProvider.progressBar = new JProgressBar();
DataProvider.progressBar .setValue(0);
DataProvider.progressBar.setStringPainted(true);
DataProvider.progressBar.setForeground(Color.BLUE);
content.add(DataProvider.progressBar);
 

 frame.setTitle("Automation Dashboard");
 frame.setLocation(770,450);
 frame.setLocationRelativeTo(null);
 frame.setAlwaysOnTop(true);
 frame.setLayout(null);
 String path = currDir + sep + "externalFiles" + sep + "uploadFiles" + sep + "Automation.jpg";
 ImageIcon img = new ImageIcon(path);
 frame.setIconImage(img.getImage());
 Font largeFontBOLD = new Font("Cambria", Font.BOLD,15);
 Font module = new Font("Trebuchet MS", Font.BOLD,20);
 
 module_name = new JLabel();
 module_name.setBounds(30, 15, 500, 20);
 module_name.setFont(module);
 frame.add(module_name);
 
 suite_count = new JLabel("Total Suites");
 suite_count_value=new JLabel();
 suite_count.setBounds(30, 40, 180, 20);
 suite_count_value.setBounds(240, 40, 100, 20);
 suite_count_value.setFont(largeFontPLAIN);
 suite_count.setFont(largeFontBOLD);
 frame.add(suite_count);
 frame.add(suite_count_value);
 
 suite_Remaining = new JLabel("Suites Remaining ");
 suite_Remaining_value=new JLabel();
 suite_Remaining.setBounds(30, 60, 180, 20);
 suite_Remaining_value.setBounds(240,60,100, 20);
 suite_Remaining_value.setFont(largeFontPLAIN);
 suite_Remaining.setFont(largeFontBOLD);
 frame.add(suite_Remaining);
 frame.add(suite_Remaining_value);
 
 displaySuite = new JLabel("Executing Suite");
 displaySuite_value=new JLabel();
 displaySuite.setBounds(30, 80, 180, 20);
 displaySuite_value.setBounds(240, 80, 500, 20);
 displaySuite_value.setFont(largeFontPLAIN);
 displaySuite.setFont(largeFontBOLD);
 frame.add(displaySuite);
 frame.add(displaySuite_value);
 
 displayTestCase = new JLabel("Executing Module");
 displayTestCase_value=new JLabel();
 displayTestCase.setBounds(30, 100, 180, 20);
 displayTestCase_value.setBounds(240, 100, 500, 20);
 displayTestCase_value.setFont(largeFontPLAIN);
 displayTestCase.setFont(largeFontBOLD);
 frame.add(displayTestCase);
 frame.add(displayTestCase_value);
 
 
 displayTimeLabel = new JLabel("Time Elapsed Current Suite");
 displayTimeLabel_value=new JLabel();
 displayTimeLabel.setBounds(30, 120, 210, 20);
 displayTimeLabel_value.setBounds(240, 120, 170, 20);
 displayTimeLabel_value.setFont(largeFontPLAIN);
 displayTimeLabel.setFont(largeFontBOLD);
 frame.add(displayTimeLabel);
 frame.add(displayTimeLabel_value);
 
 fail_count = new JLabel("Fail Count in Current Suite");
 fail_count_value=new JLabel();
 fail_count.setBounds(30, 140, 190, 20);
 fail_count_value.setBounds(240, 140, 100, 20);
 fail_count.setFont(largeFontBOLD);
 fail_count_value.setFont(largeFontBOLD);
 frame.add(fail_count);
 frame.add(fail_count_value);
 
 current_suite_lines=new JLabel("Line Remaining Current Suite");
 current_suite_lines_value=new JLabel("Calculating....");
 current_suite_lines.setBounds(30, 160, 220, 20);
 current_suite_lines_value.setBounds(240, 160, 250, 20);
 current_suite_lines_value.setFont(largeFontPLAIN);
 current_suite_lines.setFont(largeFontBOLD);
 frame.add(current_suite_lines);
 frame.add(current_suite_lines_value);
 
 total_loc=new JLabel("Executing Line ");
 total_loc_value=new JLabel("Calculating....");
 total_loc.setBounds(30, 180, 200, 20);
 total_loc_value.setBounds(240, 180, 400, 20);
 total_loc_value.setFont(largeFontPLAIN);
 total_loc.setFont(largeFontBOLD);
 frame.add(total_loc);
 frame.add(total_loc_value);
 
 displayTimeTotalLabel=new JLabel("Total Time Elapsed");
 displayTimeTotalLabel_value=new JLabel();
 displayTimeTotalLabel.setBounds(30, 200, 220, 20);
 displayTimeTotalLabel_value.setBounds(240, 200, 250, 20);
 displayTimeTotalLabel_value.setFont(largeFontPLAIN);
 displayTimeTotalLabel.setFont(largeFontBOLD);
 frame.add(displayTimeTotalLabel);
 frame.add(displayTimeTotalLabel_value);
 
 frame.setSize(600, 300);
 frame.setVisible(true);

 frame.getContentPane().setBackground( new Color( 232,232,232) );
 
 JLabel  progress_bar=new JLabel("Execution Progress                         :");
 progress_bar.setBounds(30, 220, 240, 20);
 DataProvider.progressBar.setBounds(246, 225, 220, 20);
 progress_bar.setFont(largeFontPLAIN);
 progress_bar.setFont(largeFontBOLD);
 frame.add(progress_bar);

 //code to start and stop timer for current suite
 theChronometer =
		     new Timer(1000,new ActionListener(){
		     		public void actionPerformed(ActionEvent e){
		     			int seconds = (int)(System.currentTimeMillis()-watchStart)/1000;
		     			int days = seconds / 86400;
							int hours = (seconds / 3600) - (days * 24);
							int min = (seconds / 60) - (days * 1440) - (hours * 60);
							int sec = seconds % 60;
		     			String s = new String(": "+hours+" h: "+min+ " min: "+sec+" sec");
		     		
		     			displayTimeLabel_value.setText(s);
		     		}
		     });
//code to start and stop timer for all suites
  theChronometerTotal =
	     new Timer(1000,new ActionListener(){
	     		public void actionPerformed(ActionEvent e){
	     			int seconds = (int)(System.currentTimeMillis()-watchTotalStart)/1000;
	     			int days = seconds / 86400;
						int hours = (seconds / 3600) - (days * 24);
						int min = (seconds / 60) - (days * 1440) - (hours * 60);
						int sec = seconds % 60;
	     			String s = new String(": "+hours+" h: "+min+ " min: "+sec+" sec");
	     		
	     			displayTimeTotalLabel_value.setText(s);
	     		}
	     });
 
    activateTimerButton = new JButton("Start");
	stopTimerButton = new JButton("Stop");
	activateTimerButton.addActionListener(this);
	stopTimerButton.addActionListener(this);
	
	activateTimerButtonTotal = new JButton("TotalStart");
	stopTimerButtonTotal = new JButton("TotalStop");
	activateTimerButtonTotal.addActionListener(this);
	stopTimerButtonTotal.addActionListener(this);
	//code to add View Log Link to Dashboard.
	try {
		
		String user_directory=System.getProperty("user.dir");
		String log_location=user_directory + "\\externalFiles\\log\\Application.html";
		String log_actual_location=log_location.replaceAll("\\s","");
		File f = new File(log_actual_location);
		final URI uri=f.toURI();
		class OpenUrlAction implements ActionListener {
		   public void actionPerformed(ActionEvent e) {
		       open(uri);
		     }
		}
	    JButton button = new JButton();
	    button.setText("<HTML><FONT color=\"#000099\"><U>View Log</U></FONT>"+ "</HTML>");
	    button.setBorderPainted(false);
	    button.setOpaque(false);
	    button.setBackground(Color.WHITE);
	    button.setToolTipText("Click to View Application.html");
	    button.setBounds(250, 140, 100, 20);
	    button.setFont(largeFontBOLD);
	    frame.add(button);
	    button.addActionListener(new OpenUrlAction());
	    
	}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			
		}
	

}
 
 private static void open(URI uri) {
	    if (Desktop.isDesktopSupported()) {
	      try {
	        Desktop.getDesktop().browse(uri);
	      } catch (IOException e) { /* TODO: error handling */ }
	    } else { /* TODO: error handling */ }
	  }

 
 
 
 // Method to set Tab name.
  public  void SetModule(String m_name)
 { 
	
	 module_name.setText("Executing "+m_name+ " Module");
	 frame.repaint();
	 
 }
 //Method to set Total Number of Suite with Runmode 'Y'.
 public  void setSuiteCount(int m_count)
 { 
	
	 suite_count_value.setText(": "+m_count+"");
	 frame.repaint();
	 }
 
//Method to set Remaining Number of Suite with Runmode 'Y'.
 public  void setRemaingModuleCount(int rm_count)
 { 
	
	 suite_Remaining_value.setText(": "+rm_count+"");
	 frame.repaint();
	 }
 
//Method to add current Executing line.
 public  void setCurrentLine(String loc,int line_no)
 { 
	
	 total_loc_value.setText(": (" +line_no +") "+loc+"");
	 frame.repaint();	 
 }
 
 
 
//Method to set Remaining Line of code.
public  void setLineOfCodeCurrentSuite(int loc)
{ 
	
	current_suite_lines_value.setText(": "+loc+"");
	 frame.repaint();
	
}
 
//Method to set current Executing Module.
 public  void setTestCaseName(String tcName)
 {
	 displayTestCase_value.setText(": "+tcName );
	 
 }

 
 public  void click()
 {
	 activateTimerButton.doClick();
	 
 }
 
 public  void stop()
 {
	 stopTimerButton.doClick(); 
	 
 }
 
 
 public  void startTotalTime()
 {
	 activateTimerButtonTotal.doClick();
	 
 }
 
 public  void stopTotalTime()
 {
	 stopTimerButtonTotal.doClick(); 
	 
 }
 
 
 
 public  void setName(String Name,int lines)
 {
	 displaySuite_value.setText(": "+Name+ " [" + lines  +"]");
	 frame.requestFocus();
	 frame.setAlwaysOnTop(false);
 }
 
//Method to set Fail count.
 public  void setfail(int a)
 {
	    fail_count_value.setText(": "+a+"");
		frame.setExtendedState(JFrame.NORMAL);
	    frame.toFront();
		frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setLocation(770,450);

                
        if(a==0)
        	fail_count_value.setForeground(new Color(0, 0, 0 ));

        if(a!=0)
        	fail_count_value.setForeground(new Color(255, 0, 0 ));
  
      
	 try{
	 Thread.sleep(3000);
	 
	 } 
	 catch(InterruptedException e)
	 {
		 e.getCause();
	 }
      
}
 
 public void actionPerformed(ActionEvent e){

 if(e.getActionCommand().equals("Stop"))
 
 {
	 theChronometer.stop();

 }
    
 else if(e.getActionCommand().equals("Start") ){
	
		   watchStart = System.currentTimeMillis();
  	       theChronometer.start();
  	  
 }
 		
 
 else if(e.getActionCommand().equals("TotalStart") ){
		
	 watchTotalStart = System.currentTimeMillis();
	   theChronometerTotal.start();
  

}
 
 else if(e.getActionCommand().equals("TotalStop") ){
		
	     frame.setSize(600, 300);
	     frame.setLocation(10, 10);
	     frame.setExtendedState(JFrame.NORMAL);
		  frame.toFront();
	     frame.setVisible(true);
	     watchTotalStart = System.currentTimeMillis();
	     theChronometerTotal.stop();

}
 
 
 }
 
 
}



