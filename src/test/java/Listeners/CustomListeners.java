package Listeners;

import Base.TestBase;
import Utilities.TestUtil;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        test = rep.startTest(result.getName().toUpperCase());

        //runmodes - must frst check  = Y
        if (!TestUtil.isTestRunnable(result.getName(), excel)) {
            throw new SkipException("Skipping the test: " + result.getName().toUpperCase() + " as the Run mode is not Y");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(LogStatus.PASS, result.getName().toUpperCase() + "Pass");
        rep.endTest(test);
        rep.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        try {
            TestUtil.CaptureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        test.log(LogStatus.FAIL, result.getName().toUpperCase() + "Failed with exception: " + result.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.ScreenshotName));

        Reporter.log("Capturing screenshot");
        Reporter.log("<a target='_blank' href=" + TestUtil.ScreenshotName + ">ScreenShot </a>");
        Reporter.log("</br>");
        Reporter.log("<a target='_blank' href=" + TestUtil.ScreenshotName + "><img src=" + TestUtil.ScreenshotName + "height=200 width=200></img></a>");
        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped the tes as the Run mode in NO ");
        rep.endTest(test);
        rep.flush();

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
