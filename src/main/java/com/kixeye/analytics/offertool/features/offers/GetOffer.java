package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import com.kixeye.analytics.offertool.infrastructure.RestException;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.OfferRowMapper;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            String query = "select * from WC.MYSQL.OFFERS_WC where OFFER_CODE = :offerCode";
            Map<String, String> parameters = new HashMap<>();
            parameters.put("offerCode", message.getOfferCode());
            Offer offer = this.context.getParameterJdbc().queryForObject(query, parameters, new OfferRowMapper());

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
