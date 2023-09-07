package ru.iliamalafeev.springcourse.project2SpringBoot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.iliamalafeev.springcourse.project2SpringBoot.models.Person;
import ru.iliamalafeev.springcourse.project2SpringBoot.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person validatedPerson = (Person) target;
        Optional<Person> existingPerson = peopleService.findByFullName(validatedPerson.getFullName());

        if (existingPerson.isPresent() && existingPerson.get().getId() != validatedPerson.getId()) {
            errors.rejectValue("fullName", "", "Person with this full name is already registered");
        }
    }
}
