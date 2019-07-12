package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
public class LosingWinningStreakSubscription implements AlertSubscription {

    private final String direction;
    private final Integer threshold;

    @Override
    public Map<String, AttributeValue> params() {
        return singletonMap("direction", new AttributeValue(direction));
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return singletonMap("threshold", new AttributeValue("" + threshold));
    }
}
