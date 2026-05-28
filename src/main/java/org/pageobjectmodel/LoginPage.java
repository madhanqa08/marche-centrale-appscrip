package org.pageobjectmodel;
import abstractcomponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import java.util.Set;
public class LoginPage extends AbstractComponents
{
    WebDriver driver;
    public LoginPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    private final By profile_icon = By.xpath("//div[@id='profile_svg_header']");
    private final By person_icon = By.xpath("(//button[@aria-label='user-profile'])[1]");
    private final By sing_popup = By.xpath("//div[@class='mainSigningSection ']");
    private final By welcome_msg = By.xpath("(//button[contains(@aria-label,'New to Marche Centrale? Sign Up')])[2]");
    private final By signup_popup = By.xpath("//div[contains(@class,'Signup_model singleModal')]");
    private final By firstname_field = By.xpath("(//input[@placeholder='Enter First Name'])[2]");
    private final By lastname_field = By.xpath("(//input[@id='lastName'])[2]");
    private final By mobile_field = By.xpath("(//input[@id='regPhoneInput'])[2]");
    private final By email_field = By.xpath("(//input[@name='email'])[2]");
    private final By passowrd_field = By.xpath("(//input[@name='Password'])[2]");
    private final By referal_field =By.xpath("(//input[@name='referralCode'])[2]");
    private final By tearms_field = By.xpath("(//input[@id='terms'])[2]");
    private final By countryCode = By.xpath("(//div[@class='selected-flag'])[2]");
    private final By clickCountry = By.xpath("(//span[.='India (भारत)'])[3]");
    private final By mobile = By.xpath("(//input[@id='regPhoneInput'])[2]");
    private final By emailfield = By.xpath("(//input[@name='email'])[2]");
    private final By passwordfield = By.xpath("(//input[@name='Password'])[2]");
    private final By teamsConditions = By.xpath("(//input[@id='terms']/ancestor::span[contains(@class, 'MuiCheckbox-root')])[2]");
    private final By clickButton = By.xpath("(//button[.='Sign Up'])[2]");
    private final By phoneErrormsg = By.xpath("(//label[@class='jsx-4e68a86c82818221 errMessage mb-0 mt-1'])[2]");
    private final By mobileTick = By.xpath("(//div[contains(@class,'phone-input-signup')]//div[contains(@class,'check')])[2]");
    private final By emailTick = By.xpath("(//div[contains(@class,'inputControlGroup')]//div[contains(@class,'check')])[2]");
    private final By emailErrormsg = By.xpath("(//label[@class='jsx-4e68a86c82818221 errMessage mb-0 mt-2'])[2]");
    private final By passwordEyeIcon = By.xpath("//img[@title='password-icons']");
    private final By termslink = By.xpath("(//a[@class='jsx-2103809395 px-1'][normalize-space()='Terms and Conditions'])[2]");
    private final By privacylink = By.xpath("(//a[@class='jsx-2103809395 pl-1'][normalize-space()='Privacy Policy.'])[2]");
    private final By otpPopup = By.xpath("//p[.='Enter Verification Code']");
    private final By otpTimer = By.xpath("(//h6[@class='redcircle mx-1 mb-0'])[2]");
    private final By ph_otppopup = By.xpath("(//div[@class='jsx-363255554 pt-2 verification_code_text ']//span)[1]");
    private final By otpVerifyButton = By.xpath("(//button[@class='product_sans_bold CircularProgresBtn undefined'])[2]");
    private final By d1 = By.xpath("(//input[@aria-label='Please enter verification code. Digit 1'])[2]");
    private final By d2 = By.xpath("(//input[@aria-label='Digit 2'])[2]");
    private final By d3 = By.xpath("(//input[@aria-label='Digit 3'])[2]");
    private final By d4 = By.xpath("(//input[@aria-label='Digit 4'])[2]");
    private final By successMsg = By.xpath("//span[@id='client-snackbar']");
    private final By otpErrorMsg = By.xpath("//div[contains(@class,'text-center Signup_btn')]//p");
    private final By resentOtpField = By.xpath("//h6[contains(@class,'product_sans_bold')]//span[.='Resend Verification Code']");
    private final By changeFieldOtpPage = By.xpath("(//div[@class='jsx-363255554 pt-2 verification_code_text ']//span)[2]");
    private final By profileName = By.xpath("//p[contains(@class,'title__name__hdr')]");
    private final By existingUserField = By.xpath("(//p[contains(@class,'existing-user')])[2]//span");
    private final By signInCloseButton = By.xpath("//button[@aria-label='Close login dialog']");
    private final By signUpCloseButton = By.xpath("//p[.='Sign Up']/following-sibling::img");
    private final By otpCloseButton = By.xpath("//div[contains(@class,'closeIcon')]");
    private final By FirstnameErrorField = By.xpath("(//p[.='First name should not exceed 30 characters'])[2]");
    private final By FirstNameSymbolError = By.xpath("(//p[.='Special characters are not allowed'])[2]");
    public void clickLogin()
    {
        // FirstNameSymbolError.click
    }
    public String getfisrtnameSymbolErrorMessage()
    {
        waitForElementBY(FirstNameSymbolError);
        return driver.findElement(FirstNameSymbolError).getText();
    }

