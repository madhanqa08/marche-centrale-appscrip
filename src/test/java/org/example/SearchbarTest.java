package org.example;
import Testcomponents.BaseTest;
import model.SignupPojo;
import org.testng.Assert;
import org.testng.annotations.Test;
public class SearchbarTest extends BaseTest
{
    SignupPojo signup = new SignupPojo();
    @Test(description = "Verify Email Field – Invalid Format (Missing @)")
    public void testcase015()
    {
        loginPage.clickprofileicon().clicksingup().fillEmail("madhangmail.com");
        Assert.assertEquals(loginPage.getEmailMsg(),"Enter Valid Email","Error message is missing for invalid email");
        Assert.assertFalse(true);
    }
    @Test(description = "Verify Email Field – Invalid Format (Missing Domain)")
    public void testcase016()
    {
        loginPage.clickprofileicon().clicksingup().fillEmail("madhan@gmail");
        Assert.assertEquals(loginPage.getEmailMsg(),"Enter Valid Email");
        Assert.assertTrue(false);
    }

}
