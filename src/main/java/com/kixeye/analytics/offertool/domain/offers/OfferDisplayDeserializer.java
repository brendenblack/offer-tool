//package com.kixeye.analytics.offertool.domain.offers;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class OfferDisplayDeserializer extends StdDeserializer<OfferDisplayDeserializer>
//{
//    private final Logger log = LoggerFactory.getLogger(OfferDisplayDeserializer.class);
//
//    public OfferDisplayDeserializer()
//    {
//        this(null);
//    }
//
//    public OfferDisplayDeserializer(Class<?> vc)
//    {
//        super(vc);
//    }
//
//    @Override
//    public OfferDisplayDeserializer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
//    {
//        List<OfferDisplayedItem> items = new ArrayList<>();
//        JsonNode n = p.getCodec().readTree(p);
//        for (JsonNode display : n)
//        {
//            String itemName = n.get("item").asText();
//            int amount = n.get("amount").asInt();
//            int order = n.get("order").asInt();
//
//            OfferDisplayedItem item = new OfferDisplayedItem();
//            item.setItem(itemName);
//            item.setAmount(amount);
//            item.setOrder(order);
//            items.add(item);
//        }
//
//        return items;
//    }
//}
