package com.ForMonk2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessTokenResponseModel {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("user")
@Expose
private User user;

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}

public class User {

@SerializedName("id")
@Expose
private String id;
@SerializedName("username")
@Expose
private String username;
@SerializedName("full_name")
@Expose
private String fullName;
@SerializedName("profile_picture")
@Expose
private String profilePicture;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getFullName() {
return fullName;
}

public void setFullName(String fullName) {
this.fullName = fullName;
}

public String getProfilePicture() {
return profilePicture;
}

public void setProfilePicture(String profilePicture) {
this.profilePicture = profilePicture;
}

}

}