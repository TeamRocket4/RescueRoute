package example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login_Test {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver" , "D:\\Projects\\School Projects\\RescueRoute\\Selenium_TestCases\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:3000/");
            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys("elbouzidihamza5@gmail.com");

            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("hamza2001");

            password.sendKeys(Keys.ENTER);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.urlToBe("http://localhost:3000/dashboard/users"));
            String current_url = driver.getCurrentUrl();

             String expected_url = "http://localhost:3000/dashboard/users";
            if (current_url.equals(expected_url)){
                System.out.println("Login succesful");
                System.out.println(current_url);
            }else{
                System.out.println("Login failed");
                System.out.println(current_url);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}