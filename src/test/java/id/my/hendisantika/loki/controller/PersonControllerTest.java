package id.my.hendisantika.loki.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

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

}
