package pages.open_circle;

import pages.BasePage;

import static pages.open_circle.LoginPage.VALID_PASSWORD;

public class ChatPage extends BasePage {

    private static final String CHAT_PAGE = "http://opencircle.us/chat";
    public static final String AVATAR = "xpath=//span[contains(@class,'ant-avatar')]";
    private static final String CHANGE_PASS = "xpath=//span[contains(.,'Change password')]";
    private static final String OLD_PASS_INPUT = "xpath=//input[@id='oldPassword']";
    private static final String NEW_PASS_INPUT = "xpath=//input[@id='newPassword']";
    private static final String SUBMIT_BUTTON = "xpath=(//button[@type='submit'])[2]";
    private static final String LOGOUT_BUTTON = "xpath=//span[contains(.,'Log Out')]";

    public void assertUserIsOnChatPage() {
        assert driver.getCurrentUrl().equals(CHAT_PAGE);
    }

    public void changepassword(String passValue) {
        steps.clickTo(AVATAR);
        steps.clickTo(CHANGE_PASS);
        steps.typeIn(VALID_PASSWORD,OLD_PASS_INPUT);
        steps.typeIn(passValue, NEW_PASS_INPUT);
        steps.clickTo(SUBMIT_BUTTON);
    }

    public void changepasswordback(String passValue) {
        steps.clickTo(AVATAR);
        steps.clickTo(CHANGE_PASS);
        steps.typeIn(passValue,OLD_PASS_INPUT);
        steps.typeIn(VALID_PASSWORD, NEW_PASS_INPUT);
        steps.clickTo(SUBMIT_BUTTON);
    }

    public void logout() {
        steps.waitForIsVisible(LOGOUT_BUTTON);
        steps.clickTo(LOGOUT_BUTTON);
    }
}
