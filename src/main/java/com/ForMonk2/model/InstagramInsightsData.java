package com.ForMonk2.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class InstagramInsightsData {
    private String name ,title , description , id;
    private List<InsightsValues> insights;

    @Data
    public static class InsightsValues{
       private String name;
       private double percetange;
       private int value;
    }
}
