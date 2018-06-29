package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeDao;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kixeye.analytics.offertool.domain.models.snowflake.Offer.DESCRIPTION;
import static com.kixeye.analytics.offertool.domain.models.snowflake.Offer.ICON_TITLE;
import static com.kixeye.analytics.offertool.domain.models.snowflake.Offer.ID;
import static com.kixeye.analytics.offertool.domain.models.snowflake.Offer.TITLE;
import static com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeDao.OFFERS_TABLE;

public class CloneOffers
{
    @Getter
    @Setter
    public static class Command
    {
        private Map<Integer, String> targets = new HashMap<>();
    }

    @Getter
    @Setter
    public static class Model
    {
        private String sql;
    }

    @Getter
    @Setter
    public static class OfferBasics
    {
        private int id;
        private int cost;
        private int fullCost;
        private String title;
        private String description;
        private String iconTitle;
        private String iconDescription;
        private int templateId;
        private String displayedItems;
        private String displayOptions;
        private String content;
    }

    @Service("cloneOffersHandler")
    public static class Handler implements RequestHandler<Command, Model>
    {
        private final static Logger log = LoggerFactory.getLogger(Handler.class);
        private final SnowflakeDao snowflake;
        private final NamedParameterJdbcTemplate jdbc;

        @Autowired
        Handler(NamedParameterJdbcTemplate jdbc, SnowflakeDao snowflakeDao)
        {
            this.jdbc = jdbc;
            this.snowflake = snowflakeDao;
        }

        @Override
        public Model handle(Command message)
        {
            List<String> columns = Arrays.asList(ID, TITLE, DESCRIPTION, ICON_TITLE);

            Map namedParameters = Collections.singletonMap("ids", message.getTargets().keySet());

            StringBuilder sb = new StringBuilder();
            sb.append("select ");
            for (int i = 0; i < columns.size(); i++)
            {
                sb.append(columns.get(i));
                if (i < columns.size())
                {
                    sb.append(", ");
                }
                else
                {
                    sb.append(" ");
                }
            }
            sb.append("from ")
              .append(OFFERS_TABLE)
              .append(" where ID in :ids");

            this.jdbc.queryForObject(sb.toString(), namedParameters, OfferBasics.class);


            return null;
        }

        @Override
        public Class getRequestType()
        {
            return Command.class;
        }

        @Override
        public Class getReturnType()
        {
            return Model.class;
        }
    }

    public class OfferRowMapper implements RowMapper<OfferBasics>
    {
        @Override
        public OfferBasics mapRow(ResultSet rs, int i) throws SQLException
        {
            OfferBasics offer = new OfferBasics();
            offer.setTitle(rs.getString(Offer.TITLE));
            offer.setId(rs.getInt(Offer.ID));

            return offer;
        }
    }


}
