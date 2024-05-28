package com.example.REST.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertCommentRequest {

    @NotBlank(message = "Не указано описание комментария!")
    private String description;

    private Long userId;

    private Long newsId;
}
