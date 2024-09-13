package com.example.codeevents.controllers;

import com.example.codeevents.data.EventCategoryRepository;
import com.example.codeevents.data.EventRepository;
import com.example.codeevents.data.EventTagRepository;
import com.example.codeevents.models.Event;
import com.example.codeevents.models.EventCategory;
import com.example.codeevents.models.Tag;
import com.example.codeevents.models.dto.EventTagDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private EventTagRepository tagRepository;
    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/index";
    }

    @GetMapping("category/{categoryId}")
    public String displayEventBasedOnCategry(@PathVariable int categoryId, Model model) {
        Optional<EventCategory> results = eventCategoryRepository.findById(categoryId);
        if (results.isEmpty()) {
            model.addAttribute("title", "Invalid Category ID: " + categoryId);
        }
        else {
            EventCategory category = results.get();
            model.addAttribute("title", "Events in Category: " + category.getName());
            model.addAttribute("events", category.getEvents());
        }

        return "events/index";
    }

    // located at /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("categories", eventCategoryRepository.findAll());
        return "events/create";
    }

    // located at /events/create
    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            model.addAttribute("categories", eventCategoryRepository.findAll());
            return "events/create";
        }
        eventRepository.save(newEvent);
        return "redirect:";
    }


    // Updated to delete events from Edit page
//    @GetMapping("delete")
//    public String displayDeleteEventForm(Model model) {
//        model.addAttribute("title", "Delete Events");
//        model.addAttribute("events", eventRepository.findAll());
//        return "events/delete";
//    }

    @PostMapping("delete")
    public String processDeleteEventForm(@RequestParam(required = true) int eventId){
        eventRepository.deleteById(eventId);
        return "redirect:";
    }

    @GetMapping("details/{eventId}")
    public String displayEventDetails(@PathVariable int eventId, Model model) {

        Optional<Event> results = eventRepository.findById(eventId);
        if (results.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        }
        else {
            Event event = results.get();
            model.addAttribute("title", event.getName() + " Details");
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }

        return "events/details";
    }

    // responds to /events/add-tags?eventId=13
    @GetMapping("add-tags")
    public String displayAddTagForm(@RequestParam(required = true)int eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isPresent()) {
            Event event = result.get();
            model.addAttribute("title", "Add Tag to: " + event.getName());
            model.addAttribute("tags", tagRepository.findAll());
            EventTagDTO eventTag = new EventTagDTO();
            eventTag.setEvent(event);
            model.addAttribute("eventTag", eventTag);
            return "events/add-tag";
        }
        // Handle the case where the event was not found
        return "redirect:/events"; // or any other error handling

    }

    @PostMapping("add-tags")
    public String processAddTagForm(@ModelAttribute("") @Valid EventTagDTO eventTag, Errors errors) {
//        if (!errors.hasErrors()) {
//
//            Event event = eventTag.getEvent();
//            Tag tag = eventTag.getTag();
//            System.out.println(tag.getDisplayName());
//            if (!event.getTags().contains(tag)) {
//                event.addTag(tag);
//
//                eventRepository.save(event);
//            }
//            return "redirect:details?eventId="+event.getId();
//        }
//        return "redirect:add-tag";
        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            List<Tag> tags = eventTag.getTags();

            for (Tag tag : tags) {
                if (!event.getTags().contains(tag)) {
                    event.addTag(tag);
                }
            }

            eventRepository.save(event);
            return "redirect:details/" + event.getId();
        }
        return "events/add-tag";
    }

    @GetMapping("edit-details")
    public String displayEditForm (@RequestParam(required = true)int eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Event ID: " + eventId);
        }
        else {
            Event event = result.get();
            model.addAttribute("action", "/events/delete?eventId=" + eventId);
            model.addAttribute("title", "Edit "+event.getName());
            model.addAttribute("event", event);
            model.addAttribute("categories", eventCategoryRepository.findAll());
        }

        return "events/edit-details";
    }

    @PostMapping("edit-details")
    public String processEditEventForm(@ModelAttribute @Valid Event event,
                                         Errors errors, Model model, @RequestParam(required = true)int eventId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Not Saved Edit "+event.getName());
            model.addAttribute("categories", eventCategoryRepository.findAll());
            return "events/edit-details";
        }

        Optional<Event> result = eventRepository.findById(eventId);
        Event OGEvent = result.get();
        Event.merge(event, OGEvent);
        eventRepository.save(OGEvent);
        model.addAttribute("title", event.getName() + " Details");
        model.addAttribute("event", OGEvent);
        model.addAttribute("categories", eventCategoryRepository.findAll());


        return "events/details";
    }
}
