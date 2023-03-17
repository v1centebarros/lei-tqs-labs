package tests;


import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.ConfirmationPage;
import pages.HomePage;
import pages.PurchasePage;
import pages.ReservePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class FullDockerizedTest {

    @Test
    void testFlightPurchase(@DockerBrowser(type = BrowserType.CHROME) WebDriver driver) {
        HomePage homePage = new HomePage(driver);
        assertEquals("Welcome to the Simple Travel Agency!", homePage.getHeader());

        homePage.selectFromPort("Portland");
        assertEquals("Portland", homePage.getFromPort());

        homePage.selectToPort("Rome");
        assertEquals("Rome", homePage.getToPort());

        homePage.clickFindFlights();

        ReservePage reservePage = new ReservePage(driver);

        assertEquals("Virgin America", reservePage.getAirline().getText());

        assertEquals("$472.56", reservePage.getPrice().getText());

        assertEquals("1:43 AM", reservePage.getDepartureTime().getText());

        assertEquals("9:45 PM", reservePage.getArrivalTime().getText());

        reservePage.getChooseFlight().click();

        PurchasePage purchasePage = new PurchasePage(driver);

        assertEquals("Your flight from TLV to SFO has been reserved.", purchasePage.getTitle());

        purchasePage.setFormData(
                "Vicente Barros",
                "Rua de Cima",
                "Porto",
                "Porto",
                "4320-123",
                "American Express",
                "42069123",
                "12",
                "2020",
                "Vicente Barros",
                "1");

        assertEquals("Vicente Barros", purchasePage.getName());
        assertEquals("Rua de Cima", purchasePage.getAddress());
        assertEquals("Porto", purchasePage.getCity());
        assertEquals("Porto", purchasePage.getState());
        assertEquals("4320-123", purchasePage.getZipCode());
        assertEquals("amex", purchasePage.getCardType());
        assertEquals("42069123", purchasePage.getCreditCardNumber());
        assertEquals("12", purchasePage.getCreditCardMonth());
        assertEquals("2020", purchasePage.getCreditCardYear());
        assertEquals("Vicente Barros", purchasePage.getNameOnCard());
        assertEquals("on", purchasePage.getRememberMe());

        purchasePage.clickPurchaseFlight();


        ConfirmationPage confirmationPage = new ConfirmationPage(driver);

        assertEquals("Thank you for your purchase today!", confirmationPage.getTitle());

        assertTrue(confirmationPage.getId().matches("\\d+"));


        assertEquals("PendingCapture", confirmationPage.getStatus());

        assertEquals("555 USD", confirmationPage.getAmount());

    }
}
