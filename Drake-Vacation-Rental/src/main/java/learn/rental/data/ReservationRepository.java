package learn.rental.data;


import learn.rental.models.Host;
import learn.rental.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByHost(String hostId);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation);

    Reservation delete(Reservation reservation);
}
