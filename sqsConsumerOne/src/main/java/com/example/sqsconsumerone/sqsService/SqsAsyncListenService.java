package com.example.sqsconsumerone.sqsService;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class SqsAsyncListenService {
    @SqsListener("SqsAsyncQueue")
    public void listenTwoQueues(String message) {
        System.out.println(message.replace("\"", ""));
    }
}
