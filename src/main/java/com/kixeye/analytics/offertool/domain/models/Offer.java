package com.kixeye.analytics.offertool.domain.models;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "offers")
public class Offer
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "start_time")
    private Long startTime;

    @Column(name = "end_time")
    private Long endTime;

    private Long duration;

    private Integer priority;

    @Column(name = "max_qty")
    private Integer maxQuantity;

    @Column(nullable = true)
    private Integer cooldown;

//    @Column(name = "cooldown_type")
//    private int cooldownType;

    @Column(name = "pre_req")
    private String prerequisiteOfferCode;

    @Column(nullable = true)
    private Integer cost;

    @Column(name = "full_cost", nullable = true)
    private Integer fullCost;

    private String title;

    @Column(name = "desc")
    private String description;

    @Column(name = "icon_title")
    private String iconTitle;

    @Column(name = "icon_desc")
    private String iconDescription;

    @Column(name = "template_id")
    private Integer templateId;

    @Column(name = "displayed_items")
    private String displayedItems;

    @Column(name = "display_options")
    private String displayedOptions;

    private String content;

    @Column(name = "mod_time")
    private Long modifiedTime;

    @Column(name = "created_time")
    private Long createdTime;

    @Column(name = "is_deleted")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer deleted;

    public boolean isDeleted()
    {
        return this.deleted == 1;
    }

    public void setDeleted(int deleted)
    {
        if (deleted > 0)
        {
            this.deleted = 1;
        }
        else
        {
            this.deleted = 0;
        }
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = (deleted) ? 1 : 0;
    }


    @Column(name = "is_enabled")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer enabled;

    public boolean isEnabled()
    {
        return this.enabled == 1;
    }

    public void setEnabled(int enabled)
    {
        if (enabled > 0)
        {
            this.enabled = 1;
        }
        else
        {
            this.enabled = 0;
        }
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = (enabled) ? 1 : 0;
    }

}
