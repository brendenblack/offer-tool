package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.Offer;
import com.kixeye.analytics.offertool.infrastructure.RestException;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class GetOffer
{
    @Getter
    @Setter
    public static class Query
    {
        private int offerId;
        private String offerCode;
    }

    @Getter
    @Setter
    public static class Model
    {
        private String code;
        private int id;
    }

    @Service("getOfferHandler")
    public static class Handler implements RequestHandler<Query,Model>
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
            Optional<Offer> offerResult = Optional.empty();

                offerResult = (message.getOfferId() > 0)
                    ? this.context.getOffers().findById(message.getOfferId())
                    : this.context.getOffers().findByCode(message.getOfferCode());


            if (!offerResult.isPresent())
            {
                throw new RestException(HttpStatus.NOT_FOUND, "Unable to find an offer with id " + message.getOfferId());
            }

            Offer offer = offerResult.get();

            Model model = new Model();
            model.setId(offer.getId());
            model.setCode(offer.getOfferCode());

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
