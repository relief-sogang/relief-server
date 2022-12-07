package com.sg.relief.domain.service.command;

import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.GuardianRequestVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;

public interface UserCommandService {
    UserDetailVO register(UserDetailCommand userDetailCommand);
    GuardianRequestVO guardianRequest(GuardianRequestCommand guardianRequestCommand);
    GuardianRequestVO guardianRename(GuardianRenameCommand guardianRenameCommand);
    GuardianRequestVO guardianChangeStatus(GuardianChangeStatusCommand guardianChangeStatusCommand);
    GuardianRequestVO guardianAccept(GuardianAcceptCommand guardianAcceptCommand);
}
