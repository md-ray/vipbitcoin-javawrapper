package com.saviourcat.crypto.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by saviourcat on 07/01/18.
 */
public class Ticker {
    private final String coin1;
    private final String coin2;
    private final String exchange;

    public Ticker(String exchange, String coin1, String coin2) {
        this.exchange = exchange;
        this.coin1 = coin1;
        this.coin2 = coin2;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCoin1() {
        return coin1;
    }

    public String getCoin2() {
        return coin2;
    }

    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal vol1;
    private BigDecimal vol2;
    private BigDecimal last;
    private BigDecimal buy;
    private BigDecimal sell;
    private long serverTime;

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getVol1() {
        return vol1;
    }

    public void setVol1(BigDecimal vol1) {
        this.vol1 = vol1;
    }

    public BigDecimal getVol2() {
        return vol2;
    }

    public void setVol2(BigDecimal vol2) {
        this.vol2 = vol2;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(this);
        } catch (IOException e) {
            jsonInString = "error in parsing json";
            e.printStackTrace();
        }
        return jsonInString;
    }
}
