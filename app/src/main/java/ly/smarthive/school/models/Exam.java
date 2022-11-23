package ly.smarthive.school.models;

public class Exam {
    int id;
    String subject,period,date;

    public Exam() {
    }

    public Exam(int id, String subject, String period, String date) {
        this.id = id;
        this.subject = subject;
        this.period = period;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
