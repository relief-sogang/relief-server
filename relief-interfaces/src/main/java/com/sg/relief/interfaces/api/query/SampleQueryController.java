package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.SampleQueryService;
import com.sg.relief.domain.service.query.vo.SampleVO;
import com.sg.relief.interfaces.api.query.dto.SampleQueryDTO;
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
public class SampleQueryController {
    // CQRS - Query

    @Autowired
    private SampleQueryService sampleQueryService;

    @PostMapping("/sample")
    public SampleVO sampleQuery(@RequestBody SampleQueryDTO sampleQueryDTO) {
        SampleVO sampleVO = sampleQueryService.getSample();
        return sampleVO;
    }

}
