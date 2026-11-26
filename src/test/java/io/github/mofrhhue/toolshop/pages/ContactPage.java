package io.github.mofrhhue.toolshop.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/** Contact form. The tests check validation only and never send a message. */
public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public ContactPage open() {
        openPath("/contact");
        visible(testId("contact-submit"));
        return this;
    }

    public ContactPage enterEmail(String email) {
        type(testId("email"), email);
        return this;
    }

    public ContactPage submit() {
        click(testId("contact-submit"));
        return this;
    }

    public String error(String field) {
        return visible(testId(field + "-error")).getText().trim();
    }

    public List<String> subjectOptions() {
        return new Select(visible(testId("subject"))).getOptions().stream()
                .map(o -> o.getText().trim())
                .toList();
    }
}
