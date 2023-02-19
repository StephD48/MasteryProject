package learn.rental.data;


import learn.rental.models.Guest;
import learn.rental.models.Host;
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
    void shouldAddReservation() throws DataException {
        Host host = new Host();
        host.setHostId("1");
        Guest guest = new Guest();
        guest.setGuestId(1);
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(new BigDecimal("100"));

        Reservation result = repository.add(reservation);

        assertNotNull(result);
        assertEquals(1, result.getReservationId());


    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = repository.findByHost("1").get(0);
        reservation.setTotal(new BigDecimal("500"));

        boolean success = repository.update(reservation);

        assertTrue(success);
        Reservation updated = repository.findByHost("1").get(0);
        assertEquals(new BigDecimal("500"), updated.getTotal());
    }

    @Test
    void shouldNotUpdateReservation() throws DataException {

        Reservation reservation = new Reservation();
        reservation.setReservationId(99999);

        boolean success = repository.update(reservation);

        assertFalse(success);
    }

    @Test
    void shouldDelete() throws DataException {
        Reservation reservation = repository.findByHost("1").get(0);
        boolean success = repository.delete(reservation);

        assertTrue(success);
        assertFalse(repository.findByHost("1").contains(reservation));

    }
    @Test
    void shouldNotDeleteMissingReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(99999);

        boolean success = repository.delete(reservation);

        assertFalse(success);
    }









}