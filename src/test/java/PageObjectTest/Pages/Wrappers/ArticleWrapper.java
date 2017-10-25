package PageObjectTest.Pages.Wrappers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PageObjectTest.Pages.BaseFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticleWrapper {
    private final BaseFunctions baseFunctions;
    private final WebElement element;

    private static final By TITLE = By.tagName("a");

    public ArticleWrapper(BaseFunctions commonFunctions, WebElement element) {
        this.baseFunctions = commonFunctions;
        this.element = element;
    }

    public String getArticleTitle() {
        return element.findElements(TITLE).isEmpty() ? null : element.findElement(TITLE).getText();
    }

    public String getArticleComm() {
        return (element.findElements(TITLE).size()<2) ? null : element.findElements(TITLE).get(1).getText();
    }


    public void clickOnTitle() {
        element.click();
    }
}
