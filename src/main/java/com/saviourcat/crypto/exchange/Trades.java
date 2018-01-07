package com.saviourcat.crypto.exchange;

import java.util.ArrayList;

/**
 * Created by saviourcat on 07/01/18.
 */
public class Trades {
    private final String coin1;
    private final String coin2;
    private final String exchange;

    public Trades(String exchange, String coin1, String coin2) {
        this.exchange = exchange;
        this.coin1 = coin1;
        this.coin2 = coin2;
    }

    public String getCoin1() {
        return coin1;
    }

    public String getCoin2() {
        return coin2;
    }

    public String getExchange() {
        return exchange;
    }

    private ArrayList<TradeItem> tradeItems;

    public ArrayList<TradeItem> getTradeItems() {
        return tradeItems;
    }

    public void setTradeItems(ArrayList<TradeItem> tradeItems) {
        this.tradeItems = tradeItems;
    }
}
