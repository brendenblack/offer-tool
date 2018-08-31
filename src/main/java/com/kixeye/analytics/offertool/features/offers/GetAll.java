package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.OfferSummary;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class GetAll
{
    @Getter
    @Setter
    public static class Query
    {
        private boolean onlyActive = false;
    }

    @Getter
    @Setter
    public static class Model
    {
        public List<OfferModel> offers = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class OfferModel
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
            return (this.costSku.equalsIgnoreCase("gold")) ? this.cost * .85 * .1 * this.uniquePurchased : 0.0;
        }
    }

    @Service("getAllOffers")
    public static class Handler implements RequestHandler<Query, Model>
    {
        private final Logger log = LoggerFactory.getLogger(Handler.class);
        private final NamedParameterJdbcTemplate jdbc;


        @Autowired
        Handler(@Qualifier("snowflakeNamedJdbc") NamedParameterJdbcTemplate jdbc)
        {
            this.jdbc = jdbc;
        }

        @Override
        public Model handle(Query message)
        {
            log.info("Retrieving all offers");

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
                    "ON o.id = offerednumbers.offer_id;";

            List<OfferModel> summaries = this.jdbc.query(sql, (rs, rowNum) -> {
               OfferModel summary = new OfferModel();
                summary.setId(rs.getInt("ID"));
                summary.setOfferCode(rs.getString("OFFER_CODE"));
                summary.setTitle(rs.getString("TITLE"));
                summary.setCost(rs.getInt("COST"));
                summary.setStartTime(rs.getLong("START_TIME"));
                summary.setEndTime(rs.getLong("END_TIME"));
                summary.setDuration(rs.getLong("DURATION"));
                summary.setUniquePurchased(rs.getInt("unique_purchased"));
                summary.setCohortSize(rs.getInt("offered"));
               return summary;
            });


            Model model = new Model();
            model.setOffers(summaries);
            return model;

        }

        @Override
        public Class getRequestType() {
            return Query.class;
        }

        @Override
        public Class getReturnType() {
            return Model.class;
        }
    }
}
