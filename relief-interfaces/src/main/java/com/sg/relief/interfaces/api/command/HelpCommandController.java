package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.HelpCommandService;
import com.sg.relief.domain.service.command.vo.HelpMessageReadVO;
import com.sg.relief.interfaces.api.command.dto.HelpReadCommandDTO;
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
@RequestMapping("/api/command/help")
public class HelpCommandController {
    @Autowired
    private HelpCommandService helpCommandService;
    @PostMapping("/receive/check")
    public HelpMessageReadVO readHelpMessage(@RequestBody HelpReadCommandDTO helpReadCommandDTO) {
        return helpCommandService
                .readHelpMessage(helpReadCommandDTO.getReceiverId(), helpReadCommandDTO.getMessageId());
    }
}
