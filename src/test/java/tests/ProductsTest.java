package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;

public class ProductsTest extends BaseTest {

    // Ovvoru test-ku MUNNADI login pannum (BaseTest-oda browser open aana aprom run aagum)
    @BeforeMethod
    public void loginFirst() throws Exception {
        login(VALID_USER, VALID_PASSWORD);
    }

    // Sort dropdown-la option select pannum (dropdown-ku Select class use pannuvom)
    private void sortBy(String optionText) throws Exception {
        Select dropdown = new Select(driver.findElement(By.className("product_sort_container")));
        dropdown.selectByVisibleText(optionText);
        Thread.sleep(1000);
    }

    // Screen-la irukkura ella product name-aiyum list-la edukkum
    private List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : driver.findElements(By.className("inventory_item_name"))) {
            names.add(element.getText());
        }
        return names;
    }

    // Ella price-aiyum edukkum ("$29.99" -> 29.99)
    private List<Double> getAllPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement element : driver.findElements(By.className("inventory_item_price"))) {
            prices.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        return prices;
    }

    @Test(description = "TC_PROD_01 - 6 products are displayed")
    public void sixProductsAreDisplayed() {
        int count = driver.findElements(By.className("inventory_item")).size();
        Assert.assertEquals(count, 6);
    }

    @Test(description = "TC_PROD_02 - Sort by Name (A to Z)")
    public void sortNameAToZ() throws Exception {
        sortBy("Name (A to Z)");

        List<String> actual = getAllNames();
        List<String> expected = new ArrayList<>(actual);   // copy
        Collections.sort(expected);                         // A to Z
        Assert.assertEquals(actual, expected, "Products A to Z-la sort aagala");
    }

    @Test(description = "TC_PROD_03 - Sort by Name (Z to A)")
    public void sortNameZToA() throws Exception {
        sortBy("Name (Z to A)");

        List<String> actual = getAllNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());          // Z to A
        Assert.assertEquals(actual, expected, "Products Z to A-la sort aagala");
    }

    @Test(description = "TC_PROD_04 - Sort by Price (low to high)")
    public void sortPriceLowToHigh() throws Exception {
        sortBy("Price (low to high)");

        List<Double> actual = getAllPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Price low to high-la sort aagala");
    }

    @Test(description = "TC_PROD_05 - Sort by Price (high to low)")
    public void sortPriceHighToLow() throws Exception {
        sortBy("Price (high to low)");

        List<Double> actual = getAllPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Price high to low-la sort aagala");
    }

    @Test(groups = "smoke", description = "TC_PROD_06 - Add one product, cart badge shows 1")
    public void addOneProduct() throws Exception {
        addToCart("sauce-labs-backpack");
        Assert.assertEquals(getCartCount(), 1);
    }

    @Test(description = "TC_PROD_07 - Add 3 products, cart badge shows 3")
    public void addThreeProducts() throws Exception {
        addToCart("sauce-labs-backpack");
        addToCart("sauce-labs-bike-light");
        addToCart("sauce-labs-onesie");
        Assert.assertEquals(getCartCount(), 3);
    }

    @Test(description = "TC_PROD_08 - Add then remove a product, badge disappears")
    public void addAndRemoveProduct() throws Exception {
        addToCart("sauce-labs-backpack");
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        Thread.sleep(1000);
        Assert.assertEquals(getCartCount(), 0);
    }
}
