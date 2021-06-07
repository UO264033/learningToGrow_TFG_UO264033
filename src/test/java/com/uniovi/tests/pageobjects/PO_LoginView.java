package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_LoginView {

	static public void fillForm(WebDriver driver, String usernamef, String passwordf) {
		WebElement username = driver.findElement(By.name("username"));
		username.click();
		username.clear();
		username.sendKeys(usernamef);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordf);

		// Pulsar el boton de iniciar sesion.
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	/**
	 * Refactorizado todo el paso del login
	 * 
	 * @param driver
	 * @param username
	 * @param contraseña
	 * @param text
	 */

	public static void login(WebDriver driver, String username, String contraseña, String text) {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "text", "¡Hola! Conéctate");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, username, contraseña);
		SeleniumUtils.esperarSegundos(driver, 2);
		// Comprobamos que entramos en la pagina del usuario
		PO_View.checkElement(driver, "text", text);
	}
}
