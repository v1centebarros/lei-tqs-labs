import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import portfolio.IStockMarketService;
import portfolio.Stock;
import portfolio.StocksPortfolio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) //Usa-se para evitar warnings e exceptions de misuses
public class StockPortfolioTest {

    @Test
    void getTotalValueTest() {

        //1. Prepare a mock to substitute the remote service (@Mock annotation)
        IStockMarketService mockedMarket = mock(IStockMarketService.class);

        //2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio myStocks = new StocksPortfolio(mockedMarket);

        //3. Load the mock with the proper expectations (when...thenReturn)
        when(mockedMarket.lookupPrice("MSFT")).thenReturn(1.5);
        when(mockedMarket.lookupPrice("EBAY")).thenReturn(4.0);


        //4. Execute the test (use the service in the SuT)
        myStocks.addStock(new Stock("EBAY", 2));
        myStocks.addStock(new Stock("MSFT", 4));
        double result = myStocks.getTotalValue();

        //5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result);

        assertThat(result, is(14.0));
        verify(mockedMarket, times(2)).lookupPrice(anyString());

    }

}
