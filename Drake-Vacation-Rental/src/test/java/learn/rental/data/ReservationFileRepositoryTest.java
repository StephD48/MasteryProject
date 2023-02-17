package learn.rental.data;


import learn.rental.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation_data_test/2e72f86c-b8fe-4265-b4f1-304dea8762db-seed.csv";
    static final String TEST_FILE_PATH= "./data/reservation_data_test/2e72f86c-b8fe-4265-b4f1-304dea8762db.csv";

    static final String TEST_DIR__PATH ="./data/reservation_data_test";

    final String hostId = "2e72f86c-b8fe-4265-b4f1-304dea8762db";


    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR__PATH);


    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);

    }
    @Test
    void shouldFindByHost()  {
        List<Reservation> result = repository.findByHost(hostId);
        assertNotNull(result);
        assertEquals(12,result.size());

    }
    @Test
    void shouldNotFindByUnknownHost() {
        List<Reservation> result = repository.findByHost("sfhfsfh");
        assertNotNull(result);
        assertEquals(0,result.size());
    }



    @Test
    void shouldAddReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1234);
        reservation.setStartDate(LocalDate.of(2023,5,1));
        reservation.setEndDate(LocalDate.of(2023,5,15));
        reservation.setGuestId("123");
        reservation.setTotal(BigDecimal.valueOf(1000));


    }

    @Test
    void shouldUpdate() {

    }

    @Test
    void shouldNotUpdateReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(999999);

        boolean result = repository.update(reservation);
        assertFalse(result);
    }

    @Test
    void shouldDeleteById() {

    }
    @Test
    void shouldNotDeleteUnknown(){
        assertFalse(repository.deleteById(9999999));
    }









}