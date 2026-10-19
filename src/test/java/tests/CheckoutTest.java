package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;

public class CheckoutTest extends BaseTest {

    // Ovvoru test-ku MUNNADI: login -> oru product add -> cart open -> Checkout click
    @BeforeMethod(alwaysRun = true)
    public void reachCheckoutPage() throws Exception {
        login(VALID_USER, VALID_PASSWORD);
        addToCart("sauce-labs-backpack");
        openCart();
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1500);
    }

    // "Your Information" form-ai fill panni Continue click pannum
    private void enterInformation(String firstName, String lastName, String postalCode) throws Exception {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1500);
    }

    @Test(groups = "smoke", description = "TC_CHK_01 - Complete an order end to end")
    public void completeOrder() throws Exception {
        enterInformation("Kaliraj", "K", "600001");
        Assert.assertEquals(getPageTitle(), "Checkout: Overview");

        driver.findElement(By.id("finish")).click();
        Thread.sleep(1500);
        String message = driver.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(message, "Thank you for your order!");
    }

    @Test(description = "TC_CHK_02 - First name is required")
    public void firstNameRequired() throws Exception {
        enterInformation("", "K", "600001");
        Assert.assertTrue(getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "TC_CHK_03 - Last name is required")
    public void lastNameRequired() throws Exception {
        enterInformation("Kaliraj", "", "600001");
        Assert.assertTrue(getErrorMessage().contains("Last Name is required"));
    }

    @Test(description = "TC_CHK_04 - Postal code is required")
    public void postalCodeRequired() throws Exception {
        enterInformation("Kaliraj", "K", "");
        Assert.assertTrue(getErrorMessage().contains("Postal Code is required"));
    }

    @Test(description = "TC_CHK_05 - All fields empty shows First Name error")
    public void allFieldsEmpty() throws Exception {
        enterInformation("", "", "");
        Assert.assertTrue(getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "TC_CHK_06 - Item total on overview page is correct")
    public void itemTotalIsCorrect() throws Exception {
        enterInformation("Kaliraj", "K", "600001");
        // Sauce Labs Backpack price $29.99
        String total = driver.findElement(By.className("summary_subtotal_label")).getText();
        Assert.assertTrue(total.contains("29.99"));
    }
}
