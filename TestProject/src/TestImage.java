
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestImage {

public static void main(String args[])throws NoSuchElementException
{
   
	System.setProperty(	"webdriver.chrome.driver","C:/Users/TK/workspace/seleniumuiflipassessment/externalFiles/drivers/chromedriver.exe");

   WebDriver driver= new ChromeDriver();
	//driver.get("http://docs.seleniumhq.org/");
   driver.get("https://www.google.co.in");
	WebElement ImageFile = driver.findElement(By.xpath("//img[@src='/images/big-logo.png']"));
        
	
        Boolean ImagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", ImageFile);
        if (!ImagePresent)
        {
             System.out.println("Image not displayed.");
        }
        else
        {
            System.out.println("Image displayed.");
        }
	}
	
}