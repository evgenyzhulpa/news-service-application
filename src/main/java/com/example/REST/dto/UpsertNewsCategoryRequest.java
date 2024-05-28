package com.example.REST.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertNewsCategoryRequest {

    @NotBlank(message = "Имя категории новостей должно быть заполнено!")
    private String name;
}
