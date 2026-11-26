package io.github.mofrhhue.toolshop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/** Home page: product grid, search, sorting and category filters. */
public class HomePage extends BasePage {

    private static final By SEARCH_INPUT = testId("search-query");
    private static final By SEARCH_BUTTON = testId("search-submit");
    private static final By SEARCH_CAPTION = testId("search-caption");
    private static final By NO_RESULTS = testId("no-results");
    private static final By SORT = testId("sort");
    private static final By PRODUCT_NAME = testId("product-name");
    private static final By PRODUCT_PRICE = testId("product-price");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        openPath("/");
        visible(PRODUCT_NAME);
        return this;
    }

    public HomePage search(String term) {
        type(SEARCH_INPUT, term);
        click(SEARCH_BUTTON);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(SEARCH_CAPTION, term));
        return this;
    }

    public HomePage sortBy(String value) {
        new Select(visible(SORT)).selectByValue(value);
        return this;
    }

    /** Ticks the category checkbox whose label is exactly the given text, e.g. "Pliers". */
    public HomePage filterByCategory(String label) {
        click(By.xpath("//input[@type='checkbox'][normalize-space(..)='" + label + "']"));
        return this;
    }

    public List<String> productNames() {
        return texts(PRODUCT_NAME);
    }

    public List<Double> productPrices() {
        return texts(PRODUCT_PRICE).stream().map(BasePage::money).toList();
    }

    public String noResultsMessage() {
        return visible(NO_RESULTS).getText().trim();
    }

    public ProductPage openProduct(String name) {
        click(By.xpath("//*[@data-test='product-name'][normalize-space()='" + name + "']"));
        return new ProductPage(driver);
    }

    public CategoryPage openCategoryFromMenu(String menuItemTestId) {
        click(testId("nav-categories"));
        click(testId(menuItemTestId));
        return new CategoryPage(driver);
    }
}
