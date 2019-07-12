package com.psyquation.core.factory;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.psyquation.core.model.enums.DynamoDBAction;
import com.psyquation.core.model.enums.TargetType;
import com.psyquation.core.model.subscriptions.AlertSubscription;
import java.time.OffsetDateTime;
import java.util.*;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

public class SubscriptionFactory {

    public static Map<String, AttributeValue> createSubscription(Long userId, Long login, String alertType, Set<TargetType> targetTypes, AlertSubscription subscription, OffsetDateTime timestamp) {
        return new HashMap<String, AttributeValue>() {{
            put("id", str(randomUUID().toString()));
            put("type", str(alertType));
            put("userId", num(userId));
            put("namespace", str("axi:US06-Live:" + login));
            if (subscription.params() != null)
                put("params", map(subscription.params()));
            if (subscription.inputs() != null)
                put("inputs", map(subscription.inputs()));
            put("action", str(DynamoDBAction.INSERT.name()));
            put("timestamp", num(timestamp.toEpochSecond()));
            put("targets", list(targetTypes));
            put("active",bool(true));
            put("table", str("pq-cthulhu-alert-subscription"));
        }};
    }

    private static AttributeValue str(String value) {
        return new AttributeValue(value);
    }

    private static AttributeValue str(Number value) {
        return new AttributeValue("" + value);
    }

    private static AttributeValue num(Number value) {
        AttributeValue attr = new AttributeValue();
        attr.setN(String.valueOf(value));
        return attr;
    }

    private static AttributeValue bool(boolean value) {
        AttributeValue attr = new AttributeValue();
        attr.setBOOL(value);
        return attr;
    }

    private static AttributeValue list(Collection<? extends Object> collection) {
        AttributeValue attr = new AttributeValue();
        attr.setL(
            collection.stream().map(Object::toString).map(AttributeValue::new).collect(toList())
        );
        return attr;
    }

    private static AttributeValue map(Map<String, AttributeValue> map) {
        AttributeValue attr = new AttributeValue();
        attr.setM(map);
        return attr;
    }
}
