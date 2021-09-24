package com.SimbirSoft.Practice.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class MessageDto {
    @JsonProperty("message")
    @NonNull

    private String message;
    @JsonProperty("roomName")
    @NonNull
    private String roomName;
}
