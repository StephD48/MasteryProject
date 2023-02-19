package learn.rental.data;

import learn.rental.models.Guest;
import learn.rental.models.Host;

import java.util.ArrayList;
import java.util.List;
;

public class GuestRepositoryDouble implements GuestRepository{


    public static final Guest GUEST_663 = new Guest(663, "Wallis", "Kuhl",
            "wkuhlie@patch.com", "(704) 3740857", "NC");

    public static final Guest GUEST_136 = new Guest(136,"Nert","Detoile",
            "ndetoile3r@yahoo.co.jp","(202) 5472570","DC");

    public static final Guest GUEST_738 = new Guest(738,"Annamarie","Inman",
            "ainmankh@example.com","(314) 6947914","MO");
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
