package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotDeleteCommandDTO {
    private String userId;
    private String name;
}
