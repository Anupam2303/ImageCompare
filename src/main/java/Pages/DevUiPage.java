package Pages;

import Global.GlobalFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DevUiPage {
    private WebDriver driver;
    private GlobalFunction globalFunction = new GlobalFunction();

    public DevUiPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public Map<String,String> getCssDevUI(){
        String devUrl = "https://flashsale-dev.gdn-app.com";
        driver.navigate().to(devUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement masukDev = driver.findElement(By.xpath("//*[@id=\"blibli-main-ctrl\"]/div/header/div/div/div/div[2]/div/a"));
        globalFunction.cssproperty(masukDev);
        return globalFunction.convertStringToMap(globalFunction.cssproperty(masukDev));
    }
}
