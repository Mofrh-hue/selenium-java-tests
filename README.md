# Selenium + Java UI Tests: Toolshop

[![UI tests](https://github.com/Mofrh-hue/selenium-java-tests/actions/workflows/ui-tests.yml/badge.svg)](https://github.com/Mofrh-hue/selenium-java-tests/actions/workflows/ui-tests.yml)

Browser tests for **[Toolshop](https://practicesoftwaretesting.com)**, a public demo web shop built for practising software testing. They are written in **Java 17** with **Selenium 4** and **TestNG**, built with **Maven**, and run in headless Chrome by **GitHub Actions** on every push and every Monday.

> Portfolio project. The application under test is a public practice site. The tests only browse, search, filter and use the cart: they never sign in, register or send a completed form (the form tests only submit empty or invalid input to check the error messages).

## What is covered

| Test class | Tests | What it checks |
|---|---|---|
| `SearchTests` | 5 | Results match the search term (data-driven with `@DataProvider`, including upper-case input); a search with no matches shows a clear message |
| `SortingTests` | 4 | Price low-to-high and high-to-low, name A-Z and Z-A |
| `CategoryTests` | 2 | The category filter shows only that category; the Categories menu opens the right page |
| `ProductAndCartTests` | 3 | Product page opens the clicked product; the + button works; adding 2 items shows a confirmation, updates the cart badge, and gives correct line and cart totals |
| `FormValidationTests` | 8 | Login requires email and password and rejects a malformed email; each required contact-form field shows its own message (data-driven); the subject list is correct |
| **Total** | **22** | |

## Design

```
src/test/java/io/github/mofrhhue/toolshop/
├── core/
│   ├── Config.java               baseUrl, headless and timeout from -D system properties
│   ├── DriverFactory.java        one ChromeDriver per thread (ThreadLocal) for parallel runs
│   ├── BaseTest.java             fresh browser before each test, closed after
│   └── ScreenshotOnFailure.java  TestNG listener: saves a PNG when a test fails
├── pages/                        Page Object Model
│   ├── BasePage.java             explicit waits, data-test locators, helpers
│   ├── HomePage.java  CategoryPage.java  ProductPage.java
│   └── CartPage.java  LoginPage.java  ContactPage.java
└── tests/                        test classes (no locators in here)
testng.xml                        suite: test classes run in parallel, 3 threads
```

- **Page Object Model.** Tests describe what a user does (`home.search("pliers")`); the page classes hold every locator. When the UI changes, only one class changes.
- **Stable locators.** The shop marks elements with `data-test` attributes, so locators do not depend on CSS classes or layout.
- **Explicit waits only, no `Thread.sleep`.** The shop is a single-page app that updates the product grid after an API call. Tests wait for the actual condition, such as "every name contains the search term" or "the prices are sorted". Waiting only for the page to load would read the grid while it still shows the old results.
- **Parallel and isolated.** Each test gets its own browser through a `ThreadLocal`, so classes run in parallel safely.
- **Evidence on failure.** A screenshot is saved for every failed test, and CI uploads the screenshots together with the Surefire/TestNG reports.
- **No driver downloads to manage.** Selenium Manager (built into Selenium 4) fetches the matching chromedriver.

## Run it

You need Java 17+, Maven and Google Chrome.

```bash
mvn test                                 # headless (default)
mvn test -Dheadless=false                # watch the browser
mvn test -Dtest=SearchTests              # one class
mvn test -DbaseUrl=https://with-bugs.practicesoftwaretesting.com   # the shop's deliberately buggy build
```

Reports: `target/surefire-reports/index.html`. Failure screenshots: `target/screenshots/`.

## Related projects

The same shop is tested in four ways across my portfolio:

- **[playwright-ui-api-tests](https://github.com/Mofrh-hue/playwright-ui-api-tests)**: Playwright and TypeScript, UI and API
- **[postman-api-tests](https://github.com/Mofrh-hue/postman-api-tests)**: Postman and Newman API tests
- **[qa-manual-testing-portfolio](https://github.com/Mofrh-hue/qa-manual-testing-portfolio)**: manual test plan, test cases and bug report

---

Author: **Ahmed Mofrh Fahmy**, QA Test Lead · [GitHub profile](https://github.com/Mofrh-hue)
