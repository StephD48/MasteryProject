package learn.rental.domain;

import learn.rental.data.*;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private ReservationFileRepository repository = new ReservationFileRepository("./data/data/reservations");

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());


    @Test
    void shouldFindByHost() throws DataException {
        String hostId = "3edda6bc-ab95-49a8-8962-d50b53f84b15";
        List<Reservation> actual = service.findByHost(hostId);
        assertNotNull(actual);



    }


    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1234);
        reservation.setStartDate(LocalDate.of(2023,5,1));
        reservation.setEndDate(LocalDate.of(2023,5,15));
        reservation.setGuestId("123");
        reservation.setTotal(BigDecimal.valueOf(1000));

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());



    }

}