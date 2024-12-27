package example.TestUser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestAddUser {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("http://localhost:3000/dashboard/users");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Open the "Add User" dialog
            WebElement addUserButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Add User')]")));
            addUserButton.click();

            Thread.sleep(2000);

            // Fill in user details
            WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/input")));
            firstName.sendKeys("userfirstname");
            Thread.sleep(500);

            WebElement lastName = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/input")));
            lastName.sendKeys("userlastname");
            Thread.sleep(500);

            WebElement email = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[2]/input")));
            email.sendKeys("usernamemail@gmail.com");
            Thread.sleep(500);

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[3]/input")));
            password.sendKeys("password123");
            Thread.sleep(500);

            // Handle Role dropdown
            WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[4]/button")));
            roleDropdown.click();
            Thread.sleep(1000);

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

            // Fill in birth date
            WebElement birthDate = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/div[2]/div[6]/input")));
            birthDate.sendKeys("11292001");
            Thread.sleep(500);

            // Click the Add User button in the modal
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html/body/div[3]/button[1]")));
            submitButton.click();
            Thread.sleep(5000);

            System.out.println("User added successfully!");

            // Wait before closing
            Thread.sleep(5000);

        } catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        } finally {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}