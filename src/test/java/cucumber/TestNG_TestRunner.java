package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/cucumber/", glue = "personal.stepDefinitions", monochrome = true, plugin = {
		"html:target/cucumber.html" } // , tags = "@AddPlace or @DeletePlace"
)

public class TestNG_TestRunner extends AbstractTestNGCucumberTests {

}
