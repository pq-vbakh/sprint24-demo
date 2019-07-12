package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import static java.util.Collections.singletonMap;

@RequiredArgsConstructor
public class PnlLimitSubscription implements AlertSubscription {

    private final String type;
    private final String direction;
    private final Double threshold;

    @Override
    public Map<String, AttributeValue> params() {
        return new HashMap<String, AttributeValue>() {{
            put("type", new AttributeValue(type));
            put("direction", new AttributeValue(direction));
        }};
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return singletonMap("threshold", new AttributeValue("" + threshold));
    }
}
