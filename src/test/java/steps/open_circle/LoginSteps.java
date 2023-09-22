package steps.open_circle;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.open_circle.ChatPage;
import pages.open_circle.LoginPage;
import steps.CommonSteps;

import static org.junit.Assert.assertTrue;

public class LoginSteps {

    LoginPage loginPage = new LoginPage();
    ChatPage chatPage = new ChatPage();
    CommonSteps steps = new CommonSteps();

    @Given("user credentials are {string} and {string}")
    public void userCredentialsAreAnd(String mainLoginValue, String mainPasswordValue) {
        steps.context.put("PRIMARY_LOGIN", mainLoginValue);
        steps.context.put("PRIMARY_PASSWORD", mainPasswordValue);
    }

    @Given("user opens login page")
    public void userOpensMainPage() {
        loginPage.open();
        steps.waitForIsVisible("xpath=//input[@id='email']");
    }

    @When("user fills out the form with valid credentials")
    public void userFillsOutTheFormWithValidCredentials() {
        loginPage.fillOutCredentials();
    }

    @When("user fills out the form with {string} and {string}")
    public void userFillsOutTheFormWithValidLoginAndPass(String loginValue, String passwordValue) {
        loginPage.fillOutValidLoginAndPass(loginValue, passwordValue);
    }

    @When("user inputs incorrect email with correct password")
    public void userInputsIncorrectEmailWithCorrectPassword() {
        loginPage.filloutWithInvalidEmail();
    }

    @When("user fills out the form with {int} characters long password")
    public void userFillsOutTheFormWithCharactersLongPassword(int passwordLength) {
        loginPage.filloutWithInvalidPass(passwordLength);
    }

    @And("changes password to {string} and logs out")
    public void changesPasswordToCharOneAndLogsOut(String passValue) {
        chatPage.changepassword(passValue);
        chatPage.logout();
    }

    @And("user fills out the form with new {string}")
    public void userFillsOutTheFormWithNew(String passValue) {
        loginPage.fillOutCredentialsWithNewPass(passValue);
    }

    @And("changes {string} back")
    public void changesPassBack(String passValue) {
        chatPage.changepasswordback(passValue);
        chatPage.logout();
    }

    @When("user fills out the form with valid credentials using Context")
    public void userFillsOutTheFormWithValidCredentialsUsingContext() {
        loginPage.fillOutCredentialsWithContext();
    }

    @When("user fills out the form with login field empty")
    public void userFillsOutTheFormWithLoginFieldEmpty() {
        loginPage.fillOutCredentialswithNoLogin();
    }

    @When("user fills out the form with password field empty")
    public void userFillsOutTheFormWithPasswordFieldEmpty() {
        loginPage.fillOutCredentialswithNoPassword();
    }

    @Then("assert {string} error message is present")
    public void assertErrorMessageIsPresent(String errorMessage) {
        /*if (errorMessage.equalsIgnoreCase("too_short_error_message"))
            errorMessage = loginPage.getTooShortErrorMessage();*/
        switch (errorMessage) {
            case "too_short_error_message":
                errorMessage = loginPage.getTooShortErrorMessage();
                break;
        }
        loginPage.assertErrorMessagesIsDisplayed(errorMessage);
    }

    @Then("user is landing on their Circle homepage")
    public void userIsLandingOnTheirCircleHomepage() {
        chatPage.assertUserIsOnChatPage();
    }

    @When("user fills out the form with incorrect password")
    public void userFillsOutTheFormWithIncorrectPassword() {
        loginPage.filloutWithInvalidPassSameLength();
    }

    @Then("assert warning message is displayed")
    public void assertWarningMessageIsDisplayed() {
        loginPage.assertWarningMessagesIsDisplayed();
    }


}
