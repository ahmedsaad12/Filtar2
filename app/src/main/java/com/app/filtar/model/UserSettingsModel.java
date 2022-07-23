package com.app.filtar.model;

import java.io.Serializable;

public class UserSettingsModel implements Serializable {
    private boolean isFirstTime = true;


    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }


}
