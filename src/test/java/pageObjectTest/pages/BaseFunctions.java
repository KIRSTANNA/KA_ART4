package pageObjectTest.pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BaseFunctions {
    WebDriver driver;
    Boolean isArticleExist = true;
    Boolean isCommentsExist = true;

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\JK\\chromedriver_win32\\chromedriver.exe";

    /**
     * Method starts the driver */
    public BaseFunctions() {
        LOGGER.info("Setting system properties");
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        LOGGER.info("Starting chrome driver");
        this.driver = new ChromeDriver();

        LOGGER.info("Maximize browser window");
        driver.manage().window().maximize();

    }

    /**
     * Method stop the driver */
    public void driverQuit() {
        driver.quit();
    }

    public void goToURL(String url) {
        LOGGER.info("Open URL: " + url);
        driver.get(url);
    }

    /**
     * Method returns element with a specific locator
     *
     * @param locator element locator to search
     * @return WebElement
     */
    public WebElement getElement(By locator) {
        LOGGER.info("Getting element");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    /**
     * Method find element with a specific locator and click on it
     *
     * @param locator element locator to search
     */
    public void clickElement(By locator) {
        LOGGER.info("Click element");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    /*
     * Method is waiting for element to be added in DOM
     *
     * @param element - element to wait
     * @param mills - max time to wait in milliseconds
    */
   public void waitDisplayElement(final By element, long mills) {
        Boolean pageCommentsCheckStart = (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return webDriver.findElement(element).isDisplayed();
            }
        });
    }

    /**
     * Method returns a list of elements with a specific locator
     *
     * @param element element locator to search
     * @return list of WebElements
     */
    public List<WebElement> findElements(By element) {
        return driver.findElements(element);
    }

}

