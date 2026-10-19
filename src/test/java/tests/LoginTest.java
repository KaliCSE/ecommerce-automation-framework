package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;

public class LoginTest extends BaseTest {

    // Login success: Products page open aaganum
    @Test(groups = "smoke", description = "TC_LOGIN_01 - Login with valid username and password")
    public void validLogin() throws Exception {
        login(VALID_USER, VALID_PASSWORD);

        Assert.assertEquals(getPageTitle(), "Products", "Products page open aagala");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login-ku aprom URL thappa irukku");
    }

    // Correct username + thappana password (un name password) -> error varanum
    @Test(description = "TC_LOGIN_02 - Login with wrong password")
    public void wrongPassword() throws Exception {
        login(VALID_USER, MY_PASSWORD);

        Assert.assertTrue(getErrorMessage().contains("do not match any user"));
    }

    @Test(description = "TC_LOGIN_03 - Login with empty username")
    public void emptyUsername() throws Exception {
        login("", VALID_PASSWORD);

        Assert.assertTrue(getErrorMessage().contains("Username is required"));
    }

    @Test(description = "TC_LOGIN_04 - Login with empty password")
    public void emptyPassword() throws Exception {
        login(VALID_USER, "");

        Assert.assertTrue(getErrorMessage().contains("Password is required"));
    }

    @Test(description = "TC_LOGIN_05 - Login with locked out user")
    public void lockedOutUser() throws Exception {
        login("locked_out_user", VALID_PASSWORD);

        Assert.assertTrue(getErrorMessage().contains("locked out"));
    }

    // DataProvider: oru test-ai pala data-vodu run pannum. Ovvoru row = oru thadava run.
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
            // username,        password,         expected error text
            { MY_USERNAME,      VALID_PASSWORD,   "do not match any user" },   // kali@123 / secret_sauce
            { VALID_USER,       MY_PASSWORD,      "do not match any user" },   // standard_user / kaali@2203
            { MY_USERNAME,      MY_PASSWORD,      "do not match any user" },   // kali@123 / kaali@2203
            { "",               "",               "Username is required" },
            { "STANDARD_USER",  VALID_PASSWORD,   "do not match any user" }    // username case sensitive
        };
    }

    @Test(dataProvider = "invalidLoginData", description = "TC_LOGIN_06 - Login with different invalid data")
    public void invalidLoginWithDifferentData(String username, String password, String expectedError) throws Exception {
        login(username, password);

        Assert.assertTrue(getErrorMessage().contains(expectedError));
    }

    @Test(description = "TC_LOGIN_07 - Logout returns to login page")
    public void logout() throws Exception {
        login(VALID_USER, VALID_PASSWORD);

        driver.findElement(By.id("react-burger-menu-btn")).click();     // left top menu
        Thread.sleep(1000);
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.findElement(By.id("login-button")).isDisplayed(), "Logout-ku aprom login page varala");
    }
}
