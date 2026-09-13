package com.example.taskmanager.dto;

import com.example.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private Role role;
    private List<TaskDTO> tasks;
}
