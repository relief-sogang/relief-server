package com.sg.relief.domain.service.query;

import com.sg.relief.domain.code.HelpMessageStatus;
import com.sg.relief.domain.persistence.entity.HelpMessage;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.HelpMessageRepository;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.service.query.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HelpQueryServiceImpl implements HelpQueryService{
    @Autowired
    private HelpMessageRepository helpMessageRepository;
    @Autowired
    private UserMappingRepository userMappingRepository;

    @Override
    public HelpSendListVO getHelpSendList(String senderId) {
        List<HelpMessage> helpMessageList = helpMessageRepository.findAllBySenderId(senderId);
        List<HelpSendInfoVO> helpSendInfoVOS = helpMessageList.stream().map(h-> {
            Optional<UserMapping> userMappingOptional = userMappingRepository
                    .findByProtegeIdAndGuardianId(senderId, h.getReceiverId());
            HelpSendInfoVO helpSendInfoVO = HelpSendInfoVO.builder()
                    .date(h.getDateTime())
                    .message(h.getMessage())
                    .build();
            if (userMappingOptional.isPresent()) {
                helpSendInfoVO.setGuardianName(userMappingOptional.get().getGuardianName());
            }
            else {
                helpSendInfoVO.setGuardianName("탈퇴한 사용자");
            }
            return helpSendInfoVO;
        }).collect(Collectors.toList());

        return HelpSendListVO.builder().sendList(helpSendInfoVOS).build();
    }

    @Override
    public HelpReceiveListVO getHelpReceiveList(String receiverId) {
        List<HelpMessage> helpMessageList = helpMessageRepository.findAllByReceiverId(receiverId);
        List<HelpReceiveInfoVO> helpReceiveInfoVOS = helpMessageList.stream().map(h -> {
            Optional <UserMapping> userMappingOptional = userMappingRepository
                    .findByProtegeIdAndGuardianId(h.getSenderId(), receiverId);
            HelpReceiveInfoVO helpReceiveInfoVO = HelpReceiveInfoVO.builder()
                    .messageId(Long.toString(h.getId()))
                    .checkStatus(h.getCheckStatus().toString())
                    .date(h.getDateTime())
                    .message(h.getMessage())
                    .build();
            if (userMappingOptional.isPresent()) {
                helpReceiveInfoVO.setProtegeName(userMappingOptional.get().getProtegeName());
            }
            else {
                helpReceiveInfoVO.setProtegeName("탈퇴한 사용자");
            }
            return helpReceiveInfoVO;
        }).collect(Collectors.toList());
        return HelpReceiveListVO.builder()
                .receiveList(helpReceiveInfoVOS)
                .build();
    }

    /* 미확인 메시지 개수 반환*/
    @Override
    public HelpReceiveCountVO getHelpReceiveCount(String receiverId) {
        List<HelpMessage> helpMessageList = helpMessageRepository.findAllByReceiverId(receiverId);
        Long count = helpMessageList.stream()
                .filter(m -> m.getCheckStatus().equals(HelpMessageStatus.N)).count();
        return HelpReceiveCountVO.builder().count(Long.toString(count)).build();
    }


}
