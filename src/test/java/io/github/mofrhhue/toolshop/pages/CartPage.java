package io.github.mofrhhue.toolshop.pages;

import org.openqa.selenium.WebDriver;

/** Cart (first step of checkout). */
public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String productTitle() {
        return visible(testId("product-title")).getText().trim();
    }

    public String quantity() {
        return visible(testId("product-quantity")).getDomProperty("value");
    }

    public double linePrice() {
        return money(visible(testId("line-price")).getText());
    }

    public double cartTotal() {
        return money(visible(testId("cart-total")).getText());
    }
}
