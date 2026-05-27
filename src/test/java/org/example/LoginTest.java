package org.example;
import Testcomponents.BaseTest;
import model.SignupPojo;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
public class LoginTest extends BaseTest
{
    SignupPojo signup = new SignupPojo();
    @Test(description = "Verify Profile Icon Visibility on Landing Page")
    public void testcase001()
    {
        boolean flag = loginPage.isprofilepresent();
        Assert.assertTrue(flag);
    }
    @Test(description = "Verify Click on Profile Icon Opens Sign In Modal")
    public void testcase002()
    {
        loginPage.clickprofileicon();
        Assert.assertTrue(loginPage.ispopupresent());
    }
    @Test(description = "Verify 'New to Marche Centrale? Sign Up' Link in Sign In Modal")
    public void testcase003()
    {
        loginPage.clickprofileicon();
        String signupText = loginPage.getwelcomemessage();
        Assert.assertEquals(signupText,"New to Marche Centrale? Sign Up");
    }

    @Test(description = "Verify Sign Up Modal Loads on Clicking Sign Up Link")
    public void testcase004()
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertTrue(loginPage.checksignuppopup());
    }

    @Test(description = "Verify All Required Fields Are Displayed")
    public void testcase005()
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertTrue(loginPage.checksignuppopup());
    }

    @Test(description = "Verify First Name Field Accepts Valid Input")
    public void testcase006()
    {
        loginPage.clickprofileicon().clicksingup();
        signup.setFirstname("Madhan");
        loginPage.fillfirstname(signup.getFirstname());
        String firstname = loginPage.getfirstname();
        Assert.assertEquals(firstname,signup.getFirstname());
    }

    //=====BUG
    @Test(description = "Verify Last Name Field Accepts Valid Input")
    public void testcase007()
    {
        loginPage.clickprofileicon().clicksingup();
        signup.setLastname("Kumar");
        loginPage.filllastname(signup.getLastname());
        String lastname = loginPage.getlastname();
        Assert.assertEquals(lastname,signup.getLastname(),"User entered last name is showing different");
    }

    @Test(description = "Verify First Name Field – Empty Submission")
    public void testcase008() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm("", signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }


    @Test(description = "Verify Last Name Field – Empty Submission" )
    public void testcase009() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(),"", signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(true,"Submit button is enabled, even through last name is empty");
        loginPage.isSubmitEnabled();
    }




    @Test(description = "Verify Phone Number Field – Valid Indian Number")
    public void testcase010()
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        Assert.assertTrue(loginPage.fillMobileNumber(signup.getMobile()).mobileverifiedmark());
    }

    @Test(description = "Verify Phone Number Field – Invalid (Less Than 10 Digits)")
    public void testcase011()
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertEquals(loginPage.fillMobileNumber("995240").getphoneErrorMsg(),"Enter Valid Phone Number");
    }

    @Test(description = "Verify Phone Number Field – Non-Numeric Characters")
    public void testcase012()
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertEquals(loginPage.fillMobileNumber("DJDOKIDO").getphoneErrorMsg(),"Enter Valid Phone Number");
    }

    @Test(description = "Verify Default Country Code on Phone Field")
    public void testcase013()
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertEquals(loginPage.defaltCountry(),"Cameroon (Cameroun): +237");
    }


    @Test(description = "Verify Email Field – Valid Email Address")
    public void testcase014()
    {
        loginPage.clickprofileicon().clicksingup().fillEmail(signup.getEmail());
        Assert.assertTrue(loginPage.emailverifiedmark(),"Email Verified Mark is not displayed");
    }

    //=====BUG
    @Test(description = "Verify Email Field – Invalid Format (Missing @)")
    public void testcase015()
    {
        loginPage.clickprofileicon().clicksingup().fillEmail("madhangmail.com");
        Assert.assertEquals(loginPage.getEmailMsg(),"Enter Valid Email","Error message is missing for invalid email");
    }

    @Test(description = "Verify Email Field – Invalid Format (Missing Domain)")
    public void testcase016()
    {
        loginPage.clickprofileicon().clicksingup().fillEmail("madhan@gmail");
        Assert.assertEquals(loginPage.getEmailMsg(),"Enter Valid Email");
    }

    @Test(description = "Verify Email Field – Empty Submission")
    public void testcase017() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), "", signup.getPassowrd(),true);
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }

    @Test(description = "Verify Password Field – Valid Password Entry")
    public void testcase018()
    {
        signup.getAllDatas();
        Assert.assertEquals(loginPage.clickprofileicon().clicksingup().fillPassword(signup.getPassowrd()).checkisPasswordMasked(),"password");

    }

    @Test(description = "Verify Password Field – Toggle Visibility (Show/Hide Eye Icon)")
    public void testcase019()
    {
        signup.getAllDatas();
        loginPage.clickprofileicon().clicksingup();
        loginPage.fillPassword(signup.getPassowrd()).clickpasswordeyeicon();
        Assert.assertEquals(loginPage.checkisPasswordMasked(),"text");
    }


    @Test(description = "Verify Password Field – Empty Submission")
    public void testcase020() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), "",true);
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }

    @Test(description = "Verify Sign Up Button is Disabled Initially")
    public void testcase021() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }

    @Test(description = "Verify T&C Checkbox is Unchecked by Default")
    public void testcase022()
    {
        Assert.assertTrue(loginPage.clickprofileicon().clicksingup().isTeamsEnabled());
    }

    @Test(description = "Verify Sign Up Button Remains Disabled if T&C Not Checked")
    public void testcase024() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),false);
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }

    @Test(description = "Verify T&C Hyperlinks Are Clickable")
    public void testcase025()
    {

        loginPage.clickprofileicon().clicksingup();
        String originalWindow = driver.getWindowHandle();
        loginPage.clickTerms();
        String newTabUrl = loginPage.switchToNewTabAndGetUrl(originalWindow);
        Assert.assertTrue(newTabUrl.contains("terms-conditions"), "The link did not navigate to the Terms & Conditions page! Actual URL: " + newTabUrl);

    }

    @Test(description = "Verify Privacy Policy Hyperlink is Clickable")
    public void testcase026()
    {
        loginPage.clickprofileicon().clicksingup();
        String originalWindow = driver.getWindowHandle();
        loginPage.clickPrivacy();
        String newTabUrl = loginPage.switchToNewTabAndGetUrl(originalWindow);
        Assert.assertTrue(newTabUrl.contains("privacy-policy"), "The link did not navigate to the Terms & Conditions page! Actual URL: " + newTabUrl);
    }

    @Test(description = "Verify Successful Form Submission with Valid Data")
    public void testcase027() throws InterruptedException {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear(),"After successful for submission, the otp pop up is not appeared");
    }

    @Test(description = "Verify Duplicate Phone Number – Already Registered")
    public void tescase030() throws InterruptedException {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), "9952405983", signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertEquals(loginPage.getphoneErrorMsg(),"This Phone Number Is Linked With A User Account, Please Try A Different Phone Number.");
        Assert.assertFalse(loginPage.isSubmitEnabled());;
    }


    @Test(description = "Verify Duplicate Email – Already Registered")
    public void testcase031() throws InterruptedException {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), "madhanofficial08@gmail.com", signup.getPassowrd(),true);
        Assert.assertEquals(loginPage.getEmailMsg(),"This Email Address Is Linked With A User Account, Please Try A Different Email Address.","This Email Address Is Linked With A User Account error message is missing");
        Assert.assertFalse(loginPage.isSubmitEnabled());
    }


    @Test(description = "Verify OTP Modal Displays Correctly")
    public void testcase032() throws InterruptedException
    {
        loginPage.clickprofileicon().clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear(),"Otp pop up is not appeared after successful submission");
        Assert.assertTrue(loginPage.istimerPresentinOtpPage(),"After successful form submission, otp form open but timer is not present");
        String ph = loginPage.getphFromOtpPagetoVerify();
        String mobileNumber = ph.replaceAll("\\D", "");
        // Remove country code 91
        if(mobileNumber.startsWith("91"))
        {
            mobileNumber = mobileNumber.substring(2);
        }
        Assert.assertEquals(mobileNumber.trim(),signup.getMobile().trim(),"Entered otp in Registration is showing wrong in the Otp Pop");
        //  Assert.assertFalse(loginPage.getOtpVerifybuttonStatus(),"After successful form submission,Otp verify button is enabled initially");
        Assert.assertTrue(loginPage.isOtpBoxPresent(),"After successful form submission,Otp field is not appeared");
    }

    @Test(description = "Verify OTP Timer Counts Down From 60 Seconds")
    public void testcase033() throws InterruptedException
    {
        signup.getAllDatas();
        loginPage.clickprofileicon().clicksingup().fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        String actualTime = loginPage.clickSubmit().gettimer();
        Assert.assertTrue(actualTime.equals("1:00") || actualTime.equals("0:59"),"Time is not stating  from 1:00");
    }

    @Test(description = "Verify Valid OTP (1111) Entry Within Time Limit")
    public void testcase034() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Assert.assertTrue(loginPage.fillOtp("1111"));
        //  Assert.assertEquals(loginPage.getOtpErrorMessage(),"Your Verification Code Has Expired Or Is Invalid. Please Click Resend To Get A New Code Sent.");
        //  Assert.assertEquals(loginPage.getOtp(),"1111");
        Assert.assertTrue(loginPage.getSuccessMsg().trim().contains("Signed up successfully"),"Signed up successfully is not displayed when the user fill correct otp ");
    }

    @Test(description = "Verify Incorrect OTP Entry (e.g., 0000)")
    public void testcase037() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Assert.assertTrue(loginPage.fillOtp("0000"));
        Assert.assertEquals(loginPage.getOtpErrorMessage(),"Your Verification Code Has Expired Or Is Invalid. Please Click Resend To Get A New Code Sent.");
    }

    @Test(description = "Verify OTP Entry After Timer Expires (>60 seconds)")
    public void testcase038() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Thread.sleep(61000);
        Assert.assertTrue(loginPage.fillOtp("0000"));
        Assert.assertEquals(loginPage.getOtpErrorMessage(),"Your Verification Code Has Expired Or Is Invalid. Please Click Resend To Get A New Code Sent.");
        Assert.assertTrue(loginPage.isResendOtpIsAppear());
    }

    @Test(description = "Verify 'Resend Verification Code' Appears After Timer Expires")
    public void testcase039() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Thread.sleep(62000);
        Assert.assertTrue(loginPage.isResendOtpIsAppear());
        loginPage.clickResendOtp();
        String actualTime = loginPage.gettimer();
        Assert.assertTrue(actualTime.equals("1:00") || actualTime.equals("0:59") || actualTime.equals("0:58"),"Time is not stating  from 1:00");
    }

    @Test(description = "Verify Valid OTP Entry After Resend Within New Timer")
    public void testcase041() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Thread.sleep(62000);
        Assert.assertTrue(loginPage.isResendOtpIsAppear());
        loginPage.clickResendOtp();
        Assert.assertTrue(loginPage.fillOtp("1111"));
