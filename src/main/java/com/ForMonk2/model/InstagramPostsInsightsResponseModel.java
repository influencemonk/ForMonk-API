package com.ForMonk2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstagramPostsInsightsResponseModel {

    @SerializedName("data")
    @Expose
    private List<PostsInsightsData> data = null;

    public List<PostsInsightsData> getData() {
        return data;
    }

    public void setData(List<PostsInsightsData> data) {
        this.data = data;
    }

    public static class PostsInsightsData {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("period")
        @Expose
        private String period;
        @SerializedName("values")
        @Expose
        private List<Value> values = null;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("id")
        @Expose
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public List<Value> getValues() {
            return values;
        }

        public void setValues(List<Value> values) {
            this.values = values;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public static class Value {

        @SerializedName("value")
        @Expose
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

}