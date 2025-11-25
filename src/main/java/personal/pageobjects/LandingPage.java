package personal.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import personal.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	// Constructor to initialize WebDriver and page elements
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ===== PageFactory Web Elements =====

	@FindBy(id = "userEmail")
	WebElement userEmail; // Input field for user email

	@FindBy(css = ".invalid-feedback div")
	List<WebElement> userFieldErrorMessages; // Invalid Errors

	@FindBy(id = "userPassword")
	WebElement userPass; // Input field for user password

	@FindBy(id = "login")
	WebElement login; // Login button

	@FindBy(css = ".ng-animating")
	WebElement aninamation; // Animation element during loading

	@FindBy(css = "div[aria-label]")
	WebElement toastElement; // Toast message element

	// Locators
	By toastLocator = By.cssSelector(".toast-container div[aria-label]");
	By toast = By.cssSelector("#toast-container");

	// ===== Page Methods =====
	/**
	 * Performs login action using provided email and password.
	 * @return ProductCatalogue page object after successful login 
	 * @throws InterruptedException */
	
	public ProductCatalogue loginActions(String email, String pass) throws InterruptedException {
		userEmail.sendKeys(email);
		userPass.sendKeys(pass);
		actions.moveToElement(login).click().perform();
		//login.click();
		return new ProductCatalogue(driver);
	}

	// Navigates to the application login page.
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");
	}

	// @return Toast message text 
	
	public String getToastMessage() throws InterruptedException {
		waitForElementToBeAppear(toast);
		String message = toastElement.getAttribute("aria-label");
		waitForElementToDisappear(aninamation);
		return message;
	}
	
	// return userFieldErrorMessages for Email and Password
	
	public String getFieldErrorMessage(String fieldName) {
		String ErrorMessage = null;
		for (WebElement userError : userFieldErrorMessages) {
			if (userError.getText().contains(fieldName))
				return userError.getText();
		}
		return ErrorMessage;
	}
}