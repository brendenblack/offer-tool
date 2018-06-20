package com.kixeye.analytics.offertool.domain.offers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kixeye.analytics.offertool.domain.models.Offer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the JSON blob in the "content" field of the {@link Offer} object
 *
 * Format: JSON Dict {}
 * "skus":{"sku":1, .... }
 * "building_updates":{"7":16,...}
 * "units":[{"type":141,"amount":1,"level":10,"promotion":0},...]
 * "unit_unlocks":[{"type":70,"level":10,"skin":2},...]
 * "defenselab_unlocks":[{"type":8,"level":8},...]
 * "base_upgrades":[{"type":"ENL","qty":7},...]
 * Intended to be used with 'ENL' (Base Size) and 'BIP' (Resource Compression)
 * 'type' is an 'itemcode' from the store_items DB.
 * 'qty' is either the amount to purchase(if item is 'infinite') or the maximum level to update TO (if not 'infinite')
 * "gold":0
 */
@Getter
@Setter
@ToString
@JsonDeserialize(using = OfferContentDeserializer.class)
public class OfferContent
{
    private List<OfferContentUnit> units = new ArrayList<>();

    private List<OfferContentUnitUnlock> unitUnlocks = new ArrayList<>();

    private Map<String, Integer> skus = new HashMap<>();

    private int gold;
}
