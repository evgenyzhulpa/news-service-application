package com.example.REST.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    private String name;
}
