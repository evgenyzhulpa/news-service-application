package com.example.REST.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsCategoryFilter {

    private Integer pageSize = 10;
    private Integer pageNumber = 5;
}
