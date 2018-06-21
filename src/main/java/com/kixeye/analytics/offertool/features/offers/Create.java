package com.kixeye.analytics.offertool.features.offers;

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

public class Create
{
    @Getter
    @Setter
    public static class Command
    {
        private String code;
        private String title;
        private int cost;
        private int fullCost;

        private int goldOffered = -1;

        private List<OfferUnitsCommand> unitsOffered = new ArrayList<>();

        private List<OfferTechCommand> techOffered = new ArrayList<>();


    }

    @Getter
    @Setter
    public static class OfferUnitsCommand
    {
        private int id;
        private boolean unlock = false;
        private int builtUnits;
        private int level = 0;
        private boolean display = true;

    }

    @Getter
    @Setter
    public static class OfferTechCommand
    {
        private String id;
    }

    @Getter
    @Setter
    public static class Model
    {
        private String link;
    }

    @Service("offerCreateHandler")
    public static class Handler implements RequestHandler<Command, Model>
    {
        private final Logger log = LoggerFactory.getLogger(Handler.class);
        private final Context context;

        @Autowired
        Handler(Context context)
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
