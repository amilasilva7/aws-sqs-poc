package com.example.sqsproducer.service;

import com.example.sqsproducer.sqsService.AsyncMessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Service
public class AsyncMessageService {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKeyId;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretAccessKey;

    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    public void createMessageByAsync(String request) {
        //Process the request here...
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));
        SqsAsyncClient sqsAsyncClient = SqsAsyncClient.builder().credentialsProvider(credentialsProvider)
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .build();
        AsyncMessageProducer sqsMessageProducer = new AsyncMessageProducer(sqsAsyncClient, new ObjectMapper());
        sqsMessageProducer.send("StanderedQue", request);
        System.out.println("Sent the message");
    }
}
