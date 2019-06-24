package com.ForMonk2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper=true)
@Data
@Document(collection = "InstagramAnalyticsLogs")
public class InstagramAnalyticsLogsModel extends BaseEntity {

    private int followersCount , followingCount , mediaCount , averageLikes , averageComments , averageEngagement;
    private String imcId , socialHandleId ;

    @Id
    private String _id;
}
