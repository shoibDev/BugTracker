package com.project.bugtracker.Repository;

import com.project.bugtracker.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {

    Optional<Developer> findByEmail(String email);
}
