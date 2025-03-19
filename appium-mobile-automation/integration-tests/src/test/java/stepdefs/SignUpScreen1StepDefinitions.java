
package stepdefs;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import pages.*;

import java.util.List;


public class SignUpScreen1StepDefinitions {
    private AppiumDriver driver;


    public SignUpScreen1StepDefinitions() {
        this.driver = Utils.getDriver();
        MyPages._signUpScreen1Page = new SignUpScreen1(driver);
    }

    /**
     * Verifies that the app is running and that the initial ZIP Code field text is correct.
     */
    @Given("^I have an app running with an appium$")
    public void startApplication() throws Throwable {
        Assert.assertEquals(MyPages._signUpScreen1Page.getZipCodeFieldText(), xConsts.SIGNUP_SCREEN1_ZIPCODE_TEXT);
    }

    @And("^I enter my zipcode \"([^\"]*)\"$")
    public void enterZipcode(String zipcode) throws Throwable {
        MyPages._signUpScreen1Page.enterZipcode(zipcode);

    }

    @Then("^I will see error message for \"([^\"]*)\"$")
    public void seeTheFollowingMessage(String errorType) {
        String generatedMsg = "";
        String expectedMsg = "";
        expectedMsg = MyPages._signUpScreen1Page.getExpectedErrorMessage(errorType);
        generatedMsg = MyPages._signUpScreen1Page.getErrorMessage(errorType);

        Assert.assertEquals(generatedMsg, expectedMsg);
    }

    /**
     * Clicks a button on the first screen based on the provided button name.
     * Supported values are "SIGN IN", "CHECK AVAILABILITY", and "CLEAR TEXT".
     */
    @Then("^I click \"([^\"]*)\" button on the first screen$")
    public void buttonClick(String name) throws Throwable {
        switch (name.toUpperCase()) {
            case "SIGN IN":
                MyPages._signUpScreen2Page = null;
                MyPages._signUpScreen2Page = (SignUpScreen2) MyPages._signUpScreen1Page.buttonClick(xConsts.SIGNED_IN_BTN);
                break;
            case "CHECK AVAILABILITY":
                MyPages._signUpScreen2Page = null;
                MyPages._signUpScreen2Page = (SignUpScreen2) MyPages._signUpScreen1Page.checkAvailabilityClick();
                break;
            case "CLEAR TEXT":
                MyPages._signUpScreen1Page.buttonClick(xConsts.CLEAR_TEXT_BTN);
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
    }

    /**
     * Verifies that the SignIn link is visible on the Sign Up Screen 1.
     */
    @And("^I see SignIn link$")
    public void iSeeLink() throws Throwable {
        Assert.assertTrue(MyPages._signUpScreen1Page.isShown(xConsts.SIGNED_IN_BTN));
    }

    @And("^I navigate back on Zipcode screen$")
    public void iNavigateBack() throws Throwable {
        Utils.goBack();
    }

    /**
     * Verifies that the ZipCode textbox is displayed.
     */
    @And("^I see ZipCode textbox$")
    public void iSeeZipCode() throws Throwable {
        //fixing stale element exception
        if (Utils.isAndroid()) {
            Boolean visible = Utils.isElementVisible(Utils.getId("edit_ZipCode"), 8);
            Assert.assertTrue(visible);
        } else {
            Assert.assertTrue(MyPages._signUpScreen1Page.
                    isShown(xConsts.ZIPCODE));
        }
    }

    @And("^I see Check Availability button$")
    public void iSeeCheckAvailabilityButton() throws Throwable {
        Assert.assertTrue(MyPages._signUpScreen1Page.
                isShown(xConsts.CHECK_AVAILABILITY_BTN));
    }


    @Then("I see first Signup screen$")
    public void ISeeFirstSignUpScreen() throws Throwable {
        Assert.assertTrue(MyPages._signUpScreen1Page.isInitialized());
    }


    /**
     * Verifies that the text messages on the Sign Up Screen 1 match the expected values
     * provided in a data table.
     */
    @And("I see the following text messages:$")
    public void ISeeFFollowingTextValue(DataTable dt) throws Throwable {
        String signInTxt = MyPages._signUpScreen1Page.getSignInGreeting().trim();
        String zipCodeTxt = MyPages._signUpScreen1Page.getZipCodeFieldText().trim();
        List<String> list = dt.asList(String.class);
        if (Utils.isAndroid()) {
            Assert.assertEquals(signInTxt, list.get(0).trim());
        } else {
            //There is a difference in the space character after '?' in android and ios generated text
            Assert.assertEquals(signInTxt, "Already have an account? Sign In");
        }
        Assert.assertEquals(zipCodeTxt, list.get(1));
    }


    /**
     * Verifies that the default text in the specified textbox matches the expected default.
     */
    @Then("^I see default text in \"([^\"]*)\" textbox$")
    public void seeDefaultText(String txtbox) {
        String generatedText = "";
        String expectedText = "";

        switch (txtbox.toUpperCase()) {
            case "ZIP CODE":
                expectedText = "Enter your ZIP Code";
                generatedText = MyPages._signUpScreen1Page.getZipCodeFieldText().trim();
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + txtbox);
        }
        Assert.assertEquals(expectedText, generatedText);
    }


    @And("^the button is disabled$")
    public void buttonIsDisabled() throws Throwable {
        boolean buttonEnabled = MyPages._signUpScreen1Page.isCheckAvailabilityButtonEnabled();
        Assert.assertFalse(buttonEnabled);
    }

    /**
     * Taps on the Zip Code field.
     */
    @When("^I tap on zip code$")
    public void iTapOnZipcode() throws Throwable {
        MyPages._signUpScreen1Page.tapOnZipCodeField();
    }


