package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

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
public class LearningToGrowApplicationTests {

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
	public void homeViewTest() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	/**
	 * PR02. Opción de navegación. Pinchar en el enlace Registro en la página home
	 */
	@Test
	public void navSignupTest() {
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
	}

	/**
	 * PR03. Opción de navegación. Pinchar en el enlace Identificate en la página
	 * home
	 */
	@Test
	public void navLoginTest() {
		PO_HomeView.clickOption(driver, "login", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR04. Opción de navegación. Cambio de idioma de Español a Ingles y vuelta a
	 * Español
	 */
	@Test
	public void changeLangTest() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
	}

	/**
	 * PR05. Registro de usuario con datos válidos. /
	 */
	@Test
	public void signupValidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");

		// Rellenamos el formulario con datos validos
		PO_RegisterView.fillForm(driver, "santiago", "Santi", "López", "santiago@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos que entramos en la sección privada
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Bienvenidos,", 5);
	}

	/**
	 * PR06. Prueba del formulario de registro. Username repetido en la BD Username
	 * corto Name corto Lastname corto Email no valido Contraseña no valida
	 */
	@Test
	public void signupInvalidTest() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "text", "¡Regístrate como nuevo usuario!");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "alumno", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
		// Comprobamos el error de username repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.username.duplicate", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "jose", "Jose", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
//		// Comprobamos el error de username corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.username.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "joseperez", "Jo", "Perez", "jose@gmail.com", "Pollito88", "Pollito88");
//		// Comprobamos el error de name corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Pe", "jose@gmail.com", "Pollito88", "Pollito88");
//		// Comprobamos el error de apellido corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose.com", "Pollito88", "Pollito88");
//		// Comprobamos el error de email no valido.
//		PO_RegisterView.checkKey(driver, "Error.signup.email", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "joseperez", "Jose", "Perez", "jose@gmail.com", "pollito", "pollito");
//		// Comprobamos el error de Nombre corto .
//		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
	}

	/**
	 * ----------------------------identificación por
	 * roles--------------------------
	 **/
	/**
	 * PR07a. Loguearse con exito desde el Rol de estudiante
	 */
	@Test
	public void rolStudentTest() {
		PO_LoginView.login(driver, "alumno", "123456", "student");
	}

	/**
	 * PR07b. Loguearse con exito desde el Rol de profesor
	 */
	@Test
	public void rolProfessorTest() {
		PO_LoginView.login(driver, "profesor", "123456", "professor");
	}

	/**
	 * PR07c. Loguearse con exito desde el Rol de administrador
	 */
	@Test
	public void rolAdministradorTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");
	}

	/** ----------------------------Login-------------------------- **/
	/**
	 * PR08. Identificación inválida de usuario Contraseña invalida Contraseña vacia
	 * Username vacio campos vacios
	 */
	@Test
	public void loginInvalidTest() {
		PO_LoginView.login(driver, "mariagg", "123456", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "mariagg", "", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "", "Admin33", "¡Hola! Conéctate");
		PO_LoginView.login(driver, "", "", "¡Hola! Conéctate");
	}

	/**
	 * PR09. Identificación válida de usuario y cierre de sesión
	 */
	@Test
	public void loginAndLogoutTest() {
		PO_LoginView.login(driver, "mariagg", "Admin33", "Esta es la vista de administrador");

		PO_HomeView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	 * está autenticado.
	 */
	@Test
	public void signoutDisabledTest() {
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como profesor--------------------------
	 * -------------------------------------------------------------------------
	 **/

	/**
	 * PR10. Loguearse como profesor, comprobar que se visualizan 3 filas de
	 * ejercicios y desconectarse
	 */
	@Test
	public void listExercisesHomeTest() {
		rolProfessorTest();
		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
		PO_View.checkElement(driver, "text", "E1");
		PO_View.checkElement(driver, "text", "E2");
		PO_View.checkElement(driver, "text", "E3");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**
	 * PR11. Loguearse como profesor y ver los detalles de los ejercicios
	 */
	@Test
	public void viewDetailsTest() {

		rolProfessorTest();

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
	 * P14a. Loguearse como profesor y agregar un ejercicio de tipo test
	 */
	@Test
	public void addTestExerciseTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/test/add')]", "Agregar ejercicio de tipo test");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de tipo test",
				"Este es un ejemplo de ejercicio de tipo test");

		PO_View.checkElement(driver, "text", "Agregar pregunta");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de tipo test");

		// Ahora vamos a rellenar las preguntas
		PO_PrivateView.fillFormAddQuestionTest(driver, "P1", "A1", "A2", "A3",
				"/html/body/div/form/div[2]/div/div[2]/span");

		PO_View.checkElement(driver, "text", "La pregunta se ha añadido correctamente");

		By show = By.xpath("/html/body/div/form/div[4]/div/a[1]");
		driver.findElement(show).click();
		PO_View.checkElement(driver, "text", "Ejercicio de tipo test");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de tipo test");
		PO_View.checkElement(driver, "text", "P1");
		PO_View.checkElement(driver, "text", "A1");
		PO_View.checkElement(driver, "text", "A2");
		PO_View.checkElement(driver, "text", "A3");
		By finish = By.xpath("//*[@id=\"finish\"]");
		driver.findElement(finish).click();

		PO_View.checkElement(driver, "text", "Ejercicios creados");
		PO_View.checkElement(driver, "text", "Ejercicio de tipo test");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * P14b. Loguearse como profesor y agregar un ejercicio de subida de fichero
	 */
	@Test
	public void addUploadFileExerciseTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/upFile/add')]", "Agregar ejercicio de subida de fichero");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de subida de fichero",
				"Este es un ejemplo de ejercicio de subida de fichero");

		PO_View.checkElement(driver, "text", "Ejercicios creados");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de subida de fichero");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * P14c. Loguearse como profesor y agregar un ejercicio de completar con texto
	 */
	@Test
	public void addShortAnswerExerciseTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id,
		// 'exercise-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'exercises-menu')]/a",
				"//a[contains(@href, 'exercise/shortAnswer/add')]", "Agregar ejercicio de respuesta corta");

		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddExercise(driver, "Ejercicio de respuesta corta",
				"Este es un ejemplo de ejercicio de respuesta corta");

		PO_View.checkElement(driver, "text", "Agregar pregunta");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora vamos a rellenar las preguntas
		PO_PrivateView.fillFormAddQuestionShortAnswer(driver, "Pregunta", "Respuesta");

		PO_View.checkElement(driver, "text", "La pregunta se ha añadido correctamente");

		By show = By.xpath("/html/body/div/form/div[4]/div/a[1]");
		driver.findElement(show).click();
		PO_View.checkElement(driver, "text", "Ejercicio de respuesta corta");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de respuesta corta");
		PO_View.checkElement(driver, "text", "Pregunta");
		PO_View.checkElement(driver, "text", "Respuesta");
		By finish = By.xpath("/html/body/div/div[2]/div/form/a");
		driver.findElement(finish).click();

		PO_View.checkElement(driver, "text", "Ejercicios creados");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de respuesta corta");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR15. - Ver listado de ejercicios
	 */
	@Test
	public void listExercisesTest() {
		rolProfessorTest();

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
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR16.- Eliminar algun ejercicio
	 */
	@Test
	public void deleteExerciseTest() {
		rolProfessorTest();

		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'exercise/list')]", "Ejercicios creados");

		PO_View.checkElement(driver, "text", "E3");
		PO_View.checkElement(driver, "text", "Responder a las siguientes cuestiones");

		By finish = By.xpath("/html/body/div/div[2]/table/tbody/tr[1]/td[4]/a");
		driver.findElement(finish).click();

		SeleniumUtils.textoNoPresentePagina(driver, "E3");
		SeleniumUtils.textoNoPresentePagina(driver, "Responder a las siguientes cuestiones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");

	}

	/**
	 * PR17. - Ver students
	 */
	@Test
	public void listStudentsTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'user/student/list')]", "Listado de alumnos");

		// Esperamos a que se muestren los enlaces de paginacion la lista de peticiones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "/html/body/div/div[2]", PO_View.getTimeout());

		PO_View.checkElement(driver, "text", "Pedro");
		PO_View.checkElement(driver, "text", "Lucas");
		PO_View.checkElement(driver, "text", "María");
		PO_View.checkElement(driver, "text", "Lucía");
		PO_View.checkElement(driver, "text", "Raquel");

	}

	/**
	 * PR18a. - Realizar una busqueda en el listado de usuarios (cadena vacía)
	 */
	@Test
	public void filteredStudentsEmptyTextTest() {
		listStudentsTest();

		// Realizar una busqueda --> cadena vacia
		WebElement text = driver.findElement(By.id("searchText"));
		text.click();
		text.clear();
		text.sendKeys("");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no filtra nada, y se muestran los alumnos
		PO_View.checkElement(driver, "text", "Pedro");
		PO_View.checkElement(driver, "text", "Lucas");
		PO_View.checkElement(driver, "text", "María");
		PO_View.checkElement(driver, "text", "Lucía");
		PO_View.checkElement(driver, "text", "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR18b. - Realizar una busqueda en el listado de usuarios (cadena que no
	 * existe)
	 */
	@Test
	public void filteredStudentsNonExistentTextTest() {
		listStudentsTest();

		// Realizar una busqueda --> cadena que no existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("JulioLopezSegovia");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		PO_View.checkElement(driver, "text", "No se han encontrado resultados.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR18c. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void filteredStudentExistentTextTest() {
		listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("Pedro");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		PO_View.checkElement(driver, "text", "Pedro");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR18d. - Realizar una busqueda en el listado de usuarios (cadena que existe)
	 */
	@Test
	public void filteredStudentExistentTextPartialTest() {
		listStudentsTest();

		// Realizar una busqueda --> cadena que existe
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys("ía");
		By boton = By.className("btn");
		driver.findElement(boton).click();

		// Comprobamos que no se muestran los alumnos
		PO_View.checkElement(driver, "text", "Pedro");
		PO_View.checkElement(driver, "text", "María");
		PO_View.checkElement(driver, "text", "Lucía");
		SeleniumUtils.textoNoPresentePagina(driver, "No se han encontrado resultados.");
		SeleniumUtils.textoNoPresentePagina(driver, "Lucas");
		SeleniumUtils.textoNoPresentePagina(driver, "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR19.- Ver listado de asignaturas
	 */
	@Test
	public void subjectListTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		PO_View.checkElement(driver, "text", "Matemáticas");
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR20.- Crear una nueva asignatura
	 */
	@Test
	public void createSubjectTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'subject/add')]",
				"Alumnos");

		PO_PrivateView.fillFormAddSubject(driver, "Plástica");

		By boton = By.xpath("//html/body/div/form/div[4]/a");
		driver.findElement(boton).click();

		PO_View.checkElement(driver, "text", "Listado de asignaturas");
		PO_View.checkElement(driver, "text", "Plástica");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR21.- Ver detalles de la asignatura creada
	 */
	@Test
	public void viewDetailsSubjectTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		PO_View.checkElement(driver, "text", "Plástica");

		By boton = By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/a[1]");
		driver.findElement(boton).click();

		PO_View.checkElement(driver, "text", "Plástica");
		PO_View.checkElement(driver, "text", "Marta");
		PO_View.checkElement(driver, "text", "Pedro");
		PO_View.checkElement(driver, "text", "Lucas");
		PO_View.checkElement(driver, "text", "María");
		PO_View.checkElement(driver, "text", "Lucía");
		PO_View.checkElement(driver, "text", "Raquel");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR22.- Eliminar una asignatura
	 */
	@Test
	public void deleteSubjectTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a",
				"//a[contains(@href, 'subject/list')]", "Listado de asignaturas");

		PO_View.checkElement(driver, "text", "Matemáticas");
		PO_View.checkElement(driver, "text", "Plástica");

		By boton = By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/a[2]");
		driver.findElement(boton).click();

		SeleniumUtils.textoNoPresentePagina(driver, "Plástica");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR23.- Ver feedback
	 */
	@Test
	public void viewEmptyFeedbackTest() {
		rolProfessorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'homeworks-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'homerworks-menu')]/a",
				"//a[contains(@href, 'homework/list')]", "Ejercicios para corregir");

		PO_View.checkElement(driver, "text",
				"Aún no hay tareas para corregir. Espere a que los alumnos rellenen sus tareas.");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * -------------------------------------------------------------------------
	 * ----------------------------Login como admin--------------------------
	 * -------------------------------------------------------------------------
	 **/
	/**
	 * PR24.- Ver feedback
	 */
	@Test
	public void listUsersTest() {
		rolAdministradorTest();

		// Pinchamos en la opción de menu de deberes: //li[contains(@id,
		// 'users-menu')]/a
		PO_NavView.clickOptionMenu(driver, "//li[contains(@id, 'users-menu')]/a", "//a[contains(@href, 'user/list')]",
				"Usuarios");

		// Esperamos a que se muestren los enlaces de paginacion la lista de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");

		// Contamos el número de filas de peticiones
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		PO_View.checkElement(driver, "text", "profesor");
		By next = By.xpath("/html/body/div/div[3]/ul/li[3]/a");
		driver.findElement(next).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * PR. Loguearse como estudiante, comprobar que se visualizan 3 filas de
	 * ejercicios y desconectarse
	 */
	@Test
	public void PR11() {
//		PO_LoginView.login(driver, "alumno", "123456", "student");
//		// Contamos el número de filas de notas
//		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
//				PO_View.getTimeout());
//		assertTrue(elementos.size() == 3);
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

}
