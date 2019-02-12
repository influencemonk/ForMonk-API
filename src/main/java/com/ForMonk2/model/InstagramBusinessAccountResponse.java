package com.ForMonk2.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstagramBusinessAccountResponse {

@SerializedName("instagram_business_account")
@Expose
private InstagramBusinessAccount instagramBusinessAccount;
@SerializedName("id")
@Expose
private String id;

public InstagramBusinessAccount getInstagramBusinessAccount() {
return instagramBusinessAccount;
}

public void setInstagramBusinessAccount(InstagramBusinessAccount instagramBusinessAccount) {
this.instagramBusinessAccount = instagramBusinessAccount;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public class InstagramBusinessAccount {

@SerializedName("id")
@Expose
private String id;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}


}
}