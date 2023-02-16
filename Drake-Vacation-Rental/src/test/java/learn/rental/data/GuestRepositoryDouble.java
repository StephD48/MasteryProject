package learn.rental.data;

import learn.rental.models.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository{

    GuestRepositoryDouble repository = new GuestRepositoryDouble();

    private final ArrayList<Guest> guests = new ArrayList<>();

    @Override
    public List<Guest> findAll() {
        return guests;
    }


    @Override
    public Guest findByEmail(String email)  throws DataException {
        return repository.findByEmail(email);
    }
}
