package com.project.bugtracker.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDTO (
        Integer id,
        String name,
        String description,
        LocalDateTime dateCreated,
        LocalDateTime lastUpdated,
        List<Integer> devId
){
}
