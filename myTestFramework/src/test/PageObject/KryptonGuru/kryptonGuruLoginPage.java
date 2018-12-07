package test.PageObject.KryptonGuru;

import main.Framework.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class kryptonGuruLoginPage extends BasePage {
    final WebDriver driver;

    @FindBy(id="username") public WebElement usernameTextbox;
    @FindBy(id="password") public WebElement passwordTextbox;
    @FindBy(id="submit") public WebElement submitBtn;


    public kryptonGuruLoginPage(WebDriver driver) {
        this.driver = driver;
        System.out.println(this.driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

     public boolean loginToKrypton(String Username, String Password){
        boolean Status = false;
        try {
            typeText(usernameTextbox, Username);
            typeText(passwordTextbox, Password);
            click(submitBtn);
            Status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Status;
     }


}
