package com.app.filtar.model;

import java.io.Serializable;
import java.util.List;

public class BlogDataModel extends StatusResponse implements Serializable {
    private List<BlogModel> data;

    public List<BlogModel> getData() {
        return data;
    }
}
