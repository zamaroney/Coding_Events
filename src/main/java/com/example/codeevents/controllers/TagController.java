package com.example.codeevents.controllers;

import com.example.codeevents.data.EventTagRepository;
import com.example.codeevents.models.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private EventTagRepository eventTagRepository;
    @GetMapping
    public String displayAllTags(Model model) {
        model.addAttribute("title", "All Tags");
        model.addAttribute("tags", eventTagRepository.findAll());
        return "tags/index";
    }

    // located at /events/create
    @GetMapping("create")
    public String renderCreateTagForm(Model model) {
        model.addAttribute("title", "Create Tags");
        model.addAttribute(new Tag());
        return "tags/create";
    }

    @PostMapping("create")
    public String processCreateTagForm(@ModelAttribute @Valid Tag newTag,
                                                 Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Tag");
            model.addAttribute(newTag);
            return "tags/create";
        }

        eventTagRepository.save(newTag);
        return "redirect:";
    }
}
