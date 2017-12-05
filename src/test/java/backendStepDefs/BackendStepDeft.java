package backendStepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageObjectTest.PageObjectTest_;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/*
There is a small backend test sample.
The program checks several fields (coordinates) on the samples of http://openweathermap.org/api.
 */
public class BackendStepDeft {
    private static final Logger LOGGER = LogManager.getLogger(PageObjectTest_.class);
    private WeatherRequester wr = new WeatherRequester();
    private String cityName;
    private WeatherResponse weatherResponse = new WeatherResponse();

    @Given("City name is (.*)")
    public void setCityName(String name) {
        cityName = name;
        LOGGER.info(cityName);
    }

    @When("Requesting weather information")
    public void requestWeatherInformation() throws IOException {
        weatherResponse = wr.getWeather(cityName);
    }

    @Then("Coordinates are lon: (.*) and lat: (.*)")
    public void checkCoordinates(BigDecimal lon, BigDecimal lat) {
        assertEquals(lon, weatherResponse.getCoord().getLon());
        assertEquals(lat, weatherResponse.getCoord().getLat());
    }
}
