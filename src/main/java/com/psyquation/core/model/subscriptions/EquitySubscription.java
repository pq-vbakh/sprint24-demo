package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class EquitySubscription implements AlertSubscription {

    private final double threshold;

    @Override
    public Map<String, AttributeValue> inputs() {
        return Collections.singletonMap("threshold", new AttributeValue("" + threshold));
    }
}
