package tests;

import Global.GlobalFunction;
import Pages.ZeplinLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class ElementCssCompareTest {
    WebDriver driver = new ChromeDriver();
    GlobalFunction globalFunction = new GlobalFunction();

    @BeforeSuite
    public void setUp()
    {
        driver.get("https://app.zeplin.io/login");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
