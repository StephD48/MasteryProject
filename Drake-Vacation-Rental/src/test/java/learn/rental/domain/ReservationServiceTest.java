package learn.rental.domain;

import learn.rental.data.GuestRepositoryDouble;
import learn.rental.data.HostRepositoryDouble;
import learn.rental.data.ReservationRepositoryDouble;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());

}