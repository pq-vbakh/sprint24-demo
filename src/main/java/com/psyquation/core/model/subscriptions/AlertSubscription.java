package com.psyquation.core.model.subscriptions;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public interface AlertSubscription {

    Map<String, AttributeValue> params();

    Map<String, AttributeValue> inputs();
}
