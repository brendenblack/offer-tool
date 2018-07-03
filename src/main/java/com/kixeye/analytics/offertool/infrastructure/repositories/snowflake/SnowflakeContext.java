package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Getter
public class SnowflakeContext
{
    private final NamedParameterJdbcTemplate parameterJdbc;
    private final JdbcTemplate jdbc;

    public static final String OFFERS_TABLE = "WC.MYSQL.OFFERS_WC";

    @Autowired
    SnowflakeContext(@Qualifier("snowflakeNamedJdbc") NamedParameterJdbcTemplate namedParameterJdbcTemplateJdbc,
                     @Qualifier("snowflakeJdbc") JdbcTemplate jdbcTemplate)
    {
        this.parameterJdbc = namedParameterJdbcTemplateJdbc;
        this.jdbc = jdbcTemplate;
    }
}
