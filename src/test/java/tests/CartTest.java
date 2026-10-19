package tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;

public class CartTest extends BaseTest {

    @BeforeMethod
    public void loginFirst() throws Exception {
        login(VALID_USER, VALID_PASSWORD);
    }

    // Cart page-la ethana product irukku
    private int getCartItemCount() {
        return driver.findElements(By.className("cart_item")).size();
    }

    // Cart page-la irukkura product names
    private List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(By.className("inventory_item_name"))) {
            names.add(element.getText());
        }
        return names;
    }

    @Test(description = "TC_CART_01 - Added product is shown in the cart")
    public void addedProductIsInCart() throws Exception {
        addToCart("sauce-labs-backpack");
        openCart();

        Assert.assertEquals(getPageTitle(), "Your Cart");
        Assert.assertTrue(getCartItemNames().contains("Sauce Labs Backpack"));
    }

    @Test(description = "TC_CART_02 - Multiple products are shown in the cart")
    public void multipleProductsInCart() throws Exception {
        addToCart("sauce-labs-backpack");
        addToCart("sauce-labs-bike-light");
        openCart();

        Assert.assertEquals(getCartItemCount(), 2);
    }

    @Test(description = "TC_CART_03 - Remove product from the cart page")
    public void removeProductFromCart() throws Exception {
        addToCart("sauce-labs-backpack");
        openCart();
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getCartItemCount(), 0);
    }

    @Test(description = "TC_CART_04 - Continue Shopping goes back to Products page")
    public void continueShopping() throws Exception {
        openCart();
        driver.findElement(By.id("continue-shopping")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getPageTitle(), "Products");
    }

    @Test(description = "TC_CART_05 - Cart keeps its items after going back to Products page")
    public void cartKeepsItems() throws Exception {
        addToCart("sauce-labs-backpack");
        openCart();
        driver.findElement(By.id("continue-shopping")).click();
        Thread.sleep(1000);

        Assert.assertEquals(getCartCount(), 1);
    }
}
