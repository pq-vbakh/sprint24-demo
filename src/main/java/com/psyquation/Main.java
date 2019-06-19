package com.psyquation;

import com.google.common.collect.Sets;
import com.psyquation.infrastructure.DynamoDBPublisher;
import lombok.SneakyThrows;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import static com.psyquation.core.model.TargetType.*;
import static com.psyquation.core.factory.ConfigFactory.*;
import static com.psyquation.core.factory.ConfigFactory.createEquityConfig;

public class Main {

    private static final String SERVER_NAME = "US05-Live";
    private static final Integer LOGIN = 835_123;
    private static final String NAMESPACE = String.format("axi:%s:%d", SERVER_NAME, LOGIN);

    @SneakyThrows
    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // simulate user updated alert config through REST API
        persistConfigsForBatch();

        // simulate equity update
        //KinesisPublisher.publish(AlertRelatedDataFactory.createEquityData(SERVER_NAME, 3284438, 1240.));
        //KinesisPublisher.publish(AlertRelatedDataFactory.createTradeDurationData(SERVER_NAME, "EURUSD", 1_000L));
    }

    @SneakyThrows
    private static void persistConfigsForBatch() {
        OffsetDateTime create = OffsetDateTime.of(2019, 4 ,2, 23, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime update = create.plusMinutes(30);

        DynamoDBPublisher.persist(createEquityConfig(
            String.format("axi:%s:%d", SERVER_NAME, 98826),
            Sets.newHashSet(SMS),
            new EquityUserInput(create, 0., 1000.)
        ));

        Thread.sleep(5_000);

        DynamoDBPublisher.persist(createEquityConfig(
            String.format("axi:%s:%d", SERVER_NAME, 98826),
            Sets.newHashSet(SMS, EMAIL),
            new EquityUserInput(update, 500., 1000.)
        ));

        DynamoDBPublisher.persist(createEquityConfig(
            String.format("axi:%s:%d", SERVER_NAME, 84925),
            Sets.newHashSet(SMS),
            new EquityUserInput(create.plusMinutes(10), 0., 1000.)
        ));

        DynamoDBPublisher.persist(createEquityConfig(
            String.format("axi:%s:%d", SERVER_NAME, 300809),
            Sets.newHashSet(MOBILE, WEBSOCKET),
            new EquityUserInput(create.plusMinutes(50), 0., 1000.)
        ));
    }
}
