package ly.smarthive.school.models;

public class Attendance {
    int id;
    String day;

    public Attendance() {}

    public Attendance(int id, String day) {
        this.id = id;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
