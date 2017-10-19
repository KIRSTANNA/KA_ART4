package Homework9_09_with_PageObject.Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;

public class ArticlePage {
    BaseFunctions baseFunctions;
    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final By[] TITLE = {By.xpath("//h1[@class='article-title']"),By.xpath("//div[@class='article-title']/h1")};
    //private static final By[] COMMENT_COUNT =  {By.xpath("//a[@class='comment-count']"),By.xpath("//div[@class='article-title']/a[@class='commentCount']")};
    private static final By[] COMMENT_COUNT =  {By.xpath("//a[@class='comment-count']"),By.xpath("//a[@class='commentCount']")};

    public ArticlePage(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public String getTitle(int i) {
        LOGGER.info("ArticlePage: Getting Title");
        return this.baseFunctions.getElement(TITLE[i]).getText();
    }

    public int getCommentCount(int i) {
        LOGGER.info("ArticlePage: Getting comment count");
        String countText = "(0)";
        if (baseFunctions.driver.findElements(COMMENT_COUNT[i]).size()>0)
         countText = this.baseFunctions.getElement(COMMENT_COUNT[i]).getText();
        return articleHelper.parseCount(countText);
    }

}
