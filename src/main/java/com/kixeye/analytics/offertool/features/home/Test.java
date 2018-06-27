package com.kixeye.analytics.offertool.features.home;

import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class Test
{
    public static class Command {}

    public static class Model {}

    @Service("testHandler")
    public static class Handler implements RequestHandler<Command,Model>
    {
        private final Context context;
        private final Logger log = LoggerFactory.getLogger(Handler.class);

        @Autowired
        Handler(Context context)
        {
            this.context = context;
        }


        @Override
        public Model handle(Command message)
        {
            log.debug("Beginning test");
            this.context.getTokenRepository().findAll();


            log.debug("Test ends");
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
