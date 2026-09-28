package com.example.taskmanager.service;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.enums.Category;

import java.util.List;

public interface TaskService {
    ApiResponse<TaskDTO> createTask(TaskDTO taskDTO, String email);
    ApiResponse<List<TaskDTO>> getTasksByUserAndCategory(String email, Category category);
    ApiResponse<String> deleteTask(Long id, String email);
    ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String email);
}
