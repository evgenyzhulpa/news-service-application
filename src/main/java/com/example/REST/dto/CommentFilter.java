package com.example.REST.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentFilter {
    private Long newsId;
    private Integer pageSize = 10;
    private Integer pageNumber = 0;
}
