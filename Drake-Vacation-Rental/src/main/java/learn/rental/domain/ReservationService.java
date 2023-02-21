package learn.rental.domain;


import learn.rental.data.*;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
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

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public BigDecimal calculateTotalCost(Reservation reservation) throws DataException {
        Host host = hostRepository.findByEmail(reservation.getHost().getEmail());
        BigDecimal standardRate = host.getStandardRate();
        BigDecimal weekendRate = host.getWeekendRate();

        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        BigDecimal total = BigDecimal.ZERO;
        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            DayOfWeek day = startDate.getDayOfWeek();
            if (day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) {
                total = total.add(weekendRate);
            } else {
                total = total.add(standardRate);
            }
            startDate = startDate.plusDays(1);
        }
        return total;

    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        if (reservation.getHost().getHostId() == null) {
            result.addErrorMessage("No host found");
        }
        if (result.isSuccess()) {
            if (reservationRepository.update(reservation)) {
                result.setPayload(reservation);
            } else {
                String message = String.format("No Reservation found", reservation.getHost().getHostId());
                result.addErrorMessage(message);
            }
        }
        return result;

    }

    private Result<Reservation> validate(Reservation reservation) throws DataException {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);
        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }
        if (reservation.getHost() == null || reservation.getHost().getEmail() == null) {
            result.addErrorMessage("Host is required.");
        }
        if (reservation.getGuest() == null || reservation.getGuest().getEmail() == null) {
            result.addErrorMessage("Guest is required.");
        }
        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Dates are required.");
        }

        return result;
    }

    private void validateFields(Reservation newReservation, Result<Reservation> result) {
        LocalDate startDate = newReservation.getStartDate();
        LocalDate endDate = newReservation.getEndDate();
        List<Reservation> reservations = reservationRepository.findByHost(newReservation.getHost().getHostId());


        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            result.addErrorMessage("Dates must be today or a future date.");
        }


        if (startDate.isAfter(endDate)) {
            result.addErrorMessage("Start date must be before end date.");
        }


        for (Reservation existingReservation : reservations) {
            LocalDate existingStartDate = existingReservation.getStartDate();
            LocalDate existingEndDate = existingReservation.getEndDate();

            if (newReservation.getReservationId() != 0 && newReservation.getReservationId()
                    == (existingReservation.getReservationId())) {
                continue;
            }

            if (startDate.isEqual(existingStartDate) || endDate.isEqual(existingEndDate) ||
                    (startDate.isAfter(existingStartDate) && startDate.isBefore(existingEndDate)) ||
                    (endDate.isAfter(existingStartDate) && endDate.isBefore(existingEndDate))) {
                result.addErrorMessage("Reservation dates overlap with another reservation. Please choose different dates.");
            }
        }
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) throws DataException {

        if (reservation.getHost().getHostId() == null || reservation.getHost().getEmail() == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (reservation.getGuest().getEmail() == null || guestRepository.findByEmail(reservation.getGuest()
                .getEmail()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }

    }

    public Result delete(Reservation reservation) throws DataException {
        Result result = new Result();
        if (!reservationRepository.delete(reservation)) {
            String message = String.format("No panel to delete matching the id %d", reservation);
            result.addErrorMessage(message);
        }
        return result;
    }
}



