package personal.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import personal.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;
	WebDriverWait wait;

	// Constructor: initializes the driver and PageFactory elements
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// List of products currently displayed in the cart
	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProds;

	// Checkout button element
	@FindBy(xpath = "//button[.='Checkout']")
	private WebElement checkOutPage;

	/**
	 * Verifies if the specified product is displayed in the cart.
	 * @param productName Name of the product to verify.
	 * @return true if the product is found in the cart, otherwise false.
	 */
	public boolean verifyProductDisplay(String productName) {
		boolean match = cartProds.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}

	/**
	 * Navigates to the Checkout page by clicking on the checkout button.
	 * @return a new instance of CheckoutPage.
	 */
	public CheckoutPage goToCheckOutPage() {
		actions.moveToElement(checkOutPage)     // ensures pointer is over element
	      .click()                            // perform click
	      .perform();  
		//checkOutPage.click();
		return new CheckoutPage(driver);
	}
}
