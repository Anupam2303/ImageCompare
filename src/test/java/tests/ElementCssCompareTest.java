package tests;

import Global.GlobalFunction;
import Pages.DevUiPage;
import Pages.ZeplinLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ElementCssCompareTest {
    private WebDriver driver = new ChromeDriver();
    private GlobalFunction globalFunction = new GlobalFunction(driver);
    private DevUiPage devUiPage = new DevUiPage(driver);
    private ZeplinLogin zeplinLogin = new ZeplinLogin(driver);
    private Map<String, String> test = new LinkedHashMap<String, String>();

    @BeforeClass
    public void setUp() {
        driver.get("https://flashsale-dev.gdn-app.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void compareCssvalues() throws IOException {
        Map<String, String> devUiPageCss = devUiPage.getCssDevUI();
        Map<String, String> zeplinLoginCss = zeplinLogin.getCssForElement();
        Map<String, String> simmilar = globalFunction.returnSimilar(zeplinLoginCss, devUiPageCss);
        Map<String, String> different = globalFunction.returnDifferent(zeplinLoginCss, devUiPageCss);
        globalFunction.createOutput(simmilar,different);
        test = simmilar;
    }

    @Test
    public void matchyourcss(){
        assertThat("file matches",test.containsKey("background-clip"),equalTo(true));
    }
}
