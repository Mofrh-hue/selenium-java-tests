package io.github.mofrhhue.toolshop.pages;

import org.openqa.selenium.WebDriver;

/** Sign-in page. Only the form validation is tested: no account is used. */
public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        openPath("/auth/login");
        visible(testId("login-form"));
        return this;
    }

    public LoginPage enterEmail(String email) {
        type(testId("email"), email);
        return this;
    }

    public LoginPage submit() {
        click(testId("login-submit"));
        return this;
    }

    /** Text of the validation message under a field, e.g. error("email"). */
    public String error(String field) {
        return visible(testId(field + "-error")).getText().trim();
    }
}
