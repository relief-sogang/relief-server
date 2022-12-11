package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.persistence.entity.Cctv;
import com.sg.relief.domain.service.query.CctvQueryService;
import com.sg.relief.domain.service.query.vo.CctvInfoVO;
import com.sg.relief.domain.service.query.vo.CctvListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class CctvQueryController {
    @Autowired
    CctvQueryService cctvQueryService;

    @PostMapping("/spot/cctv")
    public CctvListVO findCctv(@RequestBody CctvInfoVO cctvInfoVO) {
        // get lat(xAxis), lng(yAxis) and return cctv list
        CctvListVO cctvListVO = cctvQueryService.findCctvIn500(cctvInfoVO.getXAxis(), cctvInfoVO.getYAxis());
        return cctvListVO;
    }
}
