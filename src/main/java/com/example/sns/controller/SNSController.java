package com.example.sns.controller;

import com.example.sns.service.SNSServiceEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SNSController {

    @Autowired
    private SNSServiceEmail snsServiceEmail;

    @GetMapping("/publish/{msg}")
    public ResponseEntity<Object> publishToTopic(@PathVariable("msg") String msg) {

        var result = snsServiceEmail.publish(msg);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
