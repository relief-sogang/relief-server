package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.ShareCommandService;
import com.sg.relief.domain.service.command.co.HelpRequestCommand;
import com.sg.relief.domain.service.command.co.ShareEndCommand;
import com.sg.relief.domain.service.command.co.ShareStartCommand;
import com.sg.relief.domain.service.command.vo.HelpRequestVO;
import com.sg.relief.domain.service.command.vo.ShareEndVO;
import com.sg.relief.domain.service.command.vo.ShareStartVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/command/spot")
public class ShareCommandController {
    @Autowired
    private ShareCommandService shareCommandService;

    @PostMapping("/share/start")
    public ShareStartVO startShare(@RequestBody ShareStartCommand shareStartCommand) {
        ShareStartVO shareStartVO = shareCommandService.startShare(shareStartCommand);
        return shareStartVO;
    }

    @PostMapping("/share/end")
    public ShareEndVO endShare(@RequestBody ShareEndCommand shareEndCommand) {
        ShareEndVO shareEndVO = shareCommandService.endShare(shareEndCommand);
        return shareEndVO;
    }

    @PostMapping("/share/help")
    public HelpRequestVO sendHelp(@RequestBody HelpRequestCommand helpRequestCommand) {
        HelpRequestVO helpRequestVO = shareCommandService.sendHelp(helpRequestCommand);
        return helpRequestVO;
    }

}
