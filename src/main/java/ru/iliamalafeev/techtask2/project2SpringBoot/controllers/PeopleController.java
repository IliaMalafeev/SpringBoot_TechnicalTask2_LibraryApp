package ru.iliamalafeev.techtask2.project2SpringBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.iliamalafeev.techtask2.project2SpringBoot.models.Person;
import ru.iliamalafeev.techtask2.project2SpringBoot.services.BooksService;
import ru.iliamalafeev.techtask2.project2SpringBoot.services.PeopleService;
import ru.iliamalafeev.techtask2.project2SpringBoot.utils.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String showAllPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/all_people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("books_list", booksService.findAllByHolder(person));
        return "people/person";
    }

    @GetMapping("/new")
    public String showNewPersonForm(@ModelAttribute("person") Person person) {
        return "people/new_person_form";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new_person_form";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showPersonUpdateForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit_person_form";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult,
                             @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/edit_person_form";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
