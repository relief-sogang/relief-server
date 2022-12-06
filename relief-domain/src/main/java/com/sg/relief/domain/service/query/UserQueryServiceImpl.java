package com.sg.relief.domain.service.query;

import com.sg.relief.domain.persistence.repository.UserRepository;
import com.sg.relief.domain.service.query.vo.UserIdCheckVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserIdCheckVO findId(String id){
        if(userRepository.findByUserId(id).isPresent()){
            return UserIdCheckVO.builder().isExist(true).build();
        } else {
            return UserIdCheckVO.builder().isExist(false).build();
        }
    }
}
