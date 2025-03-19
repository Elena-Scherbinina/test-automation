package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.Assert;
import pages.*;


public class ForgotPasswordStepDefinitions {
    private AppiumDriver driver;

    private static EmailUtils emailUtils;

    public ForgotPasswordStepDefinitions() {
        this.driver = Utils.getDriver();
        MyPages._signUpScreen2Page = new SignUpScreen2(driver);
    }


    @Then("^I see Forgot Your Password page$")
    public void iSeeForgotPasswordPage() throws Throwable {
        // Verify that the Forgot Password page has loaded by checking initialization
        Assert.assertTrue(MyPages._forgotYourPasswordPage.isInitialized());
    }

    @And("^I see \"([^\"]*)\" on Forgot Your Password page$")
    public void iWillSeeTextAtTheBottomOfThePage(String expectedText) {
        String value = "";
        String foundText = "";
        // Determine the type of text based on keywords
        if (expectedText.toUpperCase().indexOf("NEED") != -1) {
            value = "HELP";
        } else if (expectedText.toUpperCase().indexOf("ENTER") != -1) {
            value = "EMAIL";
        } else {
            value = expectedText;
        }
        // Retrieve text from the appropriate element based on the keyword
        switch (value.toUpperCase()) {
            case "HELP":
                foundText = MyPages._forgotYourPasswordPage.getBottomText();
                break;
            case "RESET PASSWORD":
                foundText = MyPages._forgotYourPasswordPage.getResetPasswordButtonText();
                break;
            case "EMAIL":
                foundText = MyPages._forgotYourPasswordPage.getEmailText();
                break;
            default:
                throw new IllegalArgumentException("Invalid element on Forgot Your Password page: " + value);
        }
        Assert.assertEquals(foundText, expectedText);
    }

    /**
     * Verifies that the Reset Password button is disabled.
     */
    @And("^Reset Password button is disabled$")
    public void resetPasswordDisabled() {
        Assert.assertFalse(MyPages._forgotYourPasswordPage.isEnabled(xConsts.RESET_PASSWORD));

    }

    @And("^email text field is enabled$")
    public void emailEisabled() {
        System.out.println("isEnabled : " + MyPages._forgotYourPasswordPage.isEnabled("EMAIL"));
        Assert.assertTrue(MyPages._forgotYourPasswordPage.isEnabled("EMAIL"));

    }

    /**
     * Verifies that the email text field is visible on the Forgot Your Password page.
     */
    @And("^I see email on Forgot Your Password page$")
    public void seeEmailTextBox() {
        Assert.assertTrue(MyPages._forgotYourPasswordPage.isShown("EMAIL"));

    }

    /**
     * Verifies that the localBTV logo is visible on the Forgot Your Password page on Android devices.
     */
    @Then("I see localBTV logo on Forgot Your Password page$")
    public void ISeeLocalBTVLogoOnForgotYourPWDPage() throws Throwable {
        if (Utils.isAndroid()) {
            Assert.assertTrue(MyPages._forgotYourPasswordPage.isShown(xConsts.LOGO));
        }
    }

    @And("^I enter email \"([^\"]*)\" to reset password$")
    public void resetPassword(String email) {
        MyPages._forgotYourPasswordPage.enterEmail(email);
    }

    @When("^I tap on email on Reset Password page$")
    public void iTapOnEmail() throws Throwable {
        MyPages._forgotYourPasswordPage.tapOnEmailFieldToResetPassword();
    }

    /**
     * Clicks the Clear Text button on the Reset Password page.
     */
    @Then("^I click Clear Text button on Reset Password page$")
    public void buttonClick() throws Throwable {
        MyPages._forgotYourPasswordPage.clearEmailField();
    }

    /**
     * Verifies that a value in the email field is replaced by a new value.
     */
    @When("^\"([^\"]*)\" value is replaced by \"([^\"]*)\" on Reset Password screen$")
    public void valueIsReplaced(String value, String replacedBy) throws Throwable {
        String generatedValue = "";
        generatedValue = MyPages._forgotYourPasswordPage.getEmailText();
        Assert.assertEquals(generatedValue, replacedBy);
    }

    @Then("^I click \"([^\"]*)\" on Forgot Your Password page$")
    public void iClickButton(String name) throws Throwable {
        switch (name.toUpperCase()) {
            case "CLEAR TEXT":
                MyPages._forgotYourPasswordPage.buttonClick(xConsts.CLEAR_TEXT_BTN);
                break;
            case "CONTACT US":
                MyPages._homeLocalBtvPage = (HomeLocalBTVPage) MyPages._forgotYourPasswordPage.buttonClick(xConsts.CONTACT_US_LINK);
                break;
            default:
                throw new IllegalArgumentException("Forgot Your Password page invalid element: " + name);
        }
    }

    @And("^I click Reset Password button$")
    public void iClickResetPasswordButton() throws Throwable {
        MyPages._forgotYourPasswordPage.resetPasswordButtonClick();
    }

    /**
     * Clicks the Reset Password link, transitioning to the Verification Code page.
     */
    @And("^I click Reset Password link$")
    public void iClickResetPassword() throws Throwable {
        MyPages._verificationCodePage
                = (VerificationCodePage) MyPages._forgotYourPasswordPage.buttonClick(xConsts.RESET_PASSWORD_BTN);
    }


    /**
     * Verifies that the error message displayed on the Reset Password page matches the expected message.
     * The expected message may vary based on the platform (iOS vs. Android) and error type.
     */
    @Then("^I will see \"([^\"]*)\" or \"([^\"]*)\" message for \"([^\"]*)\" error on Reset Password page$")
    public void seeTheFollowingMessageOnResetPasswordPage(String expectedIosMsg, String expectedAndroidMsg, String errorType) {
        String generatedMsg = "";
        String expectedMsg = "";
        generatedMsg = MyPages._forgotYourPasswordPage.getErrorMessage(errorType);
        if (errorType.equalsIgnoreCase("ACCOUNT_MSG")) {
            if (Utils.isAndroid()) {
                expectedMsg = expectedAndroidMsg;
            } else {
                expectedMsg = expectedIosMsg;
            }
        } else {
            //error message for android and ios the same
            expectedMsg = expectedIosMsg;
        }
        Assert.assertEquals(generatedMsg, expectedMsg);
    }


    @Then("I see Home Local page$")
    public void ISeeTermsOfUsePage() throws Throwable {
        boolean isPageLoaded = MyPages._homeLocalBtvPage.isBarTitlePresent(11);
        if (Utils.isAndroid()) {
            if (isPageLoaded) {
                Assert.assertEquals(MyPages._homeLocalBtvPage.getBarTitle(), xConsts.HOME_TITLEBAR);
            }
        }
    }

}
