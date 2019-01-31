package com.qa.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoSiteAdd {
	@FindBy(name="username")
	private WebElement usernameInput;
	
	@FindBy(name="password")
	private WebElement passwordInput;
	
	@FindBy(name="FormsButton2")
	private WebElement submitButton;
	
	public void addUser(String username, String password) {
		usernameInput.sendKeys(username);		
		passwordInput.sendKeys(password);
		submitButton.click();	
	}
}
