package com.example.todoapp.services;

import com.example.todoapp.dto.TodoDto;
import java.util.List;

public interface TodoService {
    TodoDto create(TodoDto dto);
    List<TodoDto> getAll();
    TodoDto getById(Long id);
    TodoDto update(Long id, TodoDto dto);
    void delete(Long id);
}
