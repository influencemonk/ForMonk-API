package com.ForMonk2.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookPagesResponse {

@SerializedName("data")
@Expose
private List<FacebookPageData> data = null;

public List<FacebookPageData> getData() {
return data;
}

public void setData(List<FacebookPageData> data) {
this.data = data;
}


public class FacebookPageData {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("category")
@Expose
private String category;
@SerializedName("category_list")
@Expose
private List<FacebookPageCategory> categoryList = null;
@SerializedName("name")
@Expose
private String name;
@SerializedName("id")
@Expose
private String id;
@SerializedName("tasks")
@Expose
private List<String> tasks = null;

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public List<FacebookPageCategory> getCategoryList() {
return categoryList;
}

public void setCategoryList(List<FacebookPageCategory> categoryList) {
this.categoryList = categoryList;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public List<String> getTasks() {
return tasks;
}

public void setTasks(List<String> tasks) {
this.tasks = tasks;
}

}

public class FacebookPageCategory {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private String name;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

}

}
