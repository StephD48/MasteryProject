package learn.rental.data;

import learn.rental.models.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findByHost(String hostId) {
        return reservations.stream()
                .filter(r ->r.getHostId().equals(hostId))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) {
       reservations.add(reservation);
       return reservation;
    }

    @Override
    public boolean update(Reservation reservation) {
        return false;
    }

    @Override
    public Reservation deleteById(int reservationId) {
        return null;
    }
}
