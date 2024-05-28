package com.example.REST.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertNewsRequest {

    @NotBlank(message = "Не указан заголовок новости!")
    private String title;

    @NotBlank(message = "Не указано описание новости!")
    private String description;

    private Long newsCategoryId;

    private Long userId;
}
