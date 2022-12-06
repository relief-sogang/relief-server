package com.sg.relief.domain.service.command;

import com.sg.relief.domain.service.command.co.UserDetailCommand;
import com.sg.relief.domain.service.command.vo.UserDetailVO;

public interface UserCommandService {
    UserDetailVO register(UserDetailCommand userDetailCommand);
}
