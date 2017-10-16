package Homework9_09;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.LogManager;

import static com.google.common.primitives.Ints.min;

/*
This is the homework from 9.09.2017
 */

public class Homework9_09 {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(Homework9_09.class);

    private static final By ARTICLE_TITLE = By.xpath("//h3[@class='top2012-title']"); /* main release article&comment*/
    private static final By COMMENT_CLASS_NAME = By.className("comment-count"); /* main release comment*/
   // private static final By COMMENT_PAGE = By.xpath("//div[@class='comment-thread-switcher-list comment-thread-switcher-selected-reg']");
    /*  main release comment page - comments */

    private static final By ARTICLE_TITLE_TOP = By.xpath("//div[@class='md-mosaic']"); //mobil release articles top level all include
    private static final By ARTICLE_TITLE_M = By.xpath("//div[@class='md-mosaic-title']"); //mobil release article article&comment
    private static final By ARTICLE_CLASS_NAME_M = By.className("md-scrollpos"); //mobil release article title
    private static final By COMMENT_CLASS_NAME_M = By.className("commentCount"); //mobil release comment
    private static final By ARTICLE_TITLE_M_INSIDE = By.xpath("//div[@class='article-title']"); // mobil release article inside itself
    //private static final By ARTICLE_TITLE_INSIDE = By.xpath("//div[@class='edit']"); // mobil release article inside itself
    private static final By COMMENT_M_REG = By.xpath("//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-reg']");
    // mobil release article inside itself - commaents from registrate users count
    private static final By COMMENT_M_ANON = By.xpath("//a[@class='comment-thread-switcher-list-a comment-thread-switcher-list-a-anon']");
    // mobil release article inside itself - comments from anonimous users count

    private String HOME_PAGE = "http://www.delfi.lv/"; /* main release*/
    private String HOME_PAGE_M = "http://m.delfi.lv/"; /*mobil release*/

