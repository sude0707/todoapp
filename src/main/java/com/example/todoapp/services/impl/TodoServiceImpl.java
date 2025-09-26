package com.example.todoapp.services.impl;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.exception.DuplicateException;
import com.example.todoapp.exception.NotFoundException;
import com.example.todoapp.mapper.TodoMapper;
import com.example.todoapp.repository.TodoRepository;
import com.example.todoapp.request.TodoCreateRequest;
import com.example.todoapp.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;
    private final TodoMapper mapper;

    @Override
    public TodoDto create(TodoCreateRequest request) {
        if (repo.existsByTitle(request.title())) {
            throw new DuplicateException("Aynı başlıkta todo zaten var: " + request.title());
        }
        Todo saved = repo.save(mapper.toEntity(request));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TodoDto getById(Long id) {
        Todo t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo bulunamadı. id=" + id));
        return mapper.toDto(t);
    }

    @Override
    public TodoDto update(Long id, TodoDto dto) {
        Todo existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo bulunamadı. id=" + id));

        if (!existing.getTitle().equals(dto.getTitle()) && repo.existsByTitle(dto.getTitle())) {
            throw new DuplicateException("Aynı başlıkta todo zaten var: " + dto.getTitle());
        }

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCompleted(dto.isCompleted());
        return mapper.toDto(repo.save(existing));
    }

    @Override
    public void delete(Long id) {
        Todo t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Todo bulunamadı. id=" + id));
        repo.delete(t);
    }
}
