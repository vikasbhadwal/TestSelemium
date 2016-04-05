package Test;
//this is to check changes from system to git
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class FirstRealTest
{
	//added by vikas bhadwal

public static void main(String args[]) throws MalformedURLException, InterruptedException{
DesiredCapabilities capabilities=DesiredCapabilities.android();
//capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"CHROME");
//capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
//capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Redmi");
//capabilities.setCapability(MobileCapabilityType.VERSION,"4.4.4");

capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);
capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"DFG");
capabilities.setCapability(MobileCapabilityType.VERSION,"4.1.1");




capabilities.setCapability(MobileCapabilityType.APP_PACKAGE,"com.quora.android");
capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY,"com.quora.android.LauncherActivity");
//capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Appium");

capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Selendroid");

URL url= new URL("http://127.0.0.1:4723/wd/hub");
AppiumDriver driver = new AndroidDriver(url, capabilities);
/*driver.navigate().to("http://www.facebook.com");
System.out.println("Title "+driver.getTitle());
driver.findElement(By.name("email")).sendKeys("kangi1989@gmail.com");
driver.findElement(By.name("pass")).sendKeys("dfds");
driver.findElement(By.id("u_0_5")).click();
Thread.sleep(5000);*/
driver.quit();
}

}