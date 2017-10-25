package PageObjectTest.Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

//import java.util.logging.Logger;

public class BaseFunctions {
    WebDriver driver;
    ArticleHelper articleHelper;
    //  private static final String FIREFOX_DRIVER_PATH = "c:/geckodriver.exe";

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

