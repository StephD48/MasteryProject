package learn.rental.domain;

import learn.rental.data.DataException;
import learn.rental.data.GuestRepository;
import learn.rental.models.Guest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findByEmail(String email) throws DataException {
        return repository.findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}
