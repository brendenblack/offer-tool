package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.OfferRowMapper;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.jooq.impl.DSL.*;

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


    @Service("cloneOffersHandler")
    public static class Handler implements RequestHandler<Command, Model>
    {
        private final static Logger log = LoggerFactory.getLogger(Handler.class);

        private final SnowflakeContext context;

        @Autowired
        Handler(SnowflakeContext context)
        {
            this.context = context;
        }

        @Override
        public Model handle(Command message)
        {
            String ids = message.getTargets().keySet().toString();
            ids = ids.substring(1, ids.length() - 1); // strip the surrounding brackets [ ]

            StringBuilder sb = new StringBuilder();
            sb.append("select * from WC.MYSQL.OFFERS_WC where ID in (")
                    .append(ids)
                    .append(")");
            String query = sb.toString();

            Map<String, Set<Integer>> namedParameters = new HashMap<>();
            namedParameters.put("ids", message.getTargets().keySet());

            log.debug("Executing query: {}\nUsing parameters: {}", query, namedParameters.toString());

            List<Offer> offers = this.context.getParameterJdbc().query(query, namedParameters, new OfferRowMapper());

            DSLContext create = using(SQLDialect.MYSQL, new Settings().withRenderFormatted(true));
            InsertValuesStep12<Record, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> insert = create.insertInto(table(SnowflakeContext.OFFERS_TABLE),
                    field(Offer.OFFER_CODE),
                    field(Offer.TITLE),
                    field(Offer.DESCRIPTION),
                    field(Offer.ICON_TITLE),
                    field(Offer.ICON_DESCRIPTION),
                    field(Offer.DURATION),
                    field(Offer.COST),
                    field(Offer.FULL_COST),
                    field(Offer.COST_SKU),
                    field(Offer.CONTENT),
                    field(Offer.DISPLAYED_ITEMS),
                    field(Offer.DISPLAY_OPTIONS));

            for (Iterator<Offer> iter = offers.iterator(); iter.hasNext();)
            {
                Offer offer = iter.next();
                if (!message.getTargets().containsKey(offer.getId()))
                {
                    log.warn("Requested offer with ID {} does not exist, skipping", offer.getId());
                }
                else
                {
                    log.trace("Generating offer for: {}", offer.toString());
                    String newCode = message.getTargets().get(offer.getId());

                    insert = insert.values(
                            newCode, offer.getTitle(),
                            offer.getDescription(),
                            offer.getIconTitle(),
                            offer.getIconDescription(),
                            offer.getDuration(),
                            offer.getCost(),
                            offer.getFullCost(),
                            offer.getCostSku(),
                            offer.getContent(),
                            offer.getDisplayedItems(),
                            offer.getDisplayOptions());
                }
            }

            String generatedStatement = insert.getSQL(ParamType.INLINED);
            log.debug("Generated statement: {}", generatedStatement);
            Model result = new Model();
            result.setSql(generatedStatement);
            return result;
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
}
