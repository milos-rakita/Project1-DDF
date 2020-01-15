package TestCases;

import Base.TestBase;
import Utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void AddCustomerTest(String firstName, String lastName, String postCode, String alertText, String Runmode) {


        click("btnAddCustomer_XPATH");
        type("lblFistName_XPATH", firstName);
        type("lblLastName_XPATH", firstName);
        type("lblPostCode_XPATH", postCode);
        click("btnAdd_XPATH");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alertText));//"Customer added successfully"
        alert.accept();

    }

}
/*
        driver.findElement(By.xpath(OR.getProperty("btnAddCustomer"))).click();
        driver.findElement(By.xpath(OR.getProperty("lblFistName"))).sendKeys("Sima");
        driver.findElement(By.xpath(OR.getProperty("lblLastName"))).sendKeys("Simic");
        driver.findElement(By.xpath(OR.getProperty("lblPostCode"))).sendKeys("PC123");
        driver.findElement(By.xpath(OR.getProperty("btnAdd"))).click();

 */
/*
        click("btnAddCustomer_XPATH");
        type("lblFistName_XPATH", "Pera");
        type("lblLastName_XPATH", "PEric");
        type("lblPostCode_XPATH", "PC123456789");
        click("btnAdd_XPATH");

  */