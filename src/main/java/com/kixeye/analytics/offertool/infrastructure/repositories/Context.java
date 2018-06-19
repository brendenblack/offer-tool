package com.kixeye.analytics.offertool.infrastructure.repositories;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Getter
@Repository
public class Context
{
    private final Logger log = LoggerFactory.getLogger(Context.class);

    private final OfferRepository offerRepository;
    private final UserOfferRepository userOfferRepository;

    @Autowired
    Context(OfferRepository offerRepository,
            UserOfferRepository userOfferRepository)
    {
        log.debug("Initializing application data context");

        this.userOfferRepository = userOfferRepository;
        this.offerRepository = offerRepository;




    }
}
