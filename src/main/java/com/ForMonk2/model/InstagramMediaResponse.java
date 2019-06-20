package com.ForMonk2.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstagramMediaResponse {

    private String IMCId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("media")
    @Expose
    private Media media;

    @SerializedName("follows_count")
    @Expose
    private int follows_count;

    @SerializedName("followers_count")
    @Expose
    private int followers_count;

    @SerializedName("profile_picture_url")
    @Expose
    private String profilePictureUrl;

    @SerializedName("ig_id")
    @Expose
    private String igId;

    @SerializedName("username")
    @Expose
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIMCId(String imcId) {
        this.IMCId = imcId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollows_count() {
        return follows_count;
    }

    public void setFollows_count(int follows_count) {
        this.follows_count = follows_count;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getIMCId() {
        return IMCId;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getIgId() {
        return igId;
    }

    public void setIgId(String igId) {
        this.igId = igId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public class Media {

        @SerializedName("data")
        @Expose
        private List<InstagramPostMediaData> data = null;
        @SerializedName("paging")
        @Expose
        private Paging paging;

        public List<InstagramPostMediaData> getData() {
            return data;
        }

        public void setData(List<InstagramPostMediaData> data) {
            this.data = data;
        }

        public Paging getPaging() {
            return paging;
        }

        public void setPaging(Paging paging) {
            this.paging = paging;
        }

    }

    public class InstagramPostMediaData {

        @SerializedName("comments_count")
        @Expose
        private Integer commentsCount;

        @SerializedName("media_url")
        @Expose
        private String mediaUrl;

        @SerializedName("media_type")
        @Expose
        private String mediaType;

        @SerializedName("permalink")
        @Expose
        private String permalink;

        @SerializedName("owner")
        @Expose
        private Owner owner;

        @SerializedName("caption")
        @Expose
        private String caption;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("comments")
        @Expose
        private Comments comments;

        @SerializedName("like_count")
        @Expose
        private Integer likeCount;

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("thumbnail_url")
        @Expose
        private String thumbnailUrl ;

        @SerializedName("timestamp")
        @Expose
        private String timestamp;

        @SerializedName("insights")
        @Expose
        private Insights insights;

        public Integer getCommentsCount() {
            return commentsCount;
        }

        public void setCommentsCount(Integer commentsCount) {
            this.commentsCount = commentsCount;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Comments getComments() {
            return comments;
        }

        public void setComments(Comments comments) {
            this.comments = comments;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public Insights getInsights() {
            return insights;
        }

        public void setInsights(Insights insights) {
            this.insights = insights;
        }
    }


    public class Paging {

        @SerializedName("cursors")
        @Expose
        private Cursors cursors;

        public Cursors getCursors() {
            return cursors;
        }

        public void setCursors(Cursors cursors) {
            this.cursors = cursors;
        }

    }

    public class Owner {

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

    public class Cursors {

        @SerializedName("before")
        @Expose
        private String before;

        @SerializedName("after")
        @Expose
        private String after;

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

    }

    public class Comments {

        @SerializedName("data")
        @Expose
        private List<CommentData> data = null;

        public List<CommentData> getData() {
            return data;
        }

        public void setData(List<CommentData> data) {
            this.data = data;
        }

    }

    public class CommentData {

        @SerializedName("text")
        @Expose
        private String text;

        @SerializedName("timestamp")
        @Expose
        private String timestamp;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("id")
        @Expose
        private String id;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    
    public class Insights {
        private List<InsightData> data;

        public List<InsightData> getData() { return data; }
        public void setData(List<InsightData> value) { this.data = value; }
    }
    

    public class InsightData {

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("period")
        @Expose
        private String period;

        @SerializedName("values")
        @Expose
        private List<InsightValue> values;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("id")
        @Expose
        private String id;

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public String getPeriod() { return period; }
        public void setPeriod(String value) { this.period = value; }

        public List<InsightValue> getInsightValues() { return values; }
        public void setInsightValues(List<InsightValue> insightValue) { this.values = values; }

        public String getTitle() { return title; }
        public void setTitle(String value) { this.title = value; }

        public String getDescription() { return description; }
        public void setDescription(String value) { this.description = value; }

        public String getID() { return id; }
        public void setID(String value) { this.id = value; }
    }
    

    public class InsightValue {

        @SerializedName("value")
        @Expose
        private long value;

        public long getValue() { return value; }
        public void setValue(long value) { this.value = value; }
    }

}