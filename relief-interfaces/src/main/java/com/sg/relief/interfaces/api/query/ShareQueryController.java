package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.vo.ShareCodeVO;
import com.sg.relief.domain.service.query.vo.ShareLocationVO;
import com.sg.relief.domain.service.query.ShareQueryService;
import com.sg.relief.interfaces.api.query.dto.ShareCodeQueryDTO;
import com.sg.relief.interfaces.api.query.dto.ShareLocationQueryDTO;
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
@RequestMapping("/api/query/spot")
public class ShareQueryController {
    @Autowired
    private ShareQueryService shareQueryService;

    @PostMapping("/share/code")
    public ShareCodeVO getShareCode(@RequestBody ShareCodeQueryDTO shareCodeQueryDTO) {
        return shareQueryService.getShareCode(shareCodeQueryDTO.getUserId(), shareCodeQueryDTO.getProtegeId());
    }

    @PostMapping("/share/get")
    public ShareLocationVO getShareLocation(@RequestBody ShareLocationQueryDTO shareLocationQueryDTO) {
        return shareQueryService.getShareLocation(shareLocationQueryDTO.getCode());
    }
}
