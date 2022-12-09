package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.SpotQueryService;
import com.sg.relief.domain.service.query.vo.SpotListVO;
import com.sg.relief.interfaces.api.query.dto.UserQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query/spot")
public class SpotQueryController {

    @Autowired
    private SpotQueryService spotQueryService;

    @GetMapping("/list")
    public SpotListVO getSpotList(@RequestBody UserQueryDTO userQueryDTO){
        SpotListVO spotListVO = spotQueryService.getUserSpot(userQueryDTO.getUserId());
        return spotListVO;
    }
}
