package tests;


import Global.GlobalFunction;
import Global.JavaEmail;
import Pages.ZeplinLogin;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZeplinCompareTest {

  private WebDriver driver = new ChromeDriver();
  private ZeplinLogin zeplinLoginPage = new ZeplinLogin(driver);
  private GlobalFunction globalFunction = new GlobalFunction(driver);
  private JavaEmail javaEmail = new JavaEmail();
  private static boolean notif;

  @BeforeClass
  public void setUp()
  {
    driver.get("https://app.zeplin.io/login");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void loginTest()
  {
    zeplinLoginPage.login("zeplinid","password");
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
    notif = value;
    assertThat("file matches",value,equalTo(true));
  }

  @Test
  public void sendmail(){
    if (notif)
      javaEmail.sendMail("Zeplin Version matched ","Keep calm and relax no need to change UI");
    else
      javaEmail.sendMail("Zeplin Version not matched ","Looks like UX has changed Zeplin mockup " +
              "Please look into it.");

  }

  @AfterClass
  public void tearDown() throws Exception {
    driver.quit();
  }
}
