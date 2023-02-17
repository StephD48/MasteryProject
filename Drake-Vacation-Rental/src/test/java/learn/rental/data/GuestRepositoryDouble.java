package learn.rental.data;

import learn.rental.models.Guest;
import learn.rental.models.Host;

import java.util.ArrayList;
import java.util.List;
;

public class GuestRepositoryDouble implements GuestRepository{




    private final List<Guest> guests = new ArrayList<>();

    @Override
    public List<Guest> findAll() {
        return guests;
    }


    @Override
    public Guest findByEmail(String email)  throws DataException {
        return findByEmail(email);
    }
}
