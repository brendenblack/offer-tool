package com.kixeye.analytics.offertool.domain.offers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferContentUnit
{
    /**
     * The unit ID of the unit being granted
     */
    private int type;

    /**
     * The number of units to be granted
     */
    private int amount;

    /**
     * What level the granted units should be provided at
     */
    private int level;

    /**
     * ???
     */
    private int promotion;
}
