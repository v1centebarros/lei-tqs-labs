package portfolio;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private List<Stock> stocks;
    private IStockMarketService stockMarket;

    public StocksPortfolio(IStockMarketService stockMarket) {
        this.stockMarket = stockMarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0.0;

        for (Stock stock : stocks) {
            total += stockMarket.lookupPrice(stock.getLabel()) * stock.getQuantity();
        }

        return total;
    }
}
