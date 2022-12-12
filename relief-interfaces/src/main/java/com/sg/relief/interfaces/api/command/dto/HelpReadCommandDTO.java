package com.sg.relief.interfaces.api.command.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelpReadCommandDTO {
    private String receiverId;
    private String messageId;
}
