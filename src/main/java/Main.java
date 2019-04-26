import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;

public class Main {

    private static final String TABLE_NAME = "pq-cthulhu-user-alert-config";
    private static final String NAMESPACE = "axi:US06-Live:450342";

    public static void main(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        //TODO uncomment and execute
//        step_6();
//        step_9();
    }

    private static void step_6() {
        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        ddb.putItem(TABLE_NAME, createEquityConfig(0., 100_000.));
    }

    private static void step_9() {
        final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        ddb.putItem(TABLE_NAME, createEquityConfig(2000., 3000.));
    }

    private static Map<String, AttributeValue> createEquityConfig(double min, double max) {
        return createConfig("EQUITY", new HashMap<String, String>() {{
            put("equity_low", "" + min);
            put("equity_high","" + max);
        }});
    }

    private static Map<String, AttributeValue> createConfig(String alertType, Map<String, String> thresholds) {
        AttributeValue timestamp = new AttributeValue(OffsetDateTime.now().toString());
        return new HashMap<String, AttributeValue>() {{
            put("namespace", new AttributeValue(NAMESPACE));
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
