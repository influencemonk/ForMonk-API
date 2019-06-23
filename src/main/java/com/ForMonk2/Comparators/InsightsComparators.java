package com.ForMonk2.Comparators;

import com.ForMonk2.model.InstagramInsightsData;

import java.util.Comparator;

public class InsightsComparators implements Comparator<InstagramInsightsData.InsightsValues> {
    @Override
    public int compare(InstagramInsightsData.InsightsValues o1, InstagramInsightsData.InsightsValues o2) {
        return o2.getValue() - o1.getValue();
    }
}
