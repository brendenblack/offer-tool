package com.kixeye.analytics.offertool.features.offers;

import com.kixeye.analytics.offertool.infrastructure.mediator.Mediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public GetAll.Model getAllOffers(@RequestParam(value = "active", required = false, defaultValue = "false") boolean active)
    {
        GetAll.Query message = new GetAll.Query();
        message.setOnlyActive(active);

        return this.mediator.send(message, GetAll.Model.class);
    }

    @GetMapping(path = "/{offerId}/users")
    public GetUserOffers.Model getAllUserOffers(@PathVariable int offerId)
    {
        GetUserOffers.Query message = new GetUserOffers.Query();
        message.setOfferId(offerId);

        return this.mediator.send(message, GetUserOffers.Model.class);
    }

//    @GetMapping(path = "/{offerId}")
//    public GetOffer.Model getOffer(@PathVariable int offerId)
//    {
//        GetOffer.Query message = new GetOffer.Query();
//        message.setOfferId(offerId);
//
//        return this.mediator.send(message, GetOffer.Model.class);
//    }

    @GetMapping(path = "/{offerCode}")
    public GetOffer.Model getOffer(@PathVariable String offerCode)
    {
        GetOffer.Query message = new GetOffer.Query();
        message.setOfferCode(offerCode);

        return this.mediator.send(message, GetOffer.Model.class);
    }

    @PutMapping(path = "/clone")
    public CloneOffers.Model doGenerateClonedOffers(@RequestBody CloneOffers.Command message)
    {
        return this.mediator.send(message, CloneOffers.Model.class);
    }

}
