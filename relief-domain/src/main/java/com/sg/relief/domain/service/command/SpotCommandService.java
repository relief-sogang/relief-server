package com.sg.relief.domain.service.command;

import com.sg.relief.domain.service.command.co.UserSpotRegisterCommand;

public interface SpotCommandService {
    String registerSpot (UserSpotRegisterCommand userSpotRegisterCommand);
    String deleteSpot(String userId, String spotName);
}
