package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.Offer;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        public List<OfferModel> offers = new ArrayList<>();
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
        private final Context context;


        @Autowired
        Handler(Context context)
        {
            this.context = context;
        }

        @Override
        public Model handle(Query message)
        {
            log.info("Retrieving all offers");


            List<OfferModel> models = new ArrayList<>();

            long now = Instant.now().toEpochMilli() / 1000;

            Iterable<Offer> offers = (message.isOnlyActive())
                    ? this.context.getOffers().findAllActive()
                    : this.context.getOffers().findAll();


            for (Offer offer : offers)
            {
                log.debug(offer.toString());
                OfferModel o = new OfferModel();
                o.setId(offer.getId());
                o.setOfferCode(offer.getOfferCode());
                o.setDuration(offer.getDuration());
                o.setStartTime(offer.getStartTime());
                o.setEndTime(offer.getEndTime());
                boolean isActive = offer.getEndTime() > now;
                log.debug("[end time: {}] [now: {}] [active?: {}]", offer.getEndTime(), now, isActive);

                o.setActive(isActive);
                log.debug(offer.toString());
                models.add(o);
            }

            Model result = new Model();
            result.setOffers(models);

            return result;
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
