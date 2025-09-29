package com.example.todoapp.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TodoUpdateRequest(
        @NotNull Long id,
        @NotBlank String title,
        String description,
        boolean completed
) {}
