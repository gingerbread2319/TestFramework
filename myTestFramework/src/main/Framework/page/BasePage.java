package main.Framework.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	Logger logger = Logger.getLogger("BasePage");
	public String typeText(WebElement element, String text) {
		String Status = "";
		try {
			element.sendKeys(text);
			logger.debug("Entering " + text + " to" + element + " element.");
			System.out.println("Entering " + text + " to" + element + " element.");
			Status = "true";
		} catch (NoSuchElementException e) {
			Status = "false";
			logger.debug(e);
			e.printStackTrace();
		} catch (Exception e) {
			Status = "false";
			logger.debug(e);
			e.printStackTrace();
		}

		return Status;
	}

	public void rightClick(WebElement element, WebDriver driver) {
		try {
			Actions oAction = new Actions(driver);
			oAction.moveToElement(element);
			oAction.contextClick(element).build().perform();
			logger.debug("Sucessfully Right clicked the element"  + element);
			System.out.println("Sucessfully Right clicked the element"  + element);
		} catch (NoSuchElementException e) {
			logger.debug(e);
			e.printStackTrace();
		}
	}

	public void hover(WebElement element, WebDriver driver) {
		try {
			Actions oAction = new Actions(driver);
			oAction.moveToElement(element).build().perform();;
			logger.debug("Sucessfully hovered the element"  + element);
			System.out.println("Sucessfully hovered the element"  + element);
		} catch (NoSuchElementException ignored) {
			logger.debug("Object " + element + " does not exists.");
			System.out.println("Object " + element + " does not exists.");
			//e.printStackTrace();
		}
	}

	public void click(WebElement element) {
		try {
			element.click();
			logger.debug("Sucessfully clicked the element " + element);
			System.out.println("Sucessfully clicked the element " + element);
		} catch (NoSuchElementException e) {
			logger.debug(e);
			e.printStackTrace();
		}
	}

	public String getText(WebElement element) {

		String elementText = "";

		try{
			elementText = element.getText();
			logger.debug("Getting the text of the element " + element);
			System.out.println("Getting the text of the element " + element);
		} catch (NoSuchElementException e) {
			logger.debug(e);
			e.printStackTrace();
		}

		return elementText;
	}

	public boolean checkExist(WebElement element) {
		boolean status = false;
		try {
			if ((element.getSize().height > 0) && (element.getSize().width > 0)){
				status = true;
				logger.debug("Object " + element + " exists.");
				System.out.println("Object " + element + " exists.");
			} else {
				logger.debug("Object " + element + " does not exists.");
				System.out.println("Object " + element + " does not exists.");
			}

		} catch (NoSuchElementException ignored) {
			logger.debug("Object " + element + " does not exists.");
			System.out.println("Object " + element + " does not exists.");
			return status;
		}

		return status;
	}

	public boolean checkifEnabled(WebElement element) {
		boolean status = false;
		try {
			if (element.isEnabled()){
				status = true;
				logger.debug("Object " + element + " is enabled.");
				System.out.println("Object " + element + " is enabled.");
			} else {
				logger.debug("Object " + element + " is disabled.");
				System.out.println("Object " + element + " is disabled.");
			}

		} catch (NoSuchElementException ignored) {
			logger.debug("Object " + element + " does not exists.");
			System.out.println("Object " + element + " does not exists.");
			return status;
		}

		return status;
	}

	public boolean checkifSelected(WebElement element) {
		boolean status = false;
		try {
			if (element.isSelected()){
				status = true;
				logger.debug("Object " + element + " is selected.");
				System.out.println("Object " + element + " is selected.");
			} else {
				logger.debug("Object " + element + " is not selected.");
				System.out.println("Object " + element + " not selected.");
			}

		} catch (NoSuchElementException ignored) {
			logger.debug("Object " + element + " does not exists.");
			System.out.println("Object " + element + " does not exists.");
			return status;
		}

		return status;
	}
}
