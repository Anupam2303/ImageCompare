package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class getPageSource {
    public WebDriver driver;
    private String baseUrl;
//
//    @BeforeSuite
//    public void setUp() throws Exception {
//        System.setProperty("webdriver.chrome.driver", "/Personal/Drivers/chromedriver");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//    }
//
//    @AfterSuite
//    public void tearDown() throws Exception {
//        driver.quit();
//    }

    @Test
    public void TestPageSource() throws Exception {
        String File1 = "/Dev related/zeplinverification/src/main/resources/anupam.html";
        String File2 = "/Dev related/zeplinverification/src/main/resources/anupam2.html";
        compareFile(File1,File2);
        assertThat("file matches",compareFile(File1,File2),equalTo(1));
        System.out.println("file matches");
        getElementFromZeplin();
        getElementUI();
        System.out.println((getElementFromZeplin()));
        System.out.println(getElementUI());
    }

    public int compareFile(String fILE_ONE2, String fILE_TWO2)throws Exception
    {

        File f1 = new File(fILE_ONE2); //OUTFILE
        File f2 = new File(fILE_TWO2); //INPUT

        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = null;
        String line2 = null;
        int flag=1;
        while ((flag==1) &&((line1 = reader1.readLine()) != null)&&((line2 = reader2.readLine()) != null))
        {
            if (!line1.equalsIgnoreCase(line2))
                flag=0;
            else
                flag=1;
        }
        reader1.close();
        reader2.close();
        return flag;
    }

    private Map<String,String> getElementFromZeplin (){
        baseUrl = "https://app.zeplin.io/project/5b8aaeef38c95d7c1c3d8e2e/screen/5b8ab6221ff82a1293f061ae";
        driver.navigate().to(baseUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"handle\"]")).sendKeys("anupam_rai"+Keys.TAB);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]")).click();
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id=\"loginForm\"]")));
        actions.click().build().perform();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Coviam@2018");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/button")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"layers\"]/div[9]"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        String script = "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";
        String css = executor.executeScript(script, element1).toString();
        List<String> key = new ArrayList<String>();
        List<String> value = new ArrayList<String>();
        List<String> myList = new ArrayList<String>(Arrays.asList(css.split(";")));
        for (int i=0;i<myList.size();i++){
            String strkey = myList.get(i);
            String splitSubstring = ":";
            key.add(i,strkey.substring(0,strkey.indexOf(splitSubstring)));
            value.add(i,strkey.substring(strkey.indexOf(splitSubstring),strkey.length()));
        }
        Map<String,String> cssElementkeyValues = new LinkedHashMap<String,String>();
        for (int i=0;i<key.size();i++){
            cssElementkeyValues.put(key.get(i),value.get(i));
        }
        return cssElementkeyValues;
    }
    private Map<String,String> getElementUI (){
        baseUrl = "https://flashsale-dev.gdn-app.com";
        driver.navigate().to(baseUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement element1 = driver.findElement(By.xpath("//*[@id=\"blibli-main-ctrl\"]/div/header/div/div/div/div[2]/div/a"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        String script = "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";
        String css = executor.executeScript(script, element1).toString();
        List<String> key = new ArrayList<String>();
        List<String> value = new ArrayList<String>();
        List<String> myList = new ArrayList<String>(Arrays.asList(css.split(";")));
        for (int i=0;i<myList.size();i++){
            String strkey = myList.get(i);
            String splitSubstring = ":";
            key.add(i,strkey.substring(0,strkey.indexOf(splitSubstring)));
            value.add(i,strkey.substring(strkey.indexOf(splitSubstring),strkey.length()));
        }
        Map<String,String> cssElementkeyValues = new LinkedHashMap<String,String>();
        for (int i=0;i<key.size();i++){
            cssElementkeyValues.put(key.get(i),value.get(i));
        }
        return cssElementkeyValues;
    }

//    public boolean compareMap(Map<String, String> map1, Map<String, String> map2) {
//        boolean returnValue = false;
//        if (map1 == null || map2 == null)
//            return false;
//        for (String ch2 : map2.keySet()) {
//            if (map1.containsKey(ch2)) {
//                System.out.println("Value = " + map2.get(ch2));
//                returnValue = true;
//            }
//        }
//        return returnValue;
//    }

}
