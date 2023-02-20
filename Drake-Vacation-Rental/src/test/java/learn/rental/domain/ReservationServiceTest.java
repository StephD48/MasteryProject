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
        ReservationRepositoryDouble repository = new ReservationRepositoryDouble();
        int initialSize = repository.findByHost(HostRepositoryDouble.HOST.getHostId()).size();
        Reservation reservation = new Reservation(
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                new BigDecimal("100"),
                HostRepositoryDouble.HOST,
                GuestRepositoryDouble.GUEST_663
        );
        Reservation addedReservation = repository.add(reservation);
        List<Reservation> all = repository.findByHost(HostRepositoryDouble.HOST.getHostId());

        assertNotNull(addedReservation);
        assertEquals(initialSize + 1, all.size());
        assertTrue(all.contains(addedReservation));
    }


    @Test
    void shouldNotAddNull() throws DataException {
        Result<Reservation> result = service.add(null);
        assertNotNull(result);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddNullHost() {
        //TODO implement test
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
    void shouldUpdateReservation() throws DataException {
        Reservation reservationToUpdate = new Reservation(
                1, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-12"),
                new BigDecimal("400"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663);

        ReservationRepository repository = new ReservationRepositoryDouble();
        repository.add(reservationToUpdate);

        Reservation updatedReservation = new Reservation(
                1, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-14"),
                new BigDecimal("500"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663);

        boolean success = repository.update(updatedReservation);
        assertTrue(success);

        List<Reservation> all = repository.findByHost(HostRepositoryDouble.HOST.getHostId());
        assertEquals(3, all.size());
    }

    @Test
    void shouldNotUpdateReservation() throws DataException {
        Reservation reservationToUpdate = new Reservation(
                1, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-12"),
                new BigDecimal("400"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663);

        ReservationRepository repository = new ReservationRepositoryDouble();
        repository.add(reservationToUpdate);

        Reservation updatedReservation = new Reservation(
                999, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-14"),
                new BigDecimal("500"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663);

        boolean success = repository.update(updatedReservation);
        assertFalse(success);
        List<Reservation> all = repository.findByHost(HostRepositoryDouble.HOST.getHostId());
        assertEquals(3, all.size());

    }


    @Test
    void shouldDelete() throws DataException {
        ReservationRepository repository = new ReservationRepositoryDouble();
        Reservation reservationToDelete = repository.findByHost(HostRepositoryDouble.HOST.getHostId()).get(0);

        boolean success = repository.delete(reservationToDelete);
        assertTrue(success);

        assertEquals(2, repository.findByHost(HostRepositoryDouble.HOST.getHostId()).size());
        assertFalse(repository.findByHost(HostRepositoryDouble.HOST.getHostId()).contains(reservationToDelete));

    }

    @Test
    void shouldNotDeleteMissingReservation() throws DataException {
        ReservationRepository repository = new ReservationRepositoryDouble();
        Reservation reservation = new Reservation(
                4, LocalDate.parse("2023-11-01"), LocalDate.parse("2023-11-03"),
                null, HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663);

        boolean success = repository.delete(reservation);
        assertFalse(success);

        assertEquals(3, repository.findByHost(HostRepositoryDouble.HOST.getHostId()).size());
    }




}