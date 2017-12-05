package pageObjectTest;


import pageObjectTest.pages.CommentsPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObjectTest.pages.ArticlePage;
import pageObjectTest.pages.BaseFunctions;
import pageObjectTest.pages.HomePage;
import org.junit.Assert;
import org.junit.Test;

/*
The program checks specified article www.delfi.lv
on equivalence of article's title and number of comments:
on www.delfi.lv,
in the article's text (when click on the article's link) and
on the comment's page (when click on the comment's link).

You could specify article's title in the variable ARTICLE_NAME
*/
public class WrappersTest {

    BaseFunctions commonFunctions = new BaseFunctions();

    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest_.class);

    private static final String HOME_PAGE_URL = "http://www.delfi.lv";
    //You could specify article's title below:
    private static final String ARTICLE_NAME = "'Lamborghini' prezentējis pasaulē visātrāko apvidnieku";
    //private static final String ARTICLE_NAME = "Aizkustinošs stāsts par divu suņu draudzību: kā Vatsons palīdzēja Kiko atlabt";

    @Test
    public void wrapperExampleTest() {
        commonFunctions.goToURL(HOME_PAGE_URL);
        HomePage homePage = new HomePage(commonFunctions);
        LOGGER.info("Title on http://www.delfi.lv is " + ARTICLE_NAME);

        LOGGER.info("Getting article comments number");
        int c = homePage.CommentsCountByTitle(ARTICLE_NAME);


        LOGGER.info("Comments number on http://www.delfi.lv is " + c);

        ArticlePage articlePage = homePage.openArticleByTitle(ARTICLE_NAME);

        LOGGER.info("Getting article title");
        String titleInside = articlePage.getTitle();
        Assert.assertFalse("Such article doesn't exist!", (titleInside == ""));
        LOGGER.info("Article title inside the article text (if follow the link) : " + titleInside);

        LOGGER.info("Getting article's comments number inside the article");
        int cInside = articlePage.getCommentCount();
        LOGGER.info("Number of comments inside the article text (if follow the link) : " + cInside);

        commonFunctions.goToURL(HOME_PAGE_URL);
        homePage = new HomePage(commonFunctions);
        CommentsPage commentsPage = homePage.openCommentsByTitle(ARTICLE_NAME);

        int cComm = 0;

        LOGGER.info("Getting article title on the comments page");
        String titleComm = commentsPage.getTitle();
        if (titleComm != "") {
            LOGGER.info("Article title on the comments page: " + titleComm);

            LOGGER.info("Getting number of registered comments");
            int cCommR = commentsPage.getCommentReg();
            LOGGER.info("Number of registered comments: " + cCommR);

            LOGGER.info("Getting number of anonimous comments");
            int cCommA = commentsPage.getCommentAnon();
            LOGGER.info("Number of anonimous comments: " + cCommA);

            LOGGER.info("Getting sum of registered and anonimous comments");
            cComm = cCommA + cCommR;
            LOGGER.info("Total of registered and anonimous comments: " + cComm);
        }

        LOGGER.info("Checking title");
        LOGGER.info("" + ARTICLE_NAME);
        LOGGER.info("" + titleInside);
        LOGGER.info("" + titleComm);

        Assert.assertEquals("Titles are differ!", ARTICLE_NAME, titleInside);
        if (titleComm != "")
            Assert.assertEquals("Titles are differ!", titleInside, titleComm);

        LOGGER.info("Checking comments number");
        LOGGER.info("" + c);
        LOGGER.info("" + cInside);
        LOGGER.info("" + cComm);
        Assert.assertEquals("Number of comments  differ!", c, cInside);
        Assert.assertEquals("Number of comments  differ!", cInside, cComm);
        LOGGER.info("Test is successful");
        commonFunctions.driverQuit();

    }
}