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
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("user not found"));
        List<Task> tasks;
        if (category != null) {
            tasks = taskRepository.findByUserAndCategory(user, category);
        } else {
            tasks = taskRepository.findByUser(user);
        }
        List<TaskDTO> taskDTOs = tasks.stream().map(Task::toDTO).toList();
        return new ApiResponse<>(200, "Tasks retrieved", taskDTOs);
    }

    @Override
    public ApiResponse<String> deleteTask(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found"));
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().equals(user)) {
            throw new NotFoundException("Task not found for this user");
        }
        taskRepository.deleteById(id);
        return new ApiResponse<>(200, "Task deleted", "Task with id " + id + " has been deleted");
    }

    @Override
    public ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("user not found"));
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        if (!task.getUser().equals(user)) {
            throw new NotFoundException("Task not found for this user");
        }
        task.setCompleted(!task.isCompleted());
        Task updatedTask = taskRepository.save(task);
        return new ApiResponse<>(200, "Task completion toggled", updatedTask.toDTO());
    }

}
