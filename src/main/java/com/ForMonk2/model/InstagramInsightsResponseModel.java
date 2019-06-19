package com.ForMonk2.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InstagramInsightsResponseModel {
    private List<InstagramInsightsData> audienceResponse = new ArrayList<>();
}
