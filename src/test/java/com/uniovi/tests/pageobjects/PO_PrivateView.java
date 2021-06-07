package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {

	static public void fillFormAddTestExercise(WebDriver driver, int userOrder, String namep, String descriptionp) {
		// Esperamos 3 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 3);
		// Seleccionamos el alumnos userOrder
//		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement name = driver.findElement(By.name("name"));
		name.clear();
		name.sendKeys(namep);
		WebElement description = driver.findElement(By.name("description"));
		description.click();
		description.clear();
		description.sendKeys(descriptionp);
		By boton = By.className("btn");
		driver.findElement(boton).click();

	}

	public static void fillFormAddQuestionTest(WebDriver driver, String statementp, String texto1, String texto2, String text3, String xpath) {
		// Esperamos 3 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 3);
		// Rellenemos el campo de descripción
		WebElement statement = driver.findElement(By.name("statement"));
		statement.clear();
		statement.sendKeys(statementp);
		WebElement answer1 = driver.findElement(By.name("texto1"));
		answer1.click();
		answer1.clear();
		answer1.sendKeys(texto1);
		WebElement answer2 = driver.findElement(By.name("texto2"));
		answer2.click();
		answer2.clear();
		answer2.sendKeys(texto2);
		WebElement answer3 = driver.findElement(By.name("texto3"));
		answer3.click();
		answer3.clear();
		answer3.sendKeys(text3);
		By enlace = By.xpath(xpath);
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		By boton = By.id("addA");
		driver.findElement(boton).click();
	}
}
