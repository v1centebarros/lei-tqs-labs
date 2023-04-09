package pt.ua.deti.tqs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePageObject {
    @FindBy(xpath = "//*[@id=\"selectOption\"]")
    private WebElement selectOption;

    @FindBy(id = "cityInput")
    private WebElement cityInput;

    @FindBy(xpath = "//*[@id=\"submitBtn\"]")
    private WebElement submitBtn;

    @FindBy(id = "displayName")
    private WebElement displayName;

    @FindBy(id = "coordinates")
    private WebElement coordinates;

    @FindBy(xpath = "/html/body/div/div/div[4]/div/button")
    private WebElement loadMoreBtn;

    @FindBy(xpath="/html/body/div/div/div[4]/div/div[2]")
    private WebElement forecastEntries;

    @FindBy(xpath = "/html/body/div/div/div[4]/div/div[3]/div[1]/div[2]")
    private WebElement overallAQI;

    @FindBy(xpath = "/html/body/div/div/div[4]/div/div[1]/div[3]/span[2]")
    private WebElement loadTime;

    @FindBy(xpath = "/html/body/div/div/div[4]/div[1]")
    private WebElement airQualityStatistics;

    @FindBy(xpath = "/html/body/div/div/div[4]/div[4]")
    private WebElement airQualityForecast;

    public HomePageObject(WebDriver driver) {
        String baseUrl = "http://localhost:4173";
        driver.get(baseUrl);
        PageFactory.initElements(driver, this);
    }

    public String getOptions() {
        return selectOption.getAttribute("value");
    }

    public void setOptions(String option) {
        Select select = new Select(selectOption);
        select.selectByVisibleText(option);
    }

    public String getDisplayName() {
        return displayName.getText();
    }

    public String getCoordinates() {
        return coordinates.getText();
    }

    public String getButtonValue() {
        return submitBtn.getText();
    }



    public void seachCity(String option) {
        cityInput.clear();
        cityInput.sendKeys(option);
        submitBtn.click();
    }

    public void submitClick() {
        submitBtn.click();
    }

    public String  getLoadMoreBtn() {
        return loadMoreBtn.getText();
    }
    public void loadMore() {
        loadMoreBtn.click();
    }

    public String getForecastEntries() {
        return forecastEntries.getText();
    }

    public Integer getOverallAQI() {
        return Integer.parseInt(overallAQI.getText());
    }

    public Integer getLoadTime() {
        return Integer.parseInt(loadTime.getText().substring(0, loadTime.getText().length() - 2));
    }

    public String getAirQualityStatistics() {
        return airQualityStatistics.getText();
    }

    public String getAirQualityForecast() {
        return airQualityForecast.getText();
    }

}
