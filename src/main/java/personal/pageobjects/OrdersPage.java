package personal.pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import personal.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent {

	WebDriver driver;
	WebDriverWait wait;

	// Constructor: initializes driver, wait, and page elements
	public OrdersPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		PageFactory.initElements(driver, this);
	}

	// Locator for the country search input field on the order page
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	private WebElement searchBox;

	// Locator for selecting the desired country from the suggestion list
	@FindBy(css = ".ta-item:nth-of-type(2)")
	private WebElement countrySelection;

	// Locator for the "Place Order" button
	@FindBy(className = "action__submit")
	private WebElement placeOrder;

	// Locator used for waiting until country options appear
	private By countrySelectionList = By.cssSelector(".ta-item");

	// List of product names displayed in the orders table
	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> orderProductNames;

	/**
	 * Performs the actions required to complete an order: - Enters a country name -
	 * Selects the country from suggestions - Clicks on the "Place Order" button
	 *
	 * @return an OrderConfirmation page object instance
	 */
	public OrderConfirmation orderActions() {
		// Country selection process
		searchBox.sendKeys("india");
		waitForElementToBeAppear(countrySelectionList);
		countrySelection.click();
		placeOrder.click();

		// Navigate to order confirmation page
		OrderConfirmation orderConfirmation = new OrderConfirmation(driver);
		return orderConfirmation;
	}

	/**
	 * Verifies whether a specific product is displayed in the order history.
	 *
	 * @param productName The name of the product to verify.
	 * @return true if the product is found, false otherwise.
	 */
	public boolean verifyOrderDisplay(String productName) {
		boolean match = orderProductNames.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
}
