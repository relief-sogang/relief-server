package com.sg.relief.domain.service.command.co;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveLocationCommand {
    private String code;
    private String lat;
    private String lng;
}
