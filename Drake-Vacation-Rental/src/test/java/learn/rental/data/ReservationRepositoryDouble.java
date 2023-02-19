package learn.rental.data;


import learn.rental.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    private  List<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() {


        reservations.add(new Reservation(
                1, LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-12"),
                new BigDecimal("400"),HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_663));

        reservations.add(new Reservation(
                2, LocalDate.parse("2023-09-08"), LocalDate.parse("2023-09-14"),
                new BigDecimal("1300"),HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_136));

        reservations.add(new Reservation(
                3, LocalDate.parse("2023-09-30"), LocalDate.parse("2023-10-02"),
                new BigDecimal("450"),HostRepositoryDouble.HOST, GuestRepositoryDouble.GUEST_738));


    }

    @Override
    public List<Reservation> findByHost(String hostId) {
        return reservations.stream()
                //.filter(r ->r.getHostId().equals(hostId))
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
    public boolean delete(Reservation reservation) throws DataException {
        return false;
    }


}
