package com.sg.relief.domain.service.command;

import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.*;

public interface ShareCommandService {
    ShareStartVO startShare(ShareStartCommand shareStartCommand);
    String generateCode();
    ShareEndVO endShare(ShareEndCommand shareEndCommand);
    HelpRequestVO sendHelp(HelpRequestCommand helpRequestCommand);
    SaveShareLocationVO saveShareLocation(SaveLocationCommand saveLocationCommand);

}
