package io.github.mofrhhue.toolshop.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;

/** Category page reached from the "Categories" menu, e.g. /category/hand-tools. */
public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        return visible(testId("page-title")).getText().trim();
    }

    public List<String> productNames() {
        visible(testId("product-name"));
        return texts(testId("product-name"));
    }
}
