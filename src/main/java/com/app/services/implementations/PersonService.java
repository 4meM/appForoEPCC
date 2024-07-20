package com.app.services.implementations;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.domain.user.Person;
import com.app.repositories.PersonRepositoryImp;
import com.app.services.interfaces.IPersonService;

@Service
public class PersonService implements IPersonService{
  @Autowired
  private PersonRepositoryImp personRepository;

  @Override
  public Person createPerson(String firstName, String lastName, String email, LocalDate birthDay) {
    Person newPerson = Person.builder()
                             .firstName(firstName)
                             .lastName(lastName)
                             .email(email)
                             .dateOfBirthDay(birthDay)
                             .build();

    return personRepository.save(newPerson);
    
  }

}
