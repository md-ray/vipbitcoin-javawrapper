package com.saviourcat.crypto.exchange;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by saviourcat on 07/01/18.
 */
public class VipBitcoinAPI {
    private static final String EXCHANGE_NAME = "VIP Bitcoin";
    private static final String VIP_TICKER_API_URL = "https://vip.bitcoin.co.id/api/%s_%s/ticker";
    private static final String VIP_TRADES_API_URL = "https://vip.bitcoin.co.id/api/%s_%s/trades";

    public static void main(String[] args) {
        VipBitcoinAPI api = new VipBitcoinAPI();
        try {
//            api.getTicker("btc", "idr");
            api.getTrades("xrp", "idr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ticker getTicker(String coin1, String coin2) throws UnirestException, ExchangeException {
        coin1 = coin1.toLowerCase();
        coin2 = coin2.toLowerCase();

        HttpResponse<JsonNode> resp = Unirest.post(String.format(VIP_TICKER_API_URL, coin1, coin2))
                .header("accept", "application/json")
                .asJson();


        if (resp.getBody().getObject() == null || !resp.getBody().getObject().has("ticker")) throw new ExchangeException("improper response from VIP API");
        Ticker ticker = getTicker(resp.getBody().getObject(), coin1, coin2);

        System.out.println(ticker);
        return ticker;
    }

    protected Ticker getTicker(JSONObject json, String coin1, String coin2) {
        JSONObject tickerNode = json.getJSONObject("ticker");
        Ticker t = new Ticker(EXCHANGE_NAME, coin1, coin2);
        t.setHigh(BigDecimal.valueOf(tickerNode.getDouble("high")));
        t.setLow(BigDecimal.valueOf(tickerNode.getDouble("low")));
        t.setVol1(BigDecimal.valueOf(tickerNode.getDouble("vol_" + coin1)));
        t.setVol2(BigDecimal.valueOf(tickerNode.getDouble("vol_" + coin2)));
        t.setLast(BigDecimal.valueOf(tickerNode.getDouble("last")));
        t.setBuy(BigDecimal.valueOf(tickerNode.getDouble("buy")));
        t.setSell(BigDecimal.valueOf(tickerNode.getDouble("sell")));
        t.setServerTime(tickerNode.getLong("server_time") * 1000);

        return t;
    }

    public Trades getTrades(String coin1, String coin2) throws UnirestException, ExchangeException {
        coin1 = coin1.toLowerCase();
        coin2 = coin2.toLowerCase();
        String url = String.format(VIP_TRADES_API_URL, coin1, coin2);
        HttpResponse<JsonNode> resp = Unirest.post(url)
                .header("accept", "application/json")
                .asJson();

//        System.out.println(resp.getBody().);
        if (resp.getBody().getArray() == null ) throw new ExchangeException("improper response from VIP API = " + url);
        Trades trades = getTrades(resp.getBody().getArray(), coin1, coin2);
        return trades;
    }

    protected Trades getTrades(JSONArray jsonArr, String coin1, String coin2) {
        Trades trades = new Trades(EXCHANGE_NAME, coin1, coin2);
        ArrayList<TradeItem> res = new ArrayList<TradeItem>();
        for (int i=0; i<jsonArr.length();i++) {
            TradeItem ti = new TradeItem();
            JSONObject json = jsonArr.getJSONObject(i);
            ti.setTimestamp(json.getLong("date") * 1000);
            ti.setTid(json.getLong("tid"));
            ti.setPrice(BigDecimal.valueOf(json.getDouble("price")));
            ti.setAmount(BigDecimal.valueOf(json.getDouble("amount")));
            ti.setType(json.getString("type").substring(0,1));
            res.add(ti);
            System.out.println(ti);
        }
        trades.setTradeItems(res);
        return trades;
    }
}
