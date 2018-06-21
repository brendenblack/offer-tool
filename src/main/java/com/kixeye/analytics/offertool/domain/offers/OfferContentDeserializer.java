package com.kixeye.analytics.offertool.domain.offers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class OfferContentDeserializer extends StdDeserializer<OfferContent>
{
    private final Logger log = LoggerFactory.getLogger(OfferContentDeserializer.class);

    public OfferContentDeserializer()
    {
        this(null);
    }

    public OfferContentDeserializer(Class<?> vc)
    {
        super(vc);
    }

    @Override
    public OfferContent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        JsonNode n = p.getCodec().readTree(p);
        int gold = Optional.ofNullable(n.get("gold")).map(g -> g.asInt()).orElse(-1);

        OfferContent content = new OfferContent();
        content.setGold(gold);

        if (n.has("skus")) {
            Iterator<Map.Entry<String, JsonNode>> skuNodes = n.get("skus").fields();
            while (skuNodes.hasNext())
            {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) skuNodes.next();
                content.getSkus().put(entry.getKey(), entry.getValue().asInt());
            }
        }

        if (n.has("units"))
        {
            for (JsonNode unitNode : n.get("units"))
            {
                int type = unitNode.get("type").asInt(-1);
                int amount = unitNode.get("amount").asInt(1);
                int level = unitNode.get("level").asInt(1);
                int promotion = unitNode.get("promotion").asInt(0);

                OfferContentUnit ocu = new OfferContentUnit();
                ocu.setType(type);
                ocu.setAmount(amount);
                ocu.setLevel(level);
                ocu.setPromotion(promotion);
                content.getUnits().add(ocu);
            }
        }


        if (n.has("unit_unlocks"))
        {
            for (JsonNode unitUnlockNode : n.get("unit_unlocks"))
            {
                OfferContentUnitUnlock ucuu = new OfferContentUnitUnlock();
                int type = unitUnlockNode.get("type").asInt(-1);
                if (type < 0)
                {
                    continue;
                }

                int level = (unitUnlockNode.has("level")) ? unitUnlockNode.get("level").asInt(1) : 1;
                int skin = (unitUnlockNode.has("skin")) ? unitUnlockNode.get("skin").asInt(2) : 2;

                ucuu.setType(type);
                ucuu.setLevel(level);
                ucuu.setSkin(skin);

                content.getUnitUnlocks().add(ucuu);
            }
        }

        return content;
    }
}
