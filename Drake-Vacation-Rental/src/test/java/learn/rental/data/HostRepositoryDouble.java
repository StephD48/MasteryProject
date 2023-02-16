package learn.rental.data;

import learn.rental.models.Guest;
import learn.rental.models.Host;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostRepositoryDouble implements HostRepository{

    private final ArrayList<Host> hosts = new ArrayList<>();
    @Override
    public List<Host> findAll() {
        return hosts;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return null;
    }


}