    public String getfisrtnameErrorMessage()
    {
        waitForElementBY(FirstnameErrorField);
        return driver.findElement(FirstnameErrorField).getText();
    }



    public void clickCloseOtpUpPopUp()
    {
        waitForElementBY(otpCloseButton);
        driver.findElement(otpCloseButton).click();
    }

    public void clickCloseSignUpPopUp()
    {
        waitForElementBY(signUpCloseButton);
        driver.findElement(signUpCloseButton).click();
    }

    public void clickCloseSignInPopUp()
    {
        waitForElementBY(signInCloseButton);
        driver.findElement(signInCloseButton).click();
    }


    public void clickexistingUserField()
    {
        waitForElementBY(existingUserField);
        driver.findElement(existingUserField).click();
    }
    public String isexistingUserFieldVisible()
    {
        waitForElementBY(existingUserField);
        return driver.findElement(existingUserField).getText();
    }


    public String getProfileName()
    {
        waitForElementBY(profileName);
        return driver.findElement(profileName).getText();
    }
    public void clickPersonIcon()
    {
        waitForElementBY(person_icon);
        driver.findElement(person_icon).click();
    }
    public void clickChangeinOtpPagetoChangeMobile()
    {
        waitForElementBY(changeFieldOtpPage);
        driver.findElement(changeFieldOtpPage).click();
    }

    public void clickResendOtp() throws InterruptedException {
        waitForElementBY(resentOtpField);
        driver.findElement(resentOtpField).click();
        Thread.sleep(2000);
    }
    public boolean isResendOtpIsAppear()
    {
        waitForElementBY(resentOtpField);
        return driver.findElement(resentOtpField).isDisplayed();
    }
    public String getOtpErrorMessage()
    {
        waitForElementBY(otpErrorMsg);
        return driver.findElement(otpErrorMsg).getText();
    }

    public String getOtp() {
        return driver.findElement(d1).getAttribute("value") +
                driver.findElement(d2).getAttribute("value") +
                driver.findElement(d3).getAttribute("value") +
                driver.findElement(d4).getAttribute("value");
    }
    public boolean fillOtp(String otp)
    {
        waitForElementtoBeClickable(d1);
        driver.findElement(d1).sendKeys(otp);
        return true;

    }

    public boolean getInvisibleSuccesMessage(int time)
    {
       return waitForInvisiblilityOfByElement(successMsg,time);
    }
    public String getSuccessMsg()
    {
        waitForElementBY(successMsg);
        return driver.findElement(successMsg).getText();
    }
    public boolean isOtpBoxPresent()
    {
        waitForElementBY(d1);
        waitForElementBY(d2);
        waitForElementBY(d3);
        waitForElementBY(d4);
        return driver.findElement(d4).isDisplayed();
    }

    public boolean isOtpVerifyBtnisEnabled()
    {
        waitForElementBY(otpVerifyButton);
        return driver.findElement(otpVerifyButton).isEnabled();
    }
    public boolean getOtpVerifybuttonStatus()
    {
      return   waitForElementtoBeClickableEnabled(driver.findElement(otpVerifyButton));

    }

    public String getphFromOtpPagetoVerify()
    {
        waitForElementBY(ph_otppopup);
        return driver.findElement(ph_otppopup).getText();
    }

    public boolean istimerPresentinOtpPage()
    {
        waitForElementBY(otpTimer);
        return  driver.findElement(otpTimer).isDisplayed();
    }
    public String gettimer()
    {
//        waitForElementBY(otpTimer);
        return driver.findElement(otpTimer).getText();
    }
    public boolean isotppopupappear()
    {
        waitForElementBY(otpPopup);
        return driver.findElement(otpPopup).isDisplayed();
    }

    public LoginPage clickSubmit() throws InterruptedException
    {
        waitForElementBY(clickButton);
        Thread.sleep(2000);
        driver.findElement(clickButton).click();
        return this;
    }

