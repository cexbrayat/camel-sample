package com.ninja_squad.sample;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Mongo;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.spring.javaconfig.test.JavaConfigContextLoader;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.net.UnknownHostException;

@ContextConfiguration(
        locations = "com.ninja_squad.sample.MongoRouteTest$ContextConfig",
        loader = JavaConfigContextLoader.class)
public class MongoRouteTest extends AbstractJUnit4SpringContextTests {

    @Produce(uri = "direct:mongo")
    protected ProducerTemplate template;

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new MongoRoute();
        }

        @Bean
        public Mongo db() throws UnknownHostException {
            return new Mongo();
        }
    }

    @Test
    public void shouldFind() throws Exception {
        Object o = template.requestBodyAndHeader(BasicDBObjectBuilder.start().get(), "trend", "ninja");
        Assertions.assertThat(o).isNotNull();
    }
}
