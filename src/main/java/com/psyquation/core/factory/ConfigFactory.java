package com.psyquation.core.factory;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.psyquation.core.model.TargetType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.OffsetDateTime;
import java.util.*;
import static java.util.Collections.emptyMap;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class ConfigFactory {

    public static Map<String, AttributeValue> createEquityConfig(String namespace, Set<TargetType> targetTypes, EquityUserInput... inputs) {
        return createConfig(namespace,"EQUITY", targetTypes, inputs);
    }

    public static Map<String, AttributeValue> removeEquityConfig(String namespace) {
        throw new UnsupportedOperationException();
    }

    private static Map<String, AttributeValue> createConfig(String namespace, String alertType, Set<TargetType> targetTypes, UserInput... inputs) {

        OffsetDateTime creationTime = Arrays.stream(inputs)
            .map(UserInput::getTimestamp)
            .min(comparing(OffsetDateTime::toEpochSecond))
            .get();

        AttributeValue targetType = new AttributeValue();
        targetType.setL(
            targetTypes.stream()
                .map(type -> new AttributeValue(type.name()))
                .collect(toList()));
        return new HashMap<String, AttributeValue>() {{
            put("namespace", new AttributeValue(namespace));
            put("timestamp", toAttribute(creationTime));
            put("type", new AttributeValue(alertType));
            put("target_types", targetType);
            put("user_inputs", userInputs(inputs));
        }};
    }

    /**
     * @param timestamp
     * @param thresholds
     * @return user typed configs for specific alert type
     */
    private static AttributeValue userInputs(UserInput... inputs) {
        AttributeValue userInputs = new AttributeValue();
        userInputs.setL(
            Arrays.stream(inputs)
                .map(ConfigFactory::toAttribute)
                .collect(toList())
        );
        return userInputs;
    }

    private static AttributeValue toAttribute(UserInput input) {
        AttributeValue paramsAttr = new AttributeValue();
        paramsAttr.setM(emptyMap());

        AttributeValue thresholdsAttr = new AttributeValue();
        thresholdsAttr.setM(input.asMap());

        AttributeValue attr = new AttributeValue();
        attr.setM(
            new HashMap<String, AttributeValue>() {{
                put("params", paramsAttr);
                put("thresholds", thresholdsAttr);
                put("timestamp", toAttribute(input.getTimestamp()));
            }}
        );
        return attr;
    }

    private static AttributeValue toAttribute(OffsetDateTime eventTime) {
        AttributeValue timestamp = new AttributeValue();
        timestamp.setN(String.valueOf(eventTime.toEpochSecond()));
        return timestamp;
    }

    public static class EquityUserInput extends UserInput {

        private final double min;
        private final double max;

        public EquityUserInput(OffsetDateTime timestamp, double min, double max) {
            super(timestamp);
            this.min = min;
            this.max = max;
        }

        @Override
        public Map<String, AttributeValue> asMap() {
            return new HashMap<String, AttributeValue>() {{
                put("equity_low", new AttributeValue("" + min));
                put("equity_high", new AttributeValue("" + max));
            }};
        }
    }

    @Data
    @RequiredArgsConstructor
    public static abstract class UserInput {
        protected final OffsetDateTime timestamp;
        public abstract Map<String, AttributeValue> asMap();
    }
}
