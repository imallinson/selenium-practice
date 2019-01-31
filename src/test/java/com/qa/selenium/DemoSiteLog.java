package com.qa.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoSiteLog {
	@FindBy(name="username")
	private WebElement usernameInput;
	
	@FindBy(name="password")
	private WebElement passwordInput;
	
	@FindBy(name="FormsButton2")
	private WebElement submitButton;
	
	@FindBy(xpath="/html/body/table/tbody/tr/td[1]/big/blockquote/blockquote/font/center/b")
	private WebElement success;
	
	public String login(String username, String password) {
		usernameInput.sendKeys(username);		
		passwordInput.sendKeys(password);		
		submitButton.click();
		return success.getText();
	}
}
