package ly.smarthive.school.models;

import java.util.ArrayList;

public class TimeTable {
    int id;
    String day;
    ArrayList<String> subs = new ArrayList<>();
    public TimeTable() {
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

    public String getSubs(int index) {

        if(index >= this.subs.size() || index < 0){
            return "--";
        }else{
            return this.subs.get(index);
        }
    }

    public void setSubs(String sub) {
        this.subs.add(sub);
    }
}
