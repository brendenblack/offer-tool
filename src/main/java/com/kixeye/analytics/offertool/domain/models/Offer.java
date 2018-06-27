package com.kixeye.analytics.offertool.domain.models;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kixeye.analytics.offertool.domain.offers.OfferContent;
import com.kixeye.analytics.offertool.domain.offers.OfferDisplayedItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "offers", schema = "wc_live")
public class Offer
{
    @Transient
    private Logger log = LoggerFactory.getLogger(Offer.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "offer_code")
    private String offerCode;

    public void setOfferCode(String offerCode)
    {
        if (offerCode.length() > 20)
        {
            this.offerCode = offerCode.substring(0, 19);
        } else
        {
            this.offerCode = offerCode;
        }
    }

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

    @Column(name = "cooldown_type")
    private int cooldownType;

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
    @Setter(AccessLevel.NONE)
    private String displayedItemsJson;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<OfferDisplayedItem> displayedItems = new ArrayList<>();

    public List<OfferDisplayedItem> getDisplayedItems()
    {
        if (displayedItems.size() <= 0 && displayedItemsJson != null)
        {
            try
            {
                ObjectMapper mapper = new ObjectMapper();
                List<OfferDisplayedItem> items = mapper.readValue(this.displayedItemsJson, new TypeReference<List<OfferDisplayedItem>>()
                {
                });

                this.displayedItems.clear();
                this.displayedItems.addAll(items);
            } catch (IOException e)
            {
                log.error("", e);
            }
        }

        return this.displayedItems;
    }

    public void addDisplayedItem(String sku, int amount, int order)
    {

    }


    @Column(name = "display_options")
    private String displayedOptions;

    //region content
    @Column(name = "content")
    private String contentJson;

    @Transient
    private OfferContent content;

    /**
     * Represents a the content being offered: units, base upgrades, tech
     *
     * @return A deserialized represntation of the JSON blob stored in the offer table
     */
    public OfferContent getContent()
    {
        if (content == null)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                this.content = objectMapper.readValue(this.contentJson, OfferContent.class);
            } catch (IOException e)
            {
                log.error("An error occurred while hydrating blog {}", this.contentJson, e);
                this.content = null;
            }
        }

        return this.content;
    }

    public void setContent(OfferContent content)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            this.contentJson = mapper.writeValueAsString(content);
        } catch (IOException e)
        {
            log.error("An error occurred while serializing offer content: {}", content.toString(), e);
        }
    }
    //endregion

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
        } else
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
        } else
        {
            this.enabled = 0;
        }
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = (enabled) ? 1 : 0;
    }
}
