package com.saviourcat.crypto.exchange;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by saviourcat on 07/01/18.
 */
public class VipBitcoinAPI {
    private static final String EXCHANGE_NAME = "VIP Bitcoin";

    public static void main(String[] args) {
        VipBitcoinAPI api = new VipBitcoinAPI();
        try {
            api.getTicker("btc", "idr");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Ticker getTicker(String coin1, String coin2) throws UnirestException, ExchangeException {
        coin1 = coin1.toLowerCase();
        coin2 = coin2.toLowerCase();

        HttpResponse<JsonNode> resp = Unirest.post(String.format("https://vip.bitcoin.co.id/api/%s_%s/ticker", coin1, coin2))
                .header("accept", "application/json")
                .queryString("apiKey", "123")
                .field("parameter", "value")
                .field("foo", "bar")
                .asJson();


        if (resp.getBody().getObject() == null || !resp.getBody().getObject().has("ticker")) throw new ExchangeException("improper response from VIP API");
        Ticker ticker = getTicker(resp.getBody().getObject(), coin1, coin2);

        System.out.println(ticker);
        return ticker;
    }

    public Ticker getTicker(JSONObject json, String coin1, String coin2) {
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
}
