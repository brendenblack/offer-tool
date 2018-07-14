package com.kixeye.analytics.offertool.features.cohorts;

import com.kixeye.analytics.offertool.infrastructure.mediator.RequestHandler;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.OfferRowMapper;
import com.kixeye.analytics.offertool.infrastructure.repositories.snowflake.SnowflakeContext;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUniqueCohort
{
    @Getter
    @Setter
    public static class Query
    {
        private int unitId;
        private boolean owners = true;
        private boolean nonOwners = true;
    }

    @Getter
    @Setter
    public static class Model
    {

    }

    @Service("getUniqueCohortHandler")
    public static class Handler implements RequestHandler<Query, Model>
    {
        private static final Logger log = LoggerFactory.getLogger(Handler.class);
        private final SnowflakeContext snowflake;

        @Autowired
        Handler(SnowflakeContext snowflake)
        {
            this.snowflake = snowflake;
        }

        @Override
        public Model handle(Query message)
        {
            List<Integer> ownerIds = getOwnerIds(message.getUnitId());



            return null;
        }

        public List<Integer> getOwnerIds(int unitId)
        {
            String query = "select * from WC.MYSQL.USER_ACADEMY_WC where `TYPE` = :unitId";
            Map<String, String> parameters = new HashMap<>();
            parameters.put("unitId", String.valueOf(unitId));

            this.snowflake.getParameterJdbc().queryForObject(query, parameters, new OfferRowMapper());


            return new ArrayList<>();
        }

        public List<Integer> getNonOwnerIds(int unitId)
        {
            return new ArrayList<>();
        }

        public List<Integer> getOwnersByLevelRange(int unitId, int lowerBoundary, int upperBoundary)
        {
            return new ArrayList<>();
        }

        @Override
        public Class getRequestType()
        {
            return Query.class;
        }

        @Override
        public Class getReturnType()
        {
            return Model.class;
        }
    }
}
