import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
    private DateTimeFormatter dtf;
    private LocalDateTime currentTime;

    public DateAndTime() {
        this.dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    public String getCurrentDateAndTime() {
        currentTime = LocalDateTime.now();
        return dtf.format(currentTime);
    }
}
