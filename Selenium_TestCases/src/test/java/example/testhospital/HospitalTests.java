package example.testhospital;

import example.pageobjects.HospitalPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HospitalTests {
    private WebDriver driver;
    private HospitalPage hospitalPage;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        // Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");

        // Configure ChromeDriver options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Handle potential CORS issues

        // Initialize WebDriver and WebDriverWait
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize the HospitalPage
        hospitalPage = new HospitalPage(driver);
    }

    @BeforeEach
    public void navigateToHospitalPage() {
        // Navigate to the hospital page before each test
        driver.get("http://localhost:3000/dashboard/hospitals");
    }

    @Test
    public void testAddHospital() {
        // Test adding a hospital
        hospitalPage.clickAddHospital();
        hospitalPage.fillHospitalDetails("Hopital Bno Sina", "Gueliz, Boulevard Victor Hugo Rue10", "21.664587", "73.996295");
        hospitalPage.saveHospital();

        // Wait for the table to update (e.g., after the new row is added)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[td[text()='Hopital Bno Sina']]")));

        // Assert that the hospital is in the table by checking the row
        WebElement addedHospitalRow = driver.findElement(By.xpath("//tr[td[text()='Hopital Bno Sina']]"));
        Assertions.assertTrue(addedHospitalRow.isDisplayed(), "Hospital should be added to the list.");
    }


    @Test
    public void testEditHospital() {
        // Test editing a hospital
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the Edit button to be visible and clickable
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("editButton")));
        // Click the Edit button
        editButton.click();

        // Call the updateHospital method to fill in the new details
        hospitalPage.updateHospital("Hopital Mohamed 6", "Gueliz, Boulevard Victor Hugo Rue10", "21.664587", "73.996295");

        // Optional: Check if the updated hospital is present in the table
        WebElement updatedHospitalRow = driver.findElement(By.xpath("//tr[td[text()='Hopital Bno Sina']]"));
        Assertions.assertTrue(updatedHospitalRow.isDisplayed(), "Hospital should be updated in the list.");
    }



    @AfterAll
    public void tearDown() {
        // Clean up WebDriver after all tests are completed
        if (driver != null) {
            driver.quit();
        }
    }
}
