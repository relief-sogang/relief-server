package com.sg.relief.interfaces.api.query;

import com.sg.relief.domain.service.query.HelpQueryService;
import com.sg.relief.domain.service.query.vo.HelpMessageInfoVO;
import com.sg.relief.domain.service.query.vo.HelpReceiveCountVO;
import com.sg.relief.domain.service.query.vo.HelpReceiveListVO;
import com.sg.relief.domain.service.query.vo.HelpSendListVO;
import com.sg.relief.interfaces.api.query.dto.HelpMessageInfoQueryDTO;
import com.sg.relief.interfaces.api.query.dto.HelpReceiveCountQueryDTO;
import com.sg.relief.interfaces.api.query.dto.HelpReceiveListQueryDTO;
import com.sg.relief.interfaces.api.query.dto.HelpSendListQueryDTO;
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
@RequestMapping("/api/query/help")
public class HelpQueryController {
    @Autowired
    private HelpQueryService helpQueryService;

    @PostMapping("/send/list")
    public HelpSendListVO getHelpSendList (@RequestBody HelpSendListQueryDTO helpSendListQueryDTO) {
        return helpQueryService.getHelpSendList(helpSendListQueryDTO.getSenderId());
    }
    @PostMapping("/receive/list")
    public HelpReceiveListVO getHelpReceiveList (@RequestBody HelpReceiveListQueryDTO helpReceiveListQueryDTO) {
        return helpQueryService.getHelpReceiveList(helpReceiveListQueryDTO.getReceiverId());
    }
    @PostMapping("/receive/count")
    public HelpReceiveCountVO getHelpReceiveCount(@RequestBody HelpReceiveCountQueryDTO helpReceiveCountQueryDTO) {
        return helpQueryService.getHelpReceiveCount(helpReceiveCountQueryDTO.getReceiverId());
    }
    @PostMapping("/message")
    public HelpMessageInfoVO getHelpMessage (@RequestBody HelpMessageInfoQueryDTO helpMessageInfoQueryDTO) {
        return helpQueryService.getHelpMessage(helpMessageInfoQueryDTO.getUserId());
    }
}
