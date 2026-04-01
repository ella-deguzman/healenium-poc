import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest2 {

    private WebDriver driver;
    private WebDriverWait wait;

//    @BeforeEach
//    void setup() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }


////worked with
//    @BeforeEach
//    void setup() throws Exception {
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//
//        driver = new RemoteWebDriver(
//                new URL("http://localhost:4444/wd/hub"),
//                options
//        );
//
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

//    }
//
//    @BeforeEach
//    void setup() throws Exception {
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//
//        WebDriver delegate = new RemoteWebDriver(
//                new URL("http://localhost:8085"),
//                options
//        );
//
//        driver = SelfHealingDriver.create(delegate);
//
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }





//    @BeforeEach
//    void setup() throws Exception {
//
//        ChromeOptions options = new ChromeOptions();
////        options.addArguments("--no-sandbox");
////        options.addArguments("--disable-dev-shm-usage");
//
//        WebDriver delegate = new RemoteWebDriver(
//                new URL("http://localhost:8085/wd/hub"),   // ← change ONLY this
//                options
//        );
//
//        driver = SelfHealingDriver.create(delegate); // ← wrap it
//
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }

    @BeforeEach
    void setup() throws Exception {

        ChromeOptions options = new ChromeOptions();

        driver = new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testLogin() {
        driver.get("https://www.saucedemo.com");

        // Wait for username field
        WebElement username = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))
        );
        username.sendKeys("standard_user");

        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password"))
        );
        password.sendKeys("secret_sauce");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login-button"))
        );
        loginButton.click();

        // Wait for inventory page to load
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list"))
        );

        // Assertion to verify login success
        assertTrue(driver.getCurrentUrl().contains("inventory"));

        System.out.println("Login successful to SauceDemo.");
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}