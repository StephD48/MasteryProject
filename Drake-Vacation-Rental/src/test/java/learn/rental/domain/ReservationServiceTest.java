package learn.rental.domain;

import learn.rental.data.*;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {



    private ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());




    @Test
    void shouldFindByHostId() throws DataException {
        List<Reservation> result = service.findByHost(HostRepositoryDouble.HOST.getHostId());
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void shouldAddReservation() throws DataException{


    }

    @Test
    void shouldNotAddNull() throws DataException {
        Result<Reservation> result = service.add(null);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddNullHost() {
        // TODO implement this test
    }

    // stretch goal?
    @Test
    void shouldNotAddUnknownHost() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddNullGuest() {
        // TODO implement this test
    }

    // stretch goal?
    @Test
    void shouldNotAddUnknownGuest() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddNullStartDate() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddNullEndDate() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddStartDateThatComesAfterEndDate() {
    }      // TODO implement this test

    @Test
    void shouldNotAddOverlappingDateRangeStartDate() {
    }   // TODO implement this test

    @Test
    void shouldNotAddOverlappingDateRangeEndDate() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddOverlappingDateRangeInside() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddOverlappingDateRangeOutside() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddOverlappingDateRangeSameDates() {
        // TODO implement this test
    }

    @Test
    void shouldNotAddPastStartDate() {
        // TODO implement this
    }

    @Test
    void shouldUpdate() {
        //TODO implement this
    }

    @Test
    void shouldNotUpdate() {
        // TODO implement this
    }

    @Test
    void shouldDelete() {
        // TODO implement this
    }

    @Test
    void shouldNotDelete() {

    }






}