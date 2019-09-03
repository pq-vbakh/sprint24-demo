package com.psyquation.core.service;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.psyquation.core.model.subscriptions.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static com.google.common.collect.Sets.newHashSet;
import static com.psyquation.core.factory.SubscriptionFactory.createSubscription;
import static com.psyquation.core.model.enums.AlertType.*;
import static com.psyquation.core.model.enums.TargetType.EMAIL;
import static com.psyquation.core.model.enums.TargetType.SMS;
import static java.util.Arrays.asList;

public class DumyUS06SubscriptionService implements US06SubscriptionService {

    @Override
    public List<Map<String, AttributeValue>> initialSubscriptions(Long userId) {
        OffsetDateTime create = OffsetDateTime.of(2019, 4 ,2, 23, 0, 0, 0, ZoneOffset.UTC);
        List<Map<String, AttributeValue>> subscriptions = new LinkedList<>();
        for (String symbol : getSymbols()) {
            subscriptions.addAll(asList(
                createSubscription(userId, null, N_DAY_STREAK.name(), newHashSet(EMAIL), new NDayStreakSubscription("falling", symbol, 5), create),
                createSubscription(userId, null, MOVING_AVERAGE_CROSS.name(), newHashSet(EMAIL), new MovingAverageCrossSubscription("SimpleMovingAverage", symbol, 5, 10), create),
                createSubscription(userId, null, MOVING_AVERAGE_BREACH.name(), newHashSet(EMAIL), new MovingAverageBreachSubscription("SimpleMovingAverage", symbol, 5), create),
                createSubscription(userId, null, N_DAY_HIGH_LOW.name(), newHashSet(EMAIL), new NDayHighLowSubscription("high", symbol, 5), create),
                createSubscription(userId, null, VOLATILITY.name(), newHashSet(EMAIL), new VolatilitySubscription(symbol, 0.5), create)
            ));
        }

        for (Integer login : getLogins()) {
            subscriptions.addAll(asList(
                createSubscription(userId, login, EQUITY_LOW.name(), newHashSet(SMS), new EquitySubscription(500.0), create),
                createSubscription(userId, login, EQUITY_HIGH.name(), newHashSet(SMS), new EquitySubscription(1000.0), create)
            ));
        }

        int login = getLogins()[0];
        subscriptions.addAll(asList(
            createSubscription(userId, login, TRADE_DURATION.name(), newHashSet(SMS), new NoInputsSubscription(), create),
            createSubscription(userId, login, MARTINGALE.name(), newHashSet(SMS, EMAIL), new NoInputsSubscription(), create),
            createSubscription(userId, login, DOLLAR_COST_AVERAGING.name(), newHashSet(EMAIL), new NoInputsSubscription(), create),
            createSubscription(userId, login, LOSING_WINNING_STREAK.name(), newHashSet(EMAIL), new LosingWinningStreakSubscription("win", 5), create),
            createSubscription(userId, login, ROLLING_DAILY_MONTHLY_CONSECUTIVE_OF_TRADES.name(), newHashSet(EMAIL), new RollingDMConsequetiveSubscription("daily", 3), create),
            createSubscription(userId, login, DAILY_MONTHLY_PNL_LIMIT.name(), newHashSet(EMAIL), new PnlLimitSubscription("monthly", "below", 400.), create)
        ));
        return subscriptions;
    }
}