package pt.ua.deti.tqs;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class SeleniumTestIT {

    @BeforeEach
    void setup() {
        WebDriverManager.firefoxdriver().setup();
    }
    @Test
    void fetchAirQuality(FirefoxDriver driver) {

        HomePageObject homePage = new HomePageObject(driver);

        assertThat(homePage.getButtonValue()).isEqualTo("FIND AIR QUALITY");

        assertThat(homePage.getOptions()).contains("Current");
        homePage.seachCity("Lisbon");
        assertThat(homePage.getButtonValue()).isEqualTo("LOADING...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> homePage.getDisplayName().equals("Lisboa, Portugal"));
        wait.until(webDriver -> homePage.getCoordinates().equals("(38.7077507,-9.1365919)"));

        Integer loadTime = homePage.getLoadTime();
        assertThat(loadTime).isNotNull().isInstanceOf(Integer.class).isPositive();
        homePage.submitClick();
        wait.until(webDriver -> homePage.getLoadTime() < loadTime);
        assertThat(homePage.getLoadTime()).isLessThan(loadTime);



        homePage.setOptions("Forecast");
        assertThat(homePage.getOptions()).contains("Forecast");
        homePage.seachCity("Porto");

        wait.until(webDriver -> homePage.getDisplayName().equals("Porto, Portugal"));
        wait.until(webDriver -> homePage.getCoordinates().equals("(41.1494512,-8.6107884)"));
        assertThat(homePage.getForecastEntries()).contains("96 Forecast Entries");

        assertThat(homePage.getLoadMoreBtn()).isEqualTo("LOAD MORE");
        homePage.loadMore();

        Integer loadTime1 = homePage.getLoadTime();
        assertThat(loadTime1).isNotNull().isInstanceOf(Integer.class).isPositive();
        homePage.submitClick();
        wait.until(webDriver -> homePage.getLoadTime() < loadTime);
        assertThat(homePage.getLoadTime()).isLessThan(loadTime1);



        homePage.setOptions("NinjaAPI");
        assertThat(homePage.getOptions()).contains("NinjaAPI");
        homePage.seachCity("");
        assertThat(homePage.getButtonValue()).isEqualTo("FIND AIR QUALITY");
        homePage.seachCity("Aveiro");
        wait.until(webDriver -> homePage.getDisplayName().equals("Aveiro, Portugal"));



        homePage.setOptions("OpenWeatherAPI");
        assertThat(homePage.getOptions()).contains("OpenWeatherAPI");
        homePage.seachCity("Matosinhos");
        wait.until(webDriver -> homePage.getDisplayName().equals("Matosinhos, Porto, Portugal"));

        assertThat(homePage.getOverallAQI()).isNotNull().isInstanceOf(Integer.class).isPositive().isLessThan(5);


        homePage.setOptions("Statistics");
        assertThat(homePage.getOptions()).contains("Statistics");
        assertThat(homePage.getButtonValue()).isEqualTo("CHECK STATISTICS");
        homePage.submitClick();


        assertThat(homePage.getAirQualityStatistics()).contains("Air Quality Current Statistics");
        assertThat(homePage.getAirQualityForecast()).contains("Forecast Cache Statistics");





    }
}
