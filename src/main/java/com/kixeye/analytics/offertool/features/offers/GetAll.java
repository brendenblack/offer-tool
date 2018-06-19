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

import java.util.ArrayList;
import java.util.List;

public class GetAll
{
    public static class Query {}

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
    }

    @Service
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

            List<OfferModel> offers = new ArrayList<>();
            for (Offer offer : this.context.getOfferRepository().findAll())
            {
                log.debug(offer.toString());
                OfferModel o = new OfferModel();
                o.setId(offer.getId());
                o.setOfferCode(offer.getOfferCode());
                o.setDuration(offer.getDuration());
                o.setStartTime(offer.getStartTime());
                o.setEndTime(offer.getEndTime());
                log.debug(offer.toString());
                offers.add(o);
            }

            Model result = new Model();
            result.setOffers(offers);

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
