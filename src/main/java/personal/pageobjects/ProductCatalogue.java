package personal.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import personal.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	// Constructor to initialize WebDriver and page elements
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ===== Locators & PageFactory Elements =====

	private By findBy = By.cssSelector(".card-body"); // Locator for product cards

	@FindBy(css = ".card-body")
	private List<WebElement> productList; // List of all products displayed

	@FindBy(css = ".ng-animating")
	private WebElement aninamation; // Animation element for loading

	private By addToCart = By.cssSelector("button:last-of-type"); // Add to cart button inside product card
	private By toast = By.cssSelector("#toast-container"); // Toast message locator

	// ===== Page Methods =====

	/**
	 * Returns the list of all product elements displayed on the page.
	 *
	 * @return List of WebElements representing products
	 */
	public List<WebElement> getProductList() {
		waitForElementToBeAppear(findBy);
		return productList;
	}

	/**
	 * Finds a product element by its name.
	 *
	 * @param productName Name of the product to find
	 * @return WebElement of the product, or null if not found
	 */
	public WebElement getProductByname(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
				.findFirst().orElse(null);
		return prod;
	}

	/**
	 * Adds the specified product to the cart.
	 *
	 * @param productName Name of the product to add
	 * @throws InterruptedException if thread sleep is interrupted during wait
	 */
	public void addProductToCart(String productName) throws InterruptedException {
		WebElement prod = getProductByname(productName);
		WebElement addToCartButton = prod.findElement(addToCart);
		actions.moveToElement(addToCartButton) // ensures pointer is over element
				.click() // perform click
				.perform();
		// prod.findElement(addToCart).click();
		waitForElementToBeAppear(toast);
		waitForElementToDisappear(aninamation);
	}
}