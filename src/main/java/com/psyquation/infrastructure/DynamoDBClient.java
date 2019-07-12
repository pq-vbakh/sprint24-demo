package com.psyquation.infrastructure;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.Iterator;

public class DynamoDBClient {

    private static final String TABLE_NAME = "pq-cthulhu-alert-subscription";
    private static final String INDEX_NAME = "UserAndNamespaceToSubscriptionIdIndex";

    public static void main(String[] a) {
        //queryByPrimaryKey("00574d1b-02f4-4286-8e1c-531a29bcec4a");
        queryByIndex(100, "axi:US06-Live:830101");
    }

    private static void queryByPrimaryKey(String uuid) {
        QuerySpec spec = new QuerySpec()
            .withKeyConditionExpression("id = :v_id")
            .withValueMap(new ValueMap()
                .withString(":v_id", uuid));

        query(spec, null);
    }

    private static void queryByIndex(int userId, String namespace) {
        QuerySpec spec = new QuerySpec()
            .withKeyConditionExpression("userId = :v_userId and namespace = :v_namespace")
            .withValueMap(new ValueMap()
                .withInt(":v_userId", userId)
                .withString(":v_namespace", namespace)
            );

        query(spec, INDEX_NAME);
    }

    private static void query(QuerySpec spec, String index) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2).build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        ItemCollection<QueryOutcome> items = index == null ? table.query(spec) : table.getIndex(index).query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            System.out.println(item.toJSONPretty());
        }
    }
}
