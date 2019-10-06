package com.example.demo;

import com.example.demo.dao.FakePersonDataAccessService;
import com.example.demo.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PersonServiceTests {
    @MockBean
    private FakePersonDataAccessService fakeDaoMock;

    private UUID targetId;
    @Before
    public void setUp() {
        targetId = UUID.randomUUID();
        Person alex = new Person(targetId,"ash");

        Mockito.when(fakeDaoMock.selectPersonById(targetId))
                .thenReturn(java.util.Optional.of(alex));
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        Optional<Person> found = fakeDaoMock.selectPersonById(targetId);

        assertThat(found.isPresent());

        assertThat(found.get().getName()).isEqualTo("ash");
    }
}
