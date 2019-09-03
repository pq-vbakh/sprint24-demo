package com.psyquation.infrastructure;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import java.util.Map;

public class DynamoDBPublisher {

    private final AmazonDynamoDB db;
    private final String table;

    public DynamoDBPublisher(String profile) {
        db = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion(Regions.US_WEST_2.getName())
            .withCredentials(new ProfileCredentialsProvider(profile))
            .build();
        table = profile.equals("prod") ? "pq-psyquation2-alert-subscription" : "pq-cthulhu-alert-subscription";
    }

    public void persist(Map<String, AttributeValue> subscription) {
        db.putItem(table, subscription);
    }
}