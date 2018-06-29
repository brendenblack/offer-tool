package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.Offer;
import com.kixeye.analytics.offertool.domain.models.OfferSummary;
import com.kixeye.analytics.offertool.infrastructure.RestException;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Instant;
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
        public List<OfferSummary> offers = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class OfferModel
    {
        private String offerCode;
        private long startTime;
        private long endTime;
        private long duration;
        private int id;
        private boolean active;
    }

    @Service("getAllOffers")
    public static class Handler implements RequestHandler<Query, Model>
    {
        private final Logger log = LoggerFactory.getLogger(Handler.class);
        private final SnowflakeContext context;


        @Autowired
        Handler(SnowflakeContext context)
        {
            this.context = context;
        }

        @Override
        public Model handle(Query message)
        {
            log.info("Retrieving all offers");

            Model model = new Model();
            model.setOffers(this.context.getOffers().findAllSummaries());
            return model;

//
//            List<OfferModel> models = new ArrayList<>();
//
//            long now = Instant.now().toEpochMilli() / 1000;
//
//            Iterable<Offer> offers = new ArrayList<>();
////            message.isOnlyActive())
////                    ? this.context.findAll().findAllActive()
////                    : this.context.findAll().findAll();
//
//
//            for (Offer offer : this.context.getOffers().findAll())
//            {
//                log.debug(offer.toString());
//                OfferModel o = new OfferModel();
//                o.setId(offer.getId());
//                o.setOfferCode(offer.getOfferCode());
//                o.setDuration(offer.getDuration());
//                o.setStartTime(offer.getStartTime());
//                o.setEndTime(offer.getEndTime());
//                boolean isActive = offer.getEndTime() > now;
//                log.debug("[end time: {}] [now: {}] [active?: {}]", offer.getEndTime(), now, isActive);
//
//                o.setActive(isActive);
//                log.debug(offer.toString());
//                models.add(o);
//            }
//
//            Model result = new Model();
//            result.setOffers(models);
//
//            return result;
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
