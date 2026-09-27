package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.enums.Category;
import com.example.taskmanager.exceptions.NotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<TaskDTO> createTask(TaskDTO taskDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("user not found"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCategory(taskDTO.getCategory() != null ? taskDTO.getCategory() : Category.PERSONAL);
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return new ApiResponse<>(201, "Task created",  savedTask.toDTO());
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTasksByUserAndCategory(String email, Category category) {
        return null;
    }

    @Override
    public ApiResponse<String> deleteTask(Long id) {
        return null;
    }

    @Override
    public ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String email) {
        return null;
    }

}
