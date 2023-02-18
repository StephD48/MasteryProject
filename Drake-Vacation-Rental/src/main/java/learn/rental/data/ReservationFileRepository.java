package learn.rental.data;

import learn.rental.models.Guest;
import learn.rental.models.Host;
import learn.rental.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
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

    public ReservationFileRepository(@Value("${dataFileReservationPath}") String directory) {
        this.directory = directory;
    }


    private String getFilePath(String hostId) {
        return Paths.get(directory,hostId + ".csv").toString();
    }


    @Override
    public List<Reservation> findByHost(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {

                    result.add(deserialize(fields, hostId));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        List<Reservation> all = findByHost(reservation.getHost().getHostId());
        int nextId = getNextId(all);
        reservation.setReservationId(nextId);
        all.add(reservation);
        writeAll(all, reservation.getHost().getHostId());
        return reservation;
    }
    @Override
    public boolean  update(Reservation reservation) throws DataException {

        return false;
    }


    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(HEADER);

            for(Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }

        }catch(FileNotFoundException ex) {

        }
    }

    private int getNextId(List<Reservation> reservations) {
        int maxId = 0;
        for (Reservation reservation : reservations) {
            if (maxId < reservation.getReservationId()) {
                maxId = reservation.getReservationId();
            }
        }
        return maxId + 1;
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s,%s",
                reservation.getReservationId(),
                reservation.getHost().getHostId(),
                reservation.getGuest().getGuestId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getTotal());

    }

    private Reservation deserialize(String[] fields,String hostId) {
        Reservation result = new Reservation();
        result.setReservationId(Integer.parseInt(fields[0]));
        result.setStartDate(LocalDate.parse(fields[1]));
        result.setEndDate(LocalDate.parse(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        Host host = new Host();
        host.setHostId(hostId);
        result.setHost(host);


        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

       return result;
    }


}
