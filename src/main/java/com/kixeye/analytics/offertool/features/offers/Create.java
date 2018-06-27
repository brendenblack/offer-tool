package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.domain.models.Offer;
import com.kixeye.analytics.offertool.domain.offers.OfferContent;
import com.kixeye.analytics.offertool.domain.offers.OfferContentUnit;
import com.kixeye.analytics.offertool.domain.offers.OfferContentUnitUnlock;
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

        private List<OfferTokenCommand> tokensOffered = new ArrayList<>();

        private List<OfferPartCommand> partsOffered = new ArrayList<>();
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
    public static class OfferTokenCommand
    {
        private String tokenName;
        private int amount;
        private boolean display = true;
    }

    @Getter
    @Setter
    public static class OfferPartCommand
    {
        private String partName;
        private int amount;
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
            Offer offer = new Offer();
            offer.setOfferCode(message.getCode());
            offer.setCost(message.getCost());
            offer.setFullCost(message.getFullCost());
            offer.setCooldown(0);
            offer.setCooldownType(0);




            OfferContent content = new OfferContent();
            List<OfferContentUnitUnlock> unitUnlocks = new ArrayList<>();
            List<OfferContentUnit> units = new ArrayList<>();

            for (OfferUnitsCommand unit : message.getUnitsOffered())
            {
                if (unit.isUnlock())
                {

                }

                if (unit.isDisplay())
                {}
            }

            for (OfferTokenCommand token : message.getTokensOffered())
            {
                content.getSkus().put(token.getTokenName(), token.getAmount());
                if (token.isDisplay())
                {
                    //offer.addDisplayedItem();
                }
            }

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
