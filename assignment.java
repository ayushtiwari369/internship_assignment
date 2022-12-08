import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class assignment {
    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.of(2022, 12, 7, 13, 0, 0); // calendar datetime
        int timeRequired = 40; // integer #hours need to complete the task
        LocalTime workingHourStart = LocalTime.of(12, 30, 0);
        LocalTime workingHourEnd = LocalTime.of(17, 30, 0);

        // all the dates when the emp is on leave
        LocalDate leave1 = LocalDate.of(2022, 12, 1);
        LocalDate leave2 = LocalDate.of(2022, 12, 5);
        LocalDate leave3 = LocalDate.of(2022, 12, 15);

        List<LocalDate> leaves = new ArrayList<>();
        leaves.add(leave1);
        leaves.add(leave2);
        leaves.add(leave3);

        // return the end time when the task is over
        LocalDateTime endTime = getEndTime(startTime, timeRequired, workingHourStart, workingHourEnd, leaves);
        System.out.println();
        System.out.println("\nwork started on :" + startTime.toLocalDate() + "T" + workingHourStart);
        System.out.println("time required to complete the task : " + timeRequired + " hours");
        System.err.println("considering leaves, work will be finished on : " + endTime);

    }

    private static LocalDateTime getEndTime(LocalDateTime startTime, int timeRequired, LocalTime workingHourStart,
            LocalTime workingHourEnd, List<LocalDate> leaves) {

        long workingHrs = Duration.between(workingHourStart, workingHourEnd).toMinutes();
        long timeLeftInitially = Duration.between(startTime.toLocalTime(), workingHourEnd).toMinutes();
        long hoursRequired = Duration.ofHours(timeRequired).toMinutes() - timeLeftInitially;
        LocalDateTime endTime = LocalDateTime.of(startTime.getYear(), startTime.getMonth(),
                startTime.getDayOfMonth() + 1,
                workingHourStart.getHour(), workingHourStart.getMinute());

        while (hoursRequired >= workingHrs) {
            endTime = endTime.plusDays(1);
            hoursRequired -= workingHrs;
        }
        if (hoursRequired > 0) {
            endTime = endTime.plusMinutes(hoursRequired);
        }

        for (LocalDate leave : leaves) {
            if (leave.equals(endTime.toLocalDate())
                    || (leave.isBefore(endTime.toLocalDate()) && leave.isAfter(startTime.toLocalDate()))) {
                System.out.println(" >>  leave at : " + leave);
                endTime = endTime.plusDays(1);
            }
        }

        return endTime;
    }
}