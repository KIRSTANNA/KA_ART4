package PageObjectTest;


import PageObjectTest.Pages.CommentsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PageObjectTest.Pages.ArticlePage;
import PageObjectTest.Pages.BaseFunctions;
import PageObjectTest.Pages.HomePage;
import org.junit.Assert;
import org.junit.Test;

public class WrappersTest {
    BaseFunctions commonFunctions = new BaseFunctions();

    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest.class);

    private static final String HOME_PAGE_URL = "http://www.delfi.lv";
    private static final String ARTICLE_NAME = "'Rigvir' pārreģistrācijas procesā divi no četriem ekspertu atzinumiem sākotnēji bijuši negatīvi";

    @Test
    public void wrapperExampleTest() {
        commonFunctions.goToURL(HOME_PAGE_URL);
        HomePage homePage = new HomePage(commonFunctions);
        LOGGER.info("Заголовок на сайте http://www.delfi.lv: " + ARTICLE_NAME);

        LOGGER.info("Getting first article comment count");
        int c = homePage.CommentsCountByTitle(ARTICLE_NAME);
        LOGGER.info("Число комментариев на сайте http://www.delfi.lv: " + c);

        ArticlePage articlePage = homePage.openArticleByTitle(ARTICLE_NAME);

        LOGGER.info("Getting article title");
        String titleInside = articlePage.getTitle();
        LOGGER.info("Заголовок внутри статьи: " + titleInside);

        LOGGER.info("Getting article comment count");
        int cInside = articlePage.getCommentCount();
        LOGGER.info("Число комментариев внутри статьи: " + cInside);

        commonFunctions.goToURL(HOME_PAGE_URL);
        homePage = new HomePage(commonFunctions);
        CommentsPage commentsPage = homePage.openCommentsByTitle(ARTICLE_NAME);

        LOGGER.info("Getting title on comment page");
        String titleComm = commentsPage.getTitle();
        LOGGER.info("Заголовок в комментариях: " + titleComm);

        LOGGER.info("Getting registered comment count");
        int cCommR = commentsPage.getCommentReg();
        LOGGER.info("Зарегистрированных комментариев: " + cCommR);

        LOGGER.info("Getting anonim comment count");
        int cCommA = commentsPage.getCommentAnon();
        LOGGER.info("Анонимных комментариев: " + cCommA);

        LOGGER.info("Getting sum of registered and anonim comment count");
        int cComm = cCommA + cCommR;
        LOGGER.info("Всего комментариев на странице комментариев: " + cComm);

        LOGGER.info("Checking title");
        LOGGER.info("" + ARTICLE_NAME);
        LOGGER.info("" + titleInside);
        LOGGER.info("" + titleComm);
        boolean equalCheck = (ARTICLE_NAME == titleInside) & (titleInside == titleComm);
        Assert.assertTrue("Заголовки не равны!", equalCheck == true);

        LOGGER.info("Checking comment count");
        LOGGER.info("" + c);
        LOGGER.info("" + cInside);
        LOGGER.info("" + cComm);
        equalCheck = (c == cInside) & (cInside == cComm);

        Assert.assertTrue("Число комментариев разное!", equalCheck == true);
        LOGGER.info("Test is successful");
        commonFunctions.driverQuit();

    }
}