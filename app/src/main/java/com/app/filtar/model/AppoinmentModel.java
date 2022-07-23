package com.app.filtar.model;

import java.io.Serializable;

public class AppoinmentModel implements Serializable {
   private int candle_number;
   private String coming_clean_time;

    public int getCandle_number() {
        return candle_number;
    }

    public String getComing_clean_time() {
        return coming_clean_time;
    }
}
