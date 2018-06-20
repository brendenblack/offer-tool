package com.kixeye.analytics.offertool.features.units;

import com.kixeye.analytics.offertool.infrastructure.mediator.Mediator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
public class UnitsController
{
    private final Mediator mediator;

    UnitsController(Mediator mediator)
    {
        this.mediator = mediator;
    }

    @GetMapping
    public GetAll.Model getAllUnits()
    {
        return this.mediator.send(new GetAll.Query(), GetAll.Model.class);
    }

}
