package com.tk20.seleniumuiflipassessment.base;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.tk20.seleniumuiflipassessment.base.Constants;
import com.tk20.seleniumuiflipassessment.base.DataProvider;

public  class sendMail {
	
	static String userName=null;
	static String passWord=null;
	static String emailTo=null;
	static String emailToCC=null;
	static JTextField tfUsername=null;
	static JPasswordField pfPassword=null;
	static JTextField tfEmailTo=null;
	static JTextField tfEmailToCC=null;
	static JFrame frame= new JFrame();
	static JPanel panel=null;
	static String starttls=null; 
	static String host=null;
    static String port = null;
	static  Properties prop=new Properties();
	static String socketFactoryClass = null;
	static String fallback =null;
	static String path=null;
	static String moduleName=null;
	static int indexOfComma=0;
	static String userFullName=null;
	static String EMAIL_REGEX = "[a-z0-9\\_\\-\\.]+@[a-z0-9\\_\\-\\.]+\\.[a-z]+";
	
	
	public  static void mail(String zipFileName) throws Exception
	{

	    path=zipFileName;
		panel=new JPanel();
		
		
		String mailPropertiesFile=System.getProperty("user.dir")+"/externalFiles/config/mail.properties";
		prop.load(new FileInputStream(mailPropertiesFile));
		final String subject = prop.getProperty("subject");
		host = prop.getProperty("host");
		port = prop.getProperty("port");
		starttls = prop.getProperty("starttls");
		socketFactoryClass = prop.getProperty("socketFactoryClass");
		fallback = prop.getProperty("fallback");
		
		
		   
		   GridBagConstraints cs = new GridBagConstraints();
		   cs.fill = GridBagConstraints.HORIZONTAL;
		  
		   JLabel lbUsername = new JLabel("Username:* ");
	       cs.gridx = 0;
	       cs.gridy = 0;
	       cs.gridwidth = 1;
	       panel.add(lbUsername, cs);

	        tfUsername = new JTextField(20);
	       cs.gridx = 1;
	       cs.gridy = 0;
	       cs.gridwidth = 2;
	       panel.add(tfUsername, cs);

	       JLabel lbPassword = new JLabel("Password:* ");
	       cs.gridx = 0;
	       cs.gridy = 1;
	       cs.gridwidth = 1;
	       panel.add(lbPassword, cs);

	       pfPassword = new JPasswordField(20);
	       cs.gridx = 1;
	       cs.gridy = 1;
	       cs.gridwidth = 2;
	       panel.add(pfPassword, cs);
	       
	       
	       JLabel lbEmailTo = new JLabel("emailTo:*     ");
	       cs.gridx = 0;
	       cs.gridy = 1;
	       cs.gridwidth = 1;
	       panel.add(lbEmailTo, cs);

	       tfEmailTo = new JTextField(20);
	       cs.gridx = 1;
	       cs.gridy = 1;
	       cs.gridwidth = 2;
	       panel.add(tfEmailTo, cs);
	       
	       JLabel lbEmailToCC = new JLabel("emailToCC: ");
	       cs.gridx = 0;
	       cs.gridy = 1;
	       cs.gridwidth = 1;
	       panel.add(lbEmailToCC, cs);

	       tfEmailToCC = new JTextField(20);
	       cs.gridx = 1;
	       cs.gridy = 1;
	       cs.gridwidth = 2;
	       panel.add(tfEmailToCC, cs);
	       
	       JButton submitBtn= new JButton("Submit");
	       panel.add(submitBtn);
		   
		   frame.add(panel);
		   frame.setTitle("Email Sender");
		   frame.setVisible(true);
		   frame.setSize(350, 230);
		   frame.setLocationRelativeTo(null); 

		   submitBtn.addActionListener(new ActionListener() {
		
	           public void actionPerformed(ActionEvent e)
	           {
	        	   boolean value=getDetails();
	                
	           if(value)
	           {
	        	frame.setVisible(false);   
	        	
	        	if(tfEmailTo.getText().matches(EMAIL_REGEX))
	        {
	        	
	            Properties props = System.getProperties();
	            props.put("mail.smtp.user", userName);
	            props.put("mail.smtp.host", host);
	            props.put("mail.smtp.auth", "true");
	          
	            
	          if(!"".equals(port))
	            {
	                props.put("mail.smtp.port", port);
	                props.put("mail.smtp.socketFactory.port", port);
	            }

	            if(!"".equals(starttls))
	               props.put("mail.smtp.starttls.enable",starttls);

	            if(!"".equals(socketFactoryClass))
	                props.put("mail.smtp.socketFactory.class",socketFactoryClass);

	            if(!"".equals(fallback))
	                props.put("mail.smtp.socketFactory.fallback", fallback);

	        
	            Session session = Session.getDefaultInstance(props,null);
	            session.setDebug(false);
            
	            
	            try{
               
	            MimeMessage msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(userName,prop.getProperty("userFullName"))); 
	            msg.setSubject(subject);                         
	           
	            
	            if(!"".equals(emailToCC))
	            {
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));  	
	            msg.addRecipient(Message.RecipientType.CC, new InternetAddress(emailToCC));
	            }
	            
	            else if(emailToCC==null || emailToCC.equals(""))
	            {
	            	 msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
	            }
	            	
	            
	            BodyPart messageBodyPart = new MimeBodyPart();
	             
	            //This part of code is used to get the module names from dataprovider
	            moduleName=DataProvider.folderInitials.toString();
	           if(moduleName.contains(","))
	           {
	            indexOfComma=moduleName.lastIndexOf(",");
	            moduleName = new StringBuilder(moduleName).replace(indexOfComma, indexOfComma+1, " & ").toString();
	           }
	          
	            messageBodyPart.setText("Please find attached report for the following sheet(s):-"+ moduleName+" "+Constants.NEW_LINE+Constants.NEW_LINE+path);
	            Multipart multipart = new MimeMultipart();
	            multipart.addBodyPart(messageBodyPart);
	            messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent("<h4>Thanks & Regards,</h4><i>Software Engineer (Automation Group)</i>",
                "text/html");        
	            multipart.addBodyPart(messageBodyPart);
	            
	            messageBodyPart = new MimeBodyPart();
	            DataSource source = new FileDataSource(path);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(source.getName());
	            multipart.addBodyPart(messageBodyPart);
	            msg.setContent(multipart);
	            msg.saveChanges();  
	              
	            Transport transport = session.getTransport("smtp");     
	            transport.connect(host, userName, passWord);
	            transport.sendMessage(msg, msg.getAllRecipients());
	            transport.close();
	            JFrame mailSentFrame = new JFrame();
	            JOptionPane.showMessageDialog(mailSentFrame,"Mail Sent Successfully");
	          
	            
	            
	            
	            
	            } catch(AuthenticationFailedException e1)
	            {
	            	 JFrame wrongCredentials = new JFrame();
	            	JOptionPane.showMessageDialog(wrongCredentials,"Wrong Username or Password");
	            	frame.setVisible(true);  
	            	
	            }catch (Exception ex)
	        {
	            ex.printStackTrace();
	            System.out.println("no mail sent");
	            
	        }
	           } 
	           
	           else
	           {
	        	  JFrame invalidUsername = new JFrame();
	            	JOptionPane.showMessageDialog(invalidUsername,"emailTo field contains invalid characters");
	            	frame.setVisible(true);  
	           }
	           
	           }}
	           
	           
		   });
	}
      

	public static boolean getDetails() {
		userName=tfUsername.getText();
		passWord=pfPassword.getText();
		emailTo=tfEmailTo.getText();
		
		boolean flag=true;
		
		
		if(tfEmailTo.getText().equals("") || tfUsername.getText().equals("") || pfPassword.getText().equals("")) 
   		{
   			JOptionPane.showMessageDialog(panel, "fields can't be left Blank", "Warning",
   			        JOptionPane.WARNING_MESSAGE);
   			flag=false;
   			
   		}
		
       emailToCC=tfEmailToCC.getText();
       return flag;
		
	}
    }