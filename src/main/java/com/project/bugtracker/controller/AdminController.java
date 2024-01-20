package com.project.bugtracker.controller;

import com.project.bugtracker.DTO.DeveloperDTO;
import com.project.bugtracker.model.Developer;
import com.project.bugtracker.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final DeveloperService developerService;


    @PutMapping(path = "user/{userId}/edit-user")
    @PreAuthorize("hasAuthority('admin:edit')")
    public String put(@PathVariable("userId") Integer userId,
                       @RequestBody Developer newDeveloper) {

        Developer developer = developerService.findUserById(userId);

        developer.setFirst_name(newDeveloper.getFirst_name());
        developer.setLast_name(newDeveloper.getLast_name());
        developer.setEmail(newDeveloper.getEmail());
        developer.setPhone_number(newDeveloper.getPhone_number());
        developer.setRole(newDeveloper.getRole());

        developerService.saveUser(developer);

        return "User updated successfully";
    }

    @DeleteMapping(path = "user/{userId}/delete-user")
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(@PathVariable("userId") Integer userId){
        developerService.deleteUser(developerService.findUserById(userId));
        return "DELETE:: admin controller";
    }
}
