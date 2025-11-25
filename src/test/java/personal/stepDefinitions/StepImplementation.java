package personal.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import personal.TestComponents.BaseTest;
import personal.pageobjects.CartPage;
import personal.pageobjects.CheckoutPage;
import personal.pageobjects.LandingPage;
import personal.pageobjects.OrderConfirmation;
import personal.pageobjects.ProductCatalogue;

public class StepImplementation extends BaseTest {
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public OrderConfirmation orderConfirmation;

	@Given("landing on Ecommerce Page")
	public void landing_on_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}

	// old regex @Given("^Logging with username (.+) and password (.+)$")
	@Given("Logging with username {string} and password {string}") // new version, need to add "" as "<name>" in feature
																	// file
	public void logging_with_username_and_password(String username, String password) throws InterruptedException {

		productCatalogue = landingPage.loginActions(username, password);
	}

	@When("^Add the the product (.+) to cart$") // old regex
	public void add_the_the_product_to_cart(String productName) throws InterruptedException {
		productCatalogue.addProductToCart(productName);

	}

	@And("^Checkout (.+) and submit the order$") // old regex
	public void checkout_and_submit_the_order(String productName) {
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match, "Product was not found in the cart!");

		// Proceed to checkout
		CheckoutPage checkOut = cartPage.goToCheckOutPage();
		checkOut.selectCountry("india");
		orderConfirmation = checkOut.placeOrder();
	}

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_is_displayed_on_confirmation_page(String string) {
		String confirmMessage = orderConfirmation.getOrderConfirmation();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string), "Order confirmation message did not match!");
		// driver.quit();
	}

	@Then("{string} message is displayed")
	public void message_is_displayed(String string) throws InterruptedException {
		String actualMessage = landingPage.getToastMessage();
		Assert.assertEquals(actualMessage, string, "Error message did not match!");
	}

	@After
	public void tearDownScenario() {

		if (driver != null) {
			driver.quit();
		}
	}
}
