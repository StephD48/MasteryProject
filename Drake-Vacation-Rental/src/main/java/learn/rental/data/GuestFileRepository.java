package learn.rental.data;

import learn.rental.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository {

    private final String filePath;


    public GuestFileRepository(@Value("${dataFileGuestPath}") String filePath) {
        this.filePath = filePath;
    }

        @Override
        public List<Guest> findAll () throws DataException {
            ArrayList<Guest> result = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                reader.readLine(); // read header

                for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                    String[] fields = line.split(",", -1);
                    if (fields.length == 6) {
                        result.add(deserialize(fields));
                    }
                }
            } catch (IOException ex) {

            }
            return result;
        }


    @Override
    public Guest findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    private Guest deserialize(String[] fields) {
        Guest result = new Guest();
        result.setGuestId(Integer.parseInt(fields[0]));
        result.setFirstName(fields[1]);
        result.setLastName(fields[2]);
        result.setEmail(fields[3]);
        result.setPhoneNumber(fields[4]);
        result.setState(fields[5]);
        return result;
    }


}





