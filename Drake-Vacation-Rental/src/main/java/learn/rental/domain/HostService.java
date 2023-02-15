package learn.rental.domain;


import learn.rental.data.DataException;
import learn.rental.data.HostRepository;
import learn.rental.models.Host;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findByEmail(String email) throws DataException {
        return repository.findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}
