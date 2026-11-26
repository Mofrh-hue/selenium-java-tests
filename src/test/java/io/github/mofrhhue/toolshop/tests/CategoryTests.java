package io.github.mofrhhue.toolshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import io.github.mofrhhue.toolshop.core.BaseTest;
import io.github.mofrhhue.toolshop.pages.CategoryPage;
import io.github.mofrhhue.toolshop.pages.HomePage;
import org.testng.annotations.Test;

public class CategoryTests extends BaseTest {

    @Test(description = "Ticking the Pliers filter shows pliers and hides other tools")
    public void filterByCategoryShowsOnlyThatCategory() {
        HomePage home = new HomePage(driver).open();
        List<String> before = home.productNames();

        home.filterByCategory("Pliers");

        List<String> after = home.waitUntil(d -> {
            List<String> names = home.productNames();
            return !names.isEmpty() && !names.equals(before) && names.contains("Combination Pliers") ? names : null;
        });
        assertFalse(after.stream().anyMatch(n -> n.contains("Hammer")), "No hammers expected: " + after);
        assertTrue(after.size() < before.size(), "Filter should reduce the list: " + before + " -> " + after);
    }

    @Test(description = "The Categories menu opens the Hand Tools page")
    public void categoriesMenuOpensCategoryPage() {
        CategoryPage page = new HomePage(driver).open().openCategoryFromMenu("nav-hand-tools");

        assertEquals(page.title(), "Category: Hand Tools");
        assertTrue(driver.getCurrentUrl().endsWith("/category/hand-tools"), driver.getCurrentUrl());
        assertFalse(page.productNames().isEmpty());
    }
}
