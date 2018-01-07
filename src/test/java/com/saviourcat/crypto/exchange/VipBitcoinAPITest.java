package com.saviourcat.crypto.exchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by saviourcat on 07/01/18.
 */
public class VipBitcoinAPITest {
    @org.junit.Test
    public void getTicker() throws Exception {
        VipBitcoinAPI api = new VipBitcoinAPI();
        Ticker ticker = api.getTicker("btc", "idr");
        assertEquals(ticker.getCoin1(), "btc");
        assertEquals(ticker.getCoin2(), "idr");
        assertTrue(ticker.getServerTime() > 0);


    }

    @org.junit.Test
    public void getTrades() throws Exception {
        VipBitcoinAPI api = new VipBitcoinAPI();
        Trades trades = api.getTrades("xrp", "idr");
        assertEquals(trades.getCoin1(), "xrp");
        assertTrue(trades.getTradeItems().size() > 0);
    }

}