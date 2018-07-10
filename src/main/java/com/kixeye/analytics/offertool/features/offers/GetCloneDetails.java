package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

public class GetCloneDetails
{
    @Getter
    @Setter
    public static class Query
    {
        private List<Integer> offerIds = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class Model
    {
        private List<CloneModel> offers = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class CloneModel
    {
        private int id;
        private String title;
        private String description;
        private String iconTitle;
        private String iconDescription;
        private String displayedItems;
        private String content;
        private String displayOptions;
        private int cost;
        private int fullCost;
        private String costSku;
        private long duration;
        private int templateId;
        private int priority;
        private String prerequisite;
        private int maxQuantity;
    }

    @Service("getCloneDetailsHandler")
    public static class Handler implements RequestHandler<Query, Model>
    {
        private static final Logger log = LoggerFactory.getLogger(Handler.class);
        private final NamedParameterJdbcTemplate jdbc;


        @Autowired
        Handler(@Qualifier("snowflakeNamedJdbc") NamedParameterJdbcTemplate jdbc)
        {
            this.jdbc = jdbc;
        }

        @Override
        public Model handle(Query message)
        {

            log.info("Received request to look up clone details for offers {}", message.getOfferIds());

            String sql = "select\n" +
                    "  o.ID,\n" +
                    "  o.TITLE,\n" +
                    "  o.DESC,\n" +
                    "  o.ICON_TITLE,\n" +
                    "  o.ICON_DESC,\n" +
                    "  o.COST,\n" +
                    "  o.FULL_COST,\n" +
                    "  o.COST_SKU,\n" +
                    "  o.TEMPLATE_ID,\n" +
                    "  o.DISPLAYED_ITEMS,\n" +
                    "  o.DISPLAY_OPTIONS,\n" +
                    "  o.CONTENT,\n" +
//                    "  o.START_TIME,\n" +
//                    "  o.END_TIME,\n" +
                    "  o.DURATION,\n" +
                    "  o.TEMPLATE_ID,\n" +
                    "  o.PRIORITY,\n" +
                    "  o.MAX_QTY,\n" +
                    "  o.PRE_REQ\n" +
                    "from \n" +
                    "  WC.MYSQL.OFFERS_WC o\n" +
                    "where\n" +
                    "  o.id in (:offerIds);";

            log.debug("Query: {}", sql);

            Map<String, List<Integer>> parameters = Collections.singletonMap("offerIds", message.getOfferIds());

            List<CloneModel> clones = this.jdbc.query(sql, parameters, (rs, rowNum) -> {
                    CloneModel clone = new CloneModel();
                    clone.setId(rs.getInt(Offer.ID));
                    clone.setTitle(rs.getString(Offer.TITLE));
                    clone.setDescription(rs.getString(Offer.DESCRIPTION));
                    clone.setCost(rs.getInt(Offer.COST));
                    clone.setFullCost(rs.getInt(Offer.FULL_COST));
                    clone.setIconTitle(rs.getString(Offer.ICON_TITLE));
                    clone.setIconDescription(rs.getString(Offer.ICON_DESCRIPTION));
                    clone.setCostSku(Optional.ofNullable(rs.getString(Offer.COST_SKU)).orElse("gold"));
                    clone.setTemplateId(rs.getInt(Offer.TEMPLATE_ID));
                    clone.setDisplayedItems(rs.getString(Offer.DISPLAYED_ITEMS));
                    clone.setDisplayOptions(rs.getString(Offer.DISPLAY_OPTIONS));
                    clone.setContent(rs.getString(Offer.CONTENT));
                    clone.setDuration(rs.getLong(Offer.DURATION));
                    clone.setPriority(rs.getInt(Offer.PRIORITY));
                    clone.setPrerequisite(Optional.ofNullable(rs.getString(Offer.PREREQUISITE)).orElse(""));
                    clone.setMaxQuantity(rs.getInt(Offer.MAX_QUANTITY));
                    return clone;
                });


            Model result = new Model();
            result.setOffers(clones);
            return result;
        }

        @Override
        public Class getRequestType()
        {
            return Query.class;
        }

        @Override
        public Class getReturnType()
        {
            return Model.class;
        }
    }

}
