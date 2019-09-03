package com.psyquation;

import com.psyquation.core.service.DumyUS06SubscriptionService;
import com.psyquation.core.service.US06SubscriptionService;
import com.psyquation.infrastructure.DynamoDBPublisher;
import com.psyquation.infrastructure.KinesisPublisher;
import lombok.SneakyThrows;
import java.util.TimeZone;

import static com.psyquation.core.factory.AlertRelatedDataFactory.createEquityData;

public class Main {

    private static final Long userId = 100L;
    private static final Integer login = 830_101;
    private static final String PROFILE = "dev";

    @SneakyThrows
    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        DynamoDBPublisher publisher = new DynamoDBPublisher(PROFILE);
        US06SubscriptionService service = new DumyUS06SubscriptionService();
        service.initialSubscriptions(userId).forEach(publisher::persist);

        //new KinesisPublisher(PROFILE).publish(createEquityData(405243, -400.));
        //new KinesisPublisher(PROFILE).publish(createTradeDurationData(login,"EURUSD", 90 * 60L));
    }
}