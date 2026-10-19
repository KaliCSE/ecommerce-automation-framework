package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseTest;

/*
 * Listener = test run aagumbodhu nadakkura result-ai kavanikkum.
 * Test FAIL aana neram screen-ai "screenshots" folder-la save pannum.
 * (Idhu optional. Venaam na testng.xml-la irundhu listener lines-ai remove pannalaam.)
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        if (!(testClass instanceof BaseTest)) {
            return;
        }
        WebDriver driver = ((BaseTest) testClass).getDriver();
        if (driver == null) {
            return;
        }
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path folder = Paths.get("screenshots");
            Files.createDirectories(folder);
            String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Path target = folder.resolve(result.getName() + "_" + time + ".png");
            Files.copy(source.toPath(), target);
            System.out.println("Screenshot saved: " + target.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Screenshot edukka mudiyala: " + e.getMessage());
        }
    }
}
