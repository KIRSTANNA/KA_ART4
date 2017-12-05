package pageObjectTest.pages;

import pageObjectTest.pages.wrappers.ArticleWrapper;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage {

    BaseFunctions baseFunctions;
    ArticleHelper articleHelper = new ArticleHelper(baseFunctions);

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);

    private static final By TOPARTICLES = By.xpath("//*[@id='column1-top']//h3[contains(@class, 'top2012-title')]");
    private static final long WAIT_MILL = 10;

    private static final By ARTICLE = By.xpath("//h3[@class='top2012-title']");
    private static final By TITLE = By.xpath("//h3[@class='top2012-title']/a");
    private static final By COMMENT_COUNT = By.xpath("//h3[@class='top2012-title']/a[@class='comment-count']");

    public HomePage(BaseFunctions bs) {
        this.baseFunctions = bs;
        baseFunctions.waitDisplayElement(TOPARTICLES, WAIT_MILL);
        LOGGER.info("Home page is loaded");
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

    // Next part is for wrappers

    /**
     * Method creates list of wrappers with articles
     *
     * @return List<ArticleWrapper>
     */
    private List<ArticleWrapper> getAllArticles() {
        List<WebElement> articles = baseFunctions.findElements(ARTICLE);
        List<ArticleWrapper> articleWrappers = new ArrayList<>();

        Iterables.addAll(articleWrappers,
                articles.stream()
                        .map(webElement -> new ArticleWrapper(baseFunctions, webElement))
                        .collect(Collectors.toList()));

        return articleWrappers;
    }

    /**
     * Method returns wrapper by name of article
     *
     * @param name String article's title for serch
     * @return ArticleWrapper if found, otherwise null
     */
    private ArticleWrapper getArticleByTitle(String name) {
        Optional<ArticleWrapper> wrapper = Iterables.tryFind(getAllArticles(),
                articleWrapper -> name.equals(articleWrapper.getArticleTitle()));
        return wrapper.isPresent() ? wrapper.get() : null;
    }

    /**
     * Method returns ArticlePage by name of article
     * Boolean value of baseFunctions.isArticleExist indicates success
     *
     * @param articleName String article's title for serch
     * @return ArticlePage
     */
    public ArticlePage openArticleByTitle(String articleName) {
        ArticleWrapper forClick = getArticleByTitle(articleName);
        if (forClick == null) baseFunctions.isArticleExist = false; // Article wasn't found
        else forClick.clickOnTitle();
        return new ArticlePage(baseFunctions);
    }

    /**
     * Method returns number of comments by name of article
     * Boolean value of baseFunctions.isArticleExist indicates if article was found
     *
     * @param articleName String article's title for serch
     * @return int number of comments
     */
    public int CommentsCountByTitle(String articleName) {
        int c = 0;
        ArticleWrapper forClick = getArticleByTitle(articleName);
        if (forClick == null) baseFunctions.isArticleExist = false; // Article wasn't found
        else c = articleHelper.parseCount(forClick.getArticleComm());
        return c;
    }

    /**
     * Method returns Comments page by name of article
     * Boolean value of baseFunctions.isArticleExist indicates if article was found
     * Boolean value of baseFunctions.isCommentsExist indicates if article has comments
     *
     * @param articleName String article's title for serch
     * @return CommentsPage
     */
    public CommentsPage openCommentsByTitle(String articleName) {
        ArticleWrapper forClick = getArticleByTitle(articleName);
        if (forClick == null) baseFunctions.isArticleExist = false; // Article wasn't found
        else baseFunctions.isCommentsExist = forClick.clickOnComm();
        return new CommentsPage(baseFunctions);
    }
}