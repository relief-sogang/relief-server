//package com.sg.relief.interfaces.api.command;
//
//import com.sg.relief.domain.auth.SessionUser;
//import com.sg.relief.domain.service.command.UserCommandService;
//import com.sg.relief.domain.service.command.co.UserDetailCommand;
//import com.sg.relief.domain.service.command.vo.UserDetailVO;
//import com.sg.relief.domain.service.query.vo.SampleVO;
//import com.sg.relief.interfaces.api.command.dto.SignupDetailCommandDTO;
//import com.sg.relief.interfaces.api.query.dto.SampleQueryDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/command/member")
//public class UserCommandController {
//
//    @Autowired
//    private UserCommandService userCommandService;
//
//    private final HttpSession httpSession;
//
//    @PostMapping("/signupdetail")
//    public UserDetailVO signupDetail(@RequestBody SignupDetailCommandDTO signupDetailCommandDTO) {
//        log.info("signupDetail: {}", signupDetailCommandDTO);
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        log.info("SESSION USER : {}", user.getEmail());
//
//        UserDetailVO userDetailVO = userCommandService.register(UserDetailCommand.builder()
//                        .email(user.getEmail())
//                        .userId(signupDetailCommandDTO.getId())
//                        .password(signupDetailCommandDTO.getPassword())
//                        .phoneNumber(signupDetailCommandDTO.getPhoneNumber())
//                .build());
//        return userDetailVO;
//    }
//}
