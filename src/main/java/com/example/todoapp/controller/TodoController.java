package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.services.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// DİKKAT: doğru base path
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

    // <<< ÖNEMLİ KISIM >>>
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(todoService.getById(id));
    }
    // <<< ÖNEMLİ KISIM >>>

    @PostMapping
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> update(@PathVariable("id") Long id,
                                          @RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
