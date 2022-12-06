package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.query.vo.GuardianInfoVO;
import com.sg.relief.domain.service.query.vo.GuardianListVO;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMappingRepository userMappingRepository;


    @Override
    public UserIdCheckVO findId(String id){
        if(userRepository.findByUserId(id).isPresent()){
            return UserIdCheckVO.builder().isExist(true).build();
        } else {
            return UserIdCheckVO.builder().isExist(false).build();
        }
    }

    @Override
    public GuardianListVO getGuardianList(String userId){
        List<UserMapping> userMappings = userMappingRepository.findAllByProtegeId(userId);
        List<GuardianInfoVO> guardianInfoVOS = userMappings.stream().map(x->{
            User user = userRepository.findByUserId(x.getGuardianId()).get();
            return GuardianInfoVO.builder()
                    .name(x.getGuardianName())
                    .id(x.getGuardianId())
                    .email(user.getEmail())
                    .status(x.getStatus().toString())
                    .build();
        }).collect(Collectors.toList());

        return GuardianListVO.builder()
                .guardianList(guardianInfoVOS)
                .build();
    }

    @Override
    public GuardianInfoVO getGuardianDetail(String guardianId){
        User user = userRepository.findByUserId(guardianId).get();
        return GuardianInfoVO.builder()
                .name(user.getName())
                .id(user.getUserId())
                .email(user.getEmail())
                .status(user.getStatus().toString())
                .build();
    }
}
