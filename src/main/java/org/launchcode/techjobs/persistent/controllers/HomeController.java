package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import java.util.Optional;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.launchcode.techjobs.persistent.models.Skill;
import java.util.List;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;


    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll()); // Fetch employers and add them to the model
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, @RequestParam Long employerId,
                                    @RequestParam List<Long> skillIds, // Change the parameter type to List<Long>
                                    Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers", employerRepository.findAll());
            return "add";
        }

        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        Employer employer = optionalEmployer.orElse(null);

        if (employer != null) {
            //retrieves skills from the database using skill IDs
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skillIds);

            //sets the retrieved skills to the job
            newJob.setSkills(skillObjs);

            newJob.setEmployer(employer);
            jobRepository.save(newJob);
            return "redirect:";
        } else {
            return "redirect:add";
        }
    }
}


