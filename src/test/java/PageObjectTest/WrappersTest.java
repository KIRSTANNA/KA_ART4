package PageObjectTest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import PageObjectTest.Pages.ArticlePage;
import PageObjectTest.Pages.BaseFunctions;
import PageObjectTest.Pages.HomePage;
import org.junit.Test;

public class WrappersTest {
    BaseFunctions commonFunctions = new BaseFunctions();
    private static final String HOME_PAGE_URL = "http://www.delfi.lv";
    private static final String ARTICLE_NAME = "'Rigvir' pārreģistrācijas procesā divi no četriem ekspertu atzinumiem sākotnēji bijuši negatīvi";

    @Test
    public void wrapperExampleTest() {
        commonFunctions.goToURL(HOME_PAGE_URL);
        HomePage homePage = new HomePage(commonFunctions);
        LOGGER.info( "Article's title: "+ARTICLE_NAME);

        ArticlePage articlePage = homePage.openArticleByTitle(ARTICLE_NAME);
    }
}