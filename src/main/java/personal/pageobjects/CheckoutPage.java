package personal.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import personal.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor to initialize WebDriver and page elements
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ===== Web Elements =====

    // Input field to search for the country during checkout
    @FindBy(xpath = "//input[@placeholder='Select Country']")
    private WebElement searchBox;

    // Country selection result (2nd item in dropdown)
    @FindBy(css = ".ta-item:nth-of-type(2)")
    private WebElement selectCountry;

    // Button to place the order
    @FindBy(className = "action__submit")
    private WebElement placeOrder;

    // Locator for the country selection dropdown list
    By countrySelectionList = By.cssSelector(".ta-item");

    // ===== Page Methods =====

    /**
     * Selects the given country from the dropdown.
     *
     * @param countryName Name of the country to select
     */
    public void selectCountry(String countryName) {
        searchBox.sendKeys(countryName);
        waitForElementToBeAppear(countrySelectionList);
        selectCountry.click();
    }

    /**
     * Clicks on the 'Place Order' button and navigates to the Order Confirmation page.
     *
     * @return OrderConfirmation page object
     */
    public OrderConfirmation placeOrder() {
        placeOrder.click();
        return new OrderConfirmation(driver);
    }
}