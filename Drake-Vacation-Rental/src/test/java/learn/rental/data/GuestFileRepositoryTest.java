package learn.rental.data;

import learn.rental.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/guest-tests/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guest-tests/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByEmail() throws DataException {
        Guest email = repository.findByEmail("slomas0@mediafire.com");
        assertNotNull(email);

    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        Guest result = repository.findByEmail("");
        assertNull(result);

    }

}