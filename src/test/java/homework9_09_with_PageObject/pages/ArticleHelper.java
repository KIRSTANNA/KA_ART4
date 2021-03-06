package homework9_09_with_PageObject.pages;

import org.apache.logging.log4j.LogManager;

public class ArticleHelper {
    BaseFunctions baseFunctions;
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(BaseFunctions.class);

    public ArticleHelper(BaseFunctions bs) {
        this.baseFunctions = bs;
    }

    public int parseCount(String countText){
        LOGGER.info("Comments number, excluding braces ");
        if (countText.contains("(")) {
            countText = countText.substring(countText.indexOf('(') + 1, countText.indexOf(')'));
        } else countText = "0";
        return Integer.valueOf(countText);
    }
}
