package io.github.mofrhhue.toolshop.pages;

import java.util.List;
import java.util.function.Function;

import io.github.mofrhhue.toolshop.core.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Shared helpers. The shop marks its elements with data-test attributes, which make stable locators. */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Config.timeout());
    }

    protected static By testId(String id) {
        return By.cssSelector("[data-test='" + id + "']");
    }

    protected void openPath(String path) {
        driver.get(Config.baseUrl() + path);
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement field = visible(locator);
        field.clear();
        field.sendKeys(text);
    }

    protected List<String> texts(By locator) {
        return driver.findElements(locator).stream().map(e -> e.getText().trim()).toList();
    }

    /** Waits until a condition on the page is true, for example "the list is sorted". */
    public <T> T waitUntil(Function<WebDriver, T> condition) {
        return wait.until(condition);
    }

    /** Converts "$1,234.50" to 1234.50. */
    public static double money(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
