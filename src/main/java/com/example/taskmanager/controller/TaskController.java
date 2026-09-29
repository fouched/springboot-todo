package com.example.taskmanager.controller;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.enums.Category;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping()
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(
            @RequestBody TaskDTO taskDTO,
            Authentication authentication) {
        ApiResponse<TaskDTO> response = taskService.createTask(taskDTO, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByUserAndCategory(
            @RequestParam(required = false)Category category,
            Authentication authentication
            ) {
        return ResponseEntity.ok(taskService.getTasksByUserAndCategory(authentication.getName(), category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(taskService.deleteTask(id, authentication.getName()));
    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> toggleTaskStatus(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(taskService.toggleTaskCompletion(id, authentication.getName()));
    }

}
