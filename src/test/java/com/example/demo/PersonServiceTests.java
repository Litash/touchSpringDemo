package com.example.demo;

import com.example.demo.dao.FakePersonDataAccessService;
import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PersonServiceTests {

    @TestConfiguration
    static class PersonServiceImplTestContextConfiguration {
        private final PersonDao personDao;

        PersonServiceImplTestContextConfiguration(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Bean
        public PersonService personService() {
            return new PersonService(personDao);
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private FakePersonDataAccessService fakeDaoMock;

    private UUID targetId;

    @Before
    public void setUp() {
        targetId = UUID.randomUUID();
        Person alex = new Person(targetId,"ash");

        Mockito.when(fakeDaoMock.selectPersonById(targetId))
                .thenReturn(java.util.Optional.of(alex));

        Mockito.when(fakeDaoMock.deletePersonById(targetId))
                .thenReturn(1);
    }

    @Test
    public void whenValidId_thenPersonShouldBeFound() {

        Optional<Person> found = personService.getPersonById(targetId);

        assertThat(found.isPresent());

        assertThat(found.get().getName()).isEqualTo("ash");
    }

    @Test
    public void deleteByIdSuccess() {

        int deleteResult = personService.deletePersonById(targetId);

        assertThat(deleteResult).isEqualTo(1);
    }
}
