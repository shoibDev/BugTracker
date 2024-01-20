package com.project.bugtracker.Repository;

import com.project.bugtracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project, Integer> {


}
