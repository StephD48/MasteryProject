package learn.rental.ui;

public enum MenuOption {

    VIEW_RESERVATIONS(1,"View Reservations",false),
    ADD_RESERVATION(2,"Add a Reservation",false),
    EDIT_RESERVATION(3,"Edit a Reservation",false),
    DELETE_RESERVATION(4,"Delete a Reservation",false),
    EXIT(5,"Exit",false);

    private int value;
    private String message;
    private boolean hidden;

    private MenuOption(int value, String message, boolean hidden) {
        this.value = value;
        this.message = message;
        this.hidden = hidden;
    }

    public static MenuOption fromValue(int value) {
        for (MenuOption option : MenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public boolean isHidden() {
        return hidden;
    }

}
