package learn.rental.data;

import learn.rental.models.Guest;

import java.util.List;

public interface GuestRepository {

   List<Guest>findAll() throws DataException;

   List<Guest> findByEmail(String email) throws DataException;
}
