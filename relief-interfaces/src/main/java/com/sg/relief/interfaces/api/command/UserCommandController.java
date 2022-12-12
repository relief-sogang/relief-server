package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.PushNotificationService;
import com.sg.relief.domain.service.command.UserCommandService;
import com.sg.relief.domain.service.command.vo.FCMTokenVO;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.ResponseCodeVO;
import com.sg.relief.domain.service.command.vo.HelpMessageVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import com.sg.relief.interfaces.api.command.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseCodeVO guardianRequest(@RequestBody GuardianRequestCommandDTO guardianRequestCommandDTO) {
        ResponseCodeVO responseCodeVO = userCommandService.guardianRequest(GuardianRequestCommand.builder()
                        .protegeId(guardianRequestCommandDTO.getUserId())
                        .guardianId(guardianRequestCommandDTO.getGuardianId())
                        .guardianName(guardianRequestCommandDTO.getGuardianName())
                        .message(guardianRequestCommandDTO.getMessage())
                .build());

        // PushNotification
        if (responseCodeVO.getCode().equals("SUCCESS")) {
            userCommandService.guardianRequestPush(guardianRequestCommandDTO.getUserId(), guardianRequestCommandDTO.getMessage());
        }
        return responseCodeVO;
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
    @PostMapping("/member/fcmtoken/register")
    public FCMTokenVO receiveFCMToken (@RequestBody FCMTokenCommand fcmTokenCommand) {
        FCMTokenVO fcmTokenVO = userCommandService.receiveFCMToken(fcmTokenCommand);
        return fcmTokenVO;
    }
    @PostMapping("/guardian/rename")
    public ResponseCodeVO guardianRename(@RequestBody GuardianRenameCommandDTO guardianRenameCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.guardianRename(GuardianRenameCommand.builder()
                        .userId(guardianRenameCommandDTO.getUserId())
                        .guardianId(guardianRenameCommandDTO.getGuardianId())
                        .name(guardianRenameCommandDTO.getRename())
                .build());
        return responseCodeVO;
    }

    @PostMapping("/guardian/changestatus")
    public ResponseCodeVO guardianChangeStatus(@RequestBody GuardianChangeStatusCommandDTO guardianChangeStatusCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.guardianChangeStatus(GuardianChangeStatusCommand.builder()
                .userId(guardianChangeStatusCommandDTO.getUserId())
                .guardianId(guardianChangeStatusCommandDTO.getGuardianId())
                .isActive(guardianChangeStatusCommandDTO.getIsActive())
                .build());
        return responseCodeVO;
    }

    @PostMapping("/guardian/accept")
    public ResponseCodeVO guardianAccept(@RequestBody GuardianAcceptCommandDTO guardianAcceptCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.guardianAccept(GuardianAcceptCommand.builder()
                        .userId(guardianAcceptCommandDTO.getUserId())
                        .protegeId(guardianAcceptCommandDTO.getProtegeId())
                        .protegeName(guardianAcceptCommandDTO.getProtegeName())
                        .isAccept(guardianAcceptCommandDTO.getIsAccept())
                .build());
        return responseCodeVO;
    }

    @PostMapping("/protege/rename")
    public ResponseCodeVO renameProtege(@RequestBody ProtegeRenameCommandDTO protegeRenameCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.renameProtege(protegeRenameCommandDTO.getUserId(),
                protegeRenameCommandDTO.getProtegeId(),
                protegeRenameCommandDTO.getRename());
        return responseCodeVO;
    }

    @PostMapping("/mapping/delete")
    public ResponseCodeVO mappingDelete(@RequestBody UserMappingDeleteDTO userMappingDeleteDTO){
        ResponseCodeVO responseCodeVO = userCommandService.mappingDelete(userMappingDeleteDTO.getUserId(),
                userMappingDeleteDTO.getDeleteId(),
                userMappingDeleteDTO.getType());
        return responseCodeVO;
    }

    @PostMapping("/member/updateinfo")
    public ResponseCodeVO memberUpdateInfo(@RequestBody UserInfoUpdateCommandDTO userInfoUpdateCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.memberUpdateInfo(userInfoUpdateCommandDTO.getUserId(),
                userInfoUpdateCommandDTO.getName(),
                userInfoUpdateCommandDTO.getPhoneNumber());
        return responseCodeVO;
    }

    @PostMapping("/pushalarm/status")
    public ResponseCodeVO pushAlarmStatus(@RequestBody UserPushAlarmCommandDTO userPushAlarmCommandDTO){
        ResponseCodeVO responseCodeVO = userCommandService.pushAlarmStatus(userPushAlarmCommandDTO.getUserId(),
                userPushAlarmCommandDTO.getStatus());
        return responseCodeVO;
    }
}
