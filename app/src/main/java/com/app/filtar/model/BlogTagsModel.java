package com.app.filtar.model;

import java.io.Serializable;

public class BlogTagsModel implements Serializable {
   private int id;
   private int explanation_id;
   private String title;
   private boolean selected;
   public int getId() {
      return id;
   }

   public BlogTagsModel(String title) {
      this.title = title;
   }

   public int getExplanation_id() {
      return explanation_id;
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
