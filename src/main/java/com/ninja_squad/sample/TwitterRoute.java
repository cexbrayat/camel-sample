package com.ninja_squad.sample;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.twitter.TwitterComponent;
import org.apache.camel.main.Main;

import java.io.FileInputStream;
import java.util.Properties;

public class TwitterRoute extends RouteBuilder {

    private static String consumerKey;
    private static String consumerSecret;
    private static String accessToken;
    private static String accessTokenSecret;
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
                //.to("mongodb:sample?database=flights&collection=tickets&operation=insert");
                .to("log:twitter");
    }

    public static void main(String[] args) throws Exception {
        // loading properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/credentials.properties"));
        accessToken = properties.getProperty("accessToken");
        accessTokenSecret = properties.getProperty("accessTokenSecret");
        consumerKey = properties.getProperty("consumerKey");
        consumerSecret = properties.getProperty("consumerSecret");

        // launching camel
        Main main = new Main();

        main.enableHangupSupport();

        TwitterRoute route = new TwitterRoute();

        main.addRouteBuilder(route);

        main.run();
    }

}
