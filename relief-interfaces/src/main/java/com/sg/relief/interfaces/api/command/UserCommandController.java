package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.UserCommandService;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.GuardianRequestVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import com.sg.relief.interfaces.api.command.dto.*;
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
        return guardianRequestVO;
    }

    @PostMapping("/guardian/rename")
    public GuardianRequestVO guardianRename(@RequestBody GuardianRenameCommandDTO guardianRenameCommandDTO){
        GuardianRequestVO guardianRequestVO = userCommandService.guardianRename(GuardianRenameCommand.builder()
                        .userId(guardianRenameCommandDTO.getUserId())
                        .guardianId(guardianRenameCommandDTO.getGuardianId())
                        .name(guardianRenameCommandDTO.getRename())
                .build());
        return guardianRequestVO;
    }

    @PostMapping("/guardian/changestatus")
    public GuardianRequestVO guardianChangeStatus(@RequestBody GuardianChangeStatusCommandDTO guardianChangeStatusCommandDTO){
        GuardianRequestVO guardianRequestVO = userCommandService.guardianChangeStatus(GuardianChangeStatusCommand.builder()
                .userId(guardianChangeStatusCommandDTO.getUserId())
                .guardianId(guardianChangeStatusCommandDTO.getGuardianId())
                .isActive(guardianChangeStatusCommandDTO.getIsActive())
                .build());
        return guardianRequestVO;
    }

    @PostMapping("/guardian/accept")
    public GuardianRequestVO guardianAccept(@RequestBody GuardianAcceptCommandDTO guardianAcceptCommandDTO){
        GuardianRequestVO guardianRequestVO = userCommandService.guardianAccept(GuardianAcceptCommand.builder()
                        .userId(guardianAcceptCommandDTO.getUserId())
                        .protegeId(guardianAcceptCommandDTO.getProtegeId())
                        .protegeName(guardianAcceptCommandDTO.getProtegeName())
                        .isAccept(guardianAcceptCommandDTO.getIsAccept())
                .build());
        return guardianRequestVO;
    }
}
