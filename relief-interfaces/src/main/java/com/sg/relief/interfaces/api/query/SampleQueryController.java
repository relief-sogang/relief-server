package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.auth.SessionUser;
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

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class SampleQueryController {
    // CQRS - Query

    @Autowired
    private SampleQueryService sampleQueryService;

//    private final HttpSession httpSession;

    @PostMapping("/sample")
    public SampleVO sampleQuery(@RequestBody SampleQueryDTO sampleQueryDTO) {
        log.info("SAMPLE: {}", sampleQueryDTO);
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        log.info("SESSION USER : {}", user.getEmail());
        SampleVO sampleVO = sampleQueryService.getSample(sampleQueryDTO.getId());
        return sampleVO;
    }

}
