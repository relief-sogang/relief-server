package com.sg.relief.domain.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sg.relief.domain.model.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {
    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    /* send a message with data and token */
    public void sendMessageWithData (Map <String, String> data, PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(data, request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + response+ " msg "+jsonOutput);
    }

    /* send a message with a topic */
    public void sendMessageByTopic(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageByTopic(request);
        String response = sendAndGetResponse(message);
        logger.info("Sent message without data. Topic: " + request.getTopic() + ", " + response);
    }

    /* send a message with a token */
    public void sendMessageToToken(PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageToToken(request);
        /* for log print */
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        /* ... */
        String response = sendAndGetResponse(message);
        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response + " msg " + jsonOutput);
    }

    /* Send one message and get response */
    private String sendAndGetResponse (Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get(); // wait for message ID String completion
    }
    /* make message with token */
    private Message getPreconfiguredMessageToToken (PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .setToken(request.getToken())
                .build();
    }
    /* make message without data and token
       토큰 없이 topic을 구독하고 있는 모든 client에게 메시지를 전달함.
       client에서 필요에 따라 구독을 요청하고 관리해야 함.-> 현재 사용 X */
    private Message getPreconfiguredMessageByTopic (PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .setTopic(request.getTopic())
                .build();
    }
    /* make message(preconfigured) with data and token
       특정 유저에게 메시지를 데이터와 함께 전달함.
       client에서 data 내용을 해석해서 보여야 함. -> 현재 사용 X, title과 body만 내용 구성해서 보낼 예정. */
    private Message getPreconfiguredMessageWithData (Map <String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .putAllData(data)
                .setToken(request.getToken())
                .build();
    }

    /* configure message builder with pre-configuration and return it.
       set config and set notification body
        */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder()
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)
                .setNotification(new Notification(request.getTitle(), request.getMessage()));
    }

    /* build AndroidConfig with TOPIC and return (set notification collapse key and tag), available duration - 1 day
       use sound and color set from NotificationParameter */
    private AndroidConfig getAndroidConfig (String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofDays(1).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue())
                        .setTag(topic)
                        .build())
                .build();
    }

    /* build ApnsConfig with TOPIC and return (set notification type and identifier) */
    private ApnsConfig getApnsConfig (String topic) {
        return ApnsConfig.builder()
                .setAps(
                        Aps.builder()
                                .setCategory(topic)
                                .setThreadId(topic)
                                .build()
                ).build();
    }
}
