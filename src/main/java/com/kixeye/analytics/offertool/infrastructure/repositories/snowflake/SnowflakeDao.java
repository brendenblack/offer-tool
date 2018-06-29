package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SnowflakeDao
{
    private final Logger log = LoggerFactory.getLogger(SnowflakeDao.class);
    private final DataSource dataSource;

// https://www.developer.com/db/working-with-jdbc-and-spring.html

    SnowflakeDao(@Qualifier("snowflakeDataSource") DataSource dataSource)
    {
        this.dataSource = dataSource;
    }


    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     * @param connection The Connection to create the PreparedStatement from.
     * @param sql The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during creating the PreparedStatement.
     */
    public PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGeneratedKeys, Object... values)
            throws SQLException
    {
        // , returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setValues(preparedStatement, values);
        return preparedStatement;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    public void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException
    {
        for (int i = 0; i < values.length; i++)
        {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }


    public ResultSet execute(String sql, Object... values) throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            connection = this.dataSource.getConnection();
            ps = prepareStatement(connection, sql, false, values);
            rs = ps.executeQuery();

            rs.close();
            ps.close();

        }
        finally
        {
            if (connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException e)
                {
                    log.error("An error occurred while closing connection", e);

                }
            }
        }
    }
}
