package personal.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsNG {

    public static ExtentReports getReportObject() {
    	
    	//report generation with ExtentReports & ExtentSparkReporter
        // Define report path 
        String path = System.getProperty("user.dir") + "\\ExtentReports\\Test_Results.html";
        
        // Create and configure Spark Reporter
        ExtentSparkReporter spark = new ExtentSparkReporter(path);
        spark.config().setReportName("Web Automation Results");
        spark.config().setDocumentTitle("Test Results");

        // Create ExtentReports instance and attach the reporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Santosh Patil");

        return extent;
    }
}
