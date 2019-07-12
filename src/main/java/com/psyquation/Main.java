package com.psyquation;

import com.psyquation.core.model.subscriptions.*;
import com.psyquation.infrastructure.DynamoDBPublisher;
import lombok.SneakyThrows;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import static com.google.common.collect.Sets.newHashSet;
import static com.psyquation.core.model.enums.AlertType.*;
import static com.psyquation.core.model.enums.TargetType.*;
import static com.psyquation.core.factory.SubscriptionFactory.*;

public class Main {

    private static final Long userId = 100L;

    @SneakyThrows
    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // simulate user updated alert config through REST API
        writeAlertSubscriptionEvents();

        // simulate equity update
        //KinesisPublisher.publish(AlertRelatedDataFactory.createEquityData(SERVER_NAME, 3284438, 1240.));
        //KinesisPublisher.publish(AlertRelatedDataFactory.createTradeDurationData(SERVER_NAME, "EURUSD", 1_000L));
    }

    @SneakyThrows
    private static void writeAlertSubscriptionEvents() {
        long login = 830_101;
        OffsetDateTime create = OffsetDateTime.of(2019, 4 ,2, 23, 0, 0, 0, ZoneOffset.UTC);

        DynamoDBPublisher.persist(createSubscription(userId, login, EQUITY_LOW.name(), newHashSet(SMS), new EquitySubscription(0.), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, EQUITY_HIGH.name(), newHashSet(SMS), new EquitySubscription(1_000.), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, MARTINGALE.name(), newHashSet(SMS, EMAIL), new MartingaleSubscription(), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, DOLLAR_COST_AVERAGING.name(), newHashSet(EMAIL), new DollarCostAveragingSubscription(), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, LOSING_WINNING_STREAK.name(), newHashSet(EMAIL), new LosingWinningStreakSubscription("win", 5), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, ROLLING_DAILY_MONTHLY_CONSECUTIVE_OF_TRADES.name(), newHashSet(EMAIL), new RollingDMConsequetiveSubscription("daily", 3), create));
        DynamoDBPublisher.persist(createSubscription(userId, login, DAILY_MONTHLY_PNL_LIMIT.name(), newHashSet(EMAIL), new PnlLimitSubscription("monthly", "below", 400.), create));
        DynamoDBPublisher.persist(createSubscription(userId, null, N_DAY_STREAK.name(), newHashSet(EMAIL), new NDayStreakSubscription("falling", "GBPUSD", 5), create));
        DynamoDBPublisher.persist(createSubscription(userId, null, MOVING_AVERAGE_CROSS.name(), newHashSet(EMAIL), new MovingAverageCrossSubscription("SimpleMovingAverage", "EURUSD", 5, 10), create));
        DynamoDBPublisher.persist(createSubscription(userId, null, MOVING_AVERAGE_BREACH.name(), newHashSet(EMAIL), new MovingAverageBreachSubscription("SimpleMovingAverage", "EURUSD", 5), create));
        DynamoDBPublisher.persist(createSubscription(userId, null, N_DAY_HIGH_LOW.name(), newHashSet(EMAIL), new NDayHighLowSubscription("high", "JPYUAD", 5), create));
        DynamoDBPublisher.persist(createSubscription(userId, null, VOLATILITY.name(), newHashSet(EMAIL), new VolatilitySubscription("UADUSD", 0.5), create));

        Thread.sleep(5_000);

        DynamoDBPublisher.persist(createSubscription(userId, login, EQUITY_HIGH.name(), newHashSet(SMS), new EquitySubscription(2_000.), create.plusMinutes(30)));
    }
}
