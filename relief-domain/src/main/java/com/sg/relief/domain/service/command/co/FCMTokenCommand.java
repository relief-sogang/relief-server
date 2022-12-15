package com.sg.relief.domain.service.command.co;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FCMTokenCommand {
    private String userId;
    private String token;
}
