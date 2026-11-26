package io.github.mofrhhue.toolshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Product detail page. */
public class ProductPage extends BasePage {

    private static final By NAME = By.cssSelector("h1[data-test='product-name']");
    private static final By PRICE = testId("unit-price");
    private static final By QUANTITY = testId("quantity");
    private static final By INCREASE = testId("increase-quantity");
    private static final By ADD_TO_CART = testId("add-to-cart");
    private static final By CART_BADGE = testId("cart-quantity");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String name() {
        return visible(NAME).getText().trim();
    }

    public double price() {
        return money(visible(PRICE).getText());
    }

    public ProductPage increaseQuantity() {
        click(INCREASE);
        return this;
    }

    public String quantity() {
        return visible(QUANTITY).getDomProperty("value");
    }

    public ProductPage addToCart() {
        click(ADD_TO_CART);
        return this;
    }

    /** Waits for the confirmation pop-up and returns its text. */
    public String confirmationMessage() {
        return visible(By.xpath("//*[contains(normalize-space(.), 'Product added to shopping cart')]"
                + "[not(*[contains(normalize-space(.), 'Product added to shopping cart')])]")).getText().trim();
    }

    public ProductPage waitForCartBadge(String count) {
        wait.until(ExpectedConditions.textToBe(CART_BADGE, count));
        return this;
    }

    public CartPage openCart() {
        click(testId("nav-cart"));
        return new CartPage(driver);
    }
}
