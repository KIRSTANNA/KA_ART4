package pageObjectTest.pages.wrappers;

import org.apache.logging.log4j.LogManager;
import pageObjectTest.pages.BaseFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticleWrapper {

    private final BaseFunctions baseFunctions;
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private final WebElement element;

    private static final By TITLE = By.tagName("a");

    public ArticleWrapper(BaseFunctions commonFunctions, WebElement element) {
        this.baseFunctions = commonFunctions;
        this.element = element;
    }

    /**
     * Method returns article title for ArticleWrapper
     *
     * @return String title of article has comments, otherwise null
     */
    public String getArticleTitle() {
        return element.findElements(TITLE).isEmpty() ? null : element.findElement(TITLE).getText();
    }

    /**
     * Method returns informations about number of comments for ArticleWrapper
     *
     * @return String as "(comments number)" article has comments, otherwise ""
     */
    public String getArticleComm() {
        return (element.findElements(TITLE).size() < 2) ? "" : element.findElements(TITLE).get(1).getText();
    }

    /**
     * Method click on the article title for ArticleWrapper
     */
    public void clickOnTitle() {
        element.findElements(TITLE).get(0).click();
    }

    /**
     * Method click on the comments number for ArticleWrapper, if article has comments
     *
     * @return Boolean if comments exist or not
     */
    public Boolean clickOnComm() {
        Boolean isCommentsExist = true;
        if (element.findElements(TITLE).size() > 1)
            element.findElements(TITLE).get(1).click();
        else {
            LOGGER.info("There aren't comments!");
            isCommentsExist = false;
        }
        return isCommentsExist;
    }
}
