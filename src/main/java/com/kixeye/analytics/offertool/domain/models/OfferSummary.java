package com.kixeye.analytics.offertool.domain.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferSummary
{
    private int id;

    private String offerCode;

    private String title;

    private int cost;

    private String costSku = "gold";

    private long duration;

    private long startTime;

    private long endTime;

    private int uniquePurchased;

    private int cohortSize;

    public double getApproximateRevenue()
    {
        return this.cost * .85 * .1 * this.uniquePurchased;
    }
}
