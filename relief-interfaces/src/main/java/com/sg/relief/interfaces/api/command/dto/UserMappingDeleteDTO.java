package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMappingDeleteDTO {
    private String userId;
    private String deleteId;
    private String type;
}
