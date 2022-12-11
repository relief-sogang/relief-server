package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.model.PushNotificationRequest;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.entity.UserToken;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.persistence.repository.UserTokenRepository;
import com.sg.relief.domain.service.FCMService;
import com.sg.relief.domain.service.PushNotificationService;
import com.sg.relief.domain.service.command.co.FCMTokenCommand;
import com.sg.relief.domain.service.command.co.GuardianRequestCommand;
import com.sg.relief.domain.service.command.co.HelpMessageRegisterCommand;
import com.sg.relief.domain.service.command.co.UserDetailCommand;
import com.sg.relief.domain.service.command.vo.FCMTokenVO;
import com.sg.relief.domain.service.command.vo.GuardianRequestVO;
import com.sg.relief.domain.service.command.vo.HelpMessageVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UserDetailVO register(UserDetailCommand userDetailCommand){
        Optional<User> user = userRepository.findByEmail(userDetailCommand.getEmail());
        UserDetailVO userDetailVO = UserDetailVO.builder().userId(userDetailCommand.getUserId()).build();
        log.info("USER: {}", user);
        if(user.isPresent()){
            User updateUser = user.get();
            updateUser.setUserId(userDetailCommand.getUserId());
            updateUser.setPhoneNumber(userDetailCommand.getPhoneNumber());
            updateUser.setStatus(UserStatus.COMPLETED);
            userRepository.save(updateUser);

            userDetailVO.setUserName(user.get().getName());
        }
        return userDetailVO;
    }

    @Override
    public GuardianRequestVO guardianRequest(GuardianRequestCommand guardianRequestCommand){
        Optional<User> user = userRepository.findByUserId(guardianRequestCommand.getGuardianId());
        GuardianRequestVO guardianRequestVO = GuardianRequestVO.builder().build();
        if(user.isEmpty()){
            guardianRequestVO.setCode("NOT_EXIST");
        } else  if(userMappingRepository.findByProtegeIdAndGuardianName(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianName()).isPresent()){
            guardianRequestVO.setCode("DUPLICATE_NAME");
        } else if(userMappingRepository.findByProtegeIdAndGuardianId(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianId()).isPresent()) {
            guardianRequestVO.setCode("DUPLICATE_GUARDIAN");
        } else {
            UserMapping userMapping = UserMapping.builder()
                    .protegeId(guardianRequestCommand.getProtegeId())
                    .guardianId(guardianRequestCommand.getGuardianId())
                    .guardianName(guardianRequestCommand.getGuardianName())
                    .status(UserMappingStatus.REQUEST)
                    .message(guardianRequestCommand.getMessage())
                    .build();
            userMappingRepository.save(userMapping);
            guardianRequestVO.setCode("SUCCESS");
            pushNotificationService.sendGuardianRequestPush(user.get().getId(), guardianRequestCommand.getMessage());
        }

        return guardianRequestVO;

    }

    @Override
    public HelpMessageVO registerHelpMessage(HelpMessageRegisterCommand helpMessageRegisterCommand) {
        HelpMessageVO helpMessageVO = HelpMessageVO.builder().build();
        helpMessageVO.setCode("FAIL");
        userRepository.findByUserId(helpMessageRegisterCommand.getUserId()).ifPresent(user -> {
            user.setHelpMessage(helpMessageRegisterCommand.getMessage());
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
}
