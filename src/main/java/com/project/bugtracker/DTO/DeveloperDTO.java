package com.project.bugtracker.DTO;

import com.project.bugtracker.model.Project;
import com.project.bugtracker.security.Role;


import java.util.Set;

public record DeveloperDTO(

    Integer id,
    String first_name,
    String last_name,
    String email,
    String phoneNumber,
    Role role,
    Set<ProjectDTO> projects
) {

}
