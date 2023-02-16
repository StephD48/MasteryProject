package learn.rental.domain;

import learn.rental.data.DataException;
import learn.rental.data.GuestRepositoryDouble;

import learn.rental.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service;

    @BeforeEach
    void setup() {
        GuestRepositoryDouble repository = new GuestRepositoryDouble();
        service = new GuestService(repository);
    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        Guest result = service.findByEmail("");
        assertNotNull(result);

    }
    @Test
    void shouldFindByEmail() throws DataException {
        Guest email = service.findByEmail("chloed@.com");
        assertNotNull(email);

    }





}