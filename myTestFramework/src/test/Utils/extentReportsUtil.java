package test.Utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import main.Framework.tests.BaseTest;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class extentReportsUtil extends BaseTest {

    public ExtentReports createReportFile(String reportsPath, String reportsConfigPath) {
        ExtentReports extent;
        extent = new ExtentReports (reportsPath, false);
        extent.loadConfig(new File(reportsConfigPath));
        return extent;
    }

    public void endReport(ExtentReports report, ExtentTest test){
        report.flush();
        report.endTest(test);
        report.close();
    }

    public void testStatus(boolean testStatus, String description, ExtentTest test, String screenshotPath, WebDriver wd) {
        if (testStatus == true) {
            test.log(LogStatus.PASS, description);
        } else {
            String FileName = "Screenshot_" + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + ".jpg";
            String Snapshot = screenshotPath + FileName;
            capturePageScreenshot(screenshotPath, FileName, wd);
            test.log(LogStatus.FAIL, description + "\n"  + test.addScreenCapture(Snapshot));
        }

    }


}
