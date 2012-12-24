package com.ninja_squad.sample;

import com.mongodb.Mongo;

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

        // console

        // twitter route
        TwitterRoute twitter = new TwitterRoute();
        twitter.setAccessToken(properties.getProperty("accessToken"));
        twitter.setAccessTokenSecret(properties.getProperty("accessTokenSecret"));
        twitter.setConsumerKey(properties.getProperty("consumerKey"));
        twitter.setConsumerSecret(properties.getProperty("consumerSecret"));

        main.addRouteBuilder(twitter);

        // mongo route
        main.bind("db", new Mongo());
        MongoRoute mongo = new MongoRoute();
        main.addRouteBuilder(mongo);

        // trend route
        TrendRoute trend = new TrendRoute();
        main.addRouteBuilder(trend);

        main.run();
    }
}
