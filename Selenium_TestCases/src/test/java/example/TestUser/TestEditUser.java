package example.TestUser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestEditUser {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("http://localhost:3000/dashboard/users");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Click edit button (keeping original since it works)
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("editButton")));
            editButton.click();
            System.out.println("Edit button clicked successfully.");

            // Wait a moment for modal to appear
            Thread.sleep(1000);

            // Use the provided XPaths for each field
            WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/input")));
            firstName.clear();
            firstName.sendKeys("Zakaria");

            WebElement lastName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/input")));
            lastName.clear();
            lastName.sendKeys("Bouzidi");

            WebElement email = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[2]/input")));
            email.clear();
            email.sendKeys("zakariabouzidi@gmail.com");

            // Look for span element containing "Driver" text
            WebElement driverOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Dispatcher']")));
            driverOption.click();
            Thread.sleep(1000);

            // Handle Status dropdown
            WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[5]/button")));
            statusDropdown.click();
            Thread.sleep(1000);

            // Look for span element containing "Standby" text
            WebElement standbyOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[text()='Busy']")));
            standbyOption.click();
            Thread.sleep(1000);

            WebElement birthDate = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[6]/input")));
            birthDate.sendKeys("11292001");
            Thread.sleep(500);


            System.out.println("User updated successfully!");

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
