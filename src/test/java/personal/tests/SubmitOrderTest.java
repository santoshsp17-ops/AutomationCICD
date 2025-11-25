package personal.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import personal.TestComponents.BaseTest;
import personal.pageobjects.CartPage;
import personal.pageobjects.CheckoutPage;
import personal.pageobjects.OrderConfirmation;
import personal.pageobjects.OrdersPage;
import personal.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = {
			"MultiOrders" }, description = "Verify placing an order with all valid details")
	public void placingOrder(HashMap<String, String> input) throws IOException, InterruptedException {
		
		ProductCatalogue productCatalogue = landingPage.loginActions(input.get("email"), input.get("pass"));
		// Add desired product to cart
		productCatalogue.addProductToCart(input.get("pName"));

		// Go to cart page and verify product
		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductDisplay(input.get("pName"));
		Assert.assertTrue(match, "Product was not found in the cart!");

		// Proceed to checkout
		CheckoutPage checkOut = cartPage.goToCheckOutPage();
		checkOut.selectCountry("india");
		OrderConfirmation orderConfirmation = checkOut.placeOrder();

		// Verify order confirmation message
		String confirmMessage = orderConfirmation.getOrderConfirmation();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU for the order."),
				"Order confirmation message did not match!");
	}

	@Test(dependsOnMethods = { "placingOrder" }, description = "Verify desired product name displayed in Order details")
	public void verifyOrderDisplay() throws IOException, InterruptedException {
		ProductCatalogue productCatalogue = landingPage.loginActions("santoshsp17@gmai.com", "Gunnaa@123");
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay(productName),
				"The product was not displayed in the orders list!");
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		/*
		 * Example hardcoded data approach:
		 * 
		 * HashMap<String, String> map = new HashMap<>(); map.put("email",
		 * "santoshsp17@gmail.com"); map.put("pass", "Gunnaa@123"); map.put("pName",
		 * "ZARA COAT 3");
		 * 
		 * HashMap<String, String> map1 = new HashMap<>(); map1.put("email",
		 * "santoshsp17@gmail.com"); map1.put("pass", "Gunnaa@123"); map1.put("pName",
		 * "iphone 13 pro");
		 * 
		 * return new Object[][] { { map }, { map1 } };
		 */

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\personal\\TestData\\MultiOrder.json");

		Object[][] dataArray = new Object[data.size()][1];
		for (int i = 0; i < data.size(); i++) {
			dataArray[i][0] = data.get(i);
		}

		return dataArray;
	}
}
