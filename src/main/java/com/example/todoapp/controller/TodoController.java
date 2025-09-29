package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.request.TodoCreateRequest;
import com.example.todoapp.request.TodoUpdateRequest;
import com.example.todoapp.services.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(todoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(todoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TodoDto> create(@RequestBody @Valid TodoCreateRequest request) {
        return ResponseEntity.ok(todoService.create(request));
    }

    @PutMapping("/title-descr-completed")
    public ResponseEntity<TodoDto> updateTitleDescrCompleted(@Valid @RequestBody TodoUpdateRequest request) {
        return ResponseEntity.ok(todoService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
