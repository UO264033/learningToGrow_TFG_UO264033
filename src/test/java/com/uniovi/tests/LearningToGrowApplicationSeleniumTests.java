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
//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LearningToGrowApplicationSeleniumTests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\maari\\OneDrive\\Documentos\\Universidad\\CUARTO\\TFG\\proyecto\\workspace\\learningToGrow\\learningToGrow_TFG_UO264033\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";
	protected static PO_Properties p = new PO_Properties("messages");

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
	 * PR01. Acceder a la página principal
	 */
	@Test
	public void PR01_homeViewTest() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	/**
	 * PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
	 */
	@Test
	public void PR02_navSignupTest() {
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
	}

	/**
	 * PR03. Opción de navegación. Pinchar en el enlace Identificate en la página
	 * home
	 */
	@Test
	public void PR03_navLoginTest() {
		PO_HomeView.clickOption(driver, "login", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR04. Opción de navegación. Cambio de idioma de Español a Ingles y vuelta a
	 * Español
	 */
	@Test
	public void PR04_changeLangTest() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
	}

	/**
	 * PR05. Registro de usuario con datos válidos. /
	 */
	@Test
	public void PR05_signupValidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");

		// Rellenamos el formulario con datos validos
		PO_RegisterView.fillForm(driver, "santiago", "Santi", "López", "santiago@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos que entramos en la sección privada
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido,");
		SeleniumUtils.textoPresentePagina(driver, "santiago");
		SeleniumUtils.textoPresentePagina(driver, "autenticado como");
	}

	/**
	 * PR06. Prueba del formulario de registro. Username repetido en la BD Username
	 * corto Name corto Lastname corto Email no valido Contraseña no valida
	 */
	@Test
	public void PR06_signupInvalidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "alumno", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "jose", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos el error de username corto .
		PO_RegisterView.checkKey(driver, "Error.signup.username.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "J", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "P", "jose@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose.com", "Pollito88", "Pollito88");
		// Comprobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose@gmail.com", "pollito", "pollito");
		// Comprobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
	}

	/**
	 * ----------------------identificación por roles-------------------
	 **/

	/**
	 * PR07a. Loguearse con exito desde el Rol de estudiante
	 */
	@Test
	public void PR07a_rolStudentTest() {
		PO_LoginView.login(driver, "alumno", "123456", "student");
	}

	/**
	 * PR07b. Loguearse con exito desde el Rol de profesor
	 */
	@Test
	public void PR07b_rolProfessorTest() {
		PO_LoginView.login(driver, "profesor", "123456", "professor");
	}

	/**
	 * PR07c. Loguearse con exito desde el Rol de administrador
	 */
	@Test
	public void PR07c_rolAdministradorTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");
	}

	/** ----------------------------Login-------------------------- **/
	/**
	 * PR08. Identificación inválida de usuario Contraseña invalida Contraseña vacia
	 * Username vacio campos vacios
	 */
	@Test
	public void PR08_loginInvalidTest() {
		PO_LoginView.login(driver, "mariagg", "123456", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Usuario y/o contraseña incorrectos.");
		PO_LoginView.login(driver, "mariagg", "", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "", "Admin33", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "pepitogrillo", "Admin33", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Usuario y/o contraseña incorrectos.");
		PO_LoginView.login(driver, "", "", "¡Hola! Conéctate");
	}

	/**
	 * PR09. Identificación válida de usuario y cierre de sesión
	 */
	@Test
	public void PR09_loginAndLogoutTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");

		PO_HomeView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
		SeleniumUtils.textoPresentePagina(driver, "Sesión cerrada con éxito.");
	}

	/**
	 * PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	 * está autenticado.
	 */
	@Test
	public void PR10_signoutDisabledTest() {
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como profesor--------------------------
	 * -------------------------------------------------------------------------
	 **/

	/**
	 * PR11. Loguearse como profesor, comprobar que se visualizan 3 filas de
	 * ejercicios y desconectarse
	 */
	@Test
	public void PR11_listExercisesHomeTest() {
		PR07b_rolProfessorTest();
		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		SeleniumUtils.textoPresentePagina(driver, "E1");
		SeleniumUtils.textoPresentePagina(driver, "E2");
		SeleniumUtils.textoPresentePagina(driver, "E3");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR12. Loguearse como profesor y ver los detalles de los ejercicios
	 */
	@Test
	public void PR12_viewDetailsTest() {

		PR07b_rolProfessorTest();

		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[2]/td[3]/a", "E1",
				"Ejercicio de colores");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[3]/td[3]/a", "E2",
				"Subidme la ficha que hicimos en clase");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[1]/td[3]/a", "E3",
				"Responder a las siguientes cuestiones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * ----------------------------Crear los tres ejercicios------------------
	 **/

	/**
	 * P13a. Loguearse como profesor y agregar un ejercicio de tipo test
	 */
	@Test
	public void PR13a_addTestExerciseTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/test/add')]", "Agregar ejercicio de tipo test");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test",
				"Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de tipo test");

		// Ahora vamos a rellenar las preguntas
		PO_PrivateView.fillFormAddQuestionTest(driver, "P1", "A1", "A2", "A3",
				"/html/body/div/form/div[2]/div/div[2]/span");

		SeleniumUtils.textoPresentePagina(driver, "La pregunta se ha añadido correctamente");

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
	}

	/**
	 * P13a2. Loguearse como profesor y agregar un ejercicio de tipo test inválido
	 */
	@Test
	public void PR13a2_addTestExerciseTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/test/add')]", "Agregar ejercicio de tipo test");

		// Ahora vamos a rellenar el ejercicio: nombre repetido
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test",
				"Dos.- Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Introduzca otro nombre de ejercicio, el introducido ya existe");
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pregunta");

		// Ahora vamos a rellenar el ejercicio: nombre vacio
		PO_PrivateView.fillFormAddExercise(driver, "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pregunta");

		// Ahora vamos a rellenar el ejercicio.
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test 2",
				"Dos.- Este es un ejemplo de ejercicio de tipo test");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de tipo test");

		// Ahora vamos a rellenar las preguntas: pregunta y respuestas vacias
		PO_PrivateView.fillFormAddQuestionTest(driver, "", "", "", "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "La pregunta se ha añadido correctamente");
		SeleniumUtils.textoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de tipo test");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * P13b. Loguearse como profesor y agregar un ejercicio de subida de fichero
	 */
	@Test
	public void PR13b_addUploadFileExerciseTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * P13b. Loguearse como profesor y agregar un ejercicio de subida de fichero
	 * invalido
	 */
	@Test
	public void PR13b2_addUploadFileExerciseTest() {
		PR07b_rolProfessorTest();

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
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pregunta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * P13c. Loguearse como profesor y agregar un ejercicio de completar con texto
	 */
	@Test
	public void PR13c_addShortAnswerExerciseTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/shortAnswer/add')]", "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta",
				"Este es un ejemplo de ejercicio de respuesta corta");

		SeleniumUtils.textoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora vamos a rellenar las preguntas
		PO_PrivateView.fillFormAddQuestionShortAnswer(driver, "Pregunta", "Respuesta");

		SeleniumUtils.textoPresentePagina(driver, "La pregunta se ha añadido correctamente");

		By show = By.xpath("/html/body/div/form/div[4]/div/a[1]");
		driver.findElement(show).click();
		SeleniumUtils.textoPresentePagina(driver, "Ejercicio de respuesta corta");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");
		SeleniumUtils.textoPresentePagina(driver, "Pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Respuesta");
		By finish = By.xpath("/html/body/div/div[2]/div/form/a");
		driver.findElement(finish).click();

		SeleniumUtils.textoPresentePagina(driver, "Ejercicios creados");
		SeleniumUtils.textoPresentePagina(driver, "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * P13c2. Loguearse como profesor y agregar un ejercicio de completar con texto
	 * invalido
	 */
	@Test
	public void PR13c2_addShortAnswerExerciseTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/shortAnswer/add')]", "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio. nombre repetido
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta",
				"Este es un ejemplo de ejercicio de respuesta corta");

		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Introduzca otro nombre de ejercicio, el introducido ya existe");

		// Ahora vamos a rellenar el ejercicio. : nombre y descripcion vacia
		PO_PrivateView.fillFormAddExercise(driver, "", "");
		SeleniumUtils.textoNoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio.
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta 2",
				"Dos.- Este es un ejemplo de ejercicio de respuesta corta");
		// Pregunta y respuesta vacia
		PO_PrivateView.fillFormAddQuestionShortAnswer(driver, "", "");

		SeleniumUtils.textoNoPresentePagina(driver, "La pregunta se ha añadido correctamente");
		SeleniumUtils.textoPresentePagina(driver, "Agregar pregunta");
		SeleniumUtils.textoPresentePagina(driver, "Dos.- Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR14. - Ver listado de ejercicios
	 */
	@Test
	public void PR14_listExercisesTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR15.- Eliminar algun ejercicio
	 */
	@Test
	public void PR15_deleteExerciseTest() {
		PR07b_rolProfessorTest();

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

	}

	/**
	 * PR16. - Ver students
	 */
	@Test
	public void PR16_listStudentsTest() {
		PR07b_rolProfessorTest();

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

	}

	/**
	 * PR17a. - Realizar una busqueda en el listado de usuarios (cadena vacía)
	 */
	@Test
	public void PR17a_filteredStudentsEmptyTextTest() {
		PR16_listStudentsTest();

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no filtra nada, y se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR17b. - Realizar una busqueda en el listado de usuarios (cadena que no
	 * existe)
	 */
	@Test
	public void PR17b_filteredStudentsNonExistentTextTest() {
		PR16_listStudentsTest();

		// Realizar una busqueda --> cadena que no existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("JulioLopezSegovia");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "No se han encontrado resultados.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR17c. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void PR17c_filteredStudentExistentTextTest() {
		PR16_listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("Pedro");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR17d. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void PR17d_filteredStudentExistentTextPartialTest() {
		PR16_listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("ía");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		SeleniumUtils.textoPresentePagina(driver, "Pedro");
		SeleniumUtils.textoPresentePagina(driver, "María");
		SeleniumUtils.textoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR18.- Ver listado de asignaturas
	 */
	@Test
	public void PR18_subjectListTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR19.- Crear una nueva asignatura
	 */
	@Test
	public void PR19_createSubjectTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR20.- Ver detalles de la asignatura creada
	 */
	@Test
	public void PR20_viewDetailsSubjectTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR21.- Eliminar una asignatura
	 */
	@Test
	public void PR21_deleteSubjectTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR22.- Ver feedback vacio
	 */
	@Test
	public void PR22_viewEmptyFeedbackTest() {
		PR07b_rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		SeleniumUtils.textoPresentePagina(driver,
				"Aún no hay tareas para corregir. Espere a que los alumnos rellenen sus tareas.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como admin--------------------------
	 * -------------------------------------------------------------------------
	 **/
	/**
	 * PR23.- Listado de usuarios
	 */
	@Test
	public void PR23_listUsersTest() {
		PR07c_rolAdministradorTest();

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
		SeleniumUtils.textoPresentePagina(driver, "profesor");
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
	}

	/**
	 * PR24.- Búsqueda de usuarios, cadena vacía
	 */
	@Test
	public void PR24_filteredUsersEmptyTest() {
		PR07c_rolAdministradorTest();

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

		// Comprobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 6); // son 5+1
		SeleniumUtils.textoPresentePagina(driver, "profesor");
		SeleniumUtils.textoPresentePagina(driver, "Pelayo");
		SeleniumUtils.textoPresentePagina(driver, "mariagg");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "lucasnunez");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR25.- Búsqueda de usuarios, cadena existente
	 */
	@Test
	public void PR25_filteredUsersExistentTest() {
		PR07c_rolAdministradorTest();

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

		// Comprobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 2); // son 1+1
		SeleniumUtils.textoPresentePagina(driver, "garcialucia");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR26.- Búsqueda de usuarios, cadena no existente
	 */
	@Test
	public void PR26_filteredUsersNonExistentTest() {
		PR07c_rolAdministradorTest();

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

		// Comprobamos que no filtra nada, y se muestran los usuarios
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		System.out.println(elementos.size());
		assertTrue(elementos.size() == 1); // es 0+1
		SeleniumUtils.textoPresentePagina(driver, "No se han encontrado resultados.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR27.-Añadir un nuevo usuario
	 */
	@Test
	public void PR27_addNewUserTest() {
		PR07c_rolAdministradorTest();

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
	}

	/**
	 * PR27.-Añadir un nuevo usuario
	 */
	@Test
	public void PR27b_addNewUserInvalidTest() {
		PR07c_rolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/add')]",
				"Añadir nuevo usuario");

		PO_PrivateView.fillFormAddOrEditUser(driver, "alumnoEjemplo", "a", "e", "alumnocom", "pepe", "pepe");

		SeleniumUtils.textoNoPresentePagina(driver, "Usuarios");

		// Comprobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
		// Comprobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Comprobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Comprobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// Comprobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR28.- Detalles de usuario
	 */
	@Test
	public void PR28_viewDetailsUserTest() {
		PR07c_rolAdministradorTest();

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

	}

	/**
	 * PR29.- Acceder a editar usuario
	 */
	@Test
	public void PR29_editUserViewThroughoutDetailsTest() {
		PR28_viewDetailsUserTest();

		By details = By.xpath("/html/body/div/div[2]/div/a[1]");
		driver.findElement(details).click();

		SeleniumUtils.textoPresentePagina(driver, "Editar usuario");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR30.- Modificar usuario datos validos
	 */
	@Test
	public void PR30_editUserTest() {
		PR07c_rolAdministradorTest();

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
	}
	
	/**
	 * PR30.- Modificar usuario datos invalidos
	 */
	@Test
	public void PR30b_editUserTest() {
		PR07c_rolAdministradorTest();

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
		// Comprobamos el error de name corto .
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Comprobamos el error de apellido corto .
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Comprobamos el error de email no valido.
		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
		// Comprobamos el error de contraseña invalida .
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		
	}

	/**
	 * PR31.-Eliminar el usuario
	 */
	@Test
	public void PR31_deleteUserTest() {
		PR07c_rolAdministradorTest();

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
	}

	/**
	 * PR32.- Eliminar usuario
	 */
	@Test
	public void PR32_deleteUserThroughoutDetailsTest() {
		PR27_addNewUserTest();
		PR28_viewDetailsUserTest();

		By delete = By.xpath("/html/body/div/div[2]/div/a[2]");
		driver.findElement(delete).click();

		SeleniumUtils.textoPresentePagina(driver, "Usuarios");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * ---------------------------Identificado como alumno
	 * --------------------------
	 */
	/**
	 * PR33. Loguearse como alumno, comprobar que se visualizan las asignaturas a
	 * las que está asignado
	 */
	@Test
	public void PR33_listSubjectsHomeTest() {
		PR07a_rolStudentTest();
		SeleniumUtils.textoPresentePagina(driver, "Bienvenido,");
		SeleniumUtils.textoPresentePagina(driver, "alumno");
		SeleniumUtils.textoPresentePagina(driver, "autenticado como");
		SeleniumUtils.textoPresentePagina(driver, "student");
		SeleniumUtils.textoPresentePagina(driver, "Matemáticas");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR18.- Ver listado de asignaturas desde usuario alumno
	 */
	@Test
	public void PR34_subjectListTest() {
		PR07a_rolStudentTest();

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
	}

	/**
	 * Ver página de detalles de la asignatura
	 */
	@Test
	public void PR35_viewDetailsSubjectTest() {
		PR07a_rolStudentTest();

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
	}

	/**
	 * Acceder a la página de ejercicios a traves de la vista de detalles de
	 * asignatura
	 */
	@Test
	public void PR36_viewExercisesThroghoutSubjectDetailsTest() {
		PR07a_rolStudentTest();

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
	}

	/**
	 * Acceder a la página de feedback a traves de la vista de detalles de
	 * asignatura
	 */
	@Test
	public void PR37_viewFeedbackThroghoutSubjectDetailsTest() {
		PR07a_rolStudentTest();

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
	}

	/**
	 * Ver tareas
	 */
	@Test
	public void PR38_listHomeworksTest() {
		PR07a_rolStudentTest();

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
	}

	/**
	 * Hacer tarea de respuesta corta
	 */
	@Test
	public void PR39_doShortAnswerHomeworkTest() {
		PR07a_rolStudentTest();

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

	}

	/**
	 * Hacer tarea de tipo test
	 */
	@Test
	public void PR39_doTestHomeworkTest() {
		PR07a_rolStudentTest();

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

	}
	/**-----------------------------------------------------------------------------------**/

	/**
	 * PR22.- Ver feedback desde profesor
	 */
	@Test
	public void PR40_listFeedbackTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR41.- Corregir ejercicio de tipo test profesor
	 */
	@Test
	public void PR41_correctTestExerciseTest() {
		PR07b_rolProfessorTest();

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
	}

	/**
	 * PR41.- Corregir ejercicio de tipo test profesor
	 */
	@Test
	public void PR41_correctShortAnswerExerciseTest() {
		PR07b_rolProfessorTest();

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
	}
	
	
	/**------------------------------------------------------------------------------------------**/
	
	/**
	 * ver retroalimentaciones desde alumno
	 */
	@Test
	public void PR42_listFeedbackTest() {
		PR07a_rolStudentTest();

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
	}
	
	/**
	 * responder retroalimentaciones desde alumno
	 */
	@Test
	public void PR43_answerFeedbackTest() {
		PR07a_rolStudentTest();

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
	}
	

}
