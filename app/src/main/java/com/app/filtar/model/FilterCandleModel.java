package com.app.filtar.model;

public class FilterCandleModel {
    private int candle_number;
    private String last_clean_time;

    public int getCandle_number() {
        return candle_number;
    }

    public void setCandle_number(int candle_number) {
        this.candle_number = candle_number;
    }

    public String getLast_clean_time() {
        return last_clean_time;
    }

    public void setLast_clean_time(String last_clean_time) {
        this.last_clean_time = last_clean_time;
    }
}
