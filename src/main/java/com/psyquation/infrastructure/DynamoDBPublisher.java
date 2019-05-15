package com.psyquation.infrastructure;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public class DynamoDBPublisher {

    private static final AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
    private static final String TABLE_NAME = "pq-cthulhu-user-alert-config";

    public static void persist(Map<String, AttributeValue> config) {
        db.putItem(TABLE_NAME, config);
    }
}