package io.github.mofrhhue.toolshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.github.mofrhhue.toolshop.core.BaseTest;
import io.github.mofrhhue.toolshop.pages.HomePage;
import org.testng.annotations.Test;

public class SortingTests extends BaseTest {

    @Test(description = "Price (Low - High) sorts prices in ascending order")
    public void sortByPriceAscending() {
        HomePage home = new HomePage(driver).open().sortBy("price,asc");
        List<Double> prices = home.waitUntil(d -> sortedOrNull(home.productPrices(), Comparator.naturalOrder()));
        assertEquals(prices.size(), 9);
    }

    @Test(description = "Price (High - Low) sorts prices in descending order")
    public void sortByPriceDescending() {
        HomePage home = new HomePage(driver).open().sortBy("price,desc");
        home.waitUntil(d -> sortedOrNull(home.productPrices(), Comparator.<Double>reverseOrder()));
    }

    @Test(description = "Name (A - Z) sorts names alphabetically")
    public void sortByNameAscending() {
        HomePage home = new HomePage(driver).open().sortBy("name,asc");
        home.waitUntil(d -> sortedOrNull(home.productNames(), String.CASE_INSENSITIVE_ORDER));
    }

    @Test(description = "Name (Z - A) sorts names in reverse order")
    public void sortByNameDescending() {
        HomePage home = new HomePage(driver).open().sortBy("name,desc");
        home.waitUntil(d -> sortedOrNull(home.productNames(), String.CASE_INSENSITIVE_ORDER.reversed()));
    }

    /** Returns the list when it is sorted (so the wait can stop), otherwise null (keep waiting). */
    private static <T> List<T> sortedOrNull(List<T> values, Comparator<? super T> order) {
        if (values.isEmpty()) {
            return null;
        }
        List<T> sorted = new ArrayList<>(values);
        sorted.sort(order);
        return sorted.equals(values) ? values : null;
    }
}
