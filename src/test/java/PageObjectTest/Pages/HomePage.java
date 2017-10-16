package PageObjectTest.Pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage {

    BaseFunctions baseFunctions;
    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final By ARTICLE =  By.xpath("//h3[@class='top2012-title']");
    private static final By TITLE = By.xpath("//h3[@class='top2012-title']/a");
    private static final By COMMENT_COUNT = By.xpath("//h3[@class='top2012-title']/a[@class='comment-count']");

    public HomePage(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public WebElement getFirstArticle() {
        LOGGER.info("Getting first article on homepage");
        return baseFunctions.getElement(ARTICLE);
    }

    public String getTitle(WebElement article) {
        LOGGER.info("Getting Title");
        return article.findElement(TITLE).getText();
    }

    public int getCommentCount(WebElement article) {
        LOGGER.info("Getting comment count");
        String countText = article.findElement(COMMENT_COUNT).getText();
        LOGGER.info(countText);
        return articleHelper.parseCount(countText);
    }

    public ArticlePage openArticle() {
        LOGGER.info("Click title");
        baseFunctions.clickElement(TITLE);
        return new ArticlePage(baseFunctions);
    }

    public CommentsPage openComments() {
        LOGGER.info("Click comments");
        baseFunctions.clickElement(COMMENT_COUNT);
        return new CommentsPage(baseFunctions);
    }
}
