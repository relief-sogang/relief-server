package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.ShareCode;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
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

    /* Start share, generate and register code, push notification to guardians */
    @Override
    public ShareStartVO startShare(ShareStartCommand shareStartCommand) {
        String code = generateCode();
        ShareCode shareCode = ShareCode.builder()
                        .userId(shareStartCommand.getUserId())
                        .code(code)
                        .lat(0.0)
                        .lng(0.0)
                        .build();
        shareCodeRepository.save(shareCode);
        Optional<User> user = userRepository.findByUserId(shareStartCommand.getUserId());
        if (user.isPresent()) {
            User updateUser = user.get();
            updateUser.setStatus(UserStatus.SHARING);
            userRepository.save(updateUser);
        }

        pushNotificationService.sendShareStartPush(shareStartCommand.getUserId());
        ShareStartVO shareStartVO = ShareStartVO.builder().code(code).build();
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
        shareCodeRepository.findByUserId(shareEndCommand.getUserId()).ifPresent(shareCode -> {
            shareCodeRepository.delete(shareCode);
            shareEndVO.setCode("SUCCESS");
        });
        pushNotificationService.sendShareEndPush(shareEndCommand.getUserId());
        return shareEndVO;
    }

    @Override
    public HelpRequestVO sendHelp(HelpRequestCommand helpRequestCommand) {
        // 수정 필요한 부분 -> 도움요청 메시지 저장. 한 명 이상에게 알림 갔는지 여부 확인
        HelpRequestVO helpRequestVO = HelpRequestVO.builder().code("SUCCESS").build();
        pushNotificationService.sendHelpPush(helpRequestCommand.getUserId());
        return helpRequestVO;
    }
    @Override
    public ShareLocationVO getShareLocation(GetShareLocationCommand getShareLocationCommand) {
        Optional<ShareCode> shareCode = shareCodeRepository.findByCode(getShareLocationCommand.getCode());
        ShareLocationVO shareLocationVO = ShareLocationVO.builder().build();
        if (shareCode.isPresent()) {
            ShareCode share = shareCode.get();
            shareLocationVO.setLat(Double.toString(share.getLat()));
            shareLocationVO.setLng(Double.toString(share.getLng()));
        }
        return shareLocationVO;
    }

    /* mapping on인 상태이고, 해당 유저가 위치를 공유중 일때만 코드 보내기 가능. */
    @Override
    public GetShareCodeVO getShareCode(GetShareCodeCommand getShareCodeCommand) {
        Optional <UserMapping> userMappingOptional = userMappingRepository.findByProtegeIdAndGuardianId(
                getShareCodeCommand.getProtegeId(), getShareCodeCommand.getUserId());
        Optional<User> protegeIdOptional = userRepository.findByUserId(getShareCodeCommand.getProtegeId());
        GetShareCodeVO getShareCodeVO = GetShareCodeVO.builder().code("0").build();
        if (userMappingOptional.isPresent() && protegeIdOptional.isPresent()) {
            UserMapping userMapping = userMappingOptional.get();
            User protege = protegeIdOptional.get();
            if (userMapping.getStatus().equals(UserMappingStatus.ON) && protege.getStatus().equals(UserStatus.SHARING)) {
                getShareCodeVO.setCode(shareCodeRepository.findByUserId(protege.getUserId()).get().getCode());
            }
        }
        return getShareCodeVO;
    }

}
