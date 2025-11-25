package personal.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static void main(String args[]) throws InterruptedException {

		String ProductName = "ZARA COAT 3";
		// WebDriverManager.firefoxdriver().setup();
		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\Santosh Patil\\OneDrive\\Documents\\geckodriver-v0.36.0-win32\\geckodriver.exe");

		// Initialize the Firefox WebDriver
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//Login
		driver.findElement(By.id("userEmail")).sendKeys("santoshsp17@gmai.com");
		driver.findElement(By.id("userPassword")).sendKeys("Gunnaa@123");
		driver.findElement(By.id("login")).click();
		
		//find product select desired product
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".card-body")));
			
		List<WebElement> productList = driver.findElements(By.cssSelector(".card-body"));
		
		WebElement prod = productList.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(ProductName)).findFirst()
				.orElse(null);
		prod.findElement(By.cssSelector("button:last-of-type")).click();

		//wait for animation & toast-container"
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#toast-container")));
		
		//go cart & check desired product is slected
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		
		
		List<WebElement> cartProd = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean match = cartProd.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(ProductName));
		Assert.assertTrue(match);

		//checkout
		driver.findElement(By.xpath("//button[.='Checkout']")).click();
		
		//country slection
		Actions a =new Actions(driver);
		a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "india").build().perform();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ta-item")));
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		
		//place order
		driver.findElement(By.className("action__submit")).click();
		
		//order confirmation
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU for the order."));

		driver.quit();

	}

}
