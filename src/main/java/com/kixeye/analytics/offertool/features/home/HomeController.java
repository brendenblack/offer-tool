package com.kixeye.analytics.offertool.features.home;

import com.google.common.collect.Lists;
import com.kixeye.analytics.offertool.domain.models.Offer;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/home")
public class HomeController
{
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final Context context;

    public HomeController(Context context)
    {
        this.context = context;
    }


}
