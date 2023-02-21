package learn.rental.data;


import learn.rental.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {


        reservations.add(new Reservation(
                1, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-12"),
                new BigDecimal("400"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663));

        reservations.add(new Reservation(
                2, LocalDate.parse("2023-09-08"), LocalDate.parse("2023-09-14"),
                new BigDecimal("1300"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_136));

        reservations.add(new Reservation(
                3, LocalDate.parse("2023-09-30"), LocalDate.parse("2023-10-02"),
                new BigDecimal("450"), HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_738));


    }

    @Override
    public List<Reservation> findByHost(String hostId) {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation add(Reservation reservation) {
        reservation.setReservationId(reservations.size() + 1);
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == reservation.getReservationId()) {
                r.setStartDate(reservation.getStartDate());
                r.setEndDate(reservation.getEndDate());
                r.setTotal(reservation.getTotal());
                r.setHost(reservation.getHost());
                r.setGuest(reservation.getGuest());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
            for (Reservation r : reservations) {
                if (r.getReservationId() == reservation.getReservationId()) {
                    reservations.remove(r);
                    return true;
                }
            }
            return false;
    }


}
