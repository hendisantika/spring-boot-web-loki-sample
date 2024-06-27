package id.my.hendisantika.loki.controller;

import id.my.hendisantika.loki.entity.Person;
import org.instancio.Instancio;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-web-loki-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/27/24
 * Time: 07:04
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {
    private static final String API_PATH = "/persons";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void add() {
        Person person = restTemplate.postForObject(API_PATH, Instancio.create(Person.class), Person.class);
        assertNotNull(person);
        assertEquals(1, person.getId());
    }

    @Test
    @Order(2)
    void findAll() {
        Person[] persons = restTemplate.getForObject(API_PATH, Person[].class);
        assertTrue(persons.length > 0);
    }

    @Test
    @Order(2)
    void findById() {
        Person person = restTemplate.getForObject(API_PATH + "/{id}", Person.class, 1L);
        assertNotNull(person);
        assertEquals(1, person.getId());
    }

    @Test
    @Order(3)
    void update() {
        Person person = restTemplate.getForObject(API_PATH + "/{id}", Person.class, 1L);
        person.setFirstName("Updated");
        restTemplate.put("/persons/{id}", person, 1L);
        Person updatedPerson = restTemplate.getForObject("/persons/{id}", Person.class, 1L);
        assertEquals("Updated", updatedPerson.getFirstName());
    }

    @Test
    @Order(4)
    void delete() {
        restTemplate.delete(API_PATH + "/{id}", 1L);
        Person person = restTemplate.getForObject(API_PATH + "/{id}", Person.class, 1L);
        assertNull(person.getId());
    }
}
