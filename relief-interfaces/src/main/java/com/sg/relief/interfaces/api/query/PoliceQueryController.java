package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.PoliceQueryService;
import com.sg.relief.domain.service.query.vo.PoliceListVO;
import com.sg.relief.interfaces.api.query.dto.PoliceListQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class PoliceQueryController {
    @Autowired
    private PoliceQueryService policeQueryService;

    @PostMapping("/spot/police")
    public PoliceListVO findPolice (@RequestBody PoliceListQueryDTO policeListQueryDTO) {
        return policeQueryService.findPoliceIn500(
                Double.parseDouble(policeListQueryDTO.getLat()), Double.parseDouble(policeListQueryDTO.getLng())
        );
    }
}
