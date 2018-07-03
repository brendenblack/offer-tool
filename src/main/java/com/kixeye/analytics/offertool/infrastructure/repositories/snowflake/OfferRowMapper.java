package com.kixeye.analytics.offertool.infrastructure.repositories.snowflake;

import com.kixeye.analytics.offertool.domain.models.snowflake.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps a row in the WC.MYSQL.OFFERS_WC table to an {@link Offer} object
 */
public class OfferRowMapper implements RowMapper<Offer>
{

    @Override
    public Offer mapRow(ResultSet rs, int rowNumber) throws SQLException
    {
        Offer offer = new Offer();

        offer.setOfferCode(rs.getString(Offer.OFFER_CODE));
        offer.setId(rs.getInt(Offer.ID));
        offer.setTitle(rs.getString(Offer.TITLE));
        offer.setDescription(rs.getString(Offer.DESCRIPTION));
        offer.setIconTitle(rs.getString(Offer.ICON_TITLE));
        offer.setIconDescription(rs.getString(Offer.ICON_DESCRIPTION));
        offer.setContent(rs.getString(Offer.CONTENT));
        offer.setDisplayedItems(rs.getString(Offer.DISPLAYED_ITEMS));
        offer.setDuration(rs.getLong(Offer.DURATION));
        offer.setStartTime(rs.getLong(Offer.START_TIME));
        offer.setEndTime(rs.getLong(Offer.END_TIME));
        //offer.setMaxQuantity(rs.getInt(M));
        offer.setCost(rs.getInt(Offer.COST));
        offer.setFullCost(rs.getInt(Offer.FULL_COST));
        // offer.setCostSku(rs.getString(COST_SKU));
        offer.setCreatedTime(rs.getLong(Offer.CREATED_TIME));
        offer.setModifiedTime(rs.getLong(Offer.MODIFIED_TIME));
        offer.setEnabled(rs.getBoolean(Offer.IS_ENABLED));
        offer.setDeleted(rs.getBoolean(Offer.IS_DELETED));

        return offer;
    }
}
