package com.ninja_squad.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;

public class TwitterRoute extends RouteBuilder {

    private String searchTerm = "bieber";
    private int delay = 1;

    @java.lang.Override
    public void configure() throws Exception {
        // setup Twitter component
        TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
        tc.setAccessToken(getContext().resolvePropertyPlaceholders("{{accessToken}}"));
        tc.setAccessTokenSecret(getContext().resolvePropertyPlaceholders("{{accessTokenSecret}}"));
        tc.setConsumerKey(getContext().resolvePropertyPlaceholders("{{consumerKey}}"));
        tc.setConsumerSecret(getContext().resolvePropertyPlaceholders("{{consumerSecret}}"));

        // poll twitter search for new tweets
        fromF("twitter://search?type=polling&delay=%s&keywords=%s", delay, searchTerm)
                .routeId("twitter")
                .choice()
                .when(body().contains("love"))
                .to("direct:love")
                .when(body().contains("hate"))
                .to("direct:hate")
                .otherwise()
                .multicast().to("direct:neutral", "direct:ninja")
                .end();
    }

}
