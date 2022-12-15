package com.sg.relief.domain.service;

import com.sg.relief.domain.model.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class FCMPushTests {
    @Autowired
    private FCMService fcmService;
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    @Test
    public void FCMTestTopic() {
        PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
        pushNotificationRequest.setTopic("test");
        pushNotificationRequest.setTitle("test");
        pushNotificationRequest.setMessage("test");
        try {
            fcmService.sendMessageByTopic(pushNotificationRequest);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
