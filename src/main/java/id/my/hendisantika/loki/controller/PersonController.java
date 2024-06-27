package id.my.hendisantika.loki.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import id.my.hendisantika.loki.entity.Person;
import id.my.hendisantika.loki.service.PersonCounterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
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
@Tag(name = "Person", description = "Endpoint for managing Person")
public class PersonController {

    private final Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final List<Person> persons = new ArrayList<>();

    private final PersonCounterService counterService;

    @GetMapping
    @Operation(
            summary = "Get All Person Data",
            description = "Get All Person Data.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public List<Person> findAll() {
        return persons;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Person Data By ID",
            description = "Get Person Data By ID.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Person findById(@PathVariable("id") Long id) {
        Person p = persons.stream().filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
        LabelMarker marker = LabelMarker.of("personId", () -> String.valueOf(p.getId()));
        LOG.info(marker, "Person successfully found");
        return p;
    }

    @GetMapping("/name/{firstName}/{lastName}")
    @Operation(
            summary = "Get All Person Data By First & LastName",
            description = "Get All Person Data By First & LastName.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public List<Person> findByName(@PathVariable("firstName") String firstName,
                                   @PathVariable("lastName") String lastName) {
        return persons.stream().filter(it -> it.getFirstName().equals(firstName)
                        && it.getLastName().equals(lastName))
                .toList();
    }

    @PostMapping
    @Operation(
            summary = "Add New Person Data",
            description = "Add New Person Data.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public Person add(@RequestBody Person p) {
        p.setId((long) (persons.size() + 1));
        LabelMarker marker = LabelMarker.of("personId", () -> String.valueOf(p.getId()));
        LOG.info(marker, "New person successfully added");
        persons.add(p);
        counterService.countNewPersons();
        return p;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Person Data",
            description = "Delete Person Data.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public void delete(@PathVariable("id") Long id) {
        Person p = persons.stream().filter(it -> it.getId().equals(id)).findFirst().orElseThrow();
        persons.remove(p);
        LabelMarker marker = LabelMarker.of("personId", () -> String.valueOf(id));
        LOG.info(marker, "Person successfully removed");
        counterService.countDeletedPersons();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update Person Data",
            description = "Update Person Data.",
            tags = {"Person"})
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            Book.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Not found", responseCode = "404",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Internal error", responseCode = "500"
                    , content = @Content)
    }
    )
    public void update(@RequestBody Person p) {
        Person person = persons.stream()
                .filter(it -> it.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow();
        persons.set(persons.indexOf(person), p);
        LabelMarker marker = LabelMarker.of("personId", () -> String.valueOf(p.getId()));
        LOG.info(marker, "Person successfully updated");
    }
}
