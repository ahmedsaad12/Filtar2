package com.app.filtar.model;

import java.io.Serializable;
import java.util.List;

public class BlogTagsDataModel extends StatusResponse implements Serializable {
    private List<BlogTagsModel> data;

    public List<BlogTagsModel> getData() {
        return data;
    }

}
