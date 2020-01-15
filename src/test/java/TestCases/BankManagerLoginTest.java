package TestCases;

import Base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {

    @Test
    public void LogInAsBankManager() throws IOException, InterruptedException {

        //driver.findElement(By.xpath(OR.getProperty("btnBankManagerLogin"))).click();

        verifyEqual("abc", "xzy");

        click("btnBankManagerLogin_XPATH");
        Assert.assertTrue(IsElementPresent(By.xpath(OR.getProperty("btnAddCustomer_XPATH"))));


    }
}
