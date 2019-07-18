package com.psyquation;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.psyquation.core.model.subscriptions.*;
import com.psyquation.infrastructure.DynamoDBPublisher;
import com.psyquation.infrastructure.KinesisPublisher;
import lombok.SneakyThrows;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import static com.google.common.collect.Sets.newHashSet;
import static com.psyquation.core.factory.AlertRelatedDataFactory.createEquityData;
import static com.psyquation.core.model.enums.AlertType.*;
import static com.psyquation.core.model.enums.TargetType.*;
import static com.psyquation.core.factory.SubscriptionFactory.*;
import static java.util.Arrays.asList;

public class Main {

    private static final Long userId = 100L;
    private static final Integer login = 830_101;
    private static final String PROFILE = "dev";

    @SneakyThrows
    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        DynamoDBPublisher publisher = new DynamoDBPublisher(PROFILE);
        getInitialSubscriptions().forEach(publisher::persist);

        //KinesisPublisher.publish(createEquityData(login, 990.0));
        //KinesisPublisher.publish(AlertRelatedDataFactory.createTradeDurationData(SERVER_NAME, "EURUSD", 1_000L));
    }

    @SneakyThrows
    private static List<Map<String, AttributeValue>> getInitialSubscriptions() {
        OffsetDateTime create = OffsetDateTime.of(2019, 4 ,2, 23, 0, 0, 0, ZoneOffset.UTC);
        return asList(
            createSubscription(userId, login, EQUITY_LOW.name(), newHashSet(SMS), new EquitySubscription(0.), create),
            createSubscription(userId, login, EQUITY_HIGH.name(), newHashSet(SMS), new EquitySubscription(1_000.), create),
            createSubscription(userId, login, MARTINGALE.name(), newHashSet(SMS, EMAIL), new MartingaleSubscription(), create),
            createSubscription(userId, login, DOLLAR_COST_AVERAGING.name(), newHashSet(EMAIL), new DollarCostAveragingSubscription(), create),
            createSubscription(userId, login, LOSING_WINNING_STREAK.name(), newHashSet(EMAIL), new LosingWinningStreakSubscription("win", 5), create),
            createSubscription(userId, login, ROLLING_DAILY_MONTHLY_CONSECUTIVE_OF_TRADES.name(), newHashSet(EMAIL), new RollingDMConsequetiveSubscription("daily", 3), create),
            createSubscription(userId, login, DAILY_MONTHLY_PNL_LIMIT.name(), newHashSet(EMAIL), new PnlLimitSubscription("monthly", "below", 400.), create),
            createSubscription(userId, null, N_DAY_STREAK.name(), newHashSet(EMAIL), new NDayStreakSubscription("falling", "GBPUSD", 5), create),
            createSubscription(userId, null, MOVING_AVERAGE_CROSS.name(), newHashSet(EMAIL), new MovingAverageCrossSubscription("SimpleMovingAverage", "EURUSD", 5, 10), create),
            createSubscription(userId, null, MOVING_AVERAGE_BREACH.name(), newHashSet(EMAIL), new MovingAverageBreachSubscription("SimpleMovingAverage", "EURUSD", 5), create),
            createSubscription(userId, null, N_DAY_HIGH_LOW.name(), newHashSet(EMAIL), new NDayHighLowSubscription("high", "JPYUAD", 5), create),
            createSubscription(userId, null, VOLATILITY.name(), newHashSet(EMAIL), new VolatilitySubscription("UADUSD", 0.5), create)
        );
    }
}
