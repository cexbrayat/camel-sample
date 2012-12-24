package com.ninja_squad.sample;

import org.apache.camel.builder.RouteBuilder;

public class MongoRoute extends RouteBuilder {


    public static final String MONGODB = "mongodb:db?database=bieber&collection=trends&operation=";

    @Override
    public void configure() throws Exception {
        from("direct:mongo")
                .enrich(MONGODB + "findOneByQuery", new MongoAggregationStrategy())
                .to(MONGODB + "update");
    }
}