    public void clickPrivacy()
    {
        waitForElementBY(privacylink);
        driver.findElement(privacylink).click();
    }
    public String switchToNewTabAndGetUrl(String originalWindow)
    {
        // Get all open window handles
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                // Switch to the newly opened tab
                driver.switchTo().window(window);
                break;
            }
        }
        // Return the URL of the new tab so the test can verify it
        return driver.getCurrentUrl();
    }

    public void clickTerms()
    {
        waitForElementBY(termslink);
        driver.findElement(termslink).click();
    }

    public  boolean isTeamsEnabled()
    {
        waitForElementBY(teamsConditions);
        return driver.findElement(teamsConditions).isDisplayed();
    }
    public LoginPage clickpasswordeyeicon()
    {
        waitForElementBY(passwordEyeIcon);
        driver.findElement(passwordEyeIcon).click();
        return this;
    }

    public String checkisPasswordMasked()
    {
        waitForElementBY(passwordfield);
        return driver.findElement(passwordfield).getAttribute("type");
    }
    public LoginPage fillPassword(String pass)
    {
        waitForElementBY(passwordfield);
        driver.findElement(passwordfield).sendKeys(pass);
        return this;
    }
    public String getEmailMsg()
    {
        waitForElementBY(emailErrormsg);
        return driver.findElement(emailErrormsg).getText();
    }
    public boolean emailverifiedmark()
    {
        waitForElementBY(emailTick);
        return driver.findElement(emailTick).isDisplayed();
    }

    public void fillEmail(String Email)
    {
        waitForElementBY(emailfield);
        driver.findElement(emailfield).sendKeys(Email);
    }

    public String defaltCountry()
    {
        return driver.findElement(countryCode).getAttribute("title");
    }
    public boolean mobileverifiedmark()
    {
        waitForElementBY(mobileTick);
       return driver.findElement(mobileTick).isDisplayed();
    }
    public String getphoneErrorMsg()
    {
        waitForElementBY(phoneErrormsg);
        return driver.findElement(phoneErrormsg).getText();
    }
    public LoginPage fillMobileNumber(String mobilenumber)
    {
        driver.findElement(countryCode).click();
        waitForElementBY(clickCountry);
        driver.findElement(clickCountry).click();
        driver.findElement(mobile).sendKeys(mobilenumber);
        return this;
    }

    public boolean isSubmitEnabled() throws InterruptedException
    {
        Thread.sleep(500);
       return driver.findElement(clickButton).isEnabled();
    }
    public LoginPage fillSignUpForm(String firstName,String lastName, String mobilenumber,String email,String password,boolean isTearms)
    {
        if(firstName != null && !firstName.isEmpty())
        {
            driver.findElement(firstname_field).sendKeys(firstName);
        }
        if(lastName != null && !lastName.isEmpty())
        {
            driver.findElement(lastname_field).sendKeys(lastName);
        }
        if(mobilenumber != null && !mobilenumber.isEmpty())
        {
            driver.findElement(countryCode).click();
            waitForElementBY(clickCountry);
            driver.findElement(clickCountry).click();
            driver.findElement(mobile).sendKeys(mobilenumber);
        }
        if(email != null && !email.isEmpty())
        {
            driver.findElement(emailfield).sendKeys(email);
        }
        if(password != null && !password.isEmpty())
        {
            driver.findElement(passwordfield).sendKeys(password);
        }
        if(isTearms)
        {
            driver.findElement(teamsConditions).click();
        }
        return this;
    }

    public void filllastname(String lastName)
    {
        waitForElementBY(lastname_field);
        driver.findElement(lastname_field).click();
        driver.findElement(lastname_field).sendKeys(lastName);
    }

    public void fillfirstname(String firstName)
    {
        waitForElementBY(firstname_field);
        driver.findElement(firstname_field).click();
        driver.findElement(firstname_field).sendKeys(firstName);
    }
    public String getlastname()
    {
        return driver.findElement(lastname_field).getAttribute("value");
    }

    public String getfirstname()
    {
       return driver.findElement(firstname_field).getAttribute("value");
    }
    public boolean checksingupfields()
    {
        waitForElementBY(signup_popup);
        boolean f1 =  driver.findElement(firstname_field).isDisplayed();
        boolean f2 =  driver.findElement(lastname_field).isDisplayed();
        boolean f3 = driver.findElement(mobile_field).isDisplayed();
        boolean f4 = driver.findElement(email_field).isDisplayed();
        boolean f5 = driver.findElement(passowrd_field).isDisplayed();
        boolean f6 = driver.findElement(referal_field).isDisplayed();
        boolean f7 = driver.findElement(tearms_field).isDisplayed();
        return f1 && f2 && f3 && f4 && f5 && f6 && f7;
    }

    public boolean checksignuppopup()
    {
        waitForElementBY(signup_popup);
        return driver.findElement(signup_popup).isDisplayed();
    }
    public String getwelcomemessage()
    {
        waitForElementBY(welcome_msg);
        return driver.findElement(welcome_msg).getAttribute("aria-label");
    }


    public LoginPage clicksingup()
    {
        waitForElementBY(welcome_msg);
        driver.findElement(welcome_msg).click();
        return this;
    }

    public boolean issinguppopupdisappear(int time)
    {
        return   waitForInvisiblilityOfByElement(sing_popup,time);
    }
    public boolean isOtppopupdisappear(int time)
    {
        return   waitForInvisiblilityOfByElement(otpCloseButton,time);
    }

    public boolean ispopupdisappear(int time)
    {
      return   waitForInvisiblilityOfByElement(signup_popup,time);
    }
    public boolean ispopupresent()
    {
        waitForElementBY(sing_popup);
        return driver.findElement(sing_popup).isDisplayed();
    }
    public LoginPage clickprofileicon()
    {
        driver.findElement(profile_icon).click();
        return this;
    }
    public boolean isprofilepresent()
    {
        return  driver.findElement(profile_icon).isDisplayed();
    }

//    public LandingPage setClicklogin()
//    {
//        return new LandingPage(driver);
//    }
    public void load_url(String url)
    {
        driver.get(url);
    }
}
