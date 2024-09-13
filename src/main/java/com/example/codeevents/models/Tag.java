package com.example.codeevents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity {

    @Size(min = 1, max = 25)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private final List<Event> events = new ArrayList<>();

    public Tag(String description) {
        this.name = description;
    }

    public Tag () {}

    public String getName() {
        return name;
    }

    public String getDisplayName () {
        return "#" + name + " ";
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }
}
