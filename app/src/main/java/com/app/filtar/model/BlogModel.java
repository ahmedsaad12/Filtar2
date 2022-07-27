package com.app.filtar.model;

import java.io.Serializable;
import java.util.List;

public class BlogModel implements Serializable {
   private String id;
   private String link;
   private String title;
   private String desc;
   private String created_at;
private List<BlogTagsModel> tags;

   public String getId() {
      return id;
   }

   public String getLink() {
      return link;
   }

   public String getTitle() {
      return title;
   }

   public String getDesc() {
      return desc;
   }

   public String getCreated_at() {
      return created_at;
   }

   public List<BlogTagsModel> getTags() {
      return tags;
   }
}
