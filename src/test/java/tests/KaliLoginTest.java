package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

/*
 * Pudhu test class: un own name vachu login panna try pannum.
 * SauceDemo-la register panna mudiyaadhu, adhanaala indha username / password work aagaadhu.
 * Namba check pannurathu: "Error message sariyaa varudhaa, login aagaama irukkaa" (negative testing).
 */
public class KaliLoginTest extends BaseTest {

    String myNewUsername = "kaliraj@123";
    String myNewPassword = "kaliraj@2203";

    @Test(description = "TC_LOGIN_11 - Login with my own name as username and password")
    public void myNameAsUsernameAndPassword() throws Exception {
        login(myNewUsername, myNewPassword);

        Assert.assertTrue(getErrorMessage().contains("do not match any user"));
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"), "Un name-oda login aagida koodaadhu");
    }

    @Test(description = "TC_LOGIN_12 - My name as username with the valid SauceDemo password")
    public void myNameWithValidPassword() throws Exception {
        login(myNewUsername, VALID_PASSWORD);

        Assert.assertTrue(getErrorMessage().contains("do not match any user"));
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"), "Un name-oda login aagida koodaadhu");
    }

    @Test(description = "TC_LOGIN_13 - Valid SauceDemo username with my name as password")
    public void validUsernameWithMyPassword() throws Exception {
        login(VALID_USER, myNewPassword);

        Assert.assertTrue(getErrorMessage().contains("do not match any user"));
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"), "Thappana password-oda login aagida koodaadhu");
    }
}
