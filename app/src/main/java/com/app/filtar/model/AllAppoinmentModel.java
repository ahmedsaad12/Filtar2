package com.app.filtar.model;

import java.io.Serializable;
import java.util.List;

public class AllAppoinmentModel extends StatusResponse implements Serializable {
  private List<AppoinmentModel> data;

    public List<AppoinmentModel> getData() {
        return data;
    }
}
