package example.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HospitalPage {
    WebDriver driver;
    WebDriverWait wait;

    private final By addHospitalButton = By.xpath("//button[contains(text(),'Add Hospital')]");
    private final By editButton = By.id("editButton");
    private final By hospitalNameField = By.id("name");
    private final By hospitalAddressField = By.id("address");
    private final By latitudeField = By.id("latitude");
    private final By longitudeField = By.id("longitude");
    private final By saveButton = By.id("AddHospitalButton");
    private final By hospitalEditNameField = By.id("editName");
    private final By hospitalEditAddressField = By.id("editAddress");
    private final By hospitalEditLatitudeField = By.id("editLatitude");
    private final By hospitalEditLongitudeField = By.id("editLongitude");
    private final By updateButton = By.id("UpdateButton");

    public HospitalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait

    }

    public void clickAddHospital() {
        driver.findElement(addHospitalButton).click();
    }

    public void clickEditHospital() {
        driver.findElement(editButton).click();
    }

    public void fillHospitalDetails(String name, String address, String latitude, String longitude) {
        driver.findElement(hospitalNameField).clear();
        driver.findElement(hospitalNameField).sendKeys(name);
        driver.findElement(hospitalAddressField).clear();
        driver.findElement(hospitalAddressField).sendKeys(address);
        driver.findElement(latitudeField).clear();
        driver.findElement(latitudeField).sendKeys(latitude);
        driver.findElement(longitudeField).clear();
        driver.findElement(longitudeField).sendKeys(longitude);
    }

    public void saveHospital() {
        driver.findElement(saveButton).click();
    }

    public void updateHospital(String name, String address, String latitude, String longitude) {
        WebElement editNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("editName")));

        driver.findElement(hospitalEditNameField).clear();
        driver.findElement(hospitalEditNameField).sendKeys(name);

        driver.findElement(hospitalEditAddressField).clear();
        driver.findElement(hospitalEditAddressField).sendKeys(address);

        driver.findElement(hospitalEditLatitudeField).clear();
        driver.findElement(hospitalEditLatitudeField).sendKeys(latitude);

        driver.findElement(hospitalEditLongitudeField).clear();
        driver.findElement(hospitalEditLongitudeField).sendKeys(longitude);

        driver.findElement(updateButton).click();
    }
}
