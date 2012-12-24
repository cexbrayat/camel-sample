package com.ninja_squad.sample;

public class Main {
    public static void main(String[] args) throws Exception {

        // launching camel
        org.apache.camel.spring.Main main = new org.apache.camel.spring.Main();
        main.setApplicationContextUri("classpath:context.xml");

        main.enableHangupSupport();

        main.run();
    }
}
