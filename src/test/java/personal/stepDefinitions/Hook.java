package personal.stepDefinitions;

import io.cucumber.java.After;
import personal.TestComponents.BaseTest;

public class Hook extends BaseTest {

	@After
	public void tearDownScenario() {

		if (driver != null) {
			driver.quit();
		}
	}
}
