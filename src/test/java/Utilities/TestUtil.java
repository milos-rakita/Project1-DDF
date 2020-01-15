package Utilities;

import Base.TestBase;
import org.apache.commons.collections4.functors.IfClosure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends TestBase {

    public static String ScreenshotPath;
    public static String ScreenshotName;

    public static void CaptureScreenshot() throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Date d = new Date();
        ScreenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

        FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/surefire-report/html/" + ScreenshotName));

    }

    @DataProvider(name = "dp")
    public Object[][] getData(Method m) {

        String sheetname = m.getName();

        int rows = excel.getRowCount(sheetname);
        int cols = excel.getColumnCount(sheetname);

        Object[][] data = new Object[rows - 1][cols];

        //Hashtable<String,String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

          //  table = new Hashtable<String,String>();

            for (int colNum = 0; colNum < cols; colNum++) {

            //    table.put(excel.getCellData(sheetname,colNum,1),excel.getCellData(sheetname,colNum,rowNum));
                data[rowNum - 2][colNum] = excel.getCellData(sheetname, colNum, rowNum);//table;
            }
        }
        return data;
    }

    public static boolean isTestRunnable(String testName, ExcelReader excel) {

        String sheetName = "TestSuite";
        int rows = excel.getRowCount(sheetName);

        for (int rNum = 2; rNum <= rows; rNum++) {
            String testCase = excel.getCellData(sheetName, "TCID", rNum);
            if (testCase.equalsIgnoreCase(testName)) {
                String runmode = excel.getCellData(sheetName, "Runmode", rNum);
                if (runmode.equalsIgnoreCase("Y")) {
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;
    }

}
