package example.TestHospital;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestEditHospital {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("http://localhost:3000/dashboard/hospitals");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Click edit button (keeping original since it works)
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("editButton")));
            editButton.click();
            System.out.println("Edit button clicked successfully.");

            // Wait a moment for modal to appear
            Thread.sleep(1000);

            // Use the provided XPaths for each field
            WebElement hospitalName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[5]/div[2]/div[1]/input")));
            hospitalName.clear();
            hospitalName.sendKeys("Hopital Bno Sina");

            WebElement address = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[5]/div[2]/div[2]/input")));
            address.clear();
            address.sendKeys("Gueliz , Boulevard victor hugo Rue10");

            WebElement latitude = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[5]/div[2]/div[3]/div[1]/input")));
            latitude.clear();
            latitude.sendKeys("21.664587");

            WebElement longitude = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[5]/div[2]/div[3]/div[2]/input")));
            longitude.clear();
            longitude.sendKeys("73.996295");

            // Click update button using provided XPath
            WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[5]/button[1]")));
            updateButton.click();
            System.out.println("Hospital updated successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred during Selenium script execution:");
            e.printStackTrace();
        } finally {
            // Wait for 5 seconds before closing the browser
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}