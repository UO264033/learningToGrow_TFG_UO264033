package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PoPrivateView extends PoNavView {

	static public void fillFormAddExercise(WebDriver driver, String namep, String descriptionp) {
		// Esperamos 3 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
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

	public static void fillFormAddQuestionTest(WebDriver driver, String statementp, String texto1, String texto2,
			String text3, String xpath) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
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
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		By boton = By.id("addA");
		driver.findElement(boton).click();
	}

	public static void fillFormAddQuestionShortAnswer(WebDriver driver, String statementp, String texto) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		// Rellenemos el campo de descripción
		WebElement statement = driver.findElement(By.name("statement"));
		statement.clear();
		statement.sendKeys(statementp);
		WebElement answer1 = driver.findElement(By.name("text"));
		answer1.click();
		answer1.clear();
		answer1.sendKeys(texto);
		By boton = By.id("addA");
		driver.findElement(boton).click();

	}

	public static void fillFormAddSubject(WebDriver driver, String namep) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		WebElement name = driver.findElement(By.name("name"));
		name.clear();
		name.sendKeys(namep);
		By boton = By.id("studentsButton");
		driver.findElement(boton).click();

		By check = By.xpath("//*[@id=\"4\"]");
		driver.findElement(check).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		check = By.xpath("//*[@id=\"5\"]");
		driver.findElement(check).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		check = By.xpath("//*[@id=\"6\"]");
		driver.findElement(check).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		check = By.xpath("//*[@id=\"7\"]");
		driver.findElement(check).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		check = By.xpath("//*[@id=\"8\"]");
		driver.findElement(check).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());

		boton = By.id("addStudent");
		driver.findElement(boton).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
	}

	public static void fillFormAddOrEditUser(WebDriver driver, String usernamep, String namep, String lastNamep,
			String emailp, String passwordp, String passwordConfirmp) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		// Rellenemos el campo de descripción
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys(usernamep);
		WebElement name = driver.findElement(By.name("name"));
		name.click();
		name.clear();
		name.sendKeys(namep);
		WebElement lastName = driver.findElement(By.name("lastName"));
		lastName.click();
		lastName.clear();
		lastName.sendKeys(lastNamep);
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
		passwordConfirm.sendKeys(passwordConfirmp);
		By enlace = By.id("addU");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());

	}

	public static void fillFormAddHomeworkShortAnswer(WebDriver driver, String answerp, String descriptionp) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		// Rellenemos el campo de descripción
		WebElement answer = driver.findElement(By.xpath("/html/body/div/form/div[1]/div/div[2]/input"));
		answer.clear();
		answer.sendKeys(answerp);
		WebElement description = driver.findElement(By.name("description"));
		description.click();
		description.clear();
		description.sendKeys(descriptionp);
		By enlace = By.id("send");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());

	}
	
	public static void fillFormAddHomeworkTest(WebDriver driver, String descriptionp) {
		// Esperamos 2 segundos a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		// Rellenemos el campo de descripción
		By answer = By.xpath("//*[@id=\"huey\"]");
		driver.findElement(answer).click();
		WebElement description = driver.findElement(By.name("description"));
		description.click();
		description.clear();
		description.sendKeys(descriptionp);
		By enlace = By.id("send");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());

	}
	
	public static void fillFormCorrectTest(WebDriver driver, String retrop) {
		By nota = By.xpath("//*[@id=\"role\"]/option[6]");
		driver.findElement(nota).click();
		
		WebElement retro = driver.findElement(By.name("comment"));
		retro.click();
		retro.clear();
		retro.sendKeys(retrop);
		By enlace = By.id("correct");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
	}

	public static void fillFormAnswerFeedback(WebDriver driver, String answerp) {
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
		WebElement answer = driver.findElement(By.name("message"));
		answer.click();
		answer.clear();
		answer.sendKeys(answerp);
		
		By enviar = By.xpath("//*[@id=\"enviar\"]");
		driver.findElement(enviar).click();
		SeleniumUtils.esperarSegundos(driver, PoView.getTimeout());
	}

}
