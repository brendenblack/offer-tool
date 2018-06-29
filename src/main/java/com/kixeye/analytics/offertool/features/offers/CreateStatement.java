package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CreateStatement
{
    public static class Command
    {

    }

    public static class Model
    {

    }

    @Service("createStatementHandler")
    public static class Handler implements RequestHandler<Command, Model>
    {
        private static final Logger log = LoggerFactory.getLogger(Handler.class);
        private SnowflakeContext context;

        @Autowired
        Handler(SnowflakeContext context)
        {
            this.context = context;
        }


        @Override
        public Model handle(Command message)
        {
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
