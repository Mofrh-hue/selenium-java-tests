package io.github.mofrhhue.toolshop.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import io.github.mofrhhue.toolshop.core.BaseTest;
import io.github.mofrhhue.toolshop.pages.ContactPage;
import io.github.mofrhhue.toolshop.pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/** Negative tests: forms must refuse bad input. Nothing is ever submitted to the server. */
public class FormValidationTests extends BaseTest {

    @Test(description = "Login with empty fields shows both required messages")
    public void loginRequiresEmailAndPassword() {
        LoginPage login = new LoginPage(driver).open().submit();

        assertEquals(login.error("email"), "Email is required");
        assertEquals(login.error("password"), "Password is required");
    }

    @Test(description = "Login rejects an email address without @domain")
    public void loginRejectsInvalidEmailFormat() {
        LoginPage login = new LoginPage(driver).open().enterEmail("not-an-email").submit();

        assertEquals(login.error("email"), "Email format is invalid");
    }

    @DataProvider(name = "requiredContactFields")
    public Object[][] requiredContactFields() {
        return new Object[][] {
            {"first-name", "First name is required"},
            {"last-name", "Last name is required"},
            {"email", "Email is required"},
            {"subject", "Subject is required"},
            {"message", "Message is required"},
        };
    }

    @Test(dataProvider = "requiredContactFields", description = "Each required contact field shows its own message")
    public void contactFormRequiresField(String field, String expectedMessage) {
        ContactPage contact = new ContactPage(driver).open().submit();

        assertEquals(contact.error(field), expectedMessage);
    }

    @Test(description = "The contact form offers the expected subjects")
    public void contactFormSubjects() {
        List<String> options = new ContactPage(driver).open().subjectOptions();

        assertEquals(options, List.of(
                "Select a subject *",
                "Customer service",
                "Webmaster",
                "Return",
                "Payments",
                "Warranty",
                "Status of my order"));
    }
}
