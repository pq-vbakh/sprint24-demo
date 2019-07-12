package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
public class RollingDMConsequetiveSubscription implements AlertSubscription {

    private final String period;
    private final Integer threshold;

    @Override
    public Map<String, AttributeValue> params() {
        return singletonMap("period", new AttributeValue(period));
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return singletonMap("threshold", new AttributeValue("" + threshold));
    }
}
