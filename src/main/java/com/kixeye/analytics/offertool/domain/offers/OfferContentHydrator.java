package com.kixeye.analytics.offertool.domain.offers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class OfferContentHydrator
{
    private final Logger log = LoggerFactory.getLogger(OfferContentHydrator.class);
    private final Context context;

    OfferContentHydrator(Context context)
    {
        this.context = context;
    }

    public OfferContent hydrate(String blob)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.readValue(blob, OfferContent.class);
        }
        catch (IOException e)
        {
            log.error("An error occurred while hydrating blog {}", blob, e);
        }
        return null;
    }
}
