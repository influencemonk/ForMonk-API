package com.ForMonk2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class BaseEntity {

    @CreatedDate
    private Date createdOn;

    @LastModifiedDate
    private Date updatedOn;

    private boolean isArchived;
}
