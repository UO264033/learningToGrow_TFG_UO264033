package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.uniovi.tests.util.SeleniumUtils;

public class PoHomeView extends PoNavView {

	static public void checkWelcome(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.esperaCargaPagina(driver, "text", p.getString("welcome.message", language), getTimeout());
		SeleniumUtils.esperaCargaPagina(driver, "text", p.getString("welcome.submessage", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PoHomeView.checkWelcome(driver, locale1);
		// Cambiamos a segundo idioma
		PoHomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PoHomeView.checkWelcome(driver, locale2);
		// Volvemos a Español.
		PoHomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PoHomeView.checkWelcome(driver, locale1);
	}
	
	static public void clickDetails(WebDriver driver, String xpath, String text1, String text2) {
		// Contamos las notas
		By enlace = By.xpath(xpath);
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Esperamos por la ventana de detalle
		PoView.checkElement(driver, "text", text1);
		PoView.checkElement(driver, "text", text2);
		driver.navigate().to("http://localhost:8090/home");
	}

}
