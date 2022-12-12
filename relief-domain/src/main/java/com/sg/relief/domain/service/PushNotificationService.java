package com.sg.relief.domain.service;

import com.sg.relief.domain.code.HelpMessageStatus;
import com.sg.relief.domain.model.PushNotificationRequest;
import com.sg.relief.domain.persistence.entity.HelpMessage;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.entity.UserToken;
import com.sg.relief.domain.persistence.repository.HelpMessageRepository;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.persistence.repository.UserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PushNotificationService {
    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;
    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @Autowired
    private UserMappingRepository userMappingRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HelpMessageRepository helpMessageRepository;

    /* Send GuardianRequestPush to the user */
    public boolean sendGuardianRequestPush(Long id, String requestMessage) {
        String title = "보호자 등록 요청";
        String topic = "request";
        Optional <UserToken> userTokenOptional = userTokenRepository.findByUserId(id);
        if (userTokenOptional.isPresent()) {
            UserToken userToken = userTokenOptional.get();
            PushNotificationRequest request = PushNotificationRequest.builder()
                    .title(title).topic(topic).token(userToken.getFcmToken()).message(requestMessage).build();
            return sendPushNotificationToToken(request);
        }
        return false;
    }
    /* Send ShareStartPush to the guardians of the user */
    public void sendShareStartPush(String userId) {
        List<UserMapping> userMappingList = userMappingRepository.findAllByProtegeId(userId);
        String title = "위치 공유 시작";
        String topic = "share-start";
        String message = "이(가) 위치 공유를 시작합니다.";
        PushNotificationRequest request = PushNotificationRequest.builder().title(title).topic(topic).message(message).build();
        sendShareMessageToGuardianList(userMappingList, request);
    }
    /* Send ShareStartPush to the guardians of the user */
    public void sendShareEndPush(String userId) {
        List<UserMapping> userMappingList = userMappingRepository.findAllByProtegeId(userId);
        String title = "위치 공유 종료";
        String topic = "share-end";
        String message = "의 위치 공유가 종료되었습니다.";
        PushNotificationRequest request = PushNotificationRequest.builder().title(title).topic(topic).message(message).build();
        sendShareMessageToGuardianList(userMappingList, request);
    }
    /* Send help message to the guardians of the user */
    public boolean sendHelpPush(String userId) {
        List<UserMapping> userMappingList = userMappingRepository.findAllByProtegeId(userId);
        String title = "긴급 도움 요청";
        String topic = "share-help";
        PushNotificationRequest request = PushNotificationRequest.builder().title(title).topic(topic).build();
        return sendHelpMessageToGuardianList(userMappingList, request);
    }
    public void sendShareMessageToGuardianList (List <UserMapping> userMappingList, PushNotificationRequest request) {
        // send push to all guardians
        Iterator <UserMapping> it = userMappingList.iterator();
        while (it.hasNext()) {
            UserMapping userMapping = it.next();
            String token = userTokenRepository.findByUserId(userRepository.findByUserId(userMapping.getGuardianId()).get().getId()).get().getFcmToken();
            request.setMessage(userMapping.getProtegeName() + request.getMessage());
            request.setToken(token);
            sendPushNotificationToToken(request);
        }
    }

    public boolean sendHelpMessageToGuardianList (List <UserMapping> userMappingList, PushNotificationRequest request) {
        // send push to all guardians
        Iterator <UserMapping> it = userMappingList.iterator();
        // 한 명 이상에게 가면 true 반환함. push 알림 요청이 간 경우만 메시지를 저장함.
        boolean b = false;
        while (it.hasNext()) {
            UserMapping userMapping = it.next();
            String message = "위치를 보고 도와주세요! 긴급한 상황입니다!";
            if (userRepository.findByUserId(userMapping.getProtegeId()).get().getHelpMessage() != null)
                message = userRepository.findByUserId(userMapping.getProtegeId()).get().getHelpMessage();
            String token = userTokenRepository.findByUserId(userRepository.findByUserId(userMapping.getGuardianId()).get().getId()).get().getFcmToken();
            request.setMessage(userMapping.getProtegeName()+ ": " + message);
            request.setToken(token);
            if (sendPushNotificationToToken(request)) {
                saveHelpMessage(userMapping.getProtegeId(), userMapping.getGuardianId(), message);
                b = true;
            }
        }
        return b;
    }

    void saveHelpMessage (String senderId, String receiverId, String message) {
        LocalDateTime myDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = dateTimeFormatter.format(myDateTime);

        HelpMessage helpMessage = HelpMessage.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .dateTime(formattedDate)
                .checkStatus(HelpMessageStatus.N)
                .build();
        helpMessageRepository.save(helpMessage);
    }

    /* Send pushes to multiple tokens */
    // public void sendPushNotificationToAll ()

    /* Send a push to a specific token */
    public boolean sendPushNotificationToToken (PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    /* Send a push based on the topic - unused now */
    public void sendPushNotificationByTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageByTopic(request);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    /* Send a push with data - unused for now */
    public void sendPushNotificationWithData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithData(getSamplePayloadData(), request);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    /* make sample data used for PushNotification */
    private Map<String, String> getSamplePayloadData() {
        Map <String, String> data = new HashMap<>();
        data.put("messageId", "msgid");
        data.put("text", "text");
        return data;
    }
}
