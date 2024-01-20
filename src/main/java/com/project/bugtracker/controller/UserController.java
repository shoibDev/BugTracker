package com.project.bugtracker.controller;

import com.project.bugtracker.DTO.DeveloperDTO;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.security.Role;
import com.project.bugtracker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/user")
public class UserController {

    private final DeveloperService developerService;

    @GetMapping("/current-user")
    public DeveloperDTO getCurrentUser(Principal principal) {
        Developer developer = developerService.getUserByEmail(principal.getName());
        return developerService.fromEntityToDTO(developer);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('developer:read')")
    public List<Developer> retrieveAllUsers(){
        return developerService.retrieveAll();
    }
}
