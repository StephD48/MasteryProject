package learn.rental.data;


import learn.rental.models.Host;

import java.util.ArrayList;
import java.util.List;


public class HostRepositoryDouble implements HostRepository{



    private final List<Host> hosts = new ArrayList<>();
    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return findByEmail(email);
    }


}
