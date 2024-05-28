package com.example.REST.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {

    private Integer pageSize = 10;
    private Integer pageNumber = 0;
}
