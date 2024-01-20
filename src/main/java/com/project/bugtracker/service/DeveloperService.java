package com.project.bugtracker.service;

import com.project.bugtracker.DTO.DeveloperDTO;
import com.project.bugtracker.model.Developer;

import java.util.List;

public interface DeveloperService {

    Developer findUserById(Integer userId);

    Developer getUserByEmail(String name);

    List<Developer> retrieveAll();

    DeveloperDTO fromEntityToDTO(Developer developer);

    void saveUser(Developer developer);

    void deleteUser(Developer developer);
}
