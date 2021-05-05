package com.uniovi;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningToGrowApplicationTests {

//	static WebDriver driver = new FirefoxDriver();
//	static String URL = "http://localhost:8090";
//	Selenium s = new WebDriverBackedSelenium(driver, URL);

	@Before
	public void setUp() throws Exception {
//		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
//		driver.manage().deleteAllCookies();
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() { // Cerramos el navegador al finalizar las pruebas
//		driver.quit();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
