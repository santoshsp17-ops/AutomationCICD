package personal.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import personal.pageobjects.CartPage;
import personal.pageobjects.OrdersPage;

public class AbstractComponent {

	WebDriver driver;
	WebDriverWait wait;
	protected Actions actions;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		this.actions = new Actions(driver);
	}

	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement cartHeader;

	@FindBy(css = "[routerlink=\"/dashboard/myorders\"]")
	WebElement orderHeader;

	// Wait until an element is visible in the DOM
	public void waitForElementToBeAppear(By findBy) {
		// wait.until(ExpectedConditions.presenceOfElementLocated((findBy)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	// Wait until an element disappears (currently using sleep as a fallback)
	public void waitForElementToDisappear(WebElement element) throws InterruptedException {

		Thread.sleep(2000);
		// Ideally use: //wait.until(ExpectedConditions.invisibilityOf(element));
	}

	// Navigate to Cart Page
	public CartPage goToCartPage() {
		// Move to element and click
		actions.moveToElement(cartHeader).click().perform();
		// cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}

	// Navigate to Orders Page
	public OrdersPage goToOrdersPage() {
		// Move to element and click
		actions.moveToElement(orderHeader).click().perform();
		// orderHeader.click();
		OrdersPage ordersPage = new OrdersPage(driver);
		return ordersPage;
	}

}
