package es.urjc.code.daw.library;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(
	classes = Application.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class Web_InterfaceTest {
	
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
	public void whenAddBook_ThenBookIsCreated () throws InterruptedException {
		
		driver.get("http://localhost:" + this.port + "/");
		
		String newBookName = "Eragon";
		String newBookDesc = "En el lejano reino de Alagaesia...";
		
		driver.findElement(By.xpath("//button")).click();
		
		Thread.sleep(1000);
		
		
		driver.findElement(By.name("title")).sendKeys(newBookName);
		driver.findElement(By.name("description")).sendKeys(newBookDesc);
		driver.findElement(By.id("Save")).click();
		
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//button[contains(text(), 'All Books')]")).click();
		
		Thread.sleep(1000);
		
		
		assertNotNull(driver.findElement(By.linkText(newBookName)));
		
		
		
		
		
		
	}
	@Test
	public void whenDeleteBook_ThenBookNoLongerExists () throws InterruptedException {
		
		driver.get("http://localhost:" + this.port + "/");
		
		String newBookName = "El Hobbit";
		String newBookDesc = "Bilbo era un hobbit...";
		
		driver.findElement(By.xpath("//button")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.name("title")).sendKeys(newBookName);
		driver.findElement(By.name("description")).sendKeys(newBookDesc);
		driver.findElement(By.id("Save")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(), 'Remove')]")).click();

		Thread.sleep(1000);
		
		assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.linkText("El Hobbit"));
		});
	}
}
