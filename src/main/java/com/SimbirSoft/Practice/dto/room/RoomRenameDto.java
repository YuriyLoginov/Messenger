package com.SimbirSoft.Practice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomRenameDto {
    private String oldName;
    private String newName;
}
