package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.model.PushNotificationRequest;
import com.sg.relief.domain.persistence.entity.*;
import com.sg.relief.domain.persistence.repository.*;
import com.sg.relief.domain.service.FCMService;
import com.sg.relief.domain.service.PushNotificationService;
import com.sg.relief.domain.service.command.vo.FCMTokenVO;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.ResponseCodeVO;
import com.sg.relief.domain.service.command.vo.HelpMessageVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingRepository userMappingRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private HelpMessageRepository helpMessageRepository;

    @Autowired
    private ShareCodeRepository shareCodeRepository;

    @Autowired
    private UserSpotRepository userSpotRepository;


    @Override
    public UserDetailVO register(UserDetailCommand userDetailCommand){
        Optional<User> user = userRepository.findByEmail(userDetailCommand.getEmail());
        UserDetailVO userDetailVO = UserDetailVO.builder().userId(userDetailCommand.getUserId()).build();
        log.info("USER: {}", user);
        if(user.isPresent()){
            User updateUser = user.get();
            updateUser.setUserId(userDetailCommand.getUserId());
            updateUser.setName(userDetailCommand.getUserName());
            updateUser.setPhoneNumber(userDetailCommand.getPhoneNumber());
            updateUser.setStatus(UserStatus.COMPLETED);
            userRepository.save(updateUser);

            userDetailVO.setUserName(user.get().getName());
        }
        return userDetailVO;
    }

    @Override
    public ResponseCodeVO guardianRequest(GuardianRequestCommand guardianRequestCommand){
        Optional<User> user = userRepository.findByUserId(guardianRequestCommand.getGuardianId());
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(user.isEmpty()){
            responseCodeVO.setCode("NOT_EXIST");
        } else  if(userMappingRepository.findByProtegeIdAndGuardianName(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianName()).isPresent()){
            responseCodeVO.setCode("DUPLICATE_NAME");
        } else if(userMappingRepository.findByProtegeIdAndGuardianId(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianId()).isPresent()) {
            responseCodeVO.setCode("DUPLICATE_GUARDIAN");
        } else {
            UserMapping userMapping = UserMapping.builder()
                    .protegeId(guardianRequestCommand.getProtegeId())
                    .guardianId(guardianRequestCommand.getGuardianId())
                    .guardianName(guardianRequestCommand.getGuardianName())
                    .status(UserMappingStatus.REQUEST)
                    .message(guardianRequestCommand.getMessage())
                    .build();
            userMappingRepository.save(userMapping);
            responseCodeVO.setCode("SUCCESS");
        }

        return responseCodeVO;

    }
    @Override
    public boolean guardianRequestPush (String userId, String message) {
        Long id = userRepository.findByUserId(userId).get().getId();
        return pushNotificationService.sendGuardianRequestPush(id, message);
    }

    @Override
    public HelpMessageVO registerHelpMessage(HelpMessageRegisterCommand helpMessageRegisterCommand) {
        HelpMessageVO helpMessageVO = HelpMessageVO.builder().build();
        helpMessageVO.setCode("FAIL");
        userRepository.findByUserId(helpMessageRegisterCommand.getUserId()).ifPresent(user -> {
            user.setHelpMessage(helpMessageRegisterCommand.getMessage());
            userRepository.save(user);
            helpMessageVO.setCode("SUCCESS");
        });
        return helpMessageVO;
    }

    /* recieve Token and save it. */
    @Override
    public FCMTokenVO receiveFCMToken (FCMTokenCommand fcmTokenCommand){
        Optional<User> user = userRepository.findByUserId(fcmTokenCommand.getUserId());
        FCMTokenVO fcmTokenVO = FCMTokenVO.builder().code("FAIL").build();
        if (user.isPresent()) {
            Optional<UserToken> userToken = userTokenRepository.findByUserId(user.get().getId());
            if (userToken.isPresent()) {
                UserToken updateUserToken = userToken.get();
                updateUserToken.setFcmToken(fcmTokenCommand.getToken());
                userTokenRepository.save(updateUserToken);
                fcmTokenVO.setCode("SUCCESS");
            }
        }
        return fcmTokenVO;
    }
    
    public ResponseCodeVO guardianRename(GuardianRenameCommand guardianRenameCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianRenameCommand.getUserId(), guardianRenameCommand.getGuardianId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        List<UserMapping> userMappings = userMappingRepository.findAllByProtegeId(guardianRenameCommand.getUserId()).stream()
                        .filter(x->x.getGuardianName().equals(guardianRenameCommand.getName()))
                                .collect(Collectors.toList());
        if(userMappings.size() > 0){
            responseCodeVO.setCode("DUPLICATE_NAME");
        } else {
            userMapping.setGuardianName(guardianRenameCommand.getName());
            userMappingRepository.save(userMapping);
            responseCodeVO.setCode("SUCCESS");
        }
        return responseCodeVO;

    }

    @Override
    public ResponseCodeVO guardianChangeStatus(GuardianChangeStatusCommand guardianChangeStatusCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianChangeStatusCommand.getUserId(), guardianChangeStatusCommand.getGuardianId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(guardianChangeStatusCommand.getIsActive()){
            userMapping.setStatus(UserMappingStatus.ON);
            responseCodeVO.setCode("ON");
        } else {
            userMapping.setStatus(UserMappingStatus.OFF);
            responseCodeVO.setCode("OFF");
        }
        userMappingRepository.save(userMapping);
        return responseCodeVO;
    }

    @Override
    public ResponseCodeVO guardianAccept(GuardianAcceptCommand guardianAcceptCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianAcceptCommand.getProtegeId(), guardianAcceptCommand.getUserId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(guardianAcceptCommand.getIsAccept()){
            userMapping.setStatus(UserMappingStatus.ON);
            userMapping.setProtegeName(guardianAcceptCommand.getProtegeName());
            responseCodeVO.setCode("ACCEPT");
        } else {
            userMapping.setStatus(UserMappingStatus.REJECT);
            responseCodeVO.setCode("REJECT");
        }
        userMappingRepository.save(userMapping);
        return responseCodeVO;
    }

    @Override
    public ResponseCodeVO renameProtege(String userId, String protegeId, String rename){
        Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(protegeId, userId);
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().code("FAIL").build();
        if(userMapping.isPresent()){
            UserMapping updateMapping = userMapping.get();
            updateMapping.setProtegeName(rename);
            userMappingRepository.save(updateMapping);
            responseCodeVO.setCode("SUCCESS");
        }
        return responseCodeVO;
    }

    @Override
    public ResponseCodeVO mappingDelete(String userId, String deleteId, String type){
        if(type.equals("GUARDIAN")){
            Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(userId, deleteId);
            userMappingRepository.delete(userMapping.get());
        } else {
            Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(deleteId, userId);
            UserMapping updateMapping = userMapping.get();
            updateMapping.setStatus(UserMappingStatus.REQUEST);
            userMappingRepository.save(userMapping.get());
        }

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO memberUpdateInfo(String userId, String name, String phoneNumber){
        Optional<User> user = userRepository.findByUserId(userId);
        User userUpdate = user.get();
        userUpdate.setName(name);
        userUpdate.setPhoneNumber(phoneNumber);
        userRepository.save(userUpdate);

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO pushAlarmStatus(String userId, String status){
        Optional<User> user = userRepository.findByUserId(userId);
        User userUpdate = user.get();
        if(status.equals("ON")){
            userUpdate.setActiveAlarm(true);
        } else {
            userUpdate.setActiveAlarm(false);
        }
        userRepository.save(userUpdate);

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO deleteUser(String userId){
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent()){
            // 토큰 삭제
            Long userIdToken = user.get().getId();
            userTokenRepository.delete(userTokenRepository.findByUserId(userIdToken).get());

            // 회원 정보 삭제
            userRepository.delete(user.get());
        }

        //mapping 정보 삭제
        List<UserMapping> userMappingProtegeList = userMappingRepository.findAllByProtegeId(userId);
        userMappingRepository.deleteAll(userMappingProtegeList);
        List<UserMapping> userMappingsGuardianList = userMappingRepository.findALlByGuardianId(userId);
        userMappingRepository.deleteAll(userMappingsGuardianList);

        // 도움 메시지 삭제
        List<HelpMessage> helpMessageSender = helpMessageRepository.findAllBySenderId(userId);
        helpMessageRepository.deleteAll(helpMessageSender);
        List<HelpMessage> helpMessageReceiver = helpMessageRepository.findAllByReceiverId(userId);
        helpMessageRepository.deleteAll(helpMessageReceiver);

        // share code 삭제
        Optional<ShareCode> shareCodes = shareCodeRepository.findByUserId(userId);
        if(shareCodes.isPresent()){
            shareCodeRepository.delete(shareCodes.get());
        }

        // 스크랩 장소 삭제
        List<UserSpot> userSpots = userSpotRepository.findAllByUserId(userId);
        userSpotRepository.deleteAll(userSpots);

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();

    }

}
