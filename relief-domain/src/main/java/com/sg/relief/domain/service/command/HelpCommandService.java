package com.sg.relief.domain.service.command;


import com.sg.relief.domain.service.command.vo.HelpMessageReadVO;

public interface HelpCommandService {
    HelpMessageReadVO readHelpMessage(String receiverId, String messageId);

}
