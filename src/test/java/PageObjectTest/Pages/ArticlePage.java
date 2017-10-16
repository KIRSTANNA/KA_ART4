package PageObjectTest.Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;

import static PageObjectTest.Pages.ArticleHelper.*;

public class ArticlePage {
    BaseFunctions baseFunctions;
    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final By TITLE =  By.xpath("//h1[@class='article-title']");
    private static final By COMMENT_COUNT =  By.xpath("//a[@class='comment-count']");

    public ArticlePage(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public String getTitle() {
        LOGGER.info("ArticlePage: Getting Title");
        return this.baseFunctions.getElement(TITLE).getText();
    }

    public int getCommentCount() {
        LOGGER.info("ArticlePage: Getting comment count");
        String countText = this.baseFunctions.getElement(COMMENT_COUNT).getText();
        return articleHelper.parseCount(countText);
    }

}
