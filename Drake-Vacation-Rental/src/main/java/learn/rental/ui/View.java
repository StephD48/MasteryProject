package learn.rental.ui;



import learn.rental.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class View {

    private final Scanner console = new Scanner(System.in);

    private final ConsoleIO io;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MenuOption displayMenuOption() {
        printHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MenuOption option : MenuOption.values()) {
            if (!option.isHidden()) {
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max);
        return MenuOption.fromValue(io.readInt(message, min, max));


    }

    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));

    }

    public void printMessage(String message) {
        System.out.println(message);
    }


    public void displayReservationsByHost(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No Reservations Found");
        }
        printHeader(MenuOption.VIEW_RESERVATIONS.getMessage());
        for (Reservation r : reservations) {
            io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n",
                    r.getReservationId(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getGuest().getFirstName(),
                    r.getGuest().getLastName(),
                    r.getGuest().getEmail());

        }
    }

    public String getEmail(boolean isHost) {
        String personType = isHost ? "Host" : "Guest";
        System.out.println("Enter a " + personType + " Email");
        return console.nextLine();
    }

    public void enterToContinue() {
        io.readString("Press [Enter} to continue");
    }

    public Reservation add() {
        Reservation reservation = new Reservation();
        reservation.setStartDate(io.readLocalDate("Enter a Start Date(MM/dd/yyyy): "));
        reservation.setEndDate(io.readLocalDate("Enter a End Date (MM/dd/yyyy): "));
        reservation.setTotal(io.readBigDecimal("Total Value:"));
        return reservation;
    }

    public Reservation updateReservation(Reservation reservation) {

        reservation.setStartDate(io.readLocalDate("Enter a Start Date (MM/dd/yyyy): "));
        reservation.setEndDate(io.readLocalDate("Enter a End Date (MM/dd/yyyy): "));
        reservation.setTotal(io.readBigDecimal("Total Value:"));
        return updateReservation(new Reservation());
    }

    public Reservation chooseReservation(List<Reservation> reservations) {
        displayReservationsByHost(reservations);

        int reservationId = io.readInt("Reservation ID: ");
        if(reservationId < 0){
            io.readString("That ID does not exist. Please enter a different ID ");
            return null;
        }
        for(Reservation reservation : reservations) {
            if(reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        io.printf("No reservation found with that ID " + reservationId);
        return null;
    }


    public void displayStatus(boolean success, List<String> messages) {
        printHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }


    public LocalDate getStartDate() {
        return io.readLocalDate("Please enter a Start Date (MM/dd/yyyy): ");
    }

    public LocalDate getEndDate() {
        return io.readLocalDate("Please enter an End Date (MM/dd/yyyy): ");
    }

    public String readRequiredString(String prompt) {
        return io.readRequiredString(prompt);
    }

    public void displayReservation(Reservation reservation) {
        printHeader("Summary");
        io.printf("Reservation with: %s%n Guest: %s%s%n Start Date: %s%n End Date: %s%n Total: $%s%n ",
                reservation.getHost().getLastName(),
                reservation.getGuest().getFirstName(), reservation.getGuest().getLastName(), reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotal());
    }

    public boolean confirmReservation() {
        return io.readBoolean("Would you like to complete the reservation? ");
    }

    public boolean confirmDelete() {
        return io.readBoolean("Would you like to Delete this reservation? ");
    }

    /*public Host chooseHost(List<Host> hosts) {
        io.println("");

        if (hosts.isEmpty()) {
            io.println("No Hosts Found");
            return null;
        }

        Host host = hosts.get(0);

        io.printf("[1] - %s %s%n",host.getLastName(),host.getEmail());
        io.println("[2] Exit");
        String message = "Select the host or exit [1-2]";

        int index = io.readInt(message, 1,2);
        if(index == 2) {
            return null;
        }
        return host;

    }*/




}
