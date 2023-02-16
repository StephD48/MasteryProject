package learn.rental.domain;


import learn.rental.data.*;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }


    public List<Reservation> findByHost(String hostId) throws DataException {

        Map<Integer, Guest> guestMap = guestRepository.findAll().stream()
                .collect(Collectors.toMap(g -> g.getGuestId(), g -> g));
        Map<String, Host> hostMap = hostRepository.findAll().stream()
                .collect(Collectors.toMap(h -> h.getHostId(), h -> h));
        List<Reservation> result = reservationRepository.findByHost(hostId);
        for (Reservation reservation : result) {
            reservation.setGuest(guestMap.get(reservation.getGuest().getGuestId()));
            reservation.setHost(hostMap.get(reservation.getHost().getHostId()));

        }
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        //validateChildrenExist(reservation, result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Start Date is required.");
        }

        if (reservation.getEndDate() == null) {
            result.addErrorMessage("End Date is required.");
        }

        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {// No future dates.
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start Date cannot be in the past.");
        }

        if (reservation.getEndDate().isAfter(LocalDate.now())) {
            result.addErrorMessage("End Date cannot be before Start Date.");
        }

    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) throws DataException {

    }


}
