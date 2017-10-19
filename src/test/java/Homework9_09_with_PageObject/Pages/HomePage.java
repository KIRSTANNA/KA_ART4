package Homework9_09_with_PageObject.Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {

    BaseFunctions baseFunctions;

    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final By[] ARTICLE = {By.xpath("//h3[@class='top2012-title']"), By.xpath("//div[@class='md-mosaic-title']")};
    private static final By TITLE = By.tagName("a");
    private static final By[] COMMENT_COUNT = {By.className("comment-count"), By.className("commentCount")};

    public HomePage(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public List<WebElement> getAllArticles(int i) {
        LOGGER.info("Getting all articles on homepage");
        return baseFunctions.getElements(ARTICLE[i]);
    }

    public String getTitle(WebElement article) { //main release article title
        LOGGER.info("Getting Title");
        return article.findElement(TITLE).getText();
    }

    public String getUrl(WebElement article) { //main release article URL
        LOGGER.info("Getting URL");
        return article.findElement(TITLE).getAttribute("href");
    }

    public String getCommentCountUrl(WebElement article, int i) { // main release comment's amount
        LOGGER.info("Getting comment's URL");
        String CountUrl = article.findElement(COMMENT_COUNT[i]).getAttribute("href");
        return CountUrl;
    }

    public int getCommentCount(WebElement article, int i) { // main release comment's amount
        LOGGER.info("Getting comment count");
        String countText = "(0)";
        if (article.findElements(COMMENT_COUNT[i]).size()>0)
            countText = article.findElement(COMMENT_COUNT[i]).getText();
        return articleHelper.parseCount(countText);
    }
}
