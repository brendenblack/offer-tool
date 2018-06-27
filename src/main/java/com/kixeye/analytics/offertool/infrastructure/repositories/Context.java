package com.kixeye.analytics.offertool.infrastructure.repositories;

import com.google.api.client.auth.oauth2.TokenResponseException;
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
    private final TokenRepository tokenRepository;

    @Autowired
    Context(OfferRepository offerRepository,
            UserOfferRepository userOfferRepository,
            FactionRepository factionRepository,
            TokenRepository tokenRepository,
            UnitRepository unitRepository)
    {

        log.debug("Initializing application data context");

        this.userOffers = userOfferRepository;
        this.offers = offerRepository;
        this.factions = factionRepository;
        this.units = unitRepository;
        this.tokenRepository = tokenRepository;
    }
}
