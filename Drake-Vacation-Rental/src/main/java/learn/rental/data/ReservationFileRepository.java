package learn.rental.data;

import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ReservationFileRepository implements ReservationRepository {

    private static final String HEADER = "id,start_date,end_date,guest_id,total";

    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    private String getFilePath(Host hostId) {
        return Paths.get(directory,hostId + "csv").toString();
    }

    @Override
    public List<Reservation> findByHost(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 8) {
                    result.add(deserialize(fields,LocalDate, hostId));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }



    @Override
    public Reservation add(Reservation reservation) {
        return null;
    }
    @Override
    public boolean update(Reservation reservation) {
        return false;
    }
    @Override
    public Reservation delete(Reservation reservation) {
        return null;
    }
    private void writeAll(List<Reservation> reservations, Host hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for(Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }

        }catch(FileNotFoundException ex) {

        }
    }
    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getHost().getHostId(),
                reservation.getGuest().getGuestId(),
                reservation.getStartDate(),
                reservation.getEndDate());

    }

    private Reservation deserialize(String[] fields, LocalDate date, String hostId) {
        Reservation result = new Reservation();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(date);
        result.setEndDate(date);
        result.setTotal(new BigDecimal(fields[3]));

        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[1]));
        result.setGuest(guest);

        Host host = new Host();
        host.setHostId(Integer.parseInt(fields[2]));
        result.setHost(host);
        return result;
    }
}
