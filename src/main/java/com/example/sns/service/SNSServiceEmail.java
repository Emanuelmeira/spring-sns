package com.example.sns.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SNSServiceEmail {

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    @Value("${topic}")
    private String TOPIC_ARN;

    public static final String EVENT_TYPE = "EventType";
    public static final String OPTION = "TypeProtocol";

    public PublishResult publish(String msg) {

        var request = publishTopicWithAttributes(msg);
        //var request = publishTopicWithSimpleMessage(msg);

        try {
            return amazonSNSClient.publish(request);
        } catch (AmazonSNSException e) {
            throw new RuntimeException(e);
        }

    }

    private PublishRequest publishTopicWithAttributes(String msg) {
        Map<String, MessageAttributeValue> attributes = new HashMap<>();
        attributes.put(EVENT_TYPE, buildAttribute("CREATED", "String"));
        attributes.put(OPTION, buildAttribute("EMAIL", "String"));

        PublishRequest publishRequest =
                new PublishRequest(TOPIC_ARN, msg, "Title - Message with att");

        publishRequest.setMessageAttributes(attributes);
        return publishRequest;
    }

    private PublishRequest publishTopicWithSimpleMessage(String msg) {
        PublishRequest publishRequest =
                new PublishRequest(TOPIC_ARN, msg, "Title - Message no att");

        return publishRequest;
    }

    private MessageAttributeValue buildAttribute(String value, String dataType) {

        var attribute = new MessageAttributeValue();
        attribute.setDataType(dataType);
        attribute.setStringValue(value);
        return attribute;
    }


}
