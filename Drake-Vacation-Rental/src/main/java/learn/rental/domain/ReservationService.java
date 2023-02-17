package learn.rental.domain;


import learn.rental.data.*;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
        if(!result.isSuccess()) {
            return result;
        }
        reservation.setGuest(guestRepository.findByEmail(reservation.getGuestId()));
        reservation.setHost(hostRepository.findByEmail(reservation.getHostId()));
        reservation.setTotal(calculateTotalCost(reservation.getHostId(), reservation));
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    private BigDecimal calculateTotalCost(String hostId, Reservation reservation) throws DataException {
        Host host  = hostRepository.findByEmail(hostId);
        BigDecimal standardRate = host.getStandardRate();
        BigDecimal weekendRate = host.getWeekendRate();

        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        ArrayList<LocalDate> weekend = new ArrayList<>();
        ArrayList<LocalDate> weekday = new ArrayList<>();

        LocalDate date = startDate;
        while((date.getDayOfWeek() != DayOfWeek.FRIDAY && date.getDayOfWeek() != DayOfWeek.SATURDAY) && date != endDate) {
            weekday.add(date);
        }

        date = startDate;
        while((date.getDayOfWeek() == DayOfWeek.FRIDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) && date != endDate){
            weekend.add(date);
        }
        BigDecimal weekdayPrice = standardRate.multiply(BigDecimal.valueOf(weekday.size()));
        BigDecimal weekendPrice = weekendRate.multiply(BigDecimal.valueOf(weekend.size()));

        return weekdayPrice.add(weekendPrice);
    }


    private Result<Reservation> validate(Reservation reservation) {

        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }


        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation.getHostId() == null) {
            result.addErrorMessage("Host is required");
            return result;
        }
       if(reservation.getGuest().getEmail() == null) {
            result.addErrorMessage("Guest Required");
        }
        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Start Date is required.");
        }

        if (reservation.getEndDate() == null) {
            result.addErrorMessage("End Date is required.");
        }


        return result;
    }

    private void validateFields(Reservation reservation, Result<Reservation> result) {
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Start Date cannot be in the past.");
        }
        if (reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("End Date cannot be before Start Date.");
        }

        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        List<Reservation> reservations = reservationRepository.findByHost(reservation.getHostId());
        for(Reservation res : reservations) {
            LocalDate existingStartDate = res.getStartDate();
            LocalDate existingEndDate = res.getEndDate();

            if(startDate.isBefore(existingStartDate) && endDate.isAfter(startDate)) {
                result.addErrorMessage("The End Date overlaps the with another reservation. Please choose another End Date");
            }
            if(startDate.isAfter(existingStartDate) && startDate.isBefore(existingEndDate)) {
                result.addErrorMessage("The Start Date overlaps the with another reservation. Please choose another Start Date");
            }
            if(startDate.isEqual(existingStartDate)) {
                result.addErrorMessage("There is another reservation with that Start Date. Please choose a different Start Date");
            }
        }

    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) throws DataException {
        Guest guest = guestRepository.findByEmail(reservation.getGuestId());
        if(guest == null) {
            result.addErrorMessage("Guest does not exist");

        }
        reservation.setGuest(guest);

        Host host = hostRepository.findByEmail(reservation.getHostId());
        if(host == null){
            result.addErrorMessage("Host does not exist");
        }




    }


}
