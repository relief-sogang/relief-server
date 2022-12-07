package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.UserQueryService;
import com.sg.relief.domain.service.query.vo.GuardianInfoVO;
import com.sg.relief.domain.service.query.vo.GuardianListVO;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;
import com.sg.relief.interfaces.api.query.dto.GuardianDetailQueryDTO;
import com.sg.relief.interfaces.api.query.dto.GuardianListQueryDTO;
import com.sg.relief.interfaces.api.query.dto.UserQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @PostMapping("/member/checkid")
    public UserIdCheckVO checkId(@RequestBody UserQueryDTO userQueryDTO) {
        UserIdCheckVO userIdCheckVO = userQueryService.findId(userQueryDTO.getUserId());
        return userIdCheckVO;
    }

    @GetMapping("/guardian/list")
    public GuardianListVO guardianList(@RequestBody UserQueryDTO userQueryDTO) {
        GuardianListVO guardianListVO = userQueryService.getGuardianList(userQueryDTO.getUserId());
        return guardianListVO;
    }

    @GetMapping("/guardian/detail")
    public GuardianInfoVO guardianDetail(@RequestBody GuardianDetailQueryDTO guardianDetailQueryDTO) {
        GuardianInfoVO guardianInfoVO = userQueryService.getGuardianDetail(guardianDetailQueryDTO.getGuardianId());
        return guardianInfoVO;
    }
}
