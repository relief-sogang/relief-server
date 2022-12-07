package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.UserQueryService;
import com.sg.relief.domain.service.query.vo.GuardianInfoVO;
import com.sg.relief.domain.service.query.vo.GuardianListVO;
import com.sg.relief.domain.service.query.vo.SampleVO;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;
import com.sg.relief.interfaces.api.query.dto.GuardianDetailQueryDTO;
import com.sg.relief.interfaces.api.query.dto.GuardianListQueryDTO;
import com.sg.relief.interfaces.api.query.dto.SampleQueryDTO;
import com.sg.relief.interfaces.api.query.dto.UserCheckIdQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping("/member/checkid")
    public UserIdCheckVO checkId(@RequestBody UserCheckIdQueryDTO userCheckIdQueryDTO) {
        UserIdCheckVO userIdCheckVO = userQueryService.findId(userCheckIdQueryDTO.getId());
        return userIdCheckVO;
    }

    @GetMapping("/guardian/list")
    public GuardianListVO guardianList(@RequestBody GuardianListQueryDTO guardianListQueryDTO) {
        GuardianListVO guardianListVO = userQueryService.getGuardianList(guardianListQueryDTO.getUserId());
        return guardianListVO;
    }

    @GetMapping("/guardian/detail")
    public GuardianInfoVO guardianDetail(@RequestBody GuardianDetailQueryDTO guardianDetailQueryDTO) {
        GuardianInfoVO guardianInfoVO = userQueryService.getGuardianDetail(guardianDetailQueryDTO.getGuardianId());
        return guardianInfoVO;
    }
}
