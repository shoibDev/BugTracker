package com.project.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bugtracker.DTO.DeveloperDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Ticket {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    private String author;

    private String priority;

    private String type;

    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(mappedBy = "tickets")
    private Set<Developer> developers = new HashSet<>();

}
