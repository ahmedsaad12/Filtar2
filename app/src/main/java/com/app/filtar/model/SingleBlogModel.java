package com.app.filtar.model;

import java.io.Serializable;

public class SingleBlogModel extends StatusResponse implements Serializable {
    private BlogModel data;

    public BlogModel getData() {
        return data;
    }
}
