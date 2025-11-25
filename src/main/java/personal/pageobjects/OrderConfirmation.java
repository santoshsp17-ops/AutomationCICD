package personal.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import personal.AbstractComponents.AbstractComponent;

public class OrderConfirmation extends AbstractComponent {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor to initialize WebDriver and page elements
    public OrderConfirmation(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Web element for the order confirmation message
    @FindBy(css = ".hero-primary")
    private WebElement orderConfirmation;

    /**
     * Returns the text of the order confirmation message displayed after placing an order.
     *
     * @return confirmation message as String
     */
    public String getOrderConfirmation() {
        return orderConfirmation.getText();
    }
}
