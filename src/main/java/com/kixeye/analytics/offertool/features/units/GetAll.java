package com.kixeye.analytics.offertool.features.units;

import com.kixeye.analytics.offertool.domain.models.Unit;
import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.Context;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetAll
{
    public static class Query {}

    @Getter
    @Setter
    public static class Model
    {
        private List<UnitModel> units = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class UnitModel
    {
        private int id;
        private String name;
        private String faction;
    }

    @Service("getAllUnits")
    public static class Handler implements RequestHandler<Query, Model>
    {
        private final Logger log = LoggerFactory.getLogger(Handler.class);
        private final Context context;

        Handler(Context context)
        {
            this.context = context;
        }

        @Override
        public Model handle(Query message)
        {
            List<UnitModel> models = new ArrayList<>();
            for (Unit unit : this.context.getUnits().findAll())
            {
                UnitModel u = new UnitModel();
                u.setId(unit.getId());
                u.setName(unit.getName());
                u.setFaction(Optional.ofNullable(unit.getFaction()).map(x -> x.getName()).orElse("none"));
                log.debug(u.toString());
                models.add(u);
            }

            Model result = new Model();
            result.setUnits(models);
            return result;
        }

        @Override
        public Class getRequestType() {
            return Query.class;
        }

        @Override
        public Class getReturnType() {
            return Model.class;
        }
    }
}
