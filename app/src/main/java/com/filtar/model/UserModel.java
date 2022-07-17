package com.filtar.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;
    private String firebase_token;

    public Data getData() {
        return data;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public static class Data implements Serializable {
        private String id;
        private String first_name;
        private String last_name;
        private String image;
        private String phone_code;
        private String phone;
        private String address;
        private String created_at;
        private String updated_at;
        private String logo;
        private String nationality_id_image;
        private String vat_number;
        private String vat_number_image;
        private String commercial_number;
        private String commercial_number_image;
        private String store_name;
        private int candle_number;
        private String water_type;

        public String getId() {
            return id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getImage() {
            return image;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getAddress() {
            return address;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getLogo() {
            return logo;
        }

        public String getNationality_id_image() {
            return nationality_id_image;
        }

        public String getVat_number() {
            return vat_number;
        }

        public String getVat_number_image() {
            return vat_number_image;
        }

        public String getCommercial_number() {
            return commercial_number;
        }

        public String getCommercial_number_image() {
            return commercial_number_image;
        }

        public String getStore_name() {
            return store_name;
        }

        public int getCandle_number() {
            return candle_number;
        }

        public String getWater_type() {
            return water_type;
        }
    }

}
