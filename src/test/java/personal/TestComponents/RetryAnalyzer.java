package personal.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int count = 0;
	int maxRetry = 1;

	@Override
	public boolean retry(ITestResult result) {
		// set isRetry to true
		result.setAttribute("isRetry", true);
		if (count < maxRetry) {
			count++;
			// Mark the test result as a retry
			return true;
		}

		return false;
	}

}
