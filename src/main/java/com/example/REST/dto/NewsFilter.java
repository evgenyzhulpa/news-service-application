package com.example.REST.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {

    private Integer pageSize = 10;
    private Integer pageNumber = 0;
    private Long newsCategoryId;
    private Long userId;
}
