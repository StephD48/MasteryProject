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

import java.util.List;


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
        if(host == null) {
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
        Reservation reservation = view.addReservation();
        Result<Reservation> result = reservationService.add(reservation);
        if(!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        }else{
            String message = String.format("Reservation %s created.", result.getPayload().getReservationId());
            view.displayStatus(true, message);
        }



    }

    private void editReservation() throws DataException {
        view.printHeader(MenuOption.EDIT_RESERVATION.getMessage());
        /*List<Panel> panels = service.findAll();
        Panel panel = view.selectPanel(panels);
        if (panel != null) {
            Panel updatedPanel = view.updatePanel(panel);
            PanelResult result = service.updatePanel(updatedPanel);
            view.printResult(result, "Panel Id %s updated.%n");
        }*/
    }

    private void deleteReservation() throws DataException {
        view.printHeader(MenuOption.DELETE_RESERVATION.getMessage());
        /*List<Panel> panels = service.findAll();
        Panel panel =view.selectPanel(panels);
        if(panel != null) {
            PanelResult result = service.deleteById(panel.getPanelId());
            result.setPayload(panel);
            view.printResult(result,"Panel has been deleted");
        }*/

    }

}


