package com.kixeye.analytics.offertool.features.offers;

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

import java.util.ArrayList;
import java.util.List;

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
            for (int id : message.getOfferIds())
            {

                String sql = "SELECT o.id, offer_code, title, cost, start_time, end_time, duration, offerednumbers.*, purchasenumbers.* \n" +
                        "FROM wc.mysql.offers_wc o \n" +
                        "JOIN (\n" +
                        "    SELECT offer_id, COUNT(*) AS \"unique_purchased\" \n" +
                        "    FROM wc.mysql.user_offers_wc uo\n" +
                        "    WHERE uo.status = 2\n" +
                        "    GROUP BY uo.offer_id \n" +
                        ") purchasenumbers\n" +
                        "ON o.id = purchasenumbers.offer_id\n" +
                        "JOIN (\n" +
                        "    SELECT offer_id, COUNT(*) AS \"offered\"\n" +
                        "    FROM wc.mysql.user_offers_wc uo\n" +
                        "    GROUP BY uo.offer_id\n" +
                        ") offerednumbers\n" +
                        "ON o.id = offerednumbers.offer_id\n" +
                        "WHERE cost > 0;";

                List<CloneModel> clones = this.jdbc.query(sql, (rs, rowNum) -> {
                    CloneModel clone = new CloneModel();
                    clone.setId(rs.getInt("ID"));
                    clone.setOfferCode(rs.getString("OFFER_CODE"));
                    clone.setTitle(rs.getString("TITLE"));
                    clone.setCost(rs.getInt("COST"));
                    clone.setDuration(rs.getLong("DURATION"));
                    return summary;
                });

            }
            return null;
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
