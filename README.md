# SauceDemo QA Automation Project

Manual + automation testing of the SauceDemo e-commerce website (https://www.saucedemo.com).

**Tools:** Java 17+, Selenium WebDriver 4, TestNG, Maven

## What is tested
| Module   | What we check |
|----------|---------------|
| Login    | valid login, wrong password, empty fields, locked-out user, invalid data (DataProvider), logout, error checks with my own name-based username / password |
| Products | 6 products shown, sorting (name and price), add / remove product, cart badge count |
| Cart     | product shown in cart, multiple products, remove, continue shopping |
| Checkout | complete order, required-field errors, item total |

`docs/SauceDemo_Test_Cases_and_Bug_Report.xlsx` has 37 manual test cases (29 are automated) and a bug report sheet.

**About the login error tests:** SauceDemo has no sign-up, only its own demo users can log in.
So usernames and passwords like `kali@123` / `kaali@2203` are used as invalid data, and the tests check that the correct
error message is shown and the user stays on the login page (negative testing).

## Project structure
```
SauceDemoAutomation
 |-- pom.xml                      Maven: Selenium + TestNG libraries
 |-- testng.xml                   full suite
 |-- testng-smoke.xml             only tests marked groups = "smoke"
 |-- src/test/java/base           BaseTest (opens / closes Chrome + common methods like login())
 |-- src/test/java/tests          LoginTest, KaliLoginTest, ProductsTest, CartTest, CheckoutTest
 |-- src/test/java/utils          TestListener (screenshot on failure)
 |-- docs                         test cases + bug report (Excel)
```

## How to run
1. Install JDK 17 or higher and Google Chrome. Maven is needed only for the command line.
2. Command line:
```
mvn clean test                              (full suite, opens Chrome)
mvn clean test -Dsuite=testng-smoke.xml     (smoke tests only)
```
In Eclipse: File > Import > Maven > Existing Maven Projects, then right-click `testng.xml` > Run As > TestNG Suite.
In IntelliJ: File > Open the folder, then right-click `testng.xml` > Run.

Chromedriver is downloaded automatically by Selenium Manager (no manual setup).
Failed tests save a screenshot in the `screenshots` folder.

## Key points
- **BaseTest:** a fresh Chrome for every test (@BeforeMethod / @AfterMethod), so tests do not depend on each other.
- **Locators:** id, className, cssSelector and XPath.
- **DataProvider:** one test runs with many sets of invalid login data.
- **Groups:** smoke tests can be run alone.
- **Assertions:** TestNG Assert checks the page title, URL, error messages, product order and cart count.
- **Listener:** a screenshot is taken automatically when a test fails.
