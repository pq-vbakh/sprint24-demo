package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public interface AlertSubscription {

    default Map<String, AttributeValue> params() {
        return null;
    }

    default Map<String, AttributeValue> inputs() {
        return null;
    }
}
