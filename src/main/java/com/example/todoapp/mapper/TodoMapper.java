package com.example.todoapp.mapper;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoDto toDto(Todo entity);

    @Mapping(target = "id", ignore = true) // id servis katmanında idare edilir
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Todo toEntity(TodoDto dto);
}
