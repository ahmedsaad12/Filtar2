package com.app.filtar.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String title;

    private boolean selected;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
