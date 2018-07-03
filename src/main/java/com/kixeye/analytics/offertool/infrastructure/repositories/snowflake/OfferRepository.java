package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import com.kixeye.analytics.offertool.domain.models.OfferSummary;
import com.kixeye.analytics.offertool.domain.models.UserOffer;
import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

import static com.kixeye.analytics.offertool.domain.models.snowflake.Offer.*;

@Repository
public class OfferRepository
{
    private final Logger log = LoggerFactory.getLogger(OfferRepository.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
//
//    private final static String ID = "ID";
//    private final static String OFFER_CODE = "OFFER_CODE";
//    private final static String DURATION = "DURATION";
//    private final static String CONTENT = "CONTENT";
//    private final static String DISPLAY_OPTIONS = "DISPLAY_OPTIONS";
//    private final static String DISPLAYED_ITEMS = "DISPLAYED_ITEMS";
//    private final static String START_TIME = "START_TIME";
//    private final static String END_TIME = "END_TIME";
//    private final static String COST = "COST";
//    private final static String COST_SKU = "COST_SKU";
//    private final static String FULL_COST = "FULL_COST";
//    private final static String TITLE = "TITLE";
//    private final static String DESCRIPTION = "DESC";
//    private final static String TEMPLATE_ID = "TEMPLATE_ID";
//    private final static String MODIFIED_TIME = "MOD_TIME";
//    private final static String CREATED_TIME = "CREATED_TIME";
//    private final static String IS_DELETED = "IS_DELETED";
//    private final static String IS_ENABLED = "IS_ENABLED";

//    ICON_TITLE	VARCHAR(16777216)
//    ICON_DESC	VARCHAR(16777216)
//


    @Autowired
    OfferRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                    JdbcTemplate jdbcTemplate)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Offer> findAll()
    {
        long end = Instant.now().toEpochMilli(); // TODO: add arbitrary time
        return findAll(0, end);
    }


    public List<Offer> findAll(long startTime, long endTime)
    {
        String query = "select * from WC.MYSQL.OFFERS_WC where START_TIME >= :startTime and END_TIME <= :endTime";
        Map<String, Long> properties = new HashMap<>();
        properties.put("startTime", startTime);
        properties.put("endTime", endTime);

        List<Offer> offers = this.namedParameterJdbcTemplate.query(query, properties, new OfferRowMapper());
        return offers;
    }

//    public Optional<Offer> findById(int offerId)
//    {
//        String sql = "SELECT * FROM wc.mysql.offers_wc WHERE ID = ?;";
//        return find(sql, offerId);
//    }
//
//    public Optional<Offer> findByCode(String offerCode)
//    {
//        String sql = "select * from WC.MYSQL.OFFERS_WC where OFFER_CODE = ?";
//        return find(sql, offerCode);
//    }
//
//    private Optional<Offer> find(String sql, Object... values)
//    {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try
//        {
//            connection = this.dataSource.getConnection();
//            ps = prepareStatement(connection, sql, false, values);
//            rs = ps.executeQuery();
//            Offer offer = null;
//            if (rs.next())
//            {
//                offer = mapOffer(rs);
//            }
//            else
//            {
//                log.warn("No result found");
//            }
//
//            rs.close();
//            ps.close();
//
//            if (offer != null)
//            {
//                return Optional.of(offer);
//            }
//
//        }
//        catch (SQLException e)
//        {
//            log.error("An error occurred while retrieving offers. Query: {}", sql, e);
//        }
//        finally
//        {
//            if (connection != null)
//            {
//                try
//                {
//                    connection.close();
//                }
//                catch (SQLException e)
//                {
//                    log.error("An error occurred while closing connection", e);
//
//                }
//            }
//        }
//
//        return Optional.empty();
//    }
//


//    public List<UserOffer> findUserOffers(int offerId)
//    {
//        Connection connection = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        String sql = "select * from WC.MYSQL.USER_OFFERS_WC where OFFER_ID = ?";
//        List<UserOffer> offers = new ArrayList<>();
//
//        try
//        {
//            connection = this.dataSource.getConnection();
//            ps = prepareStatement(connection, sql, false, offerId);
//            log.debug("Executing statement on Snowflake to get all user offers for offer {}", offerId);
//            rs = ps.executeQuery();
//
//            while (rs.next())
//            {
//                UserOffer userOffer = mapUserOffer(rs);
//                log.trace(userOffer.toString());
//                offers.add(userOffer);
//            }
//
//            log.trace("Finished iterating through result set");
//            rs.close();
//            ps.close();
//        }
//        catch (SQLException e)
//        {
//            log.error("An error occurred while retrieving offers", e);
//        }
//        finally
//        {
//            if (connection != null)
//            {
//                try
//                {
//                    connection.close();
//                }
//                catch (SQLException e)
//                {
//                    log.error("An error occurred while closing connection", e);
//                }
//            }
//        }
//
//        return offers;
//
//    }


    public Offer mapOffer(ResultSet rs) throws SQLException
    {
        Offer offer = new Offer();
        offer.setOfferCode(rs.getString(OFFER_CODE));
        offer.setId(rs.getInt(ID));
        offer.setContent(rs.getString(CONTENT));
        offer.setDisplayedItems(rs.getString(DISPLAYED_ITEMS));
        offer.setDuration(rs.getLong(DURATION));
        offer.setStartTime(rs.getLong(START_TIME));
        offer.setEndTime(rs.getLong(END_TIME));
        //offer.setMaxQuantity(rs.getInt(M));
        offer.setCost(rs.getInt(COST));
        offer.setFullCost(rs.getInt(FULL_COST));
        // offer.setCostSku(rs.getString(COST_SKU));

        log.debug(offer.toString());
        return offer;
    }

    private UserOffer mapUserOffer(ResultSet rs) throws SQLException
    {
        UserOffer offer = new UserOffer();

        offer.setId(rs.getInt("ID"));
        offer.setUserId(rs.getInt("USER_ID"));
        offer.setOfferId(rs.getInt("OFFER_ID"));
        offer.setStatus(rs.getInt("STATUS"));
        offer.setModifiedTime(rs.getLong("MOD_TIME"));
        offer.setCreatedTime(rs.getLong("CREATED_TIME"));
        offer.setEndTime(rs.getLong("END_TIME"));

        return offer;
    }
}
