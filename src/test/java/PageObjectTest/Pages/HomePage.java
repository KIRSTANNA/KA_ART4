package PageObjectTest.Pages;

import PageObjectTest.Pages.Wrappers.ArticleWrapper;
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

    private static final By ARTICLE_ITEM = ARTICLE;

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


 /*   *//**
     * Method collects all top articles items
     *
     * @return - List of article items wrappers
     *//*
    private List<TopArticleWrapper> getAllTopArticles() {
        List<WebElement> topArticles = baseFunctions.findElements(TOPARTICLES);
        List<TopArticleWrapper> result = new ArrayList<TopArticleWrapper>();
        Iterables.addAll(result, Iterables.transform(topArticles, new Function<WebElement, TopArticleWrapper>() {
            public TopArticleWrapper apply(WebElement webElement) {
                return new TopArticleWrapper(baseFunctions, webElement);
            }
        }));
        return result;
    }

    *//**
     * Method returns article wrapper by order number
     *
     * @param order of article item
     * @return - selected article item wrapper
     *//*
    public TopArticleWrapper getArticleByOrder(final int order) {
        return getAllTopArticles().get(order);
    }


    *//**
     * Method returns article wrapper by name
     *
     * @param name of article item
     * @return - selected article item wrapper
     *//*
    public TopArticleWrapper getArticleByName(final String name) {
        Optional<TopArticleWrapper> wrapper = Iterables.tryFind(getAllTopArticles(),
                topArticleWrapper -> topArticleWrapper.getArticleName().equalsIgnoreCase(name));
        return wrapper.get();
    }


    *//**
     * Method returns article wrapper by name
     *
     * @param name of article item
     *
     * @return - selected article item wrapper
     *//*
//    public TopArticleWrapper getArticleByName(final String name) throws NullPointerException {
//        List<TopArticleWrapper> topArticleWrappers = getAllTopArticles();
//        TopArticleWrapper article = null;
//        for (TopArticleWrapper topArticleWrapper: topArticleWrappers) {
//            if (topArticleWrapper.getArticleName().contains(name)) {
//                article = topArticleWrapper;
//                break;
//            }
//        }
//        return article;
//    }


    *//**
     * This method selects article by name and return new page with selected article
     *
     * @param name - article name
     * @return article page
     *//*
    public ArticlePage openArticleByName(String name) {
        TopArticleWrapper article = getArticleByName(name);
        article.clickArticleName();
        LOGGER.info("User clicked on the article:" + name);
        return new ArticlePage(baseFunctions);
    }

    *//**
     * This method selects article by order and return new page with selected article
     *
     * @param order - article order
     * @return article page
     *//*
    public ArticlePage openArticleByOrder(final int order) {
        TopArticleWrapper article = getArticleByOrder(order);
        article.clickArticleName();
        LOGGER.info("User clicked on the " + order + " article");
        return new ArticlePage(baseFunctions);
    }

    *//**
     * This method selects article by name and return new page with selected article
     *
     * @param article - TopArticleWrapper article
     * @return article page
     *//*
    public ArticlePage openArticle(TopArticleWrapper article) {
        article.clickArticleName();
        LOGGER.info("User clicked on the name of the article: " + article.getArticleName());
        return new ArticlePage(baseFunctions);
    }

    *//**
     * This method selects article by name and return new page with selected article
     *
     * @param article - TopArticleWrapper article
     * @return article page
     *//*
    public CommentsPage openComments(TopArticleWrapper article) {
        article.clickArticleCommentCount();
        LOGGER.info("User clicked on the comment count of the article:" + article.getArticleName());
        return new CommentsPage(baseFunctions);
    }
*/
    private List<ArticleWrapper> getAllArticles() {
        List<WebElement> articles = baseFunctions.findElements(ARTICLE_ITEM);
        List<ArticleWrapper> articleWrappers = new ArrayList<>();

        Iterables.addAll(articleWrappers,
                articles.stream()
                        .map(webElement -> new ArticleWrapper(baseFunctions, webElement))
                        .collect(Collectors.toList()));

        return articleWrappers;
    }

    private ArticleWrapper getArticleByTitle(String name) {
        Optional<ArticleWrapper> wrapper = Iterables.tryFind(getAllArticles(),
                articleWrapper -> name.contains(articleWrapper.getArticleTitle()));
        return wrapper.isPresent() ? wrapper.get() : null;
    }

    public ArticlePage openArticleByTitle(String articleName) {
        ArticleWrapper forClick = getArticleByTitle(articleName);
        if (forClick==null) LOGGER.info("Такой статьи нет!");
        else getArticleByTitle(articleName).clickOnTitle();
        return new ArticlePage(baseFunctions);
    }
}