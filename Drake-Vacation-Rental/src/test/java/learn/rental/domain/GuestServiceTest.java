package learn.rental.domain;

import learn.rental.data.DataException;
import learn.rental.data.GuestRepositoryDouble;

import learn.rental.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @BeforeEach
    void setup() {
        GuestRepositoryDouble repository = new GuestRepositoryDouble();
        service = new GuestService(repository);
    }

    @Test
    void shouldFindByEmail() {

    }

    @Test
    void shouldNotFindNullEmail(){

    }








}