package DelfyStepDefs;

import PageObjectTest.Pages.BaseFunctions;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Test;

import java.io.DataOutput;
import java.io.IOException;

public class HomePageStepDefs {
    private static final String HOME_PAGE_URL = "http://www.delfi.lv";

    public class StepDefs {

        BaseFunctions baseFunctions = new BaseFunctions();

        @Given("^Print test annotation (.*)$")
        public void print_test_annotation(String annotation) throws Throwable {
            System.out.println(annotation);
            throw new PendingException();
        }

        @Given("^the weather is (.*) with a temperature ([0-9]*)$")
        public void the_weather_is_sunny_with_a_temperature(int arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }
/*
        @When("^we are sending data to server$")
        public void sending_data_to_server()  throws Throwable  {
            baseFunctions.goToURL(HOME_PAGE_URL);
            throw new PendingException();
            }
            */
        }

    }


