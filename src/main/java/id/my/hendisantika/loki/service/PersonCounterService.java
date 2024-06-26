package id.my.hendisantika.loki.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-web-loki-sample
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/27/24
 * Time: 06:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PersonCounterService {

    private final Counter counterAdded;
    private final Counter counterDeleted;

    @Autowired
    public PersonCounterService(MeterRegistry registry) {
        this.counterAdded = registry.counter("services.person.add");
        this.counterDeleted = registry.counter("services.person.deleted");
    }
}
