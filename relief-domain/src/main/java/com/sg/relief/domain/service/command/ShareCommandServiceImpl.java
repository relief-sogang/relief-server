package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.ShareCode;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.HelpMessageRepository;
import com.sg.relief.domain.persistence.repository.ShareCodeRepository;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.PushNotificationService;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShareCommandServiceImpl implements ShareCommandService{

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private ShareCodeRepository shareCodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingRepository userMappingRepository;

    @Autowired
    private HelpMessageRepository helpMessageRepository;

    /* Start share, generate and register code, push notification to guardians */
    @Override
    public ShareStartVO startShare(ShareStartCommand shareStartCommand) {
        ShareStartVO shareStartVO = ShareStartVO.builder().code("0").build();
        String code = generateCode();
        ShareCode shareCode = ShareCode.builder()
                        .userId(shareStartCommand.getUserId())
                        .code(code)
                        .lat(0.0)
                        .lng(0.0)
                        .build();
        Optional<User> user = userRepository.findByUserId(shareStartCommand.getUserId());
        if (user.isPresent()) {
            User updateUser = user.get();
            updateUser.setStatus(UserStatus.SHARING);
            userRepository.save(updateUser);
            shareCodeRepository.save(shareCode);
            shareStartVO.setCode(code);
            pushNotificationService.sendShareStartPush(shareStartCommand.getUserId());
        }
        return shareStartVO;
    }

    @Override
    public SaveShareLocationVO saveShareLocation(SaveLocationCommand saveLocationCommand) {
        Optional<ShareCode> shareCode = shareCodeRepository.findByCode(saveLocationCommand.getCode());
        SaveShareLocationVO saveShareLocationVO = SaveShareLocationVO.builder().code("FAIL").build();
        if (shareCode.isPresent()) {
            ShareCode shareCodeUpdate = shareCode.get();
            shareCodeUpdate.setLat(Double.parseDouble(saveLocationCommand.getLat()));
            shareCodeUpdate.setLng(Double.parseDouble(saveLocationCommand.getLng()));
            shareCodeRepository.save(shareCodeUpdate);
            saveShareLocationVO.setCode("SUCCESS");
        }
        return saveShareLocationVO;

    }

    /* 추후 수정 */
    @Override
    public String generateCode() {
        String code = Integer.toString( (int)(Math.random() * 1000000) );
        while (shareCodeRepository.findByCode(code).isPresent()) {
            code = Integer.toString( (int)(Math.random() * 1000000) );
        }
        return code;
    }


    /* End share, delete code, push notification to guardians */
    @Override
    public ShareEndVO endShare(ShareEndCommand shareEndCommand) {
        ShareEndVO shareEndVO = ShareEndVO.builder().code("FAIL").build();
        // sharing -> completed
        Optional<User> userOptional = userRepository.findByUserId(shareEndCommand.getUserId());
        if (userOptional.isPresent()) {
            User userUpdate = userOptional.get();
            userUpdate.setStatus(UserStatus.COMPLETED);
            userRepository.save(userUpdate);
        }
        // delete code
        shareCodeRepository.findByUserId(shareEndCommand.getUserId()).ifPresent(shareCode -> {
            shareCodeRepository.delete(shareCode);
            shareEndVO.setCode("SUCCESS");
        });
        pushNotificationService.sendShareEndPush(shareEndCommand.getUserId());
        return shareEndVO;
    }

    @Override
    public HelpRequestVO sendHelp(HelpRequestCommand helpRequestCommand) {
        // 푸시알림 전송 요청이 성공한 경우에만 메시지가 저장됨.
        HelpRequestVO helpRequestVO = HelpRequestVO.builder().code("FAIL").build();
        // 모든 보호자에게 보호 요청을 보내고, 한 명 이상 푸시알림이 성공하면 true, 아니면 false
        if (pushNotificationService.sendHelpPush(helpRequestCommand.getUserId())) {
            helpRequestVO.setCode("SUCCESS");
        }
        return helpRequestVO;
    }


}
