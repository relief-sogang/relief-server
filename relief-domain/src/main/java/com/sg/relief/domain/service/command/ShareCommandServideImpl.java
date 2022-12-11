package com.sg.relief.domain.service.command;

import com.sg.relief.domain.persistence.entity.ShareCode;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.repository.ShareCodeRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.PushNotificationService;
import com.sg.relief.domain.service.command.co.HelpRequestCommand;
import com.sg.relief.domain.service.command.co.SaveLocationCommand;
import com.sg.relief.domain.service.command.co.ShareEndCommand;
import com.sg.relief.domain.service.command.co.ShareStartCommand;
import com.sg.relief.domain.service.command.vo.HelpRequestVO;
import com.sg.relief.domain.service.command.vo.ShareEndVO;
import com.sg.relief.domain.service.command.vo.ShareStartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ShareCommandServideImpl implements ShareCommandService{

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private ShareCodeRepository shareCodeRepository;

    @Autowired
    private UserRepository userRepository;

    /* Start share, generate and register code, push notification to guardians */
    @Override
    public ShareStartVO startShare(ShareStartCommand shareStartCommand) {
        String code = generateCode();
        ShareCode shareCode = ShareCode.builder()
                        .userId(shareStartCommand.getUserId())
                        .code(code)
                        .lat(0l)
                        .lng(0l)
                        .build();
        shareCodeRepository.save(shareCode);
        Optional<User> user = userRepository.findByUserId(shareStartCommand.getUserId());
        if (user.isPresent()) {
            User updateUser = user.get();
            // updateUser.setStatus();
        }

        pushNotificationService.sendShareStartPush(shareStartCommand.getUserId());
        ShareStartVO shareStartVO = ShareStartVO.builder().code(code).build();
        return shareStartVO;
    }
    /* Save user location 수정 예정 */
    public String saveLocation(SaveLocationCommand saveLocationCommand) {
       shareCodeRepository.findByCode(saveLocationCommand.getCode()).ifPresent(shareCode -> {

       });

       return null;
    }

    /* 추후 수정 */
    @Override
    public String generateCode() {
        String code = Double.toString( (int)(Math.random() * 1000000) );
        while (shareCodeRepository.findByCode(code).isPresent() == true) {
            code = Double.toString( (int)(Math.random() * 1000000) );
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
}
