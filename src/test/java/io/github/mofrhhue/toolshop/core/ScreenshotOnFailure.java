package io.github.mofrhhue.toolshop.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** Saves a screenshot to target/screenshots when a test fails. CI uploads the folder. */
public class ScreenshotOnFailure implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.get();
        if (!(driver instanceof TakesScreenshot)) {
            return;
        }
        try {
            Path folder = Paths.get("target", "screenshots");
            Files.createDirectories(folder);
            String name = result.getTestClass().getRealClass().getSimpleName() + "_" + result.getName() + ".png";
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(folder.resolve(name), png);
        } catch (IOException | RuntimeException e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }
    }
}
