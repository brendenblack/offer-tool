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
        private List<UserOfferModel> userOffers = new ArrayList<>();
    }

    @Getter
    @Setter
    public static class UserOfferModel
    {
        private int userId;
        private int numberPurchased;



    }

    @Service
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
            List<UserOfferModel> offers = new ArrayList<>();

            for (UserOffer uo: this.context.getUserOfferRepository().findByOfferId(message.getOfferId()))
            {
                UserOfferModel o = new UserOfferModel();
                o.setNumberPurchased(uo.getAmountPurchased());
                int id = Optional.of(uo).map(x -> x.getUser()).map(u -> u.getUserid()).orElse(0);
                o.setUserId(id);
                offers.add(o);
            }

            Model result = new Model();
            result.setUserOffers(offers);
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
