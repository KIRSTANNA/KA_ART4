package PageObjectTest;

import PageObjectTest.Pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class PageObjectTest {

    BaseFunctions baseFunctions = new BaseFunctions();

    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest.class);
    private static final String HOME_PAGE_URL = "http://rus.delfi.lv";

    @Test
    public void delfiTest() {
        LOGGER.info("Open homepage");
        baseFunctions.goToURL(HOME_PAGE_URL);

        LOGGER.info("Getting first article title");
        HomePage homePage = new HomePage(baseFunctions);
        WebElement article = homePage.getFirstArticle();
        String title = homePage.getTitle(article);
        LOGGER.info("Заголовок на сайте rus.delfi.lv: " + title);


        LOGGER.info("Getting first article comment count");
        int c = homePage.getCommentCount(article);
        LOGGER.info("Число комментариев на сайте rus.delfi.lv: " + c);


        LOGGER.info("Open first article");
        ArticlePage articlePage = homePage.openArticle();
        //article.click();


        LOGGER.info("Getting article title");
        String titleInside = articlePage.getTitle();
        LOGGER.info("Заголовок внутри статьи: " + titleInside);

        LOGGER.info("Getting article comment count");
        int cInside = articlePage.getCommentCount();
        LOGGER.info("Число комментариев внутри статьи: " + cInside);

        baseFunctions.goToURL(HOME_PAGE_URL);

        LOGGER.info("Open comment page");
        CommentsPage commentsPage = homePage.openComments();

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
        LOGGER.info("" + title);
        LOGGER.info("" + titleInside);
        LOGGER.info("" + titleComm);
        boolean equalCheck = (title == titleInside) & (titleInside == titleComm);
        Assert.assertTrue("Заголовки не равны!", equalCheck == true);

        LOGGER.info("Checking comment count");
        LOGGER.info("" + c);
        LOGGER.info("" + cInside);
        LOGGER.info("" + cComm);
        equalCheck = (c == cInside) & (cInside == cComm);

        Assert.assertTrue("Число комментариев разное!", equalCheck == true);
        LOGGER.info("Test is successful");
        baseFunctions.driverQuit();
    }
}
