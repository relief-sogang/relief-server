package com.sg.relief.domain.service.command;


import com.sg.relief.domain.service.command.vo.FCMTokenVO;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.ResponseCodeVO;
import com.sg.relief.domain.service.command.vo.HelpMessageVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;

public interface UserCommandService {
    UserDetailVO register(UserDetailCommand userDetailCommand);
    ResponseCodeVO guardianRequest(GuardianRequestCommand guardianRequestCommand);
    ResponseCodeVO guardianRename(GuardianRenameCommand guardianRenameCommand);
    ResponseCodeVO guardianChangeStatus(GuardianChangeStatusCommand guardianChangeStatusCommand);
    ResponseCodeVO guardianAccept(GuardianAcceptCommand guardianAcceptCommand);

    ResponseCodeVO renameProtege(String userId, String protegeId, String rename);
    ResponseCodeVO mappingDelete(String userId, String deleteId, String type);
    ResponseCodeVO memberUpdateInfo(String userId, String name, String phoneNumber);
    ResponseCodeVO pushAlarmStatus(String userId, String status);

    HelpMessageVO registerHelpMessage(HelpMessageRegisterCommand helpMessageRegisterCommand);
    FCMTokenVO receiveFCMToken (FCMTokenCommand fcmTokenCommand);

    boolean guardianRequestPush (String userId, String message);
    ResponseCodeVO deleteUser(String userId);
}
