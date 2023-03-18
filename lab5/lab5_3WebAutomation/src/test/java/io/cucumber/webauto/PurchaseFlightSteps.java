package io.cucumber.webauto;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.NoSuchElementException;

public class PurchaseFlightSteps {

    private WebDriver driver;

    @Given("the user is on the Simple Travel Agency homepage")

    public void theUserIsOnTheSimpleTravelAgencyHomepage() {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get("http://blazedemo.com/");
    }

    @When("the user selects {string} as the departure city")
    public void theUserSelectsPortlandAsTheDepartureCity(String arg0) {
        driver.findElement(By.name("fromPort")).sendKeys(arg0);
    }

    @And("the user selects {string} as the destination city")
    public void theUserSelectsRomeAsTheDestinationCity(String arg0) {
        driver.findElement(By.name("toPort")).sendKeys(arg0);
    }


    @And("the user clicks on Find Flights")
    public void theUserClicksOnFindFlights() {
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
    }

    @Then("the user is taken to the flight reservation page")
    public void theUserIsTakenToTheFlightReservationPage() {
        try {
            driver.findElement(By.xpath("//*[contains(text(), 'Flights from Portland to Rome:')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError("\"Flights from Portland to:\" not available in results");
        }
    }


    @When("the user selects a flight from {string} number {string} with a price of {string}")
    public void theUserSelectsAFlightFromVirginAmericaNumberWithAPriceOf$(String arg0, String arg1, String arg2) {
        Assertions.assertEquals(arg1, driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[2]")).getText());
        Assertions.assertEquals(arg0, driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[3]")).getText());
        Assertions.assertEquals(arg2, driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[6]")).getText());

    }

    @And("the user clicks on Choose This Flight")
    public void theUserClicksOnChooseThisFlight() {
        driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[1]/input")).click();
    }

    @Then("the user is taken to the flight purchase page")
    public void theUserIsTakenToTheFlightPurchasePage() {
        Assertions.assertEquals("Your flight from TLV to SFO has been reserved.", driver.findElement(By.xpath("/html/body/div[2]/h2")).getText());
    }

    @When("the user fills out the flight purchase form with:")
    public void theUserFillsOutTheFlightPurchaseFormWith(DataTable data) {
        Map<String, String> map = data.asMap(String.class, String.class);
        driver.findElement(By.id("inputName")).sendKeys(map.get("Name"));
        driver.findElement(By.id("address")).sendKeys(map.get("Address"));
        driver.findElement(By.id("city")).sendKeys(map.get("City"));
        driver.findElement(By.id("state")).sendKeys(map.get("State"));
        driver.findElement(By.id("zipCode")).sendKeys(map.get("Zip Code"));
        driver.findElement(By.id("cardType")).sendKeys(map.get("Card Type"));
        driver.findElement(By.id("creditCardNumber")).sendKeys(map.get("Card Number"));
        driver.findElement(By.id("creditCardMonth")).sendKeys(map.get("Card Exp. Month"));
        driver.findElement(By.id("creditCardYear")).sendKeys(map.get("Card Exp. Year"));
        driver.findElement(By.id("nameOnCard")).sendKeys(map.get("Name on Card"));
        driver.findElement(By.id("rememberMe")).sendKeys(map.get("Remember Me"));
    }

    @And("the user clicks on Purchase Flight")
    public void theUserClicksOnPurchaseFlight() {
        driver.findElement(By.xpath("/html/body/div[2]/form/div[11]/div/input")).click();
    }

    @Then("the user is taken to the flight purchase confirmation page")
    public void theUserIsTakenToTheFlightPurchaseConfirmationPage() {
        Assertions.assertEquals("Thank you for your purchase today!", driver.findElement(By.xpath("/html/body/div[2]/div/h1")).getText());
        driver.quit();
    }
}
