package org.example;
import Testcomponents.BaseTest;
import model.SignupPojo;
import org.testng.Assert;
import org.testng.annotations.Test;
public class SearchbarTest extends BaseTest
{
    SignupPojo signup = new SignupPojo();
    @Test(description = "Verify Profile Icon Visibility on Landing Page")
    public void testcase001()
    {
        boolean flag = loginPage.isprofilepresent();
        Assert.assertFalse(flag);
    }



    @Test(description = "Verify Click on Profile Icon Opens Sign In Modal")
    public void testcase002()
    {
        loginPage.clickprofileicon();
        Assert.assertFalse(loginPage.ispopupresent());
    }
}
