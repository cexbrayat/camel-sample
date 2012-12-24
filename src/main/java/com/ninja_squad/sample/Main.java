package com.ninja_squad.sample;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        // loading properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/credentials.properties"));

        // launching camel
        org.apache.camel.main.Main main = new org.apache.camel.main.Main();

        main.enableHangupSupport();

        // twitter route
        TwitterRoute route = new TwitterRoute();
        route.setAccessToken(properties.getProperty("accessToken"));
        route.setAccessTokenSecret(properties.getProperty("accessTokenSecret"));
        route.setConsumerKey(properties.getProperty("consumerKey"));
        route.setConsumerSecret(properties.getProperty("consumerSecret"));

        main.addRouteBuilder(route);

        main.run();
    }
}
