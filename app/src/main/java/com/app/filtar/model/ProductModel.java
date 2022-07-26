package com.app.filtar.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String id;
    private String provider_id;
    private String main_image;
    private String title;
    private String price;
    private Provider provider;
    private List<Images> images;

    public String getId() {
        return id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getMain_image() {
        return main_image;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<Images> getImages() {
        return images;
    }

    public class Provider implements Serializable {
        private String id;
        private String logo;
        private String store_name;

        public String getId() {
            return id;
        }

        public String getLogo() {
            return logo;
        }

        public String getStore_name() {
            return store_name;
        }
    }

    public class Images implements Serializable {
        private String id;
        private String image;
        private String product_id;

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getProduct_id() {
            return product_id;
        }
    }
}
