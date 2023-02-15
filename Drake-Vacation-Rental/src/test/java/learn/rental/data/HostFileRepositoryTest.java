package learn.rental.data;


import learn.rental.models.Guest;
import learn.rental.models.Host;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);


    @Test
    void shouldFindAll() throws DataException {
        HostFileRepository repo = new HostFileRepository("./data/hosts.csv");
        List<Host> all = repo.findAll();
        assertEquals(1000, all.size());
    }

    @Test
    void shouldFindByEmail() throws DataException {
        List<Host> email = repository.findByEmail("");
        assertNotNull(email);

    }

    @Test
    void shouldNotFindByEmail() throws DataException {
        List<Host> result = repository.findByEmail("");
        assertNotNull(result);

    }
}