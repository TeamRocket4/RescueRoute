package example.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    private final By addUserButton = By.xpath("//button[contains(text(),'Add User')]");
    private final By editButton = By.id("editButton");
    private final By firstNameField = By.xpath("/html/body/div[3]/div[2]/div[1]/div[1]/input");
    private final By lastNameField = By.xpath("/html/body/div[3]/div[2]/div[1]/div[2]/input");
    private final By emailField = By.xpath("/html/body/div[3]/div[2]/div[2]/input");
    private final By passwordField = By.xpath("/html/body/div[3]/div[2]/div[3]/input");
    private final By roleDropdown = By.xpath("/html/body/div[3]/div[2]/div[4]/button");
    private final By dispatcherOption = By.xpath("//span[text()='Dispatcher']");
    private final By statusDropdown = By.xpath("/html/body/div[3]/div[2]/div[5]/button");
    private final By busyOption = By.xpath("//span[text()='Busy']");
    private final By birthDateField = By.xpath("/html/body/div[3]/div[2]/div[6]/input");
    private final By submitButton = By.xpath("/html/body/div[3]/button[1]");

    public UserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickAddUser() {
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addUserButton));
        addButton.click();
    }

    public void clickEditUser() {
        WebElement edit = wait.until(ExpectedConditions.elementToBeClickable(editButton));
        edit.click();
    }

    public void fillUserDetails(String firstName, String lastName, String email, String password, String birthDate) {
        try {
            Thread.sleep(1000); // Wait for modal

            WebElement firstNameElement = wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
            firstNameElement.clear();
            firstNameElement.sendKeys(firstName);

            WebElement lastNameElement = wait.until(ExpectedConditions.elementToBeClickable(lastNameField));
            lastNameElement.clear();
            lastNameElement.sendKeys(lastName);

            WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailField));
            emailElement.clear();
            emailElement.sendKeys(email);

            if (password != null) {  // Only fill password for new users
                WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
                passwordElement.clear();
                passwordElement.sendKeys(password);
            }

            WebElement birthDateElement = wait.until(ExpectedConditions.elementToBeClickable(birthDateField));
            birthDateElement.clear();
            birthDateElement.sendKeys(birthDate);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectRole() {
        try {
            WebElement roleDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(roleDropdown));
            roleDropdownElement.click();
            Thread.sleep(1000);

            WebElement dispatcherElement = wait.until(ExpectedConditions.elementToBeClickable(dispatcherOption));
            dispatcherElement.click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectStatus() {
        try {
            WebElement statusDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(statusDropdown));
            statusDropdownElement.click();
            Thread.sleep(1000);

            WebElement busyElement = wait.until(ExpectedConditions.elementToBeClickable(busyOption));
            busyElement.click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void submitUser() {
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submit.click();
    }

    public void addNewUser(String firstName, String lastName, String email, String password, String birthDate) {
        clickAddUser();
        fillUserDetails(firstName, lastName, email, password, birthDate);
        selectRole();
        selectStatus();
        submitUser();
    }

    public void updateUser(String firstName, String lastName, String email, String birthDate) {
        clickEditUser();
        fillUserDetails(firstName, lastName, email, null, birthDate);
        selectRole();
        selectStatus();
        submitUser();
    }
}