package com.app.filtar.model;

import java.io.Serializable;

public class NotificationModel implements Serializable {
   private String id;
   private String user_id;
   private String provider_id;
   private String title;
   private String message;
   private String type;
   private String candle_number;
   private String created_at;
   private String updated_at;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getCandle_number() {
        return candle_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
