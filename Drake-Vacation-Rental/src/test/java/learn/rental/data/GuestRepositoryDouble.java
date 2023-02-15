package learn.rental.data;

import learn.rental.models.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository{

    private final ArrayList<Guest> guests = new ArrayList<>();
    @Override
    public List<Guest> findAll() {
        return guests;
    }


    @Override
    public List<Guest> findByEmail(String email)  throws DataException {
        return guests.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}
