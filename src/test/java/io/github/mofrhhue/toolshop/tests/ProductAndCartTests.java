package io.github.mofrhhue.toolshop.tests;

import static org.testng.Assert.assertEquals;

import io.github.mofrhhue.toolshop.core.BaseTest;
import io.github.mofrhhue.toolshop.pages.CartPage;
import io.github.mofrhhue.toolshop.pages.HomePage;
import io.github.mofrhhue.toolshop.pages.ProductPage;
import org.testng.annotations.Test;

public class ProductAndCartTests extends BaseTest {

    private static final String PRODUCT = "Combination Pliers";

    @Test(description = "The product page opens the product that was clicked")
    public void productPageShowsSelectedProduct() {
        ProductPage product = new HomePage(driver).open().openProduct(PRODUCT);

        assertEquals(product.name(), PRODUCT);
    }

    @Test(description = "The + button increases the quantity")
    public void increaseQuantityButtonWorks() {
        ProductPage product = new HomePage(driver).open().openProduct(PRODUCT);

        product.increaseQuantity();

        product.waitUntil(d -> "2".equals(product.quantity()));
    }

    @Test(description = "Adding 2 items shows a confirmation, updates the badge and gives correct cart totals")
    public void addToCartUpdatesCartAndTotals() {
        ProductPage product = new HomePage(driver).open().openProduct(PRODUCT);
        double unitPrice = product.price();

        product.increaseQuantity();
        product.waitUntil(d -> "2".equals(product.quantity()));
        product.addToCart();

        assertEquals(product.confirmationMessage(), "Product added to shopping cart.");
        product.waitForCartBadge("2");

        CartPage cart = product.openCart();
        double expected = Math.round(unitPrice * 2 * 100) / 100.0;
        assertEquals(cart.productTitle(), PRODUCT);
        assertEquals(cart.quantity(), "2");
        assertEquals(cart.linePrice(), expected, 0.001);
        assertEquals(cart.cartTotal(), expected, 0.001);
    }
}
