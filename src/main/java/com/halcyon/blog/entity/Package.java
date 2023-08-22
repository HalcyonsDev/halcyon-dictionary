package com.halcyon.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Table(name = "packages")
@NoArgsConstructor
@Getter
@Setter
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "words", columnDefinition = "JSON")
    private String words;

    @Column(name = "complexity")
    private String complexity;

    public Package(String name, String description, String complexity) {
        this.name = name;
        this.description = description;
        this.complexity = complexity;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", words='" + words + '\'' +
                ", complexity='" + complexity + '\'' +
                '}';
    }
}
