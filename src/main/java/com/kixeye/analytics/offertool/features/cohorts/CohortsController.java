package com.kixeye.analytics.offertool.features.cohorts;

import com.kixeye.analytics.offertool.infrastructure.mediator.Mediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cohorts")
public class CohortsController
{
    private static final Logger log = LoggerFactory.getLogger(CohortsController.class);
    private final Mediator mediator;

    @Autowired
    CohortsController(Mediator mediator)
    {
        this.mediator = mediator;
    }

    @GetMapping("/unique")
    public GetUniqueCohort.Model getUniqueCohort(@RequestParam GetUniqueCohort.Query message)
    {
        return this.mediator.send(message, GetUniqueCohort.Model.class);
    }
}
