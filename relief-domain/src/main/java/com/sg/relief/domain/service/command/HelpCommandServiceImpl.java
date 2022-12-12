package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.HelpMessageStatus;
import com.sg.relief.domain.persistence.entity.HelpMessage;
import com.sg.relief.domain.persistence.repository.HelpMessageRepository;
import com.sg.relief.domain.service.command.vo.HelpMessageReadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class HelpCommandServiceImpl implements HelpCommandService {

    @Autowired
    private HelpMessageRepository helpMessageRepository;
    /* 미확인 메시지 읽음 처리 */
    @Override
    public HelpMessageReadVO readHelpMessage(String receiverId, String messageId) {
        Optional<HelpMessage> helpMessageOptional = helpMessageRepository.findById(Long.getLong(messageId));
        HelpMessageReadVO helpMessageReadVO = HelpMessageReadVO.builder().code("FAIL").build();
        if (helpMessageOptional.isPresent()) {
            HelpMessage helpMessageUpdate = helpMessageOptional.get();
            helpMessageUpdate.setCheckStatus(HelpMessageStatus.Y);
            helpMessageRepository.save(helpMessageUpdate);
            helpMessageReadVO.setCode("SUCCESS");
        }
        return helpMessageReadVO;
    }
}
