package tests;

/**
 * Created by Pranav on 19/12/18.
 */

import Pages.Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {

  WebDriver driver = new ChromeDriver();
  Login loginPage = new Login(driver);

  @BeforeSuite
  public void setUp()
  {
    driver.get("https://app.zeplin.io/login");
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

  }

  @org.testng.annotations.Test
  public void loginTest()
  {
    loginPage.login("pranav.ks","1");
    loginPage.verifyLogo();

  }

  @org.testng.annotations.Test
  public void navigate() throws IOException {
    loginPage.navigateTodURL("https://app.zeplin.io/project/5b8aaeef38c95d7c1c3d8e2e/screen/5b8ab6229ce544493b9a432d","homePage");
  }

  @AfterSuite
  public void tearDown() throws Exception {
    driver.quit();
  }
}
