package personal.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import personal.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
	public Properties prop = new Properties();

	// Initialize WebDriver based on browser property
	public WebDriver initializeDriver() throws IOException {

		if (prop.isEmpty()) {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\main\\java\\personal\\resources\\GlobalData.properties");
			prop.load(fis);
		}

		// String browserName = prop.getProperty("browser");
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("firefox")) {
			// Initialize the Firefox WebDriver
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\Santosh Patil\\OneDrive\\Documents\\geckodriver-v0.36.0-win32\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browserName.contains("chrome")) {
			// Initialize the Chrome WebDriver
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("--headless"); // modern headless - addArguments("--headless=new");
				options.addArguments("--window-size=1920,1080"); // ensures element visible
				// driver.manage().window().setSize(new Dimension(1920,1080));
			}
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("edge")) {
			// Initialize the Edge WebDriver
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	// Read JSON file and convert to List of HashMaps
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
	}

	// Capture and return screenshot path
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "\\ExtentReports\\" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return "./" + testCaseName + ".png";
	}

	// Load properties before running tests
	@BeforeClass(alwaysRun = true)
	public void loadGlobalData() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\personal\\resources\\GlobalData.properties");
		prop.load(fis);
	}

	// Launch application before each test
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	// Quit driver after each test
	@AfterMethod(alwaysRun = true)
	public void tearDownAfterMethod() {
		if (driver != null) {
			driver.quit();
		}
		
	}
	
	
}