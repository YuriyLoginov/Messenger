package com.SimbirSoft.Practice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageMetricsDto {
    private String message;
    private String author;
}
