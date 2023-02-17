package learn.rental.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {


    private int reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;
    private String hostId;
    private Host host;
    private String guestId;
    private Guest guest;


   /* public Reservation(int reservationId, LocalDate startDate, LocalDate endDate, BigDecimal total, String hostId, Host host, String guestId, Guest guest) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
        this.hostId = hostId;
        this.host = host;
        this.guestId = guestId;
        this.guest = guest;
    }*/

    /*public Reservation() {

    }*/

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }



/* public BigDecimal getTotal() {
        if (resevation == null || reservation.getTotal() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = new BigDecimal(d).setScale(4, RoundingMode.HALF_UP);
        return item.getDollarPerKilogram().multiply(kilos);
    }*/
}
