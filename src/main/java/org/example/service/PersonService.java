package org.example.service;

import org.example.entity.Person;
import org.example.repository.PersonRepository;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPerson(long authorId) {
        return personRepository.findById(authorId).get();
    }
}
