package com.SimbirSoft.Practice.dto.room;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RoomDto {
    private String RoomName;
    private String owner;
}
