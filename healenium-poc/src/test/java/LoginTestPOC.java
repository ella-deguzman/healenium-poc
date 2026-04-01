import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTestPOC {

    private SelfHealingDriver driver;
    private WebDriverWait wait;


    @BeforeEach
    void setup() throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.setCapability("healenium:heal-enabled", true);

        WebDriver delegate = new RemoteWebDriver(
                new URL("http://localhost:8085/wd/hub"),
                options
        );

        driver = SelfHealingDriver.create(delegate);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testLogin() {

        driver.get("http://host.docker.internal:8000/login.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                .sendKeys("test");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")))
                .sendKeys("test");

        WebElement login = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login-button"))
        );

        login.click();

        driver.quit();

        try { Thread.sleep(6000); } catch (Exception ignored) {}
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}