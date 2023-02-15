package learn.rental.ui;


import learn.rental.data.DataException;
import org.springframework.stereotype.Component;


@Component
public class Controller {

    //private final PanelService service;

    private final View view;

    public Controller(View view) {
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
    }




    private void addReservation() throws DataException {
        view.printHeader(MenuOption.ADD_RESERVATION.getMessage());
        /*Panel panel = view.createPanel();
        PanelResult result = service.addPanel(panel);
        if (result.isSuccess()) {
            view.printResult(result, "Panel Id %s added.%n");
        } else {
            view.displayError(result.getMessages());
        }*/
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


