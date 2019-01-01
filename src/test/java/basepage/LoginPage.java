package basepage;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver;

	

	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}

	//change in Xpath for Fail result
	By user =By.xpath("//input[@name='txtUserName']");
	By pass =By.id("pass");
	By loginbtn =By.id("btnLogin");
	By label =By.id("lblError");

	public void loginApplication(String username) throws InterruptedException {
		driver.findElement(user).sendKeys(username);
		Thread.sleep(2000);
		driver.findElement(loginbtn).click();
		Thread.sleep(1000);
		
		
	}
	public String fetchvalue() {
		
		WebElement text=driver.findElement(label);
		return text.getText();
	}
	
	
}
