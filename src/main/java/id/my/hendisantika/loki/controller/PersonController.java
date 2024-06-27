package id.my.hendisantika.loki.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import id.my.hendisantika.loki.entity.Person;
import id.my.hendisantika.loki.service.PersonCounterService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-web-loki-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/27/24
 * Time: 07:00
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final List<Person> persons = new ArrayList<>();

    private final PersonCounterService counterService;

    @GetMapping
    public List<Person> findAll() {
        return persons;
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        Person p = persons.stream().filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
        LabelMarker marker = LabelMarker.of("personId", () -> String.valueOf(p.getId()));
        LOG.info(marker, "Person successfully found");
        return p;
    }

    @GetMapping("/name/{firstName}/{lastName}")
    public List<Person> findByName(@PathVariable("firstName") String firstName,
                                   @PathVariable("lastName") String lastName) {
        return persons.stream().filter(it -> it.getFirstName().equals(firstName)
                        && it.getLastName().equals(lastName))
                .toList();
    }
}
