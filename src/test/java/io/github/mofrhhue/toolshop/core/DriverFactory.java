package io.github.mofrhhue.toolshop.core;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Creates one Chrome browser per test and keeps it in a ThreadLocal,
 * so tests can run in parallel without sharing a browser.
 * Selenium Manager downloads the matching chromedriver automatically.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver start() {
        ChromeOptions options = new ChromeOptions();
        if (Config.headless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1400,1000", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        ChromeDriver driver = new ChromeDriver(options);
        if (Config.headless()) {
            useNormalUserAgent(driver);
        }
        DRIVER.set(driver);
        return driver;
    }

    /**
     * Headless Chrome reports itself as "HeadlessChrome" in its user agent, and some sites
     * (including the demo shop) do not serve pages to it. This keeps the real browser version
     * but sends the same user agent as normal Chrome.
     */
    private static void useNormalUserAgent(ChromeDriver driver) {
        String userAgent = (String) driver.executeCdpCommand("Browser.getVersion", Map.of()).get("userAgent");
        driver.executeCdpCommand("Emulation.setUserAgentOverride",
                Map.of("userAgent", userAgent.replace("HeadlessChrome", "Chrome")));
    }

    public static WebDriver get() {
        return DRIVER.get();
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
