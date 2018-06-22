package com.kixeye.analytics.offertool.domain.offers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class OfferContentSerializer extends StdSerializer<OfferContent>
{
    public OfferContentSerializer()
    {
        this(null);
    }

    public OfferContentSerializer(Class<OfferContent> t)
    {
        super(t);
    }

    @Override
    public void serialize(OfferContent value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        gen.writeStartObject();
        if (value.getGold() >= 0)
        {
            gen.writeNumberField("gold", value.getGold());
        }

        if (value.getSkus().size() > 0)
        {
            gen.writeObjectFieldStart("skus");
            for (Map.Entry<String,Integer> entry : value.getSkus().entrySet())
            {
                gen.writeNumberField(entry.getKey(), entry.getValue());
            }
            gen.writeEndObject();
        }

        if (value.getUnitUnlocks().size() > 0)
        {
            gen.writeArrayFieldStart("unit_unlocks");
            for (OfferContentUnitUnlock ocuu : value.getUnitUnlocks())
            {
                gen.writeStartObject();
                gen.writeNumberField("type", ocuu.getType());
                gen.writeNumberField("level", ocuu.getLevel());
                gen.writeNumberField("skin", ocuu.getSkin());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }

        if (value.getUnits().size() > 0)
        {
            gen.writeArrayFieldStart("units");
            for (OfferContentUnit ocu : value.getUnits())
            {
                gen.writeStartObject();
                gen.writeNumberField("type", ocu.getType());
                gen.writeNumberField("amount", ocu.getAmount());
                gen.writeNumberField("level", ocu.getLevel());
                gen.writeNumberField("promotion", ocu.getPromotion());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }

        gen.writeEndObject();
    }
}
