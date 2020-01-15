package Base;

import Utilities.ExcelReader;
import Utilities.ExtentManager;
import Utilities.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    //Stao sam na lekciji 8
    /* In this class we initialise next element
     *   WebDriver + Done
     *   Properties + Done
     *   Logs - log4.jar - in pom.xml add, .log file in project add, log4j.propertoes ,Loger class
     *   ExtentReports - OBAVEZNO DOBRO PROCI DA SE MOZE UPOTREBITI !!! 12 i 13 lekcije
     *   DB
     *   Excel
     *   Mail
     *   ReportNG, ExtentReport
     *   Jenkins
     *
     */

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    private static String DirUser = System.getProperty("user.dir");
    private static String PathResources = "/src/test/resources/";
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(DirUser+"/src/test/resources/excel/TestData.xlsx");//(DirUser + PathResources + "excel/TestData.xls");
    public static WebDriverWait wait;
    public ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;


    @BeforeSuite
    public void SetUp() throws IOException {
        if (driver == null) {
            fis = new FileInputStream(DirUser + PathResources + "properties/Config.properties");
            config.load(fis);
            log.debug("Congfig file loaded!!!");

            fis = new FileInputStream(DirUser + PathResources + "properties/OR.properties");
            OR.load(fis);
            log.debug("OR file loaded!!!");

            if (config.getProperty("browser").equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", DirUser + PathResources + "executables/gecko");
                driver = new FirefoxDriver();
                log.debug("Firefox driver is open");
            } else if (config.getProperty("browser").equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/executables/chromedriver");
                driver = new ChromeDriver();
                log.debug("Chrome driver is open");
            } else if (config.getProperty("browser").equals("opera")) {
                System.setProperty("webdriver.opera.driver", DirUser + PathResources + "executables/operadriver");
                driver = new OperaDriver();
                log.debug("Opera driver is open");
            }


            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigate to> " + config.getProperty("testsiteurl"));

            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);

        }
    }

    public void click(String locator) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }

        test.log(LogStatus.INFO, "Clicking ON: " + locator);
    }

    static WebElement ddl;

    public void select(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            ddl = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            ddl =  driver.findElement(By.xpath(OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            ddl = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(ddl);
        select.selectByVisibleText(value);

        test.log(LogStatus.INFO, "Selecting from ddl : " + locator + " valued as " + value);
    }

    public void type (String locator,String value){
        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        test.log(LogStatus.INFO, "Tiping in: " + locator + " entered valued as " + value);

    }

    public boolean IsElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public  static  void  verifyEqual(String expected, String actual) throws IOException {
        try {
            Assert.assertEquals(actual,expected);
        }catch (Throwable t){
            TestUtil.CaptureScreenshot();
            //ReportNG
            Reporter.log("</br>" + "Verification failure: "+t.getMessage()+"</br>");
            Reporter.log("<a target='_blank' href="+TestUtil.ScreenshotName+"><img src="+TestUtil.ScreenshotName+"height=200 width=200></img></a>");

            //Extend Report
            test.log(LogStatus.FAIL,"Verification failure with exception: "+t.getMessage());
            test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.ScreenshotName));

        }
    }

    @AfterSuite
    public void TesrDown() {
        if (driver != null) {
            driver.quit();
            log.debug("Driver quit");
        }
    }


}
