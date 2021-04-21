package es.urjc.code.daw.library;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(
	classes = Application.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class Web_InterfaceTest {
	
	@LocalServerPort
	int port;
	protected WebDriver driver;
	
	@BeforeAll
	 public static void setupClass() {
		WebDriverManager.firefoxdriver().setup();
	}
	
	@BeforeEach
	public void setupTest() {
		this.driver = new FirefoxDriver();
	}
	
	@AfterEach
	public void teardown() {
		if (driver != null) {
			
		}
		driver.quit();
	} 
	
	
	@Test
	void whenAddBook_ThenBookIsCreated () {
		
		driver.get("http://localhost:" + this.port + "/");
		
		String newBookName = "Eragon";
		String newBookDesc = "En el lejano reino de Alagaesia...";
		
		//Se clica en el boton de "new book" y se espera a que se cargue la pagina
		driver.findElement(By.xpath("//button")).click();
		WebDriverWait wait = new WebDriverWait (driver, 30);
		wait.until(presenceOfElementLocated(By.xpath("//h2[contains(text(), 'New Book')]")));
		
		//Se rellenan los datos del nuevo libro y se clica en "Save", se espera a que se cargue la siguiente pagina
		driver.findElement(By.name("title")).sendKeys(newBookName);
		driver.findElement(By.name("description")).sendKeys(newBookDesc);
		driver.findElement(By.id("Save")).click();
		wait.until(presenceOfElementLocated(By.xpath("//button[contains(text(), 'All Books')]")));
		
		//Se vuelve al menu principal y se realiza la asercion
		driver.findElement(By.xpath("//button[contains(text(), 'All Books')]")).click();
		wait.until(presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Books')]")));
		
		assertNotNull(driver.findElement(By.linkText(newBookName)));	
	}
	
	
	@Test
	void whenDeleteBook_ThenBookNoLongerExists () {
		
		driver.get("http://localhost:" + this.port + "/");
		
		String newBookName = "El Hobbit";
		String newBookDesc = "Bilbo era un hobbit...";
		
		//Se crea un wait de forma similar al test anterior
		driver.findElement(By.xpath("//button")).click();
		WebDriverWait wait = new WebDriverWait (driver, 30);
		wait.until(presenceOfElementLocated(By.xpath("//h2[contains(text(), 'New Book')]")));
		
		//Se crea un nuevo libro como en el test anterior
		driver.findElement(By.name("title")).sendKeys(newBookName);
		driver.findElement(By.name("description")).sendKeys(newBookDesc);
		driver.findElement(By.id("Save")).click();
		wait.until(presenceOfElementLocated(By.xpath("//button[contains(text(), 'All Books')]")));
		
		//Se clica en el boton de eliminar libro, se espera a volver al inicio y se comprueba la asercion
		driver.findElement(By.xpath("//button[contains(text(), 'Remove')]")).click();
		wait.until(presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Books')]")));
		
		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.linkText(newBookName));
		});
	}
}
