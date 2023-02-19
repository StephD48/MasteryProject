package learn.rental.ui;


import learn.rental.data.DataException;
import learn.rental.domain.GuestService;
import learn.rental.domain.HostService;
import learn.rental.domain.ReservationService;
import learn.rental.domain.Result;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;

    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.printHeader("Welcome to Drake Vacation Rental");

        try {
            runMenu();
        } catch (DataException ex) {
            view.printHeader("Fatal Error");
        }
    }

    private void runMenu() throws DataException {
        MenuOption option;
        do {
            option = view.displayMenuOption();

            switch (option) {
                case VIEW_RESERVATIONS:
                    viewReservation();
                    break;
                case ADD_RESERVATION:
                    addReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case DELETE_RESERVATION:
                    deleteReservation();
                    break;
            }

        } while (option != MenuOption.EXIT);
        view.printHeader("Goodbye");
    }

    private void viewReservation() throws DataException {
        view.printHeader(MenuOption.VIEW_RESERVATIONS.getMessage());
        Host host = getHost();
        if (host == null) {
            view.printMessage("No Host Found");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host.getHostId());
        view.displayReservationsByHost(reservations);
        view.enterToContinue();
    }


    private Host getHost() throws DataException {
        String email = view.getEmail(true);
        Host host = hostService.findByEmail(email);
        return host;
    }

    private Guest getGuest() throws DataException {
        String email = view.getEmail(true);
        Guest guest = guestService.findByEmail(email);
        return guest;
    }


    private void addReservation() throws DataException {
        view.printHeader(MenuOption.ADD_RESERVATION.getMessage());
        Reservation reservation = new Reservation();

        String guestEmail = view.readRequiredString("Please enter the guest email: ");
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.printMessage("Guest not found");
            return;
        }
        String hostEmail = view.readRequiredString("Please enter a host email: ");
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printMessage("Host not found");
            return;
        }
        List<Reservation> reservations = reservationService.findByHost(host.getHostId());
        view.displayReservationsByHost(reservations);
        LocalDate start = view.getStartDate();
        LocalDate end = view.getEndDate();
        reservation.setHost(host);
        reservation.setGuest(guest);
        reservation.setStartDate(start);
        reservation.setEndDate(end);
        BigDecimal total = reservationService.calculateTotalCost(reservation);
        reservation.setTotal(total);
        view.displayReservation(reservation);

        boolean isOk = view.confirmReservation();

        Result<Reservation> result = new Result();
        if (isOk) {
            result = reservationService.add(reservation);
            if (result.isSuccess()) {
                String message = String.format("Reservation %s created.",
                        result.getPayload().getReservationId());
                view.displayStatus(true, message);
            } else {
                view.displayStatus(false, result.getErrorMessages().get(0));
            }
        } else {
            view.displayStatus(false, "Reservation cancelled");
        }
    }

    private void editReservation() throws DataException {
        view.printHeader(MenuOption.EDIT_RESERVATION.getMessage());
        Result<Reservation> results = new Result<>();
        String guestEmail = view.readRequiredString("Please enter the guest email: ");
        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.printMessage("Guest not found");
            return;
        }
        String hostEmail = view.readRequiredString("Please enter a host email: ");
        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printMessage("Host not found");
            return;
        }

        List<Reservation> reservations = reservationService.findByHost(host.getHostId());
        List<Reservation> guestReservations = reservations.stream()
                .filter(r -> r.getGuest().getEmail().equals(guestEmail))
                .collect(Collectors.toList());
        if (guestReservations.isEmpty()) {
            results.addErrorMessage("No Reservations found for this guest with this host");
        }

        int reservationId = view.chooseReservation(guestReservations).getReservationId();
        Reservation reservation = guestReservations.stream()
                .filter(r -> r.getReservationId() == reservationId)
                .findFirst()
                .orElse(null);
        if (reservation == null) {
            results.addErrorMessage("Reservation not found");
        }

        LocalDate newStartDate = view.getStartDate();
        LocalDate newEndDate = view.getEndDate();
        BigDecimal newTotal = reservationService.calculateTotalCost(reservation);

        reservation.setStartDate(newStartDate);
        reservation.setEndDate(newEndDate);
        reservation.setTotal(newTotal);
        view.displayReservation(reservation);

        boolean isOk = view.confirmReservation();


        if (isOk) {
            results = reservationService.update(reservation);
            if (results.isSuccess()) {
                String message = String.format("Reservation %s updated.",
                        results.getPayload().getReservationId());
                view.displayStatus(true, message);
            } else {
                view.displayStatus(false, results.getErrorMessages().get(0));
            }
        } else {
            view.displayStatus(false, "Reservation cancelled");
        }
    }

    private boolean deleteReservation() throws DataException {
        view.printHeader(MenuOption.DELETE_RESERVATION.getMessage());
        String guestEmail = view.readRequiredString("Please enter the guest email: ");
        String hostEmail = view.readRequiredString("Please enter  the host email: ");

        Guest guest = guestService.findByEmail(guestEmail);
        if (guest == null) {
            view.printMessage("Guest not found");
            return false;
        }

        Host host = hostService.findByEmail(hostEmail);
        if (host == null) {
            view.printMessage("Host not found");
            return false;
        }

        List<Reservation> reservations = reservationService.findByHost(host.getHostId());
        List<Reservation> guestReservations = reservations.stream()
                .filter(r -> r.getGuest().getEmail().equals(guestEmail))
                .collect(Collectors.toList());

        if (guestReservations.isEmpty()) {
            view.printMessage("No reservations found for this guest with this host");
            return false;
        }

        Reservation reservation = view.chooseReservation(guestReservations);
        boolean isOk = view.confirmDelete();

        if (isOk) {
            Result result = reservationService.delete(reservation);
            if (result.isSuccess()) {
                view.printMessage("Reservation deleted");
                return true;
            } else {
                view.printMessage(result.getErrorMessages().get(0));
            }
        } else {
            view.printMessage("Reservation deletion cancelled");
        }
        return false;
    }


}




