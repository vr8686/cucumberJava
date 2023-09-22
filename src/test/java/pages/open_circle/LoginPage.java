package pages.open_circle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Random;
import org.junit.Assert;
import java.util.UUID;
import org.openqa.selenium.TimeoutException;



class PasswordGenerator {
    public static String generateRandomPassword(int length) {
        //Thank you, ChatGPT
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        // Add one uppercase character
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        // Add one lowercase character
        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        // Add 8 more characters (you can adjust this number)
        int remainingLength = length - 2;
        for (int i = 0; i < remainingLength; i++) {
            char randomChar = digits.charAt(random.nextInt(digits.length()));
            password.append(randomChar);
        }
        return password.toString();
    }

    public static String generateRandomPasswordNew(int length) {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseCharsAndDigits = UUID.randomUUID().toString().replaceAll("[^a-z0-9]", "").substring(0, length - 2);
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // Add one uppercase character
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));

        // Add one lowercase character
        password.append(lowercaseCharsAndDigits.charAt(random.nextInt(lowercaseCharsAndDigits.length())));

        // Add (length - 2) more characters
        for (int i = 0; i < length - 2; i++) {
            char randomChar = lowercaseCharsAndDigits.charAt(random.nextInt(lowercaseCharsAndDigits.length()));
            password.append(randomChar);
        }

        return password.toString();
    }
}

class EmailGenerator {
    public static String generateRandomEmail() {
        String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        // Add one uppercase character
        password.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
        // Add one lowercase character
        password.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
        // Add 5 more characters (you can adjust this number)
        for (int i = 0; i < 5; i++) {
            char randomChar = digits.charAt(random.nextInt(digits.length()));
            password.append(randomChar);
        }
        return password.toString();
    }
}

public class LoginPage extends BasePage {
    private static final String LOGIN_PAGE = "http://opencircle.us/login/";
    public static final String VALID_LOGIN = "qwerwty@werty.com";
    public static final String LOGIN_INPUT = "xpath=//input[@id='email']";
    public static final String VALID_PASSWORD = "qwertyasfgd1F";
    private static final String PASSWORD_INPUT = "xpath=//input[@id='password']";
    private static final String SIGNIN_BUTTON = "xpath=//button[contains(.,'Sign in')]";
    private static final String AVATAR = "xpath=//span[contains(@class,'ant-avatar')]";
    private static final String PASSWORD_IS_TOO_SHORT_ERROR = "Field should contain at least one upper-case, at least one lower-case and at least one digit and be between 8 and 20";

    public void open() {
        driver.get(LOGIN_PAGE);
    }

    public void fillOutCredentials() {
        steps.typeIn(VALID_LOGIN,LOGIN_INPUT);
        steps.typeIn(VALID_PASSWORD,PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
        steps.waitForIsVisible(AVATAR);
    }

    public void fillOutCredentialsWithContext() {
        String login = (String) steps.context.get("PRIMARY_LOGIN");
        steps.typeIn(login,LOGIN_INPUT);
        String password = (String) steps.context.get("PRIMARY_PASSWORD");
        steps.typeIn(password,PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
        steps.waitForIsVisible(AVATAR);
    }

    public void fillOutCredentialswithNoLogin() {
        steps.typeIn(VALID_PASSWORD,PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
    }

    public void fillOutValidLoginAndPass(String loginValue, String passwordValue) {
        steps.typeIn(loginValue, LOGIN_INPUT);
        steps.typeIn(passwordValue, PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
        steps.waitForIsVisible(AVATAR);
    }

    public void fillOutCredentialswithNoPassword() {
        steps.typeIn(VALID_LOGIN,LOGIN_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
    }

    public void filloutWithInvalidPassSameLength() {
        steps.typeIn(VALID_LOGIN,LOGIN_INPUT);
        String randomPassword = PasswordGenerator.generateRandomPasswordNew(8);
        //Following can be used to generate password with UUID
        // String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        steps.typeIn(randomPassword, PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
    }

    public void filloutWithInvalidPass(int passwordLength) {
        steps.typeIn(VALID_LOGIN,LOGIN_INPUT);
        String randomPassword = PasswordGenerator.generateRandomPassword(passwordLength);
        steps.typeIn(randomPassword, PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
    }

    public void filloutWithInvalidEmail() {
        String randomEmail = EmailGenerator.generateRandomEmail();
        steps.typeIn(randomEmail + "@gmail.com",LOGIN_INPUT);
        steps.typeIn(VALID_PASSWORD,PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
    }

    public void assertWarningMessagesIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Adjust the timeout as needed
        WebElement warningElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-notification-notice-message' and contains(text(), 'Warning')]")));
        String elementText = warningElement.getText();
        Assert.assertTrue("Text 'Warning' is not present in the element.", elementText.contains("Warning"));
    }

    public void assertErrorMessagesIsDisplayed(String errorMessage) {
        steps.assertElementStringPresent("xpath=//li[contains(.,'" + errorMessage + "')]");
    }

    public String getTooShortErrorMessage() {
        return PASSWORD_IS_TOO_SHORT_ERROR;
    }

    /*public void assertErrorMessagesIsDisplayed(String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); //
        for (int i = 0; i < 10; i++) {
            try {
                WebElement warningElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li")));
                String elementText = warningElement.getText();
                if (elementText.contains(errorMessage)) {
                    return;
                }
            } catch (TimeoutException e) {
            }
        }
        Assert.fail("Text '" + errorMessage + "' is not present in the element after waiting.");
    }*/

    public void fillOutCredentialsWithNewPass(String passValue) {
        steps.typeIn(VALID_LOGIN,LOGIN_INPUT);
        steps.typeIn(passValue,PASSWORD_INPUT);
        steps.clickTo(SIGNIN_BUTTON);
        steps.waitForIsVisible(AVATAR);
    }


}
