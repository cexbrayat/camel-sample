package com.ninja_squad.sample;

import org.apache.camel.builder.RouteBuilder;

public class TrendRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:love")
                .routeId("love")
                .filter(body().not().contains("not"))
                .setHeader("trend", constant("love"))
                .process(new MongoProcessor())
                .to("direct:mongo");

        from("direct:hate")
                .routeId("hate")
                .filter(body().not().contains("not"))
                .setHeader("trend", constant("hate"))
                .process(new MongoProcessor())
                .to("direct:mongo");

        from("direct:neutral")
                .routeId("neutral")
                .setHeader("trend", constant("neutral"))
                .process(new MongoProcessor())
                .to("direct:mongo");

        from("direct:ninja")
                .routeId("ninja")
                .filter(body().contains("ninja"))
                .setHeader("trend", constant("ninja"))
                .process(new MongoProcessor())
                .to("direct:mongo");

    }

}