    @Test
    public void Homework9_09() throws InterruptedException {
        LOGGER.info("We are starting our test");

        String main_str, mobil_str, countToParse, string_url;
        Integer count_comm;
        Boolean ok = true;
        Boolean ok1 = true;
        Boolean ok2 = true;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\JK\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();  /* main */
        driver.manage().window().maximize();
        driver.get(HOME_PAGE);
        LOGGER.info("Go to " + HOME_PAGE);

        List<WebElement> main_all = driver.findElements(ARTICLE_TITLE);

        // gitignore - файл, где указывается, что пушить НЕ надо все, кроме src и pom.xml

        List<Integer> comments = new ArrayList<Integer>();                // main release comments quantity
        HashMap<String, Integer> articles = new HashMap<String, Integer>(); // main release articles titles
        List<String> url_s = new ArrayList<String>();                          // main release articles urls
        List<String> url_comm_s = new ArrayList<String>();                     // main release comments urls

        WebDriver driver_M = new ChromeDriver();  /*mobil */
        driver_M.manage().window().maximize();
        driver_M.get(HOME_PAGE_M);
        List<WebElement> mobil_all = driver_M.findElement(ARTICLE_TITLE_TOP).findElements(ARTICLE_TITLE_M);

        List<Integer> comments_M = new ArrayList<Integer>(); // mobil release comments counts
        List<String> articles_M = new ArrayList<String>();   // mobil release articles titles
        List<String> url_M = new ArrayList<String>();        // mobil release articles urls
        List<String> url_comm_M = new ArrayList<String>();   // mobil release comments urls

        List<By> COMMENT_M = new ArrayList<By>();   // COMMENT_M_REG, COMMENT_M_ANON - for find
        COMMENT_M.add(COMMENT_M_REG);
        COMMENT_M.add(COMMENT_M_ANON);


        Integer articles_count = main_all.size();
        articles_count = min(5, articles_count);

        // Filling in all lists
        for (int i = 0; i < articles_count; i++) {
            main_str = main_all.get(i).findElement(By.tagName("a")).getText(); //main release article title
            articles.put(main_str, i);
            url_s.add(main_all.get(i).findElement(By.tagName("a")).getAttribute("href")); // main release article url

            // main release comments:
            if (main_all.get(i).findElements(COMMENT_CLASS_NAME).size() > 0) {
                countToParse = main_all.get(i).findElement(COMMENT_CLASS_NAME).getText();
                countToParse = countToParse.substring(countToParse.indexOf('(') + 1, countToParse.indexOf(')'));
                comments.add(Integer.valueOf(countToParse));
            } else comments.add(0);

            mobil_str = mobil_all.get(i).findElement(ARTICLE_CLASS_NAME_M).getText(); //mobil release article
            articles_M.add(mobil_str);
            url_M.add(mobil_all.get(i).findElement(ARTICLE_CLASS_NAME_M).getAttribute("href")); // mobil release article url
            //System.out.println(url_M.get(i).toString());

            //mobil release comments:
            if (mobil_all.get(i).findElements(COMMENT_CLASS_NAME_M).size() > 0) {
                countToParse = mobil_all.get(i).findElement(COMMENT_CLASS_NAME_M).getText();
                if (countToParse.contains("(")) {
                    countToParse = countToParse.substring(countToParse.indexOf('(') + 1, countToParse.indexOf(')'));
                    comments_M.add(Integer.valueOf(countToParse));
                } else comments_M.add(0);
                url_comm_M.add(mobil_all.get(i).findElement(COMMENT_CLASS_NAME_M).getAttribute("href")); // mobil release comment url

            } else {
                comments_M.add(0);
                url_comm_M.add("");
            }

            // equels checking (main and mobil release)
            if (!((main_str.equals(mobil_str)) & (comments.get(i).equals(comments_M.get(i))))) {
                LOGGER.info("Обнаружено несовпадение основной и мобильной версии!!!");
                LOGGER.info("Cтатья основной версии" + i + ": " + main_str + ", " + comments.get(i));
                LOGGER.info("Cтатья мобильной версии" + i + ": " + mobil_str + ", " + comments_M.get(i));

                ok = false;
            }
        }

        // equels checking (mobil release and link from mobil release)
        for (int i = 0; i < articles_count; i++) {
            string_url = url_M.get(i).toString();

            if (string_url.contains("m.delfi.lv")) {
                // equels checking (mobil release article's titles and titles of article by link from mobil release)
                driver_M.get(url_M.get(i)); // go to the article by link from mobil release
                mobil_str = driver_M.findElement(ARTICLE_TITLE_M_INSIDE).findElement(By.tagName("h1")).getText();
                // compare mobil release article's title with the title in the article by the link from mobil release
                if (!mobil_str.equals(articles_M.get(i))) {
                    LOGGER.info("Обнаружено несовпадение мобильной версии и статьи по ссылке!!! *1");
                    LOGGER.info("Cтатья по ссылке " + i + ": " + mobil_str);
                    LOGGER.info("Cтатья мобильной версии " + i + ": " + articles_M.get(i));

                    ok1 = false;
                }
                // equels checking (mobil release comments quantity with this one by link from mobil release)
                count_comm = 0;
                if (comments_M.get(i) > 0) {
                    driver_M.get(url_comm_M.get(i)); // go to the comment by link from mobil release

                    //Registrated users's comments quantity + anonimous users's comments quantity
                    for (int j = 0; j < 2; j++) {
                        if (driver_M.findElements(COMMENT_M.get(j)).size() > 0) // if this kind of comments present
                        {
                            countToParse = driver_M.findElement(COMMENT_M.get(j)).findElement(By.tagName("span")).getText();
                            if (countToParse.contains("(")) {
                                countToParse = countToParse.substring(countToParse.indexOf('(') + 1, countToParse.indexOf(')'));
                                count_comm = count_comm + Integer.valueOf(countToParse);
                            }
                        }
                    }
                    // compare  mobil release comments quantity with this one by link from mobil release
                    if (!count_comm.equals(comments_M.get(i))) {
                        //LOGGER.info("Стaтья: " + articles_M.get(i));
                        LOGGER.info("Обнаружено несовпадение числа комментариев в мобильной верси и в статье по ссылке!!!");
                        LOGGER.info("В статье по ссылке " + i + ": " + count_comm);
                        LOGGER.info("В статье мобильной версии " + i + ": " + comments_M.get(i));
                        ok1 = false;
                    }
                }
            } else {
                if (string_url.contains("www.delfi.lv")) {
                    driver.get(url_M.get(i)); // go to the article by link from mobil release

                    if (driver.findElements(By.tagName("h1")).size() > 0) {
                        main_str = driver.findElement(By.tagName("h1")).getText();
                        // compare mobil release article's title with the title in the article by the link from mobil release
                        if (!main_str.equals(articles_M.get(i))) {
                            LOGGER.info("Обнаружено несовпадение мобильной версии и статьи по ссылке!!! *2");
                            LOGGER.info("Cтатья по ссылке " + i + ": " + main_str);
                            LOGGER.info("Cтатья мобильной версии " + i + ": " + articles.get(i));
                            ok1 = false;
                        }

                        // equels checking (mobil release comments quantity with this one by link from mobil release)
                        count_comm = 0;
                        if (comments.get(i) > 0) {
                            driver.get(url_s.get(i)); // go to the comment by link from main release

                            //Registrated users's comments quantity + anonimous users's comments quantity
                            for (int j = 0; j < 2; j++) {
                                if (driver.findElements(COMMENT_M.get(j)).size() > 0) // if this kind of comments present
                                {
                                    countToParse = driver.findElement(COMMENT_M.get(j)).findElement(By.tagName("span")).getText();
                                    if (countToParse.contains("(")) {
                                        countToParse = countToParse.substring(countToParse.indexOf('(') + 1, countToParse.indexOf(')'));
                                        count_comm = count_comm + Integer.valueOf(countToParse);
                                    }
                                }
                            }

                            // compare  mobil release comments quantity with this one by link from mobil release
                            if (!count_comm.equals(comments.get(i))) {
                                //List<String> err = new ArrayList<String>(articles.keySet());
                                //LOGGER.info("Cтатья основной версии " + i + ": " + err.get(i));
                                //LOGGER.info("Стaтья  мобильной версии : " + articles_M.get(i));
                                LOGGER.info("Обнаружено несовпадение числа комментариев в мобильной версии и в статье по ссылке!!!");
                                LOGGER.info("В статье по ссылке " + i + ": " + count_comm);
                                LOGGER.info("В статье мобильной версии " + i + ": " + comments.get(i));
                                ok1 = false;
                            }
                        }
                    }else LOGGER.info("У статьи "+i+" нестандартная страница (не сработало driver.findElement(By.tagName(\"h1\"))): "+url_M.get(i).toString());
                }
                else
                    LOGGER.info("У статьи "+i+" нестандартная страница: "+ string_url);
            }
        }

        // equels checking (main release and link from main release)
        for (int i = 0; i < articles_count; i++) {
            if (url_s.get(i).toString().contains("www.delfi.lv")) {
                // equels checking (main release and article by link from main release)
                if (driver.findElements(By.tagName("h1")).size() > 0) {
                    main_str = driver.findElement(By.tagName("h1")).getText();
                    // compare main release article's title with the title in the article by the link from mobil release
                    if (articles.get(main_str) == null) {
                        LOGGER.info("Обнаружено несовпадение основной версии и статьи по ссылке!!!");
                        LOGGER.info("Cтатья по ссылке " + i + ": " + main_str);
                        List<String> err = new ArrayList<String>(articles.keySet());
                        LOGGER.info("Cтатья основной версии " + i + ": " + err.get(i));
                        ok2 = false;
                    }
                } else LOGGER.info("У статьи "+i+" нестандартная страница (не сработало driver.findElement(By.tagName(\"h1\"))): "+url_s.get(i).toString());

                // equels checking (main release comments quantity with this one by link from main release)
                count_comm = 0;
                if (comments.get(i) > 0) {
                    driver.get(url_s.get(i)); // go to the comment by link from main release

                    //Registrated users's comments quantity + anonimous users's comments quantity
                    for (int j = 0; j < 2; j++) {
                        if (driver.findElements(COMMENT_M.get(j)).size() > 0) // if this kind of comments present
                        {
                            countToParse = driver.findElement(COMMENT_M.get(j)).findElement(By.tagName("span")).getText();
                            if (countToParse.contains("(")) {
                                countToParse = countToParse.substring(countToParse.indexOf('(') + 1, countToParse.indexOf(')'));
                                count_comm = count_comm + Integer.valueOf(countToParse);
                            }
                        }
                    }
                    // compare  main release comments quantity with this one by link from main release
                    if (!count_comm.equals(comments.get(i))) {
                        //List<String> err = new ArrayList<String>(articles.keySet());
                        //LOGGER.info("Cтатья основной версии " + i + ": " + err.get(i));
                        //LOGGER.info("Стaтья  мобильной версии : " + articles_M.get(i));
                        LOGGER.info("Обнаружено несовпадение числа комментариев в основной версии и в статье по ссылке!!!");
                        LOGGER.info("В статье по ссылке " + i + ": " + count_comm);
                        LOGGER.info("В статье основной версии " + i + ": " + comments.get(i));
                        ok2 = false;
                    }
                }
            } else
                LOGGER.info("Нестандартная страница: Путь к статье " + i + " " + url_s.get(i).toString() + " не содержит www.delfi.lv!!!");
        }

        if (ok) LOGGER.info("Основная и мобильная версии совпадают");
        if (ok1) LOGGER.info("Мобильная версия совпадает с информацией в статье");
        if (ok2) LOGGER.info("Основная версия совпадает с информацией в статье");

        driver_M.quit();
        driver.quit();

    }
}



