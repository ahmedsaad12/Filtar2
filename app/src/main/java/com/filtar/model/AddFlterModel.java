package com.filtar.model;

import java.util.List;

public class AddFlterModel {
    private int user_id;
    private String water_type;
    private int candle_number;
    private List<FilterCandleModel> filter_candle;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWater_type() {
        return water_type;
    }

    public void setWater_type(String water_type) {
        this.water_type = water_type;
    }

    public int getCandle_number() {
        return candle_number;
    }

    public void setCandle_number(int candle_number) {
        this.candle_number = candle_number;
    }

    public List<FilterCandleModel> getFilter_candle() {
        return filter_candle;
    }

    public void setFilter_candle(List<FilterCandleModel> filter_candle) {
        this.filter_candle = filter_candle;
    }
}
