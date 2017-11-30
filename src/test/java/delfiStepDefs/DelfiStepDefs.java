package delfiStepDefs;

import pageObjectTest.PageObjectTest;
import cucumber.api.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class DelfiStepDefs {
    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest.class);
    private String articleName;

    @Given("Article name is (.*)")
    public void setArticleName(String name) {
        articleName = name;
        LOGGER.info(articleName);

    }
}
