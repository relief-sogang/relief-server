package com.sg.relief.domain.service.query;

import com.sg.relief.domain.code.UserMappingStatus;
import com.sg.relief.domain.code.UserStatus;
import com.sg.relief.domain.persistence.entity.User;
import com.sg.relief.domain.persistence.entity.UserMapping;
import com.sg.relief.domain.persistence.repository.UserMappingRepository;
import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.query.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public ProtegeListVO getProtegeList(String userId, String status) {
        List<UserMapping> userMappings = userMappingRepository.findALlByGuardianId(userId);
        List<ProtegeInfoVO> protegeInfoVOS = new ArrayList<>();
        if(status.equals("REQUEST")){
            protegeInfoVOS = userMappings.stream()
                    .filter(x->x.getStatus().equals(UserMappingStatus.REQUEST))
                    .map(x->{
                        User user = userRepository.findByUserId(x.getProtegeId()).get();
                        return ProtegeInfoVO.builder()
                                .id(x.getProtegeId())
                                .name(user.getName()) // user 정보에 저장된 이름
                                .email(user.getEmail())
                                .status(x.getStatus().toString())
                                .build();
                    })
                    .collect(Collectors.toList());
        } else if (status.equals("SHARING")){
            protegeInfoVOS = userMappings.stream().filter(x->{
                if(x.getStatus().equals(UserMappingStatus.ON) && userRepository.findByUserId(x.getProtegeId()).get().getStatus().equals(UserStatus.SHARING)){
                    return true;
                } else {
                    return false;
                }
            })
            .map(x->{
                User user = userRepository.findByUserId(x.getProtegeId()).get();
                return ProtegeInfoVO.builder()
                        .id(x.getProtegeId())
                        .name(x.getProtegeName()) // user mapping 정보에 저장된 이름
                        .email(user.getEmail())
                        .status(x.getStatus().toString())
                        .build();
            })
            .collect(Collectors.toList());
        } else { // ALL
            protegeInfoVOS = userMappings.stream().map(x->{
                User user = userRepository.findByUserId(x.getProtegeId()).get();
                return ProtegeInfoVO.builder()
                        .id(x.getProtegeId())
                        .name(x.getProtegeName()) // user mapping 정보에 저장된 이름
                        .email(user.getEmail())
                        .status(x.getStatus().toString())
                        .build();
            }).collect(Collectors.toList());
        }

        return ProtegeListVO.builder()
                .protegeList(protegeInfoVOS)
                .build();
    }
}
