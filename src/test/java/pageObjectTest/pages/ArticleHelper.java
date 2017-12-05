package pageObjectTest.pages;

import org.apache.logging.log4j.LogManager;

public class ArticleHelper {
    BaseFunctions baseFunctions;
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);

    public ArticleHelper(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    /**
     * Method deletes braces from informations about number of comments
     *
     * @param countText String informations about number of comments
     * @return int comments number
     */
    public int parseCount(String countText){
        LOGGER.info("Comment's count, excluding braces");
        if (countText.contains("(")) {
            countText = countText.substring(countText.indexOf('(') + 1, countText.indexOf(')'));
        } else countText = "0";
        return Integer.valueOf(countText);
    }
}
