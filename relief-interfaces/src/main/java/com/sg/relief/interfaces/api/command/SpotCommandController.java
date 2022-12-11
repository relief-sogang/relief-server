package com.sg.relief.interfaces.api.command;

import com.sg.relief.domain.service.command.SpotCommandService;
import com.sg.relief.domain.service.command.co.UserSpotRegisterCommand;
import com.sg.relief.interfaces.api.command.dto.SpotDeleteCommandDTO;
import com.sg.relief.interfaces.api.command.dto.SpotRegisterCommandDTO;
import com.sg.relief.interfaces.api.command.dto.SpotResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/spot")
public class SpotCommandController {

    @Autowired
    private SpotCommandService spotCommandService;

    @PostMapping("/register")
    public SpotResponseDTO registerSpot(@RequestBody SpotRegisterCommandDTO spotRegisterCommandDTO){
        String response = spotCommandService.registerSpot(UserSpotRegisterCommand.builder()
                        .userId(spotRegisterCommandDTO.getUserId())
                        .name(spotRegisterCommandDTO.getName())
                        .lat(spotRegisterCommandDTO.getLat())
                        .lng(spotRegisterCommandDTO.getLng())
                .build());

        return SpotResponseDTO.builder()
                .code(response)
                .build();
    }

    @PostMapping("/delete")
    public SpotResponseDTO deleteSpot(@RequestBody SpotDeleteCommandDTO spotDeleteCommandDTO){
        String response = spotCommandService.deleteSpot(spotDeleteCommandDTO.getUserId(), spotDeleteCommandDTO.getName());

        return SpotResponseDTO.builder()
                .code(response)
                .build();
    }

}
