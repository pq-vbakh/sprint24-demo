package com.psyquation.core.factory;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

public class ConfigFactory {

    public static Map<String, AttributeValue> createEquityConfig(String namespace, double min, double max) {
        return createConfig(namespace, "EQUITY", new HashMap<String, String>() {{
            put("equity_low", "" + min);
            put("equity_high","" + max);
        }});
    }

    public static Map<String, AttributeValue> removeEquityConfig(String namespace) {
        throw new UnsupportedOperationException();
    }

    private static Map<String, AttributeValue> createConfig(String namespace, String alertType, Map<String, String> thresholds) {
        AttributeValue timestamp = new AttributeValue(
            OffsetDateTime.now()
                .withMinute(1)
                .withSecond(1)
                .withNano(0)
                .toString()
        );
        return new HashMap<String, AttributeValue>() {{
            put("namespace", new AttributeValue(namespace));
            put("timestamp", timestamp);
            put("type", new AttributeValue(alertType));
            put("user_inputs", userInputs(timestamp, thresholds));
        }};
    }

    /**
     * @param timestamp
     * @param thresholds
     * @return user typed configs for specific alert type
     */
    private static AttributeValue userInputs(AttributeValue timestamp, Map<String, String> thresholds) {
        AttributeValue paramsAttr = new AttributeValue();
        paramsAttr.setM(emptyMap());

        AttributeValue thresholdsAttr = new AttributeValue();
        thresholdsAttr.setM(
            thresholds.entrySet()
                .stream()
                .collect(toMap(
                    Map.Entry::getKey,
                    e -> new AttributeValue(e.getValue())
                ))
        );

        AttributeValue input = new AttributeValue();
        input.setM(
            new HashMap<String, AttributeValue>() {{
                put("params", paramsAttr);
                put("thresholds", thresholdsAttr);
                put("timestamp", timestamp);
            }}
        );

        AttributeValue userInputs = new AttributeValue();
        userInputs.setL(asList(input));
        return userInputs;
    }
}
