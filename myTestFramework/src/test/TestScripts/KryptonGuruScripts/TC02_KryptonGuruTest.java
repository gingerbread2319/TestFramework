package test.TestScripts.KryptonGuruScripts;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import main.Framework.tests.BaseTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import test.PageObject.KryptonGuru.kryptonGuruLoginPage;
import test.PageObject.googlePage;
import test.Utils.extentReportsUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TC02_KryptonGuruTest extends BaseTest {
    kryptonGuruLoginPage kryptonLogin;

    String Path, reportsPath, reportsConfigPath, TCName, screenshotsPath;
    ExtentReports report;
    ExtentTest test;
    extentReportsUtil testReport = new extentReportsUtil();
    String TimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    @Before
    public void SetUp(){
        TCName = "TC02_KryptonGuruTest";
        Path= readProperty("Path");
        reportsPath= readProperty("reportsPath") + TCName + "_" + TimeStamp + ".html";
        reportsConfigPath = readProperty("reportsConfig");
        screenshotsPath = readProperty("screenshotsPath") + TCName + "_" + TimeStamp + "\\";
        openURL("chrome", "http://play.krypton.infor.com/");
        kryptonLogin = PageFactory.initElements(driver, kryptonGuruLoginPage.class);

        report = testReport.createReportFile(reportsPath,reportsConfigPath);
        test = report.startTest(TCName);
    }

    @Test
    public void TC02_KryptonGuruTest() {
        try {
            List<String> excelData = readExcelTestData(Path, "KryptonGuru", TCName);
            String usernameText = excelData.get(0);
            String passwordText = excelData.get(1);

            //Login to Krypton Page
            boolean isLogintoKrypton = kryptonLogin.loginToKrypton(usernameText,passwordText);
            testReport.testStatus(isLogintoKrypton, "Login to Krypton Page", test, screenshotsPath, getDriver());
            wait(5000);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void TearDown(){
        closeBrowser(driver);
        testReport.endReport(report, test);
    }
}
