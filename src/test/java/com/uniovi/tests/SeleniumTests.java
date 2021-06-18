package com.uniovi.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@SpringBootTest
//Ordenamos las pRuebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTests {

	protected static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	protected static String Geckdriver024 = "C:\\Users\\maari\\OneDrive\\Documentos\\Universidad\\CUARTO\\TFG\\pRoyecto\\workspace\\learningToGrow\\learningToGrow_TFG_UO264033\\geckodriver024win64.exe";
	protected static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	protected static String URL = "http://localhost:8090";
	protected static PO_Properties p = new PO_Properties("messages");
	
	private int x =1;
	private int y = 1;

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	/**
	 * pR01. Acceder a la página pRincipal
	 */
	@Test
	public void pR01homeViewTest() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		assertTrue(x==y);
	}

	/**
	 * pR02. Opción de navegación. Pinchar en el enlace Registro en la página home
	 */
	@Test
	public void pR02navSignupTest() {
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
		assertTrue(x==y);
	}

	/**
	 * pR03. Opción de navegación. Pinchar en el enlace Identificate en la página
	 * home
	 */
	@Test
	public void pR03navLoginTest() {
		PO_HomeView.clickOption(driver, "login", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR04. Opción de navegación. Cambio de idioma de Español a Ingles y vuelta a
	 * Español
	 */
	@Test
	public void pR04changeLangTest() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		assertTrue(x==y);
	}

	/**
	 * pR05. Registro de usuario con datos válidos. /
	 */
	@Test
	public void pR05signupValidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");

		// Rellenamos el formulario con datos validos
		PO_RegisterView.fillForm(driver, "santiago", "Santi", "López", "santiago@gmail.com", "Pollito88", "Pollito88");
		// CompRobamos que entramos en la sección pRivada
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido,");
		SeleniumUtils.textoPresentePagina(driver, "santiago");
		SeleniumUtils.textoPresentePagina(driver, "autenticado como");
		assertTrue(x==y);
	}

	/**
	 * pR06. pRueba del formulario de registro. Username repetido en la BD Username
	 * corto Name corto Lastname corto Email no valido Contraseña no valida
	 */
	@Test
	public void pR06signupInvalidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "alumno", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// CompRobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "jose", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// CompRobamos el error de username corto .
		PO_RegisterView.checkKey(driver, "Error.signup.username.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "J", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// CompRobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "P", "jose@gmail.com", "Pollito88", "Pollito88");
		// CompRobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose.com", "Pollito88", "Pollito88");
		// CompRobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose@gmail.com", "pollito", "pollito");
		// CompRobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		assertTrue(x==y);
	}

	/**
	 * ----------------------identificación por roles-------------------
	 **/

	/**
	 * pR07a. Loguearse con exito desde el Rol de estudiante
	 */
	@Test
	public void pR07arolStudentTest() {
		PO_LoginView.login(driver, "alumno", "123456", "student");
		assertTrue(x==y);
	}

	/**
	 * pR07b. Loguearse con exito desde el Rol de pRofesor
	 */
	@Test
	public void pR07brolprofessorTest() {
		PO_LoginView.login(driver, "pRofesor", "123456", "professor");
		assertTrue(x==y);
	}

	/**
	 * pR07c. Loguearse con exito desde el Rol de administrador
	 */
	@Test
	public void pR07crolAdministradorTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");
		assertTrue(x==y);
	}

	/** ----------------------------Login-------------------------- **/
	/**
	 * pR08. Identificación inválida de usuario Contraseña invalida Contraseña vacia
	 * Username vacio campos vacios
	 */
	@Test
	public void pR08loginInvalidTest() {
		PO_LoginView.login(driver, "mariagg", "123456", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Usuario y/o contraseña incorrectos.");
		PO_LoginView.login(driver, "mariagg", "", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "", "Admin33", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "pepitogrillo", "Admin33", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Usuario y/o contraseña incorrectos.");
		PO_LoginView.login(driver, "", "", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR09. Identificación válida de usuario y cierre de sesión
	 */
	@Test
	public void pR09loginAndLogoutTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");

		PO_HomeView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Sesión cerrada con éxito.");
		assertTrue(x==y);
	}

	/**
	 * pR10. CompRobar que el botón cerrar sesión no está visible si el usuario no
	 * está autenticado.
	 */
	@Test
	public void pR10signoutDisabledTest() {
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");
		assertTrue(x==y);

	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como pRofesor--------------------------
	 * -------------------------------------------------------------------------
	 **/

	/**
	 * pR11. Loguearse como pRofesor, compRobar que se visualizan 3 filas de
	 * ejercicios y desconectarse
	 */
	@Test
	public void pR11listExercisesHomeTest() {
		pR07brolprofessorTest();
		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E2");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR12. Loguearse como pRofesor y ver los detalles de los ejercicios
	 */
	@Test
	public void pR12viewDetailsTest() {

		pR07brolprofessorTest();

		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[2]/td[3]/a", "E1",
				"Ejercicio de colores");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[3]/td[3]/a", "E2",
				"Subidme la ficha que hicimos en clase");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[1]/td[3]/a", "E3",
				"Responder a las siguientes cuestiones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * ----------------------------Crear los tres ejercicios------------------
	 **/

	/**
	 * P13a. Loguearse como pRofesor y agregar un ejercicio de tipo test
	 */
	@Test
	public void pR13aaddTestExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/test/add')]", "Agregar ejercicio de tipo test");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test",
				"Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de tipo test");

		// Ahora vamos a rellenar las pReguntas
		PO_PrivateView.fillFormAddQuestionTest(driver, "P1", "A1", "A2", "A3",
				"/html/body/div/form/div[2]/div/div[2]/span");

		SeleniumUtils.textoPresentePagina(driver, "La pRegunta se ha añadido correctamente");

		By show = By.xpath("/html/body/div/form/div[4]/div/a[1]");
		driver.findElement(show).click();
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de tipo test");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de tipo test");
		SeleniumUtils.textoPresentePagina(driver, "P1");
		SeleniumUtils.textoPresentePagina(driver, "A1");
		SeleniumUtils.textoPresentePagina(driver, "A2");
		SeleniumUtils.textoPresentePagina(driver, "A3");
		By finish = By.xpath("//*[@id=\"finish\"]");
		driver.findElement(finish).click();

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios creados");
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de tipo test");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * P13a2. Loguearse como pRofesor y agregar un ejercicio de tipo test inválido
	 */
	@Test
	public void pR13a2addTestExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/test/add')]", "Agregar ejercicio de tipo test");

		// Ahora vamos a rellenar el ejercicio: nombre repetido
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test",
				"Dos.- Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Introduzca otro nombre de ejercicio, el introducido ya existe");
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pRegunta");

		// Ahora vamos a rellenar el ejercicio: nombre vacio
		PO_PrivateView.fillFormAddExercise(driver, "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pRegunta");

		// Ahora vamos a rellenar el ejercicio.
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test 2",
				"Dos.- Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de tipo test");

		// Ahora vamos a rellenar las pReguntas: pRegunta y respuestas vacias
		PO_PrivateView.fillFormAddQuestionTest(driver, "", "", "", "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "La pRegunta se ha añadido correctamente");
		SeleniumUtils.textoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de tipo test");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * P13b. Loguearse como pRofesor y agregar un ejercicio de subida de fichero
	 */
	@Test
	public void pR13baddUploadFileExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/upFile/add')]", "Agregar ejercicio de subida de fichero");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de subida de fichero",
				"Este es un ejemplo de ejercicio de subida de fichero");

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios creados");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de subida de fichero");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * P13b. Loguearse como pRofesor y agregar un ejercicio de subida de fichero
	 * invalido
	 */
	@Test
	public void pR13b2addUploadFileExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/upFile/add')]", "Agregar ejercicio de subida de fichero");

		// Ahora vamos a rellenar el ejercicio. nombre y descripcion vacia
		PO_PrivateView.fillFormAddExercise(driver, "", "");

		SeleniumUtils.textoPresentePagina(driver, "Agregar ejercicio de subida de fichero");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de subida de fichero",
				"Este es un ejemplo de ejercicio de subida de fichero");

		SeleniumUtils.textoPresentePagina(driver, "Introduzca otro nombre de ejercicio, el introducido ya existe");
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pRegunta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * P13c. Loguearse como pRofesor y agregar un ejercicio de completar con texto
	 */
	@Test
	public void pR13caddShortAnswerExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/shortAnswer/add')]", "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta",
				"Este es un ejemplo de ejercicio de respuesta corta");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora vamos a rellenar las pReguntas
		PO_PrivateView.fillFormAddQuestionShortAnswer(driver, "pRegunta", "Respuesta");

		SeleniumUtils.textoPresentePagina(driver, "La pRegunta se ha añadido correctamente");

		By show = By.xpath("/html/body/div/form/div[4]/div/a[1]");
		driver.findElement(show).click();
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de respuesta corta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");
		SeleniumUtils.textoPresentePagina(driver, "pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Respuesta");
		By finish = By.xpath("/html/body/div/div[2]/div/form/a");
		driver.findElement(finish).click();

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios creados");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * P13c2. Loguearse como pRofesor y agregar un ejercicio de completar con texto
	 * invalido
	 */
	@Test
	public void pR13c2addShortAnswerExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/shortAnswer/add')]", "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio. nombre repetido
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta",
				"Este es un ejemplo de ejercicio de respuesta corta");

		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Introduzca otro nombre de ejercicio, el introducido ya existe");

		// Ahora vamos a rellenar el ejercicio. : nombre y descripcion vacia
		PO_PrivateView.fillFormAddExercise(driver, "", "");
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio.
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta 2",
				"Dos.- Este es un ejemplo de ejercicio de respuesta corta");
		// pRegunta y respuesta vacia
		PO_PrivateView.fillFormAddQuestionShortAnswer(driver, "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "La pRegunta se ha añadido correctamente");
		SeleniumUtils.textoPresentePagina(driver, "Agregar pRegunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR14. - Ver listado de ejercicios
	 */
	@Test
	public void pR14listExercisesTest() {
		pR07brolprofessorTest();

		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'exercise/list')]", "Ejercicios creados");

		// Esperamos a que se muestren los enlaces de paginacion la lista de peticiones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Contamos el número de filas de peticiones
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		By finish = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(finish).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR15.- Eliminar algun ejercicio
	 */
	@Test
	public void pR15deleteExerciseTest() {
		pR07brolprofessorTest();

		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'exercise/list')]", "Ejercicios creados");

		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Responder a las siguientes cuestiones");

		By finish = By.xpath("/html/body/div/div[2]/table/tbody/tr[1]/td[4]/a");
		driver.findElement(finish).click();

		SeleniumUtils.textoNoPresentePagina(driver, "E3");
		SeleniumUtils.textoNoPresentePagina(driver, "Responder a las siguientes cuestiones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);

	}

	/**
	 * pR16. - Ver students
	 */
	@Test
	public void pR16listStudentsTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'user/student/list')]", "Listado de alumnos");

		// Esperamos a que se muestren los enlaces de paginacion la lista de peticiones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "/html/body/div/div[2]", PO_View.getTimeout());
		elementos.clear();

		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoPresentePagina(driver, "Raquel");

		assertTrue(x==y);
	}

	/**
	 * pR17a. - Realizar una busqueda en el listado de usuarios (cadena vacía)
	 */
	@Test
	public void pR17afilteredStudentsEmptyTextTest() {
		pR16listStudentsTest();

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no filtra nada, y se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		
		assertTrue(x==y);
	}

	/**
	 * pR17b. - Realizar una busqueda en el listado de usuarios (cadena que no
	 * existe)
	 */
	@Test
	public void pR17bfilteredStudentsNonExistentTextTest() {
		pR16listStudentsTest();

		// Realizar una busqueda --> cadena que no existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("JulioLopezSegovia");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "No se han encontrado resultados.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR17c. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void pR17cfilteredStudentExistentTextTest() {
		pR16listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("Pedro");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR17d. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void pR17dfilteredStudentExistentTextPartialTest() {
		pR16listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("ía");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR18.- Ver listado de asignaturas
	 */
	@Test
	public void pR18subjectListTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR19.- Crear una nueva asignatura
	 */
	@Test
	public void pR19createSubjectTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'subject/add')]",
				"Alumnos");

		PO_PrivateView.fillFormAddSubject(driver, "Plástica");

		By boton = By.xpath("//html/body/div/form/div[4]/a");
		driver.findElement(boton).click();

		SeleniumUtils.textoPresentePagina(driver, "Listado de asignaturas");
		SeleniumUtils.textoPresentePagina(driver, "Plástica");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR20.- Ver detalles de la asignatura creada
	 */
	@Test
	public void pR20viewDetailsSubjectTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Plástica");

		By boton = By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/a[1]");
		driver.findElement(boton).click();

		SeleniumUtils.textoPresentePagina(driver, "Plástica");
		SeleniumUtils.textoPresentePagina(driver, "Marta");
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR21.- Eliminar una asignatura
	 */
	@Test
	public void pR21deleteSubjectTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		SeleniumUtils.textoPresentePagina(driver, "Plástica");

		By boton = By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/a[2]");
		driver.findElement(boton).click();

		SeleniumUtils.textoNoPresentePagina(driver, "Plástica");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR22.- Ver feedback vacio
	 */
	@Test
	public void pR22viewEmptyFeedbackTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		SeleniumUtils.textoPresentePagina(driver,
				"Aún no hay tareas para corregir. Espere a que los alumnos rellenen sus tareas.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como admin--------------------------
	 * -------------------------------------------------------------------------
	 **/
	/**
	 * pR23.- Listado de usuarios
	 */
	@Test
	public void pR23listUsersTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		// Esperamos a que se muestren los enlaces de paginacion la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Contamos el número de filas de usuarios + 1 (la de buscar)
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 6); // son 5+1
		SeleniumUtils.textoPresentePagina(driver, "pRofesor");
		SeleniumUtils.textoPresentePagina(driver, "Pelayo");
		SeleniumUtils.textoPresentePagina(driver, "mariagg");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "lucasnunez");
		By next = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(next).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 4); // son 3+1
		SeleniumUtils.textoPresentePagina(driver, "mariarodri");
		SeleniumUtils.textoPresentePagina(driver, "garcialucia");
		SeleniumUtils.textoPresentePagina(driver, "raquelsan");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR24.- Búsqueda de usuarios, cadena vacía
	 */
	@Test
	public void pR24filteredUsersEmptyTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		// Esperamos a que se muestren los enlaces de paginacion la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 6); // son 5+1
		SeleniumUtils.textoPresentePagina(driver, "pRofesor");
		SeleniumUtils.textoPresentePagina(driver, "Pelayo");
		SeleniumUtils.textoPresentePagina(driver, "mariagg");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "lucasnunez");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR25.- Búsqueda de usuarios, cadena existente
	 */
	@Test
	public void pR25filteredUsersExistentTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		// Esperamos a que se muestren los enlaces de paginacion la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("lucia");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 2); // son 1+1
		SeleniumUtils.textoPresentePagina(driver, "garcialucia");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR26.- Búsqueda de usuarios, cadena no existente
	 */
	@Test
	public void pR26filteredUsersNonExistentTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		// Esperamos a que se muestren los enlaces de paginacion la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("benitopepito");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// CompRobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 1); // es 0+1
		SeleniumUtils.textoPresentePagina(driver, "No se han encontrado resultados.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR27.-Añadir un nuevo usuario
	 */
	@Test
	public void pR27addNewUserTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/add')]",
				"Añadir nuevo usuario");

		PO_PrivateView.fillFormAddOrEditUser(driver, "alumnoEjemplo", "alumno", "ejemplo", "alumno@gmail.com",
				"Pollito88", "Pollito88");

		SeleniumUtils.textoPresentePagina(driver, "Usuarios");

		By pagination = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(pagination).click();
		SeleniumUtils.textoPresentePagina(driver, "alumnoEjemplo");
		SeleniumUtils.textoPresentePagina(driver, "alumno@gmail.com");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR27.-Añadir un nuevo usuario
	 */
	@Test
	public void pR27baddNewUserInvalidTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/add')]",
				"Añadir nuevo usuario");

		PO_PrivateView.fillFormAddOrEditUser(driver, "alumnoEjemplo", "a", "e", "alumnocom", "pepe", "pepe");

		SeleniumUtils.textoNoPresentePagina(driver, "Usuarios");

		// CompRobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
		// CompRobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// CompRobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// CompRobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// CompRobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR28.- Detalles de usuario
	 */
	@Test
	public void pR28viewDetailsUserTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		By pagination = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(pagination).click();
		By details = By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[6]/a");
		driver.findElement(details).click();
		SeleniumUtils.textoPresentePagina(driver, "Detalles del usuario");
		SeleniumUtils.textoPresentePagina(driver, "Editar usuario");
		SeleniumUtils.textoPresentePagina(driver, "Eliminar usuario");
		SeleniumUtils.textoPresentePagina(driver, "alumnoEjemplo");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "alumno@gmail.com");
		assertTrue(x==y);

	}

	/**
	 * pR29.- Acceder a editar usuario
	 */
	@Test
	public void pR29editUserViewThroughoutDetailsTest() {
		pR28viewDetailsUserTest();

		By details = By.xpath("/html/body/div/div[2]/div/a[1]");
		driver.findElement(details).click();

		SeleniumUtils.textoPresentePagina(driver, "Editar usuario");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR30.- Modificar usuario datos validos
	 */
	@Test
	public void pR30editUserTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		By pagination = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(pagination).click();
		By edit = By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[7]/a");
		driver.findElement(edit).click();

		PO_PrivateView.fillFormAddOrEditUser(driver, "EjemploAlumno", "Alumno", "Ejemplo", "ejemplo@alumno.es",
				"Pepito99", "Pepito99");

		SeleniumUtils.textoPresentePagina(driver, "Usuarios");

		driver.findElement(pagination).click();
		SeleniumUtils.textoPresentePagina(driver, "EjemploAlumno");
		SeleniumUtils.textoPresentePagina(driver, "ejemplo@alumno.es");
		SeleniumUtils.textoNoPresentePagina(driver, "alumnoEjemplo");
		SeleniumUtils.textoNoPresentePagina(driver, "alumno@gmail.com");
		assertTrue(x==y);
	}
	
	/**
	 * pR30.- Modificar usuario datos invalidos
	 */
	@Test
	public void pR30beditUserTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		By pagination = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(pagination).click();
		By edit = By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[7]/a");
		driver.findElement(edit).click();

		PO_PrivateView.fillFormAddOrEditUser(driver, "e", "a", "e", "ejemplo.es",
				"pepe", "pepe");

		PO_RegisterView.checkKey(driver, "Error.signup.username.length", PO_Properties.getSPANISH());
		// CompRobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// CompRobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// CompRobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// CompRobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		assertTrue(x==y);
		
	}

	/**
	 * pR31.-Eliminar el usuario
	 */
	@Test
	public void pR31deleteUserTest() {
		pR07crolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		By pagination = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(pagination).click();
		SeleniumUtils.textoPresentePagina(driver, "alumnoEjemplo");
		SeleniumUtils.textoPresentePagina(driver, "alumno@gmail.com");
		By delete = By.xpath("/html/body/div/div[2]/table/tbody/tr[4]/td[8]/a");
		driver.findElement(delete).click();
		SeleniumUtils.textoNoPresentePagina(driver, "alumnoEjemplo");
		SeleniumUtils.textoNoPresentePagina(driver, "alumno@gmail.com");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR32.- Eliminar usuario
	 */
	@Test
	public void pR32deleteUserThroughoutDetailsTest() {
		pR27addNewUserTest();
		pR28viewDetailsUserTest();

		By delete = By.xpath("/html/body/div/div[2]/div/a[2]");
		driver.findElement(delete).click();

		SeleniumUtils.textoPresentePagina(driver, "Usuarios");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * ---------------------------Identificado como alumno
	 * --------------------------
	 */
	/**
	 * pR33. Loguearse como alumno, compRobar que se visualizan las asignaturas a
	 * las que está asignado
	 */
	@Test
	public void pR33listSubjectsHomeTest() {
		pR07arolStudentTest();
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido,");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "autenticado como");
		SeleniumUtils.textoPresentePagina(driver, "student");
		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR18.- Ver listado de asignaturas desde usuario alumno
	 */
	@Test
	public void pR34subjectListTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		SeleniumUtils.textoPresentePagina(driver, "Ver detalles");
		SeleniumUtils.textoPresentePagina(driver, "Ver ejercicios");
		SeleniumUtils.textoPresentePagina(driver, "Ver correcciones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * Ver página de detalles de la asignatura
	 */
	@Test
	public void pR35viewDetailsSubjectTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");

		By boton = By.xpath("/html/body/div/div[2]/div/div/div[2]/div/a[1]");
		driver.findElement(boton).click();

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		SeleniumUtils.textoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "lucasnunez");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "Ver ejercicios");
		SeleniumUtils.textoPresentePagina(driver, "Ver correcciones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * Acceder a la página de ejercicios a traves de la vista de detalles de
	 * asignatura
	 */
	@Test
	public void pR36viewExercisesThroghoutSubjectDetailsTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");

		By boton = By.xpath("/html/body/div/div[2]/div/div/div[2]/div/a[2]");
		driver.findElement(boton).click();

		SeleniumUtils.textoPresentePagina(driver, "Lista de tareas");
		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * Acceder a la página de feedback a traves de la vista de detalles de
	 * asignatura
	 */
	@Test
	public void pR37_iewFeedbackThroghoutSubjectDetailsTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");

		By boton = By.xpath("/html/body/div/div[2]/div/div/div[2]/div/a[3]");
		driver.findElement(boton).click();

		SeleniumUtils.textoPresentePagina(driver, "Retroalimentación de los deberes");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * Ver tareas
	 */
	@Test
	public void pR38listHomeworksTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homerworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/exercise/list')]", "Lista de tareas");

		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertEquals(elementos.size(), 6);
		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E2");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de respuesta corta");
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de tipo test");
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de subida de fichero");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * Hacer tarea de respuesta corta
	 */
	@Test
	public void pR39doShortAnswerHomeworkTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homerworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/exercise/list')]", "Lista de tareas");

		By rellenar = By.xpath("//*[@id=\"tableexercise\"]/tbody/tr[1]/td[4]/a");
		driver.findElement(rellenar).click();

		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Responder a las siguientes cuestiones");
		SeleniumUtils.textoPresentePagina(driver, "¿De qué color es el cielo?");

		PO_PrivateView.fillFormAddHomeworkShortAnswer(driver, "Azul celeste", "Comentario del alumno");
		SeleniumUtils.textoPresentePagina(driver, "Lista de tareas");
		assertTrue(x==y);

	}

	/**
	 * Hacer tarea de tipo test
	 */
	@Test
	public void pR39doTestHomeworkTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homerworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/exercise/list')]", "Lista de tareas");

		By rellenar = By.xpath("//*[@id=\"tableexercise\"]/tbody/tr[2]/td[4]/a");
		driver.findElement(rellenar).click();

		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de colores");
		SeleniumUtils.textoPresentePagina(driver, "¿De qué color es el coche?");

		PO_PrivateView.fillFormAddHomeworkTest(driver, "Comentario del alumno");
		SeleniumUtils.textoPresentePagina(driver, "Lista de tareas");
		assertTrue(x==y);

	}
	/**-----------------------------------------------------------------------------------**/

	/**
	 * pR22.- Ver feedback desde pRofesor
	 */
	@Test
	public void pR40listFeedbackTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		// Contamos el número de filas de usuarios + 1 (la de buscar)
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR41.- Corregir ejercicio de tipo test pRofesor
	 */
	@Test
	public void pR41correctTestExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		By corregir = By.xpath("/html/body/div/div[2]/table/tbody/tr[2]/td[5]/a");
		driver.findElement(corregir).click();
		SeleniumUtils.textoPresentePagina(driver, "Corregir ejercicio");
		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "Comentario del alumno");

		PO_PrivateView.fillFormCorrectTest(driver, "hola");

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios para corregir");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}

	/**
	 * pR41.- Corregir ejercicio de tipo test pRofesor
	 */
	@Test
	public void pR41correctShortAnswerExerciseTest() {
		pR07brolprofessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		By corregir = By.xpath("/html/body/div/div[2]/table/tbody/tr[1]/td[5]/a");
		driver.findElement(corregir).click();
		SeleniumUtils.textoPresentePagina(driver, "Corregir ejercicio");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Comentario del alumno");

		PO_PrivateView.fillFormCorrectTest(driver, "adios");

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios para corregir");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}
	
	
	/**------------------------------------------------------------------------------------------**/
	
	/**
	 * ver retroalimentaciones desde alumno
	 */
	@Test
	public void pR42listFeedbackTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homerworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'feedback/list')]", "Retroalimentación de los deberes");

		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		SeleniumUtils.textoPresentePagina(driver, "Responder");
		SeleniumUtils.textoPresentePagina(driver, "hola");
		SeleniumUtils.textoPresentePagina(driver, "adios");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}
	
	/**
	 * responder retroalimentaciones desde alumno
	 */
	@Test
	public void pR43answerFeedbackTest() {
		pR07arolStudentTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homerworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'feedback/list')]", "Retroalimentación de los deberes");

		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		
		By responder = By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[2]/div[1]/button");
		driver.findElement(responder).click();
		
		PO_PrivateView.fillFormAnswerFeedback(driver, "respuesta");
		
		SeleniumUtils.textoPresentePagina(driver, "Respondido");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		assertTrue(x==y);
	}
	

}
