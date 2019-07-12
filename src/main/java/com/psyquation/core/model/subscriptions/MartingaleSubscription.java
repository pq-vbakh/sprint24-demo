package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public class MartingaleSubscription implements AlertSubscription {

    @Override
    public Map<String, AttributeValue> params() {
        return null;
    }

    @Override
    public Map<String, AttributeValue> inputs() {
        return null;
    }
}
