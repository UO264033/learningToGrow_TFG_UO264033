package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PoRegisterView extends PoNavView {

	static public void fillForm(WebDriver driver, String usernamep, String namep, String lastnamep, String emailp, String passwordp, String passwordconfp) {
		WebElement username = driver.findElement(By.name("username"));
		username.click();
		username.clear();
		username.sendKeys(usernamep);
		WebElement name = driver.findElement(By.name("name"));
		name.click();
		name.clear();
		name.sendKeys(namep);
		WebElement lastname = driver.findElement(By.name("lastName"));
		lastname.click();
		lastname.clear();
		lastname.sendKeys(lastnamep);
		WebElement email = driver.findElement(By.name("email"));
		email.click();
		email.clear();
		email.sendKeys(emailp);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		WebElement passwordConfirm = driver.findElement(By.name("passwordConfirm"));
		passwordConfirm.click();
		passwordConfirm.clear();
		passwordConfirm.sendKeys(passwordconfp);
		//Pulsar el boton de Alta.
		By enlace = By.id("registro");
		driver.findElement(enlace).click();
	}

}
