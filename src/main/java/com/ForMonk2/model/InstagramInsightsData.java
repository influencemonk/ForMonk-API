package com.ForMonk2.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class InstagramInsightsData {
    private String name , period , title , description , id;
    private List<HashMap<String , String>> values;
}
