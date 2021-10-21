package com.SimbirSoft.Practice.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class RoomRenameDto {
    private String oldName;
    private String newName;
}