//        Assert.assertEquals(loginPage.getOtpErrorMessage(),"Your Verification Code Has Expired Or Is Invalid. Please Click Resend To Get A New Code Sent.");
        Assert.assertTrue(loginPage.getSuccessMsg().trim().contains("Signed up successfully"),"Signed up successfully is not displayed when the user fill correct otp ");;
    }

    @Test(description = "Verify 'Change' Link in OTP Modal")
    public void testcase042() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        loginPage.clickChangeinOtpPagetoChangeMobile();
        Assert.assertTrue(loginPage.checksignuppopup(),"Sign up page is not shown when user clicking on the change in otp field");
    }

    @Test(description = "Verify Verify Button is Disabled Until All 4 OTP Digits Entered")
    public void testcase043() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Assert.assertFalse(loginPage.isOtpVerifyBtnisEnabled());
        Assert.assertTrue(loginPage.fillOtp("1111"));
        Thread.sleep(1000);
        Assert.assertFalse(loginPage.isOtpVerifyBtnisEnabled());
    }

    @Test(description = "Verify Success Toast Disappears After ~3 Seconds")
    public void testcase045() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Assert.assertTrue(loginPage.fillOtp("1111"));
        Assert.assertTrue(loginPage.getSuccessMsg().trim().contains("Signed up successfully"),"Signed up successfully is not displayed when the user fill correct otp ");
        Thread.sleep(3000);
        Assert.assertTrue(loginPage.getInvisibleSuccesMessage(3),"Signed up successfully is not displayed when the user fill correct otp ");

    }

    @Test(description = "Verify User is Logged In After Successful Sign Up")
    public void testcase046() throws InterruptedException
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        Assert.assertTrue(loginPage.fillOtp("1111"));
        Assert.assertTrue(loginPage.getSuccessMsg().trim().contains("Signed up successfully"),"Signed up successfully is not displayed when the user fill correct otp ");
        loginPage.clickPersonIcon();
        System.out.println(loginPage.getProfileName());
        Assert.assertTrue(loginPage.getProfileName().contains(signup.getFirstname()));
    }


    @Test(description = "Verify Existing User? Sign In Link in Sign Up Modal")
    public void testcase047()
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clicksingup();
        Assert.assertEquals(loginPage.isexistingUserFieldVisible(),"Sign In");
        loginPage.clickexistingUserField();
        loginPage.ispopupresent();
    }

    @Test(description = "Verify Close (X) Button on Sign In Modal")
    public void testcase048()
    {
        loginPage.clickprofileicon().ispopupresent();
        loginPage.clickCloseSignInPopUp();
        Assert.assertTrue(loginPage.ispopupdisappear(1));
    }

    @Test(description = "Verify Close (X) Button on Sign Up Modal")
    public void testcase049()
    {
        loginPage.clickprofileicon().ispopupresent(); loginPage.clicksingup();
        loginPage.clickCloseSignUpPopUp();
        Assert.assertTrue(loginPage.ispopupdisappear(1));
    }

    @Test(description = "Verify Close (X) Button on OTP Modal")
    public void testcase050() throws InterruptedException {
        loginPage.clickprofileicon().ispopupresent(); loginPage.clicksingup();
        signup.getAllDatas();
        loginPage.fillSignUpForm(signup.getFirstname(), signup.getLastname(), signup.getMobile(), signup.getEmail(), signup.getPassowrd(),true);
        Assert.assertTrue(loginPage.clickSubmit().isotppopupappear());
        loginPage.clickCloseOtpUpPopUp();
        Assert.assertTrue(loginPage.isOtppopupdisappear(1));
    }

    @Test(description = "Verify Clicking Outside Modal Does Not Close It (Or Closes It)")
    public void testcase051() throws InterruptedException
    {
        Assert.assertTrue(loginPage.clickprofileicon().ispopupresent());
        Actions actions = new Actions(driver);
        actions.moveByOffset(10, 10).click().perform();
        Thread.sleep(5000);
        Assert.assertTrue(loginPage.ispopupdisappear(1));
    }

    @Test(description = "Verify Sign Up with Very Long First Name (>50 characters)")
    public void testcase054()
    {
        Assert.assertTrue(loginPage.clickprofileicon().ispopupresent());
        signup.getAllDatas();
        loginPage.clicksingup();
        loginPage.fillfirstname("FKSMFOKSMFSKGDGRGGGGFGMFSKMFOSMFOSMFOSMFOSMFOSFMSOFMSOFMSOFMSOFMSOFM");
        Assert.assertEquals(loginPage.getfisrtnameErrorMessage(),"First Name Should Not Exceed 30 Characters");
    }

    @Test(description = "Verify Sign Up with Special Characters in Name Fields")
    public void testcase055()
    {
        Assert.assertTrue(loginPage.clickprofileicon().ispopupresent());
        signup.getAllDatas();
        loginPage.clicksingup();
        loginPage.fillfirstname("#$%$#$%$#");
        Assert.assertEquals(loginPage.getfisrtnameSymbolErrorMessage(),"Special Characters Are Not Allowed");
    }
}
