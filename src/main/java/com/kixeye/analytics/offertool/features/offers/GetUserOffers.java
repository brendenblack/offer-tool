package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.UserOffer;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetUserOffers
{
    @Getter
    @Setter
    public static class Query
    {
        private int offerId;
    }

    @Getter
    @Setter
    public static class Model
    {
        private int received;
        private int uniquePurchased;
        private int totalPurchased;
        private double conversionRate;
        private int numberOffered;

        private List<UserOfferModel> userOffers = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class UserOfferModel
    {
        private int userId;
        private int numberPurchased;
    }

    @Service("getUserOffersHandler")
    public static class Handler implements RequestHandler<Query,Model>
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
            log.debug("Fetching user offers for offer {}", message.getOfferId());

            Model result = new Model();

            for (UserOffer userOffer: this.context.getUserOffers().findByOfferId(message.getOfferId()))
            {
                result.setNumberOffered(result.getNumberOffered() + 1);
                log.trace(userOffer.toString());
                UserOfferModel model = new UserOfferModel();
                model.setNumberPurchased(userOffer.getAmountPurchased());
                // int id = Optional.of(userOffer).map(x -> x.getUser()).map(u -> u.getUserid()).orElse(0);
//                switch (userOffer.getStatus())
//                {}
                model.setUserId(userOffer.getUserId());


                if (userOffer.getAmountPurchased() > 0)
                {
                    result.setTotalPurchased(result.getTotalPurchased() + userOffer.getAmountPurchased());
                    result.setUniquePurchased(result.getUniquePurchased() + 1);
                }

                result.getUserOffers().add(model);

            }

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
