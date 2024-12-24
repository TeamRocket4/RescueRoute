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
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Handle potential CORS issues

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        hospitalPage = new HospitalPage(driver);
    }

    @BeforeEach
    public void navigateToHospitalPage() {
        driver.get("http://localhost:3000/dashboard/hospitals");
    }

    @Test
    public void testAddHospital() {
        hospitalPage.clickAddHospital();
        hospitalPage.fillHospitalDetails("Hopital Bno Sina", "Gueliz, Boulevard Victor Hugo Rue10", "21.664587", "73.996295");
        hospitalPage.saveHospital();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[td[text()='Hopital Bno Sina']]")));

        WebElement addedHospitalRow = driver.findElement(By.xpath("//tr[td[text()='Hopital Bno Sina']]"));
        Assertions.assertTrue(addedHospitalRow.isDisplayed(), "Hospital should be added to the list.");
    }


    @Test
    public void testEditHospital() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("editButton")));
        editButton.click();

        hospitalPage.updateHospital("Hopital Mohamed 6", "Gueliz, Boulevard Victor Hugo Rue10", "21.664587", "73.996295");

        WebElement updatedHospitalRow = driver.findElement(By.xpath("//tr[td[text()='Hopital Bno Sina']]"));
        Assertions.assertTrue(updatedHospitalRow.isDisplayed(), "Hospital should be updated in the list.");
    }



    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
