package Pages;

import Global.GlobalFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DevUiPage {
    private WebDriver driver;
    private GlobalFunction globalFunction = new GlobalFunction(driver);

    public DevUiPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public Map<String,String> getCssDevUI(){
        driver.navigate().to("https://flashsale-dev.gdn-app.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement masukDev = driver.findElement(By.xpath("//*[@id=\"blibli-main-ctrl\"]/div/header/div/div/div/div[2]/div/a"));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        String script = "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";
        String cssProp = executor.executeScript(script, masukDev).toString();
        return globalFunction.convertStringToMap(cssProp);
    }

}
