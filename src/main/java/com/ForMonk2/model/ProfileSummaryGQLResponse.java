package com.ForMonk2.model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileSummaryGQLResponse {

 @SerializedName("result")
 @Expose
 private String result;


 @SerializedName("data")
 @Expose
 Data DataObject;


 // Getter Methods 

 public String getResult() {
  return result;
 }

 public Data getData() {
  return DataObject;
 }

 // Setter Methods 

 public void setResult(String result) {
  this.result = result;
 }

 public void setData(Data dataObject) {
  this.DataObject = dataObject;
 }


 public class Data {
  private boolean is_private;
  private float avg_comments;
  private boolean is_business_account;
  private float media_count;
  private String connected_fb_page = null;
  private String biography;
  private float engagement_rate;
  private boolean is_verified;
  private float followed_by;
  private boolean is_joined_recently;
  private String external_url = null;
  ArrayList < Object > post_data = new ArrayList < Object > ();
  private String full_name;
  private float avg_likes;
  private float following;
  private String business_email = null;
  private String business_phone_number = null;
  private String business_category_name = null;
  private String id;
  private String profile_pic_url;
  private String username;


  // Getter Methods 

  public boolean getIs_private() {
   return is_private;
  }

  public float getAvg_comments() {
   return avg_comments;
  }

  public boolean getIs_business_account() {
   return is_business_account;
  }

  public float getMedia_count() {
   return media_count;
  }

  public String getConnected_fb_page() {
   return connected_fb_page;
  }

  public String getBiography() {
   return biography;
  }

  public float getEngagement_rate() {
   return engagement_rate;
  }

  public boolean getIs_verified() {
   return is_verified;
  }

  public float getFollowed_by() {
   return followed_by;
  }

  public boolean getIs_joined_recently() {
   return is_joined_recently;
  }

  public String getExternal_url() {
   return external_url;
  }

  public String getFull_name() {
   return full_name;
  }

  public float getAvg_likes() {
   return avg_likes;
  }

  public float getFollowing() {
   return following;
  }

  public String getBusiness_email() {
   return business_email;
  }

  public String getBusiness_phone_number() {
   return business_phone_number;
  }

  public String getBusiness_category_name() {
   return business_category_name;
  }

  public String getId() {
   return id;
  }

  public String getProfile_pic_url() {
   return profile_pic_url;
  }

  public String getUsername() {
   return username;
  }

  // Setter Methods 

  public void setIs_private(boolean is_private) {
   this.is_private = is_private;
  }

  public void setAvg_comments(float avg_comments) {
   this.avg_comments = avg_comments;
  }

  public void setIs_business_account(boolean is_business_account) {
   this.is_business_account = is_business_account;
  }

  public void setMedia_count(float media_count) {
   this.media_count = media_count;
  }

  public void setConnected_fb_page(String connected_fb_page) {
   this.connected_fb_page = connected_fb_page;
  }

  public void setBiography(String biography) {
   this.biography = biography;
  }

  public void setEngagement_rate(float engagement_rate) {
   this.engagement_rate = engagement_rate;
  }

  public void setIs_verified(boolean is_verified) {
   this.is_verified = is_verified;
  }

  public void setFollowed_by(float followed_by) {
   this.followed_by = followed_by;
  }

  public void setIs_joined_recently(boolean is_joined_recently) {
   this.is_joined_recently = is_joined_recently;
  }

  public void setExternal_url(String external_url) {
   this.external_url = external_url;
  }

  public void setFull_name(String full_name) {
   this.full_name = full_name;
  }

  public void setAvg_likes(float avg_likes) {
   this.avg_likes = avg_likes;
  }

  public void setFollowing(float following) {
   this.following = following;
  }

  public void setBusiness_email(String business_email) {
   this.business_email = business_email;
  }

  public void setBusiness_phone_number(String business_phone_number) {
   this.business_phone_number = business_phone_number;
  }

  public void setBusiness_category_name(String business_category_name) {
   this.business_category_name = business_category_name;
  }

  public void setId(String id) {
   this.id = id;
  }

  public void setProfile_pic_url(String profile_pic_url) {
   this.profile_pic_url = profile_pic_url;
  }

  public void setUsername(String username) {
   this.username = username;
  }

 }
}