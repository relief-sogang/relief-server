package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.ShareCommandService;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.*;
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

    @PostMapping("/share")
    public SaveShareLocationVO saveShareLocation(@RequestBody SaveLocationCommand saveLocationCommand) {
        return shareCommandService.saveShareLocation(saveLocationCommand);
    }


}
