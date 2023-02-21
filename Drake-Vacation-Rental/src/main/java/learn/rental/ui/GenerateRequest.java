package learn.rental.ui;



import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GenerateRequest {
    private LocalDate start;
    private LocalDate end;
    private int count;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
