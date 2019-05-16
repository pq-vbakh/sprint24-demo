package com.psyquation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psyquation.core.factory.AlertRelatedDataFactory;
import com.psyquation.core.factory.ConfigFactory;
import com.psyquation.infrastructure.DynamoDBPublisher;
import com.psyquation.infrastructure.KinesisPublisher;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final String SERVER_NAME = "US05-Live";
    private static final Integer LOGIN = 835_123;
    private static final String NAMESPACE = String.format("axi:%s:%d", SERVER_NAME, LOGIN);

    public static void main(String... args) throws JsonProcessingException, ExecutionException, InterruptedException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // simulate user updated alert config through REST API
        DynamoDBPublisher.persist(ConfigFactory.createEquityConfig(NAMESPACE, 0., 200.));

        // simulate equity update
        //KinesisPublisher.publish(AlertRelatedDataFactory.createEquityData(SERVER_NAME, LOGIN, 800.));
        //KinesisPublisher.publish(AlertRelatedDataFactory.createTradeDurationData(SERVER_NAME, "EURUSD", 1_000L));
    }
}
