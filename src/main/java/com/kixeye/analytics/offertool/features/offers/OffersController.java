package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.infrastructure.mediator.Mediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/offers")
public class OffersController
{
    private final Mediator mediator;

    @Autowired
    OffersController(Mediator mediator)
    {
        this.mediator = mediator;
    }

    @GetMapping
    public GetAll.Model getAllOffers()
    {
        return this.mediator.send(new GetAll.Query(), GetAll.Model.class);
    }

    @GetMapping(path = "/{offerId}/users")
    public GetUserOffers.Model getAllUserOffers(@PathVariable int offerId)
    {
        GetUserOffers.Query message = new GetUserOffers.Query();
        message.setOfferId(offerId);

        return this.mediator.send(message, GetUserOffers.Model.class);
    }

}
