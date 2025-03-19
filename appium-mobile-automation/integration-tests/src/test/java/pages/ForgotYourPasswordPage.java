package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import stepdefs.Utils;
import stepdefs.xConsts;

public class ForgotYourPasswordPage extends BasePage {

    @AndroidFindBy(id = "com.example.tvapp:id/edit_email")
    @iOSFindBy(accessibility = "forgot_password_emailTextField")
    private MobileElement emailField;

    @AndroidFindBy(id = "com.example.tvapp:id/image_logo")
    @iOSFindBy(accessibility = "app_logo_ios")
    private MobileElement logo;

    @AndroidFindBy(id = "com.example.tvapp:id/button_reset")
    @iOSFindBy(accessibility = "forgot_password_resetButton")
    private MobileElement resetPasswordButton;

    @AndroidFindBy(id = "com.example.tvapp:id/text_bottom_link")
    @iOSFindBy(accessibility = "forgot_password_contactUsButton")
    private MobileElement contactUsLink;

    @AndroidFindBy(id = "com.example.tvapp:id/text_error_email")
    @iOSFindBy(accessibility = "alert_messageLabel")
    private MobileElement errorTxt;

    @AndroidFindBy(id = "com.example.tvapp:id/text_bottom_link")
    @iOSFindBy(accessibility = "forgot_password_contactUsButton")
    private MobileElement bottomText;                     //	Need more help? Contact Us

    @iOSFindBy(accessibility = "forgot_password_contactUsLabel")
    private MobileElement needMoreHelpLabel;                     //    Need more help?

    @AndroidFindBy(id = "android:id/message")
    private MobileElement accttMessage;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement okMg;


    public ForgotYourPasswordPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return resetPasswordButton.isDisplayed();
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }


    /**
     * Method checks if the specified UI element is displayed on the screen.
     */

    public boolean isShown(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "EMAIL":
                el = emailField;
                break;
            case "RESET PASSWORD":
                el = resetPasswordButton;
                break;
            case xConsts.LOGO:
                el = logo;
                break;
            default:
                System.out.println("Warning: Invalid element name - " + name);
                return false;
        }
        return el != null && el.isDisplayed();
    }


    /**
     * Method checks if the specified UI element is enabled on the screen.
     */

    public boolean isEnabled(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "RESET PASSWORD":
                el = resetPasswordButton;
                break;
            case "EMAIL":
                el = emailField;
                break;
            default:
                throw new IllegalArgumentException("Invalid element name provided in isEnabled() method: " + name);

        }
        return el.isEnabled();
    }

    public void tapOnEmailField() {
        emailField.click();
    }


    /**
     * Method taps on the email field in the "Forgot Password" screen to initiate the reset password process..
     */
    public void tapOnEmailFieldToResetPassword() {
        MobileElement el = Utils.findElement("forgotPassword_emailField");
        try {
            Utils.tapElement(el);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearEmailField() {
        try {
            Utils.clearTextField(emailField);
        } catch (Exception e) {
            System.out.println("clearEmailField(): Failed to clear email field. Exception: " + e.getMessage());
        }

    }

    public void resetPasswordButtonClick() {
        resetPasswordButton.click();
    }


    /**
     * Method clicks the specified button or link and navigates to the corresponding page.
     */
    public BasePage buttonClick(String name) {
        BasePage bp = null;
        switch (name) {
            case xConsts.RESET_PASSWORD_BTN:
                resetPasswordButton.click();
                if (MyPages._verificationCodePage != null) {
                    bp = MyPages._verificationCodePage;
                } else {
                    bp = new VerificationCodePage(driver);
                }
                break;
            case xConsts.CONTACT_US_LINK:
                if (Utils.isAndroid()) {
                    int y1 = contactUsLink.getLocation().getY() + contactUsLink.getSize().height * 2 / 3;
                    int x1 = (contactUsLink.getLocation().getX() + contactUsLink.getSize().getWidth()) / 2;
                    new TouchAction(driver).tap(x1, y1).perform();
                } else {
                    contactUsLink.click();
                }
                bp = new HomeLocalPage(driver);
                break;
            case xConsts.CLEAR_TEXT_BTN:
                try {
                    Utils.clearTextField(emailField);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException("buttonClick(): Unsupported element name '" + name + "'. Expected one of: [RESET_PASSWORD_BTN, CONTACT_US_LINK, CLEAR_TEXT_BTN].");

        }
        return bp;
    }


    /**
     * Method retrieves the bottom text displayed on the screen.
     */
    public String getBottomText() {
        String retValue = "";
        if (Utils.isAndroid()) {
            retValue = bottomText.getText();
        } else {
            retValue = needMoreHelpLabel.getText().trim() + "\n" + bottomText.getText().trim();
        }
        return retValue;
    }


    public String getResetPasswordButtonText() {
        return resetPasswordButton.getText();
    }

    public String getEmailText() {
        return emailField.getText();
    }


    /**
     * Method retrieves the error message based on the specified type.
     */
    public String getErrorMessage(String type) {
        String msg = "";
        switch (type) {
            case "EMAIL":
                msg = this.errorTxt.getText();
                break;
            case "ACCOUNT_MSG":
                if (Utils.isAndroid()) {
                    msg = this.accttMessage.getText();
                } else {
                    msg = this.errorTxt.getText();
                }

                break;

            default:
                throw new IllegalArgumentException("getErrorMessage(): Unsupported message type '" + type + "'. Expected values: ['EMAIL', 'ACCOUNT_MSG'].");

        }
        return msg;
    }


}
