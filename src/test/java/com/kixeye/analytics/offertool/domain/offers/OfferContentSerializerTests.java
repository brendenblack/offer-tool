package com.kixeye.analytics.offertool.domain.offers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OfferContentSerializerTests {
    private ObjectMapper mapper;
    private OfferContentSerializer sut;
    private Writer writer;
    private JsonGenerator gen;

    @Before
    public void setup() throws IOException {
        this.mapper = new ObjectMapper();
        this.sut = new OfferContentSerializer();
        this.writer = new StringWriter();
        this.gen = new JsonFactory().createGenerator(this.writer);
    }

    @Test
    public void shouldSerializeSkus() throws IOException {
        OfferContent content = new OfferContent();
        content.getSkus().put("testsku", 5);

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"skus\":{\"testsku\":5}}")));
    }

    @Test
    public void shouldSerializeGold() throws IOException {
        OfferContent content = new OfferContent();
        content.setGold(20);

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"gold\":20}")));
    }

    @Test
    public void shouldSerializeSingleUnit() throws IOException {
        OfferContentUnit unit1 = new OfferContentUnit();
        unit1.setType(100);
        unit1.setAmount(2);
        unit1.setLevel(1);
        unit1.setPromotion(0);
        OfferContent content = new OfferContent();
        content.setUnits(Arrays.asList(unit1));

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"units\":[{\"type\":100,\"amount\":2,\"level\":1,\"promotion\":0}]}")));
    }

    @Test
    public void shouldSerializeMultipleUnits() throws IOException {
        OfferContentUnit unit1 = new OfferContentUnit();
        unit1.setType(100);
        unit1.setAmount(2);
        unit1.setLevel(1);
        unit1.setPromotion(0);
        OfferContentUnit unit2 = new OfferContentUnit();
        unit2.setType(82);
        unit2.setAmount(20);
        unit2.setLevel(10);
        unit2.setPromotion(1);
        OfferContentUnit unit3 = new OfferContentUnit();
        unit3.setType(14);
        unit3.setAmount(1);
        unit3.setLevel(31);
        unit3.setPromotion(0);
        OfferContent content = new OfferContent();
        content.setUnits(Arrays.asList(unit1, unit2, unit3));

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"units\":[{\"type\":100,\"amount\":2,\"level\":1,\"promotion\":0},{\"type\":82,\"amount\":20,\"level\":10,\"promotion\":1},{\"type\":14,\"amount\":1,\"level\":31,\"promotion\":0}]}")));
    }

    @Test
    public void shouldSerializeSingleUnlock() throws IOException {
        OfferContentUnitUnlock unlock1 = new OfferContentUnitUnlock();
        unlock1.setType(100);
        unlock1.setLevel(1);
        unlock1.setSkin(2);
        OfferContent content = new OfferContent();
        content.setUnitUnlocks(Arrays.asList(unlock1));

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"unit_unlocks\":[{\"type\":100,\"level\":1,\"skin\":2}]}")));
    }

    @Test
    public void shouldSerializeMultipleUnlocks() throws IOException {
        OfferContentUnitUnlock unlock1 = new OfferContentUnitUnlock();
        unlock1.setType(100);
        unlock1.setLevel(1);
        unlock1.setSkin(2);
        OfferContentUnitUnlock unlock2 = new OfferContentUnitUnlock();
        unlock2.setType(85);
        unlock2.setLevel(31);
        unlock2.setSkin(2);
        OfferContentUnitUnlock unlock3 = new OfferContentUnitUnlock();
        unlock3.setType(74);
        unlock3.setLevel(2);
        unlock3.setSkin(7);
        OfferContentUnitUnlock unlock4 = new OfferContentUnitUnlock();
        unlock4.setType(1243);
        unlock4.setLevel(4);
        unlock4.setSkin(2);
        OfferContent content = new OfferContent();
        content.setUnitUnlocks(Arrays.asList(unlock1, unlock2, unlock3, unlock4));

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"unit_unlocks\":[{\"type\":100,\"level\":1,\"skin\":2},{\"type\":85,\"level\":31,\"skin\":2},{\"type\":74,\"level\":2,\"skin\":7},{\"type\":1243,\"level\":4,\"skin\":2}]}")));
    }

    @Test
    public void shouldSerializeAll() throws IOException
    {
        OfferContentUnit unit1 = new OfferContentUnit();
        unit1.setType(100);
        unit1.setAmount(2);
        unit1.setLevel(1);
        unit1.setPromotion(0);
        OfferContentUnitUnlock unlock1 = new OfferContentUnitUnlock();
        unlock1.setType(100);
        unlock1.setLevel(1);
        unlock1.setSkin(2);
        OfferContent content = new OfferContent();
        content.getSkus().put("roland-p15shrapnel-componentunlocked", 1);
        content.getSkus().put("roland-heroictraining-componentunlocked", 1);
        content.setGold(20);
        content.setUnits(Arrays.asList(unit1));
        content.setUnitUnlocks(Arrays.asList(unlock1));

        this.sut.serialize(content, this.gen, this.mapper.getSerializerProvider());
        this.gen.flush();
        String result = this.writer.toString();

        assertThat(result, is(equalTo("{\"gold\":20,\"skus\":{\"roland-heroictraining-componentunlocked\":1,\"roland-p15shrapnel-componentunlocked\":1},\"unit_unlocks\":[{\"type\":100,\"level\":1,\"skin\":2}],\"units\":[{\"type\":100,\"amount\":2,\"level\":1,\"promotion\":0}]}")));
    }
}
