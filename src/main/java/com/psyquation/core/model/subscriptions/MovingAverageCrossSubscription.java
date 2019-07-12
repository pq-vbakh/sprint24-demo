package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MovingAverageCrossSubscription implements AlertSubscription {

    private final String type;
    private final String symbol;
    private final Integer shortPeriod;
    private final Integer longPeriod;

    @Override
    public Map<String, AttributeValue> params() {
        return new HashMap<String, AttributeValue>() {{
            put("type", new AttributeValue(type));
            put("symbol", new AttributeValue(symbol));
            put("short_period", new AttributeValue("" + shortPeriod));
            put("long_period", new AttributeValue("" + longPeriod));
        }};
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return null;
    }
}
