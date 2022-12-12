package com.sg.relief.domain.service.query;

import com.sg.relief.domain.service.query.vo.HelpReceiveCountVO;
import com.sg.relief.domain.service.query.vo.HelpReceiveListVO;
import com.sg.relief.domain.service.query.vo.HelpSendListVO;

public interface HelpQueryService {
    // HelpSendInfoVO getHelpSendDetail(String senderId);
    HelpSendListVO getHelpSendList(String senderId);
    // HelpReceiveInfoVO getHelpReceiveDetail(String receiverId);
    HelpReceiveListVO getHelpReceiveList(String receiverId);
    HelpReceiveCountVO getHelpReceiveCount(String receiverId);
}
