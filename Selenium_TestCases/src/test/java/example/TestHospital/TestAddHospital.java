package example.TestHospital;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestAddHospital {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:3000/dashboard/hospitals");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Open the "Add User" dialog
            WebElement addHospital1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Hospital')]")));
            addHospital1.click();

            // Fill in user details
            WebElement hospitalname = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            hospitalname.sendKeys("Hopital Mohammed 5");

            WebElement hospitaladress = driver.findElement(By.id("address"));
            hospitaladress.sendKeys("Quartier SAADA");

            WebElement latitude = driver.findElement(By.id("latitude"));
            latitude.sendKeys("31.664587,");

            WebElement logtitude = driver.findElement(By.id("longitude"));
            logtitude.sendKeys("7.996295");

            WebElement addHospitalButton = driver.findElement(By.id("AddHospitalButton"));
            addHospitalButton.click();

            System.out.println("hospital added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
