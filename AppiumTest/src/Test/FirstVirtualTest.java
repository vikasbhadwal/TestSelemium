package Test;
import java.net.URL; 
import java.util.concurrent.TimeUnit; 
import org.openqa.selenium.By; import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test; 

public class FirstVirtualTest {
	WebDriver driver;
	@Test public void setUp() throws Exception {
		// Created object of DesiredCapabilities class. 
		DesiredCapabilities capabilities = new DesiredCapabilities(); 
		// Set android deviceName desired capability. Set it Android Emulator.
		capabilities.setCapability("deviceName", "Android Emulator"); 
		// Set browserName desired capability. It's Android in our case here.
capabilities.setCapability("browserName", "Android"); 
// Set android platformVersion desired capability. Set your emulator's android version. 
capabilities.setCapability("platformVersion", "5.1"); 
// Set android platformName desired capability. It's Android in our case here. 
capabilities.setCapability("platformName", "Android"); 
// Set android appPackage desired capability. It is com.android.calculator2 for calculator application. 
// Set your application's appPackage if you are using any other app. 
capabilities.setCapability("appPackage", "com.android.calculator2"); 
// Set android appActivity desired capability. It is com.android.calculator2.Calculator for calculator application. 
// Set your application's appPackage if you are using any other app. 
capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
// Created object of RemoteWebDriver will all set capabilities.
// Set appium server address and port number in URL string.
// It will launch calculator app in emulator. 
driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); 
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); 
// Click on CLR button. 
driver.findElement(By.name("del")).click(); 
//OR you can use bellow given syntax to click on CLR/DEL button. 
driver.findElements(By.className("android.view.View")).get(1).findElements(By.className("android.widget.Button")).get(0).click(); 
// Click on number 2 button.
driver.findElement(By.name("2")).click(); 
// Click on + button.
driver.findElement(By.name("+")).click(); 
// Click on number 5 button. driver.findElement(By.name("5")).click(); 
// Click on = button. driver.findElement(By.name("=")).click(); 
// Get result from result text box of calc app. 
String result = driver.findElement(By.className("android.widget.EditText")).getText(); System.out.println("Number sum result is : " + result); driver.quit(); }  

	}
