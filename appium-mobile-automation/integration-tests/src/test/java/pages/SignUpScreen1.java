package pages;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.concurrent.TimeUnit;


public class SignUpScreen1 extends BasePage{
    @AndroidFindBy(id = "com.example.tvapp:id/edit_zip_code")
    @iOSFindBy(accessibility = "validate_zipcode_zipcodeTextField")
    private MobileElement zipcodeField;

    @AndroidFindBy(id = "com.example.tvapp:id/edit_zip_code")
    @iOSFindBy (accessibility = "validate_zipcode_zipcodeTextField")
    private MobileElement zipcodeTextbox;

    @AndroidFindBy(id = "com.example.tvapp:id/button_check_zip_code")
    @iOSFindBy(accessibility = "validate_zipcode_signUpButton")
    private MobileElement checkAvailabilityButton;


    @AndroidFindBy(id = "com.example.tvapp:id/text_greeting")
    @iOSFindBy(accessibility = "validate_zipcode_signInButton")
    private MobileElement signInText;

    @AndroidFindBy(id = "com.example.tvapp:id/text_error_zip_code")
    @iOSFindBy (accessibility = "popup_message")
    private MobileElement msgErrorZipCode;

    @AndroidFindBy(id = "android:id/message")
    private MobileElement msgBtvNotAvailable;

    @iOSFindBy(accessibility = "alert_titleLabel")
    private MobileElement msgBtvNotAvailableIos;

    @AndroidFindBy(id = "com.example.tvapp:id/button2")
    @iOSFindBy(accessibility = "popup_left_btn")
    private MobileElement noThnks;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility ="validate_zipcode_signInButton")
    private MobileElement SignInbutton1;

    @iOSFindBy(accessibility = "validate_zipcode_signInLabel")
    private MobileElement validate_zipcode_signInLabel;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "validate_zipcode_signInButton")
    private MobileElement validate_zipcode_signInButton;

    @iOSFindBy(accessibility = "app_logo_ios")
    @AndroidFindBy(id = "com.example.tvapp:id/image_logo")
    private MobileElement logo;

    @iOSFindBy(accessibility = "Delete")
    private MobileElement keyboardDeleteButton;

    public SignUpScreen1(AppiumDriver driver){
        super(driver);
    }

    public void enterZipcode(String zipcode){
        zipcodeField.sendKeys(zipcode);
    }

    public void tapOnZipCodeField(){
        zipcodeField.click();
    }


    public boolean isInitialized() {
        return checkAvailabilityButton.isDisplayed();
    }

    /**
     * Handles button clicks based on the provided button name.
     * Executes the corresponding action and returns the next page object if navigation occurs.
     */
    public BasePage buttonClick(String name) throws InterruptedException {
        switch (name) {
            case xConsts.CHECK_AVAILABILITY_BTN:
                return handleCheckAvailability();

            case xConsts.SIGNED_IN_BTN:
                return handleSignIn();

            case xConsts.CLEAR_TEXT_BTN:
                handleClearText();
                return null; // No navigation after clearing text

            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
    }


    private BasePage handleCheckAvailability() {
        checkAvailabilityButton.click();
        return new SignUpScreen2(driver);
    }

    private BasePage handleSignIn() {
        if (Utils.isAndroid()) {
            tapElement(signInText, 6.0 / 7);
        } else {
            SignInbutton1.click();
        }
        return new SignUpScreen2(driver);
    }

    private void handleClearText() {
        if (Utils.isAndroid()) {
            tapElement(zipcodeTextbox, 14.0 / 15);
        } else {
            zipcodeTextbox.clear();
        }
    }

    /**
     * Taps on a specific location of the given mobile element, calculated using a width factor.
     */
    private void tapElement(MobileElement element, double widthFactor) {
        int y = element.getLocation().getY() + element.getSize().height / 2;
        int x = (int) (element.getLocation().getX() + element.getSize().getWidth() * widthFactor);
        new TouchAction(driver).tap(PointOption.point(x, y)).perform();
    }


    public BasePage checkAvailabilityClick(){
        BasePage bp = null;
        checkAvailabilityButton.click();
        bp = new SignUpScreen2(driver);
        return bp;
    }


    public String getZipCodeFieldText(){
        return zipcodeField.getText();
    }

    public MobileElement getLogoElement(){ return logo;}

    public boolean isIosLogoElementEnabled(){
        MobileElement logo = Utils.findElement("app_logo");
        return logo.isEnabled();
    };


    /**
     * Retrieves the expected error message based on the specified type and platform.
     */
    public String getExpectedErrorMessage(String type)  {
        String msg = "";
        switch (type) {
            case "NOT_AVAILABLE":
                if (Utils.isAndroid()) {
                    msg = xConsts.NOT_AVAILALE_ERROR_MESSAGE_ANDROID;
                }else{
                    msg = xConsts.NOT_AVAILALE_ERROR_MESSAGE_IOS;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid message type: " + type);
        }
        return msg;
    }


    public String getErrorMessage(String type)  {
        String msg = "";
        switch (type) {
            case "ZIPCODE":
                msg = this.msgErrorZipCode.getText();
                break;
            case "NOT_AVAILABLE":
            if (Utils.isAndroid()){
                msg = this.msgBtvNotAvailable.getText();
            }else{
                msg = this.msgBtvNotAvailableIos.getText();
            }
                break;
            case "TEST":
                if (Utils.isAndroid()) {
                    msg = this.msgBtvNotAvailable.getText();
                    System.out.println("not available msg : " + msg);
                    break;
                } else {
                    msg = this.msgBtvNotAvailableIos.getText();
                    System.out.println("not available msg : " + msg);
                    break;
                }
            default:
                throw new IllegalArgumentException("Invalid message type: " + type);
        }
        return msg;
    }


    /**
     * Checks if the specified UI element is currently displayed on the screen.
     */
    public boolean isShown(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case xConsts.SIGNED_IN_BTN:
                el = signInText;
                break;
            case xConsts.ZIPCODE:
                el = zipcodeTextbox;
                break;

            case xConsts.CHECK_AVAILABILITY_BTN:
                el = checkAvailabilityButton;
                break;

            case xConsts.LOGO:
                el = logo;
                break;

            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        return el.isDisplayed();
    }

    public String getSignInGreeting(){
        if (Utils.isAndroid()){
            return signInText.getText();
        } else {
            return (validate_zipcode_signInLabel.getText() + " " + signInText.getText());
        }
    }

    public String getZipCodeText(){
        return zipcodeTextbox.getText();
    }

    public boolean isCheckAvailabilityButtonEnabled() {
        return checkAvailabilityButton.isEnabled();
    }
}



