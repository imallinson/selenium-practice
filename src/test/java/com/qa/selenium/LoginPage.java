package com.qa.selenium;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="login")
	private WebElement login;
	
	public void enterDetails(String usernameInput, String passwordInput) throws InterruptedException {
		username.sendKeys(usernameInput);
		password.sendKeys(passwordInput);
		login.click();
	}
}
