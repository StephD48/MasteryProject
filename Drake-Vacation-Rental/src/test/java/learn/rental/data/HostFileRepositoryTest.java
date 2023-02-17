package learn.rental.data;




import learn.rental.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/host-tests/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/host-tests/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }


    @Test
    void shouldFindAll() throws DataException {
        HostFileRepository repo = new HostFileRepository("./data/data/hosts.csv");
        List<Host> all = repo.findAll();
        assertEquals(1000, all.size());
    }
    @Test
    void shouldFindByEmail() throws DataException {
        Host email = repository.findByEmail("eyearnes0@sfgate.com");
        assertNotNull(email);

    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        Host result = repository.findByEmail("");
        assertNull(result);

    }

}