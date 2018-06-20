package com.kixeye.analytics.offertool.infrastructure.repositories;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class Context
{
    private final Logger log = LoggerFactory.getLogger(Context.class);

    private final OfferRepository offers;
    private final UserOfferRepository userOffers;
    private final FactionRepository factions;
    private final UnitRepository units;

    @Autowired
    Context(OfferRepository offerRepository,
            UserOfferRepository userOfferRepository,
            FactionRepository factionRepository,
            UnitRepository unitRepository)
    {
        log.debug("Initializing application data context");

        this.userOffers = userOfferRepository;
        this.offers = offerRepository;
        this.factions = factionRepository;
        this.units = unitRepository;




    }
}
