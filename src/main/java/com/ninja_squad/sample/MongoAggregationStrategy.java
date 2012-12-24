package com.ninja_squad.sample;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mongodb.MongoDbConstants;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class MongoAggregationStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange old, Exchange current) {
        DBObject inDb = current.getIn().getBody(DBObject.class);

        String trend = current.getIn().getHeader("trend", String.class);

        DBObject toDb = inDb != null ?
                BasicDBObjectBuilder.start(inDb.toMap()).get() :
                BasicDBObjectBuilder.start().get();
        Integer lastTrend = inDb != null && inDb.containsField(trend) ? (Integer) inDb.get(trend) : 0;
        toDb.put(trend, ++lastTrend);

        DBObject filter = BasicDBObjectBuilder.start().add("_id", inDb != null ? inDb.get("_id") : null).get();
        current.getOut().setBody(new Object[]{filter, toDb});
        current.getOut().setHeader(MongoDbConstants.UPSERT, true);
        return current;
    }

    @Override
    public String toString() {
        return "mongoAggregator";
    }
}
