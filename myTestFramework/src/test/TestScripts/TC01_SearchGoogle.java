package test.TestScripts;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import test.PageObject.googlePage;
import main.Framework.tests.BaseTest;
import org.openqa.selenium.support.PageFactory;
import test.Utils.extentReportsUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TC01_SearchGoogle extends BaseTest {

	googlePage GoogleSearchPage;

	String Path, reportsPath, reportsConfigPath, TCName, screenshotsPath;
	ExtentReports report;
	ExtentTest test;
	extentReportsUtil testReport = new extentReportsUtil();
	String TimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());


	@Before
	public void SetUp(){
		TCName = "TC01_SearchGoogle";
		Path= readProperty("Path");
		reportsPath= readProperty("reportsPath") + TCName + "_" + TimeStamp + ".html";
		reportsConfigPath = readProperty("reportsConfig");
		screenshotsPath = readProperty("screenshotsPath") + TCName + "_" + TimeStamp + "\\";
		openURL("chrome", "https://google.com");
		GoogleSearchPage = PageFactory.initElements(driver, googlePage.class);

		report = testReport.createReportFile(reportsPath,reportsConfigPath);
		test = report.startTest(TCName);
	}

	@Test
	public void TC01_SearchGoogle() {
		try {
			List<String> excelData = readExcelTestData(Path, "SearchGoogle", TCName);
			String SearchData = excelData.get(0);

			//Enter Text to Search Box
			boolean GoogleBoxIsPopulated = GoogleSearchPage.enterTextToGoogleBox(SearchData);
			testReport.testStatus(GoogleBoxIsPopulated, "Enter " + SearchData + " to Google Search box.", test, screenshotsPath, getDriver());

			//Verify if the Google Button is enabled
			boolean GoogleBtnIsEnabled = GoogleSearchPage.verifyGoogleButtonifEnabled();
			testReport.testStatus(GoogleBtnIsEnabled, "Verify if the Google Button is enabled.", test, screenshotsPath, getDriver());

            //Click GoogleButton
            boolean GoogleBtnIsClicked = GoogleSearchPage.clickGoogleButton();
            testReport.testStatus(GoogleBtnIsClicked, "Click Google Button.", test, screenshotsPath, getDriver());
            wait(5000);

            //Verify if Google Result exists
            boolean GoogleResultExists = GoogleSearchPage.verifyGoogleResult();
            testReport.testStatus(GoogleResultExists, "Verify if Google Result exists.", test, screenshotsPath, getDriver());

            //Mouse Over to Google Result
            boolean GoogleResultIsHovered = GoogleSearchPage.mouseOverGoogleResult();
            testReport.testStatus(GoogleResultIsHovered, "Mouse Over to Google Result", test, screenshotsPath, getDriver());
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