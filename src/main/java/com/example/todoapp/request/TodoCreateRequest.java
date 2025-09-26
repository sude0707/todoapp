package com.example.todoapp.request;

import jakarta.validation.constraints.NotBlank;

public record TodoCreateRequest(
        @NotBlank(message = "title boş olamaz")
        String title,
        String description
) {}
