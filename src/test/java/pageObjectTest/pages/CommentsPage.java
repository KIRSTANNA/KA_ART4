package pageObjectTest.pages;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;

public class CommentsPage {
    BaseFunctions baseFunctions;
    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);
    private static final By TITLE = By.xpath("//h1/a[@class='comment-main-title-link']");
    private static final By COMMENT_REG = By.xpath("//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']/span");
    private static final By COMMENT_ANON = By.xpath("//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']/span");


    public CommentsPage(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public String getTitle() {
        LOGGER.info("CommentsPage: Getting Title");
        String t = "";
        if ((this.baseFunctions.isArticleExist) & (this.baseFunctions.isCommentsExist))
            t = this.baseFunctions.getElement(TITLE).getText();
        return t;
        //return this.baseFunctions.getElement(TITLE).getText();
    }

    public int getCommentReg() {
        LOGGER.info("CommentsPage: Getting registered comment count");
        String countText = this.baseFunctions.getElement(COMMENT_REG).getText();
        return articleHelper.parseCount(countText);
    }

    public int getCommentAnon() {
        LOGGER.info("CommentsPage: Getting anonimous comment count");
        String countText = this.baseFunctions.getElement(COMMENT_ANON).getText();
        return articleHelper.parseCount(countText);
    }


}
