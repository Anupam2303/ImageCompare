package tests;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class ImageCompare {

    public WebDriver driver;
    private String baseUrl;

    @BeforeClass
    public void setUp() throws Exception {
        driver.quit();
        System.setProperty("webdriver.chrome.driver", "/Personal/Drivers/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "https://www.google.co.in/";
        driver.manage().window().maximize();
        driver.navigate().to(baseUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @BeforeClass
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testImageComparison(String file1,String file2) throws IOException, InterruptedException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Thread.sleep(3000);
        FileUtils.copyFile(screenshot, new File("/Personal/GoogleOutput.jpg"));

        File fileInput = new File(file1);
        File fileOutPut = new File(file2);

        BufferedImage bufferfileinput = ImageIO.read(fileInput);
        DataBuffer bufferfileInput = bufferfileinput.getData().getDataBuffer();
        int sizefileInput = bufferfileInput.getSize();
        BufferedImage bufferfileOutPut = ImageIO.read(fileOutPut);
        DataBuffer datafileOutPut = bufferfileOutPut.getData().getDataBuffer();
        int sizefileOutPut = datafileOutPut.getSize();
        boolean matchFlag = true;
        if (sizefileInput == sizefileOutPut) {
            for (int i = 0; i < sizefileInput; i++) {
                System.out.println(bufferfileInput.getElem(i));
                System.out.println(datafileOutPut.getElem(i));
                if (bufferfileInput.getElem(i) != datafileOutPut.getElem(i)) {
                    matchFlag = false;
                    break;
                }
            }
            Assert.assertTrue(matchFlag, "Images are same");

        } else {
            matchFlag = false;
            Assert.assertTrue(matchFlag, "Images are not same");
        }
    }
}
