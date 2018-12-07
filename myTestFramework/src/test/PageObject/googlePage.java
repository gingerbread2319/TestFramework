package test.PageObject;

import main.Framework.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class googlePage extends BasePage {
    final WebDriver driver;


    @FindBy(name="q") public WebElement googleBox;
    @FindBy(name="btnK") public WebElement googleBtn;
    @FindBy(id="viewports") public WebElement googleArea;
    @FindBy(xpath="//h3[.='Cheese - Wikipedia']") public WebElement googleResult;
    @FindBy(xpath="(//h3[@role='heading'])[1]") WebElement topStories;
    //@FindBy(xpath="//div[.='Disabled state']/following::button[1]") WebElement disabledButton;

    public googlePage(WebDriver driver){
        this.driver = driver;
        System.out.println(this.driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public boolean enterTextToGoogleBox(String SearchText){
        boolean Status = false;

        try {
            checkExist(googleBox);
            String actStatus = typeText(googleBox, SearchText);
            if (actStatus.equalsIgnoreCase("true")) {
                Status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public boolean clickGoogleButton() {
        boolean Status = false;

        try {
            checkExist(googleBtn);
            checkExist(googleArea);
            click(googleArea);
            click(googleBtn);
            Status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public String getGoogleHeaderText() {
        String headerText = "";
        try {
            headerText = getText(topStories);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return headerText;
    }

    public boolean verifyGoogleResult()  {
        boolean Status = false;

        try {
            Status = checkExist(googleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public boolean verifyGoogleButtonifEnabled() {
        boolean Status = false;

        try {
           Status = checkifEnabled(googleBtn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }

    public boolean mouseOverGoogleResult() {
        boolean Status = false;

        try {
            hover(googleResult, driver);
            Status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Status;
    }
}
