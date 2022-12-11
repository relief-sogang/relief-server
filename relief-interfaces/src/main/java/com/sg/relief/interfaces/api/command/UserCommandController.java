package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.UserCommandService;
import com.sg.relief.domain.service.command.co.GuardianRequestCommand;
import com.sg.relief.domain.service.command.co.HelpMessageRegisterCommand;
import com.sg.relief.domain.service.command.co.UserDetailCommand;
import com.sg.relief.domain.service.command.vo.GuardianRequestVO;
import com.sg.relief.domain.service.command.vo.HelpMessageVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import com.sg.relief.interfaces.api.command.dto.GuardianRequestCommandDTO;
import com.sg.relief.interfaces.api.command.dto.RegisterHelpMessageCommandDTO;
import com.sg.relief.interfaces.api.command.dto.SignupDetailCommandDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/command")
public class UserCommandController {

    @Autowired
    private UserCommandService userCommandService;


    @PostMapping("/member/signupdetail")
    public UserDetailVO signupDetail(@RequestBody SignupDetailCommandDTO signupDetailCommandDTO) {

        UserDetailVO userDetailVO = userCommandService.register(UserDetailCommand.builder()
                        .email(signupDetailCommandDTO.getEmail())
                        .userId(signupDetailCommandDTO.getUserId())
                        .userName(signupDetailCommandDTO.getUserName())
                        .password(signupDetailCommandDTO.getPassword())
                        .phoneNumber(signupDetailCommandDTO.getPhoneNumber())
                .build());
        return userDetailVO;
    }

    @PostMapping("/guardian/request")
    public GuardianRequestVO guardianRequest(@RequestBody GuardianRequestCommandDTO guardianRequestCommandDTO) {
        GuardianRequestVO guardianRequestVO = userCommandService.guardianRequest(GuardianRequestCommand.builder()
                        .protegeId(guardianRequestCommandDTO.getUserId())
                        .guardianId(guardianRequestCommandDTO.getGuardianId())
                        .guardianName(guardianRequestCommandDTO.getGuardianName())
                        .message(guardianRequestCommandDTO.getMessage())
                .build());
        // PushNotification
        if (guardianRequestVO.getCode().equals("SUCCESS")) {

        }
        return guardianRequestVO;
    }
    @PostMapping("/message")
    public HelpMessageVO registerHelpMessage(@RequestBody RegisterHelpMessageCommandDTO registerHelpMessageCommandDTO) {
        HelpMessageRegisterCommand  helpMessageRegisterCommand = HelpMessageRegisterCommand.builder()
                .userId(registerHelpMessageCommandDTO.getUserId())
                .message(registerHelpMessageCommandDTO.getMessage())
                .build();
        HelpMessageVO helpMessageVO = userCommandService.registerHelpMessage(helpMessageRegisterCommand);
        return helpMessageVO;
    }
}
