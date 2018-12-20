package tests;

/**
 * Created by Pranav on 19/12/18.
 */

import Global.GlobalFunction;
import Pages.ZeplinLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZeplinCompareTest {

  WebDriver driver = new ChromeDriver();
  ZeplinLogin zeplinLoginPage = new ZeplinLogin(driver);
  GlobalFunction globalFunction = new GlobalFunction(driver);

  @BeforeClass
  public void setUp()
  {
    driver.get("https://app.zeplin.io/login");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void loginTest()
  {
    zeplinLoginPage.login("anupam_rai","Coviam@2018");
    zeplinLoginPage.verifyLogo();

  }

  @Test
  public void navigateToZeplin() throws IOException {
    zeplinLoginPage.navigateTodURL("https://app.zeplin.io/project/5b8aaeef38c95d7c1c3d8e2e/screen/5b8ab6221ff82a1293f061ae","zeplinHomePage");
  }


  @Test
  public void compareZepline() throws Exception {
    String fileOne = "/CodieCon/ImageCompare/src/main/resources/zeplinHomePage1.html";
    String filetwo = "/CodieCon/ImageCompare/src/main/resources/zeplinHomePage2.html";
    int value =globalFunction.compareFileText(fileOne,filetwo);
    assertThat("file matches",value,equalTo(1));
  }

  @Test
  public void compareZeplineImage() throws Exception {
    String fileOne = "/CodieCon/ImageCompare/src/main/resources/zeplinHomePage1.png";
    String filetwo = "/CodieCon/ImageCompare/src/main/resources/zeplinHomePage2.png";
    boolean value = globalFunction.testImageComparison(fileOne, filetwo);
    assertThat("file matches",value,equalTo(true));
  }

  @AfterClass
  public void tearDown() throws Exception {
    driver.quit();
  }
}
