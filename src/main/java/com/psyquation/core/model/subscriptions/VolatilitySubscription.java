package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class VolatilitySubscription implements AlertSubscription {

    private final String symbol;
    private final Double percentage;

    @Override
    public Map<String, AttributeValue> params() {
        return new HashMap<String, AttributeValue>() {{
            put("symbol", new AttributeValue(symbol));
            put("quantile_percentage", new AttributeValue("" + percentage));
        }};
    }
}
