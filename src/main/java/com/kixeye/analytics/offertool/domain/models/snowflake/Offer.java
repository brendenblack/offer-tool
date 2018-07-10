package com.kixeye.analytics.offertool.domain.models.snowflake;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Offer
{
    public final static String ID = "ID";
    public final static String OFFER_CODE = "OFFER_CODE";
    public final static String DURATION = "DURATION";
    public final static String CONTENT = "CONTENT";
    public final static String DISPLAY_OPTIONS = "DISPLAY_OPTIONS";
    public final static String DISPLAYED_ITEMS = "DISPLAYED_ITEMS";
    public final static String START_TIME = "START_TIME";
    public final static String END_TIME = "END_TIME";
    public final static String COST = "COST";
    public final static String COST_SKU = "COST_SKU";
    public final static String FULL_COST = "FULL_COST";
    public final static String TITLE = "TITLE";
    public final static String DESCRIPTION = "DESC";
    public final static String ICON_TITLE = "ICON_TITLE";
    public final static String ICON_DESCRIPTION = "ICON_DESC";
    public final static String TEMPLATE_ID = "TEMPLATE_ID";
    public final static String MODIFIED_TIME = "MOD_TIME";
    public final static String CREATED_TIME = "CREATED_TIME";
    public final static String IS_DELETED = "IS_DELETED";
    public final static String IS_ENABLED = "IS_ENABLED";
    public final static String PREREQUISITE = "PRE_REQ";
    public final static String PRIORITY = "PRIORITY";
    public final static String MAX_QUANTITY = "MAX_QTY";

    private int id;
    private String offerCode;
    private long duration;
    private String content;
    private String displayOptions;
    private String displayedItems;
    private long startTime;
    private long endTime;
    private long createdTime;
    private long modifiedTime;
    private int cost;
    private int fullCost;
    private String costSku = "gold";
    private int maxQuantity;
    private boolean enabled;
    private boolean deleted;
    private String title;
    private String description;
    private String iconTitle;
    private String iconDescription;
    private String prerequisite = "";
    private int priority;

}
