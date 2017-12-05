package homework9_09_with_PageObject.pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseFunctions {

    WebDriver driver;
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\JK\\chromedriver_win32\\chromedriver.exe";

    public BaseFunctions() {
        LOGGER.info("Setting system properties");
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        LOGGER.info("Starting chrome driver");
        this.driver = new ChromeDriver();

        LOGGER.info("Maximize browser window");
        driver.manage().window().maximize();
    }

    public void driverQuit() {
        driver.quit();
    }

    public void goToURL(String url) {
        LOGGER.info("Open URL: " + url);
        driver.get(url);
    }

    public WebElement getElement(By locator) {
        LOGGER.info("Getting element");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    public List<WebElement> getElements(By locator) {
        LOGGER.info("Getting a lot of elements");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        List<WebElement> main_all = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return main_all;
    }

    public void clickElement(By locator) {
        LOGGER.info("Click element");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}