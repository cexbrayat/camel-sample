package com.ninja_squad.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;

public class TwitterRoute extends RouteBuilder {

    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String searchTerm = "bieber";
    private int delay = 10;

    @java.lang.Override
    public void configure() {
        // setup Twitter component
        TwitterComponent tc = getContext().getComponent("twitter", TwitterComponent.class);
        tc.setAccessToken(accessToken);
        tc.setAccessTokenSecret(accessTokenSecret);
        tc.setConsumerKey(consumerKey);
        tc.setConsumerSecret(consumerSecret);

        // poll twitter search for new tweets
        fromF("twitter://search?type=polling&delay=%s&keywords=%s", delay, searchTerm)
                .choice()
                .when(body().contains("love"))
                .to("direct:love")
                .when(body().contains("hate"))
                .to("direct:hate")
                .otherwise()
                .multicast().to("direct:neutral", "direct:ninja")
                .end();
    }


    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }


}
