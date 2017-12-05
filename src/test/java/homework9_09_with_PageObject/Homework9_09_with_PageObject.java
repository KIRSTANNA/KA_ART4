package homework9_09_with_PageObject;

import homework9_09_with_PageObject.pages.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.primitives.Ints.min;

/*
The program checks first 5 articles on the web-site rus.delfi.lv.
It verifies if article's title and number of comments are equivalent
in the main release, in the mobile release,
in the article's text when click on the article's link and
on the comment's page when click on the comment's link.
 */
public class Homework9_09_with_PageObject {

    BaseFunctions baseFunctions = new BaseFunctions();

    private static final Logger LOGGER = LogManager.getLogger(Homework9_09_with_PageObject.class);
    private static final String[] KUDA = {"http://rus.delfi.lv", "http://m.rus.delfi.lv/"};
    private static final String[] RELEASE = {"MAIN RELEASE : ", "MOBIL RELEASE : "};
    private static final String[] GDE = {" on the rus.delfi.lv: ", " on the m.rus.delfi.lv: "};

    @Test
    public void delfiTest() {
        LOGGER.info("It's homework last release");

        List<WebElement> articles = new ArrayList<WebElement>();

        HomePage homePage = new HomePage(baseFunctions);

        Integer articles_count = 5;
        String[][] title = new String[2][articles_count];     // articles titles
        int[][] c = new int[2][articles_count];               // articles comment's amount
        String[][] url = new String[2][articles_count];       // articles urls
        String[][] url_comm = new String[2][articles_count];  // comments urls

        String[][] titleInside = new String[2][articles_count];  // articles titles inside the article
        int[][] cInside = new int[2][articles_count];            // articles comment's amount  inside the article
        String[][] titleComm = new String[2][articles_count];    // articles titles inside the comment's page
        int[][] cCommR = new int[2][articles_count];             // registered comment's amount inside the comment's page
        int[][] cCommA = new int[2][articles_count];             // anonim comment's amount inside the comment's page
        int[][] cComm = new int[2][articles_count];              // all comment's amount inside the comment's page

        //Twodimension array 2/5: row - i - main(0) or mobil(1) release
        //                        column - j - article's number (0..4)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < articles_count; j++) {
                LOGGER.info("---------------" + "Collecting of information about the article number " + (j + 1) + " - high level ---------------------------------------------------------------------------------------------");

                if (j == 0) {
                    LOGGER.info(RELEASE[i] + "Open homepage");
                    baseFunctions.goToURL(KUDA[i]);

                    homePage = new HomePage(baseFunctions);
                    articles = homePage.getAllArticles(i);
                    articles_count = min(5, articles.size());
                }

                WebElement article = articles.get(j);

                title[i][j] = homePage.getTitle(article);
                LOGGER.info("Title of article " + (j + 1) + GDE[i] + title[i][j]);

                c[i][j] = homePage.getCommentCount(article, i);
                LOGGER.info("Comments number of article " + (j + 1) + GDE[i] + c[i][j]);

                url[i][j] = homePage.getUrl(article);
                LOGGER.info("URL  of article " + (j + 1) + GDE[i] + url[i][j]);

                if (c[i][j] > 0) {
                    url_comm[i][j] = homePage.getCommentCountUrl(article, i);
                    LOGGER.info("URL of comments " + (j + 1) + GDE[i] + url_comm[i][j]);
                } else url_comm[i][j] = "";
            }
            LOGGER.info("");
        }

        //Twodimension array 2/5: row - i - main(0) or mobil(1) release
        //                        column - j - article's number (0..4)
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < articles_count; j++) {
                LOGGER.info("---------------" + "Collecting of information about the article number " + (j + 1) + " - low level  ---------------------------------------------------------------------------------------------");
                LOGGER.info("Open article's page");
                baseFunctions.goToURL(url[i][j]);        // go to the article's URL


                ArticlePage articlePage = new ArticlePage(baseFunctions);
                LOGGER.info("Getting article's title inside the article's page");
                titleInside[i][j] = articlePage.getTitle(i);
                LOGGER.info(RELEASE[i] + "Article title inside the article text (if follow the link) : " + titleInside[i][j]);

                LOGGER.info("Getting article's comments number inside the article");
                cInside[i][j] = articlePage.getCommentCount(i);
                LOGGER.info(RELEASE[i] + "Number of comments inside the article text (if follow the link) : " + cInside[i][j]);

                if (url_comm[i][j] != "") {
                    LOGGER.info("Open comment's page");
                    baseFunctions.goToURL(url_comm[i][j]);        // go to the comment's URL
                    CommentsPage commentsPage = new CommentsPage(baseFunctions);


                    LOGGER.info("Getting article title on the comments page");
                    titleComm[i][j] = commentsPage.getTitle();
                    LOGGER.info("Article title on the comments page: " + titleComm[i][j]);

                    LOGGER.info("Getting number of registered comments");
                    cCommR[i][j] = commentsPage.getCommentReg();
                    LOGGER.info("Number of registered comments: " + cCommR[i][j]);

                    LOGGER.info("Getting number of anonimous comments");
                    cCommA[i][j] = commentsPage.getCommentAnon();
                    LOGGER.info("Number of anonimous comments: " + cCommA[i][j]);

                    LOGGER.info("Getting sum of registered and anonimous comments");
                    cComm[i][j] = cCommA[i][j] + cCommR[i][j];
                    LOGGER.info("Total of registered and anonimous comments: " + cComm[i][j]);
                }
            }
            LOGGER.info("");
        }

        // Equality checking

        boolean equalCheck = true;
        //Twodimension array 2/5: row - i - main(0) or mobil(1) release
        //                        column - j - article's number (0..4)
        for (int j = 0; j < articles_count; j++) {
            for (int i = 0; i < 2; i++) {
                LOGGER.info(RELEASE[i]);  //Checking inside the release

                LOGGER.info("Article number" + (j + 1) + " title ");
                LOGGER.info("Checking the title");
                LOGGER.info(title[i][j]);
                LOGGER.info(titleInside[i][j]);
                LOGGER.info(titleComm[i][j]);
                Assert.assertEquals("Article  title on rus.delfi.lv and article title inside the article text (if follow the link) are differ!", title[i][j], titleInside[i][j]);
                Assert.assertEquals("Article  title on rus.delfi.lv and article title on the comments page are differ!", titleInside[i][j], titleComm[i][j]);

                LOGGER.info("Checking comment count");
                LOGGER.info("" + c[i][j]);
                LOGGER.info("" + cInside[i][j]);
                LOGGER.info("" + cComm[i][j]);
                Assert.assertEquals("Number of comments on rus.delfi.lv and number of comments inside the article text (if follow the link) are differ!", c[i][j], cInside[i][j]);
                Assert.assertEquals("Number of comments on rus.delfi.lv and number of comments on the comments page are differ!", cInside[i][j], cComm[i][j]);
            }
            LOGGER.info(title[0][j]);
            LOGGER.info(title[1][j]);
            LOGGER.info("" + c[0][j]);
            LOGGER.info("" + c[1][j]);
            Assert.assertEquals("Titles  in the main and mobile release are differ!", title[0][j], title[1][j]);
            Assert.assertEquals("Number of comments  in the main and mobile release are differ!", c[0][j], c[1][j]);
        }
        LOGGER.info("Test is successful");
        baseFunctions.driverQuit();
    }
}
