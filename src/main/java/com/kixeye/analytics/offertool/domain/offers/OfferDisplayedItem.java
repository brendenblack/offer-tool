package com.kixeye.analytics.offertool.domain.offers;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OfferDisplayedItem
{
    private String item;

    private int amount;

    private int order;
}
