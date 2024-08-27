package com.example.sqsproducer.service;

import com.amazonaws.services.sqs.AmazonSQSRequester;
import com.amazonaws.services.sqs.AmazonSQSRequesterClientBuilder;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SyncMessageService {

    // Specify the Amazon SQS queue to which to send requests.
    private final String requestQueueUrl;

    // Use the AmazonSQSRequester interface to create
    // a temporary queue for each response.
    private final AmazonSQSRequester sqsRequester =
            AmazonSQSRequesterClientBuilder.standard().build();

    public SyncMessageService(String requestQueueUrl) {
        this.requestQueueUrl = requestQueueUrl;
    }

    // Send a login request.
    public String sendMessageAndGetResponse(String body) {

        SendMessageRequest request = SendMessageRequest.builder()
                .messageBody(body)
                .queueUrl(requestQueueUrl)
                .build();

        // If no response is received, in 5 seconds,
        // trigger the TimeoutException.
        Message reply = null;
        try {
            reply = sqsRequester.sendMessageAndGetResponse(request,
                    10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            sqsRequester.shutdown();
            throw new RuntimeException(e);
        }
        System.out.println(reply.body());
        return reply.body();
    }
}
