package learn.rental.data;

import learn.rental.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/guest-host-tests/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guest-host-tests/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);


    @Test
    void shouldFindAll() throws DataException {
        GuestFileRepository repo = new GuestFileRepository("./data/guests.csv");
        List<Guest> all = repo.findAll();
        assertEquals(1000, all.size());
    }

}