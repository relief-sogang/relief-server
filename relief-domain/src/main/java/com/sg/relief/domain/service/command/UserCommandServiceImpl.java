package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.GuardianRequestVO;
import com.sg.relief.domain.service.command.vo.UserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingRepository userMappingRepository;

    @Override
    public UserDetailVO register(UserDetailCommand userDetailCommand){
        Optional<User> user = userRepository.findByEmail(userDetailCommand.getEmail());
        UserDetailVO userDetailVO = UserDetailVO.builder().userId(userDetailCommand.getUserId()).build();
        log.info("USER: {}", user);
        if(user.isPresent()){
            User updateUser = user.get();
            updateUser.setUserId(userDetailCommand.getUserId());
            updateUser.setPhoneNumber(userDetailCommand.getPhoneNumber());
            updateUser.setStatus(UserStatus.COMPLETED);
            userRepository.save(updateUser);

            userDetailVO.setUserName(user.get().getName());
        }
        return userDetailVO;
    }

    @Override
    public GuardianRequestVO guardianRequest(GuardianRequestCommand guardianRequestCommand){
        Optional<User> user = userRepository.findByUserId(guardianRequestCommand.getGuardianId());
        GuardianRequestVO guardianRequestVO = GuardianRequestVO.builder().build();
        if(user.isEmpty()){
            guardianRequestVO.setCode("NOT_EXIST");
        } else  if(userMappingRepository.findByProtegeIdAndGuardianName(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianName()).isPresent()){
            guardianRequestVO.setCode("DUPLICATE_NAME");
        } else if(userMappingRepository.findByProtegeIdAndGuardianId(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianId()).isPresent()) {
            guardianRequestVO.setCode("DUPLICATE_GUARDIAN");
        } else {
            UserMapping userMapping = UserMapping.builder()
                    .protegeId(guardianRequestCommand.getProtegeId())
                    .guardianId(guardianRequestCommand.getGuardianId())
                    .guardianName(guardianRequestCommand.getGuardianName())
                    .status(UserMappingStatus.REQUEST)
                    .message(guardianRequestCommand.getMessage())
                    .build();
            userMappingRepository.save(userMapping);
            guardianRequestVO.setCode("SUCCESS");
        }

        return guardianRequestVO;

    }

    @Override
    public GuardianRequestVO guardianRename(GuardianRenameCommand guardianRenameCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianRenameCommand.getUserId(), guardianRenameCommand.getGuardianId()).get();
        GuardianRequestVO guardianRequestVO = GuardianRequestVO.builder().build();
        List<UserMapping> userMappings = userMappingRepository.findAllByProtegeId(guardianRenameCommand.getUserId()).stream()
                        .filter(x->x.getGuardianName().equals(guardianRenameCommand.getName()))
                                .collect(Collectors.toList());
        if(userMappings.size() > 0){
            guardianRequestVO.setCode("DUPLICATE_NAME");
        } else {
            userMapping.setGuardianName(guardianRenameCommand.getName());
            userMappingRepository.save(userMapping);
            guardianRequestVO.setCode("SUCCESS");
        }
        return guardianRequestVO;

    }

    @Override
    public GuardianRequestVO guardianChangeStatus(GuardianChangeStatusCommand guardianChangeStatusCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianChangeStatusCommand.getUserId(), guardianChangeStatusCommand.getGuardianId()).get();
        GuardianRequestVO guardianRequestVO = GuardianRequestVO.builder().build();
        if(guardianChangeStatusCommand.getIsActive()){
            userMapping.setStatus(UserMappingStatus.ON);
            guardianRequestVO.setCode("ON");
        } else {
            userMapping.setStatus(UserMappingStatus.OFF);
            guardianRequestVO.setCode("OFF");
        }
        userMappingRepository.save(userMapping);
        return guardianRequestVO;
    }

    @Override
    public GuardianRequestVO guardianAccept(GuardianAcceptCommand guardianAcceptCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianAcceptCommand.getUserId(), guardianAcceptCommand.getProtegeId()).get();
        GuardianRequestVO guardianRequestVO = GuardianRequestVO.builder().build();
        if(guardianAcceptCommand.getIsAccept()){
            userMapping.setStatus(UserMappingStatus.ON);
            userMapping.setProtegeName(guardianAcceptCommand.getProtegeName());
            guardianRequestVO.setCode("ACCEPT");
        } else {
            userMapping.setStatus(UserMappingStatus.REJECT);
            guardianRequestVO.setCode("REJECT");
        }
        userMappingRepository.save(userMapping);
        return guardianRequestVO;
    }
}
