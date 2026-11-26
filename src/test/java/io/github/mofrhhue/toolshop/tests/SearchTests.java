package io.github.mofrhhue.toolshop.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import io.github.mofrhhue.toolshop.core.BaseTest;
import io.github.mofrhhue.toolshop.pages.HomePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTests extends BaseTest {

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
            {"pliers"},
            {"hammer"},
            {"HAMMER"},  // search must ignore upper/lower case
            {"drill"},
        };
    }

    @Test(dataProvider = "searchTerms", description = "Every result contains the search term")
    public void searchReturnsOnlyMatchingProducts(String term) {
        HomePage home = new HomePage(driver).open().search(term);

        // Wait until the grid shows the new results, not the products from before the search
        List<String> names = home.waitUntil(d -> {
            List<String> current = home.productNames();
            boolean allMatch = !current.isEmpty()
                    && current.stream().allMatch(n -> n.toLowerCase().contains(term.toLowerCase()));
            return allMatch ? current : null;
        });

        assertTrue(names.size() > 0, "Expected at least one result for " + term);
    }

    @Test(description = "A search with no matches shows a clear message")
    public void searchWithNoMatchesShowsMessage() {
        HomePage home = new HomePage(driver).open().search("xyzzy-no-such-tool");

        assertEquals(home.noResultsMessage(), "There are no products found.");
        assertEquals(home.productNames().size(), 0);
    }
}
