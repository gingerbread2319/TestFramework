package main.Framework.tests;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runners.model.TestClass;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.apache.commons.io.FileUtils;


public class BaseTest {
	public WebDriver driver = null;

	Logger logger = Logger.getLogger("BaseTest");

	public void openURL(String BrowserType, String URL) {
		String path = "";
		try {
			if ("chrome".equals(BrowserType)) {
				path = readProperty("chromedriver");
				System.setProperty("webdriver.chrome.driver", path);
				logger.debug("Navigating to " + URL + " via Google Chrome.");
				System.out.println("Navigating to " + URL + " via Google Chrome.");
				driver = new ChromeDriver();
				driver.get(URL);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			} else if ("firefox".equals(BrowserType)) {
				path = readProperty("firefoxdriver");
				System.setProperty("webdriver.gecko.driver", path);
				logger.debug("Navigating to " + URL + " via Firefox.");
				System.out.println("Navigating to " + URL + " via Firefox.");
				driver = new FirefoxDriver();
				driver.get(URL);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			} else if ("ie".equals(BrowserType)) {
				path = readProperty("IEdriver");
				System.setProperty("webdriver.ie.driver", path);
				logger.debug("Navigating to " + URL + " via Internet Explorer.");
				System.out.println("Navigating to " + URL + " via Internet Explorer.");
				driver = new InternetExplorerDriver();
				driver.get(URL);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wait(int milliseconds){
		try {
			Thread.sleep(milliseconds);
			logger.debug("Wait for " + milliseconds + "ms.");
			System.out.println("Wait for " + milliseconds + "ms.");
		} catch (InterruptedException e) {
			logger.debug(e);
			e.printStackTrace();
		}
	}


	public WebDriver getDriver() {
		return driver;
	}

	public void closeBrowser(WebDriver wd) {
		try {
			wd.close();
			wd.quit();
			logger.debug("Closing the Browser...");
			System.out.println("Closing the Browser...");
		} catch (Exception e){
			e.printStackTrace();
		}

	}

	public String readProperty(String PropertyName) {
		Properties prop = new Properties();
		InputStream input = null;
		String propValue = null;
		try {

			input = new FileInputStream("C:\\Users\\larellano\\IdeaProjects\\myTestFramework\\properties\\user.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			propValue = prop.getProperty(PropertyName);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return propValue;
	}

	public List<String> readExcelTestData(String FilePath, String SheetNm, String testCaseName) {
		List<String> TestDataCell = new ArrayList<String>();
		try {
			//HashMap<String,List<String>> TestData = new HashMap<String, List<String>>();
			FileInputStream excelFile = new FileInputStream(new File(FilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheet(SheetNm);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				String testCaseNameCell = currentRow.getCell(1).getStringCellValue();
				if (testCaseNameCell.equalsIgnoreCase(testCaseName)) {
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						String currentCellValue = currentCell.getStringCellValue();
						if (currentCell.getColumnIndex() >=2) {
							TestDataCell.add(currentCellValue);
						}
						//System.out.println(currentCell.getColumnIndex());
						//TestDataCell.add(i, currentCellValue);
					}
					//TestData.put(testCaseNameCell,TestDataCell);
				}
			}
			//System.out.println(Arrays.asList(TestDataCell));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(Arrays.asList(TestDataCell));
		return TestDataCell;
	}

	public void writeToExcelTestData(String FilePath, String SheetNm, String testCaseName, int columnIndex, String cellValue) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(FilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheet(SheetNm);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				String testCaseNameCell = currentRow.getCell(1).getStringCellValue();
				if (testCaseNameCell.equalsIgnoreCase(testCaseName)) {
					currentRow.createCell(columnIndex).setCellValue(cellValue);
				}
			}
			FileOutputStream out = new FileOutputStream(new File(FilePath));
			workbook.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToExcelTestData(String FilePath, String SheetNm, String testCaseName, String columnName, String cellValue) {
		try {
			FileInputStream excelFile = new FileInputStream(new File(FilePath));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheet(SheetNm);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int columnIndex = 0;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				String testCaseNameCell = currentRow.getCell(1).getStringCellValue();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					String currentCellValue = currentCell.getStringCellValue();
					if (currentCellValue.equalsIgnoreCase(columnName)) {
						columnIndex = currentCell.getColumnIndex();
					}
				}

				if (testCaseNameCell.equalsIgnoreCase(testCaseName)) {
					currentRow.createCell(columnIndex).setCellValue(cellValue);
				}
			}

			FileOutputStream out = new FileOutputStream(new File(FilePath));
			workbook.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchToFrame(String frameName) {
		try {
			driver.switchTo().frame(frameName);
			logger.debug("Switching to frame " + frameName);
			System.out.println("Switching to frame " + frameName);
		} catch (Exception e){
			logger.debug("Failed to switch to frame " + frameName);
			System.out.println("Failed to switch to frame " + frameName);
			e.printStackTrace();
		}

	}

	public void refreshBrowser(WebDriver wd) {
		try {
			wd.navigate().refresh();
			logger.debug("Refreshing the Browser..");
			System.out.println("Refreshing the Browser..");
		} catch (Exception e){
			logger.debug("Failed to refresh the browser");
			System.out.println("Failed to refresh the browser");
			e.printStackTrace();
		}
	}

	public void goBack(WebDriver wd) {
		try {
			wd.navigate().back();
			logger.debug("Navigating to the previous page...");
			System.out.println("Navigating to the previous page...");
		} catch (Exception e){
			logger.debug("Failed in navigating to the previous page.");
			System.out.println("Failed in navigating to the previous page.");
			e.printStackTrace();
		}
	}

	public void goNext(WebDriver wd) {
		try {
			wd.navigate().forward();
			logger.debug("Navigating to the next page...");
			System.out.println("Navigating to the next page...");
		} catch (Exception e){
			logger.debug("Failed in navigating to the next page.");
			System.out.println("Failed in navigating to the next page.");
			e.printStackTrace();
		}
	}

	public void capturePageScreenshot(String screenshotPath, String FileName , WebDriver wd) {

		try {
			File screenshotFile = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(screenshotPath + FileName));
		} catch(IOException e) {
			System.out.println("Failed in taking screenshot on page.");
			e.printStackTrace();
		}

	}

	public String compareText(String expectedText, String ActualText, String compareMethod) {
		String compareResult = "";
		switch(compareMethod.toUpperCase()) {
			case "EXACTLYEQUAL" :
				if (ActualText.equals(expectedText)) {
					compareResult = "Matched";
				} else {
					compareResult = "Not Matched";
				}
				break;
			case "CONTAINS" :
				if (ActualText.contains(expectedText)) {
					compareResult = "Matched";
				} else {
					compareResult = "Not Matched";
				}
				break;
			case "TEXTEQUAL" :
				if (ActualText.equalsIgnoreCase(expectedText)) {
					compareResult = "Matched";
				} else {
					compareResult = "Not Matched";
				}
		}

		return compareResult;
	}




}
