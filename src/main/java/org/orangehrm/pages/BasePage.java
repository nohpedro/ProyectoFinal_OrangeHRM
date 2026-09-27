package org.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private final By busyOverlay = By.cssSelector(".oxd-form-loader, .oxd-loading-spinner");
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        wait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(busyOverlay)));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        // La validacion asincrona de ID/username puede activar una capa entre
        // elementToBeClickable y click. Solo se reintenta si el clic no se entrego.
        wait.until(webDriver -> {
            try {
                if (driver.findElements(busyOverlay).stream().anyMatch(WebElement::isDisplayed)) return false;
                WebElement element = ExpectedConditions.elementToBeClickable(locator).apply(driver);
                if (element == null) return false;
                element.click();
                return true;
            } catch (ElementClickInterceptedException | StaleElementReferenceException loading) {
                return false;
            }
        });
    }

    protected void write(By locator, String value) {
        WebElement input = waitForClickable(locator);
        // Ctrl+A tambien actualiza correctamente los campos reactivos de Vue.
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(value);
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }
}
