package com.project.bugtracker.DTO;

import com.project.bugtracker.model.Developer;
import com.project.bugtracker.model.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public record TicketDTO(
        Integer id,
        String title,
        String description,
        String author,
        String priority,
        String type,
        String status,
        int projectId,
        List<Integer> devId
        ) {

}

