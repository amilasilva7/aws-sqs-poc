package com.example.sqsproducer.controllers;

import com.example.sqsproducer.service.AsyncMessageService;
import com.example.sqsproducer.service.SyncMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v1/message")
public class MessageController {

    @Value("spring.cloud.aws.sqs.virtual.endpoint")
    private static String requestQueueUrl;

    @Autowired
    private AsyncMessageService webService;

    @GetMapping()
    public Boolean createMessageByAsync(@RequestParam(name = "request") String request) {
        System.out.println("Start to send async message: " + request);
        webService.createMessageByAsync(request);
        return true;
    }

    @GetMapping("/sync")
    public Boolean createMessageBySync(@RequestParam(name = "request") String request) {
        System.out.println("Request received: " + request);
        SyncMessageService syncMessageService = new SyncMessageService("");
        syncMessageService.sendMessageAndGetResponse(request);
        return true;
    }
}

