package learn.rental.domain;


import learn.rental.data.DataException;
import learn.rental.data.HostRepository;
import learn.rental.models.Host;
import org.springframework.stereotype.Service;


@Service

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public Host findByEmail(String email) throws DataException {
        return repository.findByEmail(email);

    }
}
