package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.ArrayList;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Employer> employerIterable = employerRepository.findAll();
        List<Employer> employers = new ArrayList<>();
        employerIterable.forEach(employers::add);
        model.addAttribute("title", "All Employers");
        model.addAttribute("employers", employers);
        return "employers/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute("employer", new Employer());
        return "employers/add";
    }

    //displayViewEmployer method
    @GetMapping("/view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {
        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        if (optionalEmployer.isPresent()) {
            Employer employer = optionalEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:/employers";
        //redirects to the employer's index page if employer is not found
        }
    }
    //    @GetMapping("add")
//    public String displayAddEmployerForm(Model model) {
//        model.addAttribute(new Employer());
//        return "employers/add";
//    }
//}
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }
        employerRepository.save(newEmployer); // saves the new employer
        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable Long employerId) {
        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
