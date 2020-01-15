package TestCases;

import Base.TestBase;
import Utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class,dataProvider = "dp")
    public void OpenAccountTest(String customer, String currency,String alertText) {

        click("btnOpenAccount_XPATH");
        select("ddlCustomer_ID",customer);
        select("ddlCurrency_ID",currency);
        click("btnProcess_XPATH");
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alertText));//"Customer added successfully"
        alert.accept();

    }
//Account created successfully

}
