package personal.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import personal.resources.ExtentReportsNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extent = ExtentReportsNG.getReportObject();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentThread = new ThreadLocal<>();

	@Override
	public void onStart(ITestContext context) {
		// Called before any test starts
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());

		// System.out.println("[" + LocalTime.now() + "] Starting " +
		// result.getMethod().getMethodName() + " on thread: "
		// + Thread.currentThread().getName());
		extentThread.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentThread.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = null;
		String filePath = null;

		extentThread.get().fail("Test Failed " + result.getMethod().getMethodName());
		extentThread.get().fail(result.getThrowable());

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}

		extentThread.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped: " + result.getName());

		Throwable cause = result.getThrowable();
		boolean isRetry = Boolean.TRUE.equals(result.getAttribute("isRetry"));

		String methodName = result.getMethod().getMethodName();
		String skipReason;
		
		if (isRetry) {
			// update skipReason for Retry scenario
			skipReason = "Retry Test Marked as Skipped: " + methodName;
		} else if (cause != null && cause.getMessage() != null
				&& cause.getMessage().contains("depends on not successfully finished methods")) {
			// update skipReason for Retry Dependency failure scenario
			skipReason = "Test Skipped due to dependency failure: " + methodName;
		} else if (cause != null && cause.getMessage() != null) {
			// update skipReason for Other throwable-based skips
			skipReason = "Test Skipped due to: " + cause.getMessage();
		} else {
			// update skipReason for Generic skip (no throwable or retry)
			skipReason = "Test Skipped: " + methodName;
		}

		// Log once
		extentThread.get().skip(skipReason);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test Partially Failed: " + result.getName());
	}
}
