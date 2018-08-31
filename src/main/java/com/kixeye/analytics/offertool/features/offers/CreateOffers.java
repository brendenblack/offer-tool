package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class CreateOffers
{
    @Getter
    @Setter
    public static class Command
    {
        private String offerCodePrefix;
        private int unitId;

        private boolean includeLevels = true;
        private int levelStep = 5;
    }

    @Getter
    @Setter
    public static class Model
    {

    }

    @Service("createUniqueOffersHandler")
    public static class Handler implements RequestHandler<Command, Model>
    {
        private static final Logger log = LoggerFactory.getLogger(Handler.class);
        @Override
        public Model handle(Command message)
        {
            // unlock, tech, 5-20, 25-30, 35-40, e parts, o parts


            // create offers

            // create Jira tickets

            // create cohort sql
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
}
