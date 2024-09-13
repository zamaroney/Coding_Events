package com.example.codeevents.models.dto;

import com.example.codeevents.models.Event;
import com.example.codeevents.models.Tag;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class EventTagDTO {

    @NotNull
    private Event event;

    @NotNull
    private List<Tag> tags;
    public EventTagDTO() {}

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
