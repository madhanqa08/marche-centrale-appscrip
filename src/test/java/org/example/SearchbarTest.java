package org.example;
import Testcomponents.BaseTest;
import model.SignupPojo;
import org.testng.Assert;
import org.testng.annotations.Test;
public class SearchbarTest extends BaseTest
{
    SignupPojo signup = new SignupPojo();
    @Test(description = "Verify Password Field – Toggle Visibility (Show/Hide Eye Icon)")
    public void testcase019()
    {
        signup.getAllDatas();
        loginPage.clickprofileicon().clicksingup();
        loginPage.fillPassword(signup.getPassowrd()).clickpasswordeyeicon();
        Assert.assertEquals(loginPage.checkisPasswordMasked(),"tex");
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
        Assert.assertEquals(loginPage.getOtpErrorMessage(),"Your Verification Code Has Expired Or Is Invalid. Please Click Resend To Get A  ");
    }
}
