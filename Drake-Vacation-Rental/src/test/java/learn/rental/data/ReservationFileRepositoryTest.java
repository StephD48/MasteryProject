package learn.rental.data;

import learn.rental.domain.ReservationService;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation_data_test/reservation-seed.csv";
    static final String TEST_FILE_PATH= "./data/reservation_data_test/reservation-test.csv";
    static final String TEST_DIR_PATH = "./data/reservation_data_test";


    private ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    //private ReservationService service = new ReservationService(serviceRepository);


    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

    }
    @Test
    void shouldFindByHost() {
        Host testHost = new Host();
        testHost.setHostId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
       // List<Reservation> reservations = service.findByHost(testHost);
    }






}