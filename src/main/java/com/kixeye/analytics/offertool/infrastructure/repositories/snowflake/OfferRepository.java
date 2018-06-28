package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import com.kixeye.analytics.offertool.infrastructure.SnowflakeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class OfferRepository
{
    private final SnowflakeConfiguration configuration;
    private final Logger log = LoggerFactory.getLogger(OfferRepository.class);

    @Autowired
    OfferRepository(SnowflakeConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public void getOffers() throws SQLException
    {
        Connection connection = configuration.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("");


    }
}
