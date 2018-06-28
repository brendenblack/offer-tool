package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnowflakeContext
{
    private final OfferRepository offerRepository;

    @Autowired
    SnowflakeContext(OfferRepository offerRepository)
    {
        this.offerRepository = offerRepository;
    }
}
