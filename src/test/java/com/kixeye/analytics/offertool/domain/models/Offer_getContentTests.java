package com.kixeye.analytics.offertool.domain.models;

import com.kixeye.analytics.offertool.domain.offers.OfferContent;
import com.kixeye.analytics.offertool.domain.offers.OfferContentUnit;
import com.kixeye.analytics.offertool.domain.offers.OfferContentUnitUnlock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class Offer_getContentTests
{

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void shouldHydrateSkus()
    {
        Offer offer = new Offer();
        String json = "{\"skus\":{\"titan-warpaint-inferno-componentunlocked\":1,  \"titan-warpaint-nova-componentunlocked\":1, \"titan-warpaint-reaper-componentunlocked\":1}}";
        offer.setContentJson(json);

        OfferContent content = offer.getContent();

        assertThat(content.getSkus().get("titan-warpaint-inferno-componentunlocked"), is(1));
    }

    @Test
    public void shouldReadUnits()
    {
        Offer offer = new Offer();
        String json = "{\"unit_unlocks\":[\n" +
                "{\"type\":70, \"level\":15, \"skin\":2}],\n" +
                "\n" +
                "\n" +
                "\"units\":[\n" +
                "{\"type\":146, \"amount\":1, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":145, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":160, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":154, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":153, \"amount\":4, \"level\":10, \"promotion\":0}]}";
        offer.setContentJson(json);

        OfferContent content = offer.getContent();

        assertThat(content.getUnits().size(), is(5));
    }

    @Test
    public void shouldHydrateUnits() {
        Offer offer = new Offer();
        String json = "{\"unit_unlocks\":[\n" +
                "{\"type\":70, \"level\":15, \"skin\":2}],\n" +
                "\n" +
                "\n" +
                "\"units\":[\n" +
                "{\"type\":146, \"amount\":1, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":145, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":160, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":154, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":153, \"amount\":4, \"level\":10, \"promotion\":0}]}";
        offer.setContentJson(json);
        assertThat(offer.getContentJson(), not(nullValue()));

        OfferContent content = offer.getContent();
        assertThat(content, not(nullValue()));
        Optional<OfferContentUnit> first = content.getUnits().stream().filter(u -> u.getType() == 146).findFirst();

        assertThat(first.isPresent(), is(true));
        collector.checkThat(first.get().getAmount(), is(1));
        collector.checkThat(first.get().getLevel(), is(10));
        collector.checkThat(first.get().getPromotion(), is(0));
    }

    @Test
    public void shouldHydrateUnitUnlocks() {
        Offer offer = new Offer();
        String json = "{\"unit_unlocks\":[\n" +
                "{\"type\":70, \"level\":15, \"skin\":2}],\n" +
                "\n" +
                "\n" +
                "\"units\":[\n" +
                "{\"type\":146, \"amount\":1, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":145, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":160, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":154, \"amount\":4, \"level\":10, \"promotion\":0},\n" +
                "{\"type\":153, \"amount\":4, \"level\":10, \"promotion\":0}]}";
        offer.setContentJson(json);
        assertThat(offer.getContentJson(), not(nullValue()));

        OfferContent content = offer.getContent();
        assertThat(content, not(nullValue()));
        Optional<OfferContentUnitUnlock> first = content.getUnitUnlocks().stream().filter(u -> u.getType() == 70).findFirst();

        assertThat(first.isPresent(), is(true));
        collector.checkThat(first.get().getType(), is(70));
        collector.checkThat(first.get().getLevel(), is(15));
        collector.checkThat(first.get().getSkin(), is(2));
    }

}
