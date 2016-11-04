package pialkanti.com.libraryreminder.POJO;

/**
 * Created by Pial on 11/4/2016.
 */
public class AlarmDetails {
    int Year, Month, Day, Hour, Minute, Second;
    String repeat;

    public AlarmDetails(int year, int month, int day, int hour, int minue, int second, String repeat) {
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minue;
        Second = second;
        this.repeat = repeat;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minue) {
        Minute = minue;
    }

    public int getSecond() {
        return Second;
    }

    public void setSecond(int second) {
        Second = second;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
