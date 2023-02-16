package learn.rental.domain;

import learn.rental.data.DataException;
import learn.rental.data.HostRepositoryDouble;
import learn.rental.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setup() {
        HostRepositoryDouble repository = new HostRepositoryDouble();
        service = new HostService(repository);
    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        Host result = service.findByEmail("");
        assertNotNull(result);

    }
    @Test
    void shouldFindByEmail() throws DataException {
        Host email = service.findByEmail("chloed@.com");
        assertNotNull(email);

    }

}