package com.uniovi.tests.integration.util.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.integration.util.SeleniumUtils;

public class Poview {
	
	protected static PoProperties p = new PoProperties("messages");
	protected static int timeout = 2;

	public static int getTimeout() {
		return timeout;
	}

	public static void setTimeout(int timeout) {
		Poview.timeout = timeout;
	}

	public static PoProperties getP() {
		return p;
	}

	public static void setP(PoProperties p) {
		Poview.p = p;
	}
	
	/**
	 * Espera por la visibilidad de un texto correspondiente a la propiedad key en el idioma locale en la vista actualmente cargandose en driver..
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param key: clave del archivo de propiedades.
	 * @param locale: Retorna el índice correspondient al idioma. 0 p.SPANISH y 1 p.ENGLISH.
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkKey(WebDriver driver, String key, int locale) {
		List<WebElement> elementos = SeleniumUtils.esperaCargaPagina(driver, "text", p.getString(key, locale), getTimeout());
		return elementos;
	}
	/**
	 *  Espera por la visibilidad de un elemento/s en la vista actualmente cargandose en driver..
	 * 
	 * @param driver: apuntando al navegador abierto actualmente.
	 * @param type: 
	 * @param text:
	 * @return Se retornará la lista de elementos resultantes de la búsqueda.
	 */
	static public List<WebElement> checkElement(WebDriver driver, String type, String text) {
		List<WebElement> elementos = SeleniumUtils.esperaCargaPagina(driver, type, text, getTimeout());
		return elementos;		
	}
}
