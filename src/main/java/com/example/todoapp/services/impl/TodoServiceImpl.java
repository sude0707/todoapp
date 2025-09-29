package com.example.todoapp.services.impl;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.exception.DuplicateException;
import com.example.todoapp.exception.NotFoundException;
import com.example.todoapp.mapper.TodoMapper;
import com.example.todoapp.repository.TodoRepository;
import com.example.todoapp.request.TodoCreateRequest;
import com.example.todoapp.request.TodoUpdateRequest;
import com.example.todoapp.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;
    private final TodoMapper mapper;
    @Override
    public TodoDto create(TodoCreateRequest request) {
        String normalizedTitle = request.title() == null ? "" : request.title().trim();
        if (normalizedTitle.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (repo.existsByTitleIgnoreCase(normalizedTitle)) {
            throw new DuplicateException("Title already exists: " + normalizedTitle);
        }

        Todo toSave = mapper.toEntity(request);   // request'te completed yok
        toSave.setTitle(normalizedTitle);
        toSave.setCompleted(false);               // create: her zaman false
        toSave.setCreatedAt(LocalDateTime.now());
        toSave.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repo.save(toSave));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TodoDto getById(Long id) {
        Todo t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found. id=" + id));
        return mapper.toDto(t);
    }

    @Override
    public TodoDto update(TodoUpdateRequest request) {
        Todo existing = repo.findById(request.id())
                .orElseThrow(() -> new NotFoundException("Todo not found. id=" + request.id()));

        if (request.title() != null) {
            String newTitle = request.title().trim();
            if (!newTitle.isBlank()
                    && !newTitle.equalsIgnoreCase(existing.getTitle())
                    && repo.existsByTitleIgnoreCase(newTitle)) {
                throw new DuplicateException("Title already exists: " + newTitle);
            }
            if (!newTitle.isBlank()) {
                existing.setTitle(newTitle);
            }
        }

        if (request.description() != null) {
            existing.setDescription(request.description());
        }

        existing.setCompleted(request.completed());

        existing.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repo.saveAndFlush(existing));
    }



    @Override
    public void delete(Long id) {
        Todo t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo not found. id=" + id));
        repo.delete(t);
    }
}
