package com.project.bugtracker.service;

import com.project.bugtracker.DTO.DeveloperDTO;
import com.project.bugtracker.DTO.DeveloperDTOMapper;
import com.project.bugtracker.Repository.DeveloperRepository;
import com.project.bugtracker.model.Developer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;

    @Override
    public Developer findUserById(Integer userId) {
        return developerRepository.findById(userId).get();
    }

    public Developer getUserByEmail(String email) {
        return developerRepository.findByEmail(email).orElse(null);
    }

    public List<Developer> retrieveAll(){
        return developerRepository.findAll();
    }

    public DeveloperDTO fromEntityToDTO(Developer developer) {
        return new DeveloperDTOMapper().apply(developer);
    }

    @Override
    public void saveUser(Developer developer) {
        developerRepository.save(developer);
    }

    @Override
    public void deleteUser(Developer developer) {
        developerRepository.delete(developer);
    }
}