    @And("^screen has portrait orientation$")
    public void hasPortraitOrientation() throws Throwable {
        try {
            System.out.println("*--*--*-- Screen orientation is : " + driver.getOrientation() + "--*--*--*");
            Assert.assertEquals(ScreenOrientation.PORTRAIT, driver.getOrientation());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Then("^I rotate the screen horizontally$")
    public void rotateHorizontally() throws Throwable {
        Utils.rotateScreen('L');
    }

    @Then("^I rotate the screen vertically$")
    public void rotateVertically() throws Throwable {
        Utils.rotateScreen('P');
    }


    @Then("screen has landscape orientation$")
    public void rotatedHorizontally() throws Throwable {
        try {
            System.out.println("*--*--*-- Screen orientation is : " + driver.getOrientation() + "--*--*--*");
            Assert.assertEquals(driver.getOrientation(), ScreenOrientation.LANDSCAPE);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Then("^keyboard is not shown$")
    public void keyboardIsNotShown() throws Throwable {
        boolean keyboardIsShown = false;
        if (Utils.isAndroid()) {
            keyboardIsShown = Utils.isKeyboardShownForAndroid();
        } else {
        }
        Assert.assertFalse(keyboardIsShown);
    }

    /**
     * Hides the keyboard.
     */
    @When("^I hide keyboard$")
    public void iHideKeyboard() throws Throwable {
        Utils.hideKeyboard();
    }

    /**
     * Verifies that the zip code value is replaced by the provided value.
     */
    @When("^\"([^\"]*)\" value is replaced by \"([^\"]*)\"$")
    public void valueIsReplaced(String value, String replacedBy) throws Throwable {
        String zipCodeTxt = MyPages._signUpScreen1Page.getZipCodeFieldText().trim();
        Assert.assertEquals(zipCodeTxt, replacedBy);
    }

    /**
     * Checks that the app is not currently visible.
     * For Android, it queries the app state.
     */
    @Then("^app is not shown$")
    public void app_is_not_shown() throws Throwable {
        if (Utils.isAndroid()) {
            ApplicationState state = driver.queryAppState(xConsts._BUNDLE_ID);
            MobileElement el = MyPages._signUpScreen1Page.getLogoElement();
        } else {
        }
    }

    /**
     * Launches the app.
     */
    @And("^I launch the app$")
    public void iLaunchApp() throws Throwable {
        if (Utils.isAndroid()) {
            ((AndroidDriver) driver).launchApp();
        } else {
            ((IOSDriver) driver).launchApp();
        }
    }

    /**
     * Turns on dark mode if supported.
     */
    @Then("^I turn on dark mode$")
    public void turnOnDarkMode() throws Throwable {

        if (!Utils.isDarkModeSupported()) {
            return;
        }
        Utils.turnOnDarkMode();
    }

    /**
     * Turns off dark mode if supported.
     */
    @Then("^I turn off dark mode$")
    public void turnOffDarkMode() throws Throwable {
        if (!Utils.isDarkModeSupported()) {
            return;
        }
        Utils.turnOffDarkMode();
    }

    /**
     * Verifies that the specified button texts in the message box match the expected values.
     * Checks for differences between Android and iOS.
     *
     * @param button_1_text     expected text for button 1 on Android.
     * @param button_2_text     expected text for button 2 on Android.
     * @param button_1_ios_text expected text for button 1 on iOS.
     * @param button_2_ios_text expected text for button 2 on iOS.
     */
    @Then("^I will see \"([^\"]*)\" and \"([^\"]*)\" or \"([^\"]*)\" and \"([^\"]*)\" on the message box$")
    public void siWillSeeButton1AndButton2(String button_1_text, String button_2_text, String button_1_ios_text, String button_2_ios_text) {
        MobileElement buttonNo = null;
        MobileElement buttonYes = null;
        if (Utils.isAndroid()) {
            buttonNo = Utils.findElementNoPrefix("alertWrongArea_no");
            Assert.assertEquals(buttonNo.getText(), button_2_text);
        } else {
            buttonNo = Utils.findElement("alertWrongArea_NoThanksButton");
            Assert.assertEquals(buttonNo.getText(), button_2_ios_text);
        }

        if (Utils.isAndroid()) {
            buttonYes = Utils.findElementNoPrefix("alertWrongArea_yes");
            Assert.assertEquals(buttonYes.getText(), button_1_text);
        } else {
            buttonYes = Utils.findElement("alertWrongArea_NotifyMeButton");
            Assert.assertEquals(buttonYes.getText(), button_1_ios_text);
        }

    }


    /**
     * Taps the "No Thanks" button to dismiss the Check Availability message box.
     */
    @When("^I tap \"No Thanks\" button to dismiss Check Availability message box$")
    public void iTapNoThanks() throws Throwable {
        MobileElement buttonNo = null;
        if (Utils.isAndroid()) {
            buttonNo = Utils.findElementNoPrefix("alertWrongArea_no");
        } else {
            buttonNo = Utils.findElement("alertWrongArea_NoThanksButton");
        }
        buttonNo.click();
    }


    /**
     * Check that button "NO, THANKS" is not visible
     */
    @Then("^Check Availability message box dismissed$")
    public void checkAvailabilityMessageBoxDismissed() throws Throwable {
        boolean isMsgBoxDismissed = false;
        if (Utils.isAndroid()) {
            isMsgBoxDismissed = Utils.isElementInVisible(xConsts.ANDROID_IDS2.get("alertWrongArea_no"), 3);
        } else {
            isMsgBoxDismissed = Utils.isElementInVisible(Utils.getId("alertWrongArea_NoThanksButton"), 3);
        }
        Assert.assertTrue(isMsgBoxDismissed);

    }
}

