package base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/*
 * BaseTest = ellaa test class-kum common ana velai inga irukku.
 * Ellaa test class-um "extends BaseTest" nu ezhudhum, appo indha code automatic-ah kidaikkum.
 *
 *   @BeforeMethod -> ovvoru test-ku MUNNADI Chrome open aagi SauceDemo-ku poidum
 *   @AfterMethod  -> ovvoru test-ku APPURAM Chrome close aagum
 */
public class BaseTest {

    protected WebDriver driver;

    public static final String BASE_URL = "https://www.saucedemo.com";

    // SauceDemo-oda unmaiyaana demo user (idhu mattum thaan login aagum)
    public static final String VALID_USER = "standard_user";
    public static final String VALID_PASSWORD = "secret_sauce";

    // Un name vachu username / password. SauceDemo-la register option illa,
    // adhanaala ivanga login aagaadhu, "error message" varanum (negative testing).
    public static final String MY_USERNAME = "kali@123";
    public static final String MY_PASSWORD = "kaali@2203";

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();

        // Chrome-oda "Save password / password leaked" pop-up test-ai thadukkaama irukka
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = ture)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // TestListener (screenshot edukka) idhai use pannum
    public WebDriver getDriver() {
        return driver;
    }

    // ---------- Common methods (pala test-la use aagum, adhanaala inga vechirukkom) ----------

    // Username, password type panni Login click pannum
    public void login(String username, String password) throws Exception {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1500);
    }

    // Login fail aana neram screen-la varra red error message-oda text
    // Inga XPath use pannirukkom: //*[@data-test='error'] = data-test="error" irukkura edhuvaa irundhaalum
    public String getErrorMessage() {
        return driver.findElement(By.xpath("//*[@data-test='error']")).getText();
    }

    // Page-oda title ("Products", "Your Cart"...)
    public String getPageTitle() {
        return driver.findElement(By.className("title")).getText();
    }

    // productId example: "sauce-labs-backpack"
    public void addToCart(String productId) throws Exception {
        driver.findElement(By.id("add-to-cart-" + productId)).click();
        Thread.sleep(1000);
    }

    public void openCart() throws Exception {
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1500);
    }

    // Cart icon-la theriyura number. Number illa na cart kaali, adhanaala 0.
    public int getCartCount() {
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));
        if (badge.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badge.get(0).getText());
    }
}
