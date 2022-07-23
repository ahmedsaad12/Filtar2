package com.app.filtar.model;

import java.io.Serializable;
import java.util.List;

public class SliderDataModel extends StatusResponse implements Serializable {
    private List<SliderModel> data;

    public List<SliderModel> getData() {
        return data;
    }

    public static class SliderModel implements Serializable {
       private int id;
       private int product_id;
       private String image;

        public int getId() {
            return id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getImage() {
            return image;
        }
    }
}
