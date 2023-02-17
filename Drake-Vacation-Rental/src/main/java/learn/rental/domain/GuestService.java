package learn.rental.domain;

import learn.rental.data.DataException;
import learn.rental.data.GuestRepository;
import learn.rental.models.Guest;
import learn.rental.models.Host;
import org.springframework.stereotype.Service;



@Service
public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email) throws DataException {
        return repository.findByEmail(email);

    }


}
