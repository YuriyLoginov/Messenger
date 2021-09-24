package com.SimbirSoft.Practice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoomDto {
    private String RoomName;
    private String owner;
}
