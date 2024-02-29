package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    //index method
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }
        skillRepository.save(newSkill); // Save the new skill
        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    //method retrieves a skill by its ID from the SkillRepository using the findById
    public String displayViewSkill(Model model, @PathVariable int skillId) { // Change parameter type to int
        Skill skill = skillRepository.findById((long) skillId).orElse(null); // Cast int to long
        if (skill != null) {
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:/";
        }
    }
}

