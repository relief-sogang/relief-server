package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.UserQueryService;
import com.sg.relief.domain.service.query.vo.SampleVO;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;
import com.sg.relief.interfaces.api.query.dto.SampleQueryDTO;
import com.sg.relief.interfaces.api.query.dto.UserCheckIdQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query/member")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping("/checkid")
    public UserIdCheckVO sampleQuery(@RequestBody UserCheckIdQueryDTO userCheckIdQueryDTO) {
        log.info("checkid: {}", userCheckIdQueryDTO);
        UserIdCheckVO userIdCheckVO = userQueryService.findId(userCheckIdQueryDTO.getId());
        return userIdCheckVO;
    }
}
