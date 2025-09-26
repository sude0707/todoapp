package com.example.todoapp.services;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.request.TodoCreateRequest;

import java.util.List;

public interface TodoService {
    TodoDto create(TodoCreateRequest request);
    List<TodoDto> getAll();
    TodoDto getById(Long id);
    TodoDto update(Long id, TodoDto dto);
    void delete(Long id);
}
