package personal.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import personal.TestComponents.BaseTest;
import personal.TestComponents.RetryAnalyzer;

public class ErrorValidationTest extends BaseTest {

	@Test
	public void loginErrorValidation() throws IOException, InterruptedException {
		// Attempt login with invalid credentials
		landingPage.loginActions(prop.getProperty("userEmail"), prop.getProperty("userPassword") + "12");

		String actualMessage = landingPage.getToastMessage();
		Assert.assertEquals(actualMessage, "Incorrect email or password.", "Error message did not match!");
	}

	@Test (groups={"ErrorValidationForEmptyFields"}, retryAnalyzer=RetryAnalyzer.class)
	public void loginErrorValidationForEmptyFields() throws IOException, InterruptedException {
		// Attempt login with invalid credentials (duplicate test)
		landingPage.loginActions("", "");
		Assert.assertEquals(landingPage.getFieldErrorMessage("Email"), "*Email is required");
		Assert.assertEquals(landingPage.getFieldErrorMessage("Password"), "*Password is required");
	}
}
