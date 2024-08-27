package com.example.sqsconsumerone.sqsService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@Component
public class SqsSyncListenServiceListener {

    @SqsListener("SQSSyncQueue")
    public void syncSQSListener(Message message) {
        SqsSyncListenService sqsSyncListenService = new SqsSyncListenService("https://sqs.eu-north-1.amazonaws.com/590184100944/SQSSyncQueue#MyVirtualQueueName");
        sqsSyncListenService.handleLoginRequest(message);
    }
}
