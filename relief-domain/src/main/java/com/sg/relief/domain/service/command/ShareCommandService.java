package com.sg.relief.domain.service.command;

import com.sg.relief.domain.service.command.co.HelpRequestCommand;
import com.sg.relief.domain.service.command.co.ShareEndCommand;
import com.sg.relief.domain.service.command.co.ShareStartCommand;
import com.sg.relief.domain.service.command.vo.HelpRequestVO;
import com.sg.relief.domain.service.command.vo.ShareEndVO;
import com.sg.relief.domain.service.command.vo.ShareStartVO;

public interface ShareCommandService {
    ShareStartVO startShare(ShareStartCommand shareStartCommand);
    String generateCode();
    ShareEndVO endShare(ShareEndCommand shareEndCommand);
    HelpRequestVO sendHelp(HelpRequestCommand helpRequestCommand);
}
