package com.ninja_squad.sample;

import com.mongodb.BasicDBObjectBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MongoProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        String trend = exchange.getIn().getHeader("trend", String.class);
        exchange.getOut().setHeader("trend", trend);
        exchange.getOut().setBody(BasicDBObjectBuilder.start().get());
    }

    @Override
    public String toString() {
        return "mongoProcessor";
    }
}
