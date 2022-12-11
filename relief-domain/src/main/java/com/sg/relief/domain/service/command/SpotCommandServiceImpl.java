package com.sg.relief.domain.service.command;

import com.sg.relief.domain.persistence.entity.UserSpot;
import com.sg.relief.domain.persistence.repository.UserSpotRepository;
import com.sg.relief.domain.service.command.co.UserSpotRegisterCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class SpotCommandServiceImpl implements SpotCommandService {

    @Autowired
    private UserSpotRepository userSpotRepository;

    @Override
    public String registerSpot (UserSpotRegisterCommand userSpotRegisterCommand){
        if(userSpotRepository.findByUserIdAndSpotName(userSpotRegisterCommand.getUserId(), userSpotRegisterCommand.getName()).isPresent()){
            return "DUPLICATE";
        }

        UserSpot userSpot = UserSpot.builder()
                .userId(userSpotRegisterCommand.getUserId())
                .spotName(userSpotRegisterCommand.getName())
                .lat(new BigDecimal(userSpotRegisterCommand.getLat()))
                .lng(new BigDecimal(userSpotRegisterCommand.getLng()))
                .build();
        userSpotRepository.save(userSpot);
        return "SUCCESS";
    }

    @Override
    public String deleteSpot(String userId, String spotName){
        UserSpot userSpot = userSpotRepository.findByUserIdAndSpotName(userId, spotName).get();
        userSpotRepository.delete(userSpot);
        return "SUCCESS";
    }
}
