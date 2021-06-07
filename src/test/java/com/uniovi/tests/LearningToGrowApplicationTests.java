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

	/**----------------------------identificación por roles--------------------------**/
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

	/**----------------------------Login--------------------------**/
	/**
	 * PR08. Identificación inválida de usuario
	 * Contraseña invalida
	 * Contraseña vacia
	 * Username vacio
	 * campos vacios
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
	 * PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
	 */
	@Test
	public void signoutDisabledTest() {
		SeleniumUtils.textoNoPresentePagina(driver, "Desconectar");

	}

	/**----------------------------Login como profesor--------------------------**/
	/**
	 * PR10. Loguearse como profesor, comprobar que se visualizan 3 filas de
	 * ejercicios y desconectarse
	 */
	@Test
	public void listExercisesTest() {
		PO_LoginView.login(driver, "profesor", "123456", "Ejercicios creados");
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

		PO_LoginView.login(driver, "profesor", "123456", "Ejercicios creados");

		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[2]/td[3]/a", "E1",
				"Ejercicio de colores");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[3]/td[3]/a", "E2",
				"Subidme la ficha que hicimos en clase");
		PO_HomeView.clickDetails(driver, "/html/body/div[2]/div[1]/table/tbody/tr[1]/td[3]/a", "E3",
				"Responder a las siguientes cuestiones");

		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "¡Hola! Conéctate");
	}

	/**----------------------------Crear los tres ejercicios--------------------------**/
	
	/**
	 * P14. Loguearse como profesor y agregar un ejercicio de tipo test 
	 */
	// P14. Loguearse como profesor y Agregar Nota A2.
	// P14. Esta prueba podría encapsularse mejor ...
	@Test
	public void PR12() {

		PO_LoginView.login(driver, "profesor", "123456", "Ejercicios creados");

		// Pinchamos en la opción de menu de nuevo ejercicio: //li[contains(@id, 'exercise-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'exercises-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de añadir ejercicio: //a[contains(@href,'exercise/test/add')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'exercise/test/add')]");
		// Pinchamos en agregar tipo test.
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Agregar ejercicio de tipo test");
		// Ahora vamos a rellenar el ejercicio. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddTestExercise(driver, 3, "Ejercicio de tipo test", "Este es un ejemplo de ejercicio de tipo test");
		
		PO_View.checkElement(driver, "text", "Agregar pregunta");
		PO_View.checkElement(driver, "text", "Este es un ejemplo de ejercicio de tipo test");
		
		//Ahora vamos a rellenar las preguntas
		PO_PrivateView.fillFormAddQuestionTest(driver, "P1", "A1", "A2", "A3", "/html/body/div/form/div[2]/div/div[2]/span");
		
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
		// Ahora nos desconectamos
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	}

	/**
	 * Eliminar algun ejercicio
	 */

	/**
	 * Ver students
	 */

	/**
	 * Crear asignaturas
	 */

	/**
	 * Ver asignaturas
	 */

	/**
	 * Ver feedback
	 */

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
