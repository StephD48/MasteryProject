package learn.rental.data;


import learn.rental.models.Host;

import java.util.List;

public interface HostRepository {

    List<Host> findAll() throws DataException;

    Host findByEmail(String email) throws DataException;
}
