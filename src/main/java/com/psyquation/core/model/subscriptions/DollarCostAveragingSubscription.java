package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public class DollarCostAveragingSubscription implements AlertSubscription {

    @Override
    public Map<String, AttributeValue> params() {
        return null;
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return null;
    }
}
