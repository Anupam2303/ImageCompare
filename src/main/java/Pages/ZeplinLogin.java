package Pages;

import Global.GlobalFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ZeplinLogin {

    GlobalFunction globalFunction = new GlobalFunction();

    WebDriver driver;

    WebElement userNameField;
    WebElement passwordField;
    WebElement loginButton;
    WebElement zeplinLogo;
    String pageSource;

    public ZeplinLogin(WebDriver driver) {
        this.driver = driver;

    }

    public void fillUserName(String userName) {
        this.userNameField = driver.findElement(By.xpath("//input[@id='handle']"));
        userNameField.sendKeys(userName);
    }

    public void fillPassword(String password) {
        this.passwordField = driver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys(password);
    }

    public void login(String userName, String password) {
        this.fillUserName(userName);
        this.fillPassword(password);
        this.loginButton = driver.findElement(By.xpath("//form[@id='loginForm']//button"));
        loginButton.click();
    }

    public boolean verifyLogo() {

        this.zeplinLogo = driver.findElement(By.id("zeplin"));
        return zeplinLogo.isDisplayed();
    }

    public void navigateTodURL(String pagURL, String pageName) throws IOException {
        driver.navigate().to(pagURL);
        this.pageSource = driver.getPageSource();
        this.pageSource= driver.getPageSource();
        globalFunction.createFile(pageName,pageSource);
        WebElement element = driver.findElement(By.xpath("//*[@id=\"screenImage\"]"));
        String src = element.getAttribute("src");
        URL imageURL = new URL(src);
        globalFunction.createImageFile(pageName,imageURL);
    }

    public Map<String, String> getCssForElement() {
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement masukZeplin = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[9]"));
        return globalFunction.convertStringToMap(globalFunction.cssproperty(masukZeplin));
    }
}

