package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Pranav on 19/12/18.
 */
public class Login {

  WebDriver driver;

  WebElement userNameField;
  WebElement passwordField;
  WebElement loginButton;
  WebElement zeplinLogo;
  String pageSource;

  public Login(WebDriver driver)
  {
    this.driver = driver;

  }

  public void fillUserName(String userName)
  {
    this.userNameField = driver.findElement(By.xpath("//input[@id='handle']"));
    userNameField.sendKeys(userName);
  }
  public void fillPassword(String password)
  {
    this.passwordField = driver.findElement(By.xpath("//input[@id='password']"));
    passwordField.sendKeys(password);
  }
  public void login(String userName, String password)
  {
    this.fillUserName(userName);
    this.fillPassword(password);
    this.loginButton = driver.findElement(By.xpath("//form[@id='loginForm']//button"));
    loginButton.click();
  }

  public boolean verifyLogo()
  {

    this.zeplinLogo = driver.findElement(By.id("zeplin"));
    if(zeplinLogo.isDisplayed())
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public void createFile(String pageName, String pageSource) throws IOException {
    StringBuilder fileName1 = new StringBuilder("src/main/resources/");
    fileName1.append(pageName);
    fileName1.append("1.html");
    StringBuilder filename2 = new StringBuilder("src/main/resources/");
    filename2.append(pageName);
    filename2.append("2.html");
    File sourceFile1 = new File(fileName1.toString());
    File sourceFile2 = new File(filename2.toString());
    BufferedWriter writeSource;
    FileWriter fileWrite;

  if(!sourceFile1.exists() && !sourceFile2.exists())
    {
       sourceFile1.createNewFile();
       fileWrite = new FileWriter(sourceFile1);
       writeSource = new BufferedWriter(fileWrite);
       writeSource.write(pageSource);
    }
    else if(sourceFile1.exists() && !sourceFile2.exists() )
    {
      sourceFile2.createNewFile();
      fileWrite = new FileWriter(sourceFile2);
      writeSource = new BufferedWriter(fileWrite);
      writeSource.write(pageSource);
   }
   else if(sourceFile1.exists() && sourceFile2.exists())
    {
      sourceFile2.renameTo(sourceFile1);
      fileWrite = new FileWriter(sourceFile2);
      writeSource = new BufferedWriter(fileWrite);
      writeSource.write(pageSource);

   }

  }
  public void navigateTodURL(String pagURL,String pageName) throws IOException {
    driver.navigate().to(pagURL);
    String fileName;
     this.pageSource= driver.getPageSource();
     this.createFile(pageName,this.pageSource);
    System.out.println(pageSource);

  }
}

