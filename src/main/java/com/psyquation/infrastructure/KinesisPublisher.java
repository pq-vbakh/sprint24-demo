package com.psyquation.infrastructure;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.psyquation.core.model.AlertRelatedData;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import static com.amazonaws.regions.Regions.US_WEST_2;
import static java.util.Arrays.asList;

public class KinesisPublisher {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final KinesisProducer producer;
    private final String streamName;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public KinesisPublisher(String profile) {
        producer = new KinesisProducer(
            new KinesisProducerConfiguration()
                .setRegion(US_WEST_2.getName())
                .setKinesisEndpoint("kinesis.us-west-2.amazonaws.com")
                .setKinesisPort(443)
                .setVerifyCertificate(false)
                .setCredentialsProvider(new ProfileCredentialsProvider(profile))
                .setMaxConnections(1)
                .setRequestTimeout(60_000)
                .setRecordMaxBufferedTime(15_000));
        streamName = profile.equals("prod") ? "pq-psyquation2-alert-input" : "pq-cthulhu-alert-input";;
    }

    public void publish(AlertRelatedData obj) throws JsonProcessingException, ExecutionException, InterruptedException {
        String data = objectMapper.writeValueAsString(asList(obj));
        System.out.println("Writing to Kinesis: " + data);
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8));
        UserRecordResult response = producer.addUserRecord(streamName, obj.getServerName(), buffer).get();
        System.out.println("Success: " + response.isSuccessful());
    }
}
