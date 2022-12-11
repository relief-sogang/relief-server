package com.sg.relief.domain.service.command;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.command.co.*;
import com.sg.relief.domain.service.command.vo.ResponseCodeVO;
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
    public ResponseCodeVO guardianRequest(GuardianRequestCommand guardianRequestCommand){
        Optional<User> user = userRepository.findByUserId(guardianRequestCommand.getGuardianId());
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(user.isEmpty()){
            responseCodeVO.setCode("NOT_EXIST");
        } else  if(userMappingRepository.findByProtegeIdAndGuardianName(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianName()).isPresent()){
            responseCodeVO.setCode("DUPLICATE_NAME");
        } else if(userMappingRepository.findByProtegeIdAndGuardianId(guardianRequestCommand.getProtegeId(), guardianRequestCommand.getGuardianId()).isPresent()) {
            responseCodeVO.setCode("DUPLICATE_GUARDIAN");
        } else {
            UserMapping userMapping = UserMapping.builder()
                    .protegeId(guardianRequestCommand.getProtegeId())
                    .guardianId(guardianRequestCommand.getGuardianId())
                    .guardianName(guardianRequestCommand.getGuardianName())
                    .status(UserMappingStatus.REQUEST)
                    .message(guardianRequestCommand.getMessage())
                    .build();
            userMappingRepository.save(userMapping);
            responseCodeVO.setCode("SUCCESS");
        }

        return responseCodeVO;

    }

    @Override
    public ResponseCodeVO guardianRename(GuardianRenameCommand guardianRenameCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianRenameCommand.getUserId(), guardianRenameCommand.getGuardianId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        List<UserMapping> userMappings = userMappingRepository.findAllByProtegeId(guardianRenameCommand.getUserId()).stream()
                        .filter(x->x.getGuardianName().equals(guardianRenameCommand.getName()))
                                .collect(Collectors.toList());
        if(userMappings.size() > 0){
            responseCodeVO.setCode("DUPLICATE_NAME");
        } else {
            userMapping.setGuardianName(guardianRenameCommand.getName());
            userMappingRepository.save(userMapping);
            responseCodeVO.setCode("SUCCESS");
        }
        return responseCodeVO;

    }

    @Override
    public ResponseCodeVO guardianChangeStatus(GuardianChangeStatusCommand guardianChangeStatusCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianChangeStatusCommand.getUserId(), guardianChangeStatusCommand.getGuardianId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(guardianChangeStatusCommand.getIsActive()){
            userMapping.setStatus(UserMappingStatus.ON);
            responseCodeVO.setCode("ON");
        } else {
            userMapping.setStatus(UserMappingStatus.OFF);
            responseCodeVO.setCode("OFF");
        }
        userMappingRepository.save(userMapping);
        return responseCodeVO;
    }

    @Override
    public ResponseCodeVO guardianAccept(GuardianAcceptCommand guardianAcceptCommand){
        UserMapping userMapping = userMappingRepository.findByProtegeIdAndGuardianId(guardianAcceptCommand.getUserId(), guardianAcceptCommand.getProtegeId()).get();
        ResponseCodeVO responseCodeVO = ResponseCodeVO.builder().build();
        if(guardianAcceptCommand.getIsAccept()){
            userMapping.setStatus(UserMappingStatus.ON);
            userMapping.setProtegeName(guardianAcceptCommand.getProtegeName());
            responseCodeVO.setCode("ACCEPT");
        } else {
            userMapping.setStatus(UserMappingStatus.REJECT);
            responseCodeVO.setCode("REJECT");
        }
        userMappingRepository.save(userMapping);
        return responseCodeVO;
    }

    @Override
    public ResponseCodeVO renameProtege(String userId, String protegeId, String rename){
        Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(protegeId, userId);
        if(userMapping.isPresent()){
            UserMapping updateMapping = userMapping.get();
            updateMapping.setProtegeName(rename);
        }
        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO mappingDelete(String userId, String deleteId, String type){
        if(type.equals("GUARDIAN")){
            Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(userId, deleteId);
            userMappingRepository.delete(userMapping.get());
        } else {
            Optional<UserMapping> userMapping = userMappingRepository.findByProtegeIdAndGuardianId(deleteId, userId);
            UserMapping updateMapping = userMapping.get();
            updateMapping.setStatus(UserMappingStatus.REQUEST);
            userMappingRepository.save(userMapping.get());
        }

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO memberUpdateInfo(String userId, String name, String phoneNumber){
        Optional<User> user = userRepository.findByUserId(userId);
        User userUpdate = user.get();
        userUpdate.setName(name);
        userUpdate.setPhoneNumber(phoneNumber);
        userRepository.save(userUpdate);

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

    @Override
    public ResponseCodeVO pushAlarmStatus(String userId, String status){
        Optional<User> user = userRepository.findByUserId(userId);
        User userUpdate = user.get();
        if(status.equals("ON")){
            userUpdate.setActiveAlarm(true);
        } else {
            userUpdate.setActiveAlarm(false);
        }
        userRepository.save(userUpdate);

        return ResponseCodeVO.builder()
                .code("SUCCESS")
                .build();
    }

}
