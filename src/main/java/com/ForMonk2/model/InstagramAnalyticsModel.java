package com.ForMonk2.model;
import lombok.Data;

@Data
public class InstagramAnalyticsModel {
    private String imcId;
    private double averageComments , averageLikes , engagement;
    private int followersCount , followsCount , numberOfPosts;
}
