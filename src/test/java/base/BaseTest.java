package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

public abstract class BaseTest {
    protected WebDriver driver;
    private String browserName;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        browserName = browser.toLowerCase();
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chrome = new ChromeOptions();
                chrome.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false));
                if (Boolean.getBoolean("headless")) chrome.addArguments("--headless=new");
                driver = new ChromeDriver(chrome);
                break;
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                if (Boolean.getBoolean("headless")) firefox.addArguments("-headless");
                driver = new FirefoxDriver(firefox);
                break;
            default:
                throw new IllegalArgumentException("Navegador no soportado: " + browser);
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        Reporter.log("Navegador: " + browserName + " "
                + ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion(), true);
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            try {
                Path screenshot = Path.of("target", "screenshots", browserName + "-" + result.getStartMillis() + ".png");
                Files.createDirectories(screenshot.getParent());
                Files.write(screenshot, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                Reporter.log("Captura final: " + screenshot, true);
                if (!result.isSuccess()) {
                    Files.writeString(Path.of(screenshot.toString().replace(".png", ".html")), driver.getPageSource());
                }
            } catch (Exception captureError) {
                Reporter.log("No fue posible guardar la captura: " + captureError.getMessage(), true);
            } finally {
                try {
                    driver.quit();
                } finally {
                    driver = null;
                }
            }
        }
    }
}
