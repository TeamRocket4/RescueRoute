package example.testuser;

import example.pageobjects.UserPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTests {
    private WebDriver driver;
    private UserPage userPage;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        userPage = new UserPage(driver);
    }

    @BeforeEach
    public void navigateToUserPage() {
        driver.get("http://localhost:3000/dashboard/users");
        // Wait for page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Add User')]")));
    }

    @Test
    public void testAddUser() {
        try {
            userPage.addNewUser(
                    "userfirstname",
                    "userlastname",
                    "usernamemail@gmail.com",
                    "password123",
                    "11292001"
            );

            // Wait and verify
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//td[text()='userfirstname']")
            ));

            Assertions.assertTrue(
                    driver.findElement(By.xpath("//td[text()='userfirstname']"))
                            .isDisplayed(),
                    "Added user should be visible in the table"
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
        }
    }

    @Test
    public void testEditUser() {
        try {
            userPage.updateUser(
                    "Zakaria",
                    "Bouzidi",
                    "zakariabouzidi@gmail.com",
                    "11292001"
            );

            // Wait and verify
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//td[text()='Zakaria']")
            ));

            Assertions.assertTrue(
                    driver.findElement(By.xpath("//td[text()='Zakaria']"))
                            .isDisplayed(),
                    "Updated user name should be visible in the table"
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to edit user: " + e.getMessage(), e);
        }
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}