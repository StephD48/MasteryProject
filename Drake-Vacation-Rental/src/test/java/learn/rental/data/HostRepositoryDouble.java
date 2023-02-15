package learn.rental.data;

import learn.rental.models.Host;

import java.util.List;

public class HostRepositoryDouble implements HostRepository{
    @Override
    public List<Host> findAll() throws DataException {
        return null;
    }

    @Override
    public List<Host> findByEmail(String email) throws DataException {
        return null;
    }
}
