package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SnowflakeContext
{
    private final OfferRepository offers;

    @Autowired
    SnowflakeContext(OfferRepository offerRepository)
    {
        this.offers = offerRepository;
    }
}
